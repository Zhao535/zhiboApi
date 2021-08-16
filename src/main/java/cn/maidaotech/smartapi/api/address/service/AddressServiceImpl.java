package cn.maidaotech.smartapi.api.address.service;


import cn.maidaotech.smartapi.api.address.model.Address;
import cn.maidaotech.smartapi.api.address.qo.AddressQo;
import cn.maidaotech.smartapi.api.address.repository.AddressRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Address> addressCache;

    @PostConstruct
    public void init() {
        addressCache = kvCacheFactory.create(new CacheOptions("address", 1, 3600),
                new RepositoryProvider<Integer, Address>() {

                    @Override
                    public Address findByKey(Integer id) throws ServiceException {
                        return addressRepository.findById(id).orElse(null);
                    }

                    @Override
                    public Map<Integer, Address> findByKeys(Collection<Integer> ids) throws
                            ServiceException {
                        throw new UnsupportedOperationException("findByKeys");
                    }

                }, new BeanModelConverter<>(Address.class));

    }


    @Override
    @Transactional
    public void save(Address address) throws Exception {
        if (StringUtils.isNull(address.getCode())) {
            throw new ArgumentServiceException("输入地址码为空");
        }
        if (StringUtils.isNull(address.getDetail())) {
            throw new ArgumentServiceException("输入地址为空");
        }
        if (StringUtils.isNull(address.getMobile())) {
            throw new ArgumentServiceException("输入手机号为空");
        }
        if (StringUtils.isNull(address.getName())) {
            throw new ArgumentServiceException("输入名字为空");
        }
        Integer userId = UserContexts.requestUserId();
        if (address.getId() == null || address.getId() == 0) {
            address.setUserId(userId);
            address.setCreatedAt(System.currentTimeMillis());
            address.setUpdateAt(System.currentTimeMillis());
            if (address.getIsDefault() == 1) {
                Address address1 = addressRepository.findAllByIsDefaultAndUserId((byte) 1, userId);
                if (address1 != null) {
                    address1.setIsDefault((byte) 2);
                    addressRepository.save(address1);
                }
            }
            addressRepository.save(address);
        } else {
            Address exist = addressCache.findByKey(address.getId());
            if (exist != null) {
                exist.setCode(address.getCode());
                exist.setDetail(address.getDetail());
                exist.setMobile(address.getMobile());
                exist.setName(address.getName());
                exist.setUpdateAt(System.currentTimeMillis());
                exist.setIsDefault(address.getIsDefault());
                if (address.getIsDefault() == 1) {
                    Address address1 = addressRepository.findAllByIsDefaultAndUserId((byte) 1, userId);
                    if (address1 != null) {
                        address1.setIsDefault((byte) 2);
                        addressRepository.save(address1);
                    }
                }
                exist.setIsDefault(address.getIsDefault());
                addressRepository.save(exist);
                addressCache.remove(address.getId());
            }
        }
    }


    @Override
    public List<Address> items() throws Exception {
        Integer userId = UserContexts.requestUserId();
        return addressRepository.findAllByUserId(userId);
    }

    @Override
    public Address item(Integer id) throws Exception {
        return addressCache.findByKey(id);
    }

    @Override
    public void remove(Integer id) throws Exception {
        addressRepository.deleteById(id);
        addressCache.remove(id);
    }
}
