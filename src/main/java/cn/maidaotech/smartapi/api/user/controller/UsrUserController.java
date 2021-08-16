package cn.maidaotech.smartapi.api.user.controller;


import cn.maidaotech.smartapi.api.user.model.User;
import cn.maidaotech.smartapi.api.user.service.UserService;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "usr/user")
public class UsrUserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "save")
    public ModelAndView save(String user, String code) throws Exception {
        userService.save(parseModel(user, new User(), "user"), code);
        return feedback();
    }

    @RequestMapping(value = "signin")
    public ModelAndView sigin(String mobile, String password, String code) throws Exception {
        return feedback(userService.signIn(mobile, password, code));
    }

    @RequestMapping(value = "updatePassword")
    public ModelAndView updatePassword(String username, String password, String newPassword) throws Exception {
        userService.updatePassword(username, password, newPassword);
        return feedback();
    }

    @UserAuthentication
    @RequestMapping(value = "profile")
    public ModelAndView profile() throws Exception {
        Integer id = UserContexts.requestUser().getId();
        return feedback(userService.item(id));
    }

    @RequestMapping(value = "real_name")
    public ModelAndView identity(String frontPath, String backPath) throws Exception {
        return feedback();
    }

    @RequestMapping(value = "send_signin_code")
    public ModelAndView sms(String mobile) throws Exception {
        userService.sendCode(mobile);
        return feedback();
    }

    @RequestMapping(value = "register_code")
    public ModelAndView register(String mobile) throws Exception {
        userService.sendRegisterCode(mobile);
        return feedback();
    }

    @RequestMapping("change_mobile")
    public ModelAndView changeMobile(String mobile, String newMobile, String code) throws Exception {
        userService.changeMobile(mobile, newMobile, code);
        return feedback();
    }

    @RequestMapping("send_change_mobile_code")
    public ModelAndView sendChangeMobileCode(String mobile) throws Exception {
        userService.sendCode(mobile);
        return feedback();
    }


}
