package com.jayu.note.bmo.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.bmo.ArticleArchiveBmo;
import com.jayu.note.common.Const;
import com.jayu.note.dao.ArticleArchiveDao;
import com.jayu.note.dao.model.ArticleArchive;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jayu.mybatis.bmo.BaseBMOImpl;
import com.jayu.mybatis.entity.PageDomain;
import com.jayu.mybatis.entity.PageModel;
import com.jayu.mybatis.entity.PageUtil;
import com.jayu.web.spring.annotation.transactional.Tx;
import com.jayu.exception.BusinessException;
import com.jayu.exception.ResultConstant;
import com.jayu.log.Log;
import com.jayu.note.dao.model.Article;

/**
 * 摘要：文章归档 业务实现.
 *  
 * <P>
 * @author jayu
 * @version V1.0 2015-12-13
 * @createDate 2015-12-13 20:58:53
 * @see ArticleArchiveBmoImpl
 */
@Service("ArticleArchiveBmoImpl")
public class ArticleArchiveBmoImpl extends
		BaseBMOImpl<ArticleArchive, Long> implements ArticleArchiveBmo {
	protected static Log log = Log.getLog(ArticleArchiveBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "ArticleArchiveDao")
	private ArticleArchiveDao articleArchiveDao;

	@Override
	public Integer insert(ArticleArchive articleArchive)
			throws BusinessException {
		try {
			Assert.notNull(articleArchive, "ArticleArchive不能为NULL!");
			return this.articleArchiveDao.insert(articleArchive);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(ArticleArchive articleArchive)
			throws BusinessException {
		try {
			Assert.notNull(articleArchive, "ArticleArchive不能为NULL!");
			return this.articleArchiveDao.insertSelective(articleArchive);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(ArticleArchive articleArchive)
			throws BusinessException {
		try {
			Assert.notNull(articleArchive, "ArticleArchive不能为NULL!");
			return this.articleArchiveDao.update(articleArchive);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(Long... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			this.articleArchiveDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("批量插入文章归档失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer delete(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articleArchiveDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public ArticleArchive get(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articleArchiveDao.get(id);
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
	public PageModel<ArticleArchive> query(PageDomain<ArticleArchive> pd)
			throws BusinessException {
		Assert.notNull(pd, "PageDomain<ArticleArchive>不能为NULL!");
		PageModel<ArticleArchive> pm = new PageModel<ArticleArchive>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.articleArchiveDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<ArticleArchive> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.articleArchiveDao.findPageDataByWhereAndOrderBy(
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
	 * @return PageModel<ArticleArchive> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<ArticleArchive> query(int pageNo)
			throws BusinessException {
		PageDomain<ArticleArchive> pd = new PageDomain<ArticleArchive>();
		pd.pageNo(pageNo);
		return query(pd);
	}

	/**
	 * 根据条件查询
	 * @param pd PageDomain<ArticleArchive>
	 * @return List<ArticleArchive> 文章归档列表
	 * @throws BusinessException
	 */
	public List<ArticleArchive> findDataByCondition(
			PageDomain<ArticleArchive> pd) throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.articleArchiveDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	public List<ArticleArchive> findDataOfDayByUserId(long userId) throws BusinessException {
		try {
			Assert.isTrue(userId>0, "userId 不能小于0!");
			PageDomain<ArticleArchive> pd = new  PageDomain<ArticleArchive>();
			pd.equals("user_id", userId).and().equals("archive_type", Const.ARCHIVE_OF_DAY_TYPE)
			.orderBy(COLUMN_DAY_DESC).rowLimit(true,15);
			return this.articleArchiveDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	public List<ArticleArchive> findDataOfMonthByUserId(long userId) throws BusinessException {
		try {
			Assert.isTrue(userId>0, "userId 不能小于0!");
			PageDomain<ArticleArchive> pd = new  PageDomain<ArticleArchive>();
			pd.equals("user_id", userId).and().equals("archive_type", Const.ARCHIVE_OF_MONTH_TYPE)
			.orderBy(COLUMN_MONTH_DESC).rowLimit(true,15);
			return this.articleArchiveDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	@Override
	public List<ArticleArchive> findDataOfCateByUserId(long userId) throws BusinessException {
		try {
			Assert.isTrue(userId>0, "userId 不能小于0!");
			PageDomain<ArticleArchive> pd = new  PageDomain<ArticleArchive>();
			pd.equals("user_id", userId).and().equals("archive_type", Const.ARCHIVE_OF_CATE_TYPE);
			return this.articleArchiveDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	@Tx
	public Integer archiveYMC(Article article,boolean isAddOrDel) throws BusinessException {
		try {
			Assert.notNull(article, "article 不能为NULL!");
			//日期月归档数据更新
			PageDomain<ArticleArchive>  pdMonth = new PageDomain<ArticleArchive>();
			pdMonth.equals("user_id", article.getAuthorId()).and()
			.equals("archive_type", Const.ARCHIVE_OF_MONTH_TYPE).and()
			.equals("archive_year",article.getWriteYear()).and()
			.equals("archive_month",article.getWriteMonth()).rowLimit(true);
			List<ArticleArchive> articleArchiveList = findDataByCondition(pdMonth);
			int result = 0;
			if(null != articleArchiveList && articleArchiveList.size() > 0){
				ArticleArchive articleArchive = articleArchiveList.get(0);
				articleArchive.setOldArchiveNumber(articleArchive.getArchiveNumber());
				if(isAddOrDel){
					articleArchive.setArchiveNumber(articleArchive.getArchiveNumber()+1);
				}else if(articleArchive.getArchiveNumber()-1>=0){
					articleArchive.setArchiveNumber(articleArchive.getArchiveNumber()-1);
				}
				if(articleArchive.getArchiveNumber()>0){
					result = this.update(articleArchive);
				}else{
					result = this.delete(articleArchive.getId());
				}
				
			}else if(isAddOrDel){
				ArticleArchive articleArchive = new ArticleArchive();
				articleArchive.setUserId(article.getAuthorId());
				articleArchive.setArchiveType( Const.ARCHIVE_OF_MONTH_TYPE);
				articleArchive.setArchiveTime(new Date());
				articleArchive.setArchiveNumber(1);
				articleArchive.setArchiveYear(article.getWriteYear());
				articleArchive.setArchiveMonth(article.getWriteMonth());
				result = this.insertSelective(articleArchive);
			}
			if(result<1){
				throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE,"未更新");
			}
			//日期日归档数据更新
			PageDomain<ArticleArchive>  pdDay= new PageDomain<ArticleArchive>();
			pdDay.equals("user_id", article.getAuthorId()).and()
			.equals("archive_type", Const.ARCHIVE_OF_DAY_TYPE).and()
			.equals("archive_year",article.getWriteYear()).and()
			.equals("archive_month",article.getWriteMonth()).and()
			.equals("archive_day",article.getWriteDay()).rowLimit(true);
			articleArchiveList = this.findDataByCondition(pdDay);
			result = 0;
			if(null != articleArchiveList && articleArchiveList.size() > 0){
				ArticleArchive articleArchive = articleArchiveList.get(0);
				articleArchive.setOldArchiveNumber(articleArchive.getArchiveNumber());
				if(isAddOrDel){
					articleArchive.setArchiveNumber(articleArchive.getArchiveNumber()+1);
				}else if(articleArchive.getArchiveNumber()-1 >=0){
					articleArchive.setArchiveNumber(articleArchive.getArchiveNumber()-1);
				}
				if(articleArchive.getArchiveNumber()>0){
					result = this.update(articleArchive);
				}else{
					result = this.delete(articleArchive.getId());
				}
			}else if(isAddOrDel){
				ArticleArchive articleArchive = new ArticleArchive();
				articleArchive.setUserId(article.getAuthorId());
				articleArchive.setArchiveType(Const.ARCHIVE_OF_DAY_TYPE);
				articleArchive.setArchiveTime(new Date());
				articleArchive.setArchiveNumber(1);
				articleArchive.setArchiveYear(article.getWriteYear());
				articleArchive.setArchiveMonth(article.getWriteMonth());
				articleArchive.setArchiveDay(article.getWriteDay());
				result = this.insertSelective(articleArchive);
			}
			if(result<1){
				throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE,"未更新");
			}
			//类目归档数据更新
			PageDomain<ArticleArchive>  pdCate = new PageDomain<ArticleArchive>();
			pdCate.equals("user_id", article.getAuthorId()).and()
			.equals("archive_type", Const.ARCHIVE_OF_CATE_TYPE).and()
			.equals("archive_cate",article.getCateId()).rowLimit(true);
			articleArchiveList = this.findDataByCondition(pdCate);
			result = 0;
			if(null != articleArchiveList && articleArchiveList.size() > 0){
				ArticleArchive articleArchive = articleArchiveList.get(0);
				articleArchive.setOldArchiveNumber(articleArchive.getArchiveNumber());
				if(isAddOrDel){
					articleArchive.setArchiveNumber(articleArchive.getArchiveNumber()+1);
				}else  if(articleArchive.getArchiveNumber()-1 >=0){
					articleArchive.setArchiveNumber(articleArchive.getArchiveNumber()-1);
				}
				if(articleArchive.getArchiveNumber()>0){
					result = this.update(articleArchive);
				}else{
					result = this.delete(articleArchive.getId());
				}
				
			}else if(isAddOrDel){
				ArticleArchive articleArchive = new ArticleArchive();
				articleArchive.setUserId(article.getAuthorId());
				articleArchive.setArchiveType( Const.ARCHIVE_OF_CATE_TYPE);
				articleArchive.setArchiveTime(new Date());
				articleArchive.setArchiveNumber(1);
				articleArchive.setArchiveCate(article.getCateId());
				result = this.insertSelective(articleArchive);
			}
			if(result<1){
				throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE,"未更新");
			}
			return 1;
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	
	
	@Override
	@Tx
	public Integer archiveEditC(Article article,int oldCateId) throws BusinessException {
		try {
			Assert.notNull(article, "article 不能为NULL!");
			//类目归档数据更新
			PageDomain<ArticleArchive>  pdNewCate = new PageDomain<ArticleArchive>();
			pdNewCate.equals("user_id", article.getAuthorId()).and()
			.equals("archive_type", Const.ARCHIVE_OF_CATE_TYPE).and()
			.equals("archive_cate",article.getCateId()).rowLimit(true);
			List<ArticleArchive> articleArchiveList = this.findDataByCondition(pdNewCate);
			int result = 0;
			if(null != articleArchiveList && articleArchiveList.size() > 0){
				ArticleArchive articleArchive = articleArchiveList.get(0);
				articleArchive.setOldArchiveNumber(articleArchive.getArchiveNumber());
				articleArchive.setArchiveNumber(articleArchive.getArchiveNumber()+1);
				result=this.update(articleArchive);
				
			}else{
				ArticleArchive articleArchive = new ArticleArchive();
				articleArchive.setUserId(article.getAuthorId());
				articleArchive.setArchiveType( Const.ARCHIVE_OF_CATE_TYPE);
				articleArchive.setArchiveTime(new Date());
				articleArchive.setArchiveNumber(1);
				articleArchive.setArchiveCate(article.getCateId());
				result= this.insertSelective(articleArchive);
			}
			if(result<1){
				throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE,"未更新");
			}
			PageDomain<ArticleArchive>  pdOldCate = new PageDomain<ArticleArchive>();
			pdOldCate.equals("user_id", article.getAuthorId()).and()
			.equals("archive_type", Const.ARCHIVE_OF_CATE_TYPE).and()
			.equals("archive_cate",oldCateId).rowLimit(true);
			articleArchiveList = this.findDataByCondition(pdOldCate);
			result = 0;
			if(null != articleArchiveList && articleArchiveList.size() > 0){
				ArticleArchive articleArchive = articleArchiveList.get(0);
				articleArchive.setOldArchiveNumber(articleArchive.getArchiveNumber());
				articleArchive.setArchiveNumber(articleArchive.getArchiveNumber()-1);
				if(articleArchive.getArchiveNumber()>0){
					result=this.update(articleArchive);
				}else{
					result=this.delete(articleArchive.getId());
				}
				if(result<1){
					throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE,"未更新");
				}
			}
			return 1;
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
}
