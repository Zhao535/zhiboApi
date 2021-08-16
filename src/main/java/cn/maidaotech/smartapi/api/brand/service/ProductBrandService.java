package cn.maidaotech.smartapi.api.brand.service;

import cn.maidaotech.smartapi.api.brand.entity.BrandQo;
import cn.maidaotech.smartapi.api.brand.model.ProductBrand;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProductBrandService {

    void save(ProductBrand brand) throws Exception;

    Page<ProductBrand> items(BrandQo qo) throws Exception;

    ProductBrand item(Integer id) throws Exception;

    void changeStatus(Integer id) throws Exception;

    List<ProductBrand> merchantBrands (Integer merchantId) throws Exception;

    List<ProductBrand> allBrands() throws Exception;



}
