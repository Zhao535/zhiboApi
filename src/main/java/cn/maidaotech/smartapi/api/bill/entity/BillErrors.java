package cn.maidaotech.smartapi.api.bill.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface BillErrors extends ErrorCode {
    public static final int ERR_BILL_HAVE_BEEN_PASSED = 2100;
    public static final int ERR_BILL_TRADE_ID_NOT_NULL = 2101;
    public static final int ERR_BILL_MERCHANT_ID_NOU_NULL = 2102;
    public static final int ERR_BILL_AMOUNT_NOT_NULL = 2103;
    public static final int ERR_BILL_ORDER_NUMBER_NOT_NULL = 2104;
    public static final int ERR_BILL_USER_ID_NOT_NULL = 2105;
    public static final int ERR_BILL_IMGS_NOT_NULL = 2106;
}
