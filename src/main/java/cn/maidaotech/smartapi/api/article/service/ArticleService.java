package cn.maidaotech.smartapi.api.article.service;

import cn.maidaotech.smartapi.api.article.entity.ArticleQo;
import cn.maidaotech.smartapi.api.article.model.Article;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ArticleService {

    void save(Article article) throws Exception;

    Page<Article> items(ArticleQo qo) throws Exception;

    Page<Article> itemsForAdm(ArticleQo qo) throws Exception;

    Article item(Integer id) throws Exception;

    void changeStatus(Integer id) throws Exception;

    List<Article> findAllByIdIn(Collection<Integer> ids) throws Exception;




}
