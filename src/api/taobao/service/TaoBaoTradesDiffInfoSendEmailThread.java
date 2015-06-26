package api.taobao.service;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.util.MailSenderService;
import com.innshine.daily.Constants;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.innshine.taobaoemailmanager.entity.TradeSendEmailInfo;
import com.innshine.taobaoemailmanager.service.TradeSendEmailInfoService;
import com.utils.DateUtils;
import com.utils.Exceptions;

/**
 * 发送email 线程类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */

public class TaoBaoTradesDiffInfoSendEmailThread implements Runnable
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TaoBaoTradesDiffInfoSendEmailThread.class);
	
	private TradeSendEmailInfoService emailInfoService;
	
	private MailSenderService mailSenderService;
	
	/**
	 * 获取文件名与文件路径数组，该数组永远只保持两个长度，0：文件名 1:文件完整路径中不包含文件名
	 */
	private String[] fileAttr = null;
	
	/**
	 * 品牌编号
	 */
	private Long brandId;
	
	/**
	 * 标题时间
	 */
	private String titleTime;
	
	public TaoBaoTradesDiffInfoSendEmailThread(String[] fileAttr, String titleTime,
			MailSenderService mailSenderService, TradeSendEmailInfoService emailInfoService, Long brandId)
	{
		super();
		this.fileAttr = fileAttr;
		this.titleTime = titleTime;
		this.mailSenderService = mailSenderService;
		this.emailInfoService = emailInfoService;
		this.brandId = brandId;
	}
	
	public void run()
	{
		// 收件人
		String email = getRecipientEmail();
		
		// 邮件附件
		File[] attachFile = getAttachFile();
		
		// 邮件主题
		String subject = getSubject();
		
		// 获取邮件内容
		String content = getContent();
		
		if (StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(subject) && ArrayUtils.isNotEmpty(attachFile))
		{
			try
			{
				boolean isSend = true;
				
				// 计数器，失败后默认再重发三次
				int count = 0;
				
				while (isSend)
				{
					if (count >= 3)
					{
						modiftySendStatus();
						break;
					}
					
					isSend = mailSenderService.sendMail(subject, content, attachFile, email);
					
					// ture ，默认为发送成功
					if (isSend)
					{
						isSend = false;
						LOGGER.info("Send Email Succeed !" + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
					}
					else
					{
						isSend = isSend ? isSend : true;
						count++;
						LOGGER.info("Send Email Failed !" + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
						
						// 如果发送失败， 1秒后重试
						Thread.sleep(1000);
					}
				}
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
			finally
			{
				deleteFile(getAttachFile());
			}
			
		}
		else
		{
			
			modiftySendStatus();
		}
		
	}
	
	/**
	 * 删除发送的文件
	 * 
	 * @param attachFile
	 */
	private void deleteFile(File[] attachFile)
	{
		if (ArrayUtils.isNotEmpty(attachFile))
		{
			for (File file : attachFile)
			{
				ExcelFileUtils.deleteFile(file);
			}
		}
	}
	
	/**
	 * 更新发送状态
	 */
	private void modiftySendStatus()
	{
		// if (null != dailyReportInfo && null != dailyReportInfoService)
		// {
		// // 更新状态类失败，默认让缓存更新
		// dailyReportInfo.setSendStauts(Constants.SEND_STATUS_FAILED);
		//			
		// // dailyReportInfoService.saveOrUpdate(dailyReportInfo);
		// }
	}
	
	/**
	 * 产生邮件内容
	 * 
	 * @return
	 */
	private String getContent()
	{
		
		StringBuffer buffer = new StringBuffer("Dear All :");
		buffer.append("\r\n").append("            ").append(getSubject());
		
		return buffer.toString();
	}
	
	/**
	 * 邮件主题
	 * <p>
	 * 
	 * @return
	 */
	private String getSubject()
	{
		// String currentDate = GatherDailyExcelExportUtils.getDate(-1,
		// DateUtils.SIMPLE_DEFAULT_FORMAT);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("【");
		buffer.append(titleTime);
		buffer.append(" 】");
		buffer.append("    差异数据，请注意查收！");
		return buffer.toString();
		
	}
	
	/**
	 * 获取邮件附件
	 * <p>
	 * 
	 * @return
	 */
	private File[] getAttachFile()
	{
		if (ArrayUtils.isNotEmpty(fileAttr) && fileAttr.length >= 2)
		{
			try
			{
				
				return new File[] { new File(fileAttr[1] + File.separator + fileAttr[0]) };
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return null;
	}
	
	/**
	 * 获取收件人列表，多个以;拼接
	 * <p>
	 * 
	 * @return String
	 */
	private String getRecipientEmail()
	{
		
		// 获取所有Email对象
		List<TradeSendEmailInfo> infos = emailInfoService.findByBrandId(brandId);
		
		StringBuffer buffer = new StringBuffer();
		if (CollectionUtils.isNotEmpty(infos))
		{
			for (TradeSendEmailInfo sendEmailInfo : infos)
			{
				if (null != sendEmailInfo)
				{
					// 1是发送 0： 不发送
					if (sendEmailInfo.getEmailSend() == Constants.SEND_MAIL)
					{
						String address = sendEmailInfo.getEmailAddress();
						if (StringUtils.isNotEmpty(address))
						{
							buffer.append(address).append(Constants.SEMICOLON_SYMBOL);
						}
					}
				}
			}
		}
		
		return buffer.toString();
	}
	
}
