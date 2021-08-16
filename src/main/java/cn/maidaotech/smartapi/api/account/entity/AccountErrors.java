package cn.maidaotech.smartapi.api.account.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface AccountErrors extends ErrorCode {

    public static final int ERR_ACCOUNT_TYPE_NOT_NULL = 2200;
    public static final int ERR_ACCOUNT_NAME_NOT_NULL = 2201;
    public static final int ERR_ACCOUNT_NO_NOT_NULL = 2202;
    public static final int ERR_ACCOUNT_BANK_NAME_NOT_NULL = 2203;
    public static final int ERR_ACCOUNT_QR_CODE_NOT_NULL = 2204;
}
