package com.innshine.salesstockdetails.timetask;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.entity.main.Brand;
import com.innshine.salesstockdetails.entity.OrderItemsInfo;
import com.utils.Exceptions;

/**
 * OCS销售订单数据清洗线程类
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class OcsSalesOrderDataCleaningCallable implements Callable<Boolean>
{
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OcsSalesOrderDataCleaningCallable.class);
	
	/**
	 * OCS订单数据对象
	 */
	private OrderItemsInfo orderItemsInfo;
	
	/**
	 * OCS销售订单数据清洗服务类
	 */
	private OcsSalesOrderDataCleaningService ocsSalesOrderDataCleaningService;
	
	/**
	 * 数据更新时间
	 */
	private String updateTime;
	
	/**
	 * 品牌列表
	 */
	private List<Brand> brands;
	
	public OcsSalesOrderDataCleaningCallable(OrderItemsInfo orderItemsInfo,
			OcsSalesOrderDataCleaningService ocsSalesOrderDataCleaningService, String updateTime, List<Brand> brands)
	{
		this.orderItemsInfo = orderItemsInfo;
		this.ocsSalesOrderDataCleaningService = ocsSalesOrderDataCleaningService;
		this.updateTime = updateTime;
		this.brands = brands;
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
			ocsSalesOrderDataCleaningService.dataCleaningToBaseData(orderItemsInfo, updateTime,brands);
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
