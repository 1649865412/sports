/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.nbsalesdetails.timetask;

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

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.utils.LogInfoUtilService;

import com.base.dao.sql.SQLHelper;
import com.base.dao.sql.SQLProperties;
import com.base.util.dwz.Page;
import com.innshine.nbsalesdetails.entity.NbSalesDetails;
import com.innshine.nbsalesdetails.service.NbSalesDetailsService;
import com.innshine.salesstockdetails.entity.SalesStockDetails;
import com.innshine.salesstockdetails.service.SalesStockDetailsService;
import com.utils.CalendarUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.excel.ExcelToBeanUtils;
import com.utils.validator.ValidatorsDataUtils;

/**
 * 数据清洗服务类， 提供基本数据清洗逻辑方法
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @author auto 时间 2014年7月9日 下午2:15:52
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class NBSalesInfoDataCleaningService
{
	/**
	 * 清洗的SQL
	 */
	private static final String NB_SALES_DETAILS_DATACLEASING_SQL = "nb.sales.details.datacleasing.sql";
	
	/**
	 * ，如销售时间段大于1天，那么说明这段时间的销售数量是模糊的，不准确。0 :表示准确，1:表示模糊
	 */
	private static final int BLUR_DATA = 1;
	
	/**
	 * 如销售时间段大于1天，那么说明这段时间的销售数量是模糊的，不准确。0 :表示准确，1:表示模糊
	 */
	private static final int NOT_BLUR_DATA = 0;
	
	/**
	 * 默认按小时查询
	 */
	private static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	
	/**
	 * 星号
	 */
	// private static final String ASTERISK = "*";
	
	/**
	 * 数据类型，爬虫数据清洗，用于标识销售记录
	 */
	public static final int DEFAULT_MONITORY_TYPE = 2;
	
	/**
	 * 默认线程池数量
	 */
	private static final int DEFAULT_POOL_SIZE = 20;
	
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
	private static final Logger LOG = LoggerFactory.getLogger(NBSalesInfoDataCleaningService.class);
	
	/**
	 * 数据同步结束时间
	 */
	private static final String SYNDATE_FIELD_NAME_END_TIME = "endTime";
	
	/**
	 * 数据开始结束时间
	 */
	private static final String SYNDATE_FIELD_NAME_START_TIME = "startTime";
	
	/**
	 * 销售信息
	 */
	@Autowired(required = true)
	private NbSalesDetailsService nbSalesDetailsService;
	
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
	public String getQuerySQL(String startTime, String endTime, String cfgKey) throws Exception
	{
		String sql = SQLProperties.getInstance().getSql(cfgKey);
		SQLHelper sqlHelper = new SQLHelper(sql);
		
		// 构造参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SYNDATE_FIELD_NAME_START_TIME, startTime);
		params.put(SYNDATE_FIELD_NAME_END_TIME, endTime);
		
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
	 * * @param isForceRefresh 是否是强制刷新
	 * 
	 * @throws Exception
	 */
	public synchronized void dataCleaningService(boolean isForceRefresh) throws Exception
	{
		// 当前时间往前推一天时间范围 如：2014-05-19 ~ 2014-05-20
		String[] times = getQueryTime(isForceRefresh);
		
		if (ArrayUtils.isNotEmpty(times) && times.length >= 2)
		{
			// 获取查询SQL
			String querySql = getQuerySQL(times[0], times[1], NB_SALES_DETAILS_DATACLEASING_SQL);
			
			// 初始化分页对象
			Page page = new Page();
			
			// 默认查询取值数量
			page.setNumPerPage(DEFAULT_NUM_PAGE);
			
			// 实体bean对象class
			Class<NbSalesDetails> clazz = NbSalesDetails.class;
			
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
	public List<Future<Boolean>> queryDataToExecutorClaningTask(String querySql, Page page, Class<NbSalesDetails> clazz)
	{
		
		LOG.info("Query SQL = " + querySql);
		
		// 获取第1批数据清写对象
		List<NbSalesDetails> nbSalesList = queryNbSalesInfoByUpdateTime(querySql, page, clazz);
		
		List<Future<Boolean>> futures = executorTask(nbSalesList, DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		
		return futures;
	}
	
	/**
	 * 调用程执行，数据清洗过程，并返回每个线程的执行结果
	 * 
	 * @param nbSalesDetails
	 *        需要清洗的数据列表，从原始库中查询获得。
	 * @param updateTime
	 *        清洗至生产库，记录的数据更新时间
	 * @return List< Future< Boolean>> 返回每个线程的执行结果，调用Future.get方法即可获取返回值
	 */
	public List<Future<Boolean>> executorTask(List<NbSalesDetails> nbSalesDetails, String updateTime)
	{
		// 记录执行结果
		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
		if (null == nbSalesDetails || nbSalesDetails.isEmpty())
		{
			return futures;
		}
		
		// 向线程池中添加需要执行的线程队列
		for (int i = 0; i < nbSalesDetails.size(); i++)
		{
			Future<Boolean> future = DEFAULT_EXECUTOR_POOL.submit(new NBSalesInfoDataCleaningCallable(nbSalesDetails
					.get(i), this, updateTime));
			
			// 如需要线程的返回结果，则调用Future.get方法即可
			futures.add(future);
		}
		
		// 执行完成后清空查询结果集
		nbSalesDetails.clear();
		nbSalesDetails = null;
		
		return futures;
	}
	
	/**
	 * 数据清洗正式方法，完成对欧莱雅销售数据的真实清洗，比对、组装、最后数据录入基础数据表中，productinfo、salesDetails
	 * 
	 * @param nbSalesDetails
	 *        爬虫数据对象
	 * @param updateTime
	 *        入库时间
	 */
	public void dataCleaningToBaseData(NbSalesDetails nbSalesDetails, String updateTime)
	{
		// 处理销售信息
		dealSalesDetails(nbSalesDetails, updateTime);
	}
	
	/**
	 * 处理销售详情，根据基础产品信息
	 * 
	 * @param nbSalesDetails
	 * 
	 * @param olyProductInfo
	 *        产品基础信息数据对象
	 * @param updateTime
	 *        更新时间FFF
	 * @throws Exception
	 */
	private void dealSalesDetails(NbSalesDetails nbSalesDetails, String updateTime)
	{
		separatorSalesDatas(nbSalesDetails, updateTime);
	}
	
	/**
	 * 分拆查询到的销售数据
	 * 
	 * @param olySalesDetails2
	 *        销售数据列表
	 * @param updateTime
	 *        当前数据更新时间
	 */
	private void separatorSalesDatas(NbSalesDetails nbSalesDetails, String updateTime)
	{
		
		if (null != nbSalesDetails)
		{
			List<SalesStockDetails> salesDetails = separtorMarketTime(nbSalesDetails, updateTime);
			
			addSalesDetails(salesDetails);
			
		}
		
	}
	
	/**
	 * 先效验是否唯一，再对销售信息进行入库操作
	 * 
	 * @param salesDetails
	 */
	private void addSalesDetails(List<SalesStockDetails> salesDetails)
	{
		if (null != salesDetails && !salesDetails.isEmpty())
		{
			try
			{
				List<SalesStockDetails> deleteList = new ArrayList<SalesStockDetails>();
				for (int i = salesDetails.size() - 1; i >= 0; i--)
				{
					SalesStockDetails info = salesDetails.get(i);
					
					if (!ValidatorsDataUtils.isUnique(info, salesDetailsService))
					{
						// 默认支持批量修改
						if (com.innshine.common.Constants.DEFAULT_BATCH_MODIFTY)
						{
							SalesStockDetails salesDetailsInfo = salesDetailsService.findByUnique(info);
							if (null != salesDetailsInfo)
							{
								deleteList.add(salesDetailsInfo);
							}
						}
						else
						{
							LOG.warn("Data Already Exists ! SalesDetails Info  =  " + info);
							
							// 删除已存在数据
							salesDetails.remove(i);
						}
					}
					
				}
				
				// 先删除后添加
				salesDetailsService.deleteByUnique(deleteList);
				
				try
				{
					// 休眠50毫秒，以防止delte、insert操作过快导致数据表锁未释放，造成等待释放资源超时问题。
					Thread.sleep(50);
				}
				catch (InterruptedException e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
				}
				
				salesDetailsService.saveOrUpdate(salesDetails);
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			
		}
		
	}
	
	/**
	 * 根据销售日期，构造销售记录
	 * 
	 * @param nbSalesDetails
	 *        根据产品编号+时间段查询到的销售数据列表
	 * @param tmOlyProductInfo
	 *        产品基础数据对象
	 * @param updateTime
	 *        数据更新时间
	 */
	public List<SalesStockDetails> separtorMarketTime(NbSalesDetails nbSalesDetails, String updateTime)
	{
		
		List<SalesStockDetails> salesDetailsList = new ArrayList<SalesStockDetails>();
		String marketStartTime = nbSalesDetails.getMarketStartTime();
		String marketEndTime = nbSalesDetails.getMarketEndTime();
		Integer salesNumber = null != nbSalesDetails.getSalesNumber() ? nbSalesDetails.getSalesNumber().intValue()
				: null; // 销量
		
		// 获取两个日期之间相差的天数
		Long day = CalendarUtils.getDays(marketEndTime, marketStartTime);
		
		if (null != day)
		{
			if (day > 0)
			{
				// 开始第1天也需要计算在内，估需要+1天
				int dayNumber = day.intValue() + 1;
				for (int i = 0; i < dayNumber; i++)
				{
					// 日销量
					Integer tmpDaySalesNumber = null;
					
					if (null != salesNumber)
					{
						// 如果销售时间天数大于销售数量，则默认销售数据从0~day 默认为1个
						if (day > salesNumber)
						{
							if (i < salesNumber)
							{
								tmpDaySalesNumber = 1;
							}
						}
						else
						{
							int avgSalesNumber = salesNumber / dayNumber;
							
							// 如果是最后一个除不尽的情况下，会进行补操作
							if (i == dayNumber - 1)
							{
								// 如果求得平均值后的总和小于当前销量，则会对最后一条记录进行补操作
								if (avgSalesNumber * day < salesNumber)
								{
									avgSalesNumber = avgSalesNumber + (salesNumber - avgSalesNumber * dayNumber);
								}
							}
							
							tmpDaySalesNumber = avgSalesNumber;
						}
					}
					
					salesDetailsList.add(consSaelsDetailsObj(nbSalesDetails, marketStartTime, dayNumber, i, updateTime,
							tmpDaySalesNumber, BLUR_DATA));
				}
				
			}
			else if (day <= 0)
			{
				
				salesDetailsList.add(consSaelsDetailsObj(nbSalesDetails, marketStartTime, day.intValue(), 0,
						updateTime, salesNumber, NOT_BLUR_DATA));
			}
			
		}
		
		return salesDetailsList;
	}
	
	/**
	 * 构造基础库中的销售信息对象
	 * 
	 * @param nbSalesDetails
	 *        欧莱雅导入的销售数据列表
	 * @param tmOlyProductInfo
	 *        欧莱雅导入的基础数据对象
	 * @param marketStartTime
	 *        销售开始时间
	 * @param day
	 *        销售结束时间 - 销售开始时间 = 相距天数
	 * @param i
	 *        当前日期加数
	 * @param updateTime
	 *        当前数据更新时间，也可认为是同步时间
	 * @param blur
	 *        如销售时间段大于1天，那么说明这段时间的销售数量是模糊的，不准确。0 :表示准确，1:表示模糊
	 * @param daySalesNumber
	 *        日销量
	 */
	public SalesStockDetails consSaelsDetailsObj(NbSalesDetails nbSalesDetails, String marketStartTime, int dayNumber,
			int i, String updateTime, Integer daySalesNumber, int blur)
	{
		SalesStockDetails salesDetails = new SalesStockDetails();
		
		if (null != nbSalesDetails)
		{
			
			ExcelToBeanUtils.reflectSetValue(ExcelToBeanUtils.refSupperFieldsName(NbSalesDetails.class),
					nbSalesDetails, salesDetails);
			
			BigDecimal salesAmount = nbSalesDetails.getSalesAmount();
			Double salesNumber = nbSalesDetails.getSalesNumber();
			if (nbSalesDetails.getAvgCurrentPrice() == null)
			{
				nbSalesDetails.setAvgCurrentPrice(null == salesAmount || salesNumber == null ? null : salesAmount
						.doubleValue()
						/ salesNumber);
			}
			
			salesDetails.setId(null);
			
			salesDetails.setSalesNumber((null == daySalesNumber ? null : daySalesNumber.doubleValue()));
			Double tmpValue = ((null == daySalesNumber || null == nbSalesDetails.getAvgCurrentPrice()) ? null
					: daySalesNumber.doubleValue() * nbSalesDetails.getAvgCurrentPrice());
			salesDetails.setSalesAmount(null == tmpValue ? null : new BigDecimal(tmpValue));
			salesDetails.setUpdateTime(updateTime);
			salesDetails.setSalesTime(setSalesTime(marketStartTime, i));
		}
		
		return salesDetails;
	}
	
	/**
	 * 设置销售时间
	 * 
	 * @param marketStartTime
	 *        销售开始时间
	 * @param i
	 *        需要增加的天数
	 * @return 计算后的日期，默认+1
	 */
	private String setSalesTime(String marketStartTime, int i)
	{
		
		if (i == 0)
		{
			return marketStartTime;
		}
		else if (i > 0)
		{
			Date date = DateUtils.toDate(marketStartTime, DateUtils.SIMPLE_DEFAULT_FORMAT);
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIME_ZONE_GMT));
			calendar.setTime(date);
			
			// 对日期进行加减操作
			calendar.add(Calendar.DATE, +i);
			
			return DateUtils.format(calendar.getTime(), DateUtils.SIMPLE_DEFAULT_FORMAT);
		}
		
		return null;
	}
	
	/**
	 * 获取单品当前时间段内对应的销售记录
	 * <p>
	 * 
	 * @return List< OlySalesDetails >
	 */
	// private List<OlySalesDetails> getSalesInfos()
	// {
	// String[] queryTimes = getQueryTime();
	//
	// if (ArrayUtils.isNotEmpty(queryTimes) && queryTimes.length >= 2)
	// {
	// try
	// {
	// return olySalesDetailsService.findByUpdateTime(queryTimes[0],
	// queryTimes[1], null);
	// }
	// catch (Exception e)
	// {
	// LOG.error(Exceptions.getStackTraceAsString(e));
	// }
	// }
	//
	// return null;
	// }
	
	/**
	 * 根据线程大小，对清洗的数据进行分组
	 * <p>
	 * 
	 * @param cleaningItemPrices
	 *        清洗数据列表
	 * @return List< List< DataCleaningItemPrices >> 清洗后的数据列表
	 */
	// private Map<String, List<DataCleaningItemPrices>>
	// separatorDatasByPoolSize(
	// List<DataCleaningItemPrices> cleaningItemPrices)
	// {
	// Map<String, List<DataCleaningItemPrices>> map = new HashMap<String,
	// List<DataCleaningItemPrices>>();
	// if (null != cleaningItemPrices && cleaningItemPrices.size() > 0)
	// {
	// int listSize = cleaningItemPrices.size();
	//
	// // 分组数量
	// int groupCount = listSize % DEFAULT_POOL_SIZE > 0 ? listSize /
	// DEFAULT_POOL_SIZE + 1 : listSize
	// / DEFAULT_POOL_SIZE;
	//
	// List<DataCleaningItemPrices> tmpList = new
	// ArrayList<DataCleaningItemPrices>(groupCount);
	// for (int i = 0; i < cleaningItemPrices.size(); i++)
	// {
	// if (i >= groupCount || i >= cleaningItemPrices.size() - 1)
	// {
	// map.put(String.valueOf(i), new
	// ArrayList<DataCleaningItemPrices>(tmpList));
	//
	// // 添加完成后清空集合
	// tmpList.clear();
	//
	// }
	//
	// tmpList.add(cleaningItemPrices.get(i));
	// }
	// }
	// return map;
	// }
	//
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
	private String[] getQueryTime(boolean isForceRefresh)
	{
		String[] times = new String[] {};
		
		// 住前推一天时间
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIME_ZONE_GMT));
		calendar.setTime(new Date());
		
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
		times = ArrayUtils.add(times, DateUtils.format(calendar.getTime(), YYYY_MM_DD_HH));
		
		// 结束时间
		times = ArrayUtils.add(times, DateUtils.getNow(YYYY_MM_DD_HH));
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
	public <T> List<T> queryNbSalesInfoByUpdateTime(String sql, Page page, Class<T> clazz)
	{
		try
		{
			return nbSalesDetailsService.findByUpdateTime(sql, page, clazz);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 定时任务，每小时执行一次，从NewBalance导入的数据中清洗数据至生产库
	 */
	public void doDataCleaningTask()
	{
		LOG
				.info("//=============================NewBalance Sales Info Data Cleaning Start=================================//");
		LOG.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		logInfoUtilService.info("NewBalance数据清洗定时作业开始");
		
		// 补获throwable ，防止定时器中执行内容挂掉后，不影响定时器正常执行。
		try
		{
			dataCleaningService();
		}
		catch (Throwable e)
		{
			LOG.error(Exceptions.getStackTraceAsString((Exception) e));
			logInfoUtilService.error("NewBalance数据清洗定时作业出错");
		}
		
		LOG.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOG
				.info("//=============================NewBalance Sales Info Data Cleaning End=================================//");
		logInfoUtilService.info("NewBalance数据清洗定时作业结束");
		
	}
}
