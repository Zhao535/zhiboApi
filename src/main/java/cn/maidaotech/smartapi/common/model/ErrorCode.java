package cn.maidaotech.smartapi.common.model;

public interface ErrorCode {
    public static final int ERR_UNKNOWN_ERROR = 1;
    public static final int ERR_ILLEGAL_ARGUMENT = 2;
    public static final int ERR_PERMISSION_DENIED = 3;
    public static final int ERR_DETAILED_MESSAGE = 4;
    public static final int ERR_SESSION_EXPIRES = 5;
    public static final int ERR_OPERATION_TOO_FREQUENT = 6;
    public static final int ERR_DATA_NOT_FOUND = 7;

    public static final int ERR_VAL_CODE_ERROR = 100;
    //file
    public static final int ERR_FILE_SIZE = 200;
    public static final int ERR_IMG_NEEDED = 201;
    public static final int ERR_FILE_NOT_FOUND = 202;

}

