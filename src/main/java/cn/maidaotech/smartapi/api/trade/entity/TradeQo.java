package cn.maidaotech.smartapi.api.trade.entity;

import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;

import java.util.Objects;

public class TradeQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "merchantId")
    private Integer merchantId;
    @QueryField(type = QueryType.EQUAL, name = "type")
    private Byte type;
    @QueryField(type = QueryType.EQUAL, name = "userId")
    private Integer userId;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId() {
        this.merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type == 0 ? null : type;
    }


    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
