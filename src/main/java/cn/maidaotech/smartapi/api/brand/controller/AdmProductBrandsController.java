package cn.maidaotech.smartapi.api.brand.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.brand.entity.BrandQo;
import cn.maidaotech.smartapi.api.brand.model.ProductBrand;
import cn.maidaotech.smartapi.api.brand.service.ProductBrandService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("adm/product")
public class AdmProductBrandsController extends BaseController {
    @Autowired
    private ProductBrandService productBrandService;

    @RequestMapping("brands")
    @AdminAuthentication({AdminPermission.BRAND_EDIT,AdminPermission.BRAND_LIST})
    public ModelAndView items(String qo) throws Exception {
        return feedback(productBrandService.items(parseModel(qo, new BrandQo(), "qo")));
    }

    @RequestMapping("saveProductBrand")
    @AdminAuthentication({AdminPermission.BRAND_EDIT,AdminPermission.BRAND_LIST})
    public ModelAndView save(String brand) throws Exception {
        productBrandService.save(parseModel(brand, new ProductBrand(), "brand"));
        return feedback();
    }

    @RequestMapping("item")
    @AdminAuthentication({AdminPermission.BRAND_EDIT,AdminPermission.BRAND_LIST})
    public ModelAndView findItem(Integer id) throws Exception {
        return feedback(productBrandService.item(id));
    }

    @RequestMapping("changeStatus")
    @AdminAuthentication({AdminPermission.BRAND_EDIT,AdminPermission.BRAND_LIST})
    public ModelAndView changeStatus(Integer id) throws Exception {
        productBrandService.changeStatus(id);
        return feedback();
    }




}
