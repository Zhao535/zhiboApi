package cn.maidaotech.smartapi.api.merchant.entity;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;

public class MerchantQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
