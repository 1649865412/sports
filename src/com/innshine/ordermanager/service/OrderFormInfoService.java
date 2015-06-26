/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.ordermanager.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.SqlDao;
import com.base.dao.sql.ConvertPageQueryFieldsToSQL;
import com.base.entity.main.User;
import com.base.shiro.ShiroUser;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.base.util.persistence.SearchFilter;
import com.innshine.ordermanager.dao.OrderFormInfoDAO;
import com.innshine.ordermanager.entity.OrderFormInfo;
import com.innshine.ordermanager.entity.OrderPageInfo;
import com.innshine.ordermanager.model.OrderArrivalModel;
import com.innshine.ordermanager.model.OrderFormInfoModel;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.innshine.productinfo.utils.TemplateConstants;
import com.innshine.stock.entity.StockLog;
import com.innshine.stock.service.StockLogService;
import com.innshine.stockmanager.stockinfo.entity.StockInfo;
import com.innshine.stockmanager.stockloginfo.entity.StockLogInfo;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.SecurityUtils;
import com.utils.excel.ExcelToBeanUtils;
import com.utils.validator.ValidatorsDataUtils;

@Service
@Transactional
public class OrderFormInfoService
{
	/**
	 * 默认给Admin ID
	 */
	private static final long DEFAULT_ADMIN_ID = 1L;
	
	/**
	 * 查询数据SQL
	 */
	private static final String ORDER_FORM_PAGE_SQL = "order.form.page.sql";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderFormInfoService.class);
	
	/**
	 * 必填字段列表
	 */
	public static final String[] VALIDATORS_RULE_FIELD_NAME = new String[] { "upccode", "materialNumber" };
	// , "orderNumber","transferNumber", "returnNumber","transferCargoNumber",
	// };
	
	@Autowired
	private OrderFormInfoDAO orderFormInfoDAO;
	@Autowired
	private StockLogService stockLogService;
	
	@Autowired
	private SqlDao sqlDao;
	
	public List<OrderFormInfo> findByBnAndBrandId(String bn, Long brandId)
	{
		if (StringUtils.isNotEmpty(bn))
		{
			
			return orderFormInfoDAO.findByMaterialNumberAndBrandId(bn, brandId);
		}
		
		return null;
		
	}
	
	public List<OrderFormInfo> findByUpccodeAndBnAndBrandId(String upccode, String materialNumber, Long brandId)
	{
		if (StringUtils.isNotEmpty(upccode))
		{
			return orderFormInfoDAO.findByUpccodeAndMaterialNumberAndBrandId(upccode, materialNumber, brandId);
		}
		
		return null;
		
	}
	
	public OrderFormInfo get(Long id)
	{
		return orderFormInfoDAO.findOne(id);
	}
	
	public void saveOrUpdate(OrderFormInfo orderFormInfo)
	{
		saveOrUpdate(orderFormInfo, false);
	}
	
	public void saveOrUpdate(OrderFormInfo orderFormInfo, boolean isDataCleaning)
	{
		orderFormInfoDAO.save(orderFormInfo);
		saveStockLog(orderFormInfo, getUserId(isDataCleaning));
	}
	
	private Long getUserId(boolean isDataCleaning)
	{
		if (!isDataCleaning)
		{
			try
			{
				ShiroUser shiroUser = SecurityUtils.getShiroUser();
				User u = shiroUser.getUser();
				return u.getId();
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return DEFAULT_ADMIN_ID;
	}
	//(订货1、到货2)
	private void saveStockLog(OrderFormInfo orderFormInfo, Long userId)
	{
		StockLog stockLog = new StockLog();
		stockLog.setBrandId(orderFormInfo.getBrandId());
		stockLog.setUpccode(orderFormInfo.getUpccode());
		stockLog.setMaterialNumber(orderFormInfo.getMaterialNumber());
		// 订货数量
		Integer orderNumber = null == orderFormInfo.getOrderNumber() ? 0 : orderFormInfo.getOrderNumber();
		
		// 到货数量
		Integer arrivalNumber = null == orderFormInfo.getArrivalNumber() ? 0 : orderFormInfo.getArrivalNumber();
		
		boolean isOrderAndArrival = false;
		
		stockLog.setOptTime(new Date());
		if (orderFormInfo.getOrderNumber() != null && null != orderFormInfo.getArrivalNumber())
		{
			isOrderAndArrival = true;
		}
		
		if (orderFormInfo.getArrivalNumber() == null && !isOrderAndArrival)
		{
			stockLog.setNum(orderNumber.longValue());
			stockLog.setPredictArriveTime(orderFormInfo.getPredictArriveTime());
			stockLog.setOptType("1");
		}
		
		if (orderFormInfo.getOrderNumber() == null && !isOrderAndArrival)
		{
			stockLog.setNum(arrivalNumber.longValue());
			
			if (orderFormInfo.getArrivalTime() != null)
			{
				stockLog.setOptTime(orderFormInfo.getArrivalTime());
			}
			stockLog.setOptType("2");
		}
		
		stockLog.setUpdatedTime(new Date());
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User u = shiroUser.getUser();
		//stockLog.setUpdatedUser(userId.toString());
		stockLog.setUpdatedUser(u.getUsername());
		
		
		stockLog.setPrice(orderFormInfo.getOrderAmount());
		
		// 如两者都存在 ，则拆分两条记录入库
		if (isOrderAndArrival)
		{
			List<StockLog> stockLogs = getStockLogInfos(stockLog, orderFormInfo, arrivalNumber);
			
			if (CollectionUtils.isNotEmpty(stockLogs))
			{	
				stockLogService.saveOrUpdate(stockLogs);
			}
		}
		else
		{
			
			stockLogService.saveOrUpdate(stockLog);
			
		}
	}
	
	private List<StockLog> getStockLogInfos(StockLog stockLog, OrderFormInfo orderFormInfo, Integer arrivalNumber)
	{
		List<StockLog> stockLogs = new ArrayList<StockLog>();
		
		if (null != stockLog && null != orderFormInfo)
		{
			
			if (orderFormInfo.getArrivalNumber() != null)
			{
				StockLog attrLog = new StockLog();
				attrLog.setNum(arrivalNumber.longValue());
				
				if (orderFormInfo.getArrivalTime() != null)
				{
					attrLog.setOptTime(orderFormInfo.getArrivalTime());
				}
				else
				{
					attrLog.setOptTime(stockLog.getOptTime());
				}
				
				attrLog.setBrandId(stockLog.getBrandId());
				attrLog.setUpdatedTime(stockLog.getUpdatedTime());
				attrLog.setOptType("2");
				attrLog.setBrandId(orderFormInfo.getBrandId());
				attrLog.setUpccode(orderFormInfo.getUpccode());
				attrLog.setMaterialNumber(orderFormInfo.getMaterialNumber());
				
				stockLogs.add(attrLog);
				
			}
			
			if (orderFormInfo.getOrderNumber() != null)
			{
				StockLog ordersStockLog = new StockLog();
				ordersStockLog.setNum(orderFormInfo.getOrderNumber().longValue());
				ordersStockLog.setOptTime(stockLog.getOptTime());
				ordersStockLog.setBrandId(stockLog.getBrandId());
				ordersStockLog.setUpdatedTime(stockLog.getUpdatedTime());
				ordersStockLog.setOptType("1");
				ordersStockLog.setBrandId(orderFormInfo.getBrandId());
				ordersStockLog.setUpccode(orderFormInfo.getUpccode());
				ordersStockLog.setMaterialNumber(orderFormInfo.getMaterialNumber());
				
				stockLogs.add(ordersStockLog);
			}
			
		}
		return stockLogs;
	}
	
	/**
	 * 处理前端页面添加、修改动作
	 * 
	 * @param orderFormInfo
	 *        前端页面添加、修改对象
	 * @param brandId
	 *        所属组织编号
	 * @return List< OrderFormInfo >
	 */
	public List<OrderFormInfo> operatorOrderFormInfo(OrderFormInfo orderFormInfo, Long brandId)
	{
		List<OrderFormInfo> formInfos = new ArrayList<OrderFormInfo>();
		if (null != orderFormInfo)
		{
			
			formInfos.add(orderFormInfo);
			
			consStockInfos(formInfos, brandId, true);
		}
		
		return formInfos;
	}
	
	public void saveOrUpdate(List<OrderFormInfo> orderFormInfos)
	{
		
		orderFormInfoDAO.save(orderFormInfos);
		Long userId = getUserId(false);
		for (OrderFormInfo o : orderFormInfos)
		{
			saveStockLog(o, userId);
		}
		
	}
	
	public void delete(Long id)
	{
		orderFormInfoDAO.delete(id);
	}
	
	public List<OrderFormInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<OrderFormInfo> springDataPage = orderFormInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<OrderFormInfo> findByExample(Specification<OrderFormInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<OrderFormInfo> springDataPage = orderFormInfoDAO.findAll(specification,
				PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**
	 * 多表连接查询SQL，并返回分页结果
	 * 
	 * @param searchFilters
	 *        前端页面查询条件结果集
	 * @param page
	 *        分页对象
	 * @return List< OrderPageInfo >
	 */
	public List<OrderPageInfo> findByCustomPage(Collection<SearchFilter> searchFilters, Page page)
	{
		try
		{
			Map<String, Object> params = cosParams(searchFilters, page);
			return sqlDao.queryByPage(params, ORDER_FORM_PAGE_SQL, page, OrderPageInfo.class);
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 多表连接查询SQL，并返回相应结果集
	 * 
	 * @param searchFilters
	 *        前端页面查询条件结果集
	 * @return List< OrderPageInfo >
	 */
	public List<OrderPageInfo> findByExportData(Collection<SearchFilter> searchFilters)
	{
		try
		{
			Map<String, Object> params = cosParams(searchFilters, null);
			return sqlDao.queryListBySql(params, ORDER_FORM_PAGE_SQL, OrderPageInfo.class);
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 构造参数列表
	 * 
	 * @param searchFilters
	 *        前端页面查询条件集合
	 * @param page
	 * @return Map< String, Object > 数据库查询参数Map
	 */
	private Map<String, Object> cosParams(Collection<SearchFilter> searchFilters, Page page)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		if (CollectionUtils.isNotEmpty(searchFilters))
		{
			for (SearchFilter searchFilter : searchFilters)
			{
				if (null != searchFilter)
				{
					params.put(searchFilter.getFieldName(), searchFilter.getValue());
				}
			}
		}
		
		boolean isPageNull = true;
		if (null != page)
		{
			if (StringUtils.isNotEmpty(page.getOrderField()) && StringUtils.isNotEmpty(page.getOrderDirection()))
			{
				params.put("orderField", ConvertPageQueryFieldsToSQL.fieldNameToColumName(page.getOrderField()));
				params.put("orderDirection", page.getOrderDirection());
				isPageNull = false;
			}
		}
		
		if (isPageNull)
		{
			params.put("orderField", "o.update_time");
			params.put("orderDirection", "desc");
		}
		
		return params;
	}
	
	/**
	 * 解析上传的Excel文件
	 * 
	 * @param file
	 *        上传的文件对象
	 * @param brandId
	 *        所属组织编号
	 * @param orderType
	 *        导入模型对象 1：订货单 2：到货单
	 * @return Map< NbSalesDetails, String >key： NbSalesDetails 需要提示的错误对象
	 *         value：提示信息
	 * @throws Exception
	 */
	public Map<OrderFormInfo, String> parseExcelData(File file, Long brandId, String orderType) throws Exception
	{
		Map<OrderFormInfo, String> failedMap = new HashMap<OrderFormInfo, String>();
		
		// 获取对应模型对象
		Class<?> modelClass = getModelByOrderType(orderType);
		
		// 解析Excel文件
		List<OrderFormInfo> orderFormInfos = ExcelToBeanUtils.readFilelModel(file, modelClass, OrderFormInfo.class,
				new String[] {}, false);
		
		// 去除重复
		validatorRepeatData(orderFormInfos, failedMap);
		
		// 效验数据的完整性
		validatorData(orderFormInfos, brandId, failedMap, modelClass);
		
		// 根据订货单+到货单信息，构造库存记录
		consStockInfos(orderFormInfos, brandId, false);
		
		// 数据入库
		saveOrUpdate(orderFormInfos);
		
		return failedMap;
	}
	
	/**
	 * 根据订货单+到货单信息，构造库存记录
	 * 
	 * @param orderFormInfos
	 *        订货单导入数据对象
	 * @param brandId
	 *        所属组织编号
	 * @param isWebData
	 *        true:由前端页面触发添加、修改操作,false:数据导入
	 */
	private void consStockInfos(List<OrderFormInfo> orderFormInfos, Long brandId, boolean isWebData)
	{
		if (CollectionUtils.isNotEmpty(orderFormInfos))
		{
			for (OrderFormInfo orderFormInfo : orderFormInfos)
			{
				if (null != orderFormInfo)
				{
					// 计算当前现有库存
					Integer currentStockNumber = getCurrentStockNumber(orderFormInfo);
					
					// 计算可用库存
					Integer availableStockNumber = getavailableStockNumber(orderFormInfo);
					
					// 构造最新库存信息，默认集合中只有一条记录
					List<StockInfo> lastStockInfos = getStockInfos(orderFormInfo, currentStockNumber,
							availableStockNumber, isWebData);
					
					orderFormInfo.setStockInfos(lastStockInfos);
				}
			}
		}
	}
	
	/**
	 * 获取最新库存信息，使用上一次可用的库存+本次计算结果
	 * 
	 * @param orderFormInfo
	 *        导入、添加、修改的订货单实体bean
	 * @param currentStockNumber
	 *        当前库存
	 * @param availableStockNumber
	 *        可用库存
	 * @param isWebData
	 *        true:由前端页面触发添加、修改操作,false:数据导入
	 * @return 返回处理完整的当前库存、可用库存对象信息
	 */
	private List<StockInfo> getStockInfos(OrderFormInfo orderFormInfo, Integer currentStockNumber,
			Integer availableStockNumber, boolean isWebData)
	{
		if (null != orderFormInfo)
		{
			List<StockInfo> tmpInfos = orderFormInfo.getStockInfos();
			if (CollectionUtils.isNotEmpty(tmpInfos))
			{
				for (StockInfo stockInfo : tmpInfos)
				{
					if (null != stockInfo)
					{
						cosnStockLogInfos(stockInfo, isWebData);
						
						// 可用库存
						Integer tmpAvailableStockNumber = null == stockInfo.getAvailableStockNumber() ? 0 : stockInfo
								.getAvailableStockNumber();
						
						// 当前库存
						Integer tmpCurrentStockNumber = null == stockInfo.getCurrentStockNumber() ? 0 : stockInfo
								.getCurrentStockNumber();
						stockInfo.setCurrentStockNumber(tmpCurrentStockNumber + currentStockNumber);
						stockInfo.setAvailableStockNumber(tmpAvailableStockNumber + availableStockNumber);
						stockInfo.setUpccode(orderFormInfo.getUpccode());
						stockInfo.setUpdateTime(orderFormInfo.getUpdateTime());
						stockInfo.setBrandId(orderFormInfo.getBrandId());
						stockInfo.setOrderFormInfo(orderFormInfo);
						stockInfo.setMaterialNumber(orderFormInfo.getMaterialNumber());
						stockInfo.setBrandId(orderFormInfo.getBrandId());
					}
				}
				
			}
			else
			{
				tmpInfos = new ArrayList<StockInfo>();
				StockInfo stockInfo = new StockInfo();
				stockInfo.setCurrentStockNumber(currentStockNumber);
				stockInfo.setAvailableStockNumber(availableStockNumber);
				stockInfo.setUpccode(orderFormInfo.getUpccode());
				stockInfo.setUpdateTime(orderFormInfo.getUpdateTime());
				stockInfo.setBrandId(orderFormInfo.getBrandId());
				stockInfo.setOrderFormInfo(orderFormInfo);
				stockInfo.setMaterialNumber(orderFormInfo.getMaterialNumber());
				
				cosnStockLogInfos(stockInfo, isWebData);
				
				tmpInfos.add(stockInfo);
			}
			
			return tmpInfos;
			
		}
		
		return null;
	}
	
	/**
	 * 构造库存流水信息，简称日志表
	 * 
	 * @param stockInfo
	 *        库存信息
	 * @param isWebData
	 *        true:由前端页面触发添加、修改操作,false:数据导入
	 */
	public void cosnStockLogInfos(StockInfo stockInfo, boolean isWebData)
	{
		cosnStockLogInfos(stockInfo, isWebData, false);
	}
	
	/**
	 * 构造库存流水信息，简称日志表
	 * 
	 * @param stockInfo
	 *        库存信息
	 * @param isWebData
	 * 
	 *        true:由前端页面触发添加、修改操作,false:数据导入
	 * @param isOcsDataCleaning
	 *        true: 由数据清洗调用 false:正常添加
	 */
	public void cosnStockLogInfos(StockInfo stockInfo, boolean isWebData, boolean isOcsDataCleaning)
	{
		if (null != stockInfo)
		{
			List<StockLogInfo> stockLogInfos = stockInfo.getStockLogInfos();
			
			if (isWebData || null == stockLogInfos)
			{
				stockLogInfos = new ArrayList<StockLogInfo>();
			}
			
			// for (StockLogInfo stockLogInfo : stockLogInfos)
			// {
			// if (null != stockLogInfo)
			// {
			// //stockLogInfo.setId(null);
			// }
			// }
			
			StockLogInfo logInfo = new StockLogInfo();
			logInfo.setUpdateTime(DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
			logInfo.setUpccode(stockInfo.getUpccode());
			Integer tmpValue = null == stockInfo.getCurrentStockNumber() ? 0 : stockInfo.getCurrentStockNumber();
			logInfo.setCurrentStockNumber(tmpValue);
			logInfo.setAvailableStockNumber(null == stockInfo.getAvailableStockNumber() ? 0 : stockInfo
					.getAvailableStockNumber());
			logInfo.setBrandId(stockInfo.getBrandId());
			logInfo.setStockInfo(stockInfo);
			logInfo.setMaterialNumber(stockInfo.getMaterialNumber());
			setUserNameAndId(logInfo, isOcsDataCleaning);
			stockLogInfos.add(logInfo);
			stockInfo.setStockLogInfos(stockLogInfos);
		}
	}
	
	/**
	 * 设置更新用户与用户ID
	 * 
	 * @param logInfo
	 * @param isOcsDataCleaning
	 *        true: 由数据清洗调用 false:正常添加
	 */
	private void setUserNameAndId(StockLogInfo logInfo, boolean isOcsDataCleaning)
	{
		try
		{
			if (null != logInfo)
			{
				String userName = "";
				Long userId = null;
				String ipAddress = "";
				if (isOcsDataCleaning)
				{
					userName = "OCS数据清洗操作";
				}
				else
				{
					
					ShiroUser shiroUser = SecurityUtils.getShiroUser();
					if (null != shiroUser)
					{
						ipAddress = shiroUser.getIpAddress();
						User user = shiroUser.getUser();
						if (null != user)
						{
							userName = user.getUsername();
							userId = user.getId();
						}
					}
				}
				
				logInfo.setUserName(userName);
				logInfo.setUserId(userId);
				logInfo.setIpAddress(ipAddress);
			}
			
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * 计算当前导入、添加、修改数据的可用库存数量，公式：(到货数量-转仓数量-退货数量-调货数量-样品)=得出当前可用库存数量 )
	 * <p>
	 * 
	 * @param orderFormInfo
	 *        导入的订货信息
	 * @return 当前导入可用库存数量，并未返回真实可用库数量存，需要用该库存+原库存获取完整可用库存数量
	 */
	private Integer getavailableStockNumber(OrderFormInfo orderFormInfo)
	{
		Integer availableStockNumber = 0;
		if (null != orderFormInfo)
		{
			// 到货数量
			Integer arrivalNumber = null == orderFormInfo.getArrivalNumber() ? 0 : orderFormInfo.getArrivalNumber();
			
			// 转仓数量
			Integer transferNumber = null == orderFormInfo.getTransferNumber() ? 0 : orderFormInfo.getTransferNumber();
			
			// 调货数量
			Integer transferCargoNumber = null == orderFormInfo.getTransferCargoNumber() ? 0 : orderFormInfo
					.getTransferCargoNumber();
			
			// 退货数量
			Integer returnNumber = null == orderFormInfo.getReturnNumber() ? 0 : orderFormInfo.getReturnNumber();
			
			// 样品数量
			Integer sampleNumber = null == orderFormInfo.getSampleNumber() ? 0 : orderFormInfo.getSampleNumber();
			
			// 到货数量-转仓数量-退货数量-调货数量-样品
			availableStockNumber = (arrivalNumber - transferNumber - returnNumber - transferCargoNumber - sampleNumber);
		}
		
		return availableStockNumber;
	}
	
	/**
	 * 计算当前导入、添加、修改数据的当前库存数量，公式：(订货量-转仓数量-退货数量-调货数量-样品)=得出当前库存数量
	 * 
	 * @param orderFormInfo
	 *        导入的订货信息
	 * @return 当前导入库存数量，并未返回真实库存，需要用该库存+原库存获取完整当前库存数量
	 */
	private Integer getCurrentStockNumber(OrderFormInfo orderFormInfo)
	{
		Integer tmpCurrentStockNumber = 0;
		
		if (null != orderFormInfo)
		{
			// 订货数量
			Integer orderNumber = null == orderFormInfo.getOrderNumber() ? 0 : orderFormInfo.getOrderNumber();
			
			// 转仓数量
			Integer transferNumber = null == orderFormInfo.getTransferNumber() ? 0 : orderFormInfo.getTransferNumber();
			
			// 调货数量
			Integer transferCargoNumber = null == orderFormInfo.getTransferCargoNumber() ? 0 : orderFormInfo
					.getTransferCargoNumber();
			
			// 退货数量
			Integer returnNumber = null == orderFormInfo.getReturnNumber() ? 0 : orderFormInfo.getReturnNumber();
			
			// 样品数量
			Integer sampleNumber = null == orderFormInfo.getSampleNumber() ? 0 : orderFormInfo.getSampleNumber();
			
			// 订货量-转仓数量-退货数量-调货数量-样品
			tmpCurrentStockNumber = (orderNumber - transferNumber - returnNumber - transferCargoNumber - sampleNumber);
			
		}
		return tmpCurrentStockNumber;
	}
	
	/**
	 * 根据订单类型，返回相应模版类
	 * 
	 * @param orderType
	 *        页面选择的导入类型
	 * @return Class< ?>
	 *         返回订货模型（OrderFormInfoModel）、到货模型（OrderArrivalModel）class对象
	 */
	private Class<?> getModelByOrderType(String orderType)
	{
		if (StringUtils.isNotEmpty(orderType) && NumberUtils.isNumber(orderType))
		{
			int tmpType = Integer.parseInt(orderType);
			
			if (OrderType.ORDER_INDENT_TYPE.getType() == tmpType)
			{
				return OrderFormInfoModel.class;
			}
			else if (OrderType.ORDER_ARRIVAL_TYPE.getType() == tmpType)
			{
				return OrderArrivalModel.class;
			}
		}
		
		return null;
	}
	
	/**
	 * 获取对应模版路径
	 * <p>
	 * 
	 * @param orderType
	 *        前端选择模版类型
	 * @return 模版的完整路径
	 */
	public String getModelPath(String orderType)
	{
		if (StringUtils.isNotEmpty(orderType))
		{
			if (NumberUtils.isDigits(orderType))
			{
				int type = Integer.parseInt(orderType);
				
				if (OrderType.ORDER_INDENT_TYPE.getType() == type)
				{
					return ExcelFileUtils.getExcelModelPath(TemplateConstants.EXPORT_ORDER_INDET_MODEL_PATH);
					
				}
				else if (OrderType.ORDER_ARRIVAL_TYPE.getType() == type)
				{
					return ExcelFileUtils.getExcelModelPath(TemplateConstants.EXPORT_ORDER_ARRIVAL_MODEL_PATH);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 去除重复记录，以UPC条码做一，如果找到两条或多条一样的，则以最后一条为最新记录
	 * 
	 * @param failedMap
	 *        记录失败列表
	 * 
	 * @param details
	 *        原始数据列表
	 */
	private void validatorRepeatData(List<OrderFormInfo> details, Map<OrderFormInfo, String> failedMap)
	{
		if (CollectionUtils.isNotEmpty(details))
		{
			
			for (int i = details.size() - 1; i >= 0; i--)
			{
				
				OrderFormInfo orderFormInfo = details.get(i);
				
				if (null != orderFormInfo)
				{
					// 计数器，记录重复次数
					int count = 0;
					
					for (int j = details.size() - 1; j >= 0; j--)
					{
						
						OrderFormInfo tmpOrderFormInfo = details.get(j);
						if (null != tmpOrderFormInfo)
						{
							String productUpccode = tmpOrderFormInfo.getUpccode();
							
							if (StringUtils.isNotBlank(productUpccode)
									&& productUpccode.equalsIgnoreCase(orderFormInfo.getUpccode()))
							{
								count++;
							}
						}
						
					}
					
					if (count >= 2)
					{
						
						LOGGER.warn(" Remove duplicate records ! Object = " + orderFormInfo);
						
						failedMap.put(orderFormInfo, "导入原始数据中该记录存在重复！");
						details.remove(orderFormInfo);
						
					}
				}
				
			}
		}
	}
	
	/**
	 * 效验数据的完整性
	 * <p>
	 * 
	 * @param olyProductInfos
	 *        导入数据列表
	 * @param brandId
	 *        所属部门编号
	 * @param modelClass
	 *        对应模型对象Class
	 * @return 返回不满足规则的数据列表，前端页面展示
	 */
	private void validatorData(List<OrderFormInfo> details, Long brandId, Map<OrderFormInfo, String> failedMap,
			Class<?> modelClass)
	{
		if (CollectionUtils.isNotEmpty(details))
		{
			if (null == failedMap)
			{
				failedMap = new HashMap<OrderFormInfo, String>();
			}
			
			String currTime = DateUtils.getNow(DateUtils.DEFAULT_FORMAT);
			List<OrderFormInfo> deleteList = new ArrayList<OrderFormInfo>();
			
			// 获取模型列表对象
			Vector<String> fieldNameList = ExcelToBeanUtils.refSupperFieldsName(modelClass);
			for (int i = details.size() - 1; i >= 0; i--)
			{
				OrderFormInfo orderFomrInfo = details.get(i);
				
				// 继续效验的标识
				boolean flag = false;
				if (null != orderFomrInfo)
				{
					// 效验是否有空行的情况，解析Excel文件时，会存在空对象，所有内容为空
					if (!ValidatorsDataUtils.isObjectNull(orderFomrInfo) && !flag)
					{
						flag = true;
					}
					
					// 效验必填字段时否为空
					if (!ValidatorsDataUtils.isFieldsRule(VALIDATORS_RULE_FIELD_NAME, orderFomrInfo) && !flag)
					{
						flag = true;
						failedMap.put(orderFomrInfo, "必填字段未填写");
						
					}
					
					orderFomrInfo.setUpdateTime(currTime);
					orderFomrInfo.setBrandId(brandId);
					
					// 效验数据的唯一性
					if (!ValidatorsDataUtils.isUnique(orderFomrInfo, this) && !flag)
					{
						// 默认支持批量修改
						if (com.innshine.common.Constants.DEFAULT_BATCH_MODIFTY)
						{
							OrderFormInfo queryOrderInfo = findByUnique(orderFomrInfo);
							if (null != queryOrderInfo)
							{
								deleteList.add(queryOrderInfo);
								setOrderFormInfoValue(fieldNameList, orderFomrInfo, queryOrderInfo);
								refreshDetails(details, i, orderFomrInfo, queryOrderInfo);
							}
						}
						else
						{
							failedMap.put(orderFomrInfo, "数据库中已存在该数据");
							flag = true;
						}
					}
					
					if (flag)
					{
						details.remove(orderFomrInfo);
					}
				}
				
			}
			
			deleteByUnique(deleteList);
			
		}
		
	}
	
	/**
	 * 刷新导入结果集列表
	 * 
	 * @param details
	 *        导入解析后的结果集列表
	 * @param i
	 *        当前结果集details下标
	 * @param orderFomrInfo
	 *        需要从导入数据结果集中删除的对象
	 * @param queryOrderInfo
	 *        需要从新增入导入数据结果集中的对象，默认添加至当前下标i位置
	 */
	private void refreshDetails(List<OrderFormInfo> details, int i, OrderFormInfo orderFomrInfo,
			OrderFormInfo queryOrderInfo)
	{
		if (CollectionUtils.isNotEmpty(details))
		{
			try
			{
				details.remove(i);
				orderFomrInfo = null;
				details.add(i, queryOrderInfo);
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
	}
	
	/**
	 * 设置订货信息，并保留订货信息计算的对应原始库存信息
	 * 
	 * @param fieldNameList
	 *        orderFomrInfo对象订意的属性名列表
	 * @param orderFomrInfo
	 *        订货信息实体bean
	 * @param queryOrderInfo
	 *        从数据库存中查询，获取的订货信息实体bean
	 */
	private void setOrderFormInfoValue(Vector<String> fieldNameList, OrderFormInfo orderFomrInfo,
			OrderFormInfo queryOrderInfo)
	{
		if (null != queryOrderInfo && null != orderFomrInfo)
		{
			// 更新数据库
			ExcelToBeanUtils.reflectSetValue(fieldNameList, orderFomrInfo, queryOrderInfo);
		}
	}
	
	private void deleteByUnique(List<OrderFormInfo> deleteList)
	{
		if (CollectionUtils.isNotEmpty(deleteList))
		{
			try
			{
				orderFormInfoDAO.delete(deleteList);
				orderFormInfoDAO.flush();
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
	}
	
	/**
	 * 查询唯一性数据,根据69+所属平台编号
	 * 
	 * @param orderFormInfo
	 *        订货单与到货单实体bean
	 * @return OrderFormInfo 订货单与到货单实体bean
	 */
	public OrderFormInfo findByUnique(OrderFormInfo orderFormInfo)
	{
		if (null != orderFormInfo)
		{
			List<OrderFormInfo> formInfos = findByUpccodeAndBnAndBrandId(orderFormInfo.getUpccode(), orderFormInfo
					.getMaterialNumber(), orderFormInfo.getBrandId());
			
			return CollectionUtils.isNotEmpty(formInfos) ? formInfos.get(0) : null;
		}
		
		return null;
	}
	
	/**
	 * 根据前端查询条件，查询数据生成Excel，并返回Excel文件的完整路径
	 * 
	 * @param searchFilters
	 *        页面查询条件对象
	 * @return String Excel文件的完整路径
	 */
	public String exportExcel(Collection<SearchFilter> searchFilters)
	{
		String tmpPath = null;
		if (null != searchFilters)
		{
			List<OrderPageInfo> orderPageInfos = findByExportData(searchFilters);
			tmpPath = ExcelFileUtils.createExportExcel(orderPageInfos, OrderPageInfo.class, OrderPageInfo.class,
					TemplateConstants.EXPORT_ORDER_EXPORT_MODEL_PATH);
		}
		
		return tmpPath;
	}
}
