package cn.maidaotech.smartapi.api.brand.service;

import cn.maidaotech.smartapi.api.merchant.entity.MerchantWrapOption;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.brand.entity.BrandErrors;
import cn.maidaotech.smartapi.api.brand.entity.BrandQo;
import cn.maidaotech.smartapi.api.brand.entity.ProductBrandStatus;
import cn.maidaotech.smartapi.api.brand.model.ProductBrand;
import cn.maidaotech.smartapi.api.brand.repository.ProductBrandRepository;
import cn.maidaotech.smartapi.api.category.service.ProductCategoryService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductBrandServiceImpl implements ProductBrandService, BrandErrors {

    @Autowired
    private ProductBrandRepository brandsRepository;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, ProductBrand> brandCache;

    @PostConstruct
    public void init() {
        brandCache = kvCacheFactory.create(new CacheOptions("brands", 1, 3600), new RepositoryProvider<Integer, ProductBrand>() {

            @Override
            public ProductBrand findByKey(Integer id) throws Exception {
                return brandsRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, ProductBrand> findByKeys(Collection<Integer> ids) throws Exception {
                List<ProductBrand> admins = brandsRepository.findAllById(ids);
                return admins.stream().collect(Collectors.toMap(ProductBrand::getId, it -> it));
            }
        }, new BeanModelConverter<>(ProductBrand.class));
    }


    @Override
    public void save(ProductBrand brand) throws Exception {
        Long now = System.currentTimeMillis();
        if (brand.getId() == null || brand.getId() == 0) {
            if (brand.getName() == null) {
                throw new ServiceException(ERR_BRAND_NAME_NOT_NULL);
            }
            if (brand.getLogo() == null) {
                throw new ServiceException(ERR_BRAND_LOGO_NOT_NULL);
            }
            if (brand.getPriority() == null) {
                throw new ServiceException(ERR_BRAND_PRIORITY_NOT_NULL);
            }
            if (CollectionUtils.isEmpty(brand.getSequences())) {
                throw new ServiceException(ERR_BRAND_SEQUENCES_NOT_EMPTY);
            }
            brand.setCreatedAt(now);
            brand.setStatus(ProductBrandStatus.FORBID.value());
            brandsRepository.save(brand);
        } else {
            ProductBrand exist = getById(brand.getId());
            exist.setName(brand.getName());
            exist.setLogo(brand.getLogo());
            exist.setPriority(brand.getPriority());
            brandsRepository.save(exist);
            brandCache.removeSafely(brand.getId());
        }

    }


    private ProductBrand getById(Integer id) throws Exception {
        ProductBrand brand = findById(id);
        if (brand == null) {
            throw new DataNotFoundServiceException();
        }
        return brand;
    }

    private ProductBrand findById(Integer id) throws Exception {
        if (id == null) {
            throw new ArgumentServiceException("id");
        }
        ProductBrand brand = brandCache.findByKey(id);
        return brand;
    }

    @Override
    public Page<ProductBrand> items(BrandQo qo) throws Exception {
        return brandsRepository.findAll(qo);
    }

    @Override
    public ProductBrand item(Integer id) throws Exception {
        return getById(id);
    }

    @Override
    public void changeStatus(Integer id) throws Exception {
        ProductBrand brand = getById(id);
        if (brand.getStatus() == ProductBrandStatus.NORMAL.value()) {
            brand.setStatus(ProductBrandStatus.FORBID.value());
        } else {
            brand.setStatus(ProductBrandStatus.NORMAL.value());
        }
        brandsRepository.save(brand);
        brandCache.removeSafely(id);
    }

    @Override
    public List<ProductBrand> merchantBrands(Integer merchantId) throws Exception {
        List<String> sequences = merchantService.item(merchantId, MerchantWrapOption.getDefault()).getSequences();
        List<ProductBrand> brands = brandsRepository.findAll();
        List<ProductBrand> merchantBrands = new ArrayList<>();
        for (ProductBrand brand : brands) {
            for (String sequence : sequences) {
                if (sequence.endsWith("0000")) {
                    for (String brandSequence : brand.getSequences()) {
                        if (brandSequence.substring(0, 2).equals(sequence.substring(0, 2))) {
                            if (!merchantBrands.contains(brand)) {
                                merchantBrands.add(brand);
                            }

                        }
                    }
                }
                if ((sequence.substring(4) == "00") && (sequence.substring(2, 4) != "0")) {
                    for (String brandSequence : brand.getSequences()) {
                        if (brandSequence.substring(0, 4).equals(sequence.substring(0, 4))) {
                            if (!merchantBrands.contains(brand)) {
                                merchantBrands.add(brand);
                            }
                        }
                    }
                } else {
                    for (String brandSequence : brand.getSequences()) {
                        if (brandSequence.equals(sequence)) {
                            if (!merchantBrands.contains(brand)) {
                                merchantBrands.add(brand);
                            }
                        }
                    }
                }
            }
        }

        return merchantBrands;
    }

    @Override
    public List<ProductBrand> allBrands() throws Exception {
        List<ProductBrand> allBrands = brandsRepository.findAllByStatus(Constants.STATUS_OK);
        if (CollectionUtils.isEmpty(allBrands)) {
            throw new DataNotFoundServiceException();
        }
        return allBrands;
    }
}
