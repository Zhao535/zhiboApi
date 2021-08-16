package cn.maidaotech.smartapi.api.address.controller;


import cn.maidaotech.smartapi.api.address.model.Address;
import cn.maidaotech.smartapi.api.address.qo.AddressQo;
import cn.maidaotech.smartapi.api.address.service.AddressService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usr/address")
public class UsrCodeController extends BaseController {
    @Autowired
    private AddressService addressService;

    @RequestMapping("save")
    @UserAuthentication
    public ModelAndView save(String address) throws Exception {
        addressService.save(parseModel(address, new Address(), "address"));
        return feedback();
    }

    @RequestMapping("items")
    @UserAuthentication
    public ModelAndView items() throws Exception {
        return feedback(addressService.items());
    }

    @RequestMapping("item")
    @UserAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(addressService.item(id));
    }

    @RequestMapping("remove")
    @UserAuthentication
    public ModelAndView remove(Integer id) throws Exception {
        addressService.remove(id);
        return feedback();
    }
}
