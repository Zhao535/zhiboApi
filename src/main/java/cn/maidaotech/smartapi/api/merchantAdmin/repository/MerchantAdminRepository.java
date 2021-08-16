package cn.maidaotech.smartapi.api.merchantAdmin.repository;

import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.List;

public interface MerchantAdminRepository extends BaseRepository<MerchantAdmin,Integer> {

    List<MerchantAdmin> findAllByMerchantId (Integer merchantId);

    MerchantAdmin findByMobile (String mobile);

    MerchantAdmin findByMobileAndMerchantId(String mobile,Integer merchantId);


}
