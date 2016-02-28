package com.jayu.note.bmo;

import com.jayu.exception.BusinessException;
import com.jayu.mybatis.bmo.BaseBMO;
import com.jayu.mybatis.entity.PageModel;

import com.jayu.note.dao.model.ArticleFile;

/**
 * 邮件发送列表  业务接口 概述 .
 * <P>
 * @author jayu
 * @version V1.0 2016-02-13
 * @createDate 2016-02-13 19:54:53
 * @modifyDate jayu 2016-02-13
 * @since   JDK1.7
 */
public interface ArticleFileBmo extends BaseBMO<ArticleFile, String> {
	public static final String TABLE_ALIAS = "articleFile.";
	public static final String COLUMN_SORT_ORDER_ASC = "SORT_ORDER ASC";
	public static final String COLUMN_SORT_ORDER_DESC = "SORT_ORDER DESC";

	/**
	 * 
	 * 分页业务逻辑方法 无需事务处理.默认升序排序查询所有
	 * @param pageNo 当前页码
	 * @return PageModel<ArticleFile> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<ArticleFile> query(int pageNo) throws BusinessException;
	
	/**
	 * 返回文件条数
	 * @param userId 用户ID
	 * @return int 文件条数
	 * @throws BusinessException
	 */
	public int getCountByUser(long userId)	throws BusinessException;

}
