package com.jayu.note.dao.model;

import java.io.Serializable;
import java.util.Date;

import com.jayu.web.spring.annotation.valid.LengthCN;
import com.jayu.mybatis.entity.Column;
/**
 * 摘要：用户实体.
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:25:35
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
public class User  implements Serializable {

	/**
	 * 主键:用户ID. 
	 */
	@Column("user_id")
	private Long userId;

	/**
	 * 登录账号. 
	 */
	@Column("account")
	private String account;

	/**
	 * 登录账号. 
	 */
	@Column("account_type")
	private Integer accountType;

	/**
	 * 密码. 
	 */
	@Column("passwd")
	private String passwd;

	/**
	 * 用户名称. 
	 */
	@Column("nick")
	private String nick;

	/**
	 * . 
	 */
	@Column("email")
	private String email;

	/**
	 * 头像地址. 
	 */
	@Column("header_img")
	private String headerImg;

	/**
	 * 个性签名. 
	 */
	@Column("signature")
	private String signature;

	/**
	 * 博客模板ID. 
	 */
	@Column("template_id")
	private Integer templateId;

	/**
	 * 账号锁 0:未激活，1:激活，2：锁定. 
	 */
	@Column("lock")
	private Integer lock;

	/**
	 * 激活锁码. 
	 */
	@Column("lock_no")
	private String lockNo;

	/**
	 * 激活码过期时间. 
	 */
	@Column("login_lock_expire")
	private Date lockExpire;
	
	/**
	 * 注册时间. 
	 */
	@Column("register_time")
	private Date registerTime;

	/**
	 * 最后登录时间. 
	 */
	@Column("login_time")
	private Date loginTime;

	/**
	 * 登录次数. 
	 */
	@Column("login_number")
	private Integer loginNumber;

	
	/**
	 * 登录失败次数. 
	 */
	@Column("fail_number")
	private Integer failNumber;

	/**
	 * 
	 * 获取 用户ID值.
	 * @return  用户ID值
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 
	 * 设置  用户ID值.
	 * @param userId 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * 获取 登录账号值.
	 * @return  登录账号值
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 
	 * 设置  登录账号值.
	 * @param account 登录账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	/**
	 * 
	 * 获取 密码值.
	 * @return  密码值
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * 
	 * 设置  密码值.
	 * @param passwd 密码
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 
	 * 获取 用户名称值.
	 * @return  用户名称值
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * 
	 * 设置  用户名称值.
	 * @param nick 用户名称
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * 设置  值.
	 * @param email 
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * 获取 头像地址值.
	 * @return  头像地址值
	 */
	public String getHeaderImg() {
		return headerImg;
	}

	/**
	 * 
	 * 设置  头像地址值.
	 * @param headerImg 头像地址
	 */
	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}

	/**
	 * 
	 * 获取 个性签名值.
	 * @return  个性签名值
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 
	 * 设置  个性签名值.
	 * @param signature 个性签名
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * 
	 * 获取 博客模板ID值.
	 * @return  博客模板ID值
	 */
	public Integer getTemplateId() {
		return templateId;
	}

	/**
	 * 
	 * 设置  博客模板ID值.
	 * @param templateId 博客模板ID
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	/**
	 * 
	 * 获取 账号锁 0:未激活，1:激活，2：锁定值.
	 * @return  账号锁 0:未激活，1:激活，2：锁定值
	 */
	public Integer getLock() {
		return lock;
	}

	/**
	 * 
	 * 设置  账号锁 0:未激活，1:激活，2：锁定值.
	 * @param lock 账号锁 0:未激活，1:激活，2：锁定
	 */
	public void setLock(Integer lock) {
		this.lock = lock;
	}

	/**
	 * 
	 * 获取 激活锁码值.
	 * @return  激活锁码值
	 */
	public String getLockNo() {
		return lockNo;
	}

	/**
	 * 
	 * 设置  激活锁码值.
	 * @param lockNo 激活锁码
	 */
	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}

	public Date getLockExpire() {
		return lockExpire;
	}

	public void setLockExpire(Date lockExpire) {
		this.lockExpire = lockExpire;
	}

	/**
	 * 
	 * 获取 注册时间值.
	 * @return  注册时间值
	 */
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * 
	 * 设置  注册时间值.
	 * @param registerTime 注册时间
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * 
	 * 获取 最后登录时间值.
	 * @return  最后登录时间值
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * 
	 * 设置  最后登录时间值.
	 * @param loginTime 最后登录时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * 
	 * 获取 登录次数值.
	 * @return  登录次数值
	 */
	public Integer getLoginNumber() {
		return loginNumber;
	}

	/**
	 * 
	 * 设置  登录次数值.
	 * @param loginNumber 登录次数
	 */
	public void setLoginNumber(Integer loginNumber) {
		this.loginNumber = loginNumber;
	}
	
	public Integer getFailNumber() {
		return failNumber;
	}

	public void setFailNumber(Integer failNumber) {
		this.failNumber = failNumber;
	}

}
