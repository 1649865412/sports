/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.ordermanager.timetask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.utils.LogInfoUtilService;

import com.base.SecurityConstants;
import com.base.dao.sql.SQLHelper;
import com.base.dao.sql.SQLProperties;
import com.base.entity.main.Brand;
import com.base.service.BrandService;
import com.base.util.dwz.Page;
import com.innshine.ordermanager.entity.OcsStockItemsInfo;
import com.innshine.ordermanager.entity.OrderFormInfo;
import com.innshine.ordermanager.service.OrderFormInfoService;
import com.innshine.salesstockdetails.service.OcsOrderDataCleaningService;
import com.innshine.stockmanager.stockinfo.entity.StockInfo;
import com.utils.DateUtils;
import com.utils.Exceptions;

/**
 * OCS出入库存数据清洗服务类， 提供基本数据清洗逻辑方法
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class OCSStockDataCleaningService
{
	/**
	 * SQL key
	 */
	private static final String OCS_stock_SQL = "ocs.stock.sql";
	
	/**
	 * 所属组织编号 NewBalance
	 */
	//private static final long DEFAULT_ORGANIZTION_ID = 10L;;
	
	/**
	 * 默认线程池数量
	 */
	private static final int DEFAULT_POOL_SIZE = 10;
	
	/**
	 * 默认查询数量 100
	 */
	private static final int DEFAULT_NUM_PAGE = 500;
	
	/**
	 * 创建一个可重用固定线程数的线程池
	 */
	private static final ExecutorService DEFAULT_EXECUTOR_POOL = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
	
	/**
	 * 默认时区
	 */
	private static final String DEFAULT_TIME_ZONE_GMT = "GMT";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OCSStockDataCleaningService.class);
	
	/**
	 * 数据同步结束时间
	 */
	private static final String QUERY_FIELD_NAME_END_TIME = "endTime";
	
	/**
	 * 数据开始结束时间
	 */
	private static final String QUERY_FIELD_NAME_START_TIME = "startTime";
	
	@Autowired(required = true)
	private OrderFormInfoService orderFormInfoService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired(required = true)
	private OcsOrderDataCleaningService ocsOrderDataCleaningService;
	
	/**
	 * 日记记录业务类
	 * 
	 */
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	
	/**
	 * 根据开始与结束时间，拼接查询SQL
	 * <p>
	 * 
	 * @param startTime
	 *        入库开始时间
	 * @param endTime
	 *        入库结束时间
	 * @return String 格式化参数后的SQL
	 * @throws Exception
	 *         对参数进行格式化时，可能抛出异常
	 */
	public String getQuerySQL(Long startTime, Long endTime, String cfgKey) throws Exception
	{
		String sql = SQLProperties.getInstance().getSql(cfgKey);
		SQLHelper sqlHelper = new SQLHelper(sql);
		
		// 构造参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(QUERY_FIELD_NAME_START_TIME, startTime);
		params.put(QUERY_FIELD_NAME_END_TIME, endTime);
		
		// 拼接开始与结束SQL
		StringBuffer buffer = new StringBuffer(sqlHelper.parepareSQLtext(params));
		
		return buffer.toString();
	}
	
	public void dataCleaningService() throws Exception
	{
		dataCleaningService(false);
	}
	
	/**
	 * 数据清写服务方法
	 * <p>
	 * 
	 * @param isForceRefresh
	 *        是否是强制刷新
	 * @throws Exception
	 */
	public synchronized void dataCleaningService(boolean isForceRefresh) throws Exception
	{
		// 当前时间往前推一天时间范围 如：2014-05-19 ~ 2014-05-20
		Long[] times = getQueryTime(isForceRefresh);
		
		if (ArrayUtils.isNotEmpty(times) && times.length >= 2)
		{
			// 获取查询SQL
			String querySql = getQuerySQL(times[0], times[1], OCS_stock_SQL);
			
			// 初始化分页对象
			Page page = new Page();
			
			// 默认查询取值数量
			page.setNumPerPage(DEFAULT_NUM_PAGE);
			
			// 实体bean对象class
			Class<OcsStockItemsInfo> clazz = OcsStockItemsInfo.class;
			
			// 首次查询数据
			List<Future<Boolean>> faFutures = queryDataToExecutorClaningTask(querySql, page, clazz);
			
			// 获取线程返回值，主要防止线程休眠，不继续往下执行问题
			getFutureValue(faFutures);
			
			// 如果总页数大于1，则表示还有数据，则继续向下清洗
			if (page.getTotalPage() > 1)
			{
				for (int i = 1; i < page.getTotalPage(); i++)
				{
					// 获取下一页页码
					int tmpNextPage = i + 1;
					
					// 如果是取到最后一页，则直接跳出循环，不需要继续向下再取
					if (tmpNextPage > page.getTotalPage())
					{
						break;
					}
					
					// 构造获取下一批数据对象
					Page netxPage = getNetxPage(tmpNextPage, page);
					
					// 获取线程返回值，主要防止线程休眠，不继续往下执行问题
					getFutureValue(queryDataToExecutorClaningTask(querySql, netxPage, clazz));
				}
			}
			
		}
		
	}
	
	/**
	 * 获取线程返回值，主要防止线程休眠，不继续往下执行问题
	 * <p>
	 * 
	 * @param faFutures
	 *        线程对象列表
	 * @return List< Boolean> 最终的线程执行完毕后每个返回值列表
	 */
	public List<Boolean> getFutureValue(List<Future<Boolean>> faFutures)
	{
		List<Boolean> list = new ArrayList<Boolean>();
		if (null != faFutures)
		{
			for (Future<Boolean> future : faFutures)
			{
				try
				{
					list.add(future.get());
				}
				catch (InterruptedException e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
				}
				catch (ExecutionException e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
				}
				catch (Exception e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 查询需要清洗的原始数据，并执行数据清洗动作
	 * 
	 * @param querySql
	 *        查询SQL
	 * @param page
	 *        分批获取数据对象
	 * @param clazz
	 *        实体bean Class对象
	 * @return List< Future< Boolean>> 返回每个线程的执行结果，调用Future.get方法即可获取返回值
	 */
	public List<Future<Boolean>> queryDataToExecutorClaningTask(String querySql, Page page,
			Class<OcsStockItemsInfo> clazz)
	{
		
		LOG.info("Query SQL = " + querySql);
		
		// 获取第1批数据清写对象
		List<OcsStockItemsInfo> orderItemsList = queryIoStockByCreateTime(querySql, page, clazz);
		
		List<Future<Boolean>> futures = executorTask(orderItemsList, DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		
		return futures;
	}
	
	/**
	 * 调用程执行，数据清洗过程，并返回每个线程的执行结果
	 * 
	 * @param orderItemsInfoList
	 *        需要清洗的数据列表，从原始库中查询获得。
	 * @param updateTime
	 *        数据更新时间
	 * @return List< Future< Boolean>> 返回每个线程的执行结果，调用Future.get方法即可获取返回值
	 */
	public List<Future<Boolean>> executorTask(List<OcsStockItemsInfo> orderItemsInfoList, String updateTime)
	{
		// 记录执行结果
		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
		if (null == orderItemsInfoList || orderItemsInfoList.isEmpty())
		{
			return futures;
		}
		
		// 向线程池中添加需要执行的线程队列
		for (int i = 0; i < orderItemsInfoList.size(); i++)
		{
			Future<Boolean> future = DEFAULT_EXECUTOR_POOL.submit(new OCSStockCleaningCallable(orderItemsInfoList
					.get(i), this, updateTime));
			
			// 如需要线程的返回结果，则调用Future.get方法即可
			futures.add(future);
		}
		
		// 执行完成后清空查询结果集
		orderItemsInfoList.clear();
		orderItemsInfoList = null;
		
		return futures;
	}
	
	/**
	 * 数据清洗正式方法，完成对爬虫数据的真实清洗，比对、组装、最后数据录入基础数据表中，productinfo、salesDetails
	 * 
	 * @param arrivalOrderItemsInfo
	 *        爬虫数据对象
	 * @param updateTime
	 *        入库时间
	 */
	public void dataCleaningToBaseData(OcsStockItemsInfo arrivalOrderItemsInfo, String updateTime)
	{
		if (null != arrivalOrderItemsInfo && !checkStockInfo(arrivalOrderItemsInfo))
		{ 
			addStockDetails(arrivalOrderItemsInfo, updateTime);
		}
	}
	
	/**
	 * 构造产品信息并录入新的产品信息
	 * 
	 * @param ocsStockItemsInfo
	 *        爬虫数据对象
	 * @param productInfoService2
	 */
	private void addStockDetails(OcsStockItemsInfo ocsStockItemsInfo, String updateTime)
	{
		if (null != ocsStockItemsInfo)
		{
			try
			{
				OrderFormInfo formInfo = setStoreInfo(ocsStockItemsInfo);
				
				if (null != ocsStockItemsInfo)
				{
					modiftyStockInfo(ocsStockItemsInfo, formInfo);
				}
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
	}
	
	/**
	 * 更新订货单中的库存信息
	 * 
	 * 
	 * @param ocsStockItemsInfo
	 *        销售信息对象
	 * @param formInfo
	 *        订货信息实体bean
	 */
	private void modiftyStockInfo(OcsStockItemsInfo ocsStockItemsInfo, OrderFormInfo formInfo)
	{
		try
		{
			if (null != ocsStockItemsInfo && null != formInfo)
			{
				List<StockInfo> stockInfos = formInfo.getStockInfos();
				if (CollectionUtils.isNotEmpty(stockInfos))
				{
					for (StockInfo info : stockInfos)
					{
						orderFormInfoService.cosnStockLogInfos(info, false, true);
						
						if (null != info)
						{
							Integer tmpAvailableStockNumber = getStockNumber(info.getAvailableStockNumber(),
									ocsStockItemsInfo.getStockNums());
							info.setAvailableStockNumber(null != tmpAvailableStockNumber ? tmpAvailableStockNumber
									: info.getAvailableStockNumber());
						}
					}
				}
				
				formInfo.setStockInfos(stockInfos);
				
				try
				{
					Thread.sleep(50);
				}
				catch (InterruptedException e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
					
				}
				orderFormInfoService.saveOrUpdate(formInfo);
			}
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
	}
	
	/**
	 * 设置库存信息
	 * 
	 * @param OcsStockItemsInfo
	 *        销售信息对象
	 * @return OrderFormInfo
	 */
	private OrderFormInfo setStoreInfo(OcsStockItemsInfo ocsStockItemsInfo)
	{
		OrderFormInfo orderFormInfo = null;
		if (null != ocsStockItemsInfo)
		{
			/***
			 * 根据OCS的品牌Id，对应商品管理系统中的商品Id
			 */
			Brand brand = brandService.findByExtendTypeAndExtendId(SecurityConstants.OCS_SYSTEM, ocsStockItemsInfo.getBrandId());
			List<OrderFormInfo> orderFormInfos = orderFormInfoService.findByBnAndBrandId(ocsStockItemsInfo
					.getMaterialNumber(), brand.getId());
			
			if (CollectionUtils.isNotEmpty(orderFormInfos))
			{
				orderFormInfo = orderFormInfos.get(0);
				
				if (null != orderFormInfo)
				{
					// 到货数量
					orderFormInfo.setArrivalNumber(getArriveNumber(orderFormInfo, ocsStockItemsInfo));
					
					// 预计到货时间
					orderFormInfo.setPredictArriveTime(ocsStockItemsInfo.getStockTime());
					 
					orderFormInfo.setArrivalTime(DateUtils.toDate(ocsStockItemsInfo.getStockTime()));
				}
			}
		}
		
		return orderFormInfo;
	}
	
	/**
	 * 获取到货数量
	 * 
	 * @param orderFormInfo
	 *        数据库中存在的到货数量
	 * @param ocsStockItemsInfo
	 *        OCS中查询到的到货数量 = 已入库数量+不良品数量
	 * @return 到货数量
	 */
	private Integer getArriveNumber(OrderFormInfo orderFormInfo, OcsStockItemsInfo ocsStockItemsInfo)
	{
		if (null != orderFormInfo && null != ocsStockItemsInfo)
		{
			Integer arrivalNumber = orderFormInfo.getArrivalNumber();
			
			// OCS中的库存数量(即到货数量)
			Integer stockNums = ocsStockItemsInfo.getStockNums();
			
			if (null != arrivalNumber && null != stockNums)
			{
				return arrivalNumber + stockNums;
			}
			else if (null != stockNums)
			{
				return stockNums;
			}
			
		}
		
		return null;
	}
	
	/**
	 * 获取可用库存数据
	 * 
	 * @param stockInfo
	 *        库存量对象
	 * @param ocsStockNums
	 *        入库数量 = 已入库数量+不良品数量(即到货数量，即可用库存)
	 * @return 原库存+入库数量
	 */
	private Integer getStockNumber(Integer tmpStockNumber, Integer ocsStockNums)
	{
		Integer curreStockNumber = null;
		
		if (null != tmpStockNumber && ocsStockNums != null)
		{
			curreStockNumber = tmpStockNumber + ocsStockNums;
		}
		
		return curreStockNumber;
	}
	
	/**
	 * 检测当前产品是否存在对应库存信息
	 * <p>
	 * 
	 * @param OCS数据库存中查询到的销售订单对象
	 * @return boolean true已存在 false:不存在
	 */
	private boolean checkStockInfo(OcsStockItemsInfo arrivalOrderItemsInfo)
	{
		
		boolean isFlag = false;
		if (null != arrivalOrderItemsInfo)
		{
			String tmpBarCode = arrivalOrderItemsInfo.getBarcode();
			String tmpMaterialNumber = arrivalOrderItemsInfo.getMaterialNumber();
			 
			if (StringUtils.isNotEmpty(tmpBarCode))
			{
				Brand br = brandService.findByExtendTypeAndExtendId(SecurityConstants.OCS_SYSTEM, arrivalOrderItemsInfo.getBrandId());
				// 效验订货管理中的库存信息，有则继续往下清洗，没有则不处理
				List<OrderFormInfo> formInfos = orderFormInfoService.findByUpccodeAndBnAndBrandId(tmpBarCode,
						tmpMaterialNumber, br.getId());
				
				if (CollectionUtils.isEmpty(formInfos))
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug("There is no inventory Info orderItemsInfo = " + arrivalOrderItemsInfo);
					}
					
					isFlag = true;
				}
				
			}
			
		}
		return isFlag;
		
	}
	
	/**
	 * 获取分页 对象
	 * 
	 * @param nextPageNum
	 *        下一页页码
	 * @param page
	 *        分页对象
	 * @return Page 当前取数据对象
	 */
	private Page getNetxPage(int nextPageNum, Page page)
	{
		if (null == page)
		{
			return null;
		}
		
		Page newPage = new Page();
		newPage.setPageNum(nextPageNum);
		newPage.setTotalCount(page.getTotalCount());
		newPage.setNumPerPage(page.getNumPerPage());
		newPage.setTotalPage(page.getTotalPage());
		return newPage;
	}
	
	/**
	 * 获取查询天数，默认数组只有两个长度，开始与结束时间
	 * 
	 * @param isForceRefresh
	 *        ture:前端页面点击强制刷新 ， false：定时器调用
	 * 
	 * @return String[] 默认两个查询
	 */
	private Long[] getQueryTime(boolean isForceRefresh)
	{
		Long[] times = new Long[] {};
		
		Date currDate = new Date();
		// 住前推一天时间
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIME_ZONE_GMT));
		calendar.setTime(currDate);
		
		if (isForceRefresh)
		{
			// 往前推一小时
			calendar.add(Calendar.HOUR, -1);
		}
		else
		{
			// 往前推一天
			calendar.add(Calendar.DATE, -1);
		}
		
		// 开始时间
		times = ArrayUtils.add(times, calendar.getTime().getTime() / 1000);
		
		// 结束时间
		times = ArrayUtils.add(times, currDate.getTime() / 1000);
		
		return times;
	}
	
	/**
	 * 获取真实数据，根据SQL，与分页对象
	 * <p>
	 * 
	 * @param page
	 *        分页对象，对数据进行按批次取
	 * @param sql
	 *        SQL语句
	 * @param clazz
	 *        实体bean class对象
	 * @return List< T>
	 */
	public <T> List<T> queryIoStockByCreateTime(String sql, Page page, Class<T> clazz)
	{
		try
		{
			return ocsOrderDataCleaningService.findBySalesOrderByTime(sql, page, clazz);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 定时任务，每小时执行一次，从OCS的数据中清洗数据至生产库
	 */
	public void doTask()
	{
		LOG.info(" //============================= OCS Stock Info Data Cleaning Start=================================//");
		LOG.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		logInfoUtilService.info(" OCS库存数据清洗定时任务开始");
		// 补获throwable ，防止定时器中执行内容挂掉后，不影响定时器正常执行。
		try
		{
			dataCleaningService();
		}
		catch (Throwable e)
		{
			LOG.error(Exceptions.getStackTraceAsString((Exception) e));
			logInfoUtilService.error("OCS库存数据清洗定时任务出错，请联系程序员");
		}
		
		LOG.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOG.info(" //=============================OCS Stock Info Data Cleaning End=================================//");
		logInfoUtilService.info("OCS库存数据清洗定时任务结束");
		
	}
}
