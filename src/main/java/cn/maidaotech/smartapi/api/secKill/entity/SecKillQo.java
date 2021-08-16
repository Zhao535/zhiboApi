package cn.maidaotech.smartapi.api.secKill.entity;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;

import java.util.List;

public class SecKillQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "createdAt")
    private Long createdAt;

    @QueryField(type = QueryType.EQUAL, name = "merchantId")
    private Integer merchantId;
    @QueryField(type = QueryType.IN, name = "id")
    private List<Integer> ids;
    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            this.ids = null;
        } else {
            this.ids = ids;
        }
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getMerchantId() {

        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
