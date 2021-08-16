package cn.maidaotech.smartapi.api.merchantAdmin.repository;

import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminSession;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

public interface MerchantAdminSessionRepository extends BaseRepository<MerchantAdminSession, Long> {

    MerchantAdminSession findByToken (String token);


}
