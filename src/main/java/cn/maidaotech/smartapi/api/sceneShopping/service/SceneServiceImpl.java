package cn.maidaotech.smartapi.api.sceneShopping.service;


import cn.maidaotech.smartapi.api.article.model.Article;
import cn.maidaotech.smartapi.api.article.service.ArticleService;
import cn.maidaotech.smartapi.api.brand.entity.ProductBrandStatus;
import cn.maidaotech.smartapi.api.brand.model.ProductBrand;
import cn.maidaotech.smartapi.api.sceneShopping.entity.SceneErrors;
import cn.maidaotech.smartapi.api.sceneShopping.entity.SceneQo;
import cn.maidaotech.smartapi.api.sceneShopping.entity.SceneWrapOption;
import cn.maidaotech.smartapi.api.sceneShopping.model.Scene;
import cn.maidaotech.smartapi.api.sceneShopping.ropository.SceneRepository;
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


@Service
public class SceneServiceImpl implements SceneService, SceneErrors {

    @Autowired
    private SceneRepository sceneRepository;


    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Scene> sceneCache;

    @PostConstruct
    public void init() {
        sceneCache = kvCacheFactory.create(new CacheOptions("scenes", 1, 3600), new RepositoryProvider<Integer, Scene>() {

            @Override
            public Scene findByKey(Integer id) throws Exception {
                return sceneRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, Scene> findByKeys(Collection<Integer> ids) throws Exception {
                List<Scene> admins = sceneRepository.findAllById(ids);
                return admins.stream().collect(Collectors.toMap(Scene::getId, it -> it));
            }
        }, new BeanModelConverter<>(Scene.class));
    }

    private Scene findById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        Scene scene = sceneCache.findByKey(id);
        return scene;
    }

    private Scene getById(Integer id) throws Exception {
        Scene scene = findById(id);
        if (scene == null) {
            throw new DataNotFoundServiceException();
        }
        return scene;
    }


    @Override
    public void save(Scene scene) throws Exception {
        Long now = System.currentTimeMillis();
        if (scene.getId() == null || scene.getId() == 0) {
            if (scene.getTitle() == null) {
                throw new ServiceException(ERR_SCENE_NAME_NOT_NULL);
            }
            if (scene.getSubHeading() == null) {
                throw new ServiceException(ERR_SCENE_NAME_NOT_NULL);
            }
            if (scene.getContent() == null) {
                throw new ServiceException(ERR_SCENE_ARTICLE_NULL);
            }
            if (CollectionUtils.isEmpty(scene.getProducts())) {
                throw new ServiceException(ERR_SCENE_PRODUCT_NOT_NULL);
            }
            scene.setAuthorId(AdminContexts.requestAdminId());
            scene.setCreatedAt(now);
            scene.setStatus(Constants.STATUS_HALT);
            sceneRepository.save(scene);
        } else {
            Scene exist = getById(scene.getId());
            exist.setTitle(scene.getTitle());
            exist.setSubHeading(scene.getSubHeading());
            exist.setContent(scene.getContent());
            exist.setProducts(scene.getProducts());
            sceneRepository.save(exist);
            sceneCache.removeSafely(scene.getId());
        }

    }

    @Override
    public Page<Scene> items(SceneQo qo) throws Exception {
        return sceneRepository.findAll(qo);
    }

    @Override
    public Scene item(Integer id) throws Exception {
        return getById(id);
    }

    @Override
    public void changeStatus(Integer id) throws Exception {
        Scene scene = getById(id);
        if (scene.getStatus() == Constants.STATUS_HALT) {
            scene.setStatus(Constants.STATUS_OK);
        } else {
            scene.setStatus(Constants.STATUS_HALT);
        }
        sceneRepository.save(scene);
        sceneCache.removeSafely(id);
    }

    @Override
    public List<Scene> findByIdIn(Collection<Integer> ids) throws Exception {

        return sceneRepository.findAllByIdIn(ids);
    }
}
