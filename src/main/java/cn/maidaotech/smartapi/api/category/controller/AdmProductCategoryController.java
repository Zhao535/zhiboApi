package cn.maidaotech.smartapi.api.category.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.category.model.ProductCategory;
import cn.maidaotech.smartapi.api.brand.service.ProductBrandService;
import cn.maidaotech.smartapi.api.category.service.ProductCategoryService;
import cn.maidaotech.smartapi.api.template.service.ProductTemplateService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("adm/product")
@Controller
public class AdmProductCategoryController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductBrandService productBrandService;

    @Autowired
    private ProductTemplateService productTemplateService;


    @RequestMapping(value = "saveProductCategory")
    @AdminAuthentication({AdminPermission.CATEGORY_EDIT,AdminPermission.CATEGORY_LIST})
    public ModelAndView saveMerchantCategories(String category) throws Exception {
        productCategoryService.saveProductCategory(parseModel(category, new ProductCategory(), "category"));
        return feedback();
    }

    @RequestMapping(value = "findAllCategories")
    public ModelAndView findAllCategories() throws Exception {
        return feedback(productCategoryService.findAllCategories());
    }

    @RequestMapping(value = "changeCategoriesStatus")
    @AdminAuthentication(AdminPermission.CATEGORY_EDIT)
    public ModelAndView changeCategoriesStatus(Integer categoryId, Integer status) throws Exception {
        productCategoryService.changeCategoryStatus(categoryId, status);
        return feedback();
    }






}
