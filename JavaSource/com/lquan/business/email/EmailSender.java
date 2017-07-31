package com.lquan.business.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

/**
 * 邮件发送类 简单实现，用于发送邮件 基本配置在props/mail.properties
 * 
 * @author lquan
 */
@Repository(value = "emailSender")
public class EmailSender {

	private Log log = LogFactory.getLog(EmailSender.class);
	
	@Autowired(required=true)
	private JavaMailSenderImpl mailSender;
	
	@Autowired(required=true)
	private SimpleMailMessage simpleMailMessage;
	
	@Autowired(required=true)
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * 内部发送邮件线程类
	 * @author hurong
	 */
	private class SendMailThread implements Runnable {
		private String to; // 接受邮件
		private String cc; //　抄送
		private String bcc; // 密送
		private String subject;
		private String content;

		private SendMailThread(String to,String cc,String bcc,String subject, String content) {
			super();
			this.to = to;
			this.cc = cc;
			this.bcc = bcc;
			this.subject = subject;
			this.content = content;
		}

		@Override
		public void run() {
			sendSimpleTextEmail(to,cc,bcc, subject, content);
		}
	}

	/**
	 * 利用线程池发送简单文本的邮件
	 * @param to
	 *            接收邮箱
	 * @param cc
	 * 			     抄送邮箱
	 * @param bcc
	 * 			     密送邮箱
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @return
	 */
	public boolean send(String to,String cc,String bcc, String subject, String content) {

		try {
			taskExecutor.execute(new SendMailThread(to,cc,bcc,subject, content));
		} catch (MailException e) {
			log.error("发送邮件错误", e);
			return false;
		}

		return true;
	}

	/**
	 * 	 发送简单文本的邮件
	 * 
	 * @param to
	 *            接收邮箱
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @return
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param content
	 */
	public void sendSimpleTextEmail(String to,String cc,String bcc, String subject, String content) {
		
		if(to != null && !"".equals(to.trim())){
			simpleMailMessage.setTo(emailAddressToArray(to)); // 发送
			if(cc != null && !"".equals(cc.trim()))
				simpleMailMessage.setCc(emailAddressToArray(cc)); // 抄送
			if(bcc != null && !"".equals(bcc.trim()))
				simpleMailMessage.setBcc(emailAddressToArray(bcc)); //  密送
			simpleMailMessage.setSubject(subject);
			simpleMailMessage.setText(content);
	
			mailSender.send(simpleMailMessage);
		}else
			log.info("EmailSender 发送邮件出错……没有发送人");
	}
	
	/**
	 * 转换邮件接收人的格式为数组类型
	 * 
	 * @param emails
	 * 
	 * @return
	 */
	private String[] emailAddressToArray(String emailStr){
		log.info("邮箱转换前的数据："+ emailStr);
		int firstPostion = emailStr.indexOf(";");
		int lastPostion = emailStr.lastIndexOf(";");
		int strLength = emailStr.length();
		String[] emailAddress=null;
		
		// 去掉头部的“;”
		if(firstPostion==0 && strLength>1){
			emailStr = emailStr.substring(1, strLength);
		}
		
		// 去掉尾部的";"
		if(strLength==lastPostion+1 && lastPostion>1){
			emailStr = emailStr.substring(0, strLength-1);
			
		}
		emailAddress = emailStr.split(";");
		log.info("*************转换-Start***********");
		for(int i=0;i<emailAddress.length;i++){
			log.info(emailAddress[i]);
		}
		log.info("**************--End***************");
		return emailAddress;
	}
	
	
	
	
}

