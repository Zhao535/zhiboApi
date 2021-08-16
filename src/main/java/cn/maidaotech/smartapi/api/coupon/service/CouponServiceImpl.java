package cn.maidaotech.smartapi.api.coupon.service;

import cn.maidaotech.smartapi.api.category.model.ProductCategory;
import cn.maidaotech.smartapi.api.category.service.ProductCategoryService;
import cn.maidaotech.smartapi.api.coupon.entity.*;
import cn.maidaotech.smartapi.api.coupon.model.Coupon;
import cn.maidaotech.smartapi.api.coupon.model.UserCoupon;
import cn.maidaotech.smartapi.api.coupon.qo.CouponQo;
import cn.maidaotech.smartapi.api.coupon.qo.CouponWO;
import cn.maidaotech.smartapi.api.coupon.repository.CouponRepository;
import cn.maidaotech.smartapi.api.coupon.repository.UserCouponRepository;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.api.usrCollect.model.Collect;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.Contexts;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import com.sunnysuperman.kvcache.converter.ListModelConverter;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Coupon> couponCache;

    private KvCacheWrap<Integer, List<Coupon>> couponsCache;

    @PostConstruct
    public void init() {
        couponCache = kvCacheFactory.create(new CacheOptions("coupon", 1, 3600),
                new RepositoryProvider<Integer, Coupon>() {

                    @Override
                    public Coupon findByKey(Integer id) throws
                            ServiceException {
                        return couponRepository.findById(id).orElse(null);
                    }

                    @Override
                    public Map<Integer, Coupon> findByKeys(Collection<Integer> ids) throws
                            ServiceException {
                        List<Integer> idlist = new ArrayList<>(ids);
                        List<Coupon> coupons = couponRepository.findByIdIn(idlist);
                        return coupons.stream().collect(Collectors.toMap(Coupon::getId, c -> c));
                    }
                }, new BeanModelConverter<>(Coupon.class));

        couponsCache = kvCacheFactory.create(new CacheOptions("coupons", 1, Constants.CACHE_REDIS_EXPIRE),
                new RepositoryProvider<Integer, List<Coupon>>() {

                    @Override
                    public List<Coupon> findByKey(Integer id) throws ServiceException {
                        return couponRepository.findByStatus(Constants.STATUS_OK);
                    }

                    @Override
                    public Map<Integer, List<Coupon>> findByKeys(Collection<Integer> ids) throws ServiceException {
                        throw new UnsupportedOperationException("findByKeys");
                    }
                }, new ListModelConverter<>(Coupon.class));
    }

    private void checkCoupon(Coupon coupon) throws Exception {
        if (Objects.isNull(coupon)) {
            throw new ArgumentAccessException("无法创建空的优惠券");
        }

        Payload payload = coupon.getPayload();
        CouponType couponType = CouponType.findCoupon(payload.getType());
        if (Objects.isNull(couponType)) {
            throw new ArgumentAccessException("优惠券使用类别不合法");
        }
        if (couponType.equals(CouponType.CATEGORY_COUPON)) {
            if (Objects.isNull(payload.getCategory())) {
                throw new ArgumentAccessException("请选择优惠券类别");
            }
        }
        if (couponType.equals(CouponType.PRODUCT_COUPON)) {
            if (Objects.isNull(payload.getProduct())) {
                throw new ArgumentAccessException("请选择优惠券优惠的商品");
            }
        }

        if (Objects.isNull(coupon.getDuration()) || coupon.getDuration() < 0) {
            throw new ArgumentAccessException("请选择优惠券正确的过期时间");
        }
        Rule rule = coupon.getRule();
        if (Objects.isNull(rule)) {
            throw new ArgumentAccessException("优惠券减免类型不能为空");
        }
        RuleType ruleType = RuleType.findRuleType(rule.getType());
        if (Objects.isNull(ruleType)) {
            throw new ArgumentAccessException("请选择正确的优惠券规则类型:满减（1）/每减（2）/直减（3）");
        }
        List<Integer> values = rule.getValues();
        if (Objects.isNull(values)) {
            throw new ArgumentAccessException("请输入优惠金额");
        }
        if (ruleType.equals(RuleType.FULL) || ruleType.equals(RuleType.EACH)) {
            if (values.size() != 2) {
                throw new ArgumentAccessException("优惠金额类型不合法");
            } else {
                if (values.get(0) - values.get(1) <= 0) {
                    throw new ArgumentAccessException("优惠券金额不合法");
                }
            }
        } else {
            if (values.size() != 1) {
                throw new ArgumentAccessException("优惠券类型不合法");
            }
        }
    }

    @Override
    public void save(Coupon coupon) throws Exception {
        checkCoupon(coupon);
        Integer id = coupon.getId();
        if (id == null || id == 0) {
            coupon.setCreatedAt(System.currentTimeMillis());
            coupon.setStatus((byte) 1);
            couponRepository.save(coupon);
        } else {
            Coupon exits = coupon(id);
            exits.setDuration(coupon.getDuration());
            exits.setPayload(coupon.getPayload());
            exits.setRule(coupon.getRule());
            couponRepository.save(exits);
            couponCache.remove(id);
        }
    }

    @Override
    public void remove(Integer id) {
        Coupon coupon = couponCache.findByKey(id);
        couponRepository.delete(coupon);
        couponCache.remove(id);
    }

    @Override
    public void status(Integer id) {
        Coupon coupon = couponCache.findByKey(id);
        Byte status = coupon.getStatus();
        if (status == 1) {
            couponRepository.update(id, (byte) 2);
        } else {
            couponRepository.update(id, (byte) 1);
        }
        couponCache.remove(id);
    }

    @Override
    public List<Coupon> productCoupons(Integer productId) throws Exception {
        Product product = productService.product(productId);
        List<Coupon> list = couponRepository.findAll();
        List<Coupon> coupons = list.stream().filter(item -> {
            Payload payload = item.getPayload();
            Byte type = payload.getType();
            return type == 1 || (type == 2 && payload.getCategory().getSequence().substring(0, 2).equals((product.getSequence().substring(0, 2))) || (type == 3 && payload.getProduct().getId().longValue() == productId));
        }).collect(Collectors.toList());
        return coupons;
    }

    public List<UserCoupon> tradeCoupons(List<Integer> ids) throws Exception {
        List<Product> products = productService.findAllByIds(ids);
        List<String> sequences = products.stream().map(Product::getSequence).collect(Collectors.toList());//拿到所有商品的categoryId
        List<Integer> categoryIds = categoryService.convert(sequences);
        List<ProductCategory> categorys = categoryService.findAllByIdIn(categoryIds);//拿到订单中所有产品的category
        List<UserCoupon> userCoupons = userCouponService.userCoupons((byte) 1);
        userCouponService.wrapCoupon(userCoupons);
        List<UserCoupon> coupons = userCoupons.stream().filter(item -> {
            Coupon coupon = item.getCoupon();
            Payload payload = coupon.getPayload();
            Byte type = payload.getType();
            return type == 1 || (type == 2 && categorys.stream().filter(e -> e.getSequence().substring(0, 2).equals(payload.getCategory().getSequence().substring(0, 2))).collect(Collectors.toList()).size() > 0) || (type == 3 && ids.stream().filter(e -> e.equals(payload.getProduct().getId().intValue())).collect(Collectors.toList()).size() > 0);
        }).collect(Collectors.toList());
        return coupons;
    }

    @Override
    public Map<Integer, Coupon> findByIds(List<Integer> ids) {
        return couponCache.findByKeys(ids);
    }

    @Override
    public Coupon coupon(Integer id) {
        return couponCache.findByKey(id);
    }

    @Override
    public Page<Coupon> coupons(CouponQo qo, CouponWO wo) {

        Page<Coupon> page = couponRepository.findAll(qo);
        wrapCoupon(page.getContent(), wo);
        return page;
    }

    void wrapCoupon(List<Coupon> list, CouponWO wo) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Set<Integer> couponIds;
        Map<Integer, UserCoupon> ucMap = new HashMap<>(list.size());
        if (wo.isWithUserCoupon()) {
            couponIds = list.stream().map(Coupon::getId).collect(Collectors.toSet());
            List<UserCoupon> ucs = userCouponRepository.findAllByUserIdAndCouponIdIn(UserContexts.requestUserId(), couponIds);
            if (CollectionUtils.isNotEmpty(ucs)) {
                ucMap = ucs.stream().collect(Collectors.toMap(UserCoupon::getCouponId, c -> c));
            }
        }

        for (Coupon coupon : list) {
            if (wo.isWithUserCoupon()) {
                coupon.setUserCoupon(ucMap.get(coupon.getId()));
            }
        }
    }


    @Override
    public List<Coupon> findCouponByType() throws Exception {
        List<Coupon> coupons = couponRepository.findCouponByType();
        return coupons;
    }
}
