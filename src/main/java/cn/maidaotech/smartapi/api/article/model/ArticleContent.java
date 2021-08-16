package cn.maidaotech.smartapi.api.article.model;

import cn.maidaotech.smartapi.common.utils.SimpleHtmlParser;

import javax.persistence.*;

@Entity
@Table
public class ArticleContent {

    @Id
    @Column
    private Integer articleId;

    @Column
    private String content;


    public ArticleContent() {

    }


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return SimpleHtmlParser.removeScript(content);
    }

    public void setContent(String content) {
        this.content = SimpleHtmlParser.removeScript(content);
    }
}
