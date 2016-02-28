package com.jayu.note.domain;

import java.io.Serializable;

/**
 * 管理员 Session 用户信息 类 概述 .
 * <P>
 * @author jayu
 * @version V1.0 2012-5-19
 * @createDate 2012-5-19 下午9:40:38
 * @modifyDate jayu 2012-5-19 <BR>
 */
public class SessionAdmin implements Serializable {
	private static final long serialVersionUID = -4204196986464099713L;

	/** 用户ID */
	private long id;
	private String name;
	private String account;
	private String nick;

	/** 1:普通员,2是管理员,0:游客 */
	private int accountType=1;
	private String email;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	/** 
	 * 1:普通员,2是管理员,0:游客 
	 * */
	public int getAccountType() {
		return accountType;
	}
	/** 
	 * 1:普通员,2是管理员,0:游客 
	 * */
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
}
