package cn.maidaotech.smartapi.api.brand.entity;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;
import cn.maidaotech.smartapi.common.utils.StringUtils;

public class BrandQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status;

    @QueryField(type = QueryType.EQUAL, name = "name")
    private String name;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status == 0 ? null : status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.isEmpty(name.replace(" ", "")) ? null : name;
    }
}
