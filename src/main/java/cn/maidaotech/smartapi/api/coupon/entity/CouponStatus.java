package cn.maidaotech.smartapi.api.coupon.entity;

public enum  CouponStatus {

    IS_GET(1),
    NOT_GET(2),

    //userCoupon
    UNUSED(1), USED(2), EXPIRED(3);

    private byte value;

    private CouponStatus(int value) {
        this.value = (byte) value;
    }

    public byte value() {
        return value;
    }

    public CouponStatus findCoupon(Byte value) {
        if (value == null) {
            return null;
        }
        for (CouponStatus couponStatus : CouponStatus.values()) {
            if (value.equals(couponStatus.value)) {
                return couponStatus;
            }
        }
        return null;
    }

    public CouponStatus findUserCoupon(Byte value) {
        if (value == null) {
            return null;
        }
        for (CouponStatus couponStatus : CouponStatus.values()) {
            if (value.equals(couponStatus.value)) {
                return couponStatus;
            }
        }
        return null;
    }

}
