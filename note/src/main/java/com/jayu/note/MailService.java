package com.jayu.note;

import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.jayu.log.Log;


import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 发送邮件
 *<P>
 * @author jayu
 * @version V1.0 2011-5-28
 * @CreateDate 2011-5-28 下午03:54:03
 */
public class MailService {
	protected static final Log logger = Log.getLog(MailService.class);
	private JavaMailSender javaMailSender;
	private Configuration freemarkerConfiguration;
	private TaskExecutor taskExecutor;
	private SimpleMailMessage simpleMailMessage;
	private  InternetAddress fromAddress;
	private String encoding = "UTF-8";

	/**
	 * 发送文本邮件
	 */
	public void sendSimpleMail(String to, String subject, String text) {
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		simpleMailMessage.setSentDate(new Date());
		javaMailSender.send(simpleMailMessage);
	}
	/**
	 * 发送文本邮件
	 */
	public void sendSimpleMail(String to[], String subject, String text) {
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		simpleMailMessage.setSentDate(new Date());
		javaMailSender.send(simpleMailMessage);
	}

	/**
	 * 异步发送文本邮件
	 */
	public void sendAsncSimpleMail(final String to, final String subject,final String text) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendSimpleMail(to, subject, text) ;
				} catch (Exception e) {
					logger.error("邮件发送失败，失败提示!" , e);
				}
			}
		});
	}

	/**
	 * 异步发送文本邮件
	 */
	public void sendAsncSimpleMail(final String to[], final String subject,final String text) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendSimpleMail(to, subject, text) ;
				} catch (Exception e) {
					logger.error("邮件发送失败，失败提示!" , e);
				}
			}
		});
	}

	/**
	 * 异步发送模版邮件
	 * 
	 * @param to
	 * @param subject
	 * @param encoding
	 * @param template
	 * @param parse
	 * @throws Exception
	 */
	public void setAsyncTemplateMail(final String to, final String subject, final String template,
			final Map<String, Object> parse) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					setTemplateMail(to, subject, template, parse);
				} catch (Exception e) {
					logger.error("邮件发送失败，失败提示!" , e);
				}
			}
		});
	}
	
	/**
	 * 异步发送模版邮件
	 * 
	 * @param to
	 * @param subject
	 * @param encoding
	 * @param template
	 * @param parse
	 * @throws Exception
	 */
	public void setAsyncTemplateMail(final String to[], final String subject, final String template,
			final Map<String, Object> parse) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					setTemplateMail(to, subject, template, parse);
				} catch (Exception e) {
					logger.error("邮件发送失败，失败提示!" , e);
				}
			}
		});
	}

	/**
	 * 发送模版邮件
	 * 
	 * @param to
	 * @param subject
	 * @param encoding
	 * @param template
	 * @param parse
	 * @throws Exception
	 */
	public void setTemplateMail(String to, String subject, 
			String template, Map<String, Object> parse) throws Exception {
		Template templateS = freemarkerConfiguration.getTemplate(template,
				encoding);

		String htmlCnt = FreeMarkerTemplateUtils.processTemplateIntoString(
				templateS, parse);

		sendHTMLMail(to, subject, htmlCnt);
	}
	
	public void setAncsTemplateMail(String to, String subject, 
			String template, Map<String, Object> parse) throws Exception {
		Template templateS = freemarkerConfiguration.getTemplate(template,
				encoding);

		String htmlCnt = FreeMarkerTemplateUtils.processTemplateIntoString(
				templateS, parse);

		sendAncsHTMLMail(to, subject, htmlCnt);
	}

	/**
	 * 发送模版邮件
	 * 
	 * @param to
	 * @param subject
	 * @param encoding
	 * @param template
	 * @param parse
	 * @throws Exception
	 */
	public void setTemplateMail(String to[], String subject, 
			String template, Map<String, Object> parse) throws Exception {
		Template templateS = freemarkerConfiguration.getTemplate(template,
				encoding);

		String htmlCnt = FreeMarkerTemplateUtils.processTemplateIntoString(
				templateS, parse);

		sendHTMLMail(to, subject, htmlCnt);
	}
	/**
	 * 发送模版邮件
	 * 
	 * @param to
	 * @param subject
	 * @param encoding
	 * @param template
	 * @param parse
	 * @throws Exception
	 */
	public void setAncsTemplateMail(String to[], String subject, 
			String template, Map<String, Object> parse) throws Exception {
		Template templateS = freemarkerConfiguration.getTemplate(template,
				encoding);

		String htmlCnt = FreeMarkerTemplateUtils.processTemplateIntoString(
				templateS, parse);

		sendAncsHTMLMail(to, subject, htmlCnt);
	}
	

	/**
	 * 发送html格式邮件
	 * 
	 * @param to
	 * @param subject
	 * @param htmlCnt
	 * @param encoding
	 * @throws MessagingException
	 */
	public void sendHTMLMail(String to, String subject, StringBuffer htmlCnt) throws MessagingException {
		sendHTMLMail(to, subject, htmlCnt.toString());
	}
	
	/**
	 * 异步发送html格式邮件
	 * 
	 * @param to
	 * @param subject
	 * @param htmlCnt
	 * @param encoding
	 * @throws MessagingException
	 */
	public void sendAncsHTMLMail(final String to, final String subject, final String htmlCnt) throws MessagingException {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendHTMLMail(to, subject, htmlCnt);
				} catch (Exception e) {
					logger.error("邮件发送失败，失败提示!" , e);
				}
			}
		});
	}

	/**
	 * 异步发送html格式邮件
	 * 
	 * @param to
	 * @param subject
	 * @param htmlCnt
	 * @param encoding
	 * @throws MessagingException
	 */
	public void sendAncsHTMLMail(final String to[], final String subject, final String htmlCnt) throws MessagingException {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendHTMLMail(to, subject, htmlCnt);
				} catch (Exception e) {
					logger.error("邮件发送失败，失败提示!" , e);
				}
			}
		});
	}

	/**
	 * 发送html格式邮件
	 * 
	 * @param to
	 * @param subject
	 * @param htmlCnt
	 * @param encoding
	 * @throws MessagingException
	 */
	public void sendHTMLMail(String to, String subject, String htmlCnt) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
				mimeMessage, false, encoding);
		mimeMessageHelper.setFrom(fromAddress);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(htmlCnt, true);
		mimeMessageHelper.setSentDate(new Date());
		javaMailSender.send(mimeMessage);
	}
	
	/**
	 * 发送html格式邮件
	 * 
	 * @param to
	 * @param subject
	 * @param htmlCnt
	 * @param encoding
	 * @throws MessagingException
	 */
	public void sendHTMLMail(String to[], String subject, String htmlCnt) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
				mimeMessage, false, encoding);
		mimeMessageHelper.setFrom(fromAddress);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(htmlCnt, true);
		mimeMessageHelper.setSentDate(new Date());
		javaMailSender.send(mimeMessage);
	}

	public Configuration getFreemarkerConfiguration() {
		return freemarkerConfiguration;
	}
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public  void setFromAddress(InternetAddress fromAddress) {
		this.fromAddress = fromAddress;
	}
}
