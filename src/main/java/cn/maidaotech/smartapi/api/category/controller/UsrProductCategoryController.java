package cn.maidaotech.smartapi.api.category.controller;

import cn.maidaotech.smartapi.api.category.service.ProductCategoryService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("usr/category")
public class UsrProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/choiceCategory")
    public ModelAndView choiceCategory() throws Exception {
        return feedback(productCategoryService.findAllCategoriesForUsr());
    }
}
