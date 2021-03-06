package com.jayu.note.bmo;

import com.jayu.exception.BusinessException;
import com.jayu.mybatis.bmo.BaseBMO;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.note.dao.model.ArticleContent;

/**
 * 文章内容  业务接口 概述 .
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:36:52
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
public interface ArticleContentBmo extends BaseBMO<ArticleContent, Long> {
	public static final String TABLE_ALIAS = "articleContent.";
	public static final String COLUMN_SORT_ORDER_ASC = "SORT_ORDER ASC";
	public static final String COLUMN_SORT_ORDER_DESC = "SORT_ORDER DESC";

	/**
	 * 
	 * 分页业务逻辑方法 无需事务处理.默认升序排序查询所有
	 * @param pageNo 当前页码
	 * @return PageModel<ArticleContent> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<ArticleContent> query(int pageNo) throws BusinessException;


}
