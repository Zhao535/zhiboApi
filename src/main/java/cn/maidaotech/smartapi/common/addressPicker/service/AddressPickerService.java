package cn.maidaotech.smartapi.common.addressPicker.service;

import java.util.Map;

public interface AddressPickerService {

    Map geocoder(String lat, String lng, String key);

}
