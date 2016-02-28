package com.jayu.note.job;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.jayu.note.bmo.MquartzBmo;
import com.jayu.note.dao.model.Mquartz;
import com.jayu.note.domain.Action;
import com.jayu.common.util.StringUtil;
import com.jayu.log.Log;

@Aspect
@Lazy(false)
@Component(value = "UserActionAop")
public class UserActionAop {
	/** 日志 */
	protected static Log log = Log.getLog(UserActionAop.class);
	protected static JexlEngine jexl = new JexlEngine();

	private final static String ARG_PREFIX = "arg";
	private final static String RETURN = "rt";
	@Resource(name = "MquartzBmoImpl")
	private MquartzBmo mquartzBmo;

	@Around("within(com.jayu.note..*) &&@annotation(com.jayu.note.domain.Action)")
	public Object runOnAround(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint
				.getSignature();
		Object[] args = joinPoint.getArgs();
		Method method = methodSignature.getMethod();
		Action actionAnnonation = (Action) method.getAnnotation(Action.class);
		Object returnValue = joinPoint.proceed();
		if (null != actionAnnonation) {
			Mquartz mquartz = new Mquartz();
			mquartz.setActionType(actionAnnonation.actionType().name());
			try {
				JexlContext jc = new MapContext();
				boolean isHasParam = false;
				if (null != args && args.length > 0) {
					for (int i = 0; i < args.length; i++) {
						jc.set(ARG_PREFIX + i, args[i]);
						isHasParam = true;
					}
				}
				if (null != returnValue
						&& supportsReturn(returnValue.getClass())) {
					jc.set(RETURN, returnValue);
					isHasParam = true;
				}
				// 结束返回
				if (!isHasParam) {
					return returnValue;
				}
				String bizIdExl = actionAnnonation.bizIdExl();
				String userIdExl = actionAnnonation.userIdExl();
				String[] attributeExls = actionAnnonation.attributeExl();
				if (StringUtils.isNotBlank(bizIdExl)) {
					// 纯前缀，代表数组入参
					if (ARG_PREFIX.equals(bizIdExl)) {
						if (null != args && args.length > 0) {
							List<String> bizIdValueList = new ArrayList<String>();
							for (int i = 0; i < args.length; i++) {
								Expression bizIdExp = jexl
										.createExpression(bizIdExl + i);
								Object bizIdValue = bizIdExp.evaluate(jc);
								if (null != bizIdValue
										&& supportsParam(bizIdValue.getClass())) {
									bizIdValueList.add(bizIdValue.toString());
								}
							}
							mquartz.setBizId(StringUtil.join(bizIdValueList,
									","));
						}
					} else {
						Expression bizIdExp = jexl.createExpression(bizIdExl);
						Object bizIdValue = bizIdExp.evaluate(jc);
						if (null != bizIdValue) {
							if (bizIdValue.getClass().isArray()) {
								Object[] bizIdValueArray = (Object[]) bizIdValue;
								if (bizIdValueArray.length > 0
										&& supportsParam(bizIdValueArray[0]
												.getClass())) {
									mquartz.setBizId(StringUtil.join(
											bizIdValueArray, ","));
								}
							} else if (supportsParam(bizIdValue.getClass())) {
								mquartz.setBizId(bizIdValue.toString());
							}
						}
					}
					
				}

				if (StringUtils.isNotBlank(userIdExl)) {
					Expression userIdExp = jexl.createExpression(userIdExl);
					Object userIdValue = userIdExp.evaluate(jc);
					if (null != userIdValue
							&& supportsParam(userIdValue.getClass())
							&& NumberUtils.isNumber(userIdValue.toString())) {
						mquartz.setUserId(Long.valueOf(userIdValue.toString()));
					}
				}

				if (StringUtils.isNotBlank(mquartz.getBizId())
						&& null != attributeExls && attributeExls.length > 0
						&& actionAnnonation.attributeKey() != null) {
					String[] keys = actionAnnonation.attributeKey();
					for (int i = 0; i < attributeExls.length && i < keys.length; i++) {
						Expression attrExp = jexl
								.createExpression(attributeExls[i]);
						Object value = attrExp.evaluate(jc);
						if (null != value && supportsParam(value.getClass())) {
							mquartz.putActionAttribute(keys[i],
									value.toString());
						}
					}
				}

				if (StringUtils.isNotBlank(mquartz.getBizId())) {
					mquartz.setActionStarttime(new Date());
					String[] bizIdValueArray=mquartz.getBizId().split(",");
					for(String bizId:bizIdValueArray){
						mquartz.setBizId(bizId);
						mquartzBmo.insertSelective(mquartz);
					}
					
				}

			} catch (Throwable e) {
				log.error("run action aop excception", e);
			}
		}
		return returnValue;
	}

	public boolean supportsParam(Class<?> clazz) {
		if (null != clazz
				&& (String.class.isAssignableFrom(clazz)
						|| Integer.class.isAssignableFrom(clazz)
						|| Long.class.isAssignableFrom(clazz)
						|| Float.class.isAssignableFrom(clazz)
						|| Short.class.isAssignableFrom(clazz)
						|| Byte.class.isAssignableFrom(clazz) || BigDecimal.class
							.isAssignableFrom(clazz))) {

			return true;
		}
		return false;
	}

	public boolean supportsReturn(Class<?> clazz) {
		if (clazz == null || Void.class.isAssignableFrom(clazz)) {
			return false;
		}
		return true;
	}
}
