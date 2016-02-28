package com.jayu.note.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jayu.note.MailService;
import com.jayu.note.bmo.ArticleArchiveBmo;
import com.jayu.note.bmo.ArticleBmo;
import com.jayu.note.bmo.MquartzBmo;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.Mquartz;
import com.jayu.note.domain.ActionType;
import com.jayu.exception.BusinessException;
import com.jayu.exception.ResultConstant;
import com.jayu.log.Log;
import com.jayu.mybatis.entity.PageDomain;

@Lazy(false)
@Component(value="UserActionJob")
public class UserActionJob {
	final static Log log=Log.getLog(UserActionJob.class);
	@Resource(name="ArticleBmoImpl")
	ArticleBmo articleBmo;
	@Resource(name="ArticleArchiveBmoImpl")
	ArticleArchiveBmo articleArchiveBmo;
	
	@Resource(name="MquartzBmoImpl")
	private MquartzBmo mquartzBmo;
	@Resource(name="mailService")
	private MailService mailService;
	
	@Scheduled(cron="0/10 * * * * *")
	public void execute(){
		List<Mquartz> mquartzList = null;
		try {
			PageDomain<Mquartz>  pdMquartz = new PageDomain<Mquartz>();
			pdMquartz.equals("action_status",0).and()
			.lessEquals("action_count", 5).and()
			.whereSql("action_starttime < NOW()")
			.rowLimit(true,100);
			mquartzList = mquartzBmo.findDataByCondition(pdMquartz);	
		} catch (BusinessException e) {
			log.error(e);
		}
		
		if(null != mquartzList && mquartzList.size() > 0){
			for(Mquartz  mquartz:mquartzList){
				boolean isAdd = ActionType.ADD_ARTICLE.name().equals(mquartz.getActionType());
				boolean isDel = ActionType.DEL_ARTICLE.name().equals(mquartz.getActionType());
				boolean isEdit = ActionType.EDIT_ARTICLE.name().equals(mquartz.getActionType()); 
				//添加或删除
				if(isAdd || isDel){
					try{
						ADD_DEL_ARTICLE(mquartz,isAdd);
					} catch (BusinessException e) {
						log.error(e);
						updateMquartz(mquartz);
					}
				}else if(isEdit){
					try{
						EDIT_ARTICLE(mquartz);
					} catch (BusinessException e) {
						log.error(e);
						updateMquartz(mquartz);
					}
				}
			}
		}
		
	}
	
	/**
	 * 添加和删除文章 归档数据更新
	 * @param mquartz
	 * @param isAddOrDel true:add,false:del
	 */
	public void ADD_DEL_ARTICLE(Mquartz mquartz,boolean isAddOrDel) throws BusinessException {
		try {
			Article article = null;
			if(isAddOrDel){
				article = articleBmo.get(Long.valueOf(mquartz.getBizId()));
				if(null ==article){
					mquartzBmo.deleteByBizIdType(mquartz.getBizId(), mquartz.getActionType());
					return;
				}
			}else{
				article = new Article();
				int isDel = 0;
				article.setAuthorId(mquartz.getUserId()); 
				String cateId= mquartz.getActionAttributeByKey("cateId");
				String writeYear= mquartz.getActionAttributeByKey("writeYear");
				String writeMonth= mquartz.getActionAttributeByKey("writeMonth");
				String writeDay= mquartz.getActionAttributeByKey("writeDay");
				if(StringUtils.isNotBlank(cateId) && NumberUtils.isNumber(cateId)){
					article.setCateId(Integer.parseInt(cateId));
					isDel++;
				}
			
				if(StringUtils.isNotBlank(writeYear) && NumberUtils.isNumber(writeYear)){
					article.setWriteYear(Integer.parseInt(writeYear));
					isDel++;
				}
				if(StringUtils.isNotBlank(writeMonth) && NumberUtils.isNumber(writeMonth)){
					article.setWriteMonth(Integer.parseInt(writeMonth));
					isDel++;
				}
				if(StringUtils.isNotBlank(writeDay) && NumberUtils.isNumber(writeDay)){
					article.setWriteDay(Integer.parseInt(writeDay));isDel++;
					isDel++;
				}
				if(isDel<4){
					mquartzBmo.deleteByBizIdType(mquartz.getBizId(), mquartz.getActionType());
					return;
				}
			}

			Integer resutl = articleArchiveBmo.archiveYMC(article, isAddOrDel);
			if(null !=resutl && resutl.intValue()>0){
				mquartzBmo.deleteByBizIdType(mquartz.getBizId(), mquartz.getActionType());
			}
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
			
		}
	}
	
	
	/**
	 * 编辑文章主要是涉及类目有没有变更
	 * @param mquartz
	 */
	public void EDIT_ARTICLE(Mquartz mquartz) throws BusinessException {
		try {
			Article article = articleBmo.get(Long.valueOf(mquartz.getBizId()));
			if(null == article ){
				mquartzBmo.deleteByBizIdType(mquartz.getBizId(), mquartz.getActionType());
				return;
			}
			String oldCateIdStr= mquartz.getActionAttributeByKey("oldCateId");
			int oldCateId=0;
			if(StringUtils.isNotBlank(oldCateIdStr) && NumberUtils.isNumber(oldCateIdStr)){
				oldCateId=Integer.parseInt(oldCateIdStr);
				if(oldCateId==article.getCateId()){
					mquartzBmo.deleteByBizIdType(mquartz.getBizId(), mquartz.getActionType());
					return;
				}else{
					Integer resutl = articleArchiveBmo.archiveEditC(article, oldCateId);
					if(null !=resutl && resutl.intValue()>0){
						mquartzBmo.deleteByBizIdType(mquartz.getBizId(), mquartz.getActionType());
					}
				}
			}
		} catch (Exception e) {
			throw new BusinessException(ResultConstant.DAO_RESULT_FAILTURE, e);
			
		}
	}
	
	private  void updateMquartz(Mquartz mquartz){
		if(mquartz.getActionCount()>=5){
			mquartz.setActionStatus(2);
		}else{
			mquartz.setActionCount(mquartz.getActionCount()+1);
		}
		try {
			mquartzBmo.update(mquartz);
		} catch (BusinessException e1) {
			log.error(e1);
		}
	}
}
