package com.jayu.note.dao;

import com.jayu.note.dao.model.EmailSend;
import org.springframework.stereotype.Repository;
import com.jayu.mybatis.dao.BaseDAO;

/**
 * 摘要：邮件发送列表 数据库访问接口.
 * <P>
 * @author jayu
 * @version V1.0 2016-02-12
 * @createDate 2016-02-12 15:41:53
 * @modifyDate jayu 2016-02-12
 * @since   JDK1.7
 */
@Repository("EmailSendDao")
public interface EmailSendDao extends BaseDAO<EmailSend, String> {
}
