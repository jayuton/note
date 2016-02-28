package com.jayu.note.bmo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.dao.MquartzDao;
import com.jayu.note.dao.model.Mquartz;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jayu.mybatis.bmo.BaseBMOImpl;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.mybatis.entity.PageUtil;
import com.jayu.exception.BusinessException;
import com.jayu.exception.ResultConstant;
import com.jayu.log.Log;
import com.jayu.note.bmo.MquartzBmo;

/**
 * 摘要：文章归档 业务实现.
 *  
 * <P>
 * @author jayu
 * @version V1.0 2015-12-25
 * @createDate 2015-12-25 21:22:36
 * @see MquartzBmoImpl
 */
@Service("MquartzBmoImpl")
public class MquartzBmoImpl extends BaseBMOImpl<Mquartz, String> implements
		MquartzBmo {
	protected static Log log = Log.getLog(MquartzBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "MquartzDao")
	private MquartzDao mquartzDao;

	@Override
	public Integer insert(Mquartz mquartz) throws BusinessException {
		try {
			Assert.notNull(mquartz, "Mquartz不能为NULL!");
			return this.mquartzDao.insert(mquartz);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(Mquartz mquartz) throws BusinessException {
		try {
			Assert.notNull(mquartz, "Mquartz不能为NULL!");
			return this.mquartzDao.insertSelective(mquartz);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(Mquartz mquartz) throws BusinessException {
		try {
			Assert.notNull(mquartz, "Mquartz不能为NULL!");
			return this.mquartzDao.update(mquartz);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(String... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			this.mquartzDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("批量插入文章归档失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer delete(String id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.mquartzDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	@Override
	public Integer deleteByBizIdType(String bizId, String actionType) throws BusinessException {
		try {
			Assert.notNull(bizId, "bizId 不能为NULL!");
			Assert.notNull(actionType, "actionType 不能为NULL!");
			return this.mquartzDao.deleteByBizIdType(bizId, actionType);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	public Integer deleteByUserId(long userId) throws BusinessException {
		try {
			Assert.isTrue(userId>0, "userId 不能为NULL!");
			return this.mquartzDao.deleteByUserId(userId);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	
	@Override
	public Mquartz get(String id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.mquartzDao.get(id);
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
	public PageModel<Mquartz> query(PageDomain<Mquartz> pd)
			throws BusinessException {
		Assert.notNull(pd, "PageDomain<Mquartz>不能为NULL!");
		PageModel<Mquartz> pm = new PageModel<Mquartz>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.mquartzDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<Mquartz> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.mquartzDao.findPageDataByWhereAndOrderBy(
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
	 * @return PageModel<Mquartz> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<Mquartz> query(int pageNo) throws BusinessException {
		PageDomain<Mquartz> pd = new PageDomain<Mquartz>();
		pd.pageNo(pageNo).orderBy(COLUMN_SORT_ORDER_ASC);
		return query(pd);
	}
	
	/**
	 * 根据条件查询
	 * @param pd PageDomain<Mquartz>
	 * @return List<Mquartz> 文章归档列表
	 * @throws BusinessException
	 */
	public List<Mquartz> findDataByCondition(PageDomain<Mquartz> pd)
			throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.mquartzDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
}
