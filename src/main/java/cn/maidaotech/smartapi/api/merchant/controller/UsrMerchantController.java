package cn.maidaotech.smartapi.api.merchant.controller;

import cn.maidaotech.smartapi.api.merchant.entity.MerchantQo;
import cn.maidaotech.smartapi.api.merchant.entity.MerchantWrapOption;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.product.entity.ProductQo;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("usr/merchant")
public class UsrMerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping("/item")
    public ModelAndView item(Integer id) throws Exception {
        return feedback(merchantService.item(id, MerchantWrapOption.getDefault()));
    }

    @RequestMapping("/items")
    public ModelAndView items(String qo) throws Exception {
        return feedback(merchantService.itemsForUsr(parseModel(qo, new MerchantQo(), "qo"), MerchantWrapOption.getDefault()));
    }

    @RequestMapping("/products")
    public ModelAndView products(String qo) throws Exception {
        return feedback(merchantService.products(parseModel(qo, new ProductQo(), "qo")));
    }


}
