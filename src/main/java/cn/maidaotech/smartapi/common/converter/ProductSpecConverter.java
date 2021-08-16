package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import com.alibaba.fastjson.JSON;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProductSpecConverter implements AttributeConverter<ProductSpec, String> {

    @Override
    public String convertToDatabaseColumn(ProductSpec productSpec) {
        return JSON.toJSONString(productSpec);
    }

    @Override
    public ProductSpec convertToEntityAttribute(String s) {
        try {
            return JSON.parseObject(s, ProductSpec.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
