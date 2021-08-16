package cn.maidaotech.smartapi.api.secKill.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface SecKillErrors extends ErrorCode {
    public static final int ERR_SEC_KILL_ALREADY_START_OR_END = 2300;
    public static final int ERR_SEC_KILL_PRODUCT_ID_NOT_NULL = 2301;
    public static final int ERR_SEC_KILL_PRODUCT_SNO_NOT_NULL = 2302;
    public static final int ERR_SEC_KILL_CREATED_AT_ERROR = 2303;
    public static final int ERR_SEC_KILL_END_AT_ERROR = 2304;
    public static final int ERR_SEC_KILL_SEC_KILL_PRICE_NOT_NULL = 2305;
    public static final int ERR_SEC_KILL_PRODUCT_SNO_ERROR = 2306;
    public static final int ERR_SEC_KILL_PRODUCT_ERROR = 2307;
    public static final int ERR_SEC_KILL_PRODUCT_SOLD_OUT = 2308;
    public static final int ERR_SEC_KILL_PRODUCT_NUM_NOT_NULL = 2309;
    public static final int ERR_SEC_KILL_PRODUCT_STOCK_OUT = 2310;
}
