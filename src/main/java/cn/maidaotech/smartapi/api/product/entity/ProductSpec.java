package cn.maidaotech.smartapi.api.product.entity;

import java.util.List;

public class ProductSpec {
    private List<String> imgs;
    private List<ProductParam> params;
    private Integer stock;
    private Integer price;
    private String sno;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<ProductParam> getParams() {
        return params;
    }

    public void setParams(List<ProductParam> params) {
        this.params = params;
    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }


}
