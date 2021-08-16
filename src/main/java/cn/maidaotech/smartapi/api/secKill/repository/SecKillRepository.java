package cn.maidaotech.smartapi.api.secKill.repository;

import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SecKillRepository extends BaseRepository<SecKill, Integer> {

    List<SecKill> findByIdIn(Collection<Integer> ids);

    @Query(value = "select * from sec_kill where product_id=:id and end_at>:endAt order by end_at desc", nativeQuery = true)
    List<SecKill> findByProductIdAndEndAtAfterOrderByEndAtDesc(Integer id, Long endAt);

}
