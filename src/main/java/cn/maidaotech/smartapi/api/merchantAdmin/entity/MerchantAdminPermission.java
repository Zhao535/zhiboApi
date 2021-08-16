package cn.maidaotech.smartapi.api.merchantAdmin.entity;

import cn.maidaotech.smartapi.common.model.Constants;

public enum MerchantAdminPermission {

    NONE("", ""),

    ROLE_EDIT("权限编辑",Constants.LEVEL_IMPORTANT),
    ROLE_LIST("权限列表",Constants.LEVEL_PRIMARY),

    PRODUCT_EDIT("商户编辑", Constants.LEVEL_PRIMARY),
    PRODUCT_LIST("商户列表", Constants.LEVEL_WARNING);

    private String val;
    private String level;

    MerchantAdminPermission(String val, String level) {
        this.val = val;
        this.level = level;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
