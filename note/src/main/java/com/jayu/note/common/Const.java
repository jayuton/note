package com.jayu.note.common;

import com.jayu.web.PropertiesUtils;
import com.jayu.web.SpringContextUtil;

public class Const {
	private final static PropertiesUtils propertiesUtils = (PropertiesUtils) SpringContextUtil
			.getBean("propertiesUtils");

	/** 图片目录 */
	public static final String CONST_UPLOAD_IMAGE_DIR = propertiesUtils
			.getMessage("CONST_UPLOAD_IMAGE_DIR", "");
	/** html缓存目录 */
	public static final String CONST_CACHE_DIR = propertiesUtils.getMessage(
			"CONST_CACHE_DIR", "");
	
	public static final String CONST_DESEDE_KEY = propertiesUtils.getMessage(
			"CONST_DESEDE_KEY", "");
	
	public static final String CONST_RESET_PASSWORD = propertiesUtils.getMessage(
			"CONST_STAFF_INIT_PWD", "666666");
	public static final String CONST_SITE_URL = propertiesUtils.getMessage(
			"SITE_URL", "http://note.icityshops.com");
	public static final int CONST_MAX_FILE = propertiesUtils.getMessageAsInt(
			"CONST_MAX_FILE", 1000);
	
	public static final String SITE_URL_NAME= "siteUrl";
	public static final String ACTIVE_NO_NAME= "activeNo";
	
	public static final int LOGIN_FAIL_MAX_NUM = 3;
	/** 30分钟 */
	public static final int LOGIN_LOCK_EXPIRE_TIME = 30;
	public static final int LOGIN_LOCK_NO_LENGTH = 12;
	
	public static final int ARCHIVE_OF_DAY_TYPE = 1;
	public static final int ARCHIVE_OF_MONTH_TYPE = 2;
	public static final int ARCHIVE_OF_YEAR_TYPE = 3;
	public static final int ARCHIVE_OF_CATE_TYPE = 5;
	
	public static final String MENU_CURRENT= "cmenu";
	public static final String MENU_HOME= "main";
	public static final String MENU_ARTICLE_ADD = "article_add";
	public static final String MENU_ARCHIVE_DAY = "archive_day";
	public static final String MENU_ARCHIVE_MONTH = "archive_month";
	public static final String MENU_ARTICLE_CATEGORY= "article_cate";
	public static final String MENU_CATEGORY= "category";
	public static final String MENU_MEMBER= "member";
	public static final String MENU_PROFILE= "profile";
}
