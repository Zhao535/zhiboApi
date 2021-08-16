package cn.maidaotech.smartapi.common.sms;

import com.sunnysuperman.commons.bean.Bean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "sms.ali", ignoreUnknownFields = false)
//读取配置文件里的sms.ali开头的配置
@Validated
//校验数据，如果数据异常会同意抛出
@Component
//注入服务，与controller，service，repository作用相同
public class AliSmsConfig {
    private String key;
    private String secret;
    private String templates;
    private Map<String, AliSmsTemplate> templateMap;
    private List<String> whitelist;

    @PostConstruct
    //构造函数执行前执行
    public void init() {
        List<AliSmsTemplate> items = Bean.fromJson(templates, AliSmsTemplate.class);
        templateMap = new HashMap<>();
        for (AliSmsTemplate item : items) {
            templateMap.put(item.getKey(), item);
        }
        templates = null;
    }

    public AliSmsTemplate findTemplate(String key) {
        return templateMap.get(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTemplates() {
        return templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates;
    }

    public Map<String, AliSmsTemplate> getTemplateMap() {
        return templateMap;
    }

    public void setTemplateMap(Map<String, AliSmsTemplate> templateMap) {
        this.templateMap = templateMap;
    }

    public List<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

}
