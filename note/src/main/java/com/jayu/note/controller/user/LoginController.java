package com.jayu.note.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jayu.note.MailService;
import com.jayu.note.bmo.CategoryBmo;
import com.jayu.note.common.Const;
import com.jayu.note.dao.model.User;
import com.jayu.note.domain.EmailType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jayu.note.bmo.UserBmo;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.common.entity.JsonError;
import com.jayu.common.entity.JsonResponse;
import com.jayu.common.util.CryptoUtils;
import com.jayu.common.util.DateUtil;
import com.jayu.common.util.JsonUtil;
import com.jayu.common.util.UIDGenerator;
import com.jayu.exception.BusinessException;
import com.jayu.exception.ResultConstant;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.validator.EmailValidator;
import com.jayu.validator.exception.MethodNotJsonValidException;
import com.jayu.web.ServletUtils;
import com.jayu.web.filter.CsrfPreventionFilter;
import com.jayu.web.spring.annotation.session.SessionValid;
import com.jayu.web.spring.controller.BaseController;

@Controller("user.LoginController")
@RequestMapping("/login/*")
public class LoginController extends BaseController {
    @Resource(name="UserBmoImpl")
    UserBmo userBmo;
	@Resource(name="CategoryBmoImpl")
	CategoryBmo categoryBmo;
	@Resource(name="mailService")
	private MailService mailService;
	
    @RequestMapping(value = "page*", method = RequestMethod.GET)
    public String loginPage(Model model, HttpServletRequest request) {
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(super.getRequest(),
                SysConstant.SESSION_KEY_LOGIN_USER);
        //已经登录
        if (sessionAdmin != null) {
                return super.redirect( "/user/main");
        }
    	return "/user/login";
    }
    
    @RequestMapping(value = "/logindo", method = RequestMethod.POST)
    //@JsonValidMethod
   // @LogOperatorAnn(code="logindo",desc="用户登录",level=LevelLog.DB)
    public @ResponseBody JsonResponse logindo(
    		HttpServletResponse response,
    //@JsonValid(value = "user.login.logindo",path=SysConstant.VALIDATOR_FILE_PATH) 
    @RequestBody Map<String,String> paramMap) throws MethodNotJsonValidException {
    	log.debug("param map={}", JsonUtil.toString(paramMap));
    	String account=paramMap.get("account");
    	String password=paramMap.get("password");
    	try {
    		List<JsonError> errorList = new ArrayList<JsonError>();
			errorList.add(new JsonError("account","用户名或密码错误！"));
			errorList.add(new JsonError("password","用户名或密码错误！"));
			if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
				return super.failed(errorList,ResultConstant.IN_PARAM_FAILTURE.getCode());
			}
			PageDomain<User> pd= new PageDomain<User>();
			if(EmailValidator.getInstance().isValid(account)){
				pd.equals("email", account);
			}else{
				pd.equals("account", account);
			}
			List<User> staffList=userBmo.findDataByCondition(pd);
			if(CollectionUtils.isNotEmpty(staffList)){
				User user=staffList.get(0);
				if(user.getLock() !=SysConstant.LOCK_ACTIVE){
					errorList.clear();
					if(user.getLock() ==SysConstant.LOCK){
						errorList.add(new JsonError("username","账号无法使用，请找管理员解锁！"));
						return super.failed(errorList,ResultConstant.USER_LOCK.getCode());
					}
					
					errorList.add(new JsonError("username","账号待激活中，请接收邮件进行账号激活！"));
					return super.failed(errorList,ResultConstant.USER_LOCK.getCode());
				}
				//成功
				if(user.getPasswd().equals(password)){		
					User loginUser = new User();
					loginUser.setLoginTime(new Date());
					loginUser.setUserId(user.getUserId());
					loginUser.setLoginNumber(user.getLoginNumber()==null?0:user.getLoginNumber()+1);
					loginUser.setFailNumber(0);
					loginUser.setLoginTime(new Date());
					userBmo.update(loginUser);
					//换sessionId
					super.getRequest().getSession().invalidate();
					SessionAdmin sessionAdmin = new SessionAdmin();
					sessionAdmin.setId(user.getUserId());
					sessionAdmin.setAccount(user.getAccount());
					sessionAdmin.setName(user.getNick());
					sessionAdmin.setNick(user.getNick());
					sessionAdmin.setAccountType(user.getAccountType());
					sessionAdmin.setEmail(user.getEmail());
					super.getRequest().getSession(true).setAttribute(SysConstant.SESSION_KEY_LOGIN_USER, sessionAdmin);
			    	return super.successed("/user/main");
			   
				} else {
					User failNumberUser = new User();
					failNumberUser.setLoginTime(new Date());
					failNumberUser.setUserId(user.getUserId());
					failNumberUser.setFailNumber(user.getFailNumber()+1);
					if(null != user.getFailNumber() && user.getFailNumber().intValue()> Const.LOGIN_FAIL_MAX_NUM){
						failNumberUser.setLockExpire(DateUtil.nearMinuteDate(Const.LOGIN_LOCK_EXPIRE_TIME));
						String accountEn=CryptoUtils.desEdeEncryptToHex(account+UIDGenerator.generateNonce(Const.LOGIN_LOCK_NO_LENGTH), Const.CONST_DESEDE_KEY.getBytes());
						failNumberUser.setLockNo(accountEn);
						failNumberUser.setLock(SysConstant.LOCK);
					}
					userBmo.update(failNumberUser);
					return super.failed(errorList,ResultConstant.DATA_NOT_VALID_FAILTURE.getCode());
				}
			} else {
				return super.failed(errorList,ResultConstant.DATA_NOT_VALID_FAILTURE.getCode());
			}
		} catch (BusinessException e) {
			log.debug("user login error", e);
	    	return super.failed("系统异常",ResultConstant.SYS_ERROR.getCode());
		}
    }
    
    
    @RequestMapping(value = "/registerdo", method = RequestMethod.POST)
    //@JsonValidMethod
   // @LogOperatorAnn(code="logindo",desc="注册账户",level=LevelLog.DB)
    public @ResponseBody JsonResponse register(
    		HttpServletResponse response,
    //@JsonValid(value = "user.login.logindo",path=SysConstant.VALIDATOR_FILE_PATH) 
    @RequestBody Map<String,String> paramMap) throws MethodNotJsonValidException {
    	log.debug("param map={}", JsonUtil.toString(paramMap));
    	String nick=paramMap.get("nick");
    	String email=paramMap.get("email");
    	String account=paramMap.get("account");
    	String password=paramMap.get("password");
    	
    	try {
    		
    		List<JsonError> errorList = new ArrayList<JsonError>();
			errorList.add(new JsonError("account","账号已存在错误！"));
			PageDomain<User> pd= new PageDomain<User>();
	    	pd.equals("account", account).or().equals("email", email);
			List<User> staffList=userBmo.findDataByCondition(pd);
			if(staffList !=null && staffList.size()>0){
				return super.failed(errorList,ResultConstant.DATA_NOT_VALID_FAILTURE.getCode());
			}
			User user= new User();
			user.setAccount(account);
			user.setPasswd(password);
			user.setEmail(email);
			user.setNick(nick);
			user.setLock(SysConstant.LOCK_UN);
			user.setRegisterTime(new Date());
			user.setAccountType(SysConstant.ACCOUNT_TYPE_USER);
			user.setLockExpire(DateUtil.nearMinuteDate(Const.LOGIN_LOCK_EXPIRE_TIME));
			String accountEn=CryptoUtils.desEdeEncryptToHex(account+UIDGenerator.generateNonce(Const.LOGIN_LOCK_NO_LENGTH), Const.CONST_DESEDE_KEY.getBytes());
			user.setLockNo(accountEn);
			userBmo.register(user);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put(Const.SITE_URL_NAME, Const.CONST_SITE_URL);
			dataMap.put(Const.ACTIVE_NO_NAME, accountEn);
			try {
				mailService.setAncsTemplateMail(email, "账号激活", EmailType.REGISTER_ACTIVE+".tpl", dataMap);
			} catch (Exception e) {
				log.error(e);
			}
			return super.successed("注册成功！请登录！");
		} catch (BusinessException e) {
			log.debug("user login error", e);
	    	return super.failed("系统异常",ResultConstant.SYS_ERROR.getCode());
		}
    }
    
    @RequestMapping(value = "/lockActive", method = RequestMethod.GET)
    public String lockActive(@RequestParam("no") String no) {
    	try{
    		String accountEn=CryptoUtils.desEdeDecryptFromHex(no, Const.CONST_DESEDE_KEY.getBytes());
    		if(StringUtils.isNotBlank(accountEn) && accountEn.length()>Const.LOGIN_LOCK_NO_LENGTH){
    			accountEn=accountEn.substring(0, accountEn.length()-Const.LOGIN_LOCK_NO_LENGTH);
    			PageDomain<User> pd= new PageDomain<User>();
    	    	pd.equals("account", accountEn);
    			List<User> staffList=userBmo.findDataByCondition(pd);
    			if(CollectionUtils.isNotEmpty(staffList) ){
    				User user= staffList.get(0);
    				if(user.getLock() !=null && user.getLock().intValue()==SysConstant.LOCK_UN){
    					//未过期
        				if(user.getLockExpire().after(new Date()) && user.getLock() !=null && user.getLock().intValue()==SysConstant.LOCK_UN){
        					User userLock = new User();
        					userLock.setUserId(user.getUserId());
        					userLock.setFailNumber(0);
        					userLock.setLock(SysConstant.LOCK_ACTIVE);
        					userBmo.update(userLock);
        				//过期，重新生成,最多生成3次
        				}else{
        					User userLock = new User();
        					userLock.setUserId(user.getUserId());
        					userLock.setFailNumber(user.getFailNumber()+1);
        					//最多生成3次
        					if(userLock.getFailNumber()>3){
        						userLock.setLock(SysConstant.LOCK);
        					//重新生成激活码!
        					}else{
        						userLock.setLockExpire(DateUtil.nearMinuteDate(Const.LOGIN_LOCK_EXPIRE_TIME));
        						String accountEnNew=CryptoUtils.desEdeEncryptToHex(user.getAccount()+UIDGenerator.generateNonce(Const.LOGIN_LOCK_NO_LENGTH), Const.CONST_DESEDE_KEY.getBytes());
        						userLock.setLockNo(accountEnNew);
        						userLock.setLock(SysConstant.LOCK_UN);
        					}
        					userBmo.update(userLock);
        				}
    				}
    			}
    		}
    	}catch(Exception e){
    		log.error("lockActive", e);
    	}
        return super.redirect("/");
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @SessionValid
    public String logout() {
    	ServletUtils.removeSessionAttribute(super.getRequest(), SysConstant.SESSION_KEY_LOGIN_USER);
    	ServletUtils.removeSessionAttribute(super.getRequest(), CsrfPreventionFilter.CSRF_NONCE_SESSION_ATTR_NAME);
        ServletUtils.getSession(super.getRequest()).invalidate();
        return super.redirect("/");
    }
    
}
