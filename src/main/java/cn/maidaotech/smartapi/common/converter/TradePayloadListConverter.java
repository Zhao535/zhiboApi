package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.cart.entity.CartPayload;
import cn.maidaotech.smartapi.api.trade.entity.TradePayload;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import java.util.List;

public class TradePayloadListConverter implements AttributeConverter<List<TradePayload>, String> {
    @Override
    public String convertToDatabaseColumn(List<TradePayload> tradePayload) {
        return JSON.toJSONString(tradePayload);
    }

    @Override
    public List<TradePayload> convertToEntityAttribute(String s) {
        try {
            return JSONArray.parseArray(s, TradePayload.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
