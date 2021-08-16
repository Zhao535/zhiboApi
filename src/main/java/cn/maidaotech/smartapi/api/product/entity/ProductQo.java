package cn.maidaotech.smartapi.api.product.entity;

import cn.maidaotech.smartapi.common.reposiotry.support.DataQueryObjectPage;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryBetween;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryField;
import cn.maidaotech.smartapi.common.reposiotry.support.QueryType;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;

import java.util.List;

public class ProductQo extends DataQueryObjectPage {

    @QueryField(type = QueryType.EQUAL, name = "merchantId")
    private Integer merchantId;
    @QueryField(type = QueryType.IN, name = "sequence")
    private List<String> sequence;
    @QueryField(type = QueryType.FULL_LIKE, name = "name")
    private String name;
    @QueryField(type = QueryType.EQUAL, name = "categoryId")
    private Integer categoryId;
    @QueryField(type = QueryType.EQUAL, name = "brandId")
    private Integer brandId;
    @QueryField(type = QueryType.EQUAL, name = "status")
    private Byte status;
    @QueryField(type = QueryType.BEWTEEN, name = "price")
    private QueryBetween<Integer> price;
    @QueryField(type = QueryType.IN, name = "brandId")
    private List<Integer> brandIds;
    @QueryField(type = QueryType.IN, name = "id")
    private List<Integer> id;


    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSequence() {
        return sequence;
    }

    public void setSequence(List<String> sequence) {
        this.sequence = CollectionUtils.isEmpty(sequence) ? null : sequence;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = (categoryId == 0)? null :categoryId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public QueryBetween<Integer> getPrice() {
        return price;
    }

    public void setPrice(QueryBetween<Integer> price) {
        this.price = price;
    }

    public List<Integer> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(List<Integer> brandIds) {
        this.brandIds = (CollectionUtils.isEmpty(brandIds) ? null : brandIds);
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = (brandId == 0) ? null : brandId;
    }
}
