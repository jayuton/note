package com.jayu.note.bmo;

import java.util.List;

import com.jayu.exception.BusinessException;
import com.jayu.mybatis.bmo.BaseBMO;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.ArticleArchive;

/**
 * 文章归档  业务接口 概述 .
 * <P>
 * @author jayu
 * @version V1.0 2015-12-13
 * @createDate 2015-12-13 20:58:53
 * @modifyDate jayu 2015-12-13
 * @since   JDK1.7
 */
public interface ArticleArchiveBmo extends BaseBMO<ArticleArchive, Long> {
	public static final String TABLE_ALIAS = "articleArchiveDao.";
	public static final String COLUMN_DAY_ASC = "archive_year.archive_month,archive_day ASC";
	public static final String COLUMN_DAY_DESC = "archive_ymd DESC";

	public static final String COLUMN_MONTH_DESC = "archive_ymd DESC";
	
	/**
	 * 
	 * 分页业务逻辑方法 无需事务处理.默认升序排序查询所有
	 * @param pageNo 当前页码
	 * @return PageModel<ArticleArchiveDao> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<ArticleArchive> query(int pageNo)
			throws BusinessException;
	
	/**
	 *  查找用户按天归档文章数据
	 * @param userId 用户
	 * @return List<ArticleArchive> 文章归档列表
	 * @throws BusinessException
	 */
	public List<ArticleArchive> findDataOfDayByUserId(long userId) throws BusinessException;
	
	/**
	 *  查找用户按月归档文章数据
	 * @param userId 用户
	 * @return List<ArticleArchive> 文章归档列表
	 * @throws BusinessException
	 */
	public List<ArticleArchive> findDataOfMonthByUserId(long userId) throws BusinessException;
	
	/**
	 * 查找按类目归档数据
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public List<ArticleArchive> findDataOfCateByUserId(long userId) throws BusinessException;
	/**
	 * 动态归档，日，月，类目
	 * @param article
	 * @param isAddOrDel
	 * @return
	 * @throws BusinessException
	 */
	public Integer archiveYMC(Article article,boolean isAddOrDel) throws BusinessException;
	/**
	 * 归档文章编辑，类目变化
	 * @param article
	 * @param oldCateId
	 * @return
	 * @throws BusinessException
	 */
	public Integer archiveEditC(Article article,int oldCateId) throws BusinessException;

}
