package cn.maidaotech.smartapi.api.coupon.controller;

import cn.maidaotech.smartapi.api.coupon.qo.CouponQo;
import cn.maidaotech.smartapi.api.coupon.model.Coupon;
import cn.maidaotech.smartapi.api.coupon.qo.CouponWO;
import cn.maidaotech.smartapi.api.coupon.service.CouponService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/adm/coupon")
@AdminAuthentication
public class AdmCouponController extends BaseController {
    @Autowired
    private CouponService couponService;

    @RequestMapping("save_coupon")
    public ModelAndView saveCoupon(String coupon) throws Exception {
        couponService.save(parseModel(coupon, new Coupon(), "coupon"));
        return feedback();
    }

    @RequestMapping("coupons")
    public ModelAndView coupons(String qo) throws Exception {
        return feedback(couponService.coupons(parseModel(qo, new CouponQo(), "qo"), CouponWO.noneInstance));
    }

}
