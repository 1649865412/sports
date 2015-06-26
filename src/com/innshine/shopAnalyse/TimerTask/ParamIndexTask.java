package com.innshine.shopAnalyse.TimerTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import api.utils.LogInfoUtilService;

import com.base.service.SummaryCacheService;
import com.utils.DateUtils;

public class ParamIndexTask implements Job {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ParamIndexTask.class);
	
	
	@Autowired
	private SummaryCacheService summaryCacheService;
	
	/**
	 * 日记记录业务类
	 * 
	 */
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		doTaskIndex();
	}
	
	
	
	/**
	 * 首页缓存更新
	 */
	public void doTaskIndex()
	{
		LOG.info(" //===========================首页缓存更新================================//");
		LOG.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		logInfoUtilService.info("首页缓存更新定时任务开始");
		try
		{  
			summaryCacheService.refresh();	
		}
		
		catch (Throwable e)
		{
			e.printStackTrace();
			logInfoUtilService.error("首页缓存更新定时任务出错，请联系程序员");
		}
		
		LOG.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOG.info(" //============================首页缓存更新 End=================================//");
		logInfoUtilService.info("首页缓存更新定时任务结束");
	}

}
