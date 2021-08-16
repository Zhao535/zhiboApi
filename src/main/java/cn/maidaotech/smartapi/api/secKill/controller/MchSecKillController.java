package cn.maidaotech.smartapi.api.secKill.controller;

import cn.maidaotech.smartapi.api.secKill.entity.SecKillQo;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillWrapOption;
import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import cn.maidaotech.smartapi.api.secKill.service.SecKillService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.MerchantAdminAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/mch/secKill")
public class MchSecKillController extends BaseController {
    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "secKill")
    @MerchantAdminAuthentication
    public ModelAndView item(Integer id) throws Exception {
        return feedback(secKillService.secKill(id, SecKillWrapOption.getMchList()));
    }

    @RequestMapping(value = "secKills")
    @MerchantAdminAuthentication
    public ModelAndView items(String secKillQo) throws Exception {
        return feedback(secKillService.secKillsForMch(parseModel(secKillQo, new SecKillQo(), "qo")));
    }



}
