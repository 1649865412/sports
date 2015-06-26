package api.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.main.LogInfo;
import com.base.log.LogLevel;
import com.base.service.LogInfoService;
import com.utils.Exceptions;

@Service
public class LogInfoUtilService
{
	private static final Logger LOG = LoggerFactory.getLogger(LogInfoUtilService.class);
	
	@Autowired
	private LogInfoService logInfoService;
	
	/**
	 * 记录日志
	 * 
	 * @param message
	 */
	public void log(String message, LogLevel logLevel)
	{
		LogInfo logInfo = new LogInfo();
		
		try
		{
			if (StringUtils.isNotEmpty(message))
			{   
				logInfo.setUsername("定时作业");
				logInfo.setIpAddress("----");
				logInfo.setMessage(message);
				logInfo.setCreateTime(new Date());
				logInfo.setLogLevel(logLevel);
			}
			
			logInfoService.saveOrUpdate(logInfo);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * 记录日志
	 * 
	 * @param message
	 */
	public void info(String message)
	{
		log(message, LogLevel.INFO);
	}
	
	/**
	 * 记录日志
	 * 
	 * @param message
	 */
	public void trace(String message)
	{
		log(message, LogLevel.TRACE);
	}
	
	/**
	 * 记录日志
	 * 
	 * @param message
	 */
	public void warn(String message)
	{
		log(message, LogLevel.WARN);
	}
	
	/**
	 * 记录日志
	 * 
	 * @param message
	 */
	public void error(String message)
	{
		log(message, LogLevel.ERROR);
	}
	
	/**
	 * 记录日志
	 * 
	 * @param message
	 */
	public void debug(String message)
	{
		log(message, LogLevel.DEBUG);
	}
	
}
