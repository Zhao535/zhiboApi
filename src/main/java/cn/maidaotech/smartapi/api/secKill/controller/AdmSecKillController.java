package cn.maidaotech.smartapi.api.secKill.controller;

import cn.maidaotech.smartapi.api.secKill.entity.SecKillQo;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillWrapOption;
import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import cn.maidaotech.smartapi.api.secKill.service.SecKillService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/adm/secKill")
public class AdmSecKillController extends BaseController {

    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "secKill")
    @AdminAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(secKillService.secKill(id, SecKillWrapOption.getAdmList()));
    }

    @RequestMapping(value = "save")
    @AdminAuthentication
    public ModelAndView save(String secKill) throws Exception {
        secKillService.save(parseModel(secKill, new SecKill()));
        return feedback();
    }

    @RequestMapping(value = "changeStatus")
    @AdminAuthentication
    public ModelAndView changeStatus(Integer id) throws Exception {
        secKillService.changeStatus(id);
        return feedback();
    }

    @RequestMapping(value = "secKills")
    @AdminAuthentication
    public ModelAndView items(String secKillQo) throws Exception {
        return feedback(secKillService.secKillsForAdm(parseModel(secKillQo, new SecKillQo(), "qo")));
    }


}
