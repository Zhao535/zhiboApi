package cn.maidaotech.smartapi.common.sms;

import java.util.Map;

public interface SmsService {

    boolean send(String mobile, String templateKey, Map<String, Object> templateParams);

    boolean inWhitelist(String mobile);
}
