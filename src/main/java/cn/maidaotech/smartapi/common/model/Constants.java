package cn.maidaotech.smartapi.common.model;

public class Constants {

    public static final int CACHE_REDIS_EXPIRE = 3600;

    // 权限操作级别
    public static String LEVEL_PRIMARY = "blue";
    public static String LEVEL_IMPORTANT = "red";
    public static String LEVEL_WARNING = "orange";

    public static final byte STATUS_OK = 1;// 默认
    public static final byte STATUS_HALT = 2;// 删除、停用、取消
}
