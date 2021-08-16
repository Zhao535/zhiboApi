package cn.maidaotech.smartapi.api.ui.repository;

import cn.maidaotech.smartapi.api.ui.model.UI;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

public interface UiRepository extends BaseRepository<UI,Integer> {

    UI findByIsDefault(Byte status);

    UI findByIsDefaultAndType(Byte status,Byte type);
}
