package cn.maidaotech.smartapi.api.user.service;


import cn.maidaotech.smartapi.api.user.model.User;
import cn.maidaotech.smartapi.api.user.model.UserError;
import cn.maidaotech.smartapi.api.user.model.UserSession;
import cn.maidaotech.smartapi.api.user.model.UserSessionWrapper;
import cn.maidaotech.smartapi.api.user.repository.UserRepository;
import cn.maidaotech.smartapi.api.user.repository.UserSessionRepository;
import cn.maidaotech.smartapi.api.usrCollect.service.CollectService;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.mail.MailService;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.*;
import cn.maidaotech.smartapi.common.vcode.model.Code;
import cn.maidaotech.smartapi.common.vcode.model.CodePayload;
import cn.maidaotech.smartapi.common.vcode.service.CodeService;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;


@Service
public class UserServicelmpl implements UserService, UserError {
    private final String ACCOUNT_CREATE_MAIL = R.getString("email/admin_create.html");
    private final int PAGENUM = 1;
    private final int PAGESIZE = 10;
    @Value("${user.salt}")
    private String salt;
    @Value("${user.session-hours}")
    private int sessionHours;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private KvCacheFactory kvCacheFactory;
    @Autowired
    private CodeService codeService;
    @Autowired
    private CollectService collectService;


    private KvCacheWrap<Integer, User> userCache;


    private String randomPassword() {
        return StringUtils.randomCapitalAlpha(1) + StringUtils.randomAlphanumeric(11);
    }

    @PostConstruct
    public void init() {
        userCache = kvCacheFactory.create(new CacheOptions("user", 1, 3600),
                new RepositoryProvider<Integer, User>() {
                    @Override
                    public User findByKey(Integer id) throws Exception {
                        return userRepository.findById(id).orElse(null);
                    }

                    @Override
                    public Map<Integer, User> findByKeys(Collection<Integer> ids) throws Exception {
                        return null;
                    }
                }, new BeanModelConverter<>(User.class));
    }


    @Override
    public void save(User user, String code) throws Exception {
        if (Objects.isNull(user.getId()) || user.getId() == 0) {
            String mobile = MobileUtils.formatMobile(user.getMobile());
            if (mobile == null) {
                throw new ServiceException(ERR_USER_MOBILE_INVALID);
            }
            User exist = userRepository.findByMobile(mobile);
            if (exist != null) {
                throw new ServiceException(ERR_USER_MOBILE_EXIST);
            }
            if (StringUtils.isNull(user.getPassword())) {
                throw new ServiceException(ERR_USER_PASSWORD_EMPTY);
            }
            if (StringUtils.isNull(code)) {
                throw new ServiceException(ERR_USER_CODE_WRONG);
            }
            Code realCode = codeService.getAndMatchCode("register", mobile, code);
            if (realCode == null) {
                throw new ServiceException(ERR_USER_CODE_WRONG);
            }
            user.setStatus(Constants.STATUS_OK);
            user.setName(UUID.randomUUID().toString().substring(0, 8));
            user.setMobile(mobile);
            user.setPassword(StringUtils.encryptPassword(user.getPassword(), salt));
            user.setCreatedAt(System.currentTimeMillis());
            userRepository.save(user);
        } else {
            User exist = userRepository.findById(user.getId()).orElse(null);

            assert exist != null;
            exist.setAge(user.getAge());
            exist.setAvatar(user.getAvatar());
            exist.setSex(user.getSex());
            exist.setName(user.getName());
            userRepository.save(exist);
            userCache.removeSafely(user.getId());
        }

    }

    private User findById(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        User user = userCache.findByKey(id);
        return user;
    }

    private User getById(Integer id) throws Exception {
        User user = findById(id);
        if (Objects.isNull(user)) {
            throw new DataNotFoundServiceException();
        }
        return user;
    }


    @Override
    public UserSessionWrapper signIn(String mobile, String passWord, String code) throws Exception {
        if (StringUtils.isEmpty(mobile)) {
            throw new ArgumentAccessException("mobile");
        }
        if (StringUtils.isEmpty(passWord) && StringUtils.isEmpty(code)) {
            throw new ArgumentServiceException("passwordorcode");
        }
        User user = userRepository.findByMobile(MobileUtils.formatMobile(mobile));

        if (user == null) {
            throw new ServiceException(ERR_USER_ACCOUNT_NOT_EXIST);
        }
        if (passWord == null) {
            Code realCode = codeService.getAndMatchCode("signin", mobile, code);
            if (realCode == null) {
                throw new ServiceException(ERR_USER_CODE_WRONG);
            }
        } else {
            String encrypt = StringUtils.encryptPassword(passWord, salt);
            if (!user.getPassword().equals(encrypt)) {
                throw new ServiceException(ERR_USER_PASSWORD_WRONG);
            }
        }
        UserSessionWrapper wrapper = new UserSessionWrapper();
        wrapper.setUser(user);
        UserSession session = new UserSession();
        session.setUserId(user.getId());
        String token = StringUtils.randomAlphanumeric(64);
        session.setToken(token);
        Long now = System.currentTimeMillis();
        session.setSigninAt(now);
        session.setExpireAt(now + sessionHours * DateUtils.MILLIS_PER_HOUR);
        userSessionRepository.save(session);
        wrapper.setUserSession(session);
        return wrapper;
    }

    @Override
    public User item(Integer id) throws Exception {
        User user = getById(id);
        user.setCollectNum(collectService.collectNum());
        user.setFocusNum(collectService.focusNum());
        return user;
    }


    @Override
    public void updatePassword(String mobile, String passwrod, String newPassword) throws Exception {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(passwrod)) {
            throw new ServiceException(ERR_USER_MESSEAGE_EMPTY);
        }
        User user = null;

        mobile = MobileUtils.formatMobile(mobile);
        if (mobile != null) {
            user = userRepository.findByMobile(mobile);
        }
        if (user == null) {
            throw new ServiceException(ERR_USER_ACCOUNT_NOT_EXIST);
        }
        String encrypt = StringUtils.encryptPassword(passwrod, salt);
        if (!user.getPassword().equals(encrypt)) {
            throw new ServiceException(ERR_USER_PASSWORD_WRONG);
        }
        if (newPassword == null) {
            throw new ServiceException(ERR_USER_SET_PASSWORD);
        }
        user.setPassword(StringUtils.encryptPassword(newPassword, salt));
        userRepository.save(user);
    }

    @Override
    public UserSessionWrapper authenticate(String token) throws Exception {
        UserSession userSession = userSessionRepository.findByToken(token);
        if (userSession == null) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        if (userSession.getExpireAt() < System.currentTimeMillis()) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        User user = userRepository.findById(userSession.getUserId()).orElse(null);
        if (user == null) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        return new UserSessionWrapper(user, userSession);
    }

    @Override
    public Page<User> items(PageRequest pageRequest) throws Exception {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public void sendCode(String mobile) throws Exception {
        if (StringUtils.isEmpty(mobile)) {
            throw new ServiceException(ERR_USER_NAME_EMPTY);
        } else {
            mobile = MobileUtils.formatMobile(mobile);
            codeService.send(mobile, new CodePayload().setSubject("signin"));
        }
    }


    @Override
    public List<User> findAllById(Collection<Integer> userIds) throws Exception {
        if (CollectionUtils.isEmpty(userIds)) {
            throw new ArgumentServiceException("useId");
        }
        List<User> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            throw new ServiceException(ERR_USER_ACCOUNT_NOT_EXIST);
        }
        return users;
    }

    @Override
    public void sendRegisterCode(String mobile) throws Exception {
        if (StringUtils.isEmpty(mobile)) {
            throw new ServiceException(ERR_USER_NAME_EMPTY);
        } else {
            mobile = MobileUtils.formatMobile(mobile);
            codeService.send(mobile, new CodePayload().setSubject("register"));
        }
    }


    @Override
    public void changeMobile(String mobile, String newMobile, String code) throws Exception {
        User user = userRepository.findByMobile(mobile);
        if (Objects.isNull(user)) {
            throw new ServiceException(ERR_USER_ACCOUNT_NOT_EXIST);
        }
        Code relCode = codeService.getAndMatchCode("changeMobile", mobile, code);
        if (Objects.isNull(relCode)) {
            throw new ServiceException(ERR_VAL_CODE_ERROR);
        }
        user.setMobile(newMobile);
        userRepository.save(user);
    }

    @Override
    public void sendChangeMobileCode(String mobile) throws Exception {
        if (StringUtils.isEmpty(mobile)) {
            throw new ServiceException(ERR_USER_NAME_EMPTY);
        } else {
            mobile = MobileUtils.formatMobile(mobile);
            codeService.send(mobile, new CodePayload().setSubject("changeMobile"));
        }
    }
}

