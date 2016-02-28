package com.jayu.note.domain;

/**
 * 共同常量类 . 
 * <P>
 * @author jayu
 * @version V1.0 2012-3-24
 * @createDate 2012-3-24 下午11:24:41
 * @modifyDate jayu 2012-3-24 <BR>
 */
public final class SysConstant {

	/** user Session key */
	public static final String SESSION_KEY_LOGIN_USER = "_note_ulogin_s_key";
	
	/** Session Key　验证码 */
	public static final String SESSION_KEY_IMAGE_CODE ="is_valid_imagecode_session_key";
	
	/**用户登录错误次数*/
	public static final String SESSION_KEY_LOGIN_ERROR_CHECK_COUNT="_login_error_check_count";
	
	/** 验证配置文件路径 */
	public static final String VALIDATOR_FILE_PATH =  "/config/valid/validator-json.xml";
	
	public static final String  FILE_UPLOAD_DIR="upload";

	/** 商品详情图片规格 760*3500 */
	public static final String  FILE_UPLOAD_DIR_N9="n9";
	
	/** 详情描述图片 */
	/** 详情描述图片 */
	public static final int IMAGE_HEIGHT_3500=3500;//放大图片
	
	/** 放大图片 */
	public static final int IMAGE_WIDTH_800=800;//放大图片

	/**  树文件夹节点，含有子节点 */
	public static final String  TREE_NODE_TYPE_FOLDER="folder";
	/**  树文件节点，没有子节点 */
	public static final String  TREE_NODE_TYPE_FILE="file";
	
	/**  用户类型 0:游客 */
	public static final int  ACCOUNT_TYPE_GUEST=0;
	/**  用户类型 1:用户，会员 */
	public static final int  ACCOUNT_TYPE_USER=1;
	/**  用户类型 9:管理员 */
	public static final int  ACCOUNT_TYPE_STAFF=9;
	
	/**  排序: 长序 */
	public static final String  ORDER_ASC="1";
	/**  排序: 降序 */
	public static final String  ORDER_DESC="0";
	/** 0:未激活 */
	public static final int LOCK_UN = 0;
	/** 1:激活 */
	public static final int LOCK_ACTIVE = 1;
	/** 2：锁定 */
	public static final int LOCK = 2;
	
	/** -1:所有会员 */
	public static final int ALL_MEMBER = -1;
	/** -2:所有用户 */
	public static final int ALL_GUEST = -2;
	
	/** 用户ID */
	public static final int AT_FRIEND = 1;
	/** 朋友圈 */
	public static final int AT_FRIENDS_GROUP = 2;
	/** 所有会员 */
	public static final int AT_ALL_MEMBER = 3;
	/** 所有用户 */
	public static final int AT_ALL_GUEST = 4;
}
