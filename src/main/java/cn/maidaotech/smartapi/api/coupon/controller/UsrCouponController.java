package cn.maidaotech.smartapi.api.coupon.controller;

import cn.maidaotech.smartapi.api.coupon.qo.CouponQo;
import cn.maidaotech.smartapi.api.coupon.qo.CouponWO;
import cn.maidaotech.smartapi.api.coupon.service.CouponService;
import cn.maidaotech.smartapi.api.coupon.service.UserCouponService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/usr/coupon")
@UserAuthentication
public class UsrCouponController extends BaseController {
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "/coupon")
    public ModelAndView coupon(Integer id) {
        return feedback(userCouponService.userCoupon(id));
    }

    @RequestMapping(value = "/coupons")
    public ModelAndView coupons(Integer productId) throws Exception {
        return feedback(userCouponService.productCoupons(productId));
    }

    @RequestMapping(value = "/user_coupons")
    public ModelAndView userCoupons(Byte status) {
        return feedback(userCouponService.userCoupons(status));
    }

    @RequestMapping(value = "/all_coupons")
    public ModelAndView allCoupons(String couponQo) {
        return feedback(couponService.coupons(parseModel(couponQo, new CouponQo()), CouponWO.userInstance));
    }

    @RequestMapping(value = "/trade_coupons")
    public ModelAndView tradeCoupons(String ids) throws Exception {
        return feedback(userCouponService.tradeCoupons(JSON.parseArray(ids, Integer.class)));
    }

    @RequestMapping("/save")
    public ModelAndView save(Integer id) {
        userCouponService.saveCoupon(id);
        return feedback();
    }

    @RequestMapping("/general_coupon")
    public ModelAndView generalCoupon() throws Exception {
        return feedback(couponService.findCouponByType());
    }

    @RequestMapping("/update_status")
    public ModelAndView updateStatus(Integer id, Byte status) throws Exception {
        userCouponService.updateStatus(id, status);
        return feedback();
    }
}
