package cn.maidaotech.smartapi.api.category.service;

import cn.maidaotech.smartapi.api.category.model.ProductCategory;

import java.util.Collection;
import java.util.List;

public interface ProductCategoryService {

    void saveProductCategory(ProductCategory category) throws Exception;

    List<ProductCategory> findAllCategories() throws Exception;

    List<ProductCategory> findAllCategoriesForUsr() throws Exception;

    void changeCategoryStatus(Integer categoryId, Integer status) throws Exception;

    List<ProductCategory> merchantCategories() throws Exception;

    List<Integer> convert(List<String> sequences) throws Exception;

    List<ProductCategory> findAllByIdIn(Collection<Integer> ids) throws Exception;

    ProductCategory convertToSequence(Integer categoryId) throws Exception;


}
