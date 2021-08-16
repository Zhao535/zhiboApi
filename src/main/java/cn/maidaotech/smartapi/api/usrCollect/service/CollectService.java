package cn.maidaotech.smartapi.api.usrCollect.service;

import cn.maidaotech.smartapi.api.usrCollect.entity.CollectQo;
import cn.maidaotech.smartapi.api.usrCollect.model.Collect;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CollectService {

    void save(Collect collect) throws Exception;


    Collect item(Byte type, Integer fromId) throws Exception;

    List items(CollectQo qo) throws Exception;

    boolean isCollect(Byte type, Integer fromId) throws Exception;

    Integer collectNum() throws Exception;

    Integer focusNum() throws Exception;

}
