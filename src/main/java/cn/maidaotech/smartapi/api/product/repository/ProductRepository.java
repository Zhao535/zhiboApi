package cn.maidaotech.smartapi.api.product.repository;

import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductRepository extends BaseRepository<Product,Integer> {

    List<Product> findAllByIdIn(Collection<Integer> ids);

    @Query(value = "select merchant_id as id,count(*) as num from product where `merchant_id` in :merchantIds GROUP BY merchant_id", nativeQuery = true)
    List<Map<String, Object>> getProductNumByMerchant(Collection<Integer> merchantIds);

}
