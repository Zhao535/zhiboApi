package cn.maidaotech.smartapi.api.ui.controller;

import cn.maidaotech.smartapi.api.ui.service.UiService;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usr/ui")
public class UsrUIController extends BaseController {

    @Autowired
    private UiService uiService;

    @RequestMapping("item")
    public ModelAndView item(Byte type) throws Exception{
        return feedback(uiService.itemForUsr(type));
    }

}
