package cn.hnhy.hyoa.core.common.email.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import cn.hnhy.hyoa.core.common.email.EmailSender;


/**
 * 邮件发送接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午3:26:40
 */
public class EmailSenderImpl implements EmailSender {
	
	/** 注入Spring的邮件发送接口 */
	private JavaMailSender javaMailSender;
	private String from;
	
	/**
	 * 发送邮件方法
	 * @param to 收件人
	 * @param subject 主题
	 * @param message 消息体
	 * @param html 是否为html格式的邮件 true：是  false: 不是
	 * @return true: 发送成功  false: 发送失败
	 */
	public boolean send(String to, String subject, String message, boolean html){
		try{
			/** 创建邮件消息体 */
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			/** 创建邮件消息体的帮助类 */
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			/** 设置收件人 */
			helper.setTo(to);
			/** 设置发件人 */
			helper.setFrom(from);
			/** 设置邮件的主题 */
			helper.setSubject(subject);
			/** 设置邮件的内容 */
			helper.setText(message, html);
			/** 发送邮件 */
			javaMailSender.send(mimeMessage);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public void setFrom(String from) {
		this.from = from;
	}
}