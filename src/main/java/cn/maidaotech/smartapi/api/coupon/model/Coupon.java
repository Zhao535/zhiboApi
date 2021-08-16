package cn.maidaotech.smartapi.api.coupon.model;

import cn.maidaotech.smartapi.api.coupon.entity.Payload;
import cn.maidaotech.smartapi.api.coupon.entity.Rule;
import cn.maidaotech.smartapi.common.converter.PayLoadConverter;
import cn.maidaotech.smartapi.common.converter.RuleConverter;

import javax.persistence.*;

@Entity
@Table
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private Integer duration;
    @Column
    private Byte status;
    @Column
    private Long createdAt;
    @Column
    @Convert(converter = PayLoadConverter.class)
    private Payload payload;
    @Column
    @Convert(converter = RuleConverter.class)
    private Rule rule;

    @Transient
    private Byte isHaveCoupon;

    @Transient
    private  UserCoupon userCoupon;

    public Byte getIsHaveCoupon() {
        return isHaveCoupon;
    }

    public void setIsHaveCoupon(Byte isHaveCoupon) {
        this.isHaveCoupon = isHaveCoupon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public UserCoupon getUserCoupon() {
        return userCoupon;
    }

    public void setUserCoupon(UserCoupon userCoupon) {
        this.userCoupon = userCoupon;
    }
}
