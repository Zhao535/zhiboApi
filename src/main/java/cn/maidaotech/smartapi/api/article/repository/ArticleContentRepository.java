package cn.maidaotech.smartapi.api.article.repository;

import cn.maidaotech.smartapi.api.article.model.Article;
import cn.maidaotech.smartapi.api.article.model.ArticleContent;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.Collection;
import java.util.List;

public interface ArticleContentRepository extends BaseRepository<ArticleContent,Integer> {

    List<ArticleContent> findAllByArticleIdIn(Collection<Integer> ids);
}
