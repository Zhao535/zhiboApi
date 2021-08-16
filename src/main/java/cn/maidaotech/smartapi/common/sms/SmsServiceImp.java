package cn.maidaotech.smartapi.common.sms;

import cn.maidaotech.smartapi.common.L;
import cn.maidaotech.smartapi.common.resources.LocaleBundles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsServiceImp implements SmsService {
    @Autowired
    private AliSmsConfig aliConfig;

    public boolean send(String mobile, String templateKey, Map<String, Object> templateParams) {
        String sign = LocaleBundles.get("zh_CN", "sms.sign");

        try {
            AliSmsTemplate template = aliConfig.findTemplate(templateKey);
            if (template == null) {
                L.warn("No template found for: " + templateKey);
                return false;
            }
            String aliTemplateKey;
            if (mobile.startsWith("86-")) {
                aliTemplateKey = template.getCn();
            } else {
                aliTemplateKey = template.getAborad();
            }
            return AliSmsHelper.send(aliConfig, mobile, sign, aliTemplateKey, templateParams, 2);
        } catch (Exception e) {
            L.error(e);
        }
        return false;
    }

    @Override
    public boolean inWhitelist(String mobile) {
        for (String str : aliConfig.getWhitelist()) {
            if (str.equals(mobile)) {
                return true;
            }
        }
        return false;
    }

}