package cn.maidaotech.smartapi.api.merchant.repository;

import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.Collection;
import java.util.List;


public interface MerchantRepository extends BaseRepository<Merchant, Integer> {
    List<Merchant> findAllByStatusAndIdIn(Byte status,Collection<Integer> ids);









}
