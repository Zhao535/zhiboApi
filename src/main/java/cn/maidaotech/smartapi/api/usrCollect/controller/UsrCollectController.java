package cn.maidaotech.smartapi.api.usrCollect.controller;

import cn.maidaotech.smartapi.api.usrCollect.entity.CollectQo;
import cn.maidaotech.smartapi.api.usrCollect.model.Collect;
import cn.maidaotech.smartapi.api.usrCollect.service.CollectService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usr/collect")
public class UsrCollectController extends BaseController {

    @Autowired
    private CollectService collectService;

    @RequestMapping("save")
    @UserAuthentication
    public ModelAndView save(String collect) throws Exception {
        collectService.save(parseModel(collect, new Collect(), "collect"));
        return feedback();
    }


    @RequestMapping("item")
    @UserAuthentication
    public ModelAndView item(Byte type, Integer fromId) throws Exception {
        return feedback(collectService.item(type, fromId));
    }
    //type： 0 商品 1 文章 2 情景购 3 商户

    @RequestMapping("items")
    @UserAuthentication
    public ModelAndView items(String qo) throws Exception {
        return feedback(collectService.items(parseModel(qo, new CollectQo(), "qo")));
    }

    @RequestMapping("isCollect")
    @UserAuthentication
    public ModelAndView isCollect(Byte type, Integer fromId) throws Exception {
        return feedback(collectService.isCollect(type, fromId));
    }

    @RequestMapping("collectNum")
    @UserAuthentication
    public ModelAndView collectNum() throws Exception {
        return feedback(collectService.collectNum());
    }

    @RequestMapping("focusNum")
    @UserAuthentication
    public ModelAndView focusNum() throws Exception {
        return feedback(collectService.focusNum());
    }

}
