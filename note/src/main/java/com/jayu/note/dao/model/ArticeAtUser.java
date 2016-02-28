package com.jayu.note.dao.model;

import java.io.Serializable;

import com.jayu.web.spring.annotation.valid.LengthCN;
import com.jayu.mybatis.entity.Column;
/**
 * 摘要：日志@用户查看权限实体.
 * <P>
 * @author jayu
 * @version V1.0 2016-02-06
 * @createDate 2016-02-06 19:26:21
 * @modifyDate jayu 2016-02-06
 */
public class ArticeAtUser implements Serializable {

	/**
	 * 主键:. 
	 */
	@Column("article_id")
	private Long articleId;

	/**
	 * 主键:用户ID或朋友圈ID. 
	 */
	@Column("biz_id")
	private Long bizId;

	/**
	 * 1:biz_id为用户ID，2:biz_id为朋友圈ID. 
	 */
	@Column("type")
	private Integer type;

	/** 用户账号 */
	private String account;
	
	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * 
	 * 设置  值.
	 * @param articleId 
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	/**
	 * 
	 * 获取 用户ID或朋友圈ID值.
	 * @return  用户ID或朋友圈ID值
	 */
	public Long getBizId() {
		return bizId;
	}

	/**
	 * 
	 * 设置  用户ID或朋友圈ID值.
	 * @param bizId 用户ID或朋友圈ID
	 */
	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	/**
	 * 
	 * 获取 1:biz_id为用户ID，2:biz_id为朋友圈ID值.
	 * @return  1:biz_id为用户ID，2:biz_id为朋友圈ID值
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 
	 * 设置  1:biz_id为用户ID，2:biz_id为朋友圈ID值.
	 * @param type 1:biz_id为用户ID，2:biz_id为朋友圈ID
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	

}
