package com.jayu.note.dao;

import com.jayu.note.dao.model.Mquartz;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jayu.mybatis.dao.BaseDAO;

/**
 * 摘要：文章归档 数据库访问接口.
 * <P>
 * @author jayu
 * @version V1.0 2015-12-25
 * @createDate 2015-12-25 21:22:36
 * @modifyDate jayu 2015-12-25
 * @since   JDK1.7
 */
@Repository("MquartzDao")
public interface MquartzDao extends BaseDAO<Mquartz, String> {
	
	public Integer deleteByBizIdType(@Param(value = "bizId") String bizId,@Param(value = "actionType") String actionType) throws Exception;
	
	public Integer deleteByUserId(@Param(value = "userId") long userId) throws Exception;
	
	
}
