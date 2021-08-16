package cn.maidaotech.smartapi.api.bill.controller;

import cn.maidaotech.smartapi.api.bill.entity.BillQo;
import cn.maidaotech.smartapi.api.bill.service.BillService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/mch/bill")
public class MchBillController extends BaseController {

    @Autowired
    private BillService billService;

    @RequestMapping(value = "item")
    @MerchantAdminAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(billService.item(id));
    }

    @RequestMapping(value = "items")
    @MerchantAdminAuthentication
    public ModelAndView items(String billQo) throws Exception {
        return feedback(billService.items(parseModel(billQo, new BillQo(), "qo")));
    }

    @RequestMapping(value = "check")
    @MerchantAdminAuthentication
    public ModelAndView check(Integer id, Byte status) throws Exception {
        billService.check(id, status);
        return feedback();
    }

}
