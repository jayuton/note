package com.jayu.note.dao.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jayu.mybatis.entity.Column;
/**
 * 摘要：文章实体.
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:38:53
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
public class Article implements Serializable {

	/**
	 * 主键:. 
	 */
	@Column("id")
	private Long id;

	/**
	 * 文章类目ID. 
	 */
	@Column("cate_id")
	private Integer cateId;

	/**
	 * . 
	 */
	@Column("title")
	private String title;

	/**
	 *  作者ID. 
	 */
	@Column("author_id")
	private Long authorId;

	/**
	 * 作者名称. 
	 */
	@Column("author_name")
	private String authorName;

	/**
	 * 摘要. 
	 */
	@Column("short_content")
	private String shortContent;

	/**
	 * 内容ID. 
	 */
	@Column("content_id")
	private Long contentId;

	/**
	 * 浏览次数. 
	 */
	@Column("view_count")
	private Integer viewCount;

	/**
	 * 回复次数. 
	 */
	@Column("reply_count")
	private Integer replyCount;

	/**
	 * 写的时间. 
	 */
	@Column("write_time")
	private Date writeTime;

	/**
	 * 更新时间. 
	 */
	@Column("modify_time")
	private Date modifyTime;

	/**
	 * 最后浏览时间. 
	 */
	@Column("last_view_time")
	private Date lastViewTime;

	/**
	 * 最后回复时间. 
	 */
	@Column("last_reply_time")
	private Date lastReplyTime;

	/**
	 * 1:正常可以浏览,2:关闭. 
	 */
	@Column("status")
	private Integer status;

	/**
	 * 文章标签，关键字检索. 
	 */
	@Column("tags")
	private String tags;

	/**
	 * 写的年份4位数. 
	 */
	@Column("write_year")
	private Integer writeYear;

	/**
	 * 写的月份2位数. 
	 */
	@Column("write_month")
	private Integer writeMonth;

	/**
	 * 写的一个月中日期数. 
	 */
	@Column("write_day")
	private Integer writeDay;

	/**
	 * 浏览权限，1：公开，2：指定朋友访问，3：隐私，4：@访问. 
	 */
	@Column("privilege_type")
	private Integer privilegeType;

	/**
	 * 文章内容
	 */
	private String content;
	
	/**
	 * 类目名称
	 */
	private String cateName;
	/** 授权 用户  */
	private List<ArticeAtUser> articeAtUserList;

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * 设置  值.
	 * @param id 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * 获取 文章类目ID值.
	 * @return  文章类目ID值
	 */
	public Integer getCateId() {
		return cateId;
	}

	/**
	 * 
	 * 设置  文章类目ID值.
	 * @param cateId 文章类目ID
	 */
	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * 设置  值.
	 * @param title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * 获取  作者ID值.
	 * @return   作者ID值
	 */
	public Long getAuthorId() {
		return authorId;
	}

	/**
	 * 
	 * 设置   作者ID值.
	 * @param authorId  作者ID
	 */
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	/**
	 * 
	 * 获取 作者名称值.
	 * @return  作者名称值
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * 
	 * 设置  作者名称值.
	 * @param authorName 作者名称
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * 
	 * 获取 摘要值.
	 * @return  摘要值
	 */
	public String getShortContent() {
		return shortContent;
	}

	/**
	 * 
	 * 设置  摘要值.
	 * @param shortContent 摘要
	 */
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	/**
	 * 
	 * 获取 内容ID值.
	 * @return  内容ID值
	 */
	public Long getContentId() {
		return contentId;
	}

	/**
	 * 
	 * 设置  内容ID值.
	 * @param contentId 内容ID
	 */
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	/**
	 * 
	 * 获取 浏览次数值.
	 * @return  浏览次数值
	 */
	public Integer getViewCount() {
		return viewCount;
	}

	/**
	 * 
	 * 设置  浏览次数值.
	 * @param viewCount 浏览次数
	 */
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * 
	 * 获取 回复次数值.
	 * @return  回复次数值
	 */
	public Integer getReplyCount() {
		return replyCount;
	}

	/**
	 * 
	 * 设置  回复次数值.
	 * @param replyCount 回复次数
	 */
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	/**
	 * 
	 * 获取 写的时间值.
	 * @return  写的时间值
	 */
	public Date getWriteTime() {
		return writeTime;
	}

	/**
	 * 
	 * 设置  写的时间值.
	 * @param writeTime 写的时间
	 */
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	/**
	 * 
	 * 获取 更新时间值.
	 * @return  更新时间值
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 
	 * 设置  更新时间值.
	 * @param modifyTime 更新时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 
	 * 获取 最后浏览时间值.
	 * @return  最后浏览时间值
	 */
	public Date getLastViewTime() {
		return lastViewTime;
	}

	/**
	 * 
	 * 设置  最后浏览时间值.
	 * @param lastViewTime 最后浏览时间
	 */
	public void setLastViewTime(Date lastViewTime) {
		this.lastViewTime = lastViewTime;
	}

	/**
	 * 
	 * 获取 最后回复时间值.
	 * @return  最后回复时间值
	 */
	public Date getLastReplyTime() {
		return lastReplyTime;
	}

	/**
	 * 
	 * 设置  最后回复时间值.
	 * @param lastReplyTime 最后回复时间
	 */
	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	/**
	 * 
	 * 获取 1:正常可以浏览,2:关闭值.
	 * @return  1:正常可以浏览,2:关闭值
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 
	 * 设置  1:正常可以浏览,2:关闭值.
	 * @param status 1:正常可以浏览,2:关闭
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 
	 * 获取 文章标签，关键字检索值.
	 * @return  文章标签，关键字检索值
	 */
	public String getTags() {
		return tags;
	}
	public List<String> getTagsList() {
		if(null !=tags){
			String[] tagsArray = tags.split(",");
			return Arrays.asList(tagsArray);
		}
		return null;
	}

	/**
	 * 
	 * 设置  文章标签，关键字检索值.
	 * @param tags 文章标签，关键字检索
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * 
	 * 获取 写的年份4位数值.
	 * @return  写的年份4位数值
	 */
	public Integer getWriteYear() {
		return writeYear;
	}

	/**
	 * 
	 * 设置  写的年份4位数值.
	 * @param writeYear 写的年份4位数
	 */
	public void setWriteYear(Integer writeYear) {
		this.writeYear = writeYear;
	}

	/**
	 * 
	 * 获取 写的月份2位数值.
	 * @return  写的月份2位数值
	 */
	public Integer getWriteMonth() {
		return writeMonth;
	}

	/**
	 * 
	 * 设置  写的月份2位数值.
	 * @param writeMonth 写的月份2位数
	 */
	public void setWriteMonth(Integer writeMonth) {
		this.writeMonth = writeMonth;
	}

	/**
	 * 
	 * 获取 写的一个月中日期数值.
	 * @return  写的一个月中日期数值
	 */
	public Integer getWriteDay() {
		return writeDay;
	}

	/**
	 * 
	 * 设置  写的一个月中日期数值.
	 * @param writeDay 写的一个月中日期数
	 */
	public void setWriteDay(Integer writeDay) {
		this.writeDay = writeDay;
	}

	/**
	 * 
	 * 获取 浏览权限，1：公开，2：指定朋友访问，3：隐私，4：@访问值.
	 * @return  浏览权限，1：公开，2：指定朋友访问，3：隐私，4：@访问值
	 */
	public Integer getPrivilegeType() {
		return privilegeType;
	}

	/**
	 * 
	 * 设置  浏览权限，1：公开，2：指定朋友访问，3：隐私，4：@访问值.
	 * @param privilegeType 浏览权限，1：公开，2：指定朋友访问，3：隐私，4：@访问
	 */
	public void setPrivilegeType(Integer privilegeType) {
		this.privilegeType = privilegeType;
	}

	/**
	 * 文章内容
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 文章内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}


	public List<ArticeAtUser> getArticeAtUserList() {
		return articeAtUserList;
	}

	public void setArticeAtUserList(List<ArticeAtUser> articeAtUserList) {
		this.articeAtUserList = articeAtUserList;
	}

}
