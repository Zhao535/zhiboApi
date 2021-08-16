package cn.maidaotech.smartapi.api.article.controller;

import cn.maidaotech.smartapi.api.admin.model.AdminPermission;
import cn.maidaotech.smartapi.api.article.entity.ArticleQo;
import cn.maidaotech.smartapi.api.article.entity.ArticleWrapOption;
import cn.maidaotech.smartapi.api.article.model.Article;
import cn.maidaotech.smartapi.api.article.service.ArticleService;
import cn.maidaotech.smartapi.common.controller.AdminAuthentication;
import cn.maidaotech.smartapi.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("adm/article")
public class AdmArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @AdminAuthentication({AdminPermission.ARTICL_EDIT,AdminPermission.ARTICLE_LIST})
    @RequestMapping("save")
    public ModelAndView save(String article) throws Exception {
        Article article1 = parseModel(article, new Article(), "article");
        articleService.save(article1);
        return feedback();
    }

    @AdminAuthentication({AdminPermission.ARTICL_EDIT,AdminPermission.ARTICLE_LIST})
    @RequestMapping("item")
    public ModelAndView findById(Integer id) throws Exception {
        return feedback(articleService.item(id));
    }


    @RequestMapping("items")
    @AdminAuthentication({AdminPermission.ARTICL_EDIT,AdminPermission.ARTICLE_LIST})
    public ModelAndView items(String qo) throws Exception {
        return feedback(articleService.itemsForAdm(parseModel(qo, new ArticleQo(), "qo")));

    }

    @RequestMapping("changeStatus")
    @AdminAuthentication({AdminPermission.ARTICL_EDIT,AdminPermission.ARTICLE_LIST})
    public ModelAndView changeStatus(Integer id) throws Exception {
        articleService.changeStatus(id);
        return feedback();

    }
}