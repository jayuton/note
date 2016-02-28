package com.jayu.note.controller.user;

import javax.annotation.Resource;

import com.jayu.note.dao.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jayu.note.bmo.UserBmo;
import com.jayu.note.dto.AtUserDto;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.common.entity.JsonResponse;
import com.jayu.exception.BusinessException;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.AtUserController")
@RequestMapping("/user/at/*")
public class AtUserController extends BaseController {
    @Resource(name="UserBmoImpl")
    UserBmo userBmo;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public @ResponseBody JsonResponse list(@RequestParam("q")  String userName) {
    	
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
    	//必须是管理员才行
    	if(sessionAdmin.getAccountType()!=SysConstant.ACCOUNT_TYPE_STAFF){
    		return super.failed("");
    	}
    	PageDomain<User> pd= new PageDomain<User>();
    	pd.notEquals("account_type", SysConstant.ACCOUNT_TYPE_STAFF).and()
    	.like("account", "%"+userName+"%").and().notEquals("user_id", sessionAdmin.getId());
		try {
			PageModel<User> userPage=userBmo.query(pd);
			return super.successed(AtUserDto.convertFromUser(userPage.getList()));
		} catch (BusinessException e) {
			log.error("userBmo.findDataByCondition,userId={}"+sessionAdmin.getId(), e);
			return super.failed(e.getMessage());
		}
		
    }
    
    
}
