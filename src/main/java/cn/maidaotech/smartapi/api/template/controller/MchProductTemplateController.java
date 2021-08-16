package cn.maidaotech.smartapi.api.template.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.template.entity.ProductTemplateQo;
import cn.maidaotech.smartapi.api.template.service.ProductTemplateService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("mch/product")
public class MchProductTemplateController extends BaseController {

    @Autowired
    private ProductTemplateService templateService;

    @RequestMapping("template")
    @MerchantAdminAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(templateService.item(id));
    }

    @RequestMapping("merchantTemplate")
    @MerchantAdminAuthentication
    public ModelAndView merchantTemplate(String qo) throws Exception {
        return feedback(templateService.merchantTemplate(parseModel(qo, new ProductTemplateQo(), "qo")));
    }
}