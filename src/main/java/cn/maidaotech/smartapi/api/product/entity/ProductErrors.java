package cn.maidaotech.smartapi.api.product.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface ProductErrors extends ErrorCode {

    public final static int ERR_PRODUCT_BRAND_ID_NEEDED=1501;
    public final static int ERR_PRODUCT_NAME_NEEDED=1502;
    public final static int ERR_PRODUCT_MERCHANT_ID_NEEDED=1503;
    public final static int ERR_PRODUCT_CATEGORY_ID_NEEDED=1504;
    public final static int ERR_PRODUCT_PARAMS_NEEDED=1505;
    public final static int ERR_PRODUCT_SPACES_NEEDED=1506;
    public final static int ERR_PRODUCT_PRICE_NEEDED=1507;


}
