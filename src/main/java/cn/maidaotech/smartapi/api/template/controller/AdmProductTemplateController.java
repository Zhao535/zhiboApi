package cn.maidaotech.smartapi.api.template.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.template.entity.ProductTemplateQo;
import cn.maidaotech.smartapi.api.template.model.ProductTemplate;
import cn.maidaotech.smartapi.api.template.service.ProductTemplateService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "adm/product")
public class AdmProductTemplateController extends BaseController {

    @Autowired
    private ProductTemplateService templateService;

    @RequestMapping("saveProductTemplate")
    @AdminAuthentication({AdminPermission.TEMPLATE_EDIT,AdminPermission.TEMPLATE_LIST})
    public ModelAndView save(String template) throws Exception {
        templateService.save(parseModel(template, new ProductTemplate(), "template"));
        return feedback();
    }

    @RequestMapping("findAllTemplate")
    @AdminAuthentication({AdminPermission.TEMPLATE_EDIT,AdminPermission.TEMPLATE_LIST})
    public ModelAndView findAllTemplate(String qo) throws Exception {

        return feedback(templateService.productTemplates(parseModel(qo, new ProductTemplateQo(), "qo")));
    }

    @RequestMapping("template")
    @AdminAuthentication({AdminPermission.TEMPLATE_EDIT,AdminPermission.TEMPLATE_LIST})
    public ModelAndView item(Integer id) throws Exception {
        return feedback(templateService.item(id));
    }

    @RequestMapping("changeTemplateStatus")
    @AdminAuthentication({AdminPermission.TEMPLATE_EDIT,AdminPermission.TEMPLATE_LIST})
    public ModelAndView changeTemplateStatus(Integer id) throws Exception {
        templateService.status(id);
        return feedback();
    }

}
