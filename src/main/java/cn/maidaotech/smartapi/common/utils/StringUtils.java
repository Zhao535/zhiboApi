package cn.maidaotech.smartapi.common.utils;

import com.sunnysuperman.commons.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends StringUtil {
    private static final String NUMERIC = "0123456789";
    private static final String ALPHA_NUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_ALPHA_NUMERIC = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final String EXPLICIT_ALPHA_NUMERIC = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String CAPITAL_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Pattern EMAIL_CHECKER = Pattern
            .compile("^([a-z0-9A-Z]+[-|\\._]?)+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    private static final AffineTransform atf = new AffineTransform();
    private static final FontRenderContext frc = new FontRenderContext(atf, true, true);

    public static int getWidth(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        Font font = new Font(null, 0, 12);
        return (int) font.getStringBounds(str, frc).getWidth();
    }

    public static boolean isNull(Object o) {
        return o == null || o.toString().trim().equals("");
    }

    public static boolean isEmail(String input) {
        if (input == null) {
            return false;
        }
        int len = input.length();
        if (len == 0 || len > 64) {
            return false;
        }
        Matcher matcher = EMAIL_CHECKER.matcher(input);
        return matcher.matches();
    }

    public static boolean isUpperCaseAlpha(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static boolean isLowerCaseAlpha(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isAlpha(char c) {
        // 大写字母
        if (c >= 65 && c <= 90) {
            return true;
        }
        // 小写字母
        if (c >= 97 && c <= 122) {
            return true;
        }
        return false;
    }

    public static boolean isAlphanumeric(String s) {
        return isTargetString(ALPHA_NUMERIC, s);
    }

    public static String randomAlphanumeric(int length) {
        return randomString(ALPHA_NUMERIC, length);
    }

    public static String randomLowerCaseAlphanumeric(int length) {
        return randomString(LOWERCASE_ALPHA_NUMERIC, length);
    }

    public static String randomCapitalAlpha(int length) {
        return randomString(CAPITAL_ALPHA, length);
    }

    public static boolean isLowerCaseAlphanumeric(String s) {
        return isTargetString(LOWERCASE_ALPHA_NUMERIC, s);
    }

    public static String randomExplicitAlphanumeric(int length) {
        return randomString(EXPLICIT_ALPHA_NUMERIC, length);
    }

    public static boolean isUpperCaseAlpha(String s) {
        if (isEmpty(s)) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 65 || c > 90) {
                return false;
            }
        }
        return true;
    }

    public static String randomNumeric(int length) {
        return randomString(NUMERIC, length);
    }

    public static String encryptPassword(String password, String salt) {
        if (password == null) {
            return null;
        }
        String encryptPassword = DigestUtils.sha256Hex(password + salt);
        encryptPassword = DigestUtils.sha256Hex(encryptPassword);
        return encryptPassword;
    }

    private static final String ELLIPSIS = "...";

    public static String trimByLength(String s, int len) {
        if (s == null) {
            return s;
        }
        int originalLen = s.length();
        if (originalLen <= len || originalLen <= ELLIPSIS.length()) {
            return s;
        }
        return s.substring(0, len - ELLIPSIS.length()) + ELLIPSIS;
    }

    public static String limitByLength(String s, int maxLength) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        int len = s.length();
        if (len == 0) {
            return null;
        }
        if (len > maxLength) {
            s = s.substring(0, maxLength);
        }
        return s;
    }

    public static String hideString(String s) {
        if (s == null) {
            return null;
        }
        int len = s.length();
        if (len < 1) {
            return null;
        }
        final String placeholder = "*";

        int hideLen = (int) Math.ceil(len / 3d);
        if (hideLen == 0) {
            return placeholder;
        }
        if (hideLen == len) {
            return placeholder;
        }
        int startLen = (int) Math.ceil((len - hideLen) / 2d); // >0
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < startLen; i++) {
            buf.append(s.charAt(i));
        }
        for (int i = 0; i < hideLen; i++) {
            buf.append(placeholder);
        }
        int tailOffset = startLen + hideLen;
        for (int i = tailOffset; i < len; i++) {
            buf.append(s.charAt(i));
        }
        return buf.toString();
    }

    public static String filterFourBytesChars(String s) {
        if (isEmpty(s)) {
            return s;
        }
        return s.replaceAll("[^\\u0000-\\uFFFF]", EMPTY);
    }

    public static String pad(Number number, int length, boolean limitToMax) {
        String s = number.toString();
        int padLen = length - s.length();
        if (padLen > 0) {
            StringBuilder buf = new StringBuilder(length);
            for (int i = 0; i < padLen; i++) {
                buf.append('0');
            }
            buf.append(s);
            return buf.toString();
        }
        if (padLen == 0 || !limitToMax) {
            return s;
        }
        StringBuilder buf = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            buf.append('9');
        }
        return buf.toString();
    }

    public static String pad(Number number, int length) {
        return pad(number, length, false);
    }

    public static String pad(String s, int length) {
        int padLen = length - s.length();
        if (padLen <= 0) {
            return s;
        }
        StringBuilder buf = new StringBuilder(length);
        for (int i = 0; i < padLen; i++) {
            buf.append('0');
        }
        buf.append(s);
        return buf.toString();
    }

    public static String formatNumber(String num) {
        while (num.startsWith("0")) {
            num = num.substring(1);
        }
        return num;
    }

    public static Set<String> splitAsSet(String s, String separatorChars) {
        List<String> tokens = StringUtils.split(s, separatorChars);
        if (tokens == null) {
            return null;
        }
        return new HashSet<>(tokens);
    }

    public static int[] splitAsIntArray(String s, String separatorChars) {
        List<String> tokens = StringUtils.split(s, separatorChars);
        if (tokens == null || tokens.isEmpty()) {
            return null;
        }
        int[] array = new int[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            int value = Integer.parseInt(tokens.get(i));
            array[i] = value;
        }
        return array;
    }

    public static long[] splitAsLongArray(String s, String separatorChars) {
        List<String> tokens = StringUtils.split(s, separatorChars);
        if (tokens == null || tokens.isEmpty()) {
            return null;
        }
        long[] array = new long[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            long value = Long.parseLong(tokens.get(i));
            array[i] = value;
        }
        return array;
    }

    public static List<Long> splitAsLongList(String s, String separatorChars) {
        List<String> tokens = StringUtils.split(s, separatorChars);
        if (tokens == null || tokens.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> list = new ArrayList<>(tokens.size());
        for (int i = 0; i < tokens.size(); i++) {
            list.add(Long.valueOf(tokens.get(i)));
        }
        return list;
    }

    public static List<Integer> splitAsIntList(String s, String separatorChars) {
        List<String> tokens = StringUtils.split(s, separatorChars);
        if (tokens == null || tokens.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>(tokens.size());
        for (int i = 0; i < tokens.size(); i++) {
            list.add(Integer.valueOf(tokens.get(i)));
        }
        return list;
    }

    public static String joinIntList(List<Integer> list, String separatorChars) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            str.append(list.get(i));
            if (i < list.size() - 1) {
                str.append(separatorChars);
            }
        }
        return str.toString();
    }

    public static String trimUtf8mb4(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.replaceAll("[^\\u0000-\\uD7FF\\uE000-\\uFFFF]", "");
    }

    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(true, str);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(false, str);
    }

    private static String changeFirstCharacterCase(boolean capitalize, String string) {
        int strLen = string.length();
        if (strLen == 0) {
            return string;
        }

        char ch = string.charAt(0);
        char modifiedCh;
        if (capitalize) {
            modifiedCh = Character.toUpperCase(ch);
        } else {
            modifiedCh = Character.toLowerCase(ch);
        }
        if (modifiedCh == ch) {
            // no change, return unchanged string
            return string;
        }

        char[] chars = string.toCharArray();
        chars[0] = modifiedCh;
        return new String(chars);
    }

    //订单号
    public static String getOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result.append(random.nextInt(10));
        }
        return newDate + result;
    }


}
