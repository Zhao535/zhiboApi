package cn.maidaotech.smartapi.common.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

/**
 * 创建人:CP
 * 创建时间:2020/1/19 4:28 下午
 * 项目名称:hanlin-api
 * 类名:IntegerArrayConverter
 **/
@Converter(autoApply = true)
public class IntegerArrayConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        return JSON.toJSONString(list);
    }

    @Override
    public List<Integer> convertToEntityAttribute(String data) {
        try {
            return JSONArray.parseArray(data, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
