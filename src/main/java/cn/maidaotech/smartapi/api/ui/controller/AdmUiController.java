package cn.maidaotech.smartapi.api.ui.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.ui.entity.UIQo;
import cn.maidaotech.smartapi.api.ui.model.UI;
import cn.maidaotech.smartapi.api.ui.service.UiService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("adm/ui")
public class AdmUiController extends BaseController {

    @Autowired
    private UiService uiService;

    @RequestMapping("save")
    @AdminAuthentication(AdminPermission.MERCHANT_EDIT)
    public ModelAndView save(String ui) throws Exception {
        uiService.save(parseModel(ui, new UI(), "ui"));
        return feedback();
    }

    @RequestMapping("uis")
    @AdminAuthentication(AdminPermission.MERCHANT_EDIT)
    public ModelAndView items(String qo) throws Exception {
        return feedback(uiService.items(parseModel(qo,new UIQo(),"qo")));
    }
    @RequestMapping("ui")
    @AdminAuthentication(AdminPermission.MERCHANT_EDIT)
    public ModelAndView item(Integer id) throws Exception {
        return feedback(uiService.item(id));
    }

    @RequestMapping("set_default")
    @AdminAuthentication(AdminPermission.MERCHANT_EDIT)
    public ModelAndView setDefault(Integer id) throws Exception {
        uiService.uiDefault(id);
        return feedback();
    }

}
