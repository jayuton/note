package com.jayu.note.dao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jayu.note.common.AttributeUtil;
import com.jayu.mybatis.entity.Column;
/**
 * 摘要：文章归档实体.
 * <P>
 * @author jayu
 * @version V1.0 2015-12-25
 * @createDate 2015-12-25 21:22:36
 * @modifyDate jayu 2015-12-25
 * @since   JDK1.7
 */
public class Mquartz implements Serializable {

	/**
	 * 主键:业务ID：文章，类目等等. 
	 */
	@Column("biz_id")
	private String bizId;

	/**
	 * 主键:. 
	 */
	@Column("action_type")
	private String actionType;

	/**
	 * 0:待执行,1:执行成功,2:执行失败. 
	 */
	@Column("action_status")
	private Integer actionStatus = 0;

	/**
	 * 执行次数，最多5次,每次失败间隔5秒递增. 
	 */
	@Column("action_count")
	private Integer actionCount = 0;

	/**
	 * 开始执行时间. 
	 */
	@Column("action_starttime")
	private Date actionStarttime;

	/**
	 * 执行参数. 
	 */
	@Column("action_attribute")
	private String actionAttribute;

	private Map<String, String> actionAttributeMap = new HashMap<String, String>();
	
	
	private long userId;
	


	/**
	 * 
	 * 获取 业务ID：文章，类目等等值.
	 * @return  业务ID：文章，类目等等值
	 */
	public String getBizId() {
		return bizId;
	}

	/**
	 * 
	 * 设置  业务ID：文章，类目等等值.
	 * @param bizId 业务ID：文章，类目等等
	 */
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * 
	 * 设置  值.
	 * @param actionType 
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * 
	 * 获取 0:待执行,1:执行成功,2:执行失败值.
	 * @return  0:待执行,1:执行成功,2:执行失败值
	 */
	public Integer getActionStatus() {
		return actionStatus;
	}

	/**
	 * 
	 * 设置  0:待执行,1:执行成功,2:执行失败值.
	 * @param actionStatus 0:待执行,1:执行成功,2:执行失败
	 */
	public void setActionStatus(Integer actionStatus) {
		this.actionStatus = actionStatus;
	}

	/**
	 * 
	 * 获取 执行次数，最多5次,每次失败间隔5秒递增值.
	 * @return  执行次数，最多5次,每次失败间隔5秒递增值
	 */
	public Integer getActionCount() {
		return actionCount;
	}

	/**
	 * 
	 * 设置  执行次数，最多5次,每次失败间隔5秒递增值.
	 * @param actionCount 执行次数，最多5次,每次失败间隔5秒递增
	 */
	public void setActionCount(Integer actionCount) {
		this.actionCount = actionCount;
	}

	/**
	 * 
	 * 获取 开始执行时间值.
	 * @return  开始执行时间值
	 */
	public Date getActionStarttime() {
		return actionStarttime;
	}

	/**
	 * 
	 * 设置  开始执行时间值.
	 * @param actionStarttime 开始执行时间
	 */
	public void setActionStarttime(Date actionStarttime) {
		this.actionStarttime = actionStarttime;
	}

	/**
	 * 
	 * 获取 执行参数值.
	 * @return  执行参数值
	 */
	public String getActionAttribute() {
		return AttributeUtil.toString(actionAttributeMap);
	}

	/**
	 * 
	 * 设置  执行参数值.
	 * @param actionAttribute 执行参数
	 */
	public void setActionAttribute(String actionAttribute) {
		this.actionAttributeMap.putAll(AttributeUtil.fromString(actionAttribute));
	}
	
	public Map<String, String> getActionAttributeMap() {
		return actionAttributeMap;
	}

	public void putActionAttribute(String key,String value) {
		this.actionAttributeMap.put(key, value);
	}
	public String getActionAttributeByKey(String key) {
		return this.actionAttributeMap.get(key);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
