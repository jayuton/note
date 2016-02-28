package com.jayu.note.bmo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.bmo.ArticeAtUserBmo;
import com.jayu.note.dao.ArticeAtUserDao;
import com.jayu.note.dao.model.ArticeAtUser;
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
 * 摘要：日志@用户查看权限 业务实现.
 *  
 * <P>
 * @author jayu
 * @version V1.0 2016-02-06
 * @createDate 2016-02-06 19:26:21
 * @see ArticeAtUserBmoImpl
 */
@Service("ArticeAtUserBmoImpl")
public class ArticeAtUserBmoImpl extends BaseBMOImpl<ArticeAtUser, Long>
		implements ArticeAtUserBmo {
	protected static Log log = Log.getLog(ArticeAtUserBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "ArticeAtUserDao")
	private ArticeAtUserDao articeAtUserDao;

	@Override
	public Integer insert(ArticeAtUser articeAtUser) throws BusinessException {
		try {
			Assert.notNull(articeAtUser, "ArticeAtUser不能为NULL!");
			return this.articeAtUserDao.insert(articeAtUser);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(ArticeAtUser articeAtUser)
			throws BusinessException {
		try {
			Assert.notNull(articeAtUser, "ArticeAtUser不能为NULL!");
			return this.articeAtUserDao.insertSelective(articeAtUser);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(ArticeAtUser articeAtUser) throws BusinessException {
		try {
			Assert.notNull(articeAtUser, "ArticeAtUser不能为NULL!");
			return this.articeAtUserDao.update(articeAtUser);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(Long... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			this.articeAtUserDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("批量插入日志@用户查看权限失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer delete(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articeAtUserDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public ArticeAtUser get(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articeAtUserDao.get(id);
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
	public PageModel<ArticeAtUser> query(PageDomain<ArticeAtUser> pd)
			throws BusinessException {
		Assert.notNull(pd, "PageDomain<ArticeAtUser>不能为NULL!");
		PageModel<ArticeAtUser> pm = new PageModel<ArticeAtUser>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.articeAtUserDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<ArticeAtUser> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.articeAtUserDao.findPageDataByWhereAndOrderBy(
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
	 * @return PageModel<ArticeAtUser> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<ArticeAtUser> query(int pageNo) throws BusinessException {
		PageDomain<ArticeAtUser> pd = new PageDomain<ArticeAtUser>();
		pd.pageNo(pageNo).orderBy(COLUMN_SORT_ORDER_ASC);
		return query(pd);
	}

	/**
	 * 根据条件查询
	 * @param pd PageDomain<ArticeAtUser>
	 * @return List<ArticeAtUser> 日志@用户查看权限列表
	 * @throws BusinessException
	 */
	public List<ArticeAtUser> findDataByCondition(PageDomain<ArticeAtUser> pd)
			throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.articeAtUserDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	/**
	 * 根据条件查询
	 * @param pd PageDomain<ArticeAtUser>
	 * @return List<ArticeAtUser> 日志@用户查看权限列表
	 * @throws BusinessException
	 */
	public List<ArticeAtUser> findDataByCondition(long articleId)
			throws BusinessException {
		try {
			PageDomain<ArticeAtUser> pd = new PageDomain<ArticeAtUser>();
			pd.equals("article_id", articleId); 
			return this.articeAtUserDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
}
