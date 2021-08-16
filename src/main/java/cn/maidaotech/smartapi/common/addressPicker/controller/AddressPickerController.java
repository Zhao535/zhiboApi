package cn.maidaotech.smartapi.common.addressPicker.controller;

import cn.maidaotech.smartapi.common.addressPicker.service.AddressPickerService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddressPickerController extends BaseController {
    @Autowired
    private AddressPickerService addressPickerService;

    @RequestMapping(value = "common/geocoder")
    public ModelAndView addressPicker(String lat, String lng, String key) throws Exception {
        return feedback(addressPickerService.geocoder(lat, lng, key));
    }


}
