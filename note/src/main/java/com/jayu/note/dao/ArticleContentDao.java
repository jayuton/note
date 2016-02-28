package com.jayu.note.dao;

import com.jayu.note.dao.model.ArticleContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jayu.mybatis.dao.BaseDAO;

/**
 * 摘要：文章内容 数据库访问接口.
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:36:52
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
@Repository("ArticleContentDao")
public interface ArticleContentDao extends BaseDAO<ArticleContent, Long> {
	public Integer deleteByAuthorId(@Param(value = "authorId") long authorId) throws Exception;
}
