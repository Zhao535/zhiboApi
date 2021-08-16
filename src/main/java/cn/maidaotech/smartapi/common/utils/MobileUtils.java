package cn.maidaotech.smartapi.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileUtils {
    private static final String CHINA_MOBILE_PREFIX = "86";
    private static final String CHINA_MOBILE_FULL_PREFIX = CHINA_MOBILE_PREFIX + "-";
    private static final Pattern MOBILE_CHECKER = Pattern.compile("^1[3,4,5,6,7,8,9]\\d{9}$");

    /**
     * 格式化手机号(转成标准格式:国家码-号码)
     **/
    public static String formatMobile(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        int len = s.length();
        if (len == 0 || s.length() > 30) {
            return null;
        }
        int regionIndex = s.indexOf('-');
        if (regionIndex <= 0) {
            return isChinaMobile(s) ? CHINA_MOBILE_FULL_PREFIX + s : null;
        }
        String s1 = s.substring(0, regionIndex);
        String s2 = s.substring(regionIndex + 1);
        if (!StringUtils.isNumeric(s1) || !StringUtils.isNumeric(s2)) {
            return null;
        }
        if (s1.equals(CHINA_MOBILE_PREFIX)) {
            return isChinaMobile(s2) ? s : null;
        }
        return s;
    }

    private static boolean isChinaMobile(String input) {
        if (input == null) {
            return false;
        }
        if (input.length() != 11) {
            return false;
        }
        Matcher matcher = MOBILE_CHECKER.matcher(input);
        return matcher.matches();
    }

    /**
     * 显示中国手机号(如果是中国手机号，把国家码去掉，方便前端显示)
     **/
    public static String trimChinaMobile(String s) {
        if (s == null) {
            return s;
        }
        if (s.startsWith(CHINA_MOBILE_FULL_PREFIX)) {
            return s.substring(CHINA_MOBILE_FULL_PREFIX.length());
        }
        return s;
    }

    /**
     * 隐藏手机号
     **/
    public static String confuseMobile(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        int codeIndex = s.indexOf('-');
        if (codeIndex > 0) {
            buf.append(s.substring(0, codeIndex + 1));
            s = s.substring(codeIndex + 1);
        }
        if (s.length() == 11) {
            buf.append(s.substring(0, 3)).append("****").append(s.substring(7));
        } else {
            buf.append(StringUtils.hideString(s));
        }
        return buf.toString();
    }
}
