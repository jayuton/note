package com.jayu.note;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jayu.note.bmo.ArticeAtUserBmo;
import com.jayu.note.dao.model.ArticeAtUser;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.exception.BusinessException;
import com.jayu.log.Log;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.annotation.session.AuthorityValid;
import com.jayu.web.spring.interceptor.AbstractAuthorityInterceptor;

/**
 * 权限拦截业务实现 .
 * <BR>
 *  根据权限实例编码进行拦截判断.
 * <P>
 * @author jayu
 * @version V1.0 2012-10-27
 * @createDate 2012-10-27 下午5:45:59
 * @modifyDate jayu 2012-10-27 <BR>
 */
public class MyAuthorityInterceptor extends AbstractAuthorityInterceptor {
	
	protected Log log=Log.getLog(MyAuthorityInterceptor.class);
	
	public static final String  PACKAGE_PREFIX="com.jayu.note.controller.";

	@Resource(name="ArticeAtUserBmoImpl")
	ArticeAtUserBmo articeAtUserBmo;

    @Override
    public int checkDataAuthorityByUrl(AuthorityValid authorityValid,HttpServletRequest request,String mappingUrl,Map pathVariables,Map paramMap){
    	Object articleIdObj = pathVariables.get("id");
    	if(null == articleIdObj){
    		return SUCCESS;
    	}
    	long articleId = Long.valueOf(articleIdObj.toString());
    	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
	
		try {
			List<ArticeAtUser> articeAtUserList = articeAtUserBmo.findDataByCondition(articleId);
			if(null !=articeAtUserList){
				for(ArticeAtUser articeAtUser:articeAtUserList){
					if(articeAtUser.getType().intValue()==SysConstant.AT_ALL_GUEST){
						return SUCCESS;
					}else if(articeAtUser.getType().intValue()==SysConstant.AT_ALL_MEMBER ){
						if(null !=sessionAdmin){
							return SUCCESS;
						}
					}else if(articeAtUser.getType().intValue()==SysConstant.AT_FRIEND ){
						if(null !=sessionAdmin && articeAtUser.getBizId().longValue()==sessionAdmin.getId()){
							return SUCCESS;
						}
					}
				}
				return FAILED;
			}
		} catch (BusinessException e) {
			log.error(e);
			return FAILED;
		}

		 return SUCCESS;
	 }
    
	@Override
	public int checkAuthorityByActionMethod(HttpServletRequest request,
			String authorityCode) {
		// TODO Auto-generated method stub
		return SUCCESS;
	}
    

}
