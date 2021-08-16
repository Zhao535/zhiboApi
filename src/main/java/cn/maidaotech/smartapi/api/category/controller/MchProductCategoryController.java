package cn.maidaotech.smartapi.api.category.controller;

import cn.maidaotech.smartapi.api.category.service.ProductCategoryService;
import cn.maidaotech.smartapi.api.template.service.ProductTemplateService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mch/product")
public class MchProductCategoryController extends BaseController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "merchantCategories")
    @MerchantAdminAuthentication
    public ModelAndView merchantCategories() throws Exception {
        return feedback(productCategoryService.merchantCategories());
    }
}
