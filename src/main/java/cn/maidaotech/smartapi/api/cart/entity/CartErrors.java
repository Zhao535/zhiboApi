package cn.maidaotech.smartapi.api.cart.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface CartErrors extends ErrorCode {

    public static final int ERR_CART_NOT_NULL = 2000;
    public static final int ERR_CART_BUY_NUM_NOT_NULL = 2001;
    public static final int ERR_CART_MERCHANT_ID_NOT_NULL = 2002;
    public static final int ERR_CART_PRODUCT_SPEC_NOT_NULL = 2003;
    public static final int ERR_CART_PRODUCT_ID_NOT_NULL = 2004;
    public static final int ERR_CART_SECKILL_ID_ERROR = 2004;
}
