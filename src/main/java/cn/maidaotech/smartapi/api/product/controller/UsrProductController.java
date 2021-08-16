package cn.maidaotech.smartapi.api.product.controller;

import cn.maidaotech.smartapi.api.product.entity.ProductQo;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("usr/product")
public class UsrProductController extends BaseController {
    @Autowired
    private ProductService productService;

    @RequestMapping("item")
    public ModelAndView item(Integer id) throws Exception {
        return feedback(productService.product(id));
    }

    @RequestMapping("items")
    public ModelAndView items(String qo) throws Exception {
         return feedback(productService.itemsForUsr(parseModel(qo, new ProductQo(), "qo")));
    }

}
