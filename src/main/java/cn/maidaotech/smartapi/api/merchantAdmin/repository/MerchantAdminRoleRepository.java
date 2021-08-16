package cn.maidaotech.smartapi.api.merchantAdmin.repository;

import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminRole;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.List;

public interface MerchantAdminRoleRepository extends BaseRepository<MerchantAdminRole,Integer> {

    List<MerchantAdminRole> findByMerchantId(Integer storeId);
}
