package com.jayu.note.dao.model;

import java.io.Serializable;
import java.util.Date;

import com.jayu.web.spring.annotation.valid.LengthCN;
import com.jayu.mybatis.entity.Column;
/**
 * 摘要：文章归档实体.
 * <P>
 * @author jayu
 * @version V1.0 2015-12-13
 * @createDate 2015-12-13 20:58:53
 * @modifyDate jayu 2015-12-13
 * @since   JDK1.7
 */
public class ArticleArchive implements Serializable {
	
	/**
	 * 主键:ID. 
	 */
	@Column("id")
	private Long id;

	/**
	 * 用户ID. 
	 */
	@Column("user_id")
	private Long userId;

	/**
	 * . 
	 */
	@Column("archive_year")
	private Integer archiveYear;

	/**
	 * 归档日期6位. 
	 */
	@Column("archive_month")
	private Integer archiveMonth;

	/**
	 * . 
	 */
	@Column("archive_day")
	private Integer archiveDay;

	/**
	 * 归档文章数量. 
	 */
	@Column("archive_number")
	private Integer archiveNumber;

	private Integer oldArchiveNumber;
	
	/**
	 * 1:按天归档,2:按月归档,3:按年归档,5:类目归档. 
	 */
	@Column("archive_type")
	private Integer archiveType;

	/**
	 * 类目. 
	 */
	@Column("archive_cate")
	private Integer archiveCate;

	@Column("archive_ymd")
	private Long archiveYmd;


	/**
	 * 归档时间. 
	 */
	@Column("archive_time")
	private Date archiveTime;

	/**
	 * 
	 * 获取 主键ID.
	 * @return  主键ID.值
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 设置  主键ID值.
	 * @param id 主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
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
	 * 获取 值.
	 * @return  值
	 */
	public Integer getArchiveYear() {
		return archiveYear;
	}

	/**
	 * 
	 * 设置  值.
	 * @param archiveYear 
	 */
	public void setArchiveYear(Integer archiveYear) {
		this.archiveYear = archiveYear;
	}

	/**
	 * 
	 * 获取 归档日期6位值.
	 * @return  归档日期6位值
	 */
	public Integer getArchiveMonth() {
		return archiveMonth;
	}

	/**
	 * 
	 * 设置  归档日期6位值.
	 * @param archiveMonth 归档日期6位
	 */
	public void setArchiveMonth(Integer archiveMonth) {
		this.archiveMonth = archiveMonth;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public Integer getArchiveDay() {
		return archiveDay;
	}

	/**
	 * 
	 * 设置  值.
	 * @param archiveDay 
	 */
	public void setArchiveDay(Integer archiveDay) {
		this.archiveDay = archiveDay;
	}

	/**
	 * 
	 * 获取 归档文章数量值.
	 * @return  归档文章数量值
	 */
	public Integer getArchiveNumber() {
		return archiveNumber;
	}

	/**
	 * 
	 * 设置  归档文章数量值.
	 * @param archiveNumber 归档文章数量
	 */
	public void setArchiveNumber(Integer archiveNumber) {
		this.archiveNumber = archiveNumber;
	}

	/**
	 * 
	 * 获取 1:按天归档,2:按月归档,3:按年归档,5:类目归档值.
	 * @return  1:按天归档,2:按月归档,3:按年归档,5:类目归档值
	 */
	public Integer getArchiveType() {
		return archiveType;
	}

	/**
	 * 
	 * 设置  1:按天归档,2:按月归档,3:按年归档,5:类目归档值.
	 * @param archiveType 1:按天归档,2:按月归档,3:按年归档,5:类目归档
	 */
	public void setArchiveType(Integer archiveType) {
		this.archiveType = archiveType;
	}

	/**
	 * 
	 * 获取 类目值.
	 * @return  类目值
	 */
	public Integer getArchiveCate() {
		return archiveCate;
	}

	/**
	 * 
	 * 设置  类目值.
	 * @param archiveCate 类目
	 */
	public void setArchiveCate(Integer archiveCate) {
		this.archiveCate = archiveCate;
	}

	/**
	 * 
	 * 获取 归档时间值.
	 * @return  归档时间值
	 */
	public Date getArchiveTime() {
		return archiveTime;
	}

	public Long getArchiveYmd() {
		return archiveYmd;
	}

	public void setArchiveYmd(Long archiveYmd) {
		this.archiveYmd = archiveYmd;
	}
	/**
	 * 
	 * 设置  归档时间值.
	 * @param archiveTime 归档时间
	 */
	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}
	
	public Integer getOldArchiveNumber() {
		return oldArchiveNumber;
	}

	public void setOldArchiveNumber(Integer oldArchiveNumber) {
		this.oldArchiveNumber = oldArchiveNumber;
	}

}
