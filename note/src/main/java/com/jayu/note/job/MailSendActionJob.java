package com.jayu.note.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.jayu.note.common.Const;
import com.jayu.note.domain.EmailType;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jayu.note.MailService;
import com.jayu.note.bmo.ArticleBmo;
import com.jayu.note.bmo.EmailSendBmo;
import com.jayu.note.dao.model.Article;
import com.jayu.note.dao.model.EmailSend;
import com.jayu.exception.BusinessException;
import com.jayu.log.Log;
import com.jayu.mybatis.entity.PageDomain;

@Lazy(false)
@Component(value="MailSendActionJob")
public class MailSendActionJob {
	final static Log log=Log.getLog(MailSendActionJob.class);
	@Resource(name="ArticleBmoImpl")
	ArticleBmo articleBmo;
	@Resource(name="EmailSendBmoImpl")
	private EmailSendBmo emailSendBmo;
	@Resource(name="mailService")
	private MailService mailService;
	
	@Scheduled(cron="0 0/1 10-15 * * *")
	public void execute(){
		//mailService.sendAsncSimpleMail("263616579@qq.com", "测试", "笔记网站！");
		List<EmailSend> emailSendList =queryNotSendEmail();
		doNewArticleAtUserEmail(emailSendList);
	}
	
	public List<EmailSend> queryNotSendEmail(){
		PageDomain<EmailSend>  pdEmailSend = new PageDomain<EmailSend>();
		pdEmailSend.equals("status",1)
		.rowLimit(true,100);
		try {
			List<EmailSend> emailSendList = emailSendBmo.findDataByCondition(pdEmailSend);
			return emailSendList;
		} catch (BusinessException e) {
			log.error("queryNotSendEmail", e);
		}
		return null;
	}
	
	public boolean updateStatus(String id,int status){
		EmailSend emailSend = new EmailSend();
		emailSend.setId(id);
		emailSend.setStatus(status);
		try {
			emailSendBmo.update(emailSend);
			return true;
		} catch (BusinessException e) {
			log.error(e);
		}
		return false;
	}
	public boolean delEmailSend(String id){
		try {
			emailSendBmo.delete(id);
			return true;
		} catch (BusinessException e) {
			log.error(e);
		}
		return false;
	}
	
	public void doNewArticleAtUserEmail(List<EmailSend> emailSendList){
		if(null !=emailSendList){
			Map<String,Article> cacheMap = new HashMap<String,Article> ();
			Set<String> emailUserList = new HashSet<String>();
			for(EmailSend emailSend:emailSendList){
				if(!emailUserList.contains(emailSend.getEmailPost())){
					emailUserList.add(emailSend.getEmailPost());
				}
				Article article =cacheMap.get(emailSend.getBizId());
				if(null == article){
					try {
						article = articleBmo.get(Long.valueOf(emailSend.getBizId()));
					} catch (NumberFormatException e) {
						log.error(e);
					} catch (BusinessException e) {
						log.error(e);
					}
					if(null != article){
						cacheMap.put(emailSend.getBizId(), article);
					}
				}
			}
			for(String user:emailUserList){
				Map<String,Object> dataMap = new HashMap<String,Object> ();
				dataMap.put(Const.SITE_URL_NAME, Const.CONST_SITE_URL);
				List<Article> articleList = new ArrayList<Article>();
				for(EmailSend emailSend:emailSendList){
					if(EmailType.NEW_ARTICLE_AT_USER.equals(emailSend.getEmailTpl())){
						Article article = cacheMap.get(emailSend.getBizId());
						if(null !=article && !articleList.contains(article)){
							articleList.add(article);
						}
						delEmailSend(emailSend.getId());
					}
				}
				try {
					if(articleList.size()>0){
						dataMap.put("articleList", articleList);
						mailService.setAncsTemplateMail(user, "最新朋友分享笔记", EmailType.NEW_ARTICLE_AT_USER+".tpl", dataMap);
					}
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}
}
