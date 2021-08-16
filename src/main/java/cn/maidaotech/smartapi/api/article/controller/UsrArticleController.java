package cn.maidaotech.smartapi.api.article.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.article.entity.ArticleQo;
import cn.maidaotech.smartapi.api.article.service.ArticleService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import cn.maidaotech.smartapi.common.controller.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("usr/article")
public class UsrArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("items")
    public ModelAndView items(String qo) throws Exception {
        return feedback(articleService.items(parseModel(qo, new ArticleQo(), "qo")));

    }
    @RequestMapping("item")
    public ModelAndView item(Integer id) throws Exception {
        return feedback(articleService.item(id));

    }

}
