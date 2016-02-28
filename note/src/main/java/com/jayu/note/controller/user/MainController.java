package com.jayu.note.controller.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jayu.note.bmo.ArticeAtUserBmo;
import com.jayu.note.common.Const;
import com.jayu.note.dao.model.ArticeAtUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jayu.note.bmo.ArticleBmo;
import com.jayu.note.dao.model.Article;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.exception.BusinessException;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.MainController")
@RequestMapping("/user/main")
public class MainController extends BaseController {
	@Resource(name="ArticleBmoImpl")
	ArticleBmo articleBmo;
	@Resource(name="ArticeAtUserBmoImpl")
	ArticeAtUserBmo articeAtUserBmo;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String loginPage(Model model, HttpServletRequest request) {
    	model.addAttribute(Const.MENU_CURRENT, Const.MENU_HOME);
       	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
       	List<Long> articleIdList = new ArrayList<Long>();
    	
    	try {
    		PageDomain<ArticeAtUser> pd = new PageDomain<ArticeAtUser>();
    		pd.notEquals("biz_id", sessionAdmin.getId()).and().equals("type", SysConstant.AT_FRIEND);
    		List<ArticeAtUser> articeAtUserList = articeAtUserBmo.findDataByCondition(pd);
    		if(null !=articeAtUserList){
    			for(ArticeAtUser articeAtUser:articeAtUserList){
    				if(!articleIdList.contains(articeAtUser.getArticleId())){
    					articleIdList.add(articeAtUser.getArticleId());
    				}
    			}
    		}
    		pd.clear();
    		pd.notEquals("biz_id", sessionAdmin.getId()).and().in("type", new Integer[]{SysConstant.AT_ALL_MEMBER,SysConstant.AT_ALL_GUEST}); 
    		articeAtUserList = articeAtUserBmo.findDataByCondition(pd);
    		if(null !=articeAtUserList){
    			for(ArticeAtUser articeAtUser:articeAtUserList){
    				if(!articleIdList.contains(articeAtUser.getArticleId())){
    					articleIdList.add(articeAtUser.getArticleId());
    				}
    				
    			}
    		}
    		if(articleIdList.size()>50){
    			Collections.sort(articleIdList,new Comparator<Long>(){
    	            public int compare(Long arg0, Long arg1) {
    	            	if(arg0>arg1)return 1;
    	            	if(arg0<arg1)return -1;
    	                return 0;  
    	            }  
    	        });
    			articleIdList=articleIdList.subList(0, 50);
    		};
    		if(articleIdList.size()>0){
    			PageDomain<Article> pdArticle = new PageDomain<Article>();
    			pdArticle.notEquals("author_id", sessionAdmin.getId()).and().in("id", articleIdList)
    			.orderBy("write_time DESC"); 
        		List<Article> articleList = articleBmo.findDataByCondition(pdArticle);
        		model.addAttribute("articleList", articleList);
    		}
		} catch (BusinessException e) {
			log.error(e);
		}
    	return "/user/main";
    }
}
