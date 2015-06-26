/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.salesstockdetails.timetask;

import java.math.BigDecimal;
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

import com.base.dao.sql.SQLHelper;
import com.base.dao.sql.SQLProperties;
import com.base.entity.main.Brand;
import com.base.service.BrandService;
import com.base.util.dwz.Page;
import com.innshine.ordermanager.entity.OrderFormInfo;
import com.innshine.ordermanager.service.OrderFormInfoService;
import com.innshine.salesstockdetails.entity.OrderItemsInfo;
import com.innshine.salesstockdetails.entity.SalesStockDetails;
import com.innshine.salesstockdetails.service.OcsOrderDataCleaningService;
import com.innshine.salesstockdetails.service.SalesStockDetailsService;
import com.innshine.stockmanager.stockinfo.entity.StockInfo;
import com.utils.DateUtils;
import com.utils.Exceptions;

/**
 * OCS销售订单数据清洗服务类， 提供基本数据清洗逻辑方法
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class OcsReturnOrderDataCleaningService
{
	/**
	 * SQL
	 */
	private static final String OCS_ORDER_RETURN_SQL = "ocs.return.order.sql";
	
	/**
	 * 所属组织编号
	 */
	//private static final long DEFAULT_ORGANIZTION_ID = 10L;
	
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
	private static final Logger LOG = LoggerFactory.getLogger(OcsReturnOrderDataCleaningService.class);
	
	/**
	 * 数据同步结束时间
	 */
	private static final String QUERY_FIELD_NAME_END_TIME = "endTime";
	
	/**
	 * 数据开始结束时间
	 */
	private static final String QUERY_FIELD_NAME_START_TIME = "startTime";
	
	/**
	 * 日记记录业务类
	 * 
	 */
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	
	/**
	 * 销售信息
	 */
	@Autowired(required = true)
	private SalesStockDetailsService salesDetailsService;
	
	@Autowired(required = true)
	private OrderFormInfoService orderFormInfoService;
	
	@Autowired(required = true)
	private OcsOrderDataCleaningService ocsOrderDataCleaningService;
	
	@Autowired
	private BrandService brandService;
	
	/**
	 * 根据查询爬虫爬取开始与结束时间，拼接查询SQL
	 * <p>
	 * 
	 * @param startTime
	 *        数据爬取开始时间
	 * @param endTime
	 *        数据爬取结束时间
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
			String querySql = getQuerySQL(times[0], times[1], OCS_ORDER_RETURN_SQL);
			
			// 初始化分页对象
			Page page = new Page();
			
			// 默认查询取值数量
			page.setNumPerPage(DEFAULT_NUM_PAGE);
			
			// 实体bean对象class
			Class<OrderItemsInfo> clazz = OrderItemsInfo.class;
			
			// 获取品牌列表
			List<Brand> brands = brandService.findAll();
			
			// 如果配置的品牌ID为空，则默认直接返回，不清楚
			if (CollectionUtils.isEmpty(brands))
			{
				return;
			}
			
			// 首次查询数据
			List<Future<Boolean>> faFutures = queryDataToExecutorClaningTask(querySql, page, clazz, brands);
			
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
					getFutureValue(queryDataToExecutorClaningTask(querySql, netxPage, clazz, brands));
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
	 * @param brands
	 * @return List< Future< Boolean>> 返回每个线程的执行结果，调用Future.get方法即可获取返回值
	 */
	public List<Future<Boolean>> queryDataToExecutorClaningTask(String querySql, Page page,
			Class<OrderItemsInfo> clazz, List<Brand> brands)
	{
		
		LOG.info("Query SQL = " + querySql);
		// 获取第1批数据清写对象
		List<OrderItemsInfo> orderItemsList = querySalesOrderByLastModifed(querySql, page, clazz);
		
		List<Future<Boolean>> futures = executorTask(orderItemsList, DateUtils.getNow(DateUtils.DEFAULT_FORMAT), brands);
		
		return futures;
	}
	
	/**
	 * 调用程执行，数据清洗过程，并返回每个线程的执行结果
	 * 
	 * @param orderItemsInfoList
	 *        需要清洗的数据列表，从原始库中查询获得。
	 * @param updateTime
	 *        数据更新时间
	 * @param brands
	 * @return List< Future< Boolean>> 返回每个线程的执行结果，调用Future.get方法即可获取返回值
	 */
	public List<Future<Boolean>> executorTask(List<OrderItemsInfo> orderItemsInfoList, String updateTime,
			List<Brand> brands)
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
			Future<Boolean> future = DEFAULT_EXECUTOR_POOL.submit(new OcsReturnOrderDataCleaningCallable(
					orderItemsInfoList.get(i), this, updateTime, brands));
			
			// 如需要线程的返回结果，则调用Future.get方法即可
			futures.add(future);
		}
		
		// 执行完成后清空查询结果集
		orderItemsInfoList.clear();
		orderItemsInfoList = null;
		
		return futures;
	}
	
	/**
	 * 数据清洗正式方法，完成对爬虫数据的真实清洗，比对、组装、最后数据录入基础数据表中
	 * 
	 * @param orderItemsInfo
	 *        爬虫数据对象
	 * @param updateTime
	 *        入库时间
	 * @param brands
	 *        品牌列表
	 */
	public void dataCleaningToBaseData(OrderItemsInfo orderItemsInfo, String updateTime, List<Brand> brands)
	{
		if (null != orderItemsInfo)
		{
			
			// 获取产品对应库存信息，如果有库存信息则继续向下执行
			List<OrderFormInfo> orderFormInfos = getStockInfo(orderItemsInfo, brands);
			
			if (CollectionUtils.isNotEmpty(orderFormInfos))
			{
				// 更新销售信息
				modiftySalesDetails(orderItemsInfo, updateTime, brands);
				
				// 更新库存信息
				modiftyStockInfo(orderItemsInfo, orderFormInfos, brands);
			}
			else
			{
				if (LOG.isDebugEnabled())
				{
					LOG.debug("There is no inventory Info orderItemsInfo = " + orderItemsInfo);
				}
			}
		}
		
	}
	
	/**
	 * 根据OCS退货单信息，更新对应库存信息
	 * 
	 * @param orderItemsInfo
	 *        OCS退货单信息
	 * @param updateTime
	 *        数量更新时间
	 * @param brands
	 * @return SalesStockDetails 返回更新好的库存信息
	 */
	private SalesStockDetails modiftySalesDetails(OrderItemsInfo orderItemsInfo, String updateTime, List<Brand> brands)
	{
		SalesStockDetails stockDetails = null;
		
		try
		{
			if (null != orderItemsInfo)
			{
				stockDetails = getSalesStockDetailsUnique(brands, orderItemsInfo);
				
				if (null != stockDetails)
				{
					stockDetails.setSalesNumber(getSalesNumber(orderItemsInfo, stockDetails));
					Double tmpValue = (stockDetails.getSalesNumber() != null && null != stockDetails
							.getAvgCurrentPrice()) ? stockDetails.getSalesNumber() * stockDetails.getAvgCurrentPrice()
							: null;
					
					BigDecimal bigDecimal = null != tmpValue ? new BigDecimal(tmpValue) : null;
					stockDetails.setSalesAmount(bigDecimal);
					
					// 更新当前库存信息
					stockDetails.setStockNumber(getStockNumber(stockDetails.getStockNumber(), orderItemsInfo.getNums()));
					
					// 修改当前数据更新时间
					stockDetails.setUpdateTime(updateTime);
					
					// 更新当前记录
					salesDetailsService.saveOrUpdate(stockDetails);
				}
				
			}
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return stockDetails;
	}
	
	/**
	 * 获取当前库存数据
	 * 
	 * @param stockInfo
	 *        库存量对象
	 * @param salesNumber
	 *        当前销售数量
	 * @return 当前库存量
	 */
	private Double getStockNumber(Double tmpStockNumber, Integer salesNumber)
	{
		Double curreStockNumber = tmpStockNumber;
		
		if (null != tmpStockNumber && salesNumber != null)
		{
			tmpStockNumber = tmpStockNumber + salesNumber.intValue();
			
			curreStockNumber = tmpStockNumber.doubleValue();
		}
		
		return curreStockNumber;
	}
	
	/**
	 * 获取销量信息
	 * 
	 * @param orderItemsInfo
	 *        从OCS中查询的退货单信息
	 * @param stockDetails
	 *        生产库中存放的当天销售信息
	 * @return
	 */
	private Double getSalesNumber(OrderItemsInfo orderItemsInfo, SalesStockDetails stockDetails)
	{
		if (null != orderItemsInfo && null != stockDetails)
		{
			Integer number = orderItemsInfo.getNums();
			
			Double salesNumber = null != stockDetails.getSalesNumber() ? stockDetails.getSalesNumber() : 0.0;
			
			if (null != number)
			{
				return salesNumber - number.doubleValue();
			}
		}
		
		return null;
	}
	
	/**
	 * 检测当前产品是否存在对应库存信息
	 * <p>
	 * 
	 * @param orderItemsInfo
	 *        OCS数据库存中查询到的销售订单对象
	 * @param brands
	 *        品牌列表
	 * @return boolean true已存在 false:不存在
	 */
	private List<OrderFormInfo> getStockInfo(OrderItemsInfo orderItemsInfo, List<Brand> brands)
	{
		
		if (null != orderItemsInfo)
		{
			String tmpBarCode = orderItemsInfo.getBarcode();
			String materialNumber = orderItemsInfo.getMaterialNumber();
			Long brandId = getBrandId(brands, orderItemsInfo);
			if (StringUtils.isNotEmpty(tmpBarCode) && null != brandId)
			{
				
				// 效验订货管理中的库存信息，有则继续往下清洗，没有则不处理
				List<OrderFormInfo> formInfos = orderFormInfoService.findByUpccodeAndBnAndBrandId(tmpBarCode,
						materialNumber, brandId);
				
				return formInfos;
				
			}
			
		}
		return null;
		
	}
	
	/**
	 * 获取从OCS品牌ID，转换为对应的系统编号
	 * 
	 * @param brands
	 *        系统配置的品牌列表
	 * @param orderItemsInfo
	 *        OCS数据对象
	 * @return brand.getExtendId --> brand.getId()
	 */
	public static Long getBrandId(List<Brand> brands, OrderItemsInfo orderItemsInfo)
	{
		if (null != orderItemsInfo && CollectionUtils.isNotEmpty(brands))
		{
			for (Brand brand : brands)
			{
				if (null != brand)
				{
					Long extendId = brand.getExtendId();
					
					if (null != extendId && extendId == orderItemsInfo.getBrandId())
					{
						return brand.getId();
					}
				}
				
			}
		}
		return null;
	}
	
	/**
	 * 更新订货单中的库存信息
	 * 
	 * 
	 * 销售信息对象
	 * 
	 * @param orderItemsInfo
	 *        从OCS数据库中查询到的退货单信息
	 * @param orderFormInfos
	 *        当前订货管理中查询到的库存信息
	 * @param brands
	 */
	private void modiftyStockInfo(OrderItemsInfo orderItemsInfo, List<OrderFormInfo> orderFormInfos, List<Brand> brands)
	{
		try
		{
			if (CollectionUtils.isNotEmpty(orderFormInfos) && null != orderItemsInfo)
			{
				// 默认每个一个UPC对应一个库存信息，故则默认取一个
				OrderFormInfo orderFormInfo = orderFormInfos.get(0);
				if (null != orderFormInfo)
				{
					// 更新品牌ID
					orderFormInfo.setBrandId(getBrandId(brands, orderItemsInfo));
					
					List<StockInfo> stockInfos = orderFormInfo.getStockInfos();
					if (CollectionUtils.isNotEmpty(stockInfos))
					{
						for (StockInfo info : stockInfos)
						{
							orderFormInfoService.cosnStockLogInfos(info, false, true);
							
							if (null != info)
							{
								
								// 获取当前库存数量
								Double tmpStockNumber = getStockNumber(info.getCurrentStockNumber(), orderItemsInfo
										.getNums());
								info.setCurrentStockNumber(null != tmpStockNumber ? tmpStockNumber.intValue() : info
										.getCurrentStockNumber());
								
								// 获取可用库数量
								Double availableStockNumber = getStockNumber(info.getAvailableStockNumber(),
										orderItemsInfo.getNums());
								info.setAvailableStockNumber(null != availableStockNumber ? availableStockNumber
										.intValue() : info.getCurrentStockNumber());
							}
						}
					}
					
					orderFormInfo.setStockInfos(stockInfos);
					
					orderFormInfoService.saveOrUpdate(orderFormInfo, true);
				}
			}
		}
		
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
	}
	
	/**
	 * 获取当前库存数据
	 * 
	 * @param integer
	 *        当前销售数量
	 * @return 当前库存量
	 */
	private Double getStockNumber(Integer tmpStockNumber, Integer returnNumber)
	{
		Double curreStockNumber = null;
		
		if (null != tmpStockNumber && returnNumber != null)
		{
			tmpStockNumber = tmpStockNumber + returnNumber;
			
			curreStockNumber = tmpStockNumber.doubleValue();
		}
		
		return curreStockNumber;
	}
	
	/**
	 * 检查在产品信息表中，是否存在当前产品记录
	 * <p>
	 * 
	 * @param brands
	 * 
	 * @param orderItemsInfo
	 *        原始数据清洗对象
	 * @return boolean true已存在 false:不存在
	 */
	private SalesStockDetails getSalesStockDetailsUnique(List<Brand> brands, OrderItemsInfo orderItemsInfo)
	{
		
		if (null != orderItemsInfo)
		{
			String tmpBarCode = orderItemsInfo.getBarcode();
			String tmpSalesTime = orderItemsInfo.getLastModified();
			String tmpProductId = orderItemsInfo.getProductId();
			String materialNumber = orderItemsInfo.getMaterialNumber();
			Long brandId = getBrandId(brands, orderItemsInfo);
			if (StringUtils.isNotEmpty(tmpBarCode))
			{
				
				SalesStockDetails salesStockDetails = salesDetailsService.findByUniqueOrParams(tmpBarCode,
						tmpProductId, tmpSalesTime, brandId, materialNumber);
				
				return salesStockDetails;
				
			}
			
		}
		return null;
		
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
	public <T> List<T> querySalesOrderByLastModifed(String sql, Page page, Class<T> clazz)
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
		LOG.info(" //============================= OCS Return Orders Info Data Cleaning Start=================================//");
		LOG.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		logInfoUtilService.info("OCS退货单清洗定时任务开始");
		// 补获throwable ，防止定时器中执行内容挂掉后，不影响定时器正常执行。
		try
		{
			dataCleaningService();
		}
		catch (Throwable e)
		{
			LOG.error(Exceptions.getStackTraceAsString((Exception) e));
			logInfoUtilService.error("OCS退货单清洗定时任务出错，请联系程序员");
		}
		
		LOG.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOG.info(" //=============================OCS Return Orders Info Data Cleaning End=================================//");
		logInfoUtilService.info("OCS退货单清洗定时任务结束");
	}
}
