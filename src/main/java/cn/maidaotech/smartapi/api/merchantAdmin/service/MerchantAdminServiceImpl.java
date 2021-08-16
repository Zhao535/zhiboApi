package cn.maidaotech.smartapi.api.merchantAdmin.service;

import cn.maidaotech.smartapi.api.merchant.entity.MerchantErrors;
import cn.maidaotech.smartapi.api.merchant.entity.MerchantWrapOption;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.*;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminRole;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminSession;
import cn.maidaotech.smartapi.api.merchantAdmin.repository.MerchantAdminRepository;
import cn.maidaotech.smartapi.api.merchantAdmin.repository.MerchantAdminRoleRepository;
import cn.maidaotech.smartapi.api.merchantAdmin.repository.MerchantAdminSessionRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.exception.SessionServiceException;
import cn.maidaotech.smartapi.common.sms.SmsService;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import cn.maidaotech.smartapi.common.utils.DateUtils;
import cn.maidaotech.smartapi.common.utils.MobileUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MerchantAdminServiceImpl implements MerchantAdminService, MerchantErrors {

    @Value("${merchantAdmin.salt}")
    private String salt;

    @Value("${sessionHour}")
    private int sessionHours;
    @Autowired
    private MerchantAdminRoleRepository merchantAdminRoleRepository;
    @Autowired
    private MerchantAdminRepository merchantAdminRepository;
    @Autowired
    private MerchantAdminSessionRepository merchantAdminSessionRepository;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SmsService smsService;


    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, MerchantAdmin> merchantAdminCache;

    private KvCacheWrap<Integer, MerchantAdminRole> merchantAdminRoleCache;

    @PostConstruct
    public void init() {
        merchantAdminCache = kvCacheFactory.create(new CacheOptions("merchantAdmin", 1, 3600), new RepositoryProvider<Integer, MerchantAdmin>() {

            @Override
            public MerchantAdmin findByKey(Integer id) throws Exception {
                return merchantAdminRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, MerchantAdmin> findByKeys(Collection<Integer> ids) throws Exception {
                List<MerchantAdmin> admins = merchantAdminRepository.findAllById(ids);
                return admins.stream().collect(Collectors.toMap(MerchantAdmin::getId, it -> it));
            }
        }, new BeanModelConverter<>(MerchantAdmin.class));

        merchantAdminRoleCache = kvCacheFactory.create(new CacheOptions("merchantAdminRole", 1, 3600), new RepositoryProvider<Integer, MerchantAdminRole>() {

            @Override
            public MerchantAdminRole findByKey(Integer id) throws Exception {
                return merchantAdminRoleRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, MerchantAdminRole> findByKeys(Collection<Integer> ids) throws Exception {
                throw new ArgumentServiceException("该方法暂未使用");
            }
        }, new BeanModelConverter<>(MerchantAdminRole.class));
    }

    @Override
    public void saveMerchantAdmin(MerchantAdmin merchantAdmin) throws Exception {
        if (merchantAdmin.getMerchantId() == null) {
            throw new ServiceException(ERR_MERCHANT_ADMIN_NOT_NULL);
        }
        if (merchantAdmin.getName() == null) {
            throw new ServiceException(ERR_MERCHANT_ADMIN_NOT_NULL);
        }

        String mobile = MobileUtils.formatMobile(merchantAdmin.getMobile());
        if (StringUtils.isEmpty(mobile)) {
            throw new ServiceException(ERR_MERCHANT_ADMIN_NOT_NULL);
        }
        merchantAdmin.setPassword(StringUtils.encryptPassword(merchantAdmin.getPassword(), salt));
        merchantAdmin.setStatus(MerchantAdminStatus.NORMAL.value());
        merchantAdmin.setCreatedAt(System.currentTimeMillis());
        merchantAdmin.setMobile(mobile);
        MerchantAdmin merchantAdminExist = merchantAdminRepository.findByMobileAndMerchantId(merchantAdmin.getMobile(), merchantAdmin.getMerchantId());
        if (Objects.nonNull(merchantAdminExist)) {
            throw new ArgumentServiceException("账号已经存在");
        }
        if (merchantAdmin.getId() == null || merchantAdmin.getId() == 0) {
            List<MerchantAdminRole> roles = merchantAdminRoleRepository.findByMerchantId(merchantAdmin.getMerchantId());
            if (roles.size() < 1) {
                MerchantAdminRole role = new MerchantAdminRole();
                role.setName("超级管理员");
                List<String> permissions = new ArrayList<>();
                permissions.add(MerchantAdminPermission.ROLE_EDIT.name());
                permissions.add(MerchantAdminPermission.ROLE_LIST.name());
                permissions.add(MerchantAdminPermission.PRODUCT_LIST.name());
                permissions.add(MerchantAdminPermission.PRODUCT_EDIT.name());
                role.setPermissions(permissions);
                role.setMerchantId(merchantAdmin.getMerchantId());
                role = merchantAdminRoleRepository.save(role);
                merchantAdmin.setRoleId(role.getId());
            }
            merchantAdminRepository.save(merchantAdmin);
        } else {
            MerchantAdmin exist = getById(merchantAdmin.getId());
            exist.setImg(merchantAdmin.getImg());
            exist.setName(merchantAdmin.getName());
            merchantAdminRepository.save(exist);
            merchantAdminCache.removeSafely(merchantAdmin.getId());
        }

    }

    private MerchantAdmin findById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        MerchantAdmin merchantAdmin = merchantAdminCache.findByKey(id);
        return merchantAdmin;
    }

    @Override
    public MerchantAdmin getById(Integer id) throws Exception {
        MerchantAdmin merchantAdmin = findById(id);
        if (merchantAdmin == null) {
            throw new DataNotFoundServiceException();
        }
        return merchantAdmin;
    }

    @Override
    public MerchantAdminSessionWrapper signin(String mobile, String password) throws Exception {
        if (StringUtils.isEmpty(password)) {
            throw new ArgumentServiceException("password");
        }
        MerchantAdmin merchantAdmin = null;
        if (MobileUtils.formatMobile(mobile) != null) {
            merchantAdmin = merchantAdminRepository.findByMobile(MobileUtils.formatMobile(mobile));
        }
        if (merchantAdmin == null) {
            throw new ServiceException(ERR_MERCHANT_ADMIN_ACCOUNT_NOT_EXIST);
        }
        String encrypt = StringUtils.encryptPassword(password, salt);
        if (!merchantAdmin.getPassword().equals(encrypt)) {
            throw new ServiceException(ERR_MERCHANT_ADMIN_PASSWORD_WRONG);
        }
        if (merchantAdmin.getStatus() == MerchantAdminStatus.FORBID.value()) {
            throw new ServiceException(ERR_ADMIN_STATUS_FORBID);
        }
        MerchantAdminSessionWrapper wrapper = new MerchantAdminSessionWrapper();
        wrapper.setMerchantAdmin(merchantAdmin);
        MerchantAdminSession session = new MerchantAdminSession();
        session.setMerchantAdminId(merchantAdmin.getId());
        String token = StringUtils.randomAlphanumeric(64);
        session.setToken(token);
        long now = System.currentTimeMillis();
        session.setSigninAt(now);
        session.setExpiredAt(now + sessionHours * DateUtils.MILLIS_PER_HOUR);
        merchantAdminSessionRepository.save(session);
        wrapper.setMerchantAdminSession(session);
        wrapper.setMerchantAdminRole(findRoleById(merchantAdmin.getRoleId()));
        return wrapper;
    }

    @Override
    public MerchantAdminSessionWrapper authenticate(String token) throws Exception {
        MerchantAdminSession session = merchantAdminSessionRepository.findByToken(token);
        if (session == null) {
            throw new SessionServiceException();
        }
        if (session.getExpiredAt() < System.currentTimeMillis()) {
            throw new SessionServiceException();
        }
        MerchantAdmin admin = merchantAdminRepository.findById(session.getMerchantAdminId()).orElse(null);
        if (admin == null) {
            throw new SessionServiceException();
        }
        MerchantAdminRole role = findRoleById(admin.getRoleId());
        Merchant merchant=merchantService.findById(admin.getMerchantId());
        return new MerchantAdminSessionWrapper(admin, session, role,merchant);
    }

    private MerchantAdminRole findRoleById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        MerchantAdminRole adminRole = null;
        adminRole = merchantAdminRoleRepository.findById(id).orElse(null);
        if (adminRole == null) {
            throw new ServiceException(ERR_ADMIN_ROLE_NOT_EXIST);
        }
        return adminRole;
    }

    @Override
    public void changeStatus(Integer id) throws Exception {
        MerchantAdmin merchantAdmin = merchantAdminRepository.findById(id).orElse(null);
        if (Objects.isNull(merchantAdmin)) {
            throw new ArgumentServiceException("账号不存在");
        }
        if (merchantAdmin.getStatus() == MerchantAdminStatus.NORMAL.value()) {
            merchantAdmin.setStatus(MerchantAdminStatus.FORBID.value());
        } else {
            merchantAdmin.setStatus(MerchantAdminStatus.NORMAL.value());
        }
        merchantAdminRepository.save(merchantAdmin);
        merchantAdminCache.removeSafely(id);
    }

    @Override
    public List<MerchantAdmin> findByMerchantId(Integer merchantId) throws Exception {
        List<MerchantAdmin> merchantAdmins = merchantAdminRepository.findAllByMerchantId(merchantId);
        return merchantAdmins;
    }

    @Override
    public List<MerchantAdmin> findMerchantAdmins(Integer merchantAdminId) throws Exception {
        MerchantAdmin admin = merchantAdminRepository.findById(merchantAdminId).orElse(null);
        Merchant merchant = merchantService.item(admin.getMerchantId(), MerchantWrapOption.getDefault());
        return merchantAdminRepository.findAllByMerchantId(merchant.getId());
    }

    @Override
    public List<MerchantAdmin> items() throws Exception {
        List<MerchantAdmin> admins = merchantAdminRepository.findAll();
        return admins;
    }

    @Override
    public List<MerchantAdmin> itemsForMerchantAdmin() throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        List<MerchantAdmin> admins = merchantAdminRepository.findAllByMerchantId(merchantId);
        return admins;
    }

    @Override
    public Page<MerchantAdminSession> singInLog(MerchantAdminSessionQo qo) throws Exception {
        return merchantAdminSessionRepository.findAll(qo);
    }

    @Override
    @Transactional
    public void saveRole(MerchantAdminRole role) throws Exception {
        if (Objects.isNull(role.getName())) {
            throw new ArgumentServiceException("name");
        }
        if (Objects.isNull(role.getPermissions())) {
            throw new ArgumentServiceException("choose_permission");
        }
        boolean isNew = Objects.isNull(role.getId()) || role.getId() == 0;
        if (isNew) {
            role.setMerchantId(MerchantAdminContexts.requireMerchant().getId());
            merchantAdminRoleRepository.save(role);
        } else {
            MerchantAdminRole roleExist = merchantAdminRoleCache.findByKey(role.getId());
            if (Objects.isNull(roleExist)) {
                throw new ArgumentServiceException("role not found ");
            }
            roleExist.setPermissions(role.getPermissions());
            roleExist.setName(role.getName());
            merchantAdminRoleRepository.save(roleExist);
        }
    }

    @Override
    @Transactional
    public void resetPassword(String mobile, String newPassword) throws Exception {
        String formatMobile = MobileUtils.formatMobile(mobile);
        if (StringUtils.isEmpty(formatMobile)) {
            throw new ArgumentServiceException("手机号不能为空");
        }
        if (StringUtils.isEmpty(newPassword) || newPassword.length() < 6) {
            throw new ArgumentServiceException("新密码长度不得小于六位数");
        }
        MerchantAdmin admin = merchantAdminRepository.findByMobile(mobile);
        if (Objects.isNull(admin)) {
            throw new ArgumentServiceException("该管理员不存在");
        }
        String finalPassword = StringUtils.encryptPassword(newPassword, salt);
        if (finalPassword.equals(admin.getPassword())) {
            throw new ArgumentServiceException("新密码不能与原密码相同");
        }
        admin.setPassword(finalPassword);
        merchantAdminRepository.save(admin);
        merchantAdminCache.removeSafely(admin.getId());
    }

    @Override
    public Page<MerchantAdminRole> roles(MerchantAdminRoleQo qo) throws Exception {
        qo.setMerchantId(MerchantAdminContexts.requireMerchant().getId());
        Page<MerchantAdminRole> rolePage = merchantAdminRoleRepository.findAll(qo);
        roleWrap(rolePage.getContent());
        return rolePage;
    }

    private void roleWrap(Collection<MerchantAdminRole> roles) throws Exception {
        for (MerchantAdminRole role : roles) {
            role.setPstr(MerchantAdminPermissionVo.initOmsPermissionsByPs(role.getPermissions()));
        }
    }

    @Override
    public MerchantAdminRole role(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("role_id");
        }
        MerchantAdminRole role = merchantAdminRoleCache.findByKey(id);
        roleWrap(Collections.singleton(role));
        return role;
    }

    @Override
    @Transactional
    public void removeRole(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("role_id");
        }
        MerchantAdminRole role = merchantAdminRoleCache.findByKey(id);
        if (Objects.isNull(role)) {
            throw new ArgumentServiceException("role not found");
        }
        merchantAdminRoleRepository.delete(role);
    }

    @Override
    public List<MerchantAdminRole> roleList() throws Exception {
        return merchantAdminRoleRepository.findByMerchantId(MerchantAdminContexts.requireMerchant().getId());
    }

}
