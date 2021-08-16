package cn.maidaotech.smartapi.api.secKill.controller;

import cn.maidaotech.smartapi.api.secKill.entity.SecKillQo;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillWrapOption;
import cn.maidaotech.smartapi.api.secKill.service.SecKillService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/usr/secKill")
@UserAuthentication
public class UsrSeckillController extends BaseController {

    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "/secKill")
    public ModelAndView item(Integer id) throws Exception {
        return feedback(secKillService.secKill(id, SecKillWrapOption.getUsrList()));
    }

    @RequestMapping(value = "/secKills")
    public ModelAndView items(String secKillQo) throws Exception {
        return feedback(secKillService.secKillsForUsr(parseModel(secKillQo, new SecKillQo(), "qo")));
    }

    @RequestMapping(value = "/select_secKill_stock")
    public ModelAndView selectSecKillStock(Integer id, String sno) throws Exception {
        return feedback(secKillService.num(id, sno));
    }
    @RequestMapping(value = "/modifyStock")
    public ModelAndView modifyStock(Integer id, String sno) throws Exception {
        secKillService.modifyStock(id, sno);
        return feedback();
    }
    @RequestMapping(value = "/secKill_by_product_id")
    public ModelAndView secKillByProductId(Integer id) throws Exception {
        return feedback(secKillService.secKillByProductId(id));
    }
}
