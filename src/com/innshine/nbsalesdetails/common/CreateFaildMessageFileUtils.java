package com.innshine.nbsalesdetails.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innshine.productinfo.utils.ExcelFileUtils;
import com.utils.Exceptions;

/**
 * 
 * 导出错误提示文件 <code>CreateFaildMessageFileUtils.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class CreateFaildMessageFileUtils
{
	/**
	 * 默认标题行
	 */
	private static final String DEFAULT_TITLE = "----------------69码,错误原因------------------------------";
	
	/**
	 * 导出错误文件目录
	 */
	private static final String ERROR_MESSAGE_FILE_NAME = "error_message/";
	
	/**
	 * txt文件中的换行符
	 */
	public static final String LINE_FEED_SYMBOL = "\r\n";
	
	/**
	 * 空格符
	 */
	public static final String BLANK_SYMOBL = "\t";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateFaildMessageFileUtils.class);
	
	private static File createErrorFile(String fileName)
	{
		File file = new File(ExcelFileUtils.FILE_TEMP_DIR + ExcelFileUtils.EXPORT + ERROR_MESSAGE_FILE_NAME);
		if (!file.exists())
		{
			ExcelFileUtils.createDir(file.getPath());
		}
		
		File fileErrorFile = new File(file.getPath() + File.separator
				+ (StringUtils.isNotBlank(fileName) ? fileName : "error_" + new Date().getTime()) + ".txt");
		
		try
		{
			if (fileErrorFile.exists())
			{
				fileErrorFile.delete();
			}
			
			fileErrorFile.createNewFile();
		}
		catch (IOException e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
			return null;
		}
		
		return fileErrorFile;
	}
	
	/**
	 * 导出效验失败数据列表，默认导出平台编号+错误提示信息。 产生默认标题：“69码，错误原因 ”
	 * 
	 * @param errorInfo
	 *        需要写入错误信息列表，默认为拼接 好的字段列表
	 * @param title
	 *        第一行标题 文件名
	 * @param fileName
	 *        文件名称，为空则默认以UTC时间命名
	 * @return 生成后的完成路径
	 */
	public static File exprotErrorInfo(String errorInfo, String fileName)
	{
		
		return exprotErrorInfo(errorInfo, DEFAULT_TITLE, fileName);
	}
	
	/**
	 * 导出效验失败数据列表，默认导出平台编号+错误提示信息。 <br>
	 * 1. 产生默认标题：“69码，错误原因 ”，<br>
	 * 2. 文件名：按当前UTC时间命名 如：error_1234566778.txt
	 * 
	 * @param errorInfo
	 *        需要写入错误信息列表，默认为拼接 好的字段列表
	 * @return 生成后的完成路径
	 */
	public static File exprotErrorInfo(String errorInfo)
	{
		
		return exprotErrorInfo(errorInfo, DEFAULT_TITLE, null);
	}
	
	/**
	 * 导出效验失败数据列表，默认导出平台编号
	 * 
	 * @param errorInfo
	 *        需要写入错误信息列表，默认为拼接 好的字段列表
	 * @param title
	 *        第一行标题
	 * @param fileName
	 *        文件名
	 * @return 生成后的完成路径
	 */
	public static File exprotErrorInfo(String errorInfo, String title, String fileName)
	{
		if (StringUtils.isNotEmpty(errorInfo))
		{
			File file = createErrorFile(fileName);
			
			if (null != file)
			{
				
				BufferedWriter writer = null;
				try
				{
					
					writer = new BufferedWriter(new FileWriter(file));
					StringBuffer buffer = new StringBuffer();
					if (StringUtils.isNotEmpty(title))
					{
						buffer.append(title).append(LINE_FEED_SYMBOL);
					}
					
					buffer.append(errorInfo);
					
					writer.write(buffer.toString());
					writer.flush();
					
					return file;
				}
				
				catch (IOException e)
				{
					LOGGER.error(Exceptions.getStackTraceAsString(e));
				}
				finally
				{
					if (null != writer)
					{
						try
						{
							writer.close();
						}
						catch (IOException e)
						{
							LOGGER.error(Exceptions.getStackTraceAsString(e));
						}
					}
				}
			}
		}
		
		return null;
		
	}
	
	/**
	 * 根据文件名，获取对应完整的文件对象
	 * 
	 * @param fileName
	 *        文件名
	 * @return
	 */
	public static File getErrorMessageFile(String fileName)
	{
		if (StringUtils.isNotEmpty(fileName))
		{
			File file = new File(ExcelFileUtils.FILE_TEMP_DIR + ExcelFileUtils.EXPORT + ERROR_MESSAGE_FILE_NAME
					+ fileName);
			if (file.exists())
			{
				return file;
			}
		}
		
		return null;
	}
}
