package cn.maidaotech.smartapi.api.brand.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface BrandErrors extends ErrorCode {
    public static int ERR_BRAND_NAME_NOT_NULL=1301;
    public static int ERR_BRAND_LOGO_NOT_NULL=1302;
    public static int ERR_BRAND_PRIORITY_NOT_NULL=1303;
    public static int ERR_BRAND_SEQUENCES_NOT_EMPTY=1304;


}
