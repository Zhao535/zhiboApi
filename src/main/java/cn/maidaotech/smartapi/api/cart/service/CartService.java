package cn.maidaotech.smartapi.api.cart.service;

import cn.maidaotech.smartapi.api.cart.entity.CartQo;
import cn.maidaotech.smartapi.api.cart.model.Cart;
import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {

    void removeList(List<Integer> ids) throws Exception;

    void removeOne(Integer id) throws Exception;

    Cart save(Cart cart) throws Exception;

    void saveAll(List<Cart> cartList) throws Exception;

    Cart item(Integer id) throws Exception;

    List<Cart> items(Integer userId) throws Exception;

    List<Cart> findCartList(List<Integer> ids) throws Exception;

    void updateBuyNum(Integer id, Integer number) throws Exception;

    void updateSpecs(Integer id, String productSno) throws Exception;


}
