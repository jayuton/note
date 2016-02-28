package com.jayu.note.job;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.jayu.note.common.Const;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jayu.note.bmo.ArticleArchiveBmo;
import com.jayu.note.bmo.ArticleBmo;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.ArticleArchive;
import com.jayu.common.util.DateUtil;
import com.jayu.exception.BusinessException;
import com.jayu.log.Log;
import com.jayu.mybatis.entity.PageDomain;

/**
 * 文章索引操作定时JOB . <BR>
 * 文章归档<BR>
 * 定时创建文章索引，更新，删除等.<BR>
 * <P>
 * 
 * @author jayu
 * @version V1.0 2015-12-12 
 * @createDate 2015-12-12 下午9:30:47
 * @modifyDate jayu 2015-12-12 <BR>
 */
@Lazy(false)
@Component(value="ArticleJob")
public class ArticleJob {
	final static Log log=Log.getLog(ArticleJob.class);
	
	@Resource(name="ArticleBmoImpl")
	ArticleBmo articleBmo;
	@Resource(name="ArticleArchiveBmoImpl")
	ArticleArchiveBmo articleArchiveBmo;
	
	/**
	 * 按日期归档
	 * 每分钟更新一次
	 */
	@Scheduled(cron="0/10 * * * * *")
	public void archiveDay(){//些定时放在队列里，等上一个执行完，才能进行一个执行
		PageDomain<Article>  pd = new PageDomain<Article>();
		int year = DateUtil.nowYear();
		int month = DateUtil.nowMonth();
		int day = DateUtil.nowDayOfMonth();
		long ymd = Long.valueOf(DateUtil.getNow(DateUtil.DATE_FORMATE_STRING_H));
		Date now = new Date();
		pd.equals("write_year", year).and().equals("write_month", month).and().equals("write_day",day);	
		try {
			List<ArticleArchive> aticleArchiveDayList= articleBmo.getCountGroupByUser(pd);
			if(null != aticleArchiveDayList && aticleArchiveDayList.size() > 0){
				PageDomain<ArticleArchive>  pdArchive = new PageDomain<ArticleArchive>();
				PageDomain<ArticleArchive>  pdCurrentMonthArchive = new PageDomain<ArticleArchive>();
				for(ArticleArchive  articleArchiveDay:aticleArchiveDayList){
					articleArchiveDay.setArchiveType(Const.ARCHIVE_OF_DAY_TYPE);
					articleArchiveDay.setArchiveYear(year);
					articleArchiveDay.setArchiveMonth(month);
					articleArchiveDay.setArchiveDay(day);
					articleArchiveDay.setArchiveYmd(ymd);
					articleArchiveDay.setArchiveTime(now);
					
					//查询当月所有的归档数据
					pdArchive.equals("user_id", articleArchiveDay.getUserId())
					.equals("archive_year", year).and()
					.equals("archive_month", month).and()
					.equals("archive_type", Const.ARCHIVE_OF_DAY_TYPE);	
					 List<ArticleArchive>  articleArchiveDayOfSrcList=articleArchiveBmo.findDataByCondition(pdArchive);
					 int monthTotal = 0 ;
					 for(ArticleArchive  articleArchiveDaySrc:articleArchiveDayOfSrcList){
						 if(articleArchiveDaySrc.getArchiveDay().intValue()==day){
							 articleArchiveDay.setId(articleArchiveDaySrc.getId());
							 monthTotal +=articleArchiveDay.getArchiveNumber();
						 }else{
							 monthTotal +=articleArchiveDaySrc.getArchiveNumber();
						 }
					 }
					 
					if(null != articleArchiveDay.getId() && articleArchiveDay.getId()>0){	
							articleArchiveBmo.update(articleArchiveDay);
					}else{
						articleArchiveBmo.insertSelective(articleArchiveDay);
					}
					//更新当月归档数据
					pdCurrentMonthArchive.equals("user_id", articleArchiveDay.getUserId())
					.equals("archive_year", year).and()
					.equals("archive_month", month).and()
					.equals("archive_type", Const.ARCHIVE_OF_MONTH_TYPE).rowLimit(true);
					 List<ArticleArchive>  articleArchiveMonthList=articleArchiveBmo.findDataByCondition(pdCurrentMonthArchive);
					 if(null != articleArchiveMonthList && articleArchiveMonthList.size()>0){
						 	articleArchiveMonthList.get(0).setArchiveNumber(monthTotal);
							articleArchiveBmo.update(articleArchiveMonthList.get(0));
						}else{
							articleArchiveDay.setId(null);
							articleArchiveDay.setArchiveYmd(ymd-day);
							articleArchiveDay.setArchiveDay(null);
							articleArchiveDay.setArchiveNumber(monthTotal);
							articleArchiveDay.setArchiveType(Const.ARCHIVE_OF_MONTH_TYPE);
							articleArchiveBmo.insertSelective(articleArchiveDay);
						}
					 
				}	
			}
		} catch (BusinessException e) {
			log.error("文章按天归档异常", e);
		}
	}

	/**
	 * 按月期归档
	 * 每月1号0点1分钟归档上一个月数据,保证是正确的数据
	 */
	@Scheduled(cron="0 1 0 1 * *")
	public void archiveMonth(){//些定时放在队列里，等上一个执行完，才能进行一个执行
		log.debug(" archiveMonth by cron");
		
	}
}
