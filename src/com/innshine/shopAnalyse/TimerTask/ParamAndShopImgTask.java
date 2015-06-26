package com.innshine.shopAnalyse.TimerTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import api.utils.LogInfoUtilService;

import com.base.service.component.BusinessCache;
import com.innshine.shopAnalyse.service.ShopProductionAnalyseService;
import com.innshine.shopAnalyse.util.FileOperate;
import com.utils.DateUtils;

public class ParamAndShopImgTask implements Job {
	
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ParamListener.class);
	
	/**
	 * 平台业务类
	 * 
	 */
	@Autowired
	private ShopProductionAnalyseService shopProductionAnalyseService;

	
	/**
	 * 缓存类
	 * 
	 */
	@Autowired
	private BusinessCache businessCache;

	/**
	 * 日记记录业务类
	 * 
	 */
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		doTask();
	}
	/**
	 * 定时读品类，系列等参数到缓存与定期清理销售分析预览生成的图片工作
	 */
	public void doTask()
	{
		LOG.info(" //============================开始定时读品类，系列等参数到缓存与定期清理销售分析预览生成的图片工作=================================//");
		LOG.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		logInfoUtilService.info("开始定时读品类，系列等参数到缓存与定期清理销售分析预览生成的图片工作");
		try
		  {  
			ParamListener.setParam(shopProductionAnalyseService, businessCache) ;
			// 防止数据库连接数溢出,  
			Thread.sleep(1000 * 60); 
			
			String url = this.getClass().getResource("").getPath().replaceAll(
					"%20", " ");
			String tempPath = url.substring(0, url.indexOf("WEB-INF")) + "excelImg";
			
			new FileOperate().del(tempPath);
			
			Thread.sleep(1000 * 60);
			
			
			Thread.sleep(1000 * 60);
			
			
		}
		
		catch (Throwable e)
		{
			e.printStackTrace();
			logInfoUtilService.error("定时读品类，系列等参数到缓存与定期清理销售分析预览生成的图片工作出错，请联系程序员");
		}
		
		LOG.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOG.info(" //=============================定时读品类，系列等参数到内存与定期清理销售分析预览生成的图片工作 End=================================//");
		logInfoUtilService.info("定时读品类，系列等参数到内存与定期清理销售分析预览生成的图片工作 结束");
		
	}
	
	

	
	
}
