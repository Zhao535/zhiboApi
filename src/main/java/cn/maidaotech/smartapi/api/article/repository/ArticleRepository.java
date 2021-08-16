package cn.maidaotech.smartapi.api.article.repository;

import cn.maidaotech.smartapi.api.article.model.Article;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.Collection;
import java.util.List;

public interface ArticleRepository extends BaseRepository<Article,Integer> {

    List<Article> findAllByIdIn (Collection<Integer> ids);
}
