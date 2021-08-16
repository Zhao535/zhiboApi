package cn.maidaotech.smartapi.api.admin.service;

import cn.maidaotech.smartapi.api.admin.model.*;
import cn.maidaotech.smartapi.api.admin.repository.AdminRepository;
import cn.maidaotech.smartapi.api.admin.repository.AdminRoleRepository;
import cn.maidaotech.smartapi.api.admin.repository.AdminSessionRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.AdminContexts;
import cn.maidaotech.smartapi.common.exception.*;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.DateUtils;
import cn.maidaotech.smartapi.common.utils.MobileUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService, AdminErrors {
    @Value("${admin.salt}")
    private String salt;
    @Value("${admin.session-hours}")
    private int sessionHours;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminRoleRepository adminRoleRepository;
    @Autowired
    private AdminSessionRepository adminSessionRepository;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Admin> adminCache;

    @PostConstruct
    public void init() {
        adminCache = kvCacheFactory.create(new CacheOptions("admin", 1, 3600), new RepositoryProvider<Integer, Admin>() {

            @Override
            public Admin findByKey(Integer id) throws Exception {
                throw new UnsupportedOperationException("findByKey");
            }

            @Override
            public Map<Integer, Admin> findByKeys(Collection<Integer> ids) throws Exception {
                List<Admin> admins = adminRepository.findAllById(ids);
                return admins.stream().collect(Collectors.toMap(Admin::getId, it -> it));
            }
        }, new BeanModelConverter<>(Admin.class));
    }


    @Override
    public Map<Integer, Admin> findByIdIn(Set<Integer> ids) {
        return adminCache.findByKeys(ids);
    }

    @Override
    public void save(Admin admin) throws Exception {
        if (StringUtils.isEmpty(admin.getName())) {
            throw new ServiceException(ERR_ADMIN_NAME_EMPTY);
        }
        if (!StringUtils.isEmail(admin.getEmail())) {
            throw new ServiceException(ERR_ADMIN_EMAIL_INVALID);
        }
        String mobile = MobileUtils.formatMobile(admin.getMobile());
        if (mobile == null) {
            throw new ServiceException(ERR_ADMIN_MOBILE_INVALID);
        }
        Admin exist = adminRepository.findByMobile(mobile);
        if (exist != null && !exist.getId().equals(admin.getId())) {
            throw new ServiceException(ERR_ADMIN_MOBILE_EXIST);
        }
        admin.setMobile(mobile);
        if (admin.getId() == null) {
            admin.setStatus(Constants.STATUS_OK);
            admin.setPassword(StringUtils.encryptPassword(admin.getPassword(), salt));
            admin.setCreatedAt(System.currentTimeMillis());
        }
        adminRepository.save(admin);
        adminCache.flush();
    }


    @Override
    public AdminSessionWrapper signIn(String userName, String password) throws Exception {
        if (StringUtils.isEmpty(userName)) {
            throw new ArgumentAccessException("userName");
        }
        if (StringUtils.isEmpty(password)) {
            throw new ArgumentServiceException("password");
        }
        Admin admin = null;
        if (StringUtils.isEmail(userName)) {
            admin = adminRepository.findByEmail(userName);
        }
        if (MobileUtils.formatMobile(userName) != null) {
            admin = adminRepository.findByMobile(MobileUtils.formatMobile(userName));
        }
        if (admin == null) {
            throw new ServiceException(ERR_ADMIN_ACCOUNT_NOT_EXIST);
        }
        String encrypt = StringUtils.encryptPassword(password, salt);
        if (!admin.getPassword().equals(encrypt)) {
            throw new ServiceException(ERR_ADMIN_PASSWORD_WRONG);
        }
        if (admin.getStatus() == AdminStatus.FORBID.value()) {
            throw new ServiceException(ERR_ADMIN_ACCOUNT_FORBID);
        }
        AdminSessionWrapper wrapper = new AdminSessionWrapper();
        wrapper.setAdmin(admin);
        AdminSession session = new AdminSession();
        session.setAdminId(admin.getId());
        String token = StringUtils.randomAlphanumeric(64);
        session.setToken(token);
        Long now = System.currentTimeMillis();
        session.setSigninAt(now);
        session.setExpireAt(now + sessionHours * DateUtils.MILLIS_PER_HOUR);
        adminSessionRepository.save(session);
        wrapper.setAdminSession(session);
        AdminRole role = adminRoleRepository.findById(admin.getRoleId()).orElse(null);
        wrapper.setRole(role);
        return wrapper;
    }

    @Override
    public AdminSessionWrapper authenticate(String token) throws Exception {
        AdminSession session = adminSessionRepository.findByToken(token);
        if (session == null) {
            throw new SessionServiceException();
        }
        if (session.getExpireAt() < System.currentTimeMillis()) {
            throw new SessionServiceException();
        }
        Admin admin = adminRepository.findById(session.getAdminId()).orElse(null);
        if (admin == null) {
            throw new SessionServiceException();
        }
        AdminRole role = findRoleById(admin.getRoleId());
        if (role == null) {
            throw new PermissionDeniedServiceException();
        }
        return new AdminSessionWrapper(admin, session, role);
    }

    @Override
    public void changeAdminStatus(Integer id) throws Exception {
        Admin admin = findById(id);
        if (admin.getStatus().equals(Constants.STATUS_HALT)) {
            admin.setStatus(Constants.STATUS_OK);
        } else {
            admin.setStatus(Constants.STATUS_HALT);
        }
        adminRepository.save(admin);
        adminCache.removeSafely(id);
    }

    @Override
    public Page<Admin> findAll(AdminQo qo) throws Exception {
        return adminRepository.findAll(qo);
    }

    @Override
    public Admin getById(Integer id) throws Exception {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) {
            throw new DataNotFoundServiceException();
        }
        return admin;
    }

    @Override
    public Admin findById(Integer id) throws Exception {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Page<AdminSession> adminSessions(AdminSessionQo qo) throws Exception {
        qo.setAdminId(AdminContexts.requestAdminId());
        return adminSessionRepository.findAll(qo);
    }

    @Override
    public void saveRole(AdminRole role) throws Exception {
        adminRoleRepository.save(role);
    }

    @Override
    public void removeRole(Integer id) throws Exception {
        AdminRole role = adminRoleRepository.findById(id).orElse(null);
        if (role == null) {
            throw new ServiceException(ERR_ADMIN_ROLE_NOT_EXIST);
        }
        adminRoleRepository.deleteById(id);
    }

    private AdminRole findRoleById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        AdminRole adminRole = null;
        adminRole = adminRoleRepository.findById(id).orElse(null);
        if (adminRole == null) {
            throw new ServiceException(ERR_ADMIN_ROLE_NOT_EXIST);
        }
        return adminRole;
    }

    @Override
    public AdminRole role(Integer id) throws Exception {
        AdminRole role = null;
        role = findRoleById(id);
        return role;
    }

    @Override
    public Admin changeRole(Integer adminID, Integer roleId) throws Exception {
        Admin admin = null;
        admin = adminRepository.findById(adminID).orElse(null);
        if (admin != null) {
            admin.setRoleId(roleId);
            adminRepository.save(admin);
        }
        return admin;
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String repeatPassword) throws Exception {
        Admin admin = null;
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(repeatPassword)) {
            throw new ServiceException(ERR_ADMIN_MESSAGE_EMPTY);
        }
        admin=AdminContexts.requestAdmin();
        String oldPasswordSalt=StringUtils.encryptPassword(oldPassword,salt);
        if (!oldPasswordSalt.equals(admin.getPassword())){
            throw new ArgumentAccessException("旧密码错误");
        }
        String newPasswordSalt=StringUtils.encryptPassword(repeatPassword,salt);
        if (newPasswordSalt.equals(admin.getPassword())){
            throw new ArgumentAccessException("新密码不能与旧密码相同");
        }
        admin.setPassword(newPasswordSalt);
        adminRepository.save(admin);
        adminCache.removeSafely(admin.getId());
    }


    @Override
    public List<AdminRole> roles(boolean b) throws Exception {
        List<AdminRole> roles = adminRoleRepository.findAll();
        if (b) {
            for (AdminRole r : roles) {
                r.setPstr(AdminPermissionVO.initOmsPermissionsByPs(r.getPermissions()));
            }
        }
        return roles;

    }
}

