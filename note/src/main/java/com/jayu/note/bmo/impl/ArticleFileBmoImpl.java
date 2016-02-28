package com.jayu.note.bmo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.bmo.ArticleFileBmo;
import com.jayu.note.dao.ArticleFileDao;
import com.jayu.note.dao.model.ArticleFile;
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
 * @version V1.0 2016-02-13
 * @createDate 2016-02-13 19:54:53
 */
@Service("ArticleFileBmoImpl")
public class ArticleFileBmoImpl extends BaseBMOImpl<ArticleFile, String>
		implements ArticleFileBmo {
	protected static Log log = Log.getLog(ArticleFileBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "ArticleFileDao")
	private ArticleFileDao articleFileDao;

	@Override
	public Integer insert(ArticleFile articleFile) throws BusinessException {
		try {
			Assert.notNull(articleFile, "ArticleFile不能为NULL!");
			return this.articleFileDao.insert(articleFile);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(ArticleFile articleFile)
			throws BusinessException {
		try {
			Assert.notNull(articleFile, "ArticleFile不能为NULL!");
			return this.articleFileDao.insertSelective(articleFile);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(ArticleFile articleFile) throws BusinessException {
		try {
			Assert.notNull(articleFile, "ArticleFile不能为NULL!");
			return this.articleFileDao.update(articleFile);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(String... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			this.articleFileDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("批量插入邮件发送列表失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer delete(String id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articleFileDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public ArticleFile get(String id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articleFileDao.get(id);
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
	public PageModel<ArticleFile> query(PageDomain<ArticleFile> pd)
			throws BusinessException {
		Assert.notNull(pd, "PageDomain<ArticleFile>不能为NULL!");
		PageModel<ArticleFile> pm = new PageModel<ArticleFile>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.articleFileDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<ArticleFile> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.articleFileDao.findPageDataByWhereAndOrderBy(
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
	 * @return PageModel<ArticleFile> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<ArticleFile> query(int pageNo) throws BusinessException {
		PageDomain<ArticleFile> pd = new PageDomain<ArticleFile>();
		pd.pageNo(pageNo).orderBy(COLUMN_SORT_ORDER_ASC);
		return query(pd);
	}

	/**
	 * 根据条件查询
	 * @param pd PageDomain<ArticleFile>
	 * @return List<ArticleFile> 邮件发送列表列表
	 * @throws BusinessException
	 */
	public List<ArticleFile> findDataByCondition(PageDomain<ArticleFile> pd)
			throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.articleFileDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	/**
	 * 返回文件条数
	 * @param userId 用户ID
	 * @return int 文件条数
	 * @throws BusinessException
	 */
	public int getCountByUser(long userId)	throws BusinessException {
		try {
			PageDomain<ArticleFile> pd = new PageDomain<ArticleFile>();
			pd.equals("user_id", userId);
			return this.articleFileDao.getCount(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
}
