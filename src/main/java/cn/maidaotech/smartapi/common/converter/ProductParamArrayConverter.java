package cn.maidaotech.smartapi.common.converter;


import cn.maidaotech.smartapi.api.product.entity.ProductParam;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter(autoApply = true)
public class ProductParamArrayConverter implements AttributeConverter<List<ProductParam>, String> {

    @Override
    public String convertToDatabaseColumn(List<ProductParam> productParams) {
        return JSON.toJSONString(productParams);
    }

    @Override
    public List<ProductParam> convertToEntityAttribute(String s) {
        try {
            return JSONArray.parseArray(s, ProductParam.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
