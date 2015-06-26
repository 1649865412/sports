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
import com.utils.validator.ValidatorsDataUtils;

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
public class OcsSalesOrderDataCleaningService
{
	/**
	 * SQL key
	 */
	private static final String OCS_ORDER_SALES_SQL = "ocs.sales.order.sql";
	
	/**
	 * 所属组织编号 NewBalance
	 */
	private static final long DEFAULT_ORGANIZTION_ID = 10L;;
	
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
	 * 日记记录业务类
	 * 
	 */
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OcsSalesOrderDataCleaningService.class);
	
	/**
	 * 数据同步结束时间
	 */
	private static final String QUERY_FIELD_NAME_END_TIME = "endTime";
	
	/**
	 * 数据开始结束时间
	 */
	private static final String QUERY_FIELD_NAME_START_TIME = "startTime";
	
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
			String querySql = getQuerySQL(times[0], times[1], OCS_ORDER_SALES_SQL);
			
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
			Future<Boolean> future = DEFAULT_EXECUTOR_POOL.submit(new OcsSalesOrderDataCleaningCallable(
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
	 * 数据清洗正式方法，完成对爬虫数据的真实清洗，比对、组装、最后数据录入基础数据表中，productinfo、salesDetails
	 * 
	 * @param orderItemsInfo
	 *        爬虫数据对象
	 * @param updateTime
	 *        入库时间
	 * @param brands
	 */
	public void dataCleaningToBaseData(OrderItemsInfo orderItemsInfo, String updateTime, List<Brand> brands)
	{
		if (null != orderItemsInfo && !checkStockInfo(orderItemsInfo, brands))
		{
			addSalesStockDetails(orderItemsInfo, updateTime, brands);
		}
	}
	
	/**
	 * 构造产品信息并录入新的产品信息
	 * 
	 * @param orderItemsInfo
	 *        爬虫数据对象
	 * @param brands
	 * @param productInfoService2
	 */
	private void addSalesStockDetails(OrderItemsInfo orderItemsInfo, String updateTime, List<Brand> brands)
	{
		if (null != orderItemsInfo)
		{
			try
			{
				SalesStockDetails info = new SalesStockDetails();
				info.setUpccode(orderItemsInfo.getBarcode());
				info.setPlatformId(orderItemsInfo.getProductId());
				info.setUpdateTime(updateTime);
				info.setMaterialNumber(orderItemsInfo.getMaterialNumber());
				info.setSalesTime(orderItemsInfo.getLastModified());
				info.setAvgCurrentPrice(orderItemsInfo.getSalePrice());
				info.setSalesAmount(null != orderItemsInfo.getAmount() ? new BigDecimal(orderItemsInfo.getAmount())
						: null);
				info.setSalesNumber(null != orderItemsInfo.getNums() ? orderItemsInfo.getNums().doubleValue() : null);
				info.setBrandId(OcsReturnOrderDataCleaningService.getBrandId(brands, orderItemsInfo));
				OrderFormInfo formInfo = setStoreInfo(info);
				
				info = checkSalesInfo(info);
				
				try
				{
					Thread.sleep(50);
				}
				catch (InterruptedException e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
					
				}
				
				if (null != info)
				{
					salesDetailsService.saveOrUpdate(info);
					
					modiftyStockInfo(info, formInfo);
				}
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
	}
	
	private SalesStockDetails checkSalesInfo(SalesStockDetails info)

	{
		if (null != info)
		{
			if (!ValidatorsDataUtils.isUnique(info, salesDetailsService))
			{
				// 默认支持批量修改
				if (com.innshine.common.Constants.DEFAULT_BATCH_MODIFTY)
				{
					SalesStockDetails olySalesDetails = salesDetailsService.findByUnique(info);
					if (null != olySalesDetails)
					{
						salesDetailsService.deleteByUnique(olySalesDetails);
					}
				}
				else
				{
					LOG.warn("Data Already Exists ! SalesDetails Info  =  " + info);
					
					// 删除已存在数据
					info = null;
				}
			}
		}
		return info;
	}
	
	/**
	 * 更新订货单中的库存信息
	 * 
	 * 
	 * @param salesStockDetails
	 *        销售信息对象
	 * @param formInfo
	 *        订货信息实体bean
	 */
	private void modiftyStockInfo(SalesStockDetails salesStockDetails, OrderFormInfo formInfo)
	{
		try
		{
			if (null != salesStockDetails && null != formInfo)
			{
				List<StockInfo> stockInfos = formInfo.getStockInfos();
				if (CollectionUtils.isNotEmpty(stockInfos))
				{
					for (StockInfo info : stockInfos)
					{
						orderFormInfoService.cosnStockLogInfos(info, false, true);
						
						if (null != info)
						{
							// 当前库存
							Double tmpStockNumber = getStockNumber(info.getCurrentStockNumber(), salesStockDetails
									.getSalesNumber());
							info.setCurrentStockNumber(null != tmpStockNumber ? tmpStockNumber.intValue() : info
									.getCurrentStockNumber());
							// 可用库存
							Double tmpAvailableStockNumber = getStockNumber(info.getAvailableStockNumber(),
									salesStockDetails.getSalesNumber());
							info.setAvailableStockNumber(null != tmpAvailableStockNumber ? tmpAvailableStockNumber
									.intValue() : info.getAvailableStockNumber());
						}
					}
				}
				
				formInfo.setStockInfos(stockInfos);
				
				orderFormInfoService.saveOrUpdate(formInfo,true);
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
	 * @param info
	 *        销售信息对象
	 * @return OrderFormInfo
	 */
	private OrderFormInfo setStoreInfo(SalesStockDetails info)
	{
		OrderFormInfo orderFormInfo = null;
		if (null != info)
		{
			List<OrderFormInfo> orderFormInfos = orderFormInfoService.findByUpccodeAndBnAndBrandId(info.getUpccode(),
					info.getMaterialNumber(), info.getBrandId());
			
			if (CollectionUtils.isNotEmpty(orderFormInfos))
			{
				orderFormInfo = orderFormInfos.get(0);
				
				if (null != orderFormInfo)
				{
					// 订货数量
					info.setOrderNumber(null != orderFormInfo.getOrderNumber() ? orderFormInfo.getOrderNumber()
							.doubleValue() : null);
					
					// 到货数量
					info.setArriveNumber(null != orderFormInfo.getArrivalNumber() ? orderFormInfo.getArrivalNumber()
							.doubleValue() : null);
					
					// 预计到货时间
					info.setPredictArriveTime(orderFormInfo.getPredictArriveTime());
					
					// 库存量
					List<StockInfo> stockInfos = orderFormInfo.getStockInfos();
					
					if (CollectionUtils.isNotEmpty(stockInfos))
					{
						StockInfo stockInfo = stockInfos.get(0);
						if (null != stockInfo)
						{
							info.setStockNumber(getStockNumber(stockInfo.getCurrentStockNumber(), info.getSalesNumber()));
						}
						
					}
					
				}
			}
		}
		
		return orderFormInfo;
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
	private Double getStockNumber(Integer tmpStockNumber, Double salesNumber)
	{
		Double curreStockNumber = null;
		
		if (null != tmpStockNumber && salesNumber != null)
		{
			tmpStockNumber = tmpStockNumber - salesNumber.intValue();
			
			curreStockNumber = tmpStockNumber.doubleValue();
		}
		
		return curreStockNumber;
	}
	
	/**
	 * 检测当前产品是否存在对应库存信息
	 * <p>
	 * 
	 * @param orderItemsInfo
	 *        OCS数据库存中查询到的销售订单对象
	 * @param brands
	 * @return boolean true已存在 false:不存在
	 */
	private boolean checkStockInfo(OrderItemsInfo orderItemsInfo, List<Brand> brands)
	{
		
		boolean isFlag = false;
		if (null != orderItemsInfo)
		{
			String tmpBarCode = orderItemsInfo.getBarcode();
			String materialNumber = orderItemsInfo.getMaterialNumber();
			Long brandId = OcsReturnOrderDataCleaningService.getBrandId(brands, orderItemsInfo);
			if (StringUtils.isNotEmpty(tmpBarCode) && null != brandId)
			{
				
				// 效验订货管理中的库存信息，有则继续往下清洗，没有则不处理
				List<OrderFormInfo> formInfos = orderFormInfoService.findByUpccodeAndBnAndBrandId(tmpBarCode,
						materialNumber, brandId);
				
				if (CollectionUtils.isEmpty(formInfos))
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug("There is no inventory Info orderItemsInfo = " + orderItemsInfo);
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
		LOG
				.info(" //============================= OCS Sales Orders Info Data Cleaning Start=================================//");
		LOG.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		logInfoUtilService.info("OCS的数据清洗数据定时任务开始");
		
		// 补获throwable ，防止定时器中执行内容挂掉后，不影响定时器正常执行。
		try
		{
			dataCleaningService();
		}
		catch (Throwable e)
		{
			LOG.error(Exceptions.getStackTraceAsString((Exception) e));
			logInfoUtilService.error("OCS的数据清洗数据定时任务出错,请联系程序员");
			
		}
		
		LOG.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOG
				.info(" //=============================OCS Sales Orders Info Data Cleaning End=================================//");
		logInfoUtilService.info("OCS的数据清洗数据定时任务结束");
		
	}
}
