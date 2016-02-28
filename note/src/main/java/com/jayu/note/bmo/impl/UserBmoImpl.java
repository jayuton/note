package com.jayu.note.bmo.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.bmo.UserBmo;
import com.jayu.note.dao.*;
import com.jayu.note.dao.model.Category;
import com.jayu.note.dao.model.User;
import com.jayu.note.domain.Action;
import com.jayu.note.domain.ActionType;
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

/**
 * 摘要：用户 业务实现.
 *  
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:25:35
 */
@Service("UserBmoImpl")
public class UserBmoImpl extends BaseBMOImpl<User, Long> implements UserBmo {
	protected static Log log = Log.getLog(UserBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "UserDao")
	private UserDao userDao;
	/** 注入数据库访问接口 */
	@Resource(name = "CategoryDao")
	private CategoryDao categoryDao;

	@Resource(name = "ArticleDao")
	private ArticleDao articleDao;
	@Resource(name = "ArticleContentDao")
	private ArticleContentDao articleContentDao;
	@Resource(name = "ArticleArchiveDao")
	private ArticleArchiveDao articleArchiveDao;
	@Resource(name = "ArticeAtUserDao")
	private ArticeAtUserDao articeAtUserDao;
	
	@Override
	public Integer insert(User user) throws BusinessException {
		try {
			Assert.notNull(user, "User不能为NULL!");
			return this.userDao.insert(user);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(User user) throws BusinessException {
		try {
			Assert.notNull(user, "User不能为NULL!");
			return this.userDao.insertSelective(user);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(User user) throws BusinessException {
		try {
			Assert.notNull(user, "User不能为NULL!");
			return this.userDao.update(user);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(Long... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			this.userDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("批量插入用户失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer delete(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.userDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public User get(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.userDao.get(id);
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
	public PageModel<User> query(PageDomain<User> pd) throws BusinessException {
		Assert.notNull(pd, "PageDomain<User>不能为NULL!");
		PageModel<User> pm = new PageModel<User>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.userDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<User> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.userDao.findPageDataByWhereAndOrderBy(
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
	 * @return PageModel<User> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<User> query(int pageNo) throws BusinessException {
		PageDomain<User> pd = new PageDomain<User>();
		pd.pageNo(pageNo).orderBy(COLUMN_SORT_ORDER_ASC);
		return query(pd);
	}

	/**
	 * 根据条件查询
	 * @param pd PageDomain<User>
	 * @return List<User> 用户列表
	 * @throws BusinessException
	 */
	public List<User> findDataByCondition(PageDomain<User> pd)
			throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.userDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Override
	@Tx
	public Integer register(User user) throws BusinessException {
		try {
			Assert.notNull(user, "User不能为NULL!");
			this.userDao.insertSelective(user);
			Category entity = new Category();
			entity.setCreateDt(new Date());
			entity.setLevel(1);
			entity.setName("我的日记");
			entity.setParentId(0);
			entity.setStatus(1);
			entity.setIsDefault(1);
			entity.setUserId(user.getUserId());
			return this.categoryDao.insertSelective(entity);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Tx
	@Action(actionType= ActionType.DEL_USER,bizIdExl="arg0.userId",userIdExl="arg0.userId")
	public Integer delUser(User user) throws BusinessException {
		try {
			Assert.notNull(user, "User不能为NULL!");
			this.userDao.delete(user.getUserId());
			 this.categoryDao.deleteByUserId(user.getUserId());
			this.articleDao.deleteByAuthorId(user.getUserId());
			this.articleArchiveDao.deleteByUserId(user.getUserId());
			return this.articleContentDao.deleteByAuthorId(user.getUserId());
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
}
