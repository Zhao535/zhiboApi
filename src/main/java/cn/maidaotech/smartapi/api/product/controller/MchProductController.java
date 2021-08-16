package cn.maidaotech.smartapi.api.product.controller;

import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminPermission;
import cn.maidaotech.smartapi.api.product.entity.ProductQo;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mch/product")
public class MchProductController extends BaseController {

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "saveProduct")
    @MerchantAdminAuthentication({MerchantAdminPermission.PRODUCT_EDIT, MerchantAdminPermission.PRODUCT_LIST})
    public ModelAndView save(String product) throws Exception {
        productService.save(parseModel(product, new Product(), "product"));
        return feedback();
    }


    @RequestMapping(value = "products")
    @MerchantAdminAuthentication({MerchantAdminPermission.PRODUCT_LIST})
    public ModelAndView items(String qo) throws Exception {
        return feedback(productService.items(parseModel(qo, new ProductQo(), "qo")));
    }


    @RequestMapping(value = "product")
    @MerchantAdminAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(productService.product(id));
    }

    @RequestMapping(value = "changeStatus")
    @MerchantAdminAuthentication({MerchantAdminPermission.PRODUCT_EDIT, MerchantAdminPermission.PRODUCT_LIST})
    public ModelAndView changeStatus(Integer id) throws Exception {
        productService.changeStatus(id);
        return feedback();
    }

}
