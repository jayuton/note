package com.jayu.note.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jayu.mybatis.dao.BaseDAO;
import com.jayu.note.dao.model.ArticleArchive;
/**
 * 摘要：文章归档 数据库访问接口.
 * <P>
 * @author jayu
 * @version V1.0 2015-12-13
 * @createDate 2015-12-13 20:58:53
 * @modifyDate jayu 2015-12-13
 * @since   JDK1.7
 */
@Repository("ArticleArchiveDao")
public interface ArticleArchiveDao extends BaseDAO<ArticleArchive, Long> {
	

	public Integer deleteByUserId(@Param(value = "userId") long userId);
}
