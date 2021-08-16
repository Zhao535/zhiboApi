package cn.maidaotech.smartapi.api.cart.controller;

import cn.maidaotech.smartapi.api.cart.model.Cart;
import cn.maidaotech.smartapi.api.cart.service.CartService;
import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("usr/cart")
public class UsrCartController extends BaseController {

    @Autowired
    private CartService cartService;

    @RequestMapping("save")
    @UserAuthentication
    public ModelAndView save(String cart) throws Exception {
        return feedback(cartService.save(parseModel(cart, new Cart(), "cart")));
    }

    @RequestMapping("saveAll")
    @UserAuthentication
    public ModelAndView saveAll(String carts) throws Exception {
        List<Cart> cartList = JSON.parseArray(carts, Cart.class);
        cartService.saveAll(cartList);
        return feedback();
    }

    @RequestMapping("item")
    @UserAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(cartService.item(id));
    }

    @RequestMapping("items")
    @UserAuthentication
    public ModelAndView items() throws Exception {
        return feedback(cartService.items(UserContexts.requestUserId()));
    }


    @RequestMapping("update_number")
    @UserAuthentication
    public ModelAndView update_number(Integer id, Integer num) throws Exception {
        cartService.updateBuyNum(id, num);
        return feedback();
    }

    @RequestMapping("update_spec")
    @UserAuthentication
    public ModelAndView update_productSpec(Integer id, String productSno) throws Exception {
        cartService.updateSpecs(id, productSno);
        return feedback();
    }

    @RequestMapping("remove_one")
    @UserAuthentication
    public ModelAndView remove_one(Integer id) throws Exception {
        cartService.removeOne(id);
        return feedback();
    }

    @RequestMapping("/batch_remove")
    public ModelAndView batchRemove(String ids) throws Exception {
        cartService.removeList(JSON.parseArray(ids, Integer.class));
        return feedback();
    }

    @RequestMapping("/cart_list")
    public ModelAndView cartList(String ids) throws Exception {
        return feedback(cartService.findCartList(JSON.parseArray(ids, Integer.class)));
    }

}
