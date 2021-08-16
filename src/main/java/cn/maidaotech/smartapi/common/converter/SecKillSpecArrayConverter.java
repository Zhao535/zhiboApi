package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.secKill.entity.SecKillSpec;
import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import cn.maidaotech.smartapi.api.ui.entity.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import java.util.List;

public class SecKillSpecArrayConverter implements AttributeConverter<List<SecKillSpec>, String> {

    @Override
    public String convertToDatabaseColumn(List<SecKillSpec> list) {
        return JSON.toJSONString(list);
    }

    @Override
    public List<SecKillSpec> convertToEntityAttribute(String s) {
        try {
            return JSONArray.parseArray(s, SecKillSpec.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
