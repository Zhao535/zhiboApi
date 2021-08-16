package cn.maidaotech.smartapi.api.trade.entity;

import cn.maidaotech.smartapi.api.cart.entity.CartPayload;
import cn.maidaotech.smartapi.api.product.model.Product;

public class TradeItem {
    private Product product;
    private Integer num;
    private String productSno;
    private Integer id;
    private CartPayload cartPayload;//todo 优化订单tradeItems内容。


    public CartPayload getCartPayload() {
        return cartPayload;
    }

    public void setCartPayload(CartPayload cartPayload) {
        this.cartPayload = cartPayload;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getProductSno() {
        return productSno;
    }

    public void setProductSno(String productSno) {
        this.productSno = productSno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
