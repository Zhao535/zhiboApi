package cn.maidaotech.smartapi.common.converter;

import cn.maidaotech.smartapi.common.addressPicker.model.Location;
import com.alibaba.fastjson.JSON;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocationConverter implements AttributeConverter<Location, String> {

    @Override
    public String convertToDatabaseColumn(Location location) {
        return JSON.toJSONString(location);
    }

    @Override
    public Location convertToEntityAttribute(String location) {
        try {
            return JSON.parseObject(location,Location.class);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
