package cn.maidaotech.smartapi.common.addressPicker.service;

import cn.maidaotech.smartapi.common.L;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import cn.maidaotech.smartapi.common.utils.SimpleHttpClient;
import com.sunnysuperman.commons.util.FormatUtil;
import com.sunnysuperman.commons.util.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static cn.maidaotech.smartapi.common.addressPicker.entity.PickerError.ERR_GEOCODER;

@Service
public class AddressPickerServiceImpl implements AddressPickerService {


    @Override
    public Map geocoder(String lat, String lng, String key) {
        String url = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + lat + "," + lng + "&key=" + key;

        Map<String, Object> params = new HashMap<>();

        SimpleHttpClient client = new SimpleHttpClient();
        String responseString = "";
        try {
            responseString = client.get(url, params, CollectionUtils.arrayAsMap());
        } catch (Exception e) {
            throw new ServiceException(ERR_GEOCODER);
        }
        if (L.isInfoEnabled()) {
            L.info("mbyApi - " + url + ", result: " + responseString);
        }
        Map<?, ?> response = JSONUtil.parseJSONObject(responseString);
        Integer status = FormatUtil.parseInteger(response.get("status"));
        if (L.isInfoEnabled()) {
            L.info("response status = " + status);
        }
        return response;
    }
}
