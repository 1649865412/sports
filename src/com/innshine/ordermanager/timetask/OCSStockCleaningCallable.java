package com.innshine.ordermanager.timetask;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innshine.ordermanager.entity.OcsStockItemsInfo;
import com.utils.Exceptions;

/**
 * OCS出入库存数据清洗线程类
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class OCSStockCleaningCallable implements Callable<Boolean>
{
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OCSStockCleaningCallable.class);
	
	/**
	 * OCS 出入库存对象
	 */
	private OcsStockItemsInfo arrivalOrderItemsInfo;
	
	/**
	 * OCS销售订单数据清洗服务类
	 */
	private OCSStockDataCleaningService ocsSalesOrderDataCleaningService;
	
	/**
	 * 数据更新时间
	 */
	private String updateTime;
	
	public OCSStockCleaningCallable(OcsStockItemsInfo arrivalOrderItemsInfo,
			OCSStockDataCleaningService ocsSalesOrderDataCleaningService, String updateTime)
	{
		this.arrivalOrderItemsInfo = arrivalOrderItemsInfo;
		this.ocsSalesOrderDataCleaningService = ocsSalesOrderDataCleaningService;
		this.updateTime = updateTime;
	}
	
	/**
	 * 多线程执行方法
	 */
	public Boolean call() throws Exception
	{
		
		// 让当前处理线程休眠几秒，以防止多线程抢占资源问题
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		try
		{
			ocsSalesOrderDataCleaningService.dataCleaningToBaseData(arrivalOrderItemsInfo, updateTime);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
			return false;
		}
		
		return true;
		
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
