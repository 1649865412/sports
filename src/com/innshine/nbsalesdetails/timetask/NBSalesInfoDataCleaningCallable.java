package com.innshine.nbsalesdetails.timetask;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innshine.nbsalesdetails.entity.NbSalesDetails;
import com.utils.Exceptions;

/**
 * 数据清洗线程类
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class NBSalesInfoDataCleaningCallable implements Callable<Boolean>
{
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NBSalesInfoDataCleaningCallable.class);
	
	/**
	 * 实体bean
	 */
	private NbSalesDetails nbSalesDetails;
	
	/**
	 * 数据清洗服务类
	 */
	private NBSalesInfoDataCleaningService nbSalesInfoDataCleaningService;
	
	/**
	 * 数据更新时间
	 */
	private String updateTime;
	
	/**
	 * 多线程执行方法
	 */
	@Override
	public Boolean call() throws Exception
	{
		
		// 让当前处理线程休眠几秒，以防止多线程抢占资源问题
		try
		{
			Thread.sleep(200);
		}
		catch (InterruptedException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		try
		{
			nbSalesInfoDataCleaningService.dataCleaningToBaseData(nbSalesDetails, updateTime);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
			return false;
		}
		
		return true;
	}
	
	public NBSalesInfoDataCleaningCallable(NbSalesDetails nbSalesDetails,
			NBSalesInfoDataCleaningService productInfoDataCleaningService, String updateTime)
	{
		super();
		this.nbSalesDetails = nbSalesDetails;
		this.nbSalesInfoDataCleaningService = productInfoDataCleaningService;
		this.updateTime = updateTime;
	}
	
	public NbSalesDetails getDataCleaningItemPrices()
	{
		return nbSalesDetails;
	}
	
	public void setDataCleaningItemPrices(NbSalesDetails dataCleaningItemPrices)
	{
		this.nbSalesDetails = dataCleaningItemPrices;
	}
	
	public NBSalesInfoDataCleaningService getCleaningItemPricesService()
	{
		return nbSalesInfoDataCleaningService;
	}
	
	public void setCleaningItemPricesService(NBSalesInfoDataCleaningService cleaningItemPricesService)
	{
		this.nbSalesInfoDataCleaningService = cleaningItemPricesService;
	}
	
	public String getUpdateTime()
	{
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}
	
}
