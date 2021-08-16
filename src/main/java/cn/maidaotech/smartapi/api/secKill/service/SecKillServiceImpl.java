package cn.maidaotech.smartapi.api.secKill.service;

import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillErrors;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillQo;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillSpec;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillWrapOption;
import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import cn.maidaotech.smartapi.api.secKill.repository.SecKillRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
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
public class SecKillServiceImpl implements SecKillService, SecKillErrors {

    @Autowired
    private ProductService productService;

    @Autowired
    private SecKillRepository secKillRepository;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, SecKill> secKillCache;

    @PostConstruct
    public void init() {
        secKillCache = kvCacheFactory.create(new CacheOptions("secKill", 1, 3600), new RepositoryProvider<Integer, SecKill>() {

            @Override
            public SecKill findByKey(Integer id) throws Exception {
                return secKillRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, SecKill> findByKeys(Collection<Integer> ids) throws Exception {
                List<SecKill> types = secKillRepository.findAllById(ids);
                return types.stream().collect(Collectors.toMap(SecKill::getId, it -> it));
            }
        }, new BeanModelConverter<>(SecKill.class));
    }

    @Override
    public void save(SecKill secKill) throws Exception {
        Long now = System.currentTimeMillis();
        if (Objects.isNull(secKill.getId()) || secKill.getId() == 0) {
            if (Objects.isNull(secKill.getProductId())) {
                throw new ServiceException(ERR_SEC_KILL_PRODUCT_ID_NOT_NULL);
            }
            if (Objects.isNull(secKill.getStartAt()) || now > secKill.getStartAt()) {
                throw new ServiceException(ERR_SEC_KILL_CREATED_AT_ERROR);
            }
            if (Objects.isNull(secKill.getEndAt()) || now > secKill.getEndAt() || secKill.getStartAt() > secKill.getEndAt()) {
                throw new ServiceException(ERR_SEC_KILL_CREATED_AT_ERROR);
            }
            if (CollectionUtils.isEmpty(secKill.getSecKillSpec())) {
                throw new ServiceException(ERR_SEC_KILL_PRODUCT_SNO_NOT_NULL);
            }
            secKill.setStatus(Constants.STATUS_HALT);
            secKillRepository.save(secKill);
        } else {
            SecKill exist = writeable(secKill.getId());
            exist.setSecKillSpec(secKill.getSecKillSpec());
            exist.setStartAt(secKill.getStartAt());
            exist.setEndAt(secKill.getEndAt());
            exist.setTitle(secKill.getTitle());
            secKillRepository.save(exist);
            secKillCache.removeSafely(secKill.getId());
        }
    }

    @Override
    public SecKill secKill(Integer id, SecKillWrapOption option) throws Exception {
        SecKill secKill = getById(id);
        if (option.isForUsr()) {
            if (secKill.getStatus() == Constants.STATUS_HALT) {
                throw new ServiceException(ERR_SEC_KILL_PRODUCT_SOLD_OUT);
            }
        }
        if (option.isWithProduct()) {
            secKill.setProduct(productService.product(secKill.getProductId()));
        }
        return secKill;
    }


    @Override
    public Page<SecKill> secKillsForMch(SecKillQo qo) throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        qo.setMerchantId(merchantId);
        Page<SecKill> secKillPage = secKillRepository.findAll(qo);
        List<SecKill> secKillList = secKillPage.getContent();
        if (CollectionUtils.isNotEmpty(secKillList)) {
            wrap(secKillList, SecKillWrapOption.getMchList());
        }
        return secKillPage;
    }

    @Override
    public Page<SecKill> secKillsForUsr(SecKillQo qo) throws Exception {
        qo.setStatus(Constants.STATUS_OK);
        Page<SecKill> secKillPage = secKillRepository.findAll(qo);
        List<SecKill> secKillList = secKillPage.getContent();
        if (CollectionUtils.isNotEmpty(secKillList)) {
            wrap(secKillList, SecKillWrapOption.getUsrList());
        }
        return secKillPage;
    }

    @Override
    public Page<SecKill> secKillsForAdm(SecKillQo qo) throws Exception {
        Page<SecKill> secKillPage = secKillRepository.findAll(qo);
        List<SecKill> secKillList = secKillPage.getContent();
        if (CollectionUtils.isNotEmpty(secKillList)) {
            wrap(secKillList, SecKillWrapOption.getAdmList());
        }
        return secKillPage;
    }

    @Override
    public void changeStatus(Integer id) throws Exception {
        SecKill secKill = getById(id);
        if (secKill.getStatus() == Constants.STATUS_HALT) {
            secKill.setStatus(Constants.STATUS_OK);
        } else if (secKill.getStatus() == Constants.STATUS_OK) {
            secKill.setStatus(Constants.STATUS_HALT);
        }
        secKillRepository.save(secKill);
        secKillCache.removeSafely(id);
    }

    private SecKill findById(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        SecKill secKill = secKillCache.findByKey(id);
        return secKill;
    }

    private SecKill getById(Integer id) throws Exception {
        SecKill secKill = findById(id);
        if (Objects.isNull(secKill)) {
            throw new DataNotFoundServiceException();
        }
        return secKill;
    }

    private SecKill writeable(Integer id) throws Exception {
        Long now = System.currentTimeMillis();
        SecKill secKill = getById(id);
        if (secKill.getStartAt() > now || secKill.getEndAt() > now) {
            throw new ServiceException(ERR_SEC_KILL_ALREADY_START_OR_END);
        }
        return secKill;
    }

    private void wrap(Collection<SecKill> secKills, SecKillWrapOption option) throws Exception {
        int size = secKills.size();
        Set<Integer> ids = secKills.stream().map(SecKill::getProductId).collect(Collectors.toSet());
        Map<Integer, Product> productMap = new HashMap<>(size);
        if (option.isWithProduct()) {
            List<Product> productList = productService.findByIdIn(ids);
            productMap = productList.stream().collect(Collectors.toMap(Product::getId, it -> it));
        }
        for (SecKill secKill : secKills) {
            if (option.isWithProduct()) {
                Product product = productMap.get(secKill.getProductId());
                secKill.setProduct(product);
            }
        }
    }

    @Override
    public Integer num(Integer id, String sno) throws Exception {
        SecKill secKill = secKillCache.findByKey(id);
        Integer secKillNum = 0;
        if (secKill != null) {
            for (SecKillSpec secKillSpec : secKill.getSecKillSpec()) {
                if (secKillSpec.getSno().equals(sno)) {
                    secKillNum = secKillSpec.getNum();
                    break;
                }
            }
        } else {
            throw new ArgumentServiceException("活动不存在");
        }
        return secKillNum;
    }

    @Override
    public void modifyStock(Integer id, String sno) throws Exception {
        SecKill secKill = secKillCache.findByKey(id);
        if (secKill != null) {
            for (SecKillSpec secKillSpec : secKill.getSecKillSpec()) {
                if (secKillSpec.getSno().equals(sno)) {
                    secKillSpec.setNum(secKillSpec.getNum() - 1);
                }
            }
        }
        assert secKill != null;
        secKillRepository.save(secKill);
        secKillCache.remove(id);
    }

    @Override
    public List<SecKill> findByIdIn(Collection<Integer> ids) throws Exception {
        return secKillRepository.findByIdIn(ids);
    }

    @Override
    public SecKill secKillByProductId(Integer id) throws Exception {

        List<SecKill> secKillList = secKillRepository.findByProductIdAndEndAtAfterOrderByEndAtDesc(id, System.currentTimeMillis());
        wrap(secKillList, SecKillWrapOption.USR_LIST);
        if (CollectionUtils.isNotEmpty(secKillList)) {
            List<SecKill> newSec = secKillList.stream().filter(item -> item.getSecKillSpec().get(0).getNum() > 0).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(newSec)) {
                return newSec.get(0);
            } else {
                return new SecKill();
            }
        } else {
            return new SecKill();
        }

    }
}
