package com.jayu.note.dao;

import java.util.List;

import com.jayu.note.dao.model.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jayu.mybatis.dao.BaseDAO;

/**
 * 摘要：文章类目 数据库访问接口.
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:35:02
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
@Repository("CategoryDao")
public interface CategoryDao extends BaseDAO<Category, Integer> {
	
    /**
     * 通过父节点ID查询该父节点下所有一级子节点
     * @param userId 用户userId
     * @param id 实体id
     * @return List<Category> 父节点下所有一级子节点
     */
    public List<Category> getChildrenByParentId(@Param(value = "userId") long userId,@Param(value = "id") int id) throws Exception;
    
    public List<Category> getAllChildrenByParentId(int id) throws Exception; 
    
    public Integer deleteByUserId(@Param(value = "userId") long userId);
    
    /**
     * 更新排序字段
     * @param parentId 父节点
     * @param sortOrder 临界排序值
     * @param isAdd true:加1，否则减-1
     * @param isLess true:大于,false:小于
     * @return int
     * @throws Exception
     */
    public int  updateAllSortOrder(@Param(value = "id") long parentId,@Param(value = "sortOrder") int sortOrder,@Param(value = "isAdd") boolean isAdd,
    		@Param(value = "isLess") boolean isLess) throws Exception;
 
}
