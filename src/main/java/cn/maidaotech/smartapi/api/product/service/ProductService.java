package cn.maidaotech.smartapi.api.product.service;

import cn.maidaotech.smartapi.api.product.entity.ProductQo;
import cn.maidaotech.smartapi.api.product.model.Product;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductService {

    void save(Product product) throws Exception;

    Page<Product> items(ProductQo qo) throws Exception;

    List<Product> findAllByIds(Collection<Integer> ids) throws Exception;


    Product product(Integer id) throws Exception;

    Page<Product> itemsForAdmin(ProductQo qo) throws Exception;

    Page<Product> itemsForUsr(ProductQo qo) throws Exception;

    void changeStatus(Integer id) throws Exception;

    List<Product> findByIdIn(Collection<Integer> ids) throws Exception;

    Map<Integer,Integer> getProductNumByMerchantIds(Collection<Integer> ids)throws Exception;
}
