package cn.maidaotech.smartapi.api.trade.service;

import cn.maidaotech.smartapi.api.trade.entity.TradeQo;
import cn.maidaotech.smartapi.api.trade.entity.TradeWrapOption;
import cn.maidaotech.smartapi.api.trade.model.Trade;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface TradeService {
    void send(Integer id, Byte type) throws Exception;

    Integer save(Trade trade, Integer userCouponId) throws Exception;

    Trade item(Integer id) throws Exception;

    Trade warpUserFindById(Integer id) throws Exception;

    Trade findByOrderNumber(String orderNum) throws Exception;

    void delete(Integer id) throws Exception;

    Page<Trade> trades(TradeQo qo, TradeWrapOption option) throws Exception;

    Page<Trade> items(TradeQo qo, TradeWrapOption option) throws Exception;

    void batchRemove(List<Integer> ids) throws Exception;

    void updateType(Integer id, Byte type) throws Exception;

    void cancelTrade() throws Exception;

    List<Trade> findByIdIn(Collection<Integer> ids) throws Exception;

}
