package cn.maidaotech.smartapi.api.category.service;


import cn.maidaotech.smartapi.api.merchant.entity.MerchantErrors;
import cn.maidaotech.smartapi.api.merchant.entity.MerchantWrapOption;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.category.entity.CategoryStatus;
import cn.maidaotech.smartapi.api.category.model.ProductCategory;
import cn.maidaotech.smartapi.api.category.repository.ProductCategoriesRepository;
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
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductCategoryServiceImpl implements ProductCategoryService, MerchantErrors {

    @Autowired
    private ProductCategoriesRepository productCategoriesRepository;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, ProductCategory> categoryCache;

    @PostConstruct
    public void init() {
        categoryCache = kvCacheFactory.create(new CacheOptions("category", 1, 3600), new RepositoryProvider<Integer, ProductCategory>() {

            @Override
            public ProductCategory findByKey(Integer id) throws Exception {
                return productCategoriesRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, ProductCategory> findByKeys(Collection<Integer> ids) throws Exception {
                List<ProductCategory> admins = productCategoriesRepository.findAllById(ids);
                return admins.stream().collect(Collectors.toMap(ProductCategory::getId, it -> it));
            }

            public List<ProductCategory> findAll() throws Exception {
                List categoriesList = productCategoriesRepository.findAll();
                return categoriesList;
            }


        }, new BeanModelConverter<>(ProductCategory.class));
    }

    @Override
    @Transactional
    public void saveProductCategory(ProductCategory categories) throws Exception {
        if (categories.getId() == null || categories.getId() == 0) {
            if (Objects.isNull(categories)) {
                throw new ServiceException(ERR_CATEGORIES_NOT_NULL);
            }
            if (StringUtils.isNull(categories.getName())) {
                throw new ServiceException(ERR_CATEGORIES_NAME_NOT_NULL);
            }
            if (StringUtils.isNull(categories.getSequence())) {
                throw new ServiceException(ERR_CATEGORIES_NAME_NOT_NULL);
            }
            categories.setStatus((byte) 2);
        } else {
            ProductCategory category = productCategoriesRepository.findById(categories.getId()).orElse(null);
            category.setName(categories.getName());
            category.setIcon(categories.getIcon());
            category.setPriority(categories.getPriority());
            categories = category;
        }

        productCategoriesRepository.save(categories);

    }

    private Map<Integer, List<ProductCategory>> getCategoriesMap(boolean all) throws Exception {
        Map<Integer, List<ProductCategory>> categoriesMap = new HashMap<>();
        if (all) {
            List<ProductCategory> categoriesList = productCategoriesRepository.findAll();
            categoriesMap = categoriesList.stream().collect(Collectors.groupingBy(ProductCategory::getParentId));

        } else {
            List<ProductCategory> categoriesList = productCategoriesRepository.findAllByStatus(Constants.STATUS_OK);
            categoriesMap = categoriesList.stream().collect(Collectors.groupingBy(ProductCategory::getParentId));
        }
        return categoriesMap;
    }

    private List<ProductCategory> allCategories(boolean all) throws Exception {
        Map<Integer, List<ProductCategory>> categoriesMap = getCategoriesMap(all);
        if (categoriesMap.size() == 0) {
            return Collections.emptyList();
        }
        List<ProductCategory> grands = categoriesMap.get(0);
        if (CollectionUtils.isNotEmpty(grands)) {
            for (ProductCategory grand : grands) {
                List<ProductCategory> parents = categoriesMap.get(grand.getId());
                if (CollectionUtils.isNotEmpty(parents)) {
                    for (ProductCategory parent : parents) {
                        parent.setChildren(categoriesMap.get(parent.getId()));
                    }
                }
                grand.setChildren(parents);
            }
        }
        return grands;
    }

    @Override
    public List<ProductCategory> findAllCategories() throws Exception {
        return allCategories(true);
    }

    @Override
    public List<ProductCategory> findAllCategoriesForUsr() throws Exception {
        return allCategories(false);
    }

    @Override
    public void changeCategoryStatus(Integer categoryId, Integer status) throws Exception {
        CategoryStatus categoryStatus = CategoryStatus.find(status);
        Map<Integer, List<ProductCategory>> categoriesMap = getCategoriesMap(true);//全部数据，groupBy parentId
        ProductCategory category = productCategoriesRepository.findById(categoryId).orElse(null);
        //拿到这个分类 catgory
        if (category.getParentId() == 0) { //如果这个分类的 parentId是0 这条分类是一级分类 需要修改下面所以二级三级的状态
            List<ProductCategory> list = categoriesMap.get(categoryId);
            if (CollectionUtils.isNotEmpty(list)) {
                //拿到一级分类下的所以二级分类
                for (ProductCategory categoryParent : list) {
                    if (CollectionUtils.isNotEmpty(categoriesMap.get(categoryParent.getId()))) {
                        for (ProductCategory productCategory : categoriesMap.get(categoryParent.getId())) {
                            productCategory.setStatus(categoryStatus.value());
                        }
                    }
                    categoryParent.setStatus(categoryStatus.value());
                }
            }
            category.setStatus(categoryStatus.value());
            if (CollectionUtils.isNotEmpty(list)) {
                productCategoriesRepository.saveAll(list);
            } else {
                productCategoriesRepository.save(category);
            }
        } else {//否则 在判读是二级分类还是三级分类
            ProductCategory categoryParent = productCategoriesRepository.findById(category.getParentId()).orElse(null);
            //拿到这个分类的父级
            if (categoryParent.getParentId() == 0) {//如果这个分类的父级的parentId=0 category是二级分类 他的父类categoryParent
                List<ProductCategory> categorys = categoriesMap.get(categoryId);
                //拿到二级分类下面的所以三级分类
                if (CollectionUtils.isNotEmpty(categorys)) {
                    for (ProductCategory productCategory : categorys) {
                        productCategory.setStatus(categoryStatus.value());
                    }
                }
                category.setStatus(categoryStatus.value());
                if (CollectionUtils.isNotEmpty(categorys)) {
                    productCategoriesRepository.saveAll(categorys);
                } else {
                    productCategoriesRepository.save(category);
                }
            } else {
                category.setStatus(categoryStatus.value());
                productCategoriesRepository.save(category);
            }
        }
    }

    private ProductCategory findById(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        ProductCategory category = categoryCache.findByKey(id);
        return category;
    }

    private ProductCategory getById(Integer id) throws Exception {
        ProductCategory category = findById(id);
        if (Objects.isNull(category)) {
            throw new DataNotFoundServiceException();
        }
        return category;
    }

    @Override
    public List<Integer> convert(List<String> sequences) throws Exception {
        if (CollectionUtils.isEmpty(sequences)) {
            return null;
        }
        List<Integer> ids = new ArrayList<>();
        List<ProductCategory> categories = findCategories();
        if (CollectionUtils.isEmpty(categories)) {
            return null;
        }
        for (String sequence : sequences) {
            if (CollectionUtils.isNotEmpty(categories)) {
                for (ProductCategory category : categories) {
                    List<ProductCategory> childs = category.getChildren();
                    if (CollectionUtils.isNotEmpty(childs)) {
                        for (ProductCategory child : childs) {
                            List<ProductCategory> childrens = child.getChildren();
                            if (CollectionUtils.isNotEmpty(childrens)) {
                                for (ProductCategory children : childrens) {
                                    if (sequence.equals(category.getSequence())) {
                                        ids.add(category.getId());
                                    } else if (sequence.equals(child.getSequence())) {
                                        ids.add(child.getId());
                                    } else if (sequence.equals(children.getSequence())) {
                                        ids.add(children.getId());
                                    }
                                }
                            } else {
                                if (child.getSequence().equals(sequence)) {
                                    ids.add(child.getId());
                                }

                            }

                        }

                    } else {
                        if (category.getSequence().equals(sequence)) {
                            ids.add(category.getId());
                        }
                    }


                }

            }

        }
        return ids;
    }

    private List<ProductCategory> findCategories() throws Exception {
        Map<Integer, List<ProductCategory>> categoriesMap = getCategoriesMap(true);
        List<ProductCategory> grands = categoriesMap.get(0);
        if (CollectionUtils.isNotEmpty(grands)) {
            for (ProductCategory grand : grands) {
                List<ProductCategory> parents = categoriesMap.get(grand.getId());
                if (CollectionUtils.isNotEmpty(parents)) {
                    for (ProductCategory parent : parents) {
                        parent.setChildren(categoriesMap.get(parent.getId()));
                    }
                }
                grand.setChildren(parents);
            }
        }
        return grands;
    }

    @Override
    public List<ProductCategory> merchantCategories() throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        List<Integer> ids = convert(merchantService.item(merchantId, MerchantWrapOption.getDefault()).getSequences());
        if (CollectionUtils.isEmpty(ids)) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        List<ProductCategory> merchantCategories = productCategoriesRepository.findAllById(ids);
        return merchantCategories;
    }

    @Override
    public List<ProductCategory> findAllByIdIn(Collection<Integer> ids) throws Exception {
        return productCategoriesRepository.findAllByIdIn(ids);
    }

    @Override
    public ProductCategory convertToSequence(Integer categoryId) throws Exception {
        ProductCategory category = productCategoriesRepository.findById(categoryId).orElse(null);
        if (Objects.isNull(category)) {
           return null;
        }
        return category;
    }
}
