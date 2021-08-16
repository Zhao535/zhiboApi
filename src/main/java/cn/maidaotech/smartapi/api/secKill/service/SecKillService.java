package cn.maidaotech.smartapi.api.secKill.service;

import cn.maidaotech.smartapi.api.secKill.entity.SecKillQo;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillWrapOption;
import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface SecKillService {

    void save(SecKill secKill) throws Exception;

    SecKill secKill(Integer id, SecKillWrapOption option) throws Exception;

    void changeStatus(Integer id) throws Exception;

    Integer num(Integer id, String sno) throws Exception;

    void modifyStock(Integer id, String sno) throws Exception;

    Page<SecKill> secKillsForUsr(SecKillQo qo) throws Exception;

    Page<SecKill> secKillsForAdm(SecKillQo qo) throws Exception;

    Page<SecKill> secKillsForMch(SecKillQo qo) throws Exception;

    List<SecKill> findByIdIn(Collection<Integer> ids) throws Exception;

    SecKill secKillByProductId(Integer id) throws Exception;
}
