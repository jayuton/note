package com.jayu.note.bmo;

import java.util.List;

import com.jayu.exception.BusinessException;
import com.jayu.mybatis.bmo.BaseBMO;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.note.dao.model.ArticeAtUser;

/**
 * 日志@用户查看权限  业务接口 概述 .
 * <P>
 * @author jayu
 * @version V1.0 2016-02-06
 * @createDate 2016-02-06 19:26:21
 * @modifyDate jayu 2016-02-06
 * @since   JDK1.7
 */
public interface ArticeAtUserBmo extends BaseBMO<ArticeAtUser, Long> {
	public static final String TABLE_ALIAS = "articeAtUser.";
	public static final String COLUMN_SORT_ORDER_ASC = "SORT_ORDER ASC";
	public static final String COLUMN_SORT_ORDER_DESC = "SORT_ORDER DESC";

	/**
	 * 
	 * 分页业务逻辑方法 无需事务处理.默认升序排序查询所有
	 * @param pageNo 当前页码
	 * @return PageModel<ArticeAtUser> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<ArticeAtUser> query(int pageNo) throws BusinessException;
	
	public List<ArticeAtUser> findDataByCondition(long articleId)
			throws BusinessException ;

}
