package com.jayu.note.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jayu.note.common.Const;
import com.jayu.note.dao.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jayu.note.bmo.UserBmo;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.common.entity.JsonError;
import com.jayu.common.entity.JsonResponse;
import com.jayu.common.util.JsonUtil;
import com.jayu.exception.BusinessException;
import com.jayu.exception.ResultConstant;
import com.jayu.validator.exception.MethodNotJsonValidException;
import com.jayu.web.AjaxUtils;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.ProfileController")
@RequestMapping("/user/profile/*")
public class ProfileController extends BaseController {
    @Resource(name="UserBmoImpl")
    UserBmo userBmo;

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(Model model, HttpServletRequest request, HttpServletResponse response) {
    	
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
		try {
			User user=userBmo.get(sessionAdmin.getId());
			user.setPasswd(null);
			model.addAttribute("user", user);
		} catch (BusinessException e) {
			log.error("userBmo.findDataByCondition,userId={}"+sessionAdmin.getId(), e);
			super.addHeadCode(response, ResultConstant.DAO_RESULT_FAILTURE);
		}
    	model.addAttribute(Const.MENU_CURRENT, Const.MENU_PROFILE);
    	if(AjaxUtils.isAjaxRequest(request)){
    		return "/user/profile_info_ajax";
    	}
    	return "/user/profile_info";
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    //@JsonValidMethod
   // @LogOperatorAnn(code="logindo",desc="更新资料",level=LevelLog.DB)
    public @ResponseBody JsonResponse update(
    		HttpServletResponse response,
    //@JsonValid(value = "user.login.logindo",path=SysConstant.VALIDATOR_FILE_PATH) 
    @RequestBody Map<String,String> paramMap) throws MethodNotJsonValidException {
    	log.debug("param map={}", JsonUtil.toString(paramMap));
    	String email=paramMap.get("email");
    	String nick=paramMap.get("nick");
    	String oldPassword=paramMap.get("oldPassword");
    	String newPassword=paramMap.get("newPassword");
      	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	try {
    		List<JsonError> errorList = new ArrayList<JsonError>();
			User user=userBmo.get(sessionAdmin.getId());
			//更新密码
			if(StringUtils.isNotBlank(newPassword)){
				if(user.getPasswd().equals(oldPassword)){
					user.setPasswd(newPassword);
				}else{
					errorList.add(new JsonError("oldPassword","输入旧密码错误！"));
					return super.failed(errorList,ResultConstant.IN_PARAM_FAILTURE.getCode());
				}
			}
			if(StringUtils.isNotBlank(email)){
				user.setEmail(email);
			}
			if(StringUtils.isNotBlank(nick)){
				user.setNick(nick);
			}
			userBmo.update(user);
			return super.successed("更新成功!");
		} catch (BusinessException e) {
			log.debug("user login error", e);
	    	return super.failed("系统异常",ResultConstant.SYS_ERROR.getCode());
		}
    }
}
