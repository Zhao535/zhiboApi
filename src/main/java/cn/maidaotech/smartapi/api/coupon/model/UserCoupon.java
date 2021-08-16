package cn.maidaotech.smartapi.api.coupon.model;

import javax.persistence.*;

@Entity
@Table
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private Long validThru;
    @Column
    private Integer couponId;
    @Column
    private Byte status;
    @Column
    private Integer userId;
    @Column(updatable = false)
    private Long getAt;
    @Transient
    private Coupon coupon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getValidThru() {
        return validThru;
    }

    public void setValidThru(Long validThru) {
        this.validThru = validThru;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getGetAt() {
        return getAt;
    }

    public void setGetAt(Long getAt) {
        this.getAt = getAt;
    }
}
