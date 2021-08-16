package cn.maidaotech.smartapi.common.mail;

import cn.maidaotech.smartapi.common.L;
import cn.maidaotech.smartapi.common.mail.MailHelper.MailConnectionInfo;
import cn.maidaotech.smartapi.common.mail.MailHelper.MailInfo;
import cn.maidaotech.smartapi.common.task.TaskService;
import com.sunnysuperman.commons.config.PropertiesConfig;
import com.sunnysuperman.commons.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MailServiceImp implements MailService {
    @Value("${mailservice}")
    private String mailConfig;
    @Autowired
    private TaskService taskService;

    private MailConnectionInfo connectionInfo = new MailConnectionInfo();
    private String from = null;

    @PostConstruct
    public void init() {
        L.warn("Mail config: " + mailConfig);
        PropertiesConfig config = new PropertiesConfig(JSONUtil.parseJSONObject(mailConfig));
        connectionInfo.setHost(config.getString("host"));
        connectionInfo.setPort(config.getInt("port", 25));
        connectionInfo.setUsername(config.getString("username"));
        connectionInfo.setPassword(config.getString("password"));
        connectionInfo.setSsl(config.getBoolean("ssl", false));
        from = config.getString("from", connectionInfo.getUsername());
    }


    @Override
    public boolean send(MailInfo mail, boolean async) {
        if (async) {
            taskService.addTask(new SendTask(mail));
            return true;
        } else {
            return doSend(mail);
        }
    }

    private boolean doSend(MailInfo mail) {
        mail.setFromAddress(from);
        try {
            MailHelper.sendMail(connectionInfo, mail);
            if (L.isInfoEnabled()) {
                L.info("Mail has been delivered to: " + mail.getToAddress());
            }
            return true;
        } catch (Exception e) {
            L.error(e);
            return false;
        }
    }

    private class SendTask implements Runnable {
        MailInfo mail;

        public SendTask(MailInfo mail) {
            super();
            this.mail = mail;
        }

        @Override
        public void run() {
            doSend(mail);
        }

    }
}