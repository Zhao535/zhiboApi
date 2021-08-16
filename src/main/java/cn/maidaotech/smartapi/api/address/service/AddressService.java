package cn.maidaotech.smartapi.api.address.service;

import cn.maidaotech.smartapi.api.address.model.Address;
import cn.maidaotech.smartapi.api.address.qo.AddressQo;

import java.util.List;

public interface AddressService {
    void save(Address address) throws Exception;

    Address item(Integer id) throws Exception;

    void remove(Integer id) throws Exception;

    List<Address> items() throws Exception;
}
