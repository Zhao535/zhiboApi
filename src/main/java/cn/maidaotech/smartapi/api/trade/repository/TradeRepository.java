package cn.maidaotech.smartapi.api.trade.repository;

import cn.maidaotech.smartapi.api.trade.model.Trade;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.Collection;
import java.util.List;

public interface TradeRepository  extends BaseRepository<Trade,Integer> {

    List<Trade> findByIdIn(Collection<Integer> ids);

    List<Trade> findAllByTypeEqualsAndCreatedAtBefore(byte value, Long checkTime);

    Trade findByOrderNumber(String orderNumber);

    List<Trade> findAllByType(Byte type);
}
