package com.jayu.note.bmo;

import java.util.List;

import com.jayu.exception.BusinessException;
import com.jayu.mybatis.bmo.BaseBMO;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.note.dao.model.Category;

/**
 * 文章类目  业务接口 概述 .
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:35:02
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
public interface CategoryBmo extends BaseBMO<Category, Integer> {
	public static final String TABLE_ALIAS = "category.";
	public static final String COLUMN_SORT_ORDER_ASC = "SORT_ORDER ASC";
	public static final String COLUMN_SORT_ORDER_DESC = "SORT_ORDER DESC";

	/**
	 * 
	 * 分页业务逻辑方法 无需事务处理.默认升序排序查询所有
	 * @param pageNo 当前页码
	 * @return PageModel<Category> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<Category> query(int pageNo) throws BusinessException;
	
	/**
	 * 查询数据
	 * @param userId
	 * @param id
	 * @return Category
	 * @throws BusinessException
	 */
	public Category getCategoryById(long userId , int id) throws BusinessException;
	
	/**
	 * 通过父节点，获取所有子节点数据
	 * @param userId
	 * @param parentId
	 * @return List<Category>
	 * @throws BusinessException
	 */
	public List<Category> getChildrenByParentId(long userId , int parentId) throws BusinessException;
	
	/**
	 * 根据父节点查询所有子节点数据
	 * @param parentId
	 * @return
	 * @throws BusinessException
	 */
	public List<Category> getAllChildrenByParentId(int parentId) throws BusinessException;
	
	/**
	 * 查找所有类目，纵深3级
	 * @param userId 用户
	 * @return List<Category>
	 * @throws BusinessException
	 */
	public List<Category> getAllCategoryList(long userId) throws BusinessException;
	
	/**
	 * 更新类目数据，并重新排序
	 * @param category
	 * @param minSortOrder 临界排序值
	 * @param isAdd
	 * @param isLess
	 * @return
	 * @throws BusinessException
	 */
	public Integer update(Category category,int minSortOrder, boolean isAdd,boolean isLess) throws BusinessException;
	
	public List<Category> getParents(Integer parentId) throws BusinessException;
	
	public Category getDefaultCategory(long userId ) throws BusinessException;
	
	public Integer[] deleteCategory(Category category) throws BusinessException;
	
	public Integer save(Category category) throws BusinessException;

}
