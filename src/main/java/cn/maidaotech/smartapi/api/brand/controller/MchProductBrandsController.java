package cn.maidaotech.smartapi.api.brand.controller;

import cn.maidaotech.smartapi.api.brand.entity.BrandQo;
import cn.maidaotech.smartapi.api.brand.service.ProductBrandService;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mch/product")
public class MchProductBrandsController extends BaseController {

    @Autowired
    private ProductBrandService productBrandService;

    @RequestMapping("brands")
    public ModelAndView items(String qo) throws Exception {
        return feedback(productBrandService.items(parseModel(qo, new BrandQo(), "qo")));
    }

    @RequestMapping("merchantBrands")
    @MerchantAdminAuthentication
    public ModelAndView merchantBrands() throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        return feedback(productBrandService.merchantBrands(merchantId));
    }


}
