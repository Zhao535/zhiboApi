package cn.maidaotech.smartapi.api.coupon.qo;

public class CouponWO {

    private boolean withUserCoupon = false;

    public CouponWO() {
    }

    public boolean isWithUserCoupon() {
        return withUserCoupon;
    }

    public CouponWO setWithUserCoupon(boolean withUserCoupon) {
        this.withUserCoupon = withUserCoupon;
        return this;
    }

    public static final CouponWO noneInstance = new CouponWO();

    public static final CouponWO userInstance = new CouponWO().setWithUserCoupon(true);
}
