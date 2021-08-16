package cn.maidaotech.smartapi.api.merchantAdmin.controller;

import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminPermission;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminPermissionVo;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminRoleQo;
import cn.maidaotech.smartapi.api.merchantAdmin.entity.MerchantAdminSessionQo;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdmin;
import cn.maidaotech.smartapi.api.merchantAdmin.model.MerchantAdminRole;
import cn.maidaotech.smartapi.api.merchantAdmin.service.MerchantAdminService;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("mch/merchantAdmin")
public class MchMerchantAdminController extends BaseController {


    @Autowired
    private MerchantAdminService merchantAdminService;


    @RequestMapping(value = "changeStatus")
    @MerchantAdminAuthentication
    public ModelAndView changeStatus(Integer id) throws Exception {
        merchantAdminService.changeStatus(id);
        return feedback();
    }

    @RequestMapping(value = "signin")
    public ModelAndView signin(String mobile, String password) throws Exception {
        return feedback(merchantAdminService.signin(mobile, password));
    }

    @RequestMapping(value = "profile")
    @MerchantAdminAuthentication
    public ModelAndView profile() throws Exception {
        return feedback(merchantAdminService.getById(MerchantAdminContexts.requestAdminId()));
    }


    @RequestMapping(value = "save")
    @MerchantAdminAuthentication(MerchantAdminPermission.ROLE_LIST)
    public ModelAndView save(String merchantAdmin) throws Exception {
        MerchantAdmin newMerchantAdmin = parseModel(merchantAdmin, new MerchantAdmin(), "merchantAdmin");
        newMerchantAdmin.setMerchantId(MerchantAdminContexts.requireMerchant().getId());
        merchantAdminService.saveMerchantAdmin(newMerchantAdmin);
        return feedback();
    }

    @RequestMapping(value = "items")
    @MerchantAdminAuthentication
    public ModelAndView item( ) throws Exception {
        return feedback(merchantAdminService.itemsForMerchantAdmin());
    }

    @MerchantAdminAuthentication
    @RequestMapping("signin_log")
    public ModelAndView signinLog(String qo) throws Exception {
        return feedback(merchantAdminService.singInLog(parseModel(qo, new MerchantAdminSessionQo(), "qo")));
    }

    @RequestMapping(value = "findMerchantAdmins")
    @MerchantAdminAuthentication
    public ModelAndView findMerchantAdmins() throws Exception {
        return feedback(merchantAdminService.findMerchantAdmins(MerchantAdminContexts.requestAdminId()));
    }

    @RequestMapping(value = "reset_password")
    @MerchantAdminAuthentication(MerchantAdminPermission.ROLE_LIST)
    public ModelAndView resetPassword(String mobile, String newPassword) throws Exception {
        merchantAdminService.resetPassword(mobile, newPassword);
        return feedback();
    }

    @RequestMapping(value = "roles")
    @MerchantAdminAuthentication(MerchantAdminPermission.ROLE_LIST)
    public ModelAndView roles(String qo) throws Exception {
        return feedback(merchantAdminService.roles(parseModel(qo, new MerchantAdminRoleQo(), "qo")));
    }

    @RequestMapping(value = "role_list")
    @MerchantAdminAuthentication(MerchantAdminPermission.ROLE_LIST)
    public ModelAndView roleList() throws Exception {
        return feedback(merchantAdminService.roleList());
    }

    @MerchantAdminAuthentication(value = MerchantAdminPermission.ROLE_EDIT)
    @RequestMapping("role")//查看单个权限组
    public ModelAndView role(Integer id) throws Exception {
        return feedback(merchantAdminService.role(id));
    }

    @MerchantAdminAuthentication(value = MerchantAdminPermission.ROLE_EDIT)
    @RequestMapping("permissions")//查看所有权限
    public ModelAndView permissions() throws Exception {
        return feedback(MerchantAdminPermissionVo.initOmsPermissions());
    }

    @MerchantAdminAuthentication(value = MerchantAdminPermission.ROLE_EDIT)
    @RequestMapping("save_role")//新建权限
    public ModelAndView saveRole(String role) throws Exception {
        merchantAdminService.saveRole(parseModel(role, new MerchantAdminRole(), "role"));
        return feedback();
    }

    @MerchantAdminAuthentication(value = MerchantAdminPermission.ROLE_LIST)
    @RequestMapping("remove_role")//删除权限
    public ModelAndView removeRole(Integer id) throws Exception {
        merchantAdminService.removeRole(id);
        return feedback();
    }
}
