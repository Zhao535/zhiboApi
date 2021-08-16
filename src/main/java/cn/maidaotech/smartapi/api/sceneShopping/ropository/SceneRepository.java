package cn.maidaotech.smartapi.api.sceneShopping.ropository;

import cn.maidaotech.smartapi.api.sceneShopping.model.Scene;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.Collection;
import java.util.List;

public interface SceneRepository extends BaseRepository<Scene, Integer> {

    List<Scene> findAllByIdIn(Collection<Integer> ids);

}
