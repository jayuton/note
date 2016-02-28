package com.jayu.note.bmo;

import java.util.List;

import com.jayu.exception.BusinessException;
import com.jayu.mybatis.bmo.BaseBMO;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.ArticleArchive;

/**
 * 文章  业务接口 概述 .
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:38:53
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
public interface ArticleBmo extends BaseBMO<Article, Long> {
	public static final String TABLE_ALIAS = "article.";
	public static final String COLUMN_SORT_ORDER_ASC = "SORT_ORDER ASC";
	public static final String COLUMN_SORT_ORDER_DESC = "SORT_ORDER DESC";

	/**
	 * 
	 * 分页业务逻辑方法 无需事务处理.默认升序排序查询所有
	 * @param pageNo 当前页码
	 * @return PageModel<Article> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<Article> query(int pageNo) throws BusinessException;
	/**
	 * 保存文章
	 * @param article 文章对象
	 * @return Integer
	 * @throws BusinessException
	 */
	public Integer save(Article article) throws BusinessException;
	/**
	 * 编辑文章更新
	 * @param article
	 * @return
	 * @throws BusinessException
	 */
	public Integer edit(Article article,int oldCateId) throws BusinessException;
	/**
	 * 按用户统计数量
	 * @param article
	 * @return
	 * @throws BusinessException
	 */
	public List<ArticleArchive> getCountGroupByUser(PageDomain<Article> pd)  throws BusinessException;
	
	/**
	 * 获取作者的文章
	 * @param articleId
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Article get(long articleId,long userId)
			throws BusinessException;
	
	public Integer deleteByUserId(Long authorId) throws BusinessException;
	
	public Integer deleteArticle(Article article) throws BusinessException ;

}
