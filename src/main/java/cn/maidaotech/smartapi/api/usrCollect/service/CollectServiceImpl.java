package cn.maidaotech.smartapi.api.usrCollect.service;

import cn.maidaotech.smartapi.api.article.service.ArticleService;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.product.entity.ProductQo;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.api.sceneShopping.service.SceneService;
import cn.maidaotech.smartapi.api.user.model.User;
import cn.maidaotech.smartapi.api.usrCollect.entity.CollectErrors;
import cn.maidaotech.smartapi.api.usrCollect.entity.CollectQo;
import cn.maidaotech.smartapi.api.usrCollect.entity.CollectType;
import cn.maidaotech.smartapi.api.usrCollect.model.Collect;
import cn.maidaotech.smartapi.api.usrCollect.repostory.CollectRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class CollectServiceImpl implements CollectService, CollectErrors {
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private KvCacheFactory kvCacheFactory;


    private KvCacheWrap<Long, Collect> collectCache;


    @PostConstruct
    public void init() {
        collectCache = kvCacheFactory.create(new CacheOptions("collect", 1, 3600),
                new RepositoryProvider<Long, Collect>() {
                    @Override
                    public Collect findByKey(Long id) throws Exception {
                        return collectRepository.findById(id).orElse(null);
                    }

                    @Override
                    public Map<Long, Collect> findByKeys(Collection<Long> ids) throws Exception {
                        return null;
                    }
                }, new BeanModelConverter<>(Collect.class));
    }

    private Collect getById(Long id) throws Exception {
        Collect collect = finById(id);
        if (Objects.isNull(collect)) {
            throw new DataNotFoundServiceException();
        }
        return collect;
    }

    private Collect finById(Long id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        Collect collect = collectCache.findByKey(id);
        return collect;
    }

    @Override
    public void save(Collect collect) throws Exception {
        if (Objects.isNull(collect.getType())) {
            throw new ServiceException(ERR_COLLECT_TYPE_NOT_NULL);
        }
        if (Objects.isNull(collect.getFromId())) {
            throw new ServiceException(ERR_COLLECT_FROM_ID_NOT_NULL);
        }
        Integer userId = UserContexts.requestUserId();
        Collect exist = find(collect.getType(), collect.getFromId());
        if (Objects.isNull(exist)) {
            collect.setUserId(userId);
            collect.setStatus(Constants.STATUS_OK);
            collectRepository.save(collect);
        } else {
            exist.setStatus(exist.getStatus().equals(Constants.STATUS_OK) ? Constants.STATUS_HALT : Constants.STATUS_OK);
            collectRepository.save(exist);
        }
    }


    @Override
    public Collect item(Byte type, Integer fromId) throws Exception {
        return find(type, fromId);
    }

    private Collect find(Byte type, Integer fromId) throws Exception {
        Integer userId = UserContexts.requestUserId();
        Collect collect = collectRepository.findByUserIdAndFromIdAndType(userId, fromId, type);
        return collect;
    }


    @Override
    public List items(CollectQo qo) throws Exception {
        Integer userId = UserContexts.requestUserId();
        qo.setUserId(userId);
        qo.setStatus(Constants.STATUS_OK);
        List<Collect> collectList = collectRepository.findAll(qo).getContent();
        List fromIdList = new ArrayList();
        for (Collect collect : collectList) {
            fromIdList.add(collect.getFromId());
        }
        if (qo.getType() == CollectType.PRODUCT.value()) {
            return productService.findByIdIn(fromIdList);
        }
        if (qo.getType() == CollectType.ARTICLE.value()) {
            return articleService.findAllByIdIn(fromIdList);
        }
        if (qo.getType() == CollectType.SCENE.value()) {
            return sceneService.findByIdIn(fromIdList);
        }
        if (qo.getType() == CollectType.MERCHANT.value()) {
            return merchantService.findByIdIn(fromIdList);
        }
        return null;
    }

    @Override
    public boolean isCollect(Byte type, Integer fromId) throws Exception {
        Collect collect = find(type, fromId);
        if (Objects.nonNull(collect)) {
            if (collect.getStatus().equals(Constants.STATUS_OK)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    @Override
    public Integer collectNum() throws Exception {
        Integer userId = UserContexts.requestUserId();
        Byte status = Constants.STATUS_OK;
        Byte type = CollectType.MERCHANT.value();
        return collectRepository.countByStatusAndUserIdAndTypeNot(status, userId, type);
    }

    @Override
    public Integer focusNum() throws Exception {
        Integer userId = UserContexts.requestUserId();
        Byte status = Constants.STATUS_OK;
        Byte type = (byte) 4;
        return collectRepository.countByStatusAndUserIdAndType(status, userId, type);
    }
}
