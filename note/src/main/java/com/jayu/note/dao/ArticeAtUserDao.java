package com.jayu.note.dao;

import org.springframework.stereotype.Repository;
import com.jayu.mybatis.dao.BaseDAO;
import com.jayu.note.dao.model.ArticeAtUser;
/**
 * 摘要：日志@用户查看权限 数据库访问接口.
 * <P>
 * @author jayu
 * @version V1.0 2016-02-06
 * @createDate 2016-02-06 19:26:21
 * @modifyDate jayu 2016-02-06
 * @since   JDK1.7
 */
@Repository("ArticeAtUserDao")
public interface ArticeAtUserDao extends BaseDAO<ArticeAtUser, Long> {
}
