package cn.maidaotech.smartapi.api.trade.model;


import cn.maidaotech.smartapi.api.address.model.Address;
import cn.maidaotech.smartapi.api.cart.entity.CartPayload;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.trade.entity.TradeItem;
import cn.maidaotech.smartapi.api.trade.entity.TradePayload;
import cn.maidaotech.smartapi.api.user.model.User;
import cn.maidaotech.smartapi.common.converter.CartPayloadConverter;
import cn.maidaotech.smartapi.common.converter.TradeItemArrayConverter;
import cn.maidaotech.smartapi.common.converter.TradePayloadListConverter;
import cn.maidaotech.smartapi.common.converter.UserAddressConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private Byte type;
    @Column
    private Integer userId;
    @Column
    private Integer merchantId;
    @Column
    private String orderNumber;
    @Column
    private Integer totalAmount;
    @Column
    private Integer totalPrice;
    @Column
    private Integer couponAmount;
    @Column
    private String mark;
    @Column
    private Integer userCouponId;
    @Column
    private Byte payment;
    @Column
    private Long createdAt;
    @Column
    @Convert(converter = TradePayloadListConverter.class)
    private List<TradePayload> tradePayloads;
    @Convert(converter = TradeItemArrayConverter.class)
    private List<TradeItem> tradeItems;
    @Convert(converter = UserAddressConverter.class)
    private Address address;

    @Transient
    private Merchant merchant;
    @Transient
    private User user;


    public List<TradePayload> getTradePayloads() {
        return tradePayloads;
    }

    public void setTradePayloads(List<TradePayload> tradePayloads) {
        this.tradePayloads = tradePayloads;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public List<TradeItem> getTradeItems() {
        return tradeItems;
    }

    public void setTradeItems(List<TradeItem> tradeItems) {
        this.tradeItems = tradeItems;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Byte getPayment() {
        return payment;
    }

    public void setPayment(Byte payment) {
        this.payment = payment;
    }
}
