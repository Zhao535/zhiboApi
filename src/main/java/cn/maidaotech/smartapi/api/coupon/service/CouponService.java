package cn.maidaotech.smartapi.api.coupon.service;

import cn.maidaotech.smartapi.api.coupon.qo.CouponQo;
import cn.maidaotech.smartapi.api.coupon.model.Coupon;
import cn.maidaotech.smartapi.api.coupon.model.UserCoupon;
import cn.maidaotech.smartapi.api.coupon.qo.CouponWO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CouponService {
    void save(Coupon coupon) throws Exception;

    void remove(Integer id);

    void status(Integer id);

    List<Coupon> productCoupons(Integer productId) throws Exception;

    List<UserCoupon> tradeCoupons(List<Integer> ids) throws Exception;

    Coupon coupon(Integer id);

    Page<Coupon> coupons(CouponQo qo, CouponWO wo);

    Map<Integer, Coupon> findByIds(List<Integer> ids);

    List<Coupon> findCouponByType() throws Exception;
}
