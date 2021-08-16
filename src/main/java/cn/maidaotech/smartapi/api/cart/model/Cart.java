package cn.maidaotech.smartapi.api.cart.model;

import cn.maidaotech.smartapi.api.cart.entity.CartPayload;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.converter.CartPayloadConverter;
import cn.maidaotech.smartapi.common.converter.ProductSpecConverter;

import javax.persistence.*;

@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private Integer num;
    @Column
    private Integer merchantId;
    @Column
    private Integer productId;
    @Column(updatable = false)
    private Long createdAt;
    @Column
    private String productSno;
    @Column
    private Integer userId;
    @Column
    @Convert(converter = CartPayloadConverter.class)
    private CartPayload cartPayload;
    @Transient
    private SecKill secKill;


    public void copy(Cart cart, String productSno) {
        this.setUserId(UserContexts.requestUserId());
        this.setNum(cart.getNum());
        this.setMerchantId(cart.getMerchantId());
        this.setProductId(cart.getProductId());
        this.setProductSno(productSno);
        this.setCreatedAt(System.currentTimeMillis());
    }

    @Transient
    private Merchant merchant;
    @Transient
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getProductSno() {
        return productSno;
    }

    public void setProductSno(String productSno) {
        this.productSno = productSno;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartPayload getCartPayload() {
        return cartPayload;
    }

    public void setCartPayload(CartPayload cartPayload) {
        this.cartPayload = cartPayload;
    }

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }
}
