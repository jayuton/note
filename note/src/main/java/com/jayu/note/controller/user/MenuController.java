package com.jayu.note.controller.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jayu.note.bmo.ArticleArchiveBmo;
import com.jayu.note.bmo.CategoryBmo;
import com.jayu.note.dao.model.ArticleArchive;
import com.jayu.note.dao.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.exception.BusinessException;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.MenuController")
@RequestMapping("/user/menu/*")
public class MenuController extends BaseController {
	@Resource(name="CategoryBmoImpl")
	CategoryBmo categoryBmo;
	
	@Resource(name="ArticleArchiveBmoImpl")
	ArticleArchiveBmo articleArchiveBmo;
	
	@RequestMapping(value = "leftMenu", method = RequestMethod.GET)
    public String leftMenu(Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	try {
    		List<Category> categoryList = categoryBmo.getAllCategoryList(sessionAdmin.getId());
    		
    		List<ArticleArchive> articleArchiveOfDayList = articleArchiveBmo.findDataOfDayByUserId(sessionAdmin.getId());
    		List<ArticleArchive> articleArchiveOfMonthList = articleArchiveBmo.findDataOfMonthByUserId(sessionAdmin.getId());
    		List<ArticleArchive> articleArchiveOfCateList = articleArchiveBmo.findDataOfCateByUserId(sessionAdmin.getId());
    		if(null != categoryList && null !=articleArchiveOfCateList){
    			for(Category category:categoryList){
    				for(ArticleArchive articleArchive:articleArchiveOfCateList){
    					if(category.getId().intValue()==articleArchive.getArchiveCate().intValue()){
    						category.setArticleNum(articleArchive.getArchiveNumber());
    						break;
    					}
        			}
    			}
    		}
    		model.addAttribute("categoryList", categoryList);
    		model.addAttribute("articleArchiveOfDayList", articleArchiveOfDayList);
    		model.addAttribute("articleArchiveOfMonthList", articleArchiveOfMonthList);
    	} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "/user_left_menu";
    }
    
	
   
    
}
