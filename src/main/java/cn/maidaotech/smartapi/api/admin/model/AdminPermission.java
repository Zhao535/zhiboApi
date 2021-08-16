package cn.maidaotech.smartapi.api.admin.model;


import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.StringUtils;

public enum AdminPermission {

    //    none
    NONE("", ""),

    /* 功能模块 */
    // admin&role
    ROLE_LIST("管理组列表", Constants.LEVEL_IMPORTANT),
    ROLE_EDIT("管理组管理", Constants.LEVEL_IMPORTANT),

    ADMIN_LIST("管理员列表", Constants.LEVEL_WARNING),
    ADMIN_EDIT("编辑管理员", Constants.LEVEL_IMPORTANT),
    //article
    MERCHANT_EDIT("店铺编辑", Constants.LEVEL_WARNING),
    MERCHANT_LIST("店铺列表", Constants.LEVEL_PRIMARY),
    //article
    USER_EDIT("用户编辑", Constants.LEVEL_WARNING),
    USER_LIST("用户列表", Constants.LEVEL_PRIMARY),
    //scene
    SCENE_EDIT("情景编辑", Constants.LEVEL_WARNING),
    SCENE_LIST("情景列表", Constants.LEVEL_PRIMARY),
    //product
    PRODUCT_EDIT("产品编辑", Constants.LEVEL_WARNING),
    PRODUCT_LIST("产品列表", Constants.LEVEL_PRIMARY),
    //article
    ARTICL_EDIT("文章编辑", Constants.LEVEL_WARNING),
    ARTICLE_LIST("文章列表", Constants.LEVEL_PRIMARY),
    //brand
    BRAND_EDIT("品牌编辑", Constants.LEVEL_WARNING),
    BRAND_LIST("品牌列表", Constants.LEVEL_PRIMARY),
    //template
    TEMPLATE_EDIT("模板编辑", Constants.LEVEL_WARNING),
    TEMPLATE_LIST("模板列表", Constants.LEVEL_PRIMARY),
    //category
    CATEGORY_EDIT("分类编辑", Constants.LEVEL_WARNING),
    CATEGORY_LIST("分类列表", Constants.LEVEL_PRIMARY),
    //sceneType
    SCENETYPE_EDIT("情景分类编辑",Constants.LEVEL_WARNING),
    SCENETYPE_LIST("情景分类列表",Constants.LEVEL_PRIMARY);



    private String val;
    private String level;

    AdminPermission(String val, String level) {
        this.val = val;
        this.level = level;
    }

    public static AdminPermission nameOf(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (AdminPermission p : AdminPermission.values()) {
            if (name.equals(p.name())) {
                return p;
            }
        }
        return null;
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
