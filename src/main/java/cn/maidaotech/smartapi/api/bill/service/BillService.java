package cn.maidaotech.smartapi.api.bill.service;

import cn.maidaotech.smartapi.api.bill.entity.BillQo;
import cn.maidaotech.smartapi.api.bill.model.Bill;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillService {

    Bill item(Integer id) throws Exception;

    Page<Bill> items(BillQo qo) throws Exception;

    void check(Integer id, Byte status) throws Exception;

    void save(Bill bill) throws Exception;

    Bill checkPassed(Integer tradeId) throws Exception;

    Bill findByTradeId(Integer tradeId) throws Exception;

    List<Bill> itemsForUsr(BillQo qo) throws Exception;

}
