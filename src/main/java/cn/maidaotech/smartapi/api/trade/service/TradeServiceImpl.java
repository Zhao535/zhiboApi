package cn.maidaotech.smartapi.api.trade.service;

import cn.maidaotech.smartapi.api.bill.model.Bill;
import cn.maidaotech.smartapi.api.bill.service.BillService;
import cn.maidaotech.smartapi.api.cart.service.CartService;
import cn.maidaotech.smartapi.api.category.service.ProductCategoryService;
import cn.maidaotech.smartapi.api.coupon.entity.Payload;
import cn.maidaotech.smartapi.api.coupon.entity.Rule;
import cn.maidaotech.smartapi.api.coupon.model.UserCoupon;
import cn.maidaotech.smartapi.api.coupon.service.UserCouponService;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillQo;
import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import cn.maidaotech.smartapi.api.secKill.service.SecKillService;
import cn.maidaotech.smartapi.api.trade.entity.*;
import cn.maidaotech.smartapi.api.trade.model.Trade;
import cn.maidaotech.smartapi.api.trade.repository.TradeRepository;
import cn.maidaotech.smartapi.api.user.model.User;
import cn.maidaotech.smartapi.api.user.service.UserService;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.job.Job;
import cn.maidaotech.smartapi.common.job.JobManager;
import cn.maidaotech.smartapi.common.service.DistributeLock;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService, TradeErrors {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private JobManager jobManager;
    @Autowired
    private SecKillService secKillService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private DistributeLock distributeLock;

    @Autowired
    private BillService billService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Trade> tradeCache;

    @PostConstruct
    public void init() {
        tradeCache = kvCacheFactory.create(new CacheOptions("trade", 1, 3600),
                new RepositoryProvider<Integer, Trade>() {

                    @Override
                    public Trade findByKey(Integer id) throws Exception {
                        return tradeRepository.findById(id).orElse(null);
                    }

                    @Override
                    public Map<Integer, Trade> findByKeys(Collection<Integer> ids) throws Exception {
                        List<Trade> admins = tradeRepository.findAllById(ids);
                        return admins.stream().collect(Collectors.toMap(Trade::getId, it -> it));
                    }
                }, new BeanModelConverter<>(Trade.class));

        jobManager.schedule("release_article", "0 0/30 * * * ?", new Job() {
            volatile boolean stopped = false;
            static final int BATCH_SIZE = 200;

            @Override
            public void run() throws Exception {
                if (stopped) {
                    return;
                }
                System.out.println("release_article" + System.currentTimeMillis());
                int current = 1;
                while (true) {
                    List<Trade> items = tradeRepository.findAllByType(TradeType.NO_PAY.value());
                    items = items.stream()
                            .filter(it -> System.currentTimeMillis() > (it.getCreatedAt() + 30 * 60 * 1000))
                            .collect(Collectors.toList());
                    for (Trade record : items) {
                        distributeLock.doInLock("release_article." + record.getId(), new ReleaseArticleWork(record), 5,
                                1800);
                    }
                    if (items.size() < BATCH_SIZE) {
                        break;
                    }
                    current++;
                }
            }

            @Override
            public void terminate() throws Exception {
                stopped = true;
            }
        });
    }

    private Trade findById(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        Trade trade = tradeCache.findByKey(id);

        return trade;
    }

    private Trade getById(Integer id) throws Exception {
        Trade trade = findById(id);
        if (Objects.isNull(trade)) {
            throw new DataNotFoundServiceException();
        }
        return trade;
    }

    @Override
    public Integer save(Trade trade, Integer userCouponId) throws Exception {
        Integer userId = UserContexts.requestUserId();
        List<Integer> cartIds = trade.getTradeItems().stream().map(TradeItem::getId).collect(Collectors.toList());
        Integer totalPrice = 0;

        List<Product> products = trade.getTradeItems().stream().map(TradeItem::getProduct).collect(Collectors.toList());
        List<Integer> merchantIds = products.stream().map(Product::getMerchantId).collect(Collectors.toList());
        Integer merchantId = merchantIds.get(0);
        Integer couponAmount = 0;
        totalPrice = totalPrice(products, trade);
        if (userCouponId == 0) {
            couponAmount = 0;
        } else {
            couponAmount = couponAmount(trade, userCouponId);
        }
        trade.setUserId(userId);
        trade.setMerchantId(merchantId);
        trade.setCreatedAt(System.currentTimeMillis());
        trade.setType(TradeType.NO_PAY.value());
        trade.setOrderNumber(StringUtils.getOrderNumber());
        trade.setTotalPrice(totalPrice);
        trade.setCouponAmount(couponAmount);
        trade.setTotalAmount(totalPrice - couponAmount);
        trade.setUserCouponId(userCouponId);
        tradeRepository.save(trade);
        // userCouponService.updateStatus(userCouponId, CouponStatus.USED.value());
        // billService.createBillByTrade(trade);
        updateStock(trade);

        cartService.removeList(cartIds);
        return findByOrderNumber(trade.getOrderNumber()).getId();

        // return null;
    }

    @Override
    public Trade item(Integer id) throws Exception {
        return getById(id);
    }

    @Override
    public Trade warpUserFindById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Trade findByOrderNumber(String orderNum) throws Exception {
        Trade trade = tradeRepository.findByOrderNumber(orderNum);
        if (trade == null) {
            throw new ArgumentServiceException("订单不存在！");
        } else {
            return trade;
        }

    }

    @Override
    public void delete(Integer id) throws Exception {

    }

    @Override
    public Page<Trade> trades(TradeQo qo, TradeWrapOption option) throws Exception {
        Page<Trade> page = tradeRepository.findAll(qo);
        wrapTrade(page.getContent(), option);
        return page;
    }

    @Override
    public Page<Trade> items(TradeQo qo, TradeWrapOption option) throws Exception {
        Integer merchantId = MerchantAdminContexts.requestMerchantAdmin().getMerchantId();
        qo.setMerchantId(merchantId);
        Page<Trade> page = tradeRepository.findAll(qo);
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            wrapTrade(page.getContent(), option);
        }
        return page;
    }

    @Override
    public void batchRemove(List<Integer> ids) throws Exception {

    }

    @Override
    public void updateType(Integer id, Byte type) throws Exception {
        if (type == TradeType.NO_GET.value()) {
            Bill bill = billService.checkPassed(id);
            if (Objects.isNull(bill)) {
                throw new ServiceException(ERR_TREAD_UNPAY);
            }
        }
        Trade trade = getById(id);
        trade.setType(type);
        tradeRepository.save(trade);
        tradeCache.removeSafely(id);
    }

    public Integer totalPrice(List<Product> list, Trade trade) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        if (Objects.isNull(trade)) {
            return 0;
        }

        int totalPrice = 0;
        Set<Integer> secIds = new HashSet<>();
        if (!CollectionUtils.isEmpty(trade.getTradePayloads())) {
            secIds = trade.getTradePayloads().stream().map(TradePayload::getId).collect(Collectors.toSet());
        }
        SecKillQo qo = new SecKillQo();
        qo.setIds(new ArrayList<>(secIds));
        List<SecKill> secKillList = secKillService.secKillsForUsr(qo).getContent();

        for (int i = 0; i < list.size(); i++) {
            String sno = trade.getTradeItems().get(i).getProductSno();
            List<ProductSpec> specs = list.get(i).getSpecs();
            Product product = list.get(i);
            for (ProductSpec spec : specs) {
                if (spec.getSno().equals(sno)) {
                    Set<SecKill> secKillSet = new HashSet<>();
                    if (CollectionUtils.isNotEmpty(secKillList)) {
                        secKillSet = secKillList.stream().filter(item -> item.getProductId().equals(product.getId()))
                                .collect(Collectors.toSet());
                    }
                    List<SecKill> secKills = new ArrayList<>(secKillSet);
                    if (secKillSet.size() <= 0) {
                        totalPrice += trade.getTradeItems().get(i).getNum() * spec.getPrice();
                    } else {
                        if (trade.getTradePayloads().size() > 0) {
                            for (TradePayload tradePayload : trade.getTradePayloads()) {
                                for (SecKill secKill : secKills) {
                                    if (tradePayload.getId().equals(secKill.getId())) {
                                        totalPrice += tradePayload.getRealPrice()
                                                * trade.getTradeItems().get(i).getNum();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return totalPrice;
    }

    public Integer couponAmount(Trade trade, Integer userCouponId) {

        List<TradeItem> tradeItems = trade.getTradeItems();
        Integer couponAmount = 0;
        UserCoupon userCoupon = userCouponService.userCoupon(userCouponId);
        Payload payload = userCoupon.getCoupon().getPayload();
        Rule rule = userCoupon.getCoupon().getRule();

        List<Integer> values = rule.getValues();

        Byte ruleType = rule.getType();

        Byte payloadType = payload.getType();

        Integer availablePrice = 0;

        for (TradeItem tradeItem : tradeItems) {
            Product product = tradeItem.getProduct();
            String productSno = tradeItem.getProductSno();
            Integer productId = product.getId();
            List<ProductSpec> specs = product.getSpecs();

            if (payloadType == 1
                    || (payloadType == 2 && (product.getSequence().substring(0, 2)
                            .equals(payload.getCategory().getSequence().substring(0, 2))))
                    || (payloadType == 3 && (productId.equals(payload.getProduct().getId())))) {
                for (ProductSpec spec : specs) {
                    if (spec.getSno().equals(productSno)) {
                        availablePrice += spec.getPrice();
                    }
                }
            }
        }

        if (availablePrice == 0 || (ruleType == 1 || ruleType == 2) && (availablePrice < values.get(0) / 100)) {
            throw new ArithmeticException("该优惠券在此订单不可使用");
        } else {
            if (ruleType == 1) {
                couponAmount = Math.min(values.get(1), availablePrice);
            } else if (ruleType == 2) {
                couponAmount = (availablePrice / values.get(0)) * values.get(1);
            } else {
                couponAmount = Math.min(values.get(0), availablePrice);
            }
        }

        return couponAmount;
    }

    public void wrapTrade(List<Trade> list, TradeWrapOption option) throws Exception {
        if (option.isWithMerchant()) {
            Set<Integer> merchantIds = list.stream().map(Trade::getMerchantId).collect(Collectors.toSet());
            Map<Integer, Merchant> merchantMap = merchantService.findByIdIn(merchantIds).stream()
                    .collect(Collectors.toMap(Merchant::getId, it -> it));
            for (Trade trade : list) {
                trade.setMerchant(merchantMap.get(trade.getMerchantId()));
            }
        }

        if (option.isWithUser()) {
            Set<Integer> userIds = list.stream().map(Trade::getUserId).collect(Collectors.toSet());
            Map<Integer, User> userMap = userService.findAllById(userIds).stream()
                    .collect(Collectors.toMap(User::getId, it -> it));
            for (Trade trade : list) {
                trade.setUser(userMap.get(trade.getUserId()));
            }
        }
    }

    private void validateTrade(Trade trade) throws ServiceException {
        if (trade.getMerchantId() == null) {
            throw new ArgumentServiceException("MerchantIdIsNull");
        }
        if (trade.getUserId() == null) {
            throw new ArgumentServiceException("UserIdIsNull");
        }
        if (trade.getTradeItems().size() == 0) {
            throw new ArgumentServiceException("TradeItemIsNull");
        }
        if (trade.getType() == null) {
            throw new ArgumentServiceException("TypeIsNull");
        }
    }

    public TradeType checkType(Byte type) {
        TradeType tradeType = TradeType.findTrade(type);
        if (tradeType == null) {
            throw new ArgumentServiceException("type");
        }
        return tradeType;
    }

    @Override
    public void cancelTrade() throws Exception {
        Long checkTime = System.currentTimeMillis() - (3600L / 2L * 1000L);
        List<Trade> list = tradeRepository.findAllByTypeEqualsAndCreatedAtBefore(TradeType.NO_PAY.value(), checkTime);
        wrapTrade(list, TradeWrapOption.getMerchantListInstance());
        for (Trade trade : list) {
            updateStock(trade);
            trade.setType(TradeType.CALL_OFF_TRADE.value());
        }
        tradeRepository.saveAll(list);
        tradeCache.flush();

    }

    void updateStock(Trade trade) {

        List<TradeItem> list = new ArrayList<>();
        Map<Integer, Integer> productCountMap = new HashMap<>();
        Map<Integer, String> productSpecMap = new HashMap<>();
        for (TradeItem tradeItem : trade.getTradeItems()) {
            Integer productId = tradeItem.getProduct().getId();
            productCountMap.put(productId, tradeItem.getNum());
            productSpecMap.put(productId, tradeItem.getProductSno());
            tradeItem.setProduct(tradeItem.getProduct());
            list.add(tradeItem);
        }
        List<Product> products = trade.getTradeItems().stream().map(TradeItem::getProduct).collect(Collectors.toList());
        for (Product product : products) {
            Integer count = productCountMap.get(product.getId());
            List<ProductSpec> specs = product.getSpecs();
            for (ProductSpec spec : specs) {
                String s = productSpecMap.get(product.getId());
                if (spec.getSno().equals(s)) {
                    if (trade.getType() == TradeType.CALL_OFF_TRADE.value()) {
                        // 取消订单，返还库存
                        spec.setStock(spec.getStock() + count);
                        // product.setSalesCount(product.getSalesCount() - count);
                    }
                    if (trade.getType() == TradeType.NO_PAY.value()) {
                        if ((spec.getStock() - count) < 0) {
                            throw new ArgumentServiceException("库存不足");
                        } else {
                            // 生成订单，扣库存
                            spec.setStock(spec.getStock() - count);
                            // product.setSalesCount(product.getSalesCount() + count);
                        }
                    }
                }
            }
        }
        // productService.saveAll(products);
    }

    private class ReleaseArticleWork implements DistributeLock.InLockWork {
        private Trade trade;

        public ReleaseArticleWork(Trade trade) {
            super();
            this.trade = trade;
        }

        @Override
        public void run() throws Exception {
            trade.setType(TradeType.CALL_OFF_TRADE.value());
            updateStock(trade);
            tradeRepository.save(trade);
            tradeCache.removeSafely(trade.getId());
        }
    }

    private void payment(Integer tradeId) throws Exception {
        Trade trade = getById(tradeId);
        if (trade.getType() == TradeType.NO_PAY.value()) {

        } else {
            throw new ServiceException(ERR_TREAD_CANT_PAY);
        }
    }

    @Override
    public List<Trade> findByIdIn(Collection<Integer> ids) throws Exception {
        List<Trade> tradeList = tradeRepository.findByIdIn(ids);
        return tradeList;
    }

    @Override
    public void send(Integer id, Byte type) throws Exception {
        Trade exist = tradeCache.findByKey(id);
        if (exist == null) {
            throw new ArgumentServiceException("订单不存在");
        } else {
            exist.setType(type);
            tradeRepository.save(exist);
        }

    }

}
