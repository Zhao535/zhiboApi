package cn.maidaotech.smartapi.api.coupon.entity;


import cn.maidaotech.smartapi.api.category.model.ProductCategory;
import cn.maidaotech.smartapi.api.product.model.Product;

public class Payload {
    private Byte type;
    private ProductCategory category;
    private Product product;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
