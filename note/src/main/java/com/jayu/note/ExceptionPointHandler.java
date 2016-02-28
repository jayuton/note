package com.jayu.note;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jayu.web.spring.exception.ExceptionPoint;

/**
 * 拦截异常，入参处理 .
 * <BR>
 *  根据请求路径，转入要跳转到登录页面
 * <P>
 * @author jayu
 * @version V1.0 2012-12-9
 * @createDate 2012-12-9 下午8:28:44
 * @modifyDate jayu 2012-12-9 <BR>
 */
public class ExceptionPointHandler  implements ExceptionPoint{
	public Map before(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		Map<String,String> returnMap = new HashMap<String,String>();
		returnMap.put("loginurl", "/login/page");
		return returnMap;
	}
}
