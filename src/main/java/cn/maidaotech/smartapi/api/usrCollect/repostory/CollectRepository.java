package cn.maidaotech.smartapi.api.usrCollect.repostory;

import cn.maidaotech.smartapi.api.usrCollect.model.Collect;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

public interface CollectRepository extends BaseRepository<Collect, Long> {

    Collect findByUserIdAndFromIdAndType(Integer userId, Integer fromId, Byte type);

    Integer countByStatusAndUserIdAndTypeNot(Byte status, Integer userId, Byte type);

    Integer countByStatusAndUserIdAndType(Byte status, Integer userId, Byte type);


}
