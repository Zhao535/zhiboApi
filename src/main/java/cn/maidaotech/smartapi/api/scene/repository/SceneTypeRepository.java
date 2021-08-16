package cn.maidaotech.smartapi.api.scene.repository;

import cn.maidaotech.smartapi.api.scene.model.SceneType;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;

import java.util.List;

public interface SceneTypeRepository extends BaseRepository<SceneType, Integer> {

    List<SceneType> findAllByStatus (Byte status);
}
