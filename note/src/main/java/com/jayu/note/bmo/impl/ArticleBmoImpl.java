package com.jayu.note.bmo.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.dao.ArticleArchiveDao;
import com.jayu.note.dao.UserDao;
import com.jayu.note.dao.model.ArticleArchive;
import com.jayu.note.dao.model.User;
import com.jayu.note.domain.EmailType;
import org.apache.commons.lang.StringUtils;
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
import com.jayu.note.dto.AtUserDto;
import com.jayu.note.dao.ArticeAtUserDao;
import com.jayu.note.dao.ArticleContentDao;
import com.jayu.note.dao.ArticleDao;
import com.jayu.note.dao.EmailSendDao;
import com.jayu.note.dao.model.ArticeAtUser;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.ArticleContent;
import com.jayu.note.dao.model.EmailSend;
import com.jayu.note.domain.Action;
import com.jayu.note.domain.ActionType;
import com.jayu.note.domain.SysConstant;
import com.jayu.common.util.UIDGenerator;

/**
 * 摘要：文章 业务实现.
 *  
 * <P>
 * @author jayu
 * @version V1.0 2015-11-22
 * @createDate 2015-11-22 20:38:53
 */
@Service("ArticleBmoImpl")
public class ArticleBmoImpl extends BaseBMOImpl<Article, Long> implements
		ArticleBmo {
	protected static Log log = Log.getLog(ArticleBmoImpl.class);
	/** 注入数据库访问接口 */
	@Resource(name = "ArticleDao")
	private ArticleDao articleDao;
	@Resource(name = "ArticleContentDao")
	private ArticleContentDao articleContentDao;
	@Resource(name = "ArticleArchiveDao")
	private ArticleArchiveDao articleArchiveDao;
	@Resource(name = "ArticeAtUserDao")
	private ArticeAtUserDao articeAtUserDao;
	@Resource(name = "UserDao")
	private UserDao userDao;
	@Resource(name = "EmailSendDao")
	private EmailSendDao emailSendDao;
	
	@Override
	public Integer insert(Article article) throws BusinessException {
		try {
			Assert.notNull(article, "Article不能为NULL!");
			return this.articleDao.insert(article);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer insertSelective(Article article) throws BusinessException {
		try {
			Assert.notNull(article, "Article不能为NULL!");
			return this.articleDao.insertSelective(article);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public Integer update(Article article) throws BusinessException {
		try {
			Assert.notNull(article, "Article不能为NULL!");
			return this.articleDao.update(article);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	public void deleteBatch(Long... ids) throws BusinessException {
		try {
			Assert.notNull(ids, "ids 不能为NULL!");
			for(long id:ids){
				this.deleteArticle(this.get(id));
			}
		} catch (Exception e) {
			logger.error("批量插入文章失败", e);
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}

	@Override
	@Tx
	public Integer delete(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			 this.articleDao.delete(id);
			 return this.articleContentDao.delete(id);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	@Override
	@Action(actionType=ActionType.DEL_ARTICLE,bizIdExl="arg0.id",userIdExl="arg0.authorId",
	attributeExl={"arg0.cateId","arg0.writeYear","arg0.writeMonth","arg0.writeDay"},attributeKey={"cateId","writeYear","writeMonth","writeDay"})
	public Integer deleteArticle(Article article) throws BusinessException {
		try {
			Assert.notNull(article, "article 不能为NULL!");
			log.debug("########### deleteArticle @Action={}", article);
			 return this.delete(article.getId());
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	@Override
	public Article get(Long id) throws BusinessException {
		try {
			Assert.notNull(id, "id 不能为NULL!");
			return this.articleDao.get(id);
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
	public PageModel<Article> query(PageDomain<Article> pd)
			throws BusinessException {
		Assert.notNull(pd, "PageDomain<Article>不能为NULL!");
		PageModel<Article> pm = new PageModel<Article>();
		pm.setPageNo(pd.getPageNo());
		pm.setPageSize(pd.getPageSize());
		try {
			int totalRecords = this.articleDao.getCount(pd);
			pm.setTotalRecords(totalRecords);
			List<Article> list = null;
			if (totalRecords > 0 && pm.hasCurrentPage()) {
				list = this.articleDao.findPageDataByWhereAndOrderBy(
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
	 * @return PageModel<Article> 分页数据对象
	 * @throws BusinessException
	 *             业务层自定义异常
	 */
	public PageModel<Article> query(int pageNo) throws BusinessException {
		PageDomain<Article> pd = new PageDomain<Article>();
		pd.pageNo(pageNo).orderBy(COLUMN_SORT_ORDER_ASC);
		return query(pd);
	}

	/**
	 * 根据条件查询
	 * @param pd PageDomain<Article>
	 * @return List<Article> 文章列表
	 * @throws BusinessException
	 */
	public List<Article> findDataByCondition(PageDomain<Article> pd)
			throws BusinessException {
		try {
			Assert.notNull(pd, "pd 不能为NULL!");
			return this.articleDao.findDataByCondition(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	

	@Override
	@Tx
	@Action(actionType=ActionType.ADD_ARTICLE,bizIdExl="arg0.id",userIdExl="arg0.authorId",
	attributeExl={"arg0.cateId","arg0.authorId"},attributeKey={"cateId","authorId"})
	public Integer save(Article article) throws BusinessException {
		try {
			Assert.notNull(article, "Article不能为NULL!");
			if(null == article.getContent()){
				article.setContent("");
			}
			 this.articleDao.insertSelective(article);
			ArticleContent articleContent = new ArticleContent();
			articleContent.setContent(article.getContent());
			articleContent.setId(article.getId());
			articleContent.setAuthorId(article.getAuthorId());
			Integer result = this.articleContentDao.insert(articleContent);
			if(null !=article.getArticeAtUserList()){
				int type=SysConstant.AT_FRIEND;
				for(ArticeAtUser atUser:article.getArticeAtUserList()){
					if(atUser.getBizId().longValue()==SysConstant.ALL_GUEST){
						type=Math.max(type,SysConstant.AT_ALL_GUEST);
					}else if(atUser.getBizId().longValue()==SysConstant.ALL_MEMBER){
						type=Math.max(type,SysConstant.AT_ALL_MEMBER);
					}
				}
				for(ArticeAtUser atUser:article.getArticeAtUserList()){
					ArticeAtUser articeAtUser = new ArticeAtUser();
					articeAtUser.setArticleId(article.getId());
					articeAtUser.setBizId(atUser.getBizId());
					if(atUser.getBizId().longValue()==SysConstant.ALL_MEMBER || type==SysConstant.AT_ALL_MEMBER ){
						articeAtUser.setType(SysConstant.AT_ALL_MEMBER);
						articeAtUser.setAccount("all");
					}else if(atUser.getBizId().longValue()==SysConstant.ALL_GUEST || type==SysConstant.AT_ALL_GUEST ){
						articeAtUser.setType(SysConstant.AT_ALL_GUEST);
						articeAtUser.setAccount("guest");
					}else{
						articeAtUser.setType(SysConstant.AT_FRIEND);
						User user = userDao.get(atUser.getBizId());
						articeAtUser.setType(SysConstant.AT_FRIEND);
						if(null != user){
							articeAtUser.setAccount(user.getAccount());
						}
					}
					articeAtUserDao.insert(articeAtUser);
					if(articeAtUser.getType().intValue()==SysConstant.AT_ALL_MEMBER || articeAtUser.getType().intValue()==SysConstant.AT_ALL_GUEST){
						PageDomain<User> pdUser= new PageDomain<User>();
						pdUser.notEquals("account_type", SysConstant.ACCOUNT_TYPE_STAFF).and()
						.notEquals("user_id", article.getAuthorId())
						.rowLimit(true,1000);
						List<User> userList = userDao.findDataByCondition(pdUser);
						if(null !=userList && userList.size() > 0 ){
							for(User user:userList){
								//新增加，额外发送邮件通知
								if(StringUtils.isNotBlank(user.getEmail())){
									EmailSend emailSend = new EmailSend();
									emailSend.setId(UIDGenerator.generateNonceYYmd(14));
									emailSend.setEmailPost(user.getEmail());
									emailSend.setBizId(String.valueOf(article.getId()));
									emailSend.setStatus(1);
									emailSend.setEmailTpl(EmailType.NEW_ARTICLE_AT_USER);
									emailSendDao.insert(emailSend);
								}
							}
						}
						//结束
						break;
					}else if(articeAtUser.getType().intValue()==SysConstant.AT_FRIEND){
						//新增加，额外发送邮件通知
						if(StringUtils.isNotBlank(articeAtUser.getAccount())){
							EmailSend emailSend = new EmailSend();
							emailSend.setId(UIDGenerator.generateNonceYYmd(14));
							emailSend.setEmailPost(articeAtUser.getAccount());
							emailSend.setBizId(String.valueOf(article.getId()));
							emailSend.setStatus(1);
							emailSend.setEmailTpl(EmailType.NEW_ARTICLE_AT_USER);
							emailSendDao.insert(emailSend);
						}
					}
				}
			}
			return result;
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	@Override
	@Action(actionType=ActionType.EDIT_ARTICLE,bizIdExl="arg0.id",userIdExl="arg0.authorId",
	attributeExl={"arg1"},attributeKey={"oldCateId"})
	@Tx
	public Integer edit(Article article,int oldCateId) throws BusinessException {
		try {
			Assert.notNull(article, "Article不能为NULL!");
			Assert.notNull(article.getContent(), "Article content不能为NULL!");
			Assert.notNull(article.getId(), "Article id 不能为NULL!");
			Assert.notNull(article.getAuthorId(), "Article authorId 不能为NULL!");
			Assert.isTrue(article.getId()>0, "Article id>0!");
			 this.articleDao.update(article);
			ArticleContent articleContent = new ArticleContent();
			articleContent.setContent(article.getContent());
			articleContent.setId(article.getId());
			Integer result = this.articleContentDao.update(articleContent);
			PageDomain<ArticeAtUser> atPd = new PageDomain<ArticeAtUser>();
			atPd.equals("article_id", article.getId());
			List<ArticeAtUser> dbArticeAtUserList = articeAtUserDao.findDataByCondition(atPd);
			if(null !=dbArticeAtUserList && dbArticeAtUserList.size() > 0){
				articeAtUserDao.delete(article.getId());
			}
			if(null !=article.getArticeAtUserList() && article.getArticeAtUserList().size() > 0){
				int type=SysConstant.AT_FRIEND;
				for(ArticeAtUser atUser:article.getArticeAtUserList()){
					if(atUser.getBizId().longValue()==SysConstant.ALL_GUEST){
						type=Math.max(type,SysConstant.AT_ALL_GUEST);
					}else if(atUser.getBizId().longValue()==SysConstant.ALL_MEMBER){
						type=Math.max(type,SysConstant.AT_ALL_MEMBER);
					}
				}
				for(ArticeAtUser atUser:article.getArticeAtUserList()){
					ArticeAtUser articeAtUser = new ArticeAtUser();
					articeAtUser.setArticleId(article.getId());
					articeAtUser.setBizId(atUser.getBizId());
					if(atUser.getBizId().longValue()==SysConstant.ALL_GUEST || type== SysConstant.AT_ALL_GUEST){
						articeAtUser.setType(SysConstant.AT_ALL_GUEST);
						articeAtUser.setAccount("guest");
					}else if(atUser.getBizId().longValue()==SysConstant.ALL_MEMBER || type== SysConstant.AT_ALL_MEMBER){
						articeAtUser.setType(SysConstant.AT_ALL_MEMBER);
						articeAtUser.setAccount("all");
					}else{
						User user = userDao.get(atUser.getBizId());
						articeAtUser.setType(SysConstant.AT_FRIEND);
						if(null != user){
							articeAtUser.setAccount(user.getAccount());
						}
					}
					
					articeAtUserDao.insert(articeAtUser);
					if(articeAtUser.getType().intValue()==SysConstant.AT_ALL_MEMBER || articeAtUser.getType().intValue()==SysConstant.AT_ALL_GUEST){
						PageDomain<User> pdUser= new PageDomain<User>();
						pdUser.notEquals("account_type", SysConstant.ACCOUNT_TYPE_STAFF).and()
						.notEquals("user_id", article.getAuthorId())
						.rowLimit(true,1000);
						List<User> userList = userDao.findDataByCondition(pdUser);
						if(null !=userList && userList.size() > 0 ){
							for(User user:userList){
								//新增加，额外发送邮件通知
								if(StringUtils.isNotBlank(user.getEmail()) && !AtUserDto.isContains(articeAtUser, dbArticeAtUserList)){
									EmailSend emailSend = new EmailSend();
									emailSend.setId(UIDGenerator.generateNonceYYmd(14));
									emailSend.setEmailPost(user.getEmail());
									emailSend.setBizId(String.valueOf(article.getId()));
									emailSend.setStatus(1);
									emailSend.setEmailTpl(EmailType.NEW_ARTICLE_AT_USER);
									emailSendDao.insert(emailSend);
								}
							}
						}
						//结束
						break;
					}else if(articeAtUser.getType().intValue()==SysConstant.AT_FRIEND){
						//新增加，额外发送邮件通知
						if(StringUtils.isNotBlank(articeAtUser.getAccount()) && !AtUserDto.isContains(articeAtUser, dbArticeAtUserList)){
							EmailSend emailSend = new EmailSend();
							emailSend.setId(UIDGenerator.generateNonceYYmd(14));
							emailSend.setEmailPost(articeAtUser.getAccount());
							emailSend.setBizId(String.valueOf(article.getId()));
							emailSend.setStatus(1);
							emailSend.setEmailTpl(EmailType.NEW_ARTICLE_AT_USER);
							emailSendDao.insert(emailSend);
						}
					}
				}
			}
			return result;
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	/**
	 * 获取作者的文章
	 * @param articleId
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Article get(long articleId,long userId)
			throws BusinessException {
		try {
			Assert.notNull(articleId>0, "articleId <=0 !");
			Assert.notNull(userId>0, "userId <=0!");
			PageDomain<Article> pd = new PageDomain<Article>();
			pd.equals("id", articleId).and()
			.equals("author_id", userId).rowLimit(true);
			List<Article> articleList = this.articleDao.findDataByCondition(pd);
			if(null != articleList && articleList.size() >0){
				Article article = articleList.get(0);
				ArticleContent articleContent= this.articleContentDao.get(article.getId());
				if(null != articleContent){
					article.setContent(articleContent.getContent());
				}
				return article;
			}
			return null;
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	public List<ArticleArchive> getCountGroupByUser(PageDomain<Article> pd)  throws BusinessException{
		try {
			Assert.notNull(pd, "PageDomain不能为NULL!");
			return this.articleDao.getCountGroupByUser(pd);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
	@Tx
	public Integer deleteByUserId(Long authorId) throws BusinessException {
		try {
			Assert.notNull(authorId, "authorId 不能为NULL!");
			this.articleDao.deleteByAuthorId(authorId);
			 this.articleArchiveDao.deleteByUserId(authorId);
			 return this.articleContentDao.deleteByAuthorId(authorId);
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
		}
	}
	
 
}
