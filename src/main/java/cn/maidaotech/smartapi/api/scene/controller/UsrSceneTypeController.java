package cn.maidaotech.smartapi.api.scene.controller;

import cn.maidaotech.smartapi.api.scene.service.SceneTypeService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usr/scene")
public class UsrSceneTypeController extends BaseController {

    @Autowired
    private SceneTypeService sceneTypeService;


    @RequestMapping("items")
    public ModelAndView items() throws Exception {
        return feedback(sceneTypeService.allTypesForUsr());
    }

}
