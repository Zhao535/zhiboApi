package cn.maidaotech.smartapi.api.sceneShopping.entity;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;

public class SceneQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.FULL_LIKE,name = "title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
