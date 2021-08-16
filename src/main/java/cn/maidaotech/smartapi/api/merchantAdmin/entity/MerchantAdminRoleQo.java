package cn.maidaotech.smartapi.api.merchantAdmin.entity;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;

public class MerchantAdminRoleQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "merchantId")
    private Integer merchantId;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
