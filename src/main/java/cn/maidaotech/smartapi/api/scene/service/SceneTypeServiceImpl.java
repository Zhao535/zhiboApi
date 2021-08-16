package cn.maidaotech.smartapi.api.scene.service;

import cn.maidaotech.smartapi.api.scene.entity.SceneTypeQo;
import cn.maidaotech.smartapi.api.scene.model.SceneType;
import cn.maidaotech.smartapi.api.scene.repository.SceneTypeRepository;
import cn.maidaotech.smartapi.api.sceneShopping.model.Scene;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.maidaotech.smartapi.api.sceneShopping.entity.SceneErrors.ERR_SCENETYPE_NAME_NOT_NULL;

@Service
public class SceneTypeServiceImpl implements SceneTypeService {

    @Autowired
    private SceneTypeRepository sceneTypeRepository;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, SceneType> sceneTypeCache;

    @PostConstruct
    public void init() {
        sceneTypeCache = kvCacheFactory.create(new CacheOptions("sceneTypes", 1, 3600), new RepositoryProvider<Integer, SceneType>() {

            @Override
            public SceneType findByKey(Integer id) throws Exception {
                return sceneTypeRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, SceneType> findByKeys(Collection<Integer> ids) throws Exception {
                List<SceneType> types = sceneTypeRepository.findAllById(ids);
                return types.stream().collect(Collectors.toMap(SceneType::getId, it -> it));
            }
        }, new BeanModelConverter<>(SceneType.class));
    }

    private SceneType findById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        SceneType sceneType = sceneTypeCache.findByKey(id);
        return sceneType;
    }

    private SceneType getById(Integer id) throws Exception {
        SceneType sceneType = findById(id);
        if (sceneType == null) {
            throw new DataNotFoundServiceException();
        }
        return sceneType;
    }


    @Override
    public void save(SceneType sceneType) throws Exception {
        Long now = System.currentTimeMillis();
        if (sceneType.getId() == null || sceneType.getId() == 0) {
            if (sceneType.getName() == null) {
                throw new ServiceException(ERR_SCENETYPE_NAME_NOT_NULL);
            }
            if (Objects.isNull(sceneType.getStatus())) {
                sceneType.setStatus(Constants.STATUS_HALT);
            }
            sceneTypeRepository.save(sceneType);
        } else {
            SceneType exist = getById(sceneType.getId());
            exist.setName(sceneType.getName());
            exist.setSubHeading(sceneType.getSubHeading());
            exist.setIcon(sceneType.getIcon());
            sceneTypeRepository.save(exist);
            sceneTypeCache.removeSafely(sceneType.getId());
        }
    }

    @Override
    public Page<SceneType> items(SceneTypeQo qo) throws Exception {
        return sceneTypeRepository.findAll(qo);
    }

    @Override
    public SceneType item(Integer id) throws Exception {
        return findById(id);
    }

    @Override
    public void changeStatus(Integer id) throws Exception {
        SceneType sceneType = getById(id);
        if (sceneType.getStatus() == Constants.STATUS_HALT) {
            sceneType.setStatus(Constants.STATUS_OK);
        } else {
            sceneType.setStatus(Constants.STATUS_HALT);
        }
        sceneTypeRepository.save(sceneType);
        sceneTypeCache.removeSafely(id);
    }

    @Override
    public List<SceneType> allTypes() throws Exception {
        return sceneTypeRepository.findAll();
    }

    @Override
    public List<SceneType> allTypesForUsr() throws Exception {
        return sceneTypeRepository.findAllByStatus(Constants.STATUS_OK);
    }

    @Override
    public void remove(Integer id) throws Exception {
        sceneTypeRepository.deleteById(id);
    }
}
