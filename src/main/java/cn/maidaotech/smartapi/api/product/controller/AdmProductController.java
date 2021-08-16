package cn.maidaotech.smartapi.api.product.controller;

import cn.maidaotech.smartapi.api.product.entity.ProductQo;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("adm/product")
public class AdmProductController extends BaseController {

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "products")
    @AdminAuthentication
    public ModelAndView items(String qo) throws Exception {
        return feedback(productService.itemsForAdmin(parseModel(qo, new ProductQo(), "qo")));
    }


}
