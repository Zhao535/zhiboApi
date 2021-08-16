package cn.maidaotech.smartapi.api.merchant.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface MerchantErrors extends ErrorCode {

    public static final int ERR_MERCHANT_NOT_EXIST = 1100;
    public static final int ERR_MERCHANT_NAME_EMPTY = 1101;
    public static final int ERR_MERCHANT_MOBILE_INVALID = 1102;
    public static final int ERR_MERCHANT_ADMIN_ACCOUNT_NOT_EXIST = 1103;
    public static final int ERR_MERCHANT_DURATION_NOT_NULL = 1104;
    public static final int ERR_MERCHANT_ADMIN_PASSWORD_WRONG = 1105;
    public static final int ERR_CATEGORIES_NOT_NULL =1106;
    public static final int ERR_CATEGORIES_NAME_NOT_NULL =1107;
    public static final int ERR_CATEGORIES_NOT_EXIST =1108;
    public static final int ERR_MERCHANT_ADMIN_NOT_NULL =1009;
    public static final int ERR_ADMIN_ROLE_NOT_EXIST =1010;
    public static final int ERR_ADMIN_STATUS_FORBID =1011;
}
