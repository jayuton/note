package com.jayu.note.dao.model;

import java.io.Serializable;

import com.jayu.web.spring.annotation.valid.LengthCN;
import com.jayu.mybatis.entity.Column;
/**
 * 摘要：邮件发送列表实体.
 * <P>
 * @author jayu
 * @version V1.0 2016-02-12
 * @createDate 2016-02-12 15:41:53
 * @modifyDate jayu 2016-02-12
 * @since   JDK1.7
 */
public class EmailSend implements Serializable {

	/**
	 * 主键:. 
	 */
	@Column("id")
	private String id;

	/**
	 * 邮件业务对象. 
	 */
	@Column("biz_id")
	private String bizId;

	/**
	 * 接收邮件的email用户. 
	 */
	@Column("email_post")
	private String emailPost;

	/**
	 * 1:未发,2:发成功，3:发失败. 
	 */
	@Column("status")
	private Integer status;

	/**
	 * email模板类型. 
	 */
	@Column("email_tpl")
	private String emailTpl;

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * 设置  值.
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * 获取 邮件业务对象ID值.
	 * @return  邮件业务对象ID
	 */
	public String getBizId() {
		return bizId;
	}

	/**
	 * 
	 * 设置  邮件业务对象ID.
	 * @param bizId 邮件业务对象ID.
	 */
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	/**
	 * 
	 * 获取 接收邮件的email用户值.
	 * @return  接收邮件的email用户值
	 */
	public String getEmailPost() {
		return emailPost;
	}

	/**
	 * 
	 * 设置  接收邮件的email用户值.
	 * @param emailPost 接收邮件的email用户
	 */
	public void setEmailPost(String emailPost) {
		this.emailPost = emailPost;
	}

	/**
	 * 
	 * 获取 1:未发,2:发成功，3:发失败值.
	 * @return  1:未发,2:发成功，3:发失败值
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 
	 * 设置  1:未发,2:发成功，3:发失败值.
	 * @param status 1:未发,2:发成功，3:发失败
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 
	 * 获取 email模板类型值.
	 * @return  email模板类型值
	 */
	public String getEmailTpl() {
		return emailTpl;
	}

	/**
	 * 
	 * 设置  email模板类型值.
	 * @param emailTpl email模板类型
	 */
	public void setEmailTpl(String emailTpl) {
		this.emailTpl = emailTpl;
	}

}
