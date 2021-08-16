package cn.maidaotech.smartapi.api.merchant.service;


import cn.maidaotech.smartapi.api.merchant.entity.*;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.product.entity.ProductQo;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.trade.entity.TradeQo;
import cn.maidaotech.smartapi.api.trade.model.Trade;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface MerchantService {

    void save(Merchant merchant, MerchantAdmin merchantAdmin) throws Exception;

    void updateStatus(Integer id) throws Exception;

    Page<Merchant> items(MerchantQo qo, MerchantWrapOption option) throws Exception;

    Page<Merchant> itemsForUsr(MerchantQo qo, MerchantWrapOption option) throws Exception;

    Merchant item(Integer id, MerchantWrapOption option) throws Exception;

    void merchantDuration(Integer merchantId, String duration) throws Exception;

    Page<Trade> trades(TradeQo qo) throws Exception;


    Page<Product> products(ProductQo qo) throws Exception;

//    Page<Bill> bills(Integer merchantId, TradeQo qo) throws Exception;

    List<Merchant> findByIdIn(Collection<Integer> ids) throws Exception;

    Merchant findById(Integer id)throws Exception;

    void renewal(Integer id, String duration) throws Exception;


}
