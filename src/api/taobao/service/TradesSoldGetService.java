package api.taobao.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.taobao.constant.TradeConstants;
import api.taobao.dao.TaobaoTradeDao;
import api.taobao.entity.LogisticsTag;
import api.taobao.entity.OCSOrdersInfo;
import api.taobao.entity.Order;
import api.taobao.entity.ServiceOrder;
import api.taobao.entity.Shop;
import api.taobao.entity.Trade;
import api.taobao.entity.TradePromotionDetail;
import api.taobao.utils.ExcelUtils;
import api.taobao.utils.OCSSyncOrdersUtils;
import api.utils.LogInfoUtilService;

import com.base.util.MailSenderService;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.taobaoemailmanager.service.TradeSendEmailInfoService;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;
import com.utils.DateUtils;
import com.utils.Exceptions;

/**
 * 获取店铺交易信息服务类<code>TaobaoTradeService.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */

@Service
@Transactional
public class TradesSoldGetService
{
	
	private static final String YYYY_M_MDD_H_HMMSS = "yyyyMMddHHmmss";
	
	private static final String UNDER_LINE = "_";
	
	/**
	 * 默认页码
	 */
	private static final long DEFAULT_PAGE_NO = 1L;
	
	private static final String YYYY_MM_DD_HH_00_00 = "yyyy-MM-dd HH:00:00";
	private static final String YYYY_MM_DD_00_00_00 = "yyyy-MM-dd 00:00:00";
	
	private static final Logger LOG = LoggerFactory.getLogger(TradesSoldGetService.class);
	
	/**
	 * 默认每页条数
	 */
	public static long TRADES_SOLD_GET_REQUEST_PAGE_SIZE = 40;
	
	@Autowired(required = true)
	private ShopService shopService;
	
	@Autowired
	private OCSService ocsService;
	
	@Autowired
	private TaobaoTradeDao taobaoTradeDao;
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@Autowired
	private TradeSendEmailInfoService emailInfoService;
	
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.innshine.taobao.service.TTradeService#get(java.lang.Long)
	 */

	public Trade get(Long id)
	{
		return taobaoTradeDao.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.innshine.taobao.service.TTradeService#delete(java.lang.Long)
	 */

	public void delete(Long id)
	{
		taobaoTradeDao.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.taobao.service.TTradeService#findAll(com.base.util.dwz.Page)
	 */

	public List<Trade> findAll(Page page)
	{
		setOrderField(page);
		
		org.springframework.data.domain.Page<Trade> springDataPage = taobaoTradeDao.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	private void setOrderField(Page page)
	{
		if (null != page)
		{
			page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
			page.setOrderField("updateTime");
		}
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.taobao.service.TTradeService#findByExample(org.springframework
	 * .data.jpa.domain.Specification, com.base.util.dwz.Page)
	 */
	public List<Trade> findByExample(Specification<Trade> specification, Page page)
	{
		setOrderField(page);
		org.springframework.data.domain.Page<Trade> springDataPage = taobaoTradeDao.findAll(specification, PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.taobao.service.TTradeService#findByExample(org.springframework
	 * .data.jpa.domain.Specification, com.base.util.dwz.Page)
	 */
	public List<Trade> findByExample(Specification<Trade> specification)
	{
		if (null != specification)
		{
			return taobaoTradeDao.findAll(specification);
		}
		
		return null;
	}
	
	public void saveOrUpdate(Trade trade)
	{
		if (null != trade)
		{
			taobaoTradeDao.save(trade);
		}
	}
	
	public void saveOrUpdate(List<Trade> trades)
	{
		if (null != trades)
		{
			taobaoTradeDao.save(trades);
		}
	}
	
	public List<Trade> findByUpdateTimeBetween(String startTime, String endTime)
	{
		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime))
		{
			return taobaoTradeDao.findByUpdateTimeBetween(startTime, endTime);
		}
		
		return null;
	}
	
	public List<Trade> findByUpdateTimeBetweenAndBrandId(String startTime, String endTime, Long brandId)
	{
		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime))
		{
			return taobaoTradeDao.findByUpdateTimeBetweenAndBrandId(startTime, endTime, brandId);
		}
		
		return null;
	}
	
	public List<Trade> findByUpdateTimeBetweenAndStatusIn(String startTime, String endTime, List<String> statusList,
			Long brandId)
	{
		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)
				&& CollectionUtils.isNotEmpty(statusList))
		{
			return taobaoTradeDao.findByUpdateTimeBetweenAndStatusInAndBrandId(startTime, endTime, statusList, brandId);
		}
		
		return null;
	}
	
	public void getTaobaoTradeData()
	{
		Date[] queryTime = getQueryTime();
		
		// 请求淘宝，获取当前时间段内，店铺交易记录
		// getTradeInfo(queryTime);
		
		// 获取店铺淘宝订单
		getTradeAllInfo(queryTime);
	}
	
	/**
	 * 检查一段时间内是否存在差异数据，有则生成差异excel文件，并发送Email，故则相反不处理
	 */
	public void queryDiffDataAndGeneratorExcel()
	{
		// new String[]{"2015-01-06 10:00:27","2015-01-06 10:50:27"};
		String[] queryTime = getDifferenceQueryTime();
		
		Date[] dates = getQueryTime();
		
		List<Shop> shops = shopService.findAll();
		
		if (ArrayUtils.isNotEmpty(queryTime) && queryTime.length >= 2 && CollectionUtils.isNotEmpty(shops))
		{
			for (Shop shop : shops)
			{
				if (null != shop)
				{
					logInfoUtilService.info("OCS与淘宝交易信息比对开始，店铺名：" + shop.getTitle());
					
					// 比对OCS与淘宝交易订单
					List<Trade> trades = compareOCSAndTaobaoTradesInfo(queryTime[0], queryTime[1], dates, shop);
					
					if (CollectionUtils.isNotEmpty(trades))
					{
						logInfoUtilService.info("本次比对差异数量：" + trades.size() + ",店铺：" + shop.getTitle());
						Workbook workbook = exportExcel(trades);
						
						String[] fileInfo = ExcelUtils.saveFile(workbook, getFileName(shop));
						
						// 构造发送邮件对象
						Thread thread = new Thread(new TaoBaoTradesDiffInfoSendEmailThread(fileInfo, DateUtils
								.format(dates[0])
								+ "~" + DateUtils.format(dates[1]), mailSenderService, emailInfoService, shop
								.getBrandId()));
						
						thread.start();
						
						logInfoUtilService.info("开始同步订单至OCS......");
						
						// 通知OCS同步订单，记录同步失败订单以备后重做使用
						List<Trade> syncFailedList = OCSSyncOrdersUtils.syncOrderToOcs(trades, shop);
						
						// 处理同步失败信息
						dealSyncOrderFailedData(syncFailedList, shop);
						
						logInfoUtilService.info("完成同步订单至OCS......");
					}
					
					logInfoUtilService.info("OCS与淘宝交易信息比对结束，店铺名：" + shop.getTitle());
				}
			}
			
		}
	}
	
	/**
	 *生成文件名
	 * <p>
	 * 
	 * @param shop
	 * @return
	 */
	private String getFileName(Shop shop)
	{
		String tempFileName = "";
		
		if (null != shop)
		{
			String brand = shop.getBrand();
			String title = shop.getTitle();
			Long brandId = shop.getBrandId();
			String format = DateUtils.getNow(YYYY_M_MDD_H_HMMSS);
			
			if (StringUtils.isNotEmpty(brand))
			{
				tempFileName = brand + UNDER_LINE + format;
			}
			
			if (StringUtils.isEmpty(tempFileName) && StringUtils.isNotEmpty(title))
			{
				tempFileName = title + UNDER_LINE + format;
			}
			
			if (StringUtils.isEmpty(tempFileName) && null != brandId)
			{
				tempFileName = brandId + UNDER_LINE + format;
			}
		}
		
		return StringUtils.isNotEmpty(tempFileName) ? tempFileName : String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * 对比OCS数据与淘宝数据
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dates
	 * @param shop
	 * @return
	 */
	private List<Trade> compareOCSAndTaobaoTradesInfo(String startTime, String endTime, Date[] dates, Shop shop)
	{
		// 记录差异数据
		List<Trade> dataDifferenceList = new ArrayList<Trade>();
		
		// Date date1 = new Date(1413357583L * 1000);
		// Date date2 = new Date(1413358383L * 1000);
		// dates = new Date[] { date1, date2 };
		
		// 获取OCS该段范围内的订单详情
		List<OCSOrdersInfo> ordersInfos = ocsService.queryOCSOrdersInfoByTime(dates[0], dates[1], shop.getShopId(),
				OCSOrdersInfo.class);
		
		// 淘宝获取的交易信息
		List<Trade> trades = findByUpdateTimeBetweenAndStatusIn(startTime, endTime,
				getTaobaoTradeStatus(TradeConstants.TRADE_STATUS), shop.getBrandId());
		
		// 对比OCS数据与淘宝数据
		if (null != ordersInfos && null != trades)
		{
			logInfoUtilService.info("本次比对获取OCS订单数量：" + ordersInfos.size() + ",时间范围：" + DateUtils.format(dates[0]) + "~"
					+ DateUtils.format(dates[1]));
			
			logInfoUtilService.info("本次比对获取淘宝交易订单数量：" + trades.size() + ",时间范围：" + DateUtils.format(dates[0]) + "~"
					+ DateUtils.format(dates[1]));
			// 如果OCS数据小于淘宝交易记录，则需要对比详情
			// if (ordersInfos.size() < trades.size())
			// {
			// 转换OCS订单，提高对比效率
			Map<String, OCSOrdersInfo> customOcsTradeInfoMap = customOcsTradeIds(ordersInfos);
			
			if (MapUtils.isEmpty(customOcsTradeInfoMap))
			{
				return trades;
			}
			
			for (Trade trade : trades)
			{
				if (checkNextCompare(trade))
				{
					// 检测OCS中是否存在对应淘宝交易订单信息
					if (!isExistTrade(customOcsTradeInfoMap, trade))
					{
						dataDifferenceList.add(trade);
					}
				}
			}
			// }
			
		}
		
		return dataDifferenceList;
	}
	
	/**
	 * 检测OCS中是否存在对应淘宝交易订单信息
	 * 
	 * @param customOcsTradeInfoMap
	 *        转换后的OCS交易信息MAP
	 * @param trade
	 *        淘宝交易信息对象
	 * @return true:存在， false:不存在
	 */
	private boolean isExistTrade(Map<String, OCSOrdersInfo> customOcsTradeInfoMap, Trade trade)
	{
		if (null != trade && MapUtils.isNotEmpty(customOcsTradeInfoMap))
		{
			Long tid = trade.getTid();
			if (null != tid)
			{
				OCSOrdersInfo ocsOrdersInfo = customOcsTradeInfoMap.get(String.valueOf(trade.getTid()));
				
				if (null != ocsOrdersInfo)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 转换OCS订单对象，key=订单编号 ，value:为订单对象
	 * 
	 * @param ordersInfos
	 * @return
	 */
	private Map<String, OCSOrdersInfo> customOcsTradeIds(List<OCSOrdersInfo> ordersInfos)
	{
		Map<String, OCSOrdersInfo> map = new HashMap<String, OCSOrdersInfo>();
		if (CollectionUtils.isNotEmpty(ordersInfos))
		{
			for (OCSOrdersInfo ocsOrdersInfo : ordersInfos)
			{
				if (null != ocsOrdersInfo)
				{
					map.put(ocsOrdersInfo.getOrderBn(), ocsOrdersInfo);
				}
			}
		}
		return map;
	}
	
	/**
	 * 校验一些必要参数，如果为空则不向下继续比较,必要参数如：销量num>0,支付时间为空等，
	 * <p>
	 * 
	 * 
	 * @param trade
	 * @return
	 */
	private boolean checkNextCompare(Trade trade)
	{
		if (null != trade)
		{
			Long num = trade.getNum();
			Date payTimeDate = trade.getPayTime();
			if (null != num && num > 0 && payTimeDate != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 获取默认查询的订单状态
	 * 
	 * @return
	 */
	private List<String> getTaobaoTradeStatus(String[] status)
	{
		
		List<String> tradeStatusList = new ArrayList<String>();
		
		CollectionUtils.addAll(tradeStatusList, status);
		
		return tradeStatusList;
	}
	
	/**
	 * 
	 * 请求淘宝，获取对应店铺交易记录
	 * 
	 * @param queryTime
	 *        查询交易记录时间 queryTime[0] ：开始时间 queryTime[1]:结束时间
	 * @return TradesSoldGetResponse
	 */
	@SuppressWarnings("unused")
	private void getTradeInfo(Date[] queryTime)
	{
		if (null != queryTime && queryTime.length >= 2)
		{
			List<Shop> shops = shopService.findAll();
			BigInteger ocsOrderTotalCount = ocsService.queryOCSOrdersNumberByTime(queryTime[0], queryTime[1]);
			if (CollectionUtils.isNotEmpty(shops))
			{
				for (Shop shop : shops)
				{
					if (null != shop)
					{
						// 执行查询
						try
						{
							TaobaoClient taobaoClient = TaoBaoServiceUtils.getTaobaoNormalClient(shop,
									TaoBaoServiceUtils.FOMATE_JSON);
							TradesSoldGetRequest tradesSoldGetRequest = createTradeRequest(queryTime[0], queryTime[1],
									DEFAULT_PAGE_NO, TRADES_SOLD_GET_REQUEST_PAGE_SIZE, false);
							
							TradesSoldGetResponse tradesSoldGetResponse = executeClient(taobaoClient,
									tradesSoldGetRequest, shop);
							
							// 记录请求响应结果日志
							logInfoTotalResults(queryTime, tradesSoldGetResponse);
							
							if (tradesSoldGetResponse.isSuccess())
							{
								// if (shop.getIsOCSOrderCompare())
								// {
								// 记录一下info日志
								logInfoTotalResults(queryTime, tradesSoldGetResponse);
								
								// 校验OCS订单与淘宝订单是否一致
								if (!checkOCsTotalResultsTotaobaoTotalResults(tradesSoldGetResponse, ocsOrderTotalCount))
								{
									getTradesDetailsCompareOcsOrder(taobaoClient, tradesSoldGetRequest,
											tradesSoldGetResponse, shop, queryTime[0], queryTime[1]);
								}
								else
								{
									// 正常则跳出本次循环，不需要继续向下对比
									continue;
								}
								// }
								
							}
						}
						catch (ApiException e)
						{
							LOG.error(Exceptions.getStackTraceAsString(e));
						}
						catch (Exception e)
						{
							LOG.error(Exceptions.getStackTraceAsString(e));
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * 请求淘宝，获取对应店铺所有交易记录
	 * 
	 * @param queryTime
	 *        查询交易记录时间 queryTime[0] ：开始时间 queryTime[1]:结束时间
	 * @return TradesSoldGetResponse
	 */
	private void getTradeAllInfo(Date[] queryTime)
	{
		if (null != queryTime && queryTime.length >= 2)
		{
			List<Shop> shops = shopService.findAll();
			if (CollectionUtils.isNotEmpty(shops))
			{
				for (Shop shop : shops)
				{
					if (null != shop)
					{
						List<Trade> trades = getTaobaoTradeInfo(queryTime, shop);
						
						if (CollectionUtils.isNotEmpty(trades))
						{
							saveOrUpdate(trades);
							
							logInfoUtilService.info("本次获取淘宝店铺交易订单总量：" + trades.size() + ",店铺名称：" + shop.getTitle());
						}
					}
				}
			}
		}
	}
	
	private List<Trade> getTradesNextDetails(TaobaoClient taobaoClient, TradesSoldGetRequest tradesSoldGetRequest,
			TradesSoldGetResponse tradesSoldGetResponse, Shop shop, Date startDate, Date endDate) throws Exception
	{
		// 记录数据
		List<Trade> dataList = new ArrayList<Trade>();
		
		addList(tradesSoldGetResponse, dataList, shop);
		
		if (tradesSoldGetResponse != null && tradesSoldGetResponse.getHasNext())
		{
			getNextPageTradesInfo(taobaoClient, tradesSoldGetRequest, shop, DEFAULT_PAGE_NO + 1, dataList);
		}
		
		// if (CollectionUtils.isNotEmpty(dataList))
		// {
		// saveOrUpdate(dataList);
		// }
		
		return dataList;
		
	}
	
	/**
	 * 获取下一页数据
	 * 
	 * @param taobaoClient
	 * @param tradesSoldGetRequest
	 * @param shop
	 * @param nextPageNo
	 * @param dataList
	 * @throws Exception
	 */
	private void getNextPageTradesInfo(TaobaoClient taobaoClient, TradesSoldGetRequest tradesSoldGetRequest, Shop shop,
			long nextPageNo, List<Trade> dataList) throws Exception
	{
		
		tradesSoldGetRequest.setPageNo(nextPageNo);
		
		// 获取返回值
		TradesSoldGetResponse tradesSoldGetResponse = executeClient(taobaoClient, tradesSoldGetRequest, shop);
		
		// 记录请求日志
		logInfoTotalResults(null, tradesSoldGetResponse);
		
		// 添加至集合中
		addList(tradesSoldGetResponse, dataList, shop);
		
		if (tradesSoldGetResponse != null && tradesSoldGetResponse.getHasNext())
		{
			// 休眠一下
			currentThradSheep100ms();
			
			getNextPageTradesInfo(taobaoClient, tradesSoldGetRequest, shop, nextPageNo + 1, dataList);
		}
		
	}
	
	private void addList(TradesSoldGetResponse tradesSoldGetResponse, List<Trade> dataList, Shop shop) throws Exception
	{
		// 如果OCS数据为空，则认为OCS没有订单，做全量入库处理
		if (null != tradesSoldGetResponse)
		{
			// 把淘宝的trade对象转换为自定义trade对象，以方便比较与存储至数据库存。
			List<Trade> customTradeList = convertCustomTradeList(tradesSoldGetResponse, shop);
			
			if (CollectionUtils.isNotEmpty(customTradeList))
			{
				dataList.addAll(customTradeList);
			}
		}
	}
	
	/**
	 * 记录请求响应结果日志
	 * <p>
	 * 
	 * @param queryTime
	 *        请求时间
	 * @param tradesSoldGetResponse
	 *        响应结果
	 */
	private void logInfoTotalResults(Date[] queryTime, TradesSoldGetResponse tradesSoldGetResponse)
	{
		if (LOG.isInfoEnabled() && null != tradesSoldGetResponse)
		{
			try
			{
				String temp = "";
				
				if (ArrayUtils.isNotEmpty(queryTime))
				{
					temp = DateUtils.format(queryTime[0]) + "~" + DateUtils.format(queryTime[1]);
				}
				else
				{
					temp = DateUtils.getNow(DateUtils.DEFAULT_FORMAT);
				}
				
				LOG.info("==============================" + temp + "==============================");
				LOG.info(" Response totalResults size = " + tradesSoldGetResponse.getTotalResults());
				LOG.info(" Response Body = " + tradesSoldGetResponse.getBody());
				LOG.info(" Response HashNext = " + tradesSoldGetResponse.getHasNext());
				LOG.info(" Response Trades = " + tradesSoldGetResponse.getTrades());
				LOG.info(" Response Msg = " + tradesSoldGetResponse.getMsg());
				LOG.info(" Response ErrorCode = " + tradesSoldGetResponse.getErrorCode());
				LOG.info(" Response SubCode = " + tradesSoldGetResponse.getSubCode());
				LOG.info(" Response SubMsg = " + tradesSoldGetResponse.getSubMsg());
				LOG.info(" Response default page size = " + TRADES_SOLD_GET_REQUEST_PAGE_SIZE);
				LOG.info("================================================================================");
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
	}
	
	/**
	 * 取淘宝店铺订单详情操作，并且与OCS订单进行比较，不一致则记录入库
	 * 
	 * @param taobaoClient
	 * @param tradesSoldGetRequest
	 * @param tradesSoldGetResponse
	 * @param shop
	 * @param startDate
	 *        开始时间
	 * @param endDate
	 *        结束时间
	 * @throws Exception
	 */
	private void getTradesDetailsCompareOcsOrder(TaobaoClient taobaoClient, TradesSoldGetRequest tradesSoldGetRequest,
			TradesSoldGetResponse tradesSoldGetResponse, Shop shop, Date startDate, Date endDate) throws Exception
	{
		// 获取OCS该段范围内的订单详情
		List<OCSOrdersInfo> ordersInfos = ocsService.queryOCSOrdersInfoByTime(startDate, endDate, shop.getShopId(),
				OCSOrdersInfo.class);
		
		// 记录差异数据
		List<Trade> dataDifferenceList = new ArrayList<Trade>();
		
		// 比较淘宝第一页数据
		compareData(ordersInfos, tradesSoldGetResponse, dataDifferenceList, shop);
		
		// 检测是否需要继续取下一页数据
		if (checkNextPage(tradesSoldGetResponse))
		{
			// 比较淘宝后面数据
			compareData(ordersInfos, DEFAULT_PAGE_NO + 1, shop, taobaoClient, tradesSoldGetRequest, dataDifferenceList);
		}
		
		// 有差异数据入库
		if (CollectionUtils.isNotEmpty(dataDifferenceList))
		{
			saveOrUpdate(dataDifferenceList);
		}
		
	}
	
	/**
	 * 检测是否需要继续取下一页数据
	 * <p>
	 * 
	 * 
	 * @param tradesSoldGetResponse
	 * @return
	 */
	private boolean checkNextPage(TradesSoldGetResponse tradesSoldGetResponse)
	{
		if (null != tradesSoldGetResponse)
		{
			Long taobaoTotalResults = tradesSoldGetResponse.getTotalResults();
			
			// 总数量大于默认条数认为有下一页，则继续向下取下一页
			if (null != taobaoTotalResults && TRADES_SOLD_GET_REQUEST_PAGE_SIZE < taobaoTotalResults)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * OCS订单数据与淘宝店铺订单，以订单编号做比较，如果从OCS订单列表中不存在 ，则认为该订单记录OCS中不存在，记录差异值
	 * 
	 * @param ordersInfos
	 *        OCS订单信息
	 * @param shop
	 * @param l
	 * @param taobaoClient
	 * @param tradesSoldGetResponse
	 *        从淘宝店铺中取的订单响应
	 * @param dataDifferenceList
	 *        记录差异数据
	 * @throws Exception
	 * @throws Exception
	 */
	private void compareData(List<OCSOrdersInfo> ordersInfos, long nextPageNo, Shop shop, TaobaoClient taobaoClient,
			TradesSoldGetRequest tradesSoldGetRequest, List<Trade> dataDifferenceList) throws Exception
	{
		if (null != tradesSoldGetRequest)
		{
			tradesSoldGetRequest.setPageNo(nextPageNo);
			
			// 以true表示，根据此参数判断取下一页面
			tradesSoldGetRequest.setUseHasNext(true);
			
			// 获取返回值
			TradesSoldGetResponse tradesSoldGetResponse = executeClient(taobaoClient, tradesSoldGetRequest, shop);
			
			// 数据进行对比
			compareData(ordersInfos, tradesSoldGetResponse, dataDifferenceList, shop);
			
			// 如果存在下一页，继续向下取
			if (null != tradesSoldGetResponse && tradesSoldGetResponse.getHasNext())
			{
				// 请求淘宝太频繁造成 connect timed out，故在此休眠一下
				currentThradSheep100ms();
				
				compareData(ordersInfos, nextPageNo + 1, shop, taobaoClient, tradesSoldGetRequest, dataDifferenceList);
			}
			
		}
	}
	
	/**
	 * 请求淘宝太频繁造成 connect timed out，故在此休眠一下
	 * <p>
	 */
	private void currentThradSheep100ms()
	{
		try
		{
			// 请求淘宝太频繁造成 connect timed out，故在此休眠一下
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * OCS订单数据与淘宝店铺订单，以订单编号做比较，如果从OCS订单列表中不存在 ，则认为该订单记录OCS中不存在，记录差异值
	 * 
	 * @param ordersInfos
	 *        OCS订单信息
	 * @param tradesSoldGetResponse
	 *        从淘宝店铺中取的订单响应
	 * @param dataDifferenceList
	 *        记录差异数据
	 * @param shop
	 * @throws Exception
	 */
	private void compareData(List<OCSOrdersInfo> ordersInfos, TradesSoldGetResponse tradesSoldGetResponse,
			List<Trade> dataDifferenceList, Shop shop) throws Exception
	{
		// 记录响应日志
		logInfoTotalResults(null, tradesSoldGetResponse);
		
		// 如果OCS数据为空，则认为OCS没有订单，做全量入库处理
		if (CollectionUtils.isEmpty(ordersInfos) && null != tradesSoldGetResponse)
		{
			// 把淘宝的trade对象转换为自定义trade对象，以方便比较与存储至数据库存。
			List<Trade> customTradeList = convertCustomTradeList(tradesSoldGetResponse, shop);
			
			if (CollectionUtils.isNotEmpty(customTradeList))
			{
				dataDifferenceList.addAll(customTradeList);
			}
		}
		
		if (CollectionUtils.isNotEmpty(ordersInfos) && null != tradesSoldGetResponse)
		{
			// 把淘宝的trade对象转换为自定义trade对象，以方便比较与存储至数据库存。
			List<Trade> customTradeList = convertCustomTradeList(tradesSoldGetResponse, shop);
			
			if (CollectionUtils.isNotEmpty(customTradeList))
			{
				for (OCSOrdersInfo oCSOrdersInfo : ordersInfos)
				{
					if (null != oCSOrdersInfo && StringUtils.isNotEmpty(oCSOrdersInfo.getOrderBn()))
					{
						for (Trade trade : customTradeList)
						{
							if (trade != null && null != trade.getTid())
							{
								// 比较OCS订单号与淘宝订单号
								if (!oCSOrdersInfo.getOrderBn().equalsIgnoreCase(trade.getTid().toString()))
								{
									// 记录存在差异订单
									dataDifferenceList.add(trade);
								}
							}
						}
					}
				}
			}
			
		}
	}
	
	/**
	 * 淘宝的trade对象转换为自定义trade对象，以方便比较与存储至数据库存。
	 * 
	 * @param tradesSoldGetResponse
	 *        淘宝响应对象
	 * @param shop
	 * @return List< Trade>
	 */
	@SuppressWarnings("unchecked")
	private List<Trade> convertCustomTradeList(TradesSoldGetResponse tradesSoldGetResponse, Shop shop) throws Exception
	{
		if (null != tradesSoldGetResponse)
		{
			List<com.taobao.api.domain.Trade> jsonArrayTrades = tradesSoldGetResponse.getTrades();
			if (null != jsonArrayTrades && CollectionUtils.isNotEmpty(jsonArrayTrades))
			{
				JSONArray jsonArray = JSONArray.fromObject(jsonArrayTrades);
				
				// 实体bean中包含的CLASS对象
				Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
				classMap.put("orders", Order.class);
				classMap.put("serviceOrders", ServiceOrder.class);
				classMap.put("serviceTags", LogisticsTag.class);
				classMap.put("promotionDetails", TradePromotionDetail.class);
				
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setClassMap(classMap);
				jsonConfig.setRootClass(Trade.class);
				
				Collection<Trade> trades = JSONArray.toCollection(jsonArray, jsonConfig);
				
				// 设置对应外键
				moidfyForeignKey(trades, shop);
				
				return (List<Trade>) (null != trades ? trades : null);
			}
		}
		
		return null;
	}
	
	/**
	 * 设置对应外键值
	 * 
	 * @param trades
	 * @param shop
	 */
	private void moidfyForeignKey(Collection<Trade> trades, Shop shop)
	{
		if (CollectionUtils.isNotEmpty(trades))
		{
			for (Trade trade : trades)
			{
				if (null != trade)
				{
					trade.setBrandId(null != shop ? shop.getBrandId() : null);
					
					List<Order> orders = trade.getOrders();
					List<ServiceOrder> serviceOrders = trade.getServiceOrders();
					
					if (CollectionUtils.isNotEmpty(orders))
					{
						for (Order order : orders)
						{
							if (null != order)
							{
								order.setTradeInfo(trade);
							}
						}
					}
					
					if (CollectionUtils.isNotEmpty(serviceOrders))
					{
						for (ServiceOrder serviceOrder : serviceOrders)
						{
							if (null != serviceOrder)
							{
								serviceOrder.setTradeInfo(trade);
							}
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 校验OCS订单与淘宝订单是否一致，true:正常，不继续对比 false: 不一致，需要取详情对比
	 * <p>
	 * 
	 * @param tradesSoldGetResponse
	 *        淘宝响应结果
	 * @param ocsOrderTotalCount
	 *        OCS订单总数
	 * @return true:正常，不继续对比 false: 不一致，需要取详情对比
	 */
	private boolean checkOCsTotalResultsTotaobaoTotalResults(TradesSoldGetResponse tradesSoldGetResponse,
			BigInteger ocsOrderTotalCount)
	{
		if (null != tradesSoldGetResponse && null != ocsOrderTotalCount)
		{
			Long tabaoTotalResults = tradesSoldGetResponse.getTotalResults();
			Long ocsTotalResults = 1L;// ocsOrderTotalCount.longValue();
			
			if (null != tabaoTotalResults && null != ocsTotalResults)
			{
				return ocsTotalResults >= tabaoTotalResults ? true : false;
			}
		}
		
		return true;
	}
	
	/**
	 * 执行API请求。
	 * 
	 * @param taobaoClient
	 *        淘宝客户端对象
	 * @param tradesSoldGetRequest
	 *        查询卖家已卖出的交易数据请求对象
	 * @param shop
	 * @return TradesSoldGetResponse
	 * @throws ApiException
	 */
	private TradesSoldGetResponse executeClient(TaobaoClient taobaoClient, TradesSoldGetRequest tradesSoldGetRequest,
			Shop shop) throws ApiException
	{
		if (null != taobaoClient && null != tradesSoldGetRequest && null != shop)
		{
			try
			{
				return taobaoClient.execute(tradesSoldGetRequest, shop.getSessionKey());
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return null;
	}
	
	/**
	 * 功能:
	 * <p>
	 * 作者 admin 2014-12-25 下午02:24:19
	 * 
	 * @param startCreated
	 * @param endCreated
	 * @param pageNo
	 * @param pageSize
	 * @param nextPage
	 * @return
	 */
	private TradesSoldGetRequest createTradeRequest(Date startCreated, Date endCreated, Long pageNo, Long pageSize,
			boolean nextPage)
	{
		TradesSoldGetRequest tradesSoldGetRequest = new TradesSoldGetRequest();
		
		// 默认取条数20条
		tradesSoldGetRequest.setPageSize(pageSize);
		tradesSoldGetRequest.setPageNo(DEFAULT_PAGE_NO);
		// true: 不返回数量totail_result_size ，根据该值取下一页，true or false
		tradesSoldGetRequest.setUseHasNext(nextPage);
		
		// 设置查询开始、结束时间
		tradesSoldGetRequest.setStartCreated(startCreated);
		tradesSoldGetRequest.setEndCreated(endCreated);
		
		// 设置返回字段值
		tradesSoldGetRequest.setFields(TradeConstants.FIELDS_GET_SOLD_TRADES);
		
		return tradesSoldGetRequest;
	}
	
	/**
	 * 获取查询时间，如：2000-01-01 00:00:00~2000-01-01 23:59:59
	 * 
	 * @return Date[]
	 */
	private Date[] getQueryTime()
	{
		// // 查询开始时间
		// String endTime = DateUtils.format(new Date(), YYYY_MM_DD_HH_00_00);
		//		
		// Date endDate = DateUtils.toDate(endTime, YYYY_MM_DD_HH_00_00);
		//		
		// // 开始时间
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(endDate);
		//		
		// // 查询开始时间 、往前推两个小时
		// calendar.add(Calendar.HOUR, -2);
		// Date startTime = calendar.getTime();
		//		
		// // 结束时间向前推一秒
		// calendar.setTime(endDate);
		// calendar.add(Calendar.SECOND, -1);
		//		
		// return new Date[] { startTime, calendar.getTime() };
		
		return getQuery3dayTime(YYYY_MM_DD_HH_00_00, Calendar.HOUR, -2);
	}
	
	/**
	 * 获取查询时间，如：2000-01-01 00:00:00~2000-01-01 00:30:00
	 * 
	 * @return Date[]
	 */
	private String[] getDifferenceQueryTime()
	{
		// 当前时间
		Date currentDate = new Date();
		
		// 查询开始时间
		String endTime = DateUtils.format(currentDate, DateUtils.DEFAULT_FORMAT);
		
		// 开始时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		
		// 查询开始时间 、往前推30分钟
		calendar.add(Calendar.MINUTE, -30);
		
		return new String[] { DateUtils.format(calendar.getTime(), DateUtils.DEFAULT_FORMAT), endTime };
	}
	
	/**
	 * 导出差异数据
	 * 
	 * @param trades
	 *        需要导出的数据列表
	 * @param response
	 *        响应
	 * @return Workbook
	 */
	public Workbook exportExcel(List<Trade> trades)
	{
		return ExcelUtils.writerExcel(trades, TradeConstants.TRADE_TITLE, TradeConstants.TRADE_FIELD_NAME, null);
	}
	
	/**
	 * 检测未付款订单，如果OCS中已存在，则修改本地库存状态，标为已付款状态
	 */
	public void checkNonPaymentTradeInfo()
	{
		// 比较订单信息
		compareNonPaymentTradeInfo();
	}
	
	/**
	 * 比较未支付的订单，OCS中是否存在，不存在则需要补齐
	 */
	private void compareNonPaymentTradeInfo()
	{
		Date[] queryTime = getQuery3dayTime(YYYY_MM_DD_00_00_00, Calendar.DATE, -3);
		
		if (null != queryTime && queryTime.length >= 2)
		{
			List<Shop> shops = shopService.findAll();
			if (CollectionUtils.isNotEmpty(shops))
			{
				for (Shop shop : shops)
				{
					if (null != shop)
					{
						// 本地库存中的交易信息
						List<Trade> localTrades = findByUpdateTimeBetweenAndStatusIn(DateUtils.format(queryTime[0],
								DateUtils.DEFAULT_FORMAT), DateUtils.format(queryTime[1], DateUtils.DEFAULT_FORMAT),
								getTaobaoTradeStatus(TradeConstants.TRADE_NO_PAY_STATUS), shop.getBrandId());
						
						if (CollectionUtils.isNotEmpty(localTrades))
						{
							logInfoUtilService.info("检测3天内未支付订单任务执行开始，店铺名：" + shop.getTitle());
							logInfoUtilService.info("已下载订单三天内未支付数量：" + localTrades.size());
							
							// 获取淘宝三天内所有交易记录
							List<Trade> taobaoTrades = getTaobaoTradeInfo(queryTime, shop);
							logInfoUtilService.info("三天内淘宝订单数量(包含所有状态)："
									+ (taobaoTrades != null ? taobaoTrades.size() : ""));
							
							// 本地库存中未支付的记录与淘宝三天内所有交易记录进行比对，如果未支付三天内已支付 ，则更新状态
							List<Trade> tradesSucceedList = getStatusSuccess(localTrades, taobaoTrades);
							logInfoUtilService.info("三天内淘宝订单数量(状态:已支付)："
									+ (tradesSucceedList != null ? tradesSucceedList.size() : ""));
							
							// 检测OCS中是否存在记录
							List<Trade> ocsNotExitslist = checkOcsOrderData(tradesSucceedList, shop);
							logInfoUtilService.info("三天内已支付且OCS中不存在的订单数量:"
									+ (ocsNotExitslist != null ? ocsNotExitslist.size() : ""));
							
							if (CollectionUtils.isNotEmpty(ocsNotExitslist))
							{
								logInfoUtilService.info("开始同步订单至OCS......");
								
								// 通知OCS同步订单,获取同步失败的信息
								List<Trade> syncFailedList = OCSSyncOrdersUtils.syncOrderToOcs(ocsNotExitslist, shop);
								
								// 处理同步失败记录
								dealSyncOrderFailedData(syncFailedList, shop);
								
								logInfoUtilService.info("完成同步订单至OCS......");
							}
							
							logInfoUtilService.info("检测3天内未支付订单任务执行结束，店铺名：" + shop.getTitle());
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 处理同步失败的订单，1分钟后重试，重试继续失败，则标记为同步失败
	 * <p>
	 * 
	 * 
	 * @param syncFailedList
	 *        同步失败列表
	 * @param shop
	 *        店铺属性列表
	 */
	private void dealSyncOrderFailedData(List<Trade> syncFailedList, Shop shop)
	{
		if (CollectionUtils.isNotEmpty(syncFailedList))
		{
			LOG.info("current Thread sleep start Time = " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
			
			// 当前线程休眠1分钟，1分钟后重试同步
			try
			{
				Thread.sleep(1000 * 60);
			}
			catch (InterruptedException e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			
			LOG.info("current Thread sleep end Time = " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
			
			// 通知OCS同步订单,获取同步失败的交易信息
			List<Trade> syncFailedList2 = OCSSyncOrdersUtils.syncOrderToOcs(syncFailedList, shop);
			
			// 记录同步失败信息
			saveSyncOrderFailedTrade(syncFailedList2);
		}
	}
	
	/**
	 * 保存同步失败记录
	 * 
	 * @param syncFailedList
	 */
	public void saveSyncOrderFailedTrade(List<Trade> syncFailedList)
	{
		if (CollectionUtils.isNotEmpty(syncFailedList))
		{
			for (Trade trade : syncFailedList)
			{
				if (trade != null)
				{
					// 标致为同步失败
					trade.setSyncOcsOrder(false);
				}
			}
		}
		
		try
		{
			saveOrUpdate(syncFailedList);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * 检测OCS中是否存在应该记录，不存在则记录并返回应该对象
	 * 
	 * @param tradesSucceedList
	 *        支付成功状态的交易记录列表
	 * @param shop
	 * @return
	 */
	private List<Trade> checkOcsOrderData(List<Trade> tradesSucceedList, Shop shop)
	{
		List<Trade> ocsNotExitsData = new ArrayList<Trade>();
		
		if (CollectionUtils.isNotEmpty(tradesSucceedList) && null != shop)
		{
			for (Trade trade : tradesSucceedList)
			{
				if (trade != null)
				{
					if (!checkOCsData(trade, shop))
					{
						ocsNotExitsData.add(trade);
					}
				}
			}
		}
		
		return ocsNotExitsData;
	}
	
	/**
	 * 检测OCS中是否存在该记录
	 * 
	 * @param trade
	 *        支付成功的交易对象
	 * @param shop
	 *        存储一些基本信息对象，如：appkey,sessionKey,shop_id等
	 * @return true:存在，false：不存在
	 */
	private boolean checkOCsData(Trade trade, Shop shop)
	{
		if (null != trade && null != shop)
		{
			try
			{
				List<OCSOrdersInfo> infos = ocsService.queryOCSOrdersInfoByTime(null, null, shop.getShopId(), String
						.valueOf(trade.getTid()), OCSOrdersInfo.class);
				
				if (CollectionUtils.isNotEmpty(infos))
				{
					infos.clear();
					infos = null;
					return true;
				}
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			
		}
		
		return false;
	}
	
	/**
	 * 本地库存中未支付的记录与淘宝三天内所有交易记录进行比对，如果未支付订单三天内已支付 ，则更新状态,并记录入库
	 * 
	 * @param localTrades
	 *        本地库存三天内未支付的交易订单
	 * @param taobaoTrades
	 *        淘宝三天内的所有状态交易记录
	 * @return
	 */
	private List<Trade> getStatusSuccess(List<Trade> localTrades, List<Trade> taobaoTrades)
	{
		List<Trade> trades = new ArrayList<Trade>();
		
		if (CollectionUtils.isNotEmpty(localTrades) && CollectionUtils.isNotEmpty(taobaoTrades))
		{
			// 把从淘宝获取的3天内所有订单转换为MAP存储，以提高比对效率,key=tid：交易编号 ，value=status,交易状态
			Map<Long, String> customMap = taobaoTradsToCustomMap(taobaoTrades);
			
			if (MapUtils.isNotEmpty(customMap))
			{
				for (Trade trade : localTrades)
				{
					if (null != trade)
					{
						String status = customMap.get(trade.getTid());
						if (StringUtils.isNotEmpty(status) && ArrayUtils.contains(TradeConstants.TRADE_STATUS, status))
						{
							trade.setStatus(status);
							trades.add(trade);
						}
						
					}
				}
			}
		}
		
		return trades;
	}
	
	/**
	 * 把从淘宝获取的3天内所有订单转换为MAP存储，以提高比对效率,key=tid：交易编号 ，value=status,交易状态
	 * 
	 * @param taobaoTrades
	 *        淘宝获取的3天内所有订单
	 * @return Map<String, String>
	 */
	private Map<Long, String> taobaoTradsToCustomMap(List<Trade> taobaoTrades)
	{
		Map<Long, String> customMap = new HashMap<Long, String>(null != taobaoTrades ? taobaoTrades.size() : 16);
		if (CollectionUtils.isNotEmpty(taobaoTrades))
		{
			try
			{
				for (int i = taobaoTrades.size() - 1; i >= 0; i--)
				{
					Trade trade = taobaoTrades.get(i);
					
					if (null != trade)
					{
						customMap.put(trade.getTid(), trade.getStatus());
						
						taobaoTrades.remove(i);
					}
				}
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
		return customMap;
	}
	
	/**
	 * 根据查询时间，获取该时间段内所有交易信息
	 * 
	 * @param queryTime
	 *        查询时间范围
	 * @param shop
	 * @return
	 */
	private List<Trade> getTaobaoTradeInfo(Date[] queryTime, Shop shop)
	{
		try
		{
			TaobaoClient taobaoClient = TaoBaoServiceUtils.getTaobaoNormalClient(shop, TaoBaoServiceUtils.FOMATE_JSON);
			TradesSoldGetRequest tradesSoldGetRequest = createTradeRequest(queryTime[0], queryTime[1], DEFAULT_PAGE_NO,
					TRADES_SOLD_GET_REQUEST_PAGE_SIZE, true);
			
			TradesSoldGetResponse tradesSoldGetResponse = executeClient(taobaoClient, tradesSoldGetRequest, shop);
			
			// 记录请求响应结果日志
			logInfoTotalResults(queryTime, tradesSoldGetResponse);
			
			if (tradesSoldGetResponse.isSuccess())
			{
				return getTradesNextDetails(taobaoClient, tradesSoldGetRequest, tradesSoldGetResponse, shop,
						queryTime[0], queryTime[1]);
			}
		}
		catch (ApiException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 获取查询查询
	 * 
	 * @param dateFormat
	 *        日期格式
	 * @param field
	 *        向前或向后+、-、*、/ 字段名，如：化格式： Calendar.DATE
	 * @param amount
	 *        -1、+2、*2、、/2 ,带符号
	 * @return
	 */
	private Date[] getQuery3dayTime(String dateFormat, int field, int amount)
	{
		// 查询开始时间
		String endTime = DateUtils.format(new Date(), dateFormat);
		
		Date endDate = DateUtils.toDate(endTime, dateFormat);
		
		// 开始时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		
		// 查询开始时间 、往前推两个小时
		calendar.add(field, amount);
		Date startTime = calendar.getTime();
		
		// 结束时间向前推一秒
		calendar.setTime(endDate);
		calendar.add(Calendar.SECOND, -1);
		
		return new Date[] { startTime, calendar.getTime() };
		
	}
}
