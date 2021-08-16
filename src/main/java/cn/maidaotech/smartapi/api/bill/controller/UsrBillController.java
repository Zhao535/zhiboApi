package cn.maidaotech.smartapi.api.bill.controller;

import cn.maidaotech.smartapi.api.bill.entity.BillQo;
import cn.maidaotech.smartapi.api.bill.model.Bill;
import cn.maidaotech.smartapi.api.bill.service.BillService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/usr/bill")
public class UsrBillController extends BaseController {

    @Autowired
    private BillService billService;

    @RequestMapping(value = "save")
    @UserAuthentication
    public ModelAndView save(String bill) throws Exception {
        billService.save(parseModel(bill,new Bill(),"bill"));
        return feedback();
    }


    @RequestMapping(value = "items")
    @UserAuthentication
    public ModelAndView items(String billQo) throws Exception {
        return feedback(billService.itemsForUsr(parseModel(billQo, new BillQo(), "qo")));
    }

    @RequestMapping(value = "item")
    @UserAuthentication
    public ModelAndView item(Integer tradeId) throws Exception {
        return feedback(billService.findByTradeId(tradeId));
    }

}
