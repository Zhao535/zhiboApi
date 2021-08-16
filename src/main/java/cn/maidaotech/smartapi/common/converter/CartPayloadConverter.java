package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.cart.entity.CartPayload;
import cn.maidaotech.smartapi.api.ui.entity.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import java.util.List;

public class CartPayloadConverter implements AttributeConverter<CartPayload, String> {
    @Override
    public String convertToDatabaseColumn(CartPayload cartPayload) {
        return JSON.toJSONString(cartPayload);
    }

    @Override
    public CartPayload convertToEntityAttribute(String s) {
        try {
            return JSONArray.parseObject(s, CartPayload.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
