package com.jayu.note.controller.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jayu.note.bmo.ArticleBmo;
import com.jayu.note.bmo.ArticleContentBmo;
import com.jayu.note.bmo.CategoryBmo;
import com.jayu.note.common.Const;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.ArticleContent;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jayu.exception.BusinessException;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.web.AjaxUtils;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.ArticlePageController")
@RequestMapping("/user/artpage/*")
public class ArticlePageController extends BaseController {
	@Resource(name="CategoryBmoImpl")
	CategoryBmo categoryBmo;
	@Resource(name="ArticleBmoImpl")
	ArticleBmo articleBmo;
	
	@Resource(name="ArticleContentBmoImpl")
	ArticleContentBmo articleContentBmo;
	
	@RequestMapping(value = "view/{id:\\d+}", method = RequestMethod.GET)
    public String view(@PathVariable("id") int id,
    		Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
    	PageDomain<Article>  pd = new PageDomain<Article>();
    	pd.equals("author_id", sessionAdmin.getId()).and().equals("id", id);
    	try {
			List<Article> aticleList= articleBmo.findDataByCondition(pd);
			if(null != aticleList && aticleList.size() > 0){
				Article article = aticleList.get(0);
				ArticleContent articleContent = articleContentBmo.get(article.getId());
				if(null != articleContent){
					article.setContent(articleContent.getContent());
				}
				model.addAttribute("article", article);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(AjaxUtils.isAjaxRequest(request)){
    		return "/user/article/view_ajax";
    	}
    	return "/user/article/view";
    }

    @RequestMapping(value = "day/{yyyy:\\d+}-{mm:\\d+}-{dd:\\d+}", method = RequestMethod.GET)
    public String dayOfPage(@PathVariable("yyyy") int year,
    		@PathVariable("mm") int month,
    		@PathVariable("dd") int day,
    		Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
    	PageDomain<Article>  pd = new PageDomain<Article>();
    	pd.equals("author_id", sessionAdmin.getId()).and().equals("write_year", year).and().equals("write_month", month).and().equals("write_day",day)
    	.orderBy("write_time DESC");
    	try {
			List<Article> aticleList= articleBmo.findDataByCondition(pd);
			model.addAttribute("articleList", aticleList);
			model.addAttribute("archive_time", year+"-"+month+"-"+day);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	model.addAttribute(Const.MENU_CURRENT, Const.MENU_ARCHIVE_DAY);
    	if(AjaxUtils.isAjaxRequest(request)){
    		return "/user/article/page_day_list_ajax";
    	}
    	return "/user/article/page_day_list";
    }
    
    @RequestMapping(value = "month/{yyyy:\\d+}-{mm:\\d+}", method = RequestMethod.GET)
    public String monthOfPage(@PathVariable("yyyy") int year,
    		@PathVariable("mm") int month,
    		Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
    	PageDomain<Article>  pd = new PageDomain<Article>();
    	pd.equals("author_id", sessionAdmin.getId()).and().equals("write_year", year).and().equals("write_month", month)
    	.orderBy("write_time DESC");
    	try {
			List<Article> aticleList= articleBmo.findDataByCondition(pd);
			model.addAttribute("articleList", aticleList);
			model.addAttribute("archive_time", year+"-"+month);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	model.addAttribute(Const.MENU_CURRENT, Const.MENU_ARCHIVE_MONTH);
    	if(AjaxUtils.isAjaxRequest(request)){
    		return "/user/article/page_month_list_ajax";
    	}
    	return "/user/article/page_month_list";
    }
    
    @RequestMapping(value = "cate/{id}", method = RequestMethod.GET)
    public String categoryOfPage(@PathVariable("id") int cateId,
    		Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
    	PageDomain<Article>  pd = new PageDomain<Article>();
    	pd.equals("author_id", sessionAdmin.getId()).and().equals("cate_id", cateId)
    	.orderBy("write_time DESC");
    	try {
			List<Article> aticleList= articleBmo.findDataByCondition(pd);
			model.addAttribute("articleList", aticleList);
			if(null != aticleList && aticleList.size() > 0){
				model.addAttribute("category", categoryBmo.get(aticleList.get(0).getCateId()));
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	model.addAttribute(Const.MENU_CURRENT, Const.MENU_ARTICLE_CATEGORY);
    	if(AjaxUtils.isAjaxRequest(request)){
    		return "/user/article/page_cate_list_ajax";
    	}
    	return "/user/article/page_cate_list";
    }
    
    @RequestMapping(value = "/title/cate/{id}", method = RequestMethod.GET)
    public String articleTitleOfPage(@PathVariable("id") int cateId,
    		Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
    	PageDomain<Article>  pd = new PageDomain<Article>();
    	pd.equals("author_id", sessionAdmin.getId()).and().equals("cate_id", cateId)
    	.orderBy("write_time DESC");
    	try {
			List<Article> aticleList= articleBmo.findDataByCondition(pd);
			model.addAttribute("articleList", aticleList);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "/user/article/article_title_list_ajax";
    }
}
