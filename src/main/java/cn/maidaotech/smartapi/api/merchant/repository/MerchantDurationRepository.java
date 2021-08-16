package cn.maidaotech.smartapi.api.merchant.repository;


import cn.maidaotech.smartapi.api.merchant.model.MerchantDuration;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface MerchantDurationRepository extends BaseRepository<MerchantDuration, Long> {

    List<MerchantDuration> findAllByMerchantIdIn(Collection<Integer> merchantIds);

    MerchantDuration findByMerchantId(Integer merchantId);
}
