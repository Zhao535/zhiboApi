package cn.maidaotech.smartapi.common.converter;


import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter(autoApply = true)
public class ProductSpecArrayConverter implements AttributeConverter<List<ProductSpec>, String> {

    @Override
    public String convertToDatabaseColumn(List<ProductSpec> productSpecs) {
        return JSON.toJSONString(productSpecs);
    }

    @Override
    public List<ProductSpec> convertToEntityAttribute(String s) {
        try {
            return JSONArray.parseArray(s, ProductSpec.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
