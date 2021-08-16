package cn.maidaotech.smartapi.api.coupon.service;

import cn.maidaotech.smartapi.api.coupon.entity.CouponStatus;
import cn.maidaotech.smartapi.api.coupon.qo.UserCouponQo;
import cn.maidaotech.smartapi.api.coupon.model.Coupon;
import cn.maidaotech.smartapi.api.coupon.model.UserCoupon;
import cn.maidaotech.smartapi.api.coupon.repository.UserCouponRepository;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.ByteUtils;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private CouponService couponService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, UserCoupon> userCouponCache;

    @PostConstruct
    public void init() {
        userCouponCache = kvCacheFactory.create(new CacheOptions("userCoupon", 1, Constants.CACHE_REDIS_EXPIRE),
                new RepositoryProvider<Integer, UserCoupon>() {

                    @Override
                    public UserCoupon findByKey(Integer id) throws ServiceException {
                        UserCoupon userCoupon = userCouponRepository.findById(id).orElse(null);
                        userCoupon.setCoupon(couponService.coupon(userCoupon.getCouponId()));
                        return userCoupon;
                    }

                    @Override
                    public Map<Integer, UserCoupon> findByKeys(Collection<Integer> ids) throws
                            ServiceException {
                        List<Integer> idlist = new ArrayList<>(ids);
                        List<UserCoupon> userCoupons = userCouponRepository.findByIdIn(idlist);
                        return userCoupons.stream().collect(Collectors.toMap(UserCoupon::getId, c -> c));
                    }
                }, new BeanModelConverter<>(UserCoupon.class));
    }

    public void wrapCoupon(List<UserCoupon> list) {
        List<Integer> couponIds = list.stream().map(UserCoupon::getCouponId).collect(Collectors.toList());
        Map<Integer, Coupon> couponMap = couponService.findByIds(couponIds);
        for (UserCoupon userCoupon : list) {
            userCoupon.setCoupon(couponMap.get(userCoupon.getCouponId()));
        }
    }

    @Override
    public Page<UserCoupon> userCoupons(UserCouponQo qo) {
        qo.setUserId(UserContexts.requestUserId());
        Page<UserCoupon> page = userCouponRepository.findAll(qo);
        wrapCoupon(page.getContent());
        return page;
    }

    @Override
    public List<Coupon> productCoupons(Integer productId) throws Exception {
        List<Coupon> couponList=couponService.productCoupons(productId);
        wrap(couponList);
        return couponList;
    }
    private void wrap(List<Coupon> couponList)throws Exception{
        if (true){
            List<UserCoupon>  userCouponList=userCouponRepository.findAllByUserId(UserContexts.requestUserId());
            if (CollectionUtils.isEmpty(couponList)){
                return;
            }
            for (Coupon coupon : couponList) {
                if (CollectionUtils.isEmpty(userCouponList)){
                    coupon.setIsHaveCoupon(ByteUtils.BYTE_0);
                }
                for (UserCoupon userCoupon : userCouponList) {
                    if (coupon.getId().equals(userCoupon.getCouponId())){
                        coupon.setIsHaveCoupon(ByteUtils.BYTE_1);
                        break;
                    }else {
                        coupon.setIsHaveCoupon(ByteUtils.BYTE_0);
                    }
                }
            }
        }
    }

    @Override
    public List<UserCoupon> tradeCoupons(List<Integer> ids) throws Exception {
        return couponService.tradeCoupons(ids);
    }

    @Override
    public UserCoupon userCoupon(Integer id) {
        return userCouponCache.findByKey(id);
    }

    @Override
    public void saveCoupon(Integer id) {
        Coupon coupon = couponService.coupon(id);
        if (coupon == null) {
            throw new ArithmeticException("优惠券不存在!");
        }
        long curr = System.currentTimeMillis();
        Integer userId = UserContexts.requestUserId();
        UserCoupon newUserCoupon = new UserCoupon();
        newUserCoupon.setCouponId(id);
        newUserCoupon.setUserId(userId);
        newUserCoupon.setStatus(CouponStatus.UNUSED.value());
        newUserCoupon.setGetAt(curr);
        newUserCoupon.setValidThru(curr + coupon.getDuration() * 86400000L);
        userCouponRepository.save(newUserCoupon);
    }

    @Override
    public void updateStatus(Integer id, Byte status) {
        userCouponRepository.updateByIdAndStatus(id, status);
        userCouponCache.remove(id);
    }

    @Override
    public List<UserCoupon> userCoupons(Byte status) {
        List<UserCoupon> list = userCouponRepository.findByUserIdAndStatus(UserContexts.requestUserId(),status);
        wrapCoupon(list);
        return list;
    }

    @Override
    public List<UserCoupon> findByStatus(Byte status) {
        return userCouponRepository.findByUserIdAndStatus(UserContexts.requestUserId(), status);
    }

    @Override
    public void syncStatus() {
        List<UserCoupon> list = findByStatus((byte) 1);
        for (UserCoupon userCoupon : list) {
            if (userCoupon.getValidThru() < System.currentTimeMillis()) {
                userCoupon.setStatus((byte) 3);
            }
            userCouponRepository.saveAll(list);
            userCouponCache.flush();
        }
    }

}
