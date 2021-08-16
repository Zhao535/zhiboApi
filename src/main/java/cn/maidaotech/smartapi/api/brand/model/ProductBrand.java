package cn.maidaotech.smartapi.api.brand.model;

import cn.maidaotech.smartapi.common.converter.IntegerArrayConverter;
import cn.maidaotech.smartapi.common.converter.StringArrayConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ProductBrand {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String logo;
    @Column
    private String name;
    @Column
    private Integer priority;
    @Column
    private Byte status;
    @Column
    private Long createdAt;
    @Column
    @Convert(converter = StringArrayConverter.class)
    private List<String> sequences;

    public ProductBrand() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getSequences() {
        return sequences;
    }

    public void setSequences(List<String> sequences) {
        this.sequences = sequences;
    }
}
