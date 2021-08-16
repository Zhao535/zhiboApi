package cn.maidaotech.smartapi.api.account.repository;

import cn.maidaotech.smartapi.api.account.model.Account;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.List;

public interface AccountRepository extends BaseRepository<Account, Integer> {

    List<Account> findAllByMerchantIdAndStatus(Integer merchantId, Byte status);

    Account findByTypeAndStatus(Byte type, Byte status);

}
