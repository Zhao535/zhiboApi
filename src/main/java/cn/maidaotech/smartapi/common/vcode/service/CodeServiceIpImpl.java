package cn.maidaotech.smartapi.common.vcode.service;

import cn.maidaotech.smartapi.common.L;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.SingleRepositoryProvider;
import cn.maidaotech.smartapi.common.mail.MailHelper;
import cn.maidaotech.smartapi.common.mail.MailService;
import cn.maidaotech.smartapi.common.sms.SmsService;
import cn.maidaotech.smartapi.common.task.TaskService;
import cn.maidaotech.smartapi.common.utils.DateUtils;
import cn.maidaotech.smartapi.common.utils.MobileUtils;
import cn.maidaotech.smartapi.common.utils.R;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import cn.maidaotech.smartapi.common.vcode.model.*;
import cn.maidaotech.smartapi.common.vcode.repositroy.CodeRepositroy;
import cn.maidaotech.smartapi.common.vcode.sendCodeTask.SendCodeTask;
import com.sunnysuperman.commons.util.PlaceholderUtil;
import com.sunnysuperman.kvcache.KvCache;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class CodeServiceIpImpl implements CodeService, CodeErrors {

    private final String SIGN_IN_CODE_MAIL = R.getString("email/user_signin_code.html");
    @Autowired
    private CodeRepositroy codeRepositroy;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private KvCacheFactory kvCacheFactory;
    @Autowired
    private MailService mailService;

    private KvCache<CodeKey, Code> codeCache;

    @PostConstruct
    public void init() {
        codeCache = kvCacheFactory.create(new CacheOptions("vcode", 1, 300),
                new SingleRepositoryProvider<CodeKey, Code>() {
                    @Override
                    public Code findByKey(CodeKey key) throws Exception {
                        return codeRepositroy.findBySubjectAndUsername(key.getSubject(), key.getUsername());
                    }
                }, new BeanModelConverter<>(Code.class));
    }

    private class CodeKey {
        private String subject;
        private String username;

        public CodeKey(String subject, String username) {
            this.subject = subject;
            this.username = username;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CodeKey codeKey = (CodeKey) o;
            return Objects.equals(subject, codeKey.subject) &&
                    Objects.equals(username, codeKey.username);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subject, username);
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    @Override
    public Code send(String username, CodePayload payload) throws Exception {
        final String subject = payload.getSubject();
        if (subject == null) {
            throw new NullPointerException("subject");
        }
        CodeDeliveryType deliveryType = null;
        String mobile = MobileUtils.formatMobile(username);
        CodeGenerator generator = null;
        Long now = System.currentTimeMillis();
        Code code = new Code();
        code.setCreatedAt(now);
        code.setUsername(username);
        code.setSubject(subject);
        code.setExpiredAt(now + 10 * DateUtils.MILLIS_PER_MINUTE);
        code.setExtra(payload.getExtra());
        if (mobile != null) {
            deliveryType = CodeDeliveryType.SMS;
            generator = DefaultCodeGenerator.getInstance();
            code.setCode(generator.generatorCode());
            SendSmsTask task = new SendSmsTask(subject, username, code);
            taskService.addTask(task);
        }
        if (StringUtils.isEmail(username)) {
            deliveryType = CodeDeliveryType.EMAIL;
            generator = MailCodeGenerator.getINSTANCE();
            code.setCode(generator.generatorCode());
            SendMailTask task = new SendMailTask(subject, username, code);
            taskService.addTask(task);
        } else {
            // TODO: 2020/12/15  throw new ServiceException(ERR_USER_MOBILE_INVALID);
        }
        return code;
    }


    @Override
    public Code getAndMatchCode(String subject, String username, String code) throws Exception {
        if (StringUtils.isEmpty(subject) || StringUtils.isEmpty(username) || StringUtils.isEmpty(code)) {
            return null;
        }
        Code realcode = codeCache.findByKey(new CodeKey(subject, username));
        if (realcode == null) {
            return null;
        }
        if (!realcode.getCode().equals(code)) {
            return null;
        }
        if (realcode.getExpiredAt() < System.currentTimeMillis()) {
            return null;
        }
        return realcode;
    }

    private class SendSmsTask extends SendCodeTask {

        public SendSmsTask(String subject, String username, Code code) {
            super(subject, username, code);
        }

        @Override
        public void run() {
            boolean success = smsService.send(getUsername(), "vcode", Collections.singletonMap("code", getCode().getCode()));
            if (success) {
                try {
                    codeCache.save(new CodeKey(getSubject(),getUsername()),getCode());
                    codeRepositroy.save(getCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SendMailTask extends SendCodeTask {

        public SendMailTask(String subject, String username, Code code) {
            super(subject, username, code);
        }

        @Override
        public void run() {
            try {
                Map<String, Object> context = new HashMap<>();
                context.put("signinCode", getCode().getCode());
                String content = PlaceholderUtil.compile(SIGN_IN_CODE_MAIL, context);
                MailHelper.MailInfo mail = new MailHelper.MailInfo();
                mail.setToAddress(SendMailTask.this.getUsername());
                mail.setSubject("猪圈里最笨的猪APP");
                mail.setContent(content);
                boolean success = mailService.send(mail, true);
                if (success) {
                    try {
                        codeRepositroy.save(getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                L.error(ex);
            }
        }
    }
}
