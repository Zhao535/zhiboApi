package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.common.addressPicker.model.Location;
import com.alibaba.fastjson.JSON;

import javax.persistence.AttributeConverter;
import java.util.List;

public class ProductArrayConverter implements AttributeConverter<List<Product>, String> {

    @Override
    public String convertToDatabaseColumn(List<Product> products) {
        return JSON.toJSONString(products);
    }

    @Override
    public List<Product> convertToEntityAttribute(String s) {
        try {
            return JSON.parseArray(s,Product.class);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
