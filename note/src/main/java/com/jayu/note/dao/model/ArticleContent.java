package com.jayu.note.dao.model;

import java.io.Serializable;

import com.jayu.web.spring.annotation.valid.LengthCN;
import com.jayu.mybatis.entity.Column;
/**
 * 摘要：文章内容实体.
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:36:52
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
public class ArticleContent implements Serializable {

	/**
	 * 主键:文章ID. 
	 */
	@Column("id")
	private Long id;

	/**
	 * 作者. 
	 */
	@Column("author_id")
	private Long authorId;
	
	/**
	 * 文章内容. 
	 */
	@Column("content")
	private String content;

	/**
	 * 
	 * 获取 文章ID值.
	 * @return  文章ID值
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * 设置  文章ID值.
	 * @param id 文章ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	/**
	 * 
	 * 获取 文章内容值.
	 * @return  文章内容值
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * 设置  文章内容值.
	 * @param content 文章内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
