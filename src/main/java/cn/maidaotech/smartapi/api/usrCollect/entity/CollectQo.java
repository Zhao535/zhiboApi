package cn.maidaotech.smartapi.api.usrCollect.entity;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;

public class CollectQo extends DataQueryObjectPage {
    @QueryField(type = QueryType.EQUAL, name = "type")
    private Byte type;
    @QueryField(type = QueryType.EQUAL, name = "userId")
    private Integer userId;
    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
