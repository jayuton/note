package com.jayu.note.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jayu.web.spring.annotation.valid.LengthCN;
import com.jayu.mybatis.entity.Column;
/**
 * 摘要：笔记文件发送列表实体.
 * <P>
 * @author jayu
 * @version V1.0 2016-02-13
 * @createDate 2016-02-13 19:54:53
 * @modifyDate jayu 2016-02-13
 * @since   JDK1.7
 */
public class ArticleFile implements Serializable {

	/**
	 * 主键:. 
	 */
	@Column("id")
	private String id;

	/**
	 * . 
	 */
	@Column("user_id")
	private Long userId;

	/**
	 * . 
	 */
	@Column("url")
	private String url;

	/**
	 * . 
	 */
	@Column("name")
	private String name;

	/**
	 * . 
	 */
	@Column("type")
	private String type;

	/**
	 * . 
	 */
	@Column("size")
	private BigDecimal size;

	/**
	 * . 
	 */
	@Column("create_dt")
	private Date createDt;

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
	 * 获取 值.
	 * @return  值
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 
	 * 设置  值.
	 * @param userId 
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * 设置  值.
	 * @param url 
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * 设置  值.
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * 设置  值.
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public BigDecimal getSize() {
		return size;
	}

	/**
	 * 
	 * 设置  值.
	 * @param size 
	 */
	public void setSize(BigDecimal size) {
		this.size = size;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public Date getCreateDt() {
		return createDt;
	}

	/**
	 * 
	 * 设置  值.
	 * @param createDt 
	 */
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

}
