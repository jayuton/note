package com.jayu.note.dao;

import com.jayu.note.dao.model.User;
import org.springframework.stereotype.Repository;
import com.jayu.mybatis.dao.BaseDAO;

/**
 * 摘要：用户 数据库访问接口.
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:25:35
 * @modifyDate jayu 2015-11-22
 * @since   JDK1.7
 */
@Repository("UserDao")
public interface UserDao extends BaseDAO<User, Long> {
}
