package com.jayu.note.controller.guest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jayu.note.bmo.ArticeAtUserBmo;
import com.jayu.note.bmo.ArticleBmo;
import com.jayu.note.bmo.ArticleContentBmo;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.ArticleContent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jayu.exception.BusinessException;
import com.jayu.web.spring.annotation.session.AuthorityValid;
import com.jayu.web.spring.controller.BaseController;

@Controller("guest.ArticleController")
@RequestMapping("/guest/article/*")
public class ArticleController extends BaseController {
	@Resource(name="ArticleBmoImpl")
	ArticleBmo articleBmo;
	@Resource(name="ArticeAtUserBmoImpl")
	ArticeAtUserBmo articeAtUserBmo;
	@Resource(name="ArticleContentBmoImpl")
	ArticleContentBmo articleContentBmo;
	
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	@AuthorityValid(isDataCheck=true,isNeedSession=false)
    public String loginPage(@PathVariable("id") long id,Model model, HttpServletRequest request) {
		try {
			Article article = articleBmo.get(id);
			if(null != article){
				ArticleContent articleContent = articleContentBmo.get(article.getId());
				if(null != articleContent){
					article.setContent(articleContent.getContent());
				}
				model.addAttribute("article", article);
			}
		} catch (BusinessException e) {
			log.error("获取文章详情失败 id="+id, e);
		}
    	return "/guest/article_detail";
    }
}
