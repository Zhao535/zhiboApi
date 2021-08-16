package cn.maidaotech.smartapi.api.bill.service;


import cn.maidaotech.smartapi.api.bill.entity.BillErrors;
import cn.maidaotech.smartapi.api.bill.entity.BillQo;
import cn.maidaotech.smartapi.api.bill.entity.BillStatus;
import cn.maidaotech.smartapi.api.bill.entity.BillWrapOption;
import cn.maidaotech.smartapi.api.bill.model.Bill;
import cn.maidaotech.smartapi.api.bill.repository.BillRepository;
import cn.maidaotech.smartapi.api.brand.model.ProductBrand;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.trade.entity.TradeErrors;
import cn.maidaotech.smartapi.api.trade.entity.TradeType;
import cn.maidaotech.smartapi.api.trade.model.Trade;
import cn.maidaotech.smartapi.api.trade.service.TradeService;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.PermissionDeniedServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService, TradeErrors, BillErrors {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Bill> billCache;

    @PostConstruct
    public void init() {
        billCache = kvCacheFactory.create(new CacheOptions("bill", 1, 3600), new RepositoryProvider<Integer, Bill>() {

            @Override
            public Bill findByKey(Integer id) throws Exception {
                return billRepository.findById(id).orElse(null);
            }

            @Override
            public Map<Integer, Bill> findByKeys(Collection<Integer> ids) throws Exception {
                List<Bill> admins = billRepository.findAllById(ids);
                return admins.stream().collect(Collectors.toMap(Bill::getId, it -> it));
            }
        }, new BeanModelConverter<>(Bill.class));
    }

    @Override
    public Bill item(Integer id) throws Exception {
        return getById(id);
    }

    @Override
    public Page<Bill> items(BillQo qo) throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        qo.setMerchantId(merchantId);
        return billRepository.findAll(qo);
    }

    @Override
    public List<Bill> itemsForUsr(BillQo qo) throws Exception {
        qo.setStatus(BillStatus.PASSED.value());
        qo.setUserId(UserContexts.requestUserId());
        List<Bill> billList = billRepository.findAll(qo).getContent();
        warp(billList, BillWrapOption.DEFAULT);
        return billList;
    }

    @Override
    public void check(Integer id, Byte status) throws Exception {
        Bill bill = checkable(id);
        if (status == Constants.STATUS_OK) {
            bill.setStatus(Constants.STATUS_OK);
            waitSend(bill.getTradeId());
        }
        if (status == Constants.STATUS_HALT) {
            bill.setStatus(Constants.STATUS_HALT);
            tradeService.updateType(bill.getTradeId(), TradeType.NO_PAY.value());
        } else {
            throw new ArgumentServiceException("status");
        }
        billRepository.save(bill);
        billCache.removeSafely(id);
    }

    @Override
    public void save(Bill bill) throws Exception {
        if (bill.getId() == null || bill.getId() == 0) {
            if (Objects.isNull(bill.getTradeId())) {
                throw new ServiceException(ERR_BILL_TRADE_ID_NOT_NULL);
            }
            Trade trade = tradeService.item(bill.getTradeId());
            if (Objects.isNull(trade)) {
                throw new DataNotFoundServiceException();
            }
            if (trade.getType() != TradeType.NO_PAY.value()) {
                throw new ServiceException(ERR_TREAD_CANT_PAY);
            }
            if (Objects.isNull(bill.getMerchantId())) {
                throw new ServiceException(ERR_BILL_MERCHANT_ID_NOU_NULL);
            }
            if (Objects.isNull(bill.getUserId())) {
                throw new ServiceException(ERR_BILL_USER_ID_NOT_NULL);
            }
            if (Objects.isNull(bill.getAmount())) {
                throw new ServiceException(ERR_BILL_AMOUNT_NOT_NULL);
            }
            if (Objects.isNull(bill.getOrderNumber())) {
                throw new ServiceException(ERR_BILL_ORDER_NUMBER_NOT_NULL);
            }
            if (CollectionUtils.isEmpty(bill.getImgs())) {
                throw new ServiceException(ERR_BILL_IMGS_NOT_NULL);
            }
            Long now = System.currentTimeMillis();
            bill.setCreatedAt(now);
            bill.setStatus(BillStatus.WAITING.value());
            billRepository.save(bill);
            waitSend(bill.getTradeId());
        } else {
            Bill exist = billRepository.findById(bill.getId()).orElse(null);
            exist.setImgs(bill.getImgs());
            exist.setStatus(BillStatus.WAITING.value());
            billRepository.save(exist);
            waitSend(exist.getTradeId());
            billCache.removeSafely(bill.getId());
        }
    }

    public Bill findById(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        Bill bill = billCache.findByKey(id);
        return bill;
    }

    public Bill getById(Integer id) throws Exception {
        Bill bill = findById(id);
        if (Objects.isNull(bill)) {
            throw new DataNotFoundServiceException();
        }
        return bill;
    }

    private Bill checkable(Integer id) throws Exception {
        Bill bill = getById(id);
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        if (bill.getMerchantId().equals(merchantId)) {
            return bill;
        }
        return null;
    }

    private Bill writeable(Integer id) throws Exception {
        Bill bill = getById(id);
        Integer userId = UserContexts.requestUserId();
        if (userId == bill.getUserId()) {
            return bill;
        }
        return null;
    }

    @Override
    public Bill checkPassed(Integer tradeId) throws Exception {
        Bill bill = billRepository.findByTradeId(tradeId);
        if (bill.getStatus() == BillStatus.PASSED.value()) {
            return bill;
        }
        return null;
    }

    @Override
    public Bill findByTradeId(Integer tradeId) throws Exception {
        Bill bill = billRepository.findByTradeId(tradeId);
        return bill;
    }

    private void waitSend(Integer tradeId) throws Exception {
        tradeService.updateType(tradeId, TradeType.NO_SEND.value());
    }

    private void warp(Collection<Bill> bills, BillWrapOption option) throws Exception {
        int size = bills.size();
        Set<Integer> ids = bills.stream().map(Bill::getId).collect(Collectors.toSet());
        Map<Integer, Trade> tradeMap = new HashMap<>(size);
        Map<Integer, Product> productMap = new HashMap<>(size);
        Map<Integer, Merchant> merchantMap = new HashMap<>(size);
        if (option.isWithTrade()) {
            Set<Integer> tradeIds = bills.stream().map(Bill::getTradeId).collect(Collectors.toSet());
            List<Trade> tradeList = tradeService.findByIdIn(tradeIds);
            tradeMap = tradeList.stream().collect(Collectors.toMap(Trade::getId, it -> it));
        }
        if (option.isWithMerchant()) {
            Set<Integer> merchantIds = bills.stream().map(Bill::getMerchantId).collect(Collectors.toSet());
            List<Merchant> merchantList = merchantService.findByIdIn(merchantIds);
            merchantMap = merchantList.stream().collect(Collectors.toMap(Merchant::getId, it -> it));
        }

        for (Bill bill : bills) {
            if (option.isWithMerchant()) {
                Merchant merchant = merchantMap.get(bill.getMerchantId());
                if (merchant != null) {
                    bill.setMerchant(merchant);
                }
            }

            if (option.isWithTrade()) {
                Trade trade = tradeMap.get(bill.getTradeId());
                if (trade != null) {
                    bill.setTrade(trade);
                }
            }

        }

    }

}
