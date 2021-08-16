package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.coupon.entity.Payload;
import cn.maidaotech.smartapi.api.product.model.Product;
import com.alibaba.fastjson.JSON;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter(autoApply = true)
public class PayLoadConverter implements AttributeConverter<Payload, String> {
    @Override
    public String convertToDatabaseColumn(Payload payload) {
        return JSON.toJSONString(payload);
    }

    @Override
    public Payload convertToEntityAttribute(String s) {
        try {
            return JSON.parseObject(s, Payload.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
