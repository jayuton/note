package com.jayu.note.dao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.jayu.mybatis.entity.Column;
/**
 * 摘要：文章类目实体.
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:35:02
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
public class Category implements Serializable {

	/**
	 * 主键:. 
	 */
	@Column("id")
	private Integer id;

	/**
	 * 菜单名称. 
	 */
	@Column("name")
	private String name;

	/**
	 * 请求地址. 
	 */
	@Column("url")
	private String url;

	/**
	 * 1:普通下拉,2:分级平铺,3:分级全铺. 
	 */
	@Column("layout")
	private Integer layout;

	/**
	 * . 
	 */
	@Column("parent_id")
	private Integer parentId;

	/**
	 * file,folder. 
	 */
	@Column("node_type")
	private String type;

	/**
	 * . 
	 */
	@Column("sort_order")
	private Integer sortOrder;

	/**
	 * . 
	 */
	@Column("status")
	private Integer status;

	/**
	 * . 
	 */
	@Column("create_dt")
	private Date createDt;

	/**
	 * 用户ID. 
	 */
	@Column("user_id")
	private Long userId;
	/** 类别级别 */
	@Column("is_default")
	private Integer isDefault;
	/** 类别级别 */
	@Column("level")
	private Integer level;
	@Column("id_path")
	private String idPath;

	/** 子节点 */
	private List<Category> childrenList =null;
	/** 文章数量 */
	private int articleNum = 0;
	

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * 设置  值.
	 * @param id 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * 获取 菜单名称值.
	 * @return  菜单名称值
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * 设置  菜单名称值.
	 * @param name 菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * 获取 请求地址值.
	 * @return  请求地址值
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * 设置  请求地址值.
	 * @param url 请求地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * 获取 1:普通下拉,2:分级平铺,3:分级全铺值.
	 * @return  1:普通下拉,2:分级平铺,3:分级全铺值
	 */
	public Integer getLayout() {
		return layout;
	}

	/**
	 * 
	 * 设置  1:普通下拉,2:分级平铺,3:分级全铺值.
	 * @param layout 1:普通下拉,2:分级平铺,3:分级全铺
	 */
	public void setLayout(Integer layout) {
		this.layout = layout;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 
	 * 设置  值.
	 * @param parentId 
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 
	 * 获取 file,folder值.
	 * @return  file,folder值
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * 设置  file,folder值.
	 * @param nodeType file,folder
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * 
	 * 设置  值.
	 * @param sortOrder 
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * 
	 * 获取 值.
	 * @return  值
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 
	 * 设置  值.
	 * @param status 
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}
	
	public List<Category> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<Category> childrenList) {
		this.childrenList = childrenList;
	}
	/** 文章数量 */
	public int getArticleNum() {
		return articleNum;
	}
	/** 文章数量 */
	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}
}
