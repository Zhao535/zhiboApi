package cn.maidaotech.smartapi.api.brand.repository;

import cn.maidaotech.smartapi.api.brand.model.ProductBrand;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.List;

public interface ProductBrandRepository extends BaseRepository<ProductBrand,Integer> {
    List<ProductBrand> findAllByStatus(Byte status);
}
