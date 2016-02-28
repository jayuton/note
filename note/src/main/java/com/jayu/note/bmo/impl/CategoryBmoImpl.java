package com.jayu.note.bmo.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.bmo.CategoryBmo;
import com.jayu.note.dao.model.Category;
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
import com.jayu.note.bmo.ArticleBmo;
import com.jayu.note.dao.CategoryDao;
import com.jayu.note.dao.model.Article;
import com.jayu.common.util.StringUtil;

/**
 * 摘要：文章类目 业务实现.
 *  
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:35:02
 */
@Service("CategoryBmoImpl")
public class CategoryBmoImpl extends BaseBMOImpl<Category, Integer> implements
		CategoryBmo {
	protected static Log log = Log.getLog(CategoryBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "CategoryDao")
	private CategoryDao categoryDao;
	@Resource(name="ArticleBmoImpl")
	ArticleBmo articleBmo;
	
	@Override
	public Integer insert(Category category) throws BusinessException {
		try {
			Assert.notNull(category, "Category不能为NULL!");
			return this.categoryDao.insert(category);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(Category category) throws BusinessException {
		try {
			Assert.notNull(category, "Category不能为NULL!");
			return this.categoryDao.insertSelective(category);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(Category category) throws BusinessException {
		try {
			Assert.notNull(category, "Category不能为NULL!");
			return this.categoryDao.update(category);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(Integer... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			this.categoryDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("批量插入文章类目失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer delete(Integer id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.categoryDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Category get(Integer id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.categoryDao.get(id);
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
	public PageModel<Category> query(PageDomain<Category> pd)
			throws BusinessException {
		Assert.notNull(pd, "PageDomain<Category>不能为NULL!");
		PageModel<Category> pm = new PageModel<Category>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.categoryDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<Category> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.categoryDao.findPageDataByWhereAndOrderBy(
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
	 * @return PageModel<Category> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<Category> query(int pageNo) throws BusinessException {
		PageDomain<Category> pd = new PageDomain<Category>();
		pd.pageNo(pageNo).orderBy(COLUMN_SORT_ORDER_ASC);
		return query(pd);
	}

	/**
	 * 根据条件查询
	 * @param pd PageDomain<Category>
	 * @return List<Category> 文章类目列表
	 * @throws BusinessException
	 */
	public List<Category> findDataByCondition(PageDomain<Category> pd)
			throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.categoryDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Category getDefaultCategory(long userId ) throws BusinessException{
		try {
			PageDomain<Category> pd = new PageDomain<Category>();
			pd.equals("user_id", userId).and()
			.equals("is_default",1).rowLimit(true);
			List<Category> categoryList =this.findDataByCondition(pd);
			if(null != categoryList && categoryList.size() > 0){
				return categoryList.get(0);
			}
			return null;
		}catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	@Override
	public Category getCategoryById(long userId , int id) throws BusinessException{
		try {
			PageDomain<Category> pd = new PageDomain<Category>();
			pd.equals("user_id", userId).and()
			.equals("id",id).rowLimit(true);
			List<Category> categoryList =this.findDataByCondition(pd);
			if(null != categoryList && categoryList.size() > 0){
				return categoryList.get(0);
			}
			return null;
		}catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	public List<Category> getChildrenByParentId(long userId , int parentId) throws BusinessException{
		try {
			return this.categoryDao.getChildrenByParentId(userId , parentId);
		}catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	public List<Category> getAllChildrenByParentId(int parentId) throws BusinessException{
		try {
			return this.categoryDao.getAllChildrenByParentId(parentId);
		}catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	public List<Category> getAllCategoryList(long userId) throws BusinessException{
		try {
			List<Category> rootCategoryList =this.getChildrenByParentId(userId,0);
			if(null != rootCategoryList && rootCategoryList.size() > 0){
				for(Category rootCategory:rootCategoryList){
					rootCategory.setChildrenList(this.getAllChildrenByParentId(rootCategory.getId()));
				}
			}
			return rootCategoryList;
		}catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	public Integer update(Category category, int minSortOrder,boolean isAdd,boolean isLess) throws BusinessException {
		try {
			Assert.notNull(category, "Category不能为NULL!");
			if(minSortOrder>0){
				 this.categoryDao.updateAllSortOrder(category.getParentId(),minSortOrder,isAdd,isLess);
			}
			 return this.categoryDao.update(category);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer save(Category category) throws BusinessException {
		try {
			Assert.notNull(category, "Category不能为NULL!");
			category.setIdPath(idPath(getParents(category.getParentId())));
			return this.categoryDao.insertSelective(category);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	public String idPath(List<Category> categoryList){
		List<Integer> cateIdList = new ArrayList<Integer>();
		if(null != categoryList && categoryList.size() > 0){
			for(Category child : categoryList){
				cateIdList.add(child.getId());
			}
		}
		if(cateIdList.size()==0){
			return null;
		}
		return StringUtil.join(cateIdList, ",");
	}
	@Override
	public List<Category> getParents(Integer parentId) throws BusinessException {
		try {
			Assert.notNull(parentId, "parentId 不能为NULL!");
			List<Category> categoryList = new ArrayList<Category>();
			if(parentId.intValue() > 0){
				Category category = this.categoryDao.get(parentId);
				if(null != category){
					categoryList.add(category);
					if(category.getId() > 0){
						List<Category> parentCategoryList = getParents(category.getParentId());
						if(null != parentCategoryList && parentCategoryList.size() > 0){
							categoryList.addAll(parentCategoryList);
						}
					}
				}
			}
			return categoryList;
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	/**
	 * 删除所有子节点和其对应的文章数据
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Tx
	public Integer[] deleteCategory(Category category) throws BusinessException {
		try {
			Assert.notNull(category, "category 不能为NULL!");
			List<Category> categoryList = this.categoryDao.getAllChildrenByParentId(category.getId());
			List<Integer> cateIdList = new ArrayList<Integer>();
			cateIdList.add(category.getId());
			if(null != categoryList && categoryList.size() > 0){
				for(Category child : categoryList){
					cateIdList.add(child.getId());
				}
			}
			this.deleteBatch(cateIdList.toArray(new Integer[cateIdList.size()]));
			PageDomain<Article> pd = new PageDomain<Article>();
			pd.equals("author_id", category.getUserId()).and()
			.in("cate_id", cateIdList);
			 List<Article> articleList=this.articleBmo.findDataByCondition(pd);
			 if(null !=articleList && articleList.size() > 0){
				 for(Article article:articleList){
					 this.articleBmo.deleteArticle(article);
				 }
			 }
			return cateIdList.toArray(new Integer[cateIdList.size()]);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

}
