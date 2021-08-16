package cn.maidaotech.smartapi.api.brand.controller;

import cn.maidaotech.smartapi.api.brand.entity.BrandQo;
import cn.maidaotech.smartapi.api.brand.service.ProductBrandService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("usr/product")
public class UsrProductBrandController extends BaseController {
    @Autowired
    private ProductBrandService productBrandService;

    @RequestMapping("merchantBrands")
    public ModelAndView brands(Integer merchantId) throws Exception {
        return feedback(productBrandService.merchantBrands(merchantId));
    }

    @RequestMapping("allBrands")
    public ModelAndView allBrands() throws Exception {
        return feedback(productBrandService.allBrands());
    }


}
