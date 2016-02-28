package com.jayu.note.controller.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jayu.note.bmo.CategoryBmo;
import com.jayu.note.common.Const;
import com.jayu.note.dao.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jayu.note.bmo.ArticeAtUserBmo;
import com.jayu.note.bmo.ArticleBmo;
import com.jayu.note.bmo.ArticleContentBmo;
import com.jayu.note.dao.model.ArticeAtUser;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.ArticleContent;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.common.entity.JsonResponse;
import com.jayu.common.util.DateUtil;
import com.jayu.common.util.EncodeUtils;
import com.jayu.common.util.StringUtil;
import com.jayu.exception.BusinessException;
import com.jayu.web.AjaxUtils;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.ArticleController")
@RequestMapping("/user/article/*")
public class ArticleController extends BaseController {
	@Resource(name="CategoryBmoImpl")
	CategoryBmo categoryBmo;
	@Resource(name="ArticleBmoImpl")
	ArticleBmo articleBmo;
	@Resource(name="ArticeAtUserBmoImpl")
	ArticeAtUserBmo articeAtUserBmo;
	@Resource(name="ArticleContentBmoImpl")
	ArticleContentBmo articleContentBmo;
	
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addPage(Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
		try {
			Category category = categoryBmo.getDefaultCategory(sessionAdmin.getId());
			if(category != null){
				model.addAttribute("category", category);
			}
		} catch (BusinessException e) {
			log.error("userId="+sessionAdmin.getId(), e);
		}
    	
    	model.addAttribute(Const.MENU_CURRENT, Const.MENU_ARTICLE_ADD);
    	if(AjaxUtils.isAjaxRequest(request)){
    		return "/user/article/add_ajax";
    	}
    	return "/user/article/add";
    }
//    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
//    public String addPage(@PathVariable("id") long id, Model model, HttpServletRequest request) {
//    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
//                SysConstant.SESSION_KEY_LOGIN_USER);
//    	try {
//			Article article = articleBmo.get(id,sessionAdmin.getId());
//			if(null != article){
//				List<Category> categoryList =categoryBmo.getParents(article.getCateId());
//				if(null != categoryList && categoryList.size() > 0){
//					article.setCateName(categoryList.get(0).getName());
//					StringBuilder cateNamePath = new StringBuilder();
//					int size = categoryList.size();
//					for(int i =size-1;i>=0;i--){
//						Category category = categoryList.get(i);
//						cateNamePath.append(category.getName());
//						if(i>0){
//							cateNamePath.append("->");
//						}
//					}
//					 model.addAttribute("cateNamePath", cateNamePath);
//				}
//				List<ArticeAtUser> articeAtUserList = articeAtUserBmo.findDataByCondition(id);
//				if(null !=articeAtUserList && articeAtUserList.size() > 0){
//					List<AtUserDto>  atUserDtoList = AtUserDto.convertFromAtUser(articeAtUserList);
//					model.addAttribute("articeAtUserJson", JsonUtil.toString(atUserDtoList));
//					model.addAttribute("articeAtUserStr", AtUserDto.convertToAccountStr(atUserDtoList));
//				}
//				
//				model.addAttribute("article", article);
//			}
//			
//			
//		} catch (BusinessException e) {
//			log.error("获取文章详情失败 userId="+sessionAdmin.getId(), e);
//		}
//    	return "/user/article/edit";
//    }
//    
    @RequestMapping(value = "loadEdit/{id}", method = RequestMethod.GET)
    public @ResponseBody JsonResponse  loadEdit(@PathVariable("id") long id, Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
    	try {
			Article article = articleBmo.get(id,sessionAdmin.getId());
			if(null != article){
				ArticleContent articleContent = articleContentBmo.get(article.getId());
				if(null != articleContent){
					article.setContent(articleContent.getContent());
				}
				List<ArticeAtUser> articeAtUserList = articeAtUserBmo.findDataByCondition(id);
				article.setArticeAtUserList(articeAtUserList);
				return super.successed(article);
			}
			
			
		} catch (BusinessException e) {
			log.error("获取文章详情失败 userId="+sessionAdmin.getId(), e);
		}
    	return super.failed("");
    }
    
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public  @ResponseBody JsonResponse save(@RequestBody Article article, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);

    	article.setAuthorId(sessionAdmin.getId());
    	article.setAuthorName(sessionAdmin.getName());
    	article.setModifyTime(new Date());
    	article.setPrivilegeType(3);
    	article.setWriteTime(article.getModifyTime());
    	article.setWriteDay(DateUtil.nowDayOfMonth());
    	article.setWriteMonth(DateUtil.nowMonth());
    	article.setWriteYear(DateUtil.nowYear());
    	try {
			// 去掉所有html元素,   
    		article.setContent(StringUtil.removeHtml(EncodeUtils.urlDecode(article.getContent())));
			article.setShortContent(StringUtil.bSubstring(article.getContent(), 200));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			articleBmo.save(article);
		} catch (BusinessException e) {
			log.error("保存文章失败 userId="+sessionAdmin.getId(), e);
			return super.failed("保存异常！");
		}
    	return super.successed(article);
    }
    
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public  @ResponseBody JsonResponse update(@RequestBody Article article, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
  
    	try {
    		Article articleDB = articleBmo.get(article.getId(),sessionAdmin.getId());
    		if(null != articleDB){
    			Article newArticle = new Article();
    			newArticle.setModifyTime(new Date());
    			newArticle.setCateId(article.getCateId());
    			newArticle.setTags(article.getTags());
    			newArticle.setTitle(article.getTitle());
    			newArticle.setArticeAtUserList(article.getArticeAtUserList());
    			newArticle.setContent(StringUtil.removeHtml(EncodeUtils.urlDecode(article.getContent())));
    			newArticle.setShortContent(StringUtil.bSubstring(newArticle.getContent(), 200));
    			newArticle.setId(articleDB.getId());
    			newArticle.setAuthorId(articleDB.getAuthorId());
    			articleBmo.edit(newArticle,articleDB.getCateId());
    			return super.successed(newArticle.getId());
    		}
		} catch (BusinessException e) {
			log.error("保存文章失败 userId="+sessionAdmin.getId(), e);
		} catch (Exception e) {
			log.error("保存文章失败 userId="+sessionAdmin.getId(), e);
		}	
    	return super.failed("保存异常！");
    }
    
    @RequestMapping(value = "del/{id}", method = RequestMethod.POST)
    public  @ResponseBody JsonResponse del(@PathVariable("id") long id, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
    	try {
			Article article= articleBmo.get(id, sessionAdmin.getId());
			if(null !=article){
				articleBmo.deleteArticle(article);
			}
			return super.successed("删除成功！");
		} catch (BusinessException e) {
			log.error("保存文章失败 userId="+sessionAdmin.getId(), e);
			return super.failed("保存异常！");
		}
    	
    }
}
