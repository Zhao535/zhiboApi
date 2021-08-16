package cn.maidaotech.smartapi.api.cart.repository;

import cn.maidaotech.smartapi.api.cart.model.Cart;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.List;

public interface CartRepository extends BaseRepository<Cart, Integer> {

    List<Cart> findByUserId(Integer userId);

    List<Cart> findAllByIdIn(List<Integer> ids);

    Cart findByUserIdAndProductIdAndProductSno(Integer userId, Integer productId, String productSno);

    List<Cart> findAllByUserId(Integer userId);
}
