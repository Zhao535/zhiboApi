package cn.maidaotech.smartapi.api.ui.model;

import cn.maidaotech.smartapi.api.ui.entity.Component;
import cn.maidaotech.smartapi.common.converter.ComponentArrayConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ui")
public class UI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    @Convert (converter = ComponentArrayConverter.class)
    private List<Component> components;
    @Column
    private Long createdAt;
    @Column
    private Byte isDefault;
    @Column
    private String title;
    @Column
    private Byte type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
