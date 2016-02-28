package com.jayu.note.dao;

import java.util.List;

import com.jayu.note.dao.model.ArticleArchive;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jayu.mybatis.dao.BaseDAO;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.note.dao.model.Article;

/**
 * 摘要：文章 数据库访问接口.
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:38:53
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
@Repository("ArticleDao")
public interface ArticleDao extends BaseDAO<Article, Long> {
	
	public List<ArticleArchive> getCountGroupByUser(@Param(value = "pd") PageDomain<Article> pd);

	public Integer deleteByAuthorId(@Param(value = "authorId") long authorId);
	
	public Integer deleteByCateId(@Param(value = "authorId") long authorId,@Param(value = "cateId") Integer cateId);
	public Integer deleteByCateIdArray(@Param(value = "authorId") long authorId,@Param(value = "cateIdArray") Integer[] cateIdArray);
}
