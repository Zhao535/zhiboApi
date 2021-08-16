package cn.maidaotech.smartapi.common.sms;

import cn.maidaotech.smartapi.common.L;
import cn.maidaotech.smartapi.common.utils.Base64Utils;
import cn.maidaotech.smartapi.common.utils.SimpleHttpClient;
import com.sunnysuperman.commons.util.FormatUtil;
import com.sunnysuperman.commons.util.JSONUtil;
import com.sunnysuperman.commons.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class AliSmsHelper {

    private static String specialUrlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String sign(String accessSecret, String stringToSign) {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
            mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            return Base64Utils.encode(signData);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String makeSignature(AliSmsConfig config, Map<String, Object> paras) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
        // 1. 系统参数
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", UUID.randomUUID().toString());
        paras.put("AccessKeyId", config.getKey());
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", df.format(new Date()));
        paras.put("Format", "XML");

        // 4. 参数KEY排序
        TreeMap<String, Object> sortParas = new TreeMap<>();
        sortParas.putAll(paras);
        // 5. 构造待签名的字符串
        Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            String value = FormatUtil.parseString(paras.get(key));
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(value));
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("GET").append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(sortedQueryString));
        String sign = sign(config.getSecret() + "&", stringToSign.toString());
        // 6. 签名最后也要做特殊URL编码
        String signature = specialUrlEncode(sign);
        return "http://dysmsapi.aliyuncs.com/?Signature=" + signature + sortQueryStringTmp;
    }

    public static boolean send(AliSmsConfig config, String mobile, String sign, String templateCode,
                               Map<String, Object> templateParams, int maxTry) {
        if (mobile.startsWith("86-")) {
            mobile = mobile.substring(3);
        } else {
            if (mobile.indexOf('-') <= 0) {
                throw new RuntimeException("Bad mobile");
            }
            mobile = "00" + mobile.replace("-", StringUtil.EMPTY);
        }
        Map<String, Object> paras = new HashMap<>();
        paras.put("Action", "SendSms");
        paras.put("Version", "2017-05-25");
        paras.put("RegionId", "cn-hangzhou");
        paras.put("PhoneNumbers", mobile);
        paras.put("SignName", sign);
        paras.put("TemplateParam", JSONUtil.toJSONString(templateParams));
        paras.put("TemplateCode", templateCode);

        String url = makeSignature(config, paras);

        SimpleHttpClient client = new SimpleHttpClient();
        client.setConnectTimeout(15);
        client.setReadTimeout(20);
        int trial = 0;
        while (trial < maxTry) {
            try {
                String body = client.get(url, null, null);
                if (L.isInfoEnabled()) {
                    L.info("Send message response: " + body);
                }
                return true;
                //最终结果
            } catch (Exception e) {
                L.error(e);
            }
            trial++;
        }
        return false;
    }

}
