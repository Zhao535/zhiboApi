package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.trade.entity.TradeItem;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter(autoApply = true)
public class TradeItemArrayConverter implements AttributeConverter<List<TradeItem>, String> {
    @Override
    public String convertToDatabaseColumn(List<TradeItem> tradeItems) {
        return JSON.toJSONString(tradeItems);
    }

    @Override
    public List<TradeItem> convertToEntityAttribute(String s) {
        try {
            return JSON.parseArray(s, TradeItem.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
