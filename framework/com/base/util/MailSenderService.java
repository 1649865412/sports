package com.base.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Repository;

/**
 * @description 邮件服务类
 * @author shing
 * 
 */
@Repository
public class MailSenderService {

	private  JavaMailSender mailSender;
	
	private  SimpleMailMessage simpleMailMessage ;
	
	private String from;  

	/**
	 * 发邮件
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param attachFile 附件（多附件以;号分隔）
     * @param from 发件人邮箱
     * @param to 收件人邮箱（多收件人邮箱以;号分隔）
     * @return boolean
     */
	public boolean sendMail(String subject, String content,
			String attachFile, String from, String to) {
		
		final String from_ = from;
		final String[] to_ = to.split(";");
		final String subject_ = subject;
		final String content_ = content;
		final String[] files = (attachFile.trim() == "" || attachFile == null) ? new String[0]
				: attachFile.split(";");// 附件文件集合

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException, UnsupportedEncodingException {
				// 设置收信人
				InternetAddress[] address = new InternetAddress[to_.length];
				for (int i = 0; i < to_.length; i++) {
					address[i] = new InternetAddress(to_[i]);
				}
				mimeMessage.setRecipients(Message.RecipientType.TO, address);
				// 设置发信人
				mimeMessage.setFrom(new InternetAddress(from_));
				// 设置主题
				mimeMessage.setSubject(subject_, "UTF-8");
				Multipart mp = new MimeMultipart();
				// 向Multipart添加正文
				MimeBodyPart mbpContent = new MimeBodyPart();
				// mbpContent.setText(content_);
				// 发送网页内容
				mbpContent.setContent(content_, "text/html;charset=UTF-8");
				// 向MimeMessage添加（Multipart代表正文）
				mp.addBodyPart(mbpContent);
				// 向Multipart添加附件
				for (int i = 0; i < files.length; i++) {
					MimeBodyPart mbpFile = new MimeBodyPart();
					String filename = files[i];
					FileDataSource fds = new FileDataSource(filename);
					mbpFile.setDataHandler(new DataHandler(fds));
					mbpFile.setFileName(MimeUtility.encodeText(fds.getName()));
					mp.addBodyPart(mbpFile);
				}
				// 向Multipart添加MimeMessage
				mimeMessage.setContent(mp);
				mimeMessage.setSentDate(new Date());
			}
		};
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 发邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachFile
	 *            附件（多附件以;号分隔）
	 * @param to
	 *            收件人邮箱（多收件人邮箱以;号分隔）
	 * @return boolean
	 */
	public boolean sendMail(String subject, String content,
			String attachFile, String to) {
		String from = simpleMailMessage.getFrom();
		System.out.print("__TO_MAIL: " + to);
		boolean result = this.sendMail(subject, content, attachFile, from, to);
		return result;
	}

	/**
	 * 发邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachFile
	 *            附件
	 * @param from
	 *            发件人邮箱
	 * @param to
	 *            收件人邮箱（多收件人邮箱以;号分隔）
	 * @return boolean
	 */
	public boolean sendMail(String subject, String content,
			File[] attachFile, String from, String to) {

		final String from_ = from;
		final String[] to_ = to.split(";");
		final String subject_ = subject;
		final String content_ = content;
		final File[] files = attachFile;// 附件文件集合

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException, UnsupportedEncodingException {
				// 设置收信人
				InternetAddress[] address = new InternetAddress[to_.length];

				for (int i = 0; i < to_.length; i++) {
					address[i] = new InternetAddress(to_[i]);
				}
				mimeMessage.setRecipients(Message.RecipientType.TO, address);
				// 设置发信人
				mimeMessage.setFrom(new InternetAddress(from_));
				// 设置主题
				mimeMessage.setSubject(subject_, "gb2312");
				Multipart mp = new MimeMultipart();
				// 向Multipart添加正文
				MimeBodyPart mbpContent = new MimeBodyPart();
				mbpContent.setText(content_);
				// 向MimeMessage添加（Multipart代表正文）
				mp.addBodyPart(mbpContent);
				// 向Multipart添加附件
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						MimeBodyPart mbpFile = new MimeBodyPart();
						FileDataSource fds = new FileDataSource(files[i]);
						mbpFile.setDataHandler(new DataHandler(fds));
						mbpFile.setFileName(MimeUtility.encodeText(fds
								.getName()));
						mp.addBodyPart(mbpFile);
					}
				}
				// 向Multipart添加MimeMessage
				mimeMessage.setContent(mp);
				mimeMessage.setSentDate(new Date());
			}
		};
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 发邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachFile
	 *            附件
	 * @param to
	 *            收件人邮箱（多收件人邮箱以;号分隔）
	 * @return boolean
	 */
	public boolean sendMail(String subject, String content,
			File[] attachFile, String to) {
		String from = simpleMailMessage.getFrom();
		boolean result =this.sendMail(subject, content, attachFile, from, to);
		return result;
	}
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public static void main(String[] args){
		//String[] path={"spring/applicationContext.xml","spring/mail.xml"} ;
		//ApplicationContext context = new ClassPathXmlApplicationContext(path);
	}
}
