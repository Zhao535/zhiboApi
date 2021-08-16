package cn.maidaotech.smartapi.api.template.model;

import cn.maidaotech.smartapi.api.product.entity.ProductParam;
import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import cn.maidaotech.smartapi.common.converter.ProductParamArrayConverter;
import cn.maidaotech.smartapi.common.converter.ProductSpecArrayConverter;
import cn.maidaotech.smartapi.common.utils.SimpleHtmlParser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ProductTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String title;
    @Column
    private String sequence;
    @Column
    @Convert(converter = ProductSpecArrayConverter.class)
    private List<ProductSpec> specs;
    @Column
    @Convert(converter = ProductParamArrayConverter.class)
    private List<ProductParam> params;
    @Column
    private String content;
    @Column(updatable = false)
    private Long createdAt;
    @Column
    private Byte status;

    public ProductTemplate() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
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

    public String getContent() {
        return SimpleHtmlParser.removeScript(content);
    }

    public void setContent(String content) {
        this.content = SimpleHtmlParser.removeScript(content);
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = (status == null ? 2 : status);
    }
}
