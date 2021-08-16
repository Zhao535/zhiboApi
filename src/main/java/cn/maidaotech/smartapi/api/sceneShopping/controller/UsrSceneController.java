package cn.maidaotech.smartapi.api.sceneShopping.controller;

import cn.maidaotech.smartapi.api.sceneShopping.entity.SceneQo;
import cn.maidaotech.smartapi.api.sceneShopping.service.SceneService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("usr/sceneShopping")
public class UsrSceneController extends BaseController {

    @Autowired
    private SceneService sceneService;


    @RequestMapping("item")
    public ModelAndView item(Integer id) throws Exception {
        return feedback(sceneService.item(id));

    }

    @RequestMapping("items")
    public ModelAndView items(String qo) throws Exception {
        return feedback(sceneService.items(parseModel(qo, new SceneQo(), "qo")));

    }
}