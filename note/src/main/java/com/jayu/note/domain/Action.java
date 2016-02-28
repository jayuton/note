package com.jayu.note.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.ANNOTATION_TYPE,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
	
	/**
	 * arg0.id  : 0代表第一个入参位置
	 * rt:代表回参
	 */
	String bizIdExl() default "arg0";
	
	String userIdExl() default "";
	/**
	 * arg0.id  : 0代表第一个入参位置
	 * arg1.id,arg2
	 * rt:代表回参
	 */
	String[] attributeExl() default "";	
	String[] attributeKey() default "";	
	/**
	 * 类型编码.
	 * <P>
	 */
	ActionType actionType();

}
