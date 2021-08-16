package cn.maidaotech.smartapi.api.coupon.repository;

import cn.maidaotech.smartapi.api.coupon.model.Coupon;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CouponRepository extends BaseRepository<Coupon,Integer> {

    List<Coupon> findByIdIn(List<Integer> ids);

    @Transactional
    @Modifying
    @Query(value = "update coupon set status=:status where id=:id", nativeQuery = true)
    void update(Integer id, Byte status);

    @Query(value = "select * from coupon where json_contains(payload,'{\"type\":1}')", nativeQuery = true)
    List<Coupon> findCouponByType();

    List<Coupon> findByStatus(Byte status);



}
