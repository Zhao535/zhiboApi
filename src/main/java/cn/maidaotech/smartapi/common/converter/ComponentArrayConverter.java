package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.ui.entity.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import java.util.List;

public class ComponentArrayConverter implements AttributeConverter<List<Component>, String> {

    @Override
    public String convertToDatabaseColumn(List<Component> components) {
        return JSON.toJSONString(components);
    }

    @Override
    public List<Component> convertToEntityAttribute(String s) {
        try {
            return JSONArray.parseArray(s, Component.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
