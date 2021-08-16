package cn.maidaotech.smartapi.api.admin.controller;

import cn.maidaotech.smartapi.api.admin.model.*;
import cn.maidaotech.smartapi.api.admin.service.AdminService;
import cn.maidaotech.smartapi.common.context.AdminContexts;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "adm/admin")
public class AdmAdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @AdminAuthentication({AdminPermission.ADMIN_EDIT,AdminPermission.ADMIN_LIST})
    @RequestMapping(value = "save")
    public ModelAndView save(String admin) throws Exception {
        adminService.save(parseModel(admin, new Admin(), "admin"));
        return feedback();
    }

    @RequestMapping(value = "signin")
    public ModelAndView signin(String username, String password) throws Exception {
        return feedback(adminService.signIn(username, password));
    }


    @AdminAuthentication(value = {AdminPermission.ADMIN_EDIT, AdminPermission.ADMIN_LIST})
    @RequestMapping("items")
    public ModelAndView items(String qo) throws Exception {
        return feedback(adminService.findAll(parseModel(qo, new AdminQo(), "qo")));
    }

    @AdminAuthentication(value = {AdminPermission.ADMIN_EDIT, AdminPermission.ADMIN_LIST})
    @RequestMapping("item")
    public ModelAndView item(Integer id) throws Exception {
        return feedback(adminService.findById(id));
    }

    @AdminAuthentication
    @RequestMapping("profile")
    public ModelAndView profile() throws Exception {
        return feedback(adminService.getById(AdminContexts.requestAdminId()));

    }

    @AdminAuthentication
    @RequestMapping("sign_log")
    public ModelAndView signinLog(String qo) throws Exception {
        return feedback(adminService.adminSessions(parseModel(qo, new AdminSessionQo(), "qo")));
    }

    @RequestMapping(value = "/save_role")
    @AdminAuthentication(AdminPermission.ROLE_EDIT)
    public ModelAndView saveRole(String role) throws Exception {
        adminService.saveRole(parseModel(role, new AdminRole(), "role"));
        return feedback();
    }

    @RequestMapping(value = "/remove_role")
    @AdminAuthentication(AdminPermission.ROLE_EDIT)
    public ModelAndView removeRole(Integer id) throws Exception {
        adminService.removeRole(id);
        return feedback();
    }

    @RequestMapping(value = "/role")
    @AdminAuthentication(AdminPermission.ROLE_EDIT)
    public ModelAndView role(Integer id) throws Exception {
        return feedback(adminService.role(id));
    }

    @RequestMapping(value = "/roles")
    @AdminAuthentication(AdminPermission.ROLE_EDIT)
    public ModelAndView roles() throws Exception {
        return feedback(adminService.roles(true));
    }


    @RequestMapping(value = "/permissions")
    @AdminAuthentication(AdminPermission.ROLE_EDIT)
    public ModelAndView permissions() throws Exception {
        return feedback(AdminPermissionVO.initOmsPermissions());
    }

    @AdminAuthentication(value = AdminPermission.ROLE_EDIT)
    @RequestMapping(value = "change_role")
    public ModelAndView changeRole(Integer adminId, Integer roleId) throws Exception {
        adminService.changeRole(adminId, roleId);
        return feedback();
    }


    @RequestMapping(value = "change_password")
    @AdminAuthentication(AdminPermission.NONE)
    public ModelAndView changePassword(String oldPassword, String repeatPassword) throws Exception {
        adminService.changePassword(oldPassword, repeatPassword);
        return feedback();
    }


//    @RequestMapping(value = "/roles")
//    @AdminAuthentication(AdminPermission.ROLE_EDIT)
//    public ModelAndView roles() throws Exception {
//        return feedback(adminService.roles(true));
//    }


}
