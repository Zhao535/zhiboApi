package cn.maidaotech.smartapi.api.sceneShopping.model;


import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.common.converter.ProductArrayConverter;
import cn.maidaotech.smartapi.common.utils.SimpleHtmlParser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Scene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String title;
    @Column
    private String subHeading;
    @Column
    private String img;
    @Column
    private Integer authorId;
    @Column
    private Byte type;
    @Column
    private String content;
    @Column
    private Long createdAt;
    @Column
    private Byte status;
    @Column
    @Convert(converter = ProductArrayConverter.class)
    private List<Product> products;


    public Scene() {
    }

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return SimpleHtmlParser.removeScript(content);
    }

    public void setContent(String content) {
        this.content = SimpleHtmlParser.removeScript(content);
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }


    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}

