package cn.maidaotech.smartapi.api.ui.service;


import cn.maidaotech.smartapi.api.ui.entity.UIErrors;
import cn.maidaotech.smartapi.api.ui.entity.UIQo;
import cn.maidaotech.smartapi.api.ui.model.UI;
import cn.maidaotech.smartapi.api.ui.repository.UiRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
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


@Service
public class UIServiceImpl implements UiService, UIErrors {

    @Autowired
    private UiRepository uiRepository;
    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, UI> uiCache;

    @PostConstruct
    public void init() {
        uiCache = kvCacheFactory.create(new CacheOptions("ui", 1, 3600), new RepositoryProvider<Integer, UI>() {

            @Override
            public UI findByKey(Integer id) throws Exception {
                return uiRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, UI> findByKeys(Collection<Integer> ids) throws Exception {
                List<UI> UIS = uiRepository.findAllById(ids);
                return UIS.stream().collect(Collectors.toMap(UI::getId, it -> it));
            }
        }, new BeanModelConverter<>(UI.class));
    }

    private UI findById(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        UI ui = uiCache.findByKey(id);
        return ui;
    }

    private UI getById(Integer id) throws Exception {
        UI ui = findById(id);
        if (Objects.isNull(ui)) {
            throw new DataNotFoundServiceException();
        }
        return ui;
    }


    @Override
    public void save(UI ui) throws Exception {
        if (Objects.isNull(ui.getId()) || ui.getId() == 0) {
            if (CollectionUtils.isEmpty(ui.getComponents())) {
                throw new ServiceException(ERR_UI_COMPONENT_NOT_NULL);
            }
            if (StringUtils.isNull(ui.getTitle())) {
                throw new ServiceException(ERR_UI_TITLE_NOT_NULL);
            }
            ui.setCreatedAt(System.currentTimeMillis());
            ui.setIsDefault(Constants.STATUS_HALT);
            ui = uiRepository.save(ui);
        } else {
            UI exist = getById(ui.getId());
            exist.setTitle(ui.getTitle());
            exist.setComponents(ui.getComponents());
            uiRepository.save(exist);
        }
        uiCache.removeSafely(ui.getId());
    }

    @Override
    public UI item(Integer id) throws Exception {
        return getById(id);
    }

    @Override
    public UI itemForUsr(Byte type) throws Exception {
        return uiRepository.findByIsDefaultAndType(Constants.STATUS_OK, type);
    }

    @Override
    public Page<UI> items(UIQo qo) throws Exception {
        return uiRepository.findAll(qo);
    }

    @Override
    public void uiDefault(Integer id) throws Exception {
        UI ui = getById(id);
        ui.setIsDefault(Constants.STATUS_OK);
        UI defaultUI = uiRepository.findByIsDefaultAndType(Constants.STATUS_OK,ui.getType());
        if (Objects.nonNull(defaultUI)) {
            defaultUI.setIsDefault(Constants.STATUS_HALT);
            uiRepository.save(defaultUI);
        }
        uiRepository.save(ui);

    }
}
