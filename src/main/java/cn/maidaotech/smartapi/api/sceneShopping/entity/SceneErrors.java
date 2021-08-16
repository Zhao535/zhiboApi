package cn.maidaotech.smartapi.api.sceneShopping.entity;

import cn.maidaotech.smartapi.common.model.ErrorCode;

public interface SceneErrors extends ErrorCode {
    public final static int ERR_SCENE_NAME_NOT_NULL=1800;
    public final static int ERR_SCENE_ARTICLE_NULL=1801;
    public final static int ERR_SCENE_PRODUCT_NOT_NULL=1802;
    public final static int ERR_SCENETYPE_NAME_NOT_NULL=1803;

}
