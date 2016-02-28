package com.jayu.note.bmo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.bmo.EmailSendBmo;
import com.jayu.note.dao.EmailSendDao;
import com.jayu.note.dao.model.EmailSend;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jayu.mybatis.bmo.BaseBMOImpl;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.mybatis.entity.PageUtil;
import com.jayu.exception.BusinessException;
import com.jayu.exception.ResultConstant;
import com.jayu.log.Log;

/**
 * 摘要：邮件发送列表 业务实现.
 *  
 * <P>
 * @author jayu
 * @version V1.0 2016-02-12
 * @createDate 2016-02-12 15:41:53
 */
@Service("EmailSendBmoImpl")
public class EmailSendBmoImpl extends BaseBMOImpl<EmailSend, String> implements
		EmailSendBmo {
	protected static Log log = Log.getLog(EmailSendBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "EmailSendDao")
	private EmailSendDao emailSendDao;

	@Override
	public Integer insert(EmailSend emailSend) throws BusinessException {
		try {
			Assert.notNull(emailSend, "EmailSend不能为NULL!");
			return this.emailSendDao.insert(emailSend);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(EmailSend emailSend)
			throws BusinessException {
		try {
			Assert.notNull(emailSend, "EmailSend不能为NULL!");
			return this.emailSendDao.insertSelective(emailSend);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(EmailSend emailSend) throws BusinessException {
		try {
			Assert.notNull(emailSend, "EmailSend不能为NULL!");
			return this.emailSendDao.update(emailSend);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(String... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			this.emailSendDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("批量插入邮件发送列表失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer delete(String id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.emailSendDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public EmailSend get(String id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.emailSendDao.get(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	/**
	 * 
	 * 分页业务逻辑方法  无需事务处理.
	 * @param pd 页面请求数据封装，包括分页信息等
	 * @return 分页数据对象
	 * @throws BusinessException 业务层自定义异常 
	 */
	public PageModel<EmailSend> query(PageDomain<EmailSend> pd)
			throws BusinessException {
		Assert.notNull(pd, "PageDomain<EmailSend>不能为NULL!");
		PageModel<EmailSend> pm = new PageModel<EmailSend>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.emailSendDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<EmailSend> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.emailSendDao.findPageDataByWhereAndOrderBy(
						pm.getStartIndex(), pm.getPageSize(), pd);
			}
			pm = PageUtil.buildPageModel(pd, list, totalRecords);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
		return pm;
	}

	/**
	 * 
	 * 分页业务逻辑方法 无需事务处理.默认升序排序查询所有
	 * @param pageNo 当前页码
	 * @return PageModel<EmailSend> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<EmailSend> query(int pageNo) throws BusinessException {
		PageDomain<EmailSend> pd = new PageDomain<EmailSend>();
		pd.pageNo(pageNo).orderBy(COLUMN_SORT_ORDER_ASC);
		return query(pd);
	}

	/**
	 * 根据条件查询
	 * @param pd PageDomain<EmailSend>
	 * @return List<EmailSend> 邮件发送列表列表
	 * @throws BusinessException
	 */
	public List<EmailSend> findDataByCondition(PageDomain<EmailSend> pd)
			throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.emailSendDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
}
