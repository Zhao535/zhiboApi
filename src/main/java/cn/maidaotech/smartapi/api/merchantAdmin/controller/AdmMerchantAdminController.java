package cn.maidaotech.smartapi.api.merchantAdmin.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.merchantAdmin.service.MerchantAdminService;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/adm/merchantAdmin")
public class AdmMerchantAdminController extends BaseController {

    @Autowired
    private MerchantAdminService merchantAdminService;



    @RequestMapping(value = "save")
    @AdminAuthentication(AdminPermission.MERCHANT_EDIT)
    public ModelAndView save(String merchantAdmin) throws Exception {
        merchantAdminService.saveMerchantAdmin(parseModel(merchantAdmin, new MerchantAdmin()));
        return feedback();
    }

    @RequestMapping(value = "changeStatus")
    @AdminAuthentication(AdminPermission.MERCHANT_EDIT)
    public ModelAndView changeStatus(Integer id) throws Exception {
        merchantAdminService.changeStatus(id);
        return feedback();
    }

    @RequestMapping(value = "item")
    @AdminAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(merchantAdminService.getById(id));
    }

    @RequestMapping(value = "items")
    @AdminAuthentication
    public ModelAndView item( ) throws Exception {
        return feedback(merchantAdminService.items());
    }

    @RequestMapping(value = "reset_password")
    @AdminAuthentication(AdminPermission.MERCHANT_LIST)
    public ModelAndView resetPassword(String mobile,String newPassword) throws Exception {
        merchantAdminService.resetPassword(mobile,newPassword);
        return feedback();
    }

}
