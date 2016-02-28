package com.jayu.note.bmo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jayu.mybatis.bmo.BaseBMOImpl;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.mybatis.entity.PageUtil;
import com.jayu.exception.BusinessException;
import com.jayu.exception.ResultConstant;
import com.jayu.log.Log;
import com.jayu.note.bmo.ArticleContentBmo;
import com.jayu.note.dao.ArticleContentDao;
import com.jayu.note.dao.model.ArticleContent;

/**
 * 摘要：文章内容 业务实现.
 *  
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:36:52
 */
@Service("ArticleContentBmoImpl")
public class ArticleContentBmoImpl extends BaseBMOImpl<ArticleContent, Long>
		implements ArticleContentBmo {
	protected static Log log = Log.getLog(ArticleContentBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "ArticleContentDao")
	private ArticleContentDao articleContentDao;

	@Override
	public Integer insert(ArticleContent articleContent)
			throws BusinessException {
		try {
			Assert.notNull(articleContent, "ArticleContent不能为NULL!");
			return this.articleContentDao.insert(articleContent);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(ArticleContent articleContent)
			throws BusinessException {
		try {
			Assert.notNull(articleContent, "ArticleContent不能为NULL!");
			return this.articleContentDao.insertSelective(articleContent);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(ArticleContent articleContent)
			throws BusinessException {
		try {
			Assert.notNull(articleContent, "ArticleContent不能为NULL!");
			return this.articleContentDao.update(articleContent);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(Long... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			this.articleContentDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("批量插入文章内容失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer delete(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articleContentDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public ArticleContent get(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articleContentDao.get(id);
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
	public PageModel<ArticleContent> query(PageDomain<ArticleContent> pd)
			throws BusinessException {
		Assert.notNull(pd, "PageDomain<ArticleContent>不能为NULL!");
		PageModel<ArticleContent> pm = new PageModel<ArticleContent>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.articleContentDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<ArticleContent> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.articleContentDao.findPageDataByWhereAndOrderBy(
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
	 * @return PageModel<ArticleContent> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<ArticleContent> query(int pageNo) throws BusinessException {
		PageDomain<ArticleContent> pd = new PageDomain<ArticleContent>();
		pd.pageNo(pageNo).orderBy(COLUMN_SORT_ORDER_ASC);
		return query(pd);
	}

	/**
	 * 根据条件查询
	 * @param pd PageDomain<ArticleContent>
	 * @return List<ArticleContent> 文章内容列表
	 * @throws BusinessException
	 */
	public List<ArticleContent> findDataByCondition(
			PageDomain<ArticleContent> pd) throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.articleContentDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

}
