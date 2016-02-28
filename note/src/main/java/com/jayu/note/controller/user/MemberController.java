package com.jayu.note.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jayu.note.common.Const;
import com.jayu.note.dao.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jayu.note.bmo.UserBmo;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.common.entity.JsonResponse;
import com.jayu.exception.BusinessException;
import com.jayu.exception.ResultConstant;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.web.AjaxUtils;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.MemberController")
@RequestMapping("/user/member/*")
public class MemberController extends BaseController {
    @Resource(name="UserBmoImpl")
    UserBmo userBmo;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listPage(Model model, HttpServletRequest request, HttpServletResponse response) {
    	
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	//必须是管理员才行
    	if(sessionAdmin.getAccountType()!=SysConstant.ACCOUNT_TYPE_STAFF){
    		return "/error/forbidden";
    	}
    	PageDomain<User> pd= new PageDomain<User>();
    	pd.notEquals("account_type", SysConstant.ACCOUNT_TYPE_STAFF);
		try {
			PageModel<User> userPage=userBmo.query(pd);
			model.addAttribute("userPage", userPage);
		} catch (BusinessException e) {
			log.error("userBmo.findDataByCondition,userId={}"+sessionAdmin.getId(), e);
			super.addHeadCode(response, ResultConstant.DAO_RESULT_FAILTURE);
		}
    	model.addAttribute(Const.MENU_CURRENT, Const.MENU_MEMBER);
    	if(AjaxUtils.isAjaxRequest(request)){
    		return "/user/member/list_ajax";
    	}
    	return "/user/member/list";
    }
    
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") long id,Model model, HttpServletRequest request, HttpServletResponse response) {
    	
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	//必须是管理员才行
    	if(sessionAdmin.getAccountType()!=SysConstant.ACCOUNT_TYPE_STAFF){
    		return "/error/forbidden";
    	}
		try {
			User user=userBmo.get(id);
			user.setPasswd(null);
			model.addAttribute("user", user);
		} catch (BusinessException e) {
			log.error("userBmo.findDataByCondition,userId={}"+sessionAdmin.getId(), e);
			super.addHeadCode(response, ResultConstant.DAO_RESULT_FAILTURE);
		}
    	model.addAttribute(Const.MENU_CURRENT, Const.MENU_MEMBER);
    	if(AjaxUtils.isAjaxRequest(request)){
    		return "/user/member/view";
    	}
    	return "/user/member/view";
    }
    
    @RequestMapping(value = "reset/{id}", method = RequestMethod.POST)
    public @ResponseBody JsonResponse reset(@PathVariable("id") long userId, HttpServletRequest request) {
    	
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	//必须是管理员才行
    	if(sessionAdmin.getAccountType()!=SysConstant.ACCOUNT_TYPE_STAFF){
    		return super.failed(ResultConstant.ACCESS_NOT_AUTHORITY_DATA);
    	}
		try {
			User user=userBmo.get(userId);
			if(null != user){
				if(user.getAccountType()==SysConstant.ACCOUNT_TYPE_STAFF){
		    		return super.failed(ResultConstant.ACCESS_NOT_AUTHORITY_DATA);
		    	}
				User userReset = new User();
				userReset.setUserId(user.getUserId());
				userReset.setPasswd(Const.CONST_RESET_PASSWORD);
				userReset.setLock(SysConstant.LOCK_ACTIVE);
				userBmo.update(userReset);
				return super.successed("重置成功！");
			}
			return super.failed("数据不存在，重置失败！");
		} catch (BusinessException e) {
			log.error("userBmo.ResetPasswd,userId={}"+sessionAdmin.getId(), e);
			return super.failed(ResultConstant.SERVICE_RESULT_FAILTURE);
		}
    }
    
    @RequestMapping(value = "del/{id}", method = RequestMethod.POST)
    public @ResponseBody JsonResponse del(@PathVariable("id") long userId, HttpServletRequest request) {
    	
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	//必须是管理员才行
    	if(sessionAdmin.getAccountType()!=SysConstant.ACCOUNT_TYPE_STAFF){
    		return super.failed(ResultConstant.ACCESS_NOT_AUTHORITY_DATA);
    	}
		try {
			User user=userBmo.get(userId);
			if(null != user){
				if(user.getAccountType()==SysConstant.ACCOUNT_TYPE_STAFF){
		    		return super.failed(ResultConstant.ACCESS_NOT_AUTHORITY_DATA);
		    	}
				userBmo.delUser(user);
				return super.successed("删除成功！");
			}
			return super.failed("数据不存在，删除失败！");
		} catch (BusinessException e) {
			log.error("userBmo.delUser,userId={}"+sessionAdmin.getId(), e);
			return super.failed(ResultConstant.SERVICE_RESULT_FAILTURE);
		}
    }
}
