package cn.maidaotech.smartapi.api.scene.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.scene.entity.SceneTypeQo;
import cn.maidaotech.smartapi.api.scene.model.SceneType;
import cn.maidaotech.smartapi.api.scene.service.SceneTypeService;
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
@RequestMapping("adm/sceneType")
public class AdmSceneTypeController extends BaseController {
    @Autowired
    private SceneTypeService sceneTypeService;

    @RequestMapping("items")
    @AdminAuthentication(AdminPermission.SCENETYPE_LIST)
    public ModelAndView items(String qo) throws Exception {
        return feedback(sceneTypeService.items(parseModel(qo, new SceneTypeQo(), "qo")));
    }

    @RequestMapping("save")
    @AdminAuthentication({AdminPermission.SCENETYPE_EDIT,AdminPermission.SCENETYPE_LIST})
    public ModelAndView save(String sceneType) throws Exception {
        sceneTypeService.save(parseModel(sceneType, new SceneType(), "sceneType"));
        return feedback();
    }

    @RequestMapping("item")
    @AdminAuthentication(AdminPermission.SCENETYPE_LIST)
    public ModelAndView findItem(Integer id) throws Exception {
        return feedback(sceneTypeService.item(id));
    }

    @RequestMapping("changeStatus")
    @AdminAuthentication({AdminPermission.SCENETYPE_EDIT,AdminPermission.SCENETYPE_LIST})
    public ModelAndView changeStatus(Integer id) throws Exception {
        sceneTypeService.changeStatus(id);
        return feedback();
    }

    @RequestMapping("allTypes")
    public ModelAndView types(String qo) throws Exception {
        return feedback(sceneTypeService.allTypes());
    }

    @RequestMapping("remove")
    @AdminAuthentication({AdminPermission.SCENETYPE_EDIT,AdminPermission.SCENETYPE_LIST})
    public ModelAndView remove(Integer id) throws Exception {
        sceneTypeService.remove(id);
        return feedback();
    }


}
