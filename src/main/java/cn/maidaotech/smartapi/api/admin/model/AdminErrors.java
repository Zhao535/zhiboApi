package cn.maidaotech.smartapi.api.admin.model;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface AdminErrors extends ErrorCode {

    public static final int ERR_ADMIN_NAME_EMPTY = 1100;
    public static final int ERR_ADMIN_EMAIL_INVALID = 1101;
    public static final int ERR_ADMIN_MOBILE_INVALID = 1102;
    public static final int ERR_ADMIN_MOBILE_EXIST = 1103;
    public static final int ERR_ADMIN_ACCOUNT_NOT_EXIST = 1104;
    public static final int ERR_ADMIN_PASSWORD_WRONG = 1105;
    public static final int ERR_ADMIN_SET_PASSWORD =1106;
    public static final int ERR_ADMIN_ACCOUNT_FORBID =1107;
    public static final int ERR_ADMIN_MESSAGE_EMPTY =1108;
    public static final int ERR_ADMIN_ROLE_NOT_EXIST =1109;
    public static final int ERR_ADMIN_STATUS_NOT_EXIST =1110;
}
