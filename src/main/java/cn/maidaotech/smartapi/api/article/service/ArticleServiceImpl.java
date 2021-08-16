package cn.maidaotech.smartapi.api.article.service;


import cn.maidaotech.smartapi.api.admin.model.Admin;
import cn.maidaotech.smartapi.api.admin.service.AdminService;
import cn.maidaotech.smartapi.api.article.entity.ArticleQo;
import cn.maidaotech.smartapi.api.article.model.Article;
import cn.maidaotech.smartapi.api.article.model.ArticleContent;
import cn.maidaotech.smartapi.api.article.repository.ArticleContentRepository;
import cn.maidaotech.smartapi.api.article.repository.ArticleRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.AdminContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static cn.maidaotech.smartapi.api.article.entity.ArticleError.*;
import static cn.maidaotech.smartapi.common.model.ErrorCode.ERR_PERMISSION_DENIED;

@Service
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleContentRepository contentRepository;
    @Autowired
    private AdminService adminService;

    @Autowired
    private KvCacheFactory kvCacheFactory;


    private KvCacheWrap<Integer, Article> articleCache;

    @PostConstruct
    public void init() {
        articleCache = kvCacheFactory.create(new CacheOptions("article", 1, 3600), new RepositoryProvider<Integer, Article>() {
            @Override
            public Article findByKey(Integer id) throws Exception {
                return articleRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, Article> findByKeys(Collection<Integer> ids) throws Exception {
                List<Article> articles = articleRepository.findAllById(ids);
                return articles.stream().collect(Collectors.toMap(Article::getId, it -> it));
            }
        }, new BeanModelConverter<>(Article.class));
    }

    private Article writableArticle(Integer id) throws Exception {
        Article article = findById(id);

        if (!article.getAuthorId().equals(AdminContexts.requestAdminId())) {
            throw new ServiceException(ERR_PERMISSION_DENIED);
        }
        return article;
    }


    @Override
    public void save(Article article) throws Exception {
        Long now = System.currentTimeMillis();
        if (article.getId() == null || article.getId() == 0) {
            if (article.getTitle() == null) {
                throw new ServiceException(ERR_ARTICLE_TITLE_LENGTH);
            }
            if (article.getPicture() == null) {
                throw new ServiceException(ERR_ARTICLE_PICTURE_NOT_NULL);
            }
            if (article.getContent() == null) {
                throw new ServiceException(ERR_ARTICLE_CONTENT_NOT_NULL);
            }
            article.setAuthorId(AdminContexts.requestAdminId());
            article.setCreatedAt(now);
            if (Objects.isNull(article.getStatus())) {
                article.setStatus(Constants.STATUS_HALT);
            }
            Integer id = articleRepository.save(article).getId();
            ArticleContent content = new ArticleContent();
            content.setArticleId(id);
            content.setContent(article.getContent());
            contentRepository.save(content);
        } else {
            Article exist = getById(article.getId());
            exist.setPicture(article.getPicture());
            exist.setIntro(article.getIntro());
            articleRepository.save(exist);
            articleCache.removeSafely(article.getId());
        }

    }


    private Article getById(Integer id) throws Exception {
        Article article = findById(id);
        if (article == null) {
            throw new DataNotFoundServiceException();
        }
        return article;
    }

    private Article findById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        Article article = articleCache.findByKey(id);
        return article;
    }

    @Override
    public Page<Article> items(ArticleQo qo) throws Exception {
        qo.setStatus(Constants.STATUS_OK);
        Page<Article> articles = articleRepository.findAll(qo);
        warp(articles.getContent());
        return articles;
    }

    @Override
    public Page<Article> itemsForAdm(ArticleQo qo) throws Exception {
        Page<Article> articles = articleRepository.findAll(qo);
        warp(articles.getContent());
        return articles;
    }

    private void warp(Collection<Article> items) throws Exception {
        Set<Integer> ids = items.stream().map(Article::getId).collect(Collectors.toSet());
        if (true) {
            Map<Integer, ArticleContent> contentMap = contentRepository.findAllByArticleIdIn(ids).stream().collect(Collectors.toMap(ArticleContent::getArticleId, it -> it));
            for (Article item : items) {
                Integer id = item.getId();
                ArticleContent content = contentMap.get(id);
                if (Objects.nonNull(content)) {
                    item.setContent(content.getContent());
                }
            }
        }

        if (true) {
            Set<Integer> authorIds = items.stream().map(Article::getAuthorId).collect(Collectors.toSet());
            Map<Integer, Admin> admins = adminService.findByIdIn(authorIds);
            for (Article item : items) {
                Admin admin = admins.get(item.getAuthorId());
                item.setAuthorName(admin.getName());
                item.setAuthorImg(admin.getImg());
            }
        }
    }

    @Override
    public Article item(Integer id) throws Exception {
        Article article = getById(id);
        article.setContent(contentRepository.findById(id).orElse(null).getContent());
        return article;
    }

    @Override
    public void changeStatus(Integer id) throws Exception {
        Article article = getById(id);
        if (article.getStatus() == Constants.STATUS_OK) {
            article.setStatus(Constants.STATUS_HALT);
        } else {
            article.setStatus(Constants.STATUS_OK);
        }
        articleRepository.save(article);
        articleCache.removeSafely(id);
    }

    @Override
    public List<Article> findAllByIdIn(Collection<Integer> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<Article> articleList = articleRepository.findAllByIdIn(ids);
        Map<Integer, ArticleContent> contentMap = contentRepository.findAllByArticleIdIn(ids).stream().collect(Collectors.toMap(ArticleContent::getArticleId, it -> it));
        if (CollectionUtils.isEmpty(articleList)) {
            throw new DataNotFoundServiceException();
        }
        for (Article article : articleList) {
            Integer id = article.getId();
            ArticleContent content = contentMap.get(id);
            if (Objects.nonNull(content)) {
                article.setContent(content.getContent());
            }
        }
        return articleList;
    }


}
