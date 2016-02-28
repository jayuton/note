package com.jayu.note.bmo;

import com.jayu.exception.BusinessException;
import com.jayu.mybatis.bmo.BaseBMO;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.note.dao.model.Mquartz;

/**
 * 文章归档  业务接口 概述 .
 * <P>
 * @author jayu
 * @version V1.0 2015-12-25
 * @createDate 2015-12-25 21:22:36
 * @modifyDate jayu 2015-12-25
 * @since   JDK1.7
 */
public interface MquartzBmo extends BaseBMO<Mquartz, String> {
	public static final String TABLE_ALIAS = "mquartz.";
	public static final String COLUMN_SORT_ORDER_ASC = "SORT_ORDER ASC";
	public static final String COLUMN_SORT_ORDER_DESC = "SORT_ORDER DESC";

	/**
	 * 
	 * 分页业务逻辑方法 无需事务处理.默认升序排序查询所有
	 * @param pageNo 当前页码
	 * @return PageModel<Mquartz> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<Mquartz> query(int pageNo) throws BusinessException;
	
	public Integer deleteByBizIdType(String bizId, String actionType) throws BusinessException;
	
	public Integer deleteByUserId(long userId) throws BusinessException;

}
