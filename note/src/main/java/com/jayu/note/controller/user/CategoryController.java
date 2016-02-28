package com.jayu.note.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jayu.note.bmo.CategoryBmo;
import com.jayu.note.common.Const;
import com.jayu.note.dao.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.exception.BusinessException;
import com.jayu.web.AjaxUtils;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.CategoryController")
@RequestMapping("/user/category/*")
public class CategoryController extends BaseController {
	@Resource(name="CategoryBmoImpl")
	CategoryBmo categoryBmo;

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String loginPage(Model model, HttpServletRequest request) {
     	model.addAttribute(Const.MENU_CURRENT, Const.MENU_CATEGORY);
     	if(AjaxUtils.isAjaxRequest(request)){
     		return "/user/category/view_ajax";
     	}
    	return "/user/category/view";
    }
    
    @RequestMapping(value = "nodeChildren", method = RequestMethod.GET)
    public @ResponseBody Map nodeChildren(@RequestParam("id") int id) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	//查找所有根类目
    	if(id <=0){
    		id=0;
    	}
    	Map nodes = new HashMap();
    	try {
    		List<Category> categoryList = categoryBmo.getChildrenByParentId(sessionAdmin.getId(),id);
    		nodes.put("nodes", categoryList);
		} catch (BusinessException e) {
			log.error("userId="+sessionAdmin.getId(), e);
		}
    	return nodes;
    }
    
    @RequestMapping(value = "nodeUpdate", method = RequestMethod.POST)
    public @ResponseBody Map nodeUpdate(@RequestParam("id") int id,@RequestParam("parent") int parentId,
    		@RequestParam("name") String name) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	Map nodes = new HashMap();
    	try {
    		Category category = categoryBmo.getCategoryById(sessionAdmin.getId(),id);
    		if(null != category){
    			category.setName(name);
    			categoryBmo.update(category);
    			nodes.put("id", id);
    			nodes.put("name", name);
    		}
		} catch (BusinessException e) {
			log.error("userId="+sessionAdmin.getId(), e);
		}
    	return nodes;
    }
    
    @RequestMapping(value = "nodeCreate", method = RequestMethod.POST)
    public @ResponseBody Map nodeCreate(@RequestParam("parent") int parentId,
    		@RequestParam("name") String name,@RequestParam("position") String position,
    		@RequestParam("related") int related) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	Map nodes = new HashMap();
    	try {
    		Category category = new Category();
    		Category relateCategory = categoryBmo.getCategoryById(sessionAdmin.getId(),related);
    		if(null != relateCategory){
    			if("before".equals(position)){
    				category.setSortOrder(relateCategory.getSortOrder()-1);
    				category.setLevel(relateCategory.getLevel());
    			}else if("after".equals(position)){
    				category.setSortOrder(relateCategory.getSortOrder());
    				category.setLevel(relateCategory.getLevel());
    			}else if("firstChild".equals(position)){
    				category.setSortOrder(1);
    				category.setLevel(relateCategory.getLevel()+1);
    			}else if("lastChild".equals(position)){
    				category.setSortOrder(relateCategory.getSortOrder()+1);
    				category.setLevel(relateCategory.getLevel()+1);
    			}
    		}else{
    			category.setLevel(0);
    			category.setSortOrder(1);
    		}
    		
    		category.setName(name);
    		category.setParentId(parentId);
    		category.setUserId(sessionAdmin.getId());
    		category.setCreateDt(new Date());
    		
    		categoryBmo.save(category);
    		nodes.put("id", category.getId());
			nodes.put("name", name);
    		
		} catch (BusinessException e) {
			log.error("userId="+sessionAdmin.getId(), e);
		}
    	return nodes;
    }
    
    
    @RequestMapping(value = "nodeDelete", method = RequestMethod.POST)
    public @ResponseBody Map nodeDelete(@RequestParam("id") int id) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	Map nodes = new HashMap();
    	try {
    		Category category = categoryBmo.getCategoryById(sessionAdmin.getId(),id);
    		if(null != category){
    			categoryBmo.deleteCategory(category);
    		}
		} catch (BusinessException e) {
			log.error("userId="+sessionAdmin.getId(), e);
		}
    	return nodes;
    }
    
    @RequestMapping(value = "nodeMove", method = RequestMethod.POST)
    public @ResponseBody Map nodeMove(@RequestParam("id") int id,@RequestParam("position") String position,
    		@RequestParam("related") int related) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	Map nodes = new HashMap();
    	try {
    		Category category = categoryBmo.getCategoryById(sessionAdmin.getId(),id);
    		Category relateCategory = categoryBmo.getCategoryById(sessionAdmin.getId(),related);
    		int minSortOrder = -1;
    		if( null != relateCategory){
    			if("before".equals(position)){
    				category.setSortOrder(relateCategory.getSortOrder());
    				category.setLevel(category.getLevel());
    				minSortOrder=relateCategory.getSortOrder();
    			}else if("after".equals(position)){
    				category.setSortOrder(relateCategory.getSortOrder()+1);
    				category.setLevel(category.getLevel());
    				minSortOrder = relateCategory.getSortOrder();
    			}else if("firstChild".equals(position)){
    				minSortOrder=1;
    				category.setSortOrder(minSortOrder);
    				category.setLevel(category.getLevel()+1);
    			}else if("lastChild".equals(position)){
    				category.setSortOrder(relateCategory.getSortOrder()+1);
    				category.setLevel(category.getLevel()+1);
    			}else{
    				return null;
    			}
    			category.setParentId(relateCategory.getParentId());
    		}else{
    			category.setLevel(1);
    			category.setSortOrder(1);
    		}
   
    		categoryBmo.update(category,minSortOrder,true,false);
    		
		} catch (BusinessException e) {
			log.error("userId="+sessionAdmin.getId(), e);
		}
    	return nodes;
    }
    
}
