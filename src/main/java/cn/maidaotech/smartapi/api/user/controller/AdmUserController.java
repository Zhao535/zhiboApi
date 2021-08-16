package cn.maidaotech.smartapi.api.user.controller;


import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.user.service.UserService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("adm/user")
public class AdmUserController extends BaseController {

    @Autowired
    private UserService userService;


    @AdminAuthentication(value = AdminPermission.USER_LIST)
    @RequestMapping("items")
    public ModelAndView items(Integer pagenum,Integer pagesize) throws Exception {
        return feedback(userService.items(parsePageRequest(pagenum, pagesize)));
    }

    @AdminAuthentication(value = AdminPermission.USER_LIST)
    @RequestMapping("item")
    public ModelAndView item(Integer id) throws Exception {
        return feedback(userService.item(id));
    }






}
