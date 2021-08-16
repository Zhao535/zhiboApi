package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.coupon.entity.Payload;
import cn.maidaotech.smartapi.api.coupon.entity.Rule;
import com.alibaba.fastjson.JSON;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RuleConverter implements AttributeConverter<Rule,String> {

    @Override
    public String convertToDatabaseColumn(Rule rule) {
        return JSON.toJSONString(rule);
    }

    @Override
    public Rule convertToEntityAttribute(String s) {
        try {
            return JSON.parseObject(s, Rule.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
