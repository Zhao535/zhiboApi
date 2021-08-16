package cn.maidaotech.smartapi.api.user.model;


import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface UserError extends ErrorCode {
    public static final int ERR_USER_NAME_EMPTY = 1200;
    public static final int ERR_USER_PASSWORD_EMPTY = 1201;
    public static final int ERR_USER_MOBILE_INVALID = 1202;
    public static final int ERR_USER_MOBILE_EXIST = 1203;
    public static final int ERR_USER_ACCOUNT_NOT_EXIST = 1204;
    public static final int ERR_USER_PASSWORD_WRONG = 1205;
    public static final int ERR_USER_SET_PASSWORD = 1206;
    public static final int ERR_USER_MESSEAGE_EMPTY = 1207;
    public static final int ERR_USER_ACCOUNT_FORBID = 1208;
    public static final int ERR_USER_EMAIL_EXIST = 1209;
    public static final int ERR_USER_CODE_WRONG = 1211;
    public static final int ERR_USER_IDENTITY_INVALID = 1212;


}
