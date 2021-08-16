package cn.maidaotech.smartapi.api.coupon.repository;

import cn.maidaotech.smartapi.api.coupon.model.UserCoupon;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserCouponRepository extends BaseRepository<UserCoupon, Integer> {
    List<UserCoupon> findByIdIn(Collection<Integer> ids);

    List<UserCoupon> findByUserIdAndStatus(Integer userId, Byte status);

    @Transactional
    @Modifying
    @Query(value = "update user_coupon set status=:status where id=:id", nativeQuery = true)
    void updateByIdAndStatus(Integer id, Byte status);

    List<UserCoupon> findAllByUserId(Integer userId);

    List<UserCoupon> findAllByUserIdAndCouponIdIn(int userId, Set<Integer> couponIds);
}
