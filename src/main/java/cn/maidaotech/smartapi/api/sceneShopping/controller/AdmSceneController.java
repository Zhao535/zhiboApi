package cn.maidaotech.smartapi.api.sceneShopping.controller;
import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.sceneShopping.entity.SceneQo;
import cn.maidaotech.smartapi.api.sceneShopping.model.Scene;
import cn.maidaotech.smartapi.api.sceneShopping.service.SceneService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("adm/sceneShopping")
public class AdmSceneController extends BaseController {


    @Autowired
    private SceneService sceneService;

    @RequestMapping("items")
    @AdminAuthentication({AdminPermission.SCENE_LIST})
    public ModelAndView items(String qo) throws Exception {
        return feedback(sceneService.items(parseModel(qo, new SceneQo(), "qo")));
    }

    @RequestMapping("save")
    @AdminAuthentication({AdminPermission.SCENE_EDIT,AdminPermission.SCENE_LIST})
    public ModelAndView save(String scene) throws Exception {
        sceneService.save(parseModel(scene, new Scene(), "scene"));
        return feedback();
    }

    @RequestMapping("item")
    @AdminAuthentication({AdminPermission.SCENE_LIST})
    public ModelAndView findItem(Integer id) throws Exception {
        return feedback(sceneService.item(id));
    }

    @RequestMapping("changeStatus")
    @AdminAuthentication({AdminPermission.SCENE_EDIT,AdminPermission.SCENE_LIST})
    public ModelAndView changeStatus(Integer id) throws Exception {
        sceneService.changeStatus(id);
        return feedback();
    }


}
