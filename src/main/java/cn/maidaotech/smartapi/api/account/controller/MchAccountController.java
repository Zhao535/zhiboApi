package cn.maidaotech.smartapi.api.account.controller;

import cn.maidaotech.smartapi.api.account.model.Account;
import cn.maidaotech.smartapi.api.account.service.AccountService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/mch/account/")
public class MchAccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/items")
    @MerchantAdminAuthentication
    public ModelAndView items() throws Exception {
        return feedback(accountService.items());
    }

    @RequestMapping(value = "/save")
    @MerchantAdminAuthentication
    public ModelAndView save(String account) throws Exception {
        accountService.save(parseModel(account, new Account(), "account"));
        return feedback();
    }

    @RequestMapping(value = "/remove")
    @MerchantAdminAuthentication
    public ModelAndView remove(Integer id) throws Exception {
        accountService.remove(id);
        return feedback();
    }

}
