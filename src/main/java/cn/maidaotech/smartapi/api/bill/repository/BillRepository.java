package cn.maidaotech.smartapi.api.bill.repository;

import cn.maidaotech.smartapi.api.bill.model.Bill;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

public interface BillRepository extends BaseRepository<Bill, Integer> {

    Bill findByTradeId(Integer tradeId);

}
