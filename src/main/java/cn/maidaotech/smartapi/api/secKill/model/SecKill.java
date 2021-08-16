package cn.maidaotech.smartapi.api.secKill.model;

import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillSpec;
import cn.maidaotech.smartapi.common.converter.SecKillSpecArrayConverter;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
public class SecKill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer productId;
    private Long startAt;
    private Long endAt;
    @Column
    @Convert(converter = SecKillSpecArrayConverter.class)
    private List<SecKillSpec> secKillSpec;
    private Byte status;

    @Transient
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public List<SecKillSpec> getSecKillSpec() {
        return secKillSpec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSecKillSpec(List<SecKillSpec> secKillSpec) {
        this.secKillSpec = secKillSpec;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
