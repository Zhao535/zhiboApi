package cn.maidaotech.smartapi.api.product.model;

import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.product.entity.ProductParam;
import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import cn.maidaotech.smartapi.common.converter.ProductParamArrayConverter;
import cn.maidaotech.smartapi.common.converter.ProductSpecArrayConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String sequence;
    @Column
    private Integer merchantId;
    @Column
    private String name;
    @Column
    private Integer brandId;
    @Column
    private Long createdAt;
    @Column
    @Convert(converter = ProductSpecArrayConverter.class)
    private List<ProductSpec> specs;
    @Column
    @Convert(converter = ProductParamArrayConverter.class)
    private List<ProductParam> params;
    @Column
    private Integer priority;
    @Column
    private Byte status;
    @Column
    private String content;
    @Column
    private Long pubAt;
    @Column
    private Integer price;
    @Transient
    private Merchant merchant;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public List<ProductSpec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<ProductSpec> specs) {
        this.specs = specs;
    }

    public List<ProductParam> getParams() {
        return params;
    }

    public void setParams(List<ProductParam> params) {
        this.params = params;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPubAt() {
        return pubAt;
    }

    public void setPubAt(Long pubAt) {
        this.pubAt = pubAt;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}
