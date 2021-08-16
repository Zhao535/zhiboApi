package cn.maidaotech.smartapi.api.coupon.entity;

public enum CouponType {
    //1.通用券 2.分类券 3.商品券
    COMMON_COUPON(1),
    CATEGORY_COUPON(2),
    PRODUCT_COUPON(3);

    private byte value;

    private CouponType(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return value;
    }

    public static CouponType findCoupon(Byte value) {
        if (value == null) {
            return null;
        }
        for (CouponType couponType : CouponType.values()) {
            if (value.equals(couponType.value)) {
                return couponType;
            }
        }
        return null;
    }

}
