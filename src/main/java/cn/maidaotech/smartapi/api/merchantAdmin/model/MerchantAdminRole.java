package cn.maidaotech.smartapi.api.merchantAdmin.model;

import cn.maidaotech.smartapi.common.converter.StringArrayConverter;
import cn.maidaotech.smartapi.common.model.Permission;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class MerchantAdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer merchantId;


    @Convert(converter = StringArrayConverter.class)
    @Column
    private List<String> permissions;

    @Transient
    private List<Permission> pstr;


    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPstr() {
        return pstr;
    }

    public void setPstr(List<Permission> pstr) {
        this.pstr = pstr;
    }
}
