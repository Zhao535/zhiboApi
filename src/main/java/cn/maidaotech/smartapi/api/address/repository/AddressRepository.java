package cn.maidaotech.smartapi.api.address.repository;


import cn.maidaotech.smartapi.api.address.model.Address;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.List;

public interface AddressRepository extends BaseRepository<Address, Integer> {
    Address findAllByIsDefaultAndUserId(Byte isDefault,Integer userId) throws Exception;

    List<Address> findAllByUserId(Integer userId) throws Exception;
}
