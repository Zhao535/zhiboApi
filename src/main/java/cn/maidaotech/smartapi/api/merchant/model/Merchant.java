package cn.maidaotech.smartapi.api.merchant.model;

import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.trade.model.Trade;
import cn.maidaotech.smartapi.common.addressPicker.model.Location;
import cn.maidaotech.smartapi.common.converter.LocationConverter;
import cn.maidaotech.smartapi.common.converter.StringArrayConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String mobile;

    @Column
    @Convert(converter = LocationConverter.class)
    private Location location;

    @Column
    private String logo;

    @Column
    @Convert(converter = StringArrayConverter.class)
    private List<String> sequences;

    @Column
    @Convert(converter = StringArrayConverter.class)
    private List<String> imgs;//TODO 拓展 图片组 json {logo:"",imgs:"{[,,]}"}

    @Column
    private Byte status;

    @Column(updatable = false)
    private Long createdAt;

    @Column
    private Long signinAt;

    @Column
    private Long lastModify;

    @Transient
    private Long expiredAt;

    @Transient
    private String duration;

    @Transient
    private List<MerchantAdmin> MerchantAdmin;

    @Transient
    private List<Trade> trades;

    @Transient
    private Integer productNum;


    public Merchant() {
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }


//    public Merchant(String img, String name) {
//        this.setImg(img);
//        this.setName(name);
//    }
//
//    public Merchant simplifyAdmin() {
//        return new Merchant(img, name);
//    }


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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
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

    public Long getSigninAt() {
        return signinAt;
    }

    public void setSigninAt(Long signinAt) {
        this.signinAt = signinAt;
    }

    public List<cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin> getMerchantAdmin() {
        return MerchantAdmin;
    }

    public void setMerchantAdmin(List<cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin> merchantAdmin) {
        MerchantAdmin = merchantAdmin;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Long expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public List<String> getSequences() {
        return sequences;
    }

    public void setSequences(List<String> sequences) {
        this.sequences = sequences;
    }


}
