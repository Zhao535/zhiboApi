package cn.maidaotech.smartapi.api.category.repository;

import cn.maidaotech.smartapi.api.category.model.ProductCategory;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.Collection;
import java.util.List;

public interface ProductCategoriesRepository extends BaseRepository<ProductCategory, Integer> {

    List<ProductCategory> findAllByStatus(Byte status);

    List<ProductCategory> findAllByIdIn(Collection<Integer> ids) ;

}
