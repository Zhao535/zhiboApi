package cn.maidaotech.smartapi.api.product.service;


import cn.maidaotech.smartapi.api.category.model.ProductCategory;
import cn.maidaotech.smartapi.api.category.service.ProductCategoryService;
import cn.maidaotech.smartapi.api.merchant.entity.MerchantWrapOption;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.product.entity.*;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.product.repository.ProductRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.commons.util.FormatUtil;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService, ProductErrors {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Product> productCache;

    @PostConstruct
    public void init() {
        productCache = kvCacheFactory.create(new CacheOptions("product", 1, 3600), new RepositoryProvider<Integer, Product>() {

            @Override
            public Product findByKey(Integer id) throws Exception {
                return productRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, Product> findByKeys(Collection<Integer> ids) throws Exception {
                List<Product> products = productRepository.findAllById(ids);
                return products.stream().collect(Collectors.toMap(Product::getId, it -> it));
            }
        }, new BeanModelConverter<>(Product.class));
    }

    private void checkParams(List<ProductParam> params) throws Exception {
        if (CollectionUtils.isEmpty(params)) {
            throw new ArgumentServiceException("产品参数不能为空");
        }
        for (int i = 0; i < params.size(); i++) {
            ProductParam param = params.get(i);
            if (StringUtils.isEmpty(param.getLabel())) {
                throw new ArgumentServiceException("参数名字不能为空");
            }
            if (StringUtils.isEmpty(param.getValue())) {
                throw new ArgumentServiceException("参数值不能为空");
            }
        }
    }


    private void checkSpecs(List<ProductSpec> specs) throws Exception {
        if (CollectionUtils.isEmpty(specs)) {
            throw new ArgumentServiceException("产品规格不能为空");
        }
        for (int i = 0; i < specs.size(); i++) {
            ProductSpec spec = specs.get(i);
            if (Objects.isNull(spec.getSno())) {
                throw new ArgumentServiceException("第" + i + "规格编号不能为空");
            }
            if (Objects.isNull(spec.getStock()) || spec.getStock() < 0) {
                throw new ArgumentServiceException("第" + i + "规格库存不能为空或者为负值");
            }
            if (spec.getImgs().size() < 1) {
                throw new ArgumentServiceException("第" + i + "规格产品照片不能少于一张");
            }
            if (Objects.isNull(spec.getPrice()) || spec.getPrice() <= 0) {
                throw new ArgumentServiceException("第" + i + "规格产品价格不能为空或者小于等于零");
            }
            checkParams(spec.getParams());
        }

    }

    private void changeAble(Product product) throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
//todo 商品品牌可以没有 应该可以没有
//        if (Objects.isNull(product.getBrandId())) {
//            throw new ServiceException(ERR_PRODUCT_BRAND_ID_NEEDED);
//        }
        if (Objects.isNull(product.getName())) {
            throw new ServiceException(ERR_PRODUCT_NAME_NEEDED);
        }
        if (Objects.isNull(product.getParams())) {
            throw new ServiceException(ERR_PRODUCT_PARAMS_NEEDED);
        }
        product.setStatus(ProductStatus.FORBID.value());
        List<ProductSpec> specs = product.getSpecs();
        checkSpecs(specs);
        Integer price = specs.stream().filter(spec -> spec.getPrice() != null).map(spec -> spec.getPrice()).min(Integer::compareTo).orElse(0);
        product.setPrice(price);
        product.setMerchantId(merchantId);
    }

    private Product findById(Integer id) throws ServiceException {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        return productCache.findByKey(id);
    }

    private Product getById(Integer id) throws ServiceException {
        Product product = findById(id);
        if (Objects.isNull(product)) {
            throw new DataNotFoundServiceException();
        }
        return product;
    }


    @Override
    public void save(Product product) throws Exception {
        changeAble(product);
        if (product.getId() == null || product.getId() == 0) {
            product.setCreatedAt(System.currentTimeMillis());
            productRepository.save(product);
        } else {
            Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
            if (!merchantId.equals(product.getMerchantId())) {
                throw new ServiceException(ERR_PERMISSION_DENIED);
            }
            Product exist = getById(product.getId());
            exist.setSpecs(product.getSpecs());
            exist.setName(product.getName());
            exist.setParams(product.getParams());
            exist.setContent(product.getContent());
            exist.setSequence(product.getSequence());
            exist.setPrice(product.getPrice());
            productRepository.save(exist);
            productCache.removeSafely(product.getId());
        }


    }

    @Override
    public Page<Product> itemsForAdmin(ProductQo qo) throws Exception {
        return productRepository.findAll(qo);
    }


    @Override
    public Page<Product> items(ProductQo qo) throws Exception {
        qo.setMerchantId(MerchantAdminContexts.requestMerchantAdmin().getMerchantId());
        return productRepository.findAll(qo);
    }

    @Override
    public Page<Product> itemsForUsr(ProductQo qo) throws Exception {
        Integer categoryId = qo.getCategoryId();
        List<String> sequenceList = new ArrayList<>();
        if (Objects.nonNull(categoryId)) {
            ProductCategory category = categoryService.convertToSequence(categoryId);
            if (Objects.nonNull(category) && CollectionUtils.isNotEmpty(category.getChildren())) {
                for (ProductCategory child : category.getChildren()) {
                    sequenceList.add(child.getSequence());
                }
                qo.setSequence(sequenceList);
            } else {
                qo.setCategoryId(0);
                sequenceList.add(category.getSequence());
                qo.setSequence(sequenceList);
                ;
            }
        }
        return productRepository.findAll(qo);
    }

    @Override
    public Product product(Integer id) throws Exception {
        Product product = findById(id);
        product.setMerchant(merchantService.item(product.getMerchantId(), MerchantWrapOption.getDefault()));
        return product;
    }

    @Override
    public void changeStatus(Integer id) throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        Product product = getById(id);
        if (merchantId == product.getMerchantId()) {
            if (product.getStatus() == Constants.STATUS_OK) {
                product.setStatus(Constants.STATUS_HALT);
            } else {
                product.setStatus(Constants.STATUS_OK);
            }
            productRepository.save(product);
            productCache.removeSafely(id);
        } else {
            throw new ServiceException(ERR_PERMISSION_DENIED);
        }
    }

    @Override
    public List<Product> findByIdIn(Collection<Integer> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return productRepository.findAllByIdIn(ids);
    }

    @Override
    public List<Product> findAllByIds(Collection<Integer> ids) throws Exception {
        return productRepository.findAllByIdIn(ids);
    }

    @Override
    public Map<Integer, Integer> getProductNumByMerchantIds(Collection<Integer> ids) throws Exception {
        List<Map<String, Object>> counts = productRepository.getProductNumByMerchant(ids);
        Map<Integer, Integer> result = new HashMap<>(ids.size());
        for (Map<String, Object> count : counts) {
            result.put(FormatUtil.parseIntValue(count.get("id"), 0), FormatUtil.parseIntValue(count.get("num"), 0));
        }
        return result;
    }

}
