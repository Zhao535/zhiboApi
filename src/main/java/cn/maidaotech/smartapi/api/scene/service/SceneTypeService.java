package cn.maidaotech.smartapi.api.scene.service;

import cn.maidaotech.smartapi.api.scene.entity.SceneTypeQo;
import cn.maidaotech.smartapi.api.scene.model.SceneType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SceneTypeService {

    void save(SceneType sceneType) throws Exception;

    Page<SceneType> items(SceneTypeQo qo) throws Exception;

    SceneType item(Integer id) throws Exception;

    void changeStatus(Integer id) throws Exception;

    List<SceneType> allTypes() throws Exception;

    List<SceneType> allTypesForUsr() throws Exception;

    void remove(Integer id) throws Exception;


}
