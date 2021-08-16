package cn.maidaotech.smartapi.api.merchant.service;

import cn.maidaotech.smartapi.api.category.repository.ProductCategoriesRepository;
import cn.maidaotech.smartapi.api.merchant.entity.*;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchant.model.MerchantDuration;
import cn.maidaotech.smartapi.api.merchant.repository.MerchantDurationRepository;
import cn.maidaotech.smartapi.api.merchant.repository.MerchantRepository;
import cn.maidaotech.smartapi.api.merchant.repository.MerchantTradeRepository;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.merchantAdmin.repository.MerchantAdminRepository;
import cn.maidaotech.smartapi.api.merchantAdmin.service.MerchantAdminService;
import cn.maidaotech.smartapi.api.product.entity.ProductQo;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.api.trade.entity.TradeQo;
import cn.maidaotech.smartapi.api.trade.model.Trade;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.DateUtils;
import cn.maidaotech.smartapi.common.utils.MobileUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MerchantServiceImpl implements MerchantService, MerchantErrors {
    @Value("{$merchantAdmin.salt}")
    private String salt;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private MerchantAdminRepository merchantAdminRepository;
    @Autowired
    private MerchantAdminService merchantAdminService;
    @Autowired
    private MerchantDurationRepository merchantDurationRepository;
    @Autowired
    private ProductCategoriesRepository productCategoriesRepository;
    @Autowired
    private MerchantTradeRepository tradeRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Merchant> merchantCache;

    @PostConstruct
    public void init() {
        merchantCache = kvCacheFactory.create(new CacheOptions("merchant", 1, 3600), new RepositoryProvider<Integer, Merchant>() {

            @Override
            public Merchant findByKey(Integer id) throws Exception {
                return merchantRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, Merchant> findByKeys(Collection<Integer> ids) throws Exception {
                List<Merchant> admins = merchantRepository.findAllById(ids);
                return admins.stream().collect(Collectors.toMap(Merchant::getId, it -> it));
            }
        }, new BeanModelConverter<>(Merchant.class));
    }

    @Override
    public Merchant findById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        return merchantCache.findByKey(id);
    }

    private Merchant getById(Integer id) throws Exception {
        Merchant merchant = findById(id);
        if (merchant == null) {
            throw new DataNotFoundServiceException();
        }
        return merchant;
    }

    private void wrap(Collection<Merchant> merchants, MerchantWrapOption option) throws Exception {
        int size = merchants.size();
        Set<Integer> ids = merchants.stream().map(Merchant::getId).collect(Collectors.toSet());

        if (option.isWithMerchantProductNum()) {
            Set<Integer> merchantIds = merchants.stream().map(Merchant::getId).collect(Collectors.toSet());
            Map<Integer, Integer> map = productService.getProductNumByMerchantIds(merchantIds);
            for (Merchant merchant : merchants) {
                merchant.setProductNum(map.get(merchant.getId()));
            }
        }
    }

    public void merchantDuration(Merchant merchant, List<MerchantDuration> list) {
        list = list.stream().sorted(Comparator.comparing(MerchantDuration::getId).reversed()).collect(Collectors.toList());
        MerchantDuration duration = list.stream().findFirst().orElse(null);
        if (Objects.nonNull(duration)) {
            merchant.setExpiredAt(duration.getExpiredAt());
        }
    }

    private void wrap(Merchant merchant, MerchantWrapOption option) throws Exception {
        if (option.isWithMerchantExpiredAt()) {
            List<MerchantDuration> duration = merchantDurationRepository.findAllByMerchantIdIn(Arrays.asList(merchant.getId()));
            merchantDuration(merchant, duration);
        }
    }

    private void warpMerchantAdmin(Merchant merchant, MerchantWrapOption option) throws Exception {
        List<MerchantAdmin> merchantAdmins = merchantAdminService.findByMerchantId(merchant.getId());
        merchant.setMerchantAdmin(merchantAdmins);
    }


    private Merchant updatable(MerchantAdmin merchantAdmin) throws Exception {
        Merchant merchant = getById(merchantAdmin.getMerchantId());
        List<MerchantAdmin> merchantAdmins = merchantAdminRepository.findAllByMerchantId(merchantAdmin.getMerchantId());
        for (MerchantAdmin relMerchantAdmin : merchantAdmins) {
            if (relMerchantAdmin == merchantAdmin) {
                return merchant;
            }
        }
        return null;
    }

    private void filterMerchant(Merchant merchant) throws Exception {
        if (StringUtils.isEmpty(merchant.getName())) {
            throw new ServiceException(ERR_MERCHANT_NAME_EMPTY);
        }

        if (StringUtils.isNull(merchant.getSequences())) {
            throw new ServiceException(ERR_MERCHANT_DURATION_NOT_NULL);
        }
        if (StringUtils.isNull(merchant.getDuration())) {
            throw new ServiceException(ERR_MERCHANT_DURATION_NOT_NULL);
        }
        String mobile = MobileUtils.formatMobile(merchant.getMobile());
        if (mobile == null) {
            throw new ServiceException(ERR_MERCHANT_MOBILE_INVALID);
        }
        merchant.setMobile(mobile);
    }

    @Override
    @Transactional
    public void save(Merchant merchant, MerchantAdmin merchantAdmin) throws Exception {
        long now = System.currentTimeMillis();
        if (StringUtils.isNull(merchant.getId()) || merchant.getId() == 0) {

            merchant.setStatus(MerchantStatus.FORBID.value());    //不要直接上架
            merchant.setCreatedAt(now);
            merchant = merchantRepository.save(merchant);

            Duration duration = Duration.parse(merchant.getDuration());
            Long expiredAt = DateUtils.duration2milli(duration);
            if (Objects.isNull(expiredAt)){
                throw new ArgumentServiceException("duration");
            }
            MerchantDuration merchantDuration = new MerchantDuration();
            merchantDuration.setCreatedAt(now);
            merchantDuration.setExpiredAt(now + expiredAt);
            merchantDuration.setMerchantId(merchant.getId());

            merchantAdmin.setMerchantId(merchant.getId());
            merchantAdmin.setCreatedAt(now);
            merchantAdminService.saveMerchantAdmin(merchantAdmin);

            merchantDurationRepository.save(merchantDuration);

        } else {
//TODO            merchantAdmin=MerchantAdminContexts.requestMerchantAdmin();
            Merchant exist = getById(merchant.getId());
            exist.setMobile(merchant.getMobile());
            exist.setSequences(merchant.getSequences());
            exist.setName(merchant.getName());
            exist.setImgs(merchant.getImgs());
            exist.setLogo(merchant.getLogo());
            exist.setLastModify(now);
            merchantRepository.save(exist);
            merchantCache.remove(exist.getId());
        }
        //TODO 扩充adminType  商户名也不可以直接修改
    }

    @Override
    public void updateStatus(Integer id) throws Exception {
        Merchant merchant = getById(id);
        if (merchant.getStatus().equals(MerchantStatus.NORMAL.value())) {
            merchant.setStatus(MerchantStatus.FORBID.value());
        } else {
            merchant.setStatus(MerchantStatus.NORMAL.value());
        }
        merchant.setLastModify(System.currentTimeMillis());
        merchantRepository.save(merchant);
        merchantCache.removeSafely(id);
        // 每次更新数据之后都需要清除缓存
        //TODO 商品下架
    }


    @Override
    public Page<Merchant> items(MerchantQo qo, MerchantWrapOption option) throws Exception {
        Page<Merchant> merchants = merchantRepository.findAll(qo);
        wrap(merchants.getContent(), option);
        return merchants;
    }

    @Override
    public Page<Merchant> itemsForUsr(MerchantQo qo, MerchantWrapOption option) throws Exception {
        qo.setStatus(Constants.STATUS_OK);
        Page<Merchant> merchants = merchantRepository.findAll(qo);
        wrap(merchants.getContent(), option);
        return merchants;
    }

    @Override
    public Merchant item(Integer id, MerchantWrapOption option) throws Exception {
        Merchant merchant = getById(id);
        warpMerchantAdmin(merchant, option);
        wrap(merchant, option);
        return merchant;
    }

    @Override
    public void merchantDuration(Integer merchantId, String duration) throws Exception {
        List<MerchantDuration> durations = merchantDurationRepository.findAllByMerchantIdIn(Arrays.asList(merchantId));
        MerchantDuration merchantDuration = durations.stream().sorted(Comparator.comparing(MerchantDuration::getId).reversed()).findFirst().orElse(null);
        Long expiredAt = merchantDuration.getExpiredAt() + DateUtils.duration2milli(Duration.parse(duration));
        merchantDuration.setExpiredAt(expiredAt);
        merchantDurationRepository.save(merchantDuration);
    }

    @Override
    public Page<Trade> trades(TradeQo qo) throws Exception {
        return tradeRepository.findAll(qo);
    }



    @Override
    public Page<Product> products(ProductQo qo) throws Exception {
        qo.setStatus(Constants.STATUS_OK);
        return productService.itemsForUsr(qo);
    }

    @Override
    public List<Merchant> findByIdIn(Collection<Integer> ids) throws Exception {
        return merchantRepository.findAllByStatusAndIdIn(Constants.STATUS_OK, ids);
    }


    @Override
    @Transactional
    public void renewal(Integer id, String duration) throws Exception {
        MerchantDuration merchantDuration=merchantDurationRepository.findByMerchantId(id);
        if (Objects.isNull(duration)) {
            throw new ArgumentServiceException("duration");
        }
        Long durationMills = DateUtils.duration2milli(Duration.parse(duration));
        if (Objects.isNull(durationMills)) {
            throw new ArgumentServiceException("duration");
        }
        Long vipTime = merchantDuration.getExpiredAt();
        Long now = System.currentTimeMillis();
        merchantDuration.setExpiredAt(vipTime > now ? vipTime + durationMills : now + durationMills);
        merchantDurationRepository.save(merchantDuration);
    }
}

