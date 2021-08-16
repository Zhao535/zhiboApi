package cn.maidaotech.smartapi.common.sms.model;

import cn.maidaotech.smartapi.common.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

public class CodeDeliveryValidator {
    public static enum Platform {
        web, ios, android
    }

    public static String getDeliveryCode(String mobile, String timestamp, String platform, String deviceId) {
        // {length-of-timestamp}{timestamp}{length-of-platform}{platform}{length-of-deviceId}{deviceId}123456789
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(platform)
                || StringUtils.isEmpty(deviceId)) {
            return null;
        }
        try {
            Platform.valueOf(platform);
        } catch (Exception e) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        buf.append(timestamp.length()).append(timestamp);
        buf.append(platform.length()).append(platform);
        buf.append(deviceId.length()).append(deviceId);
        buf.append(mobile);
        return DigestUtils.md5Hex(buf.toString());
    }
}
