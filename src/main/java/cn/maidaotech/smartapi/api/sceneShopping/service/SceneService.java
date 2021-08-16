package cn.maidaotech.smartapi.api.sceneShopping.service;

import cn.maidaotech.smartapi.api.sceneShopping.entity.SceneQo;
import cn.maidaotech.smartapi.api.sceneShopping.model.Scene;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface SceneService {

    void save(Scene scene) throws Exception;

    Page<Scene> items(SceneQo qo) throws Exception;

    Scene item(Integer id) throws Exception;

    void changeStatus(Integer id) throws Exception;

    List<Scene> findByIdIn(Collection<Integer> ids) throws Exception;


}
