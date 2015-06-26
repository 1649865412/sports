package com.innshine.stockmanager.stockanalysis.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import com.base.dao.SqlDao;
import com.base.dao.sql.ConvertPageQueryFieldsToSQL;
import com.base.dao.sql.SQLHelper;
import com.base.dao.sql.SQLProperties;
import com.base.util.dwz.Page;
import com.base.util.persistence.SearchFilter;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.innshine.shopAnalyse.util.Excel2Html;
import com.innshine.shopAnalyse.util.ExcelModelTool;
import com.innshine.stockmanager.stockanalysis.StockAnalysisConstants;
import com.innshine.stockmanager.stockanalysis.controller.StockAnalysisController;
import com.innshine.stockmanager.stockanalysis.entity.StockAnalysisInfo;
import com.innshine.stockmanager.stockanalysis.entity.StockAnalysisInfoModel;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.excel.template.ExcelExportByTemplate;
import com.utils.excel.template.ExportConfig;

@Service
@Transactional
public class StockAnalysisService
{
	/**
	 * 销售结果时间 SQL 参数名称
	 */
	private static final String FIELD_SQL_END_SALES_TIME = "endSalesTime";
	
	/**
	 * 销售开始时间 SQL 参数名称
	 */
	private static final String FIELD_SQL_START_SALES_TIME = "startSalesTime";
	
	/**
	 * 未来库存查询SQL KEY
	 */
	private static final String STOCK_ANALYSIS_FUTURE_STOCK_NUMBER_SQL = "stock.analysis.future.stock.number.sql";
	
	/**
	 * 未来库存查询时间格式
	 */
	private static final String FORMAT_YYYY_MM = "yyyy-MM";
	
	/**
	 * 下划线
	 */
	private static final String UNDER_LINE = "_";
	
	/**
	 * 选中的多选框下标集合
	 */
	// private static final String GROUP_TYPE_INDEX = "group_type_index";
	
	/**
	 * 显示字段
	 */
	private static final String GROUP_BY_SHOW_FIELD_NAMS = "groupByShowFieldNams";
	
	/**
	 * sql中排序列表
	 */
	private static final String GROUP_BY_FIELD_NAMS = "groupByFieldNams";
	
	/**
	 * 库存分析SQL
	 */
	private static final String STOCK_ANALYSIS_SQL = "stock.analysis.sql";
	
	/**
	 * product_info 表查询别名
	 */
	private static final String STOCK_ANALYSIS_PRODUCT_INFO_AS_NAME_SQL = "stock.analysis.product_info.as.name.sql";
	
	/**
	 * 逗号
	 */
	private static final String REGEX = ",";
	
	/**
	 * 排序列表
	 */
	// private static final String ORDER_LIST_VALUE = "order_list_value";
	
	/**
	 * 前台页面选择的分类列数组
	 */
	public static final String GROUP_TYPE = "group_type";
	/**
	 * 前台页面选择的分类列名称数组
	 */
	public static final String GROUP_NAME = "group_name";
	
	/**
	 * 选择未来到货字段名
	 */
	public static final String FUTURE_ARRIVE_FIELD_NAME = "futureArriveName";
	/**
	 * 选择未来到货时间
	 */
	public static final String FUTURE_ARRIVE_TIME_NAME = "factArriveTime";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StockAnalysisController.class);
	
	/**
	 * 实体bean字段与名称对应字段
	 */
	private static final Map<String, String> FIELD_NAME = new HashMap<String, String>();
	
	@Autowired
	private SqlDao sqlDao;
	
	static
	{
		FIELD_NAME.put("productType", "种类");
		FIELD_NAME.put("quater", "季节");
		FIELD_NAME.put("series", "系列");
		FIELD_NAME.put("inlineOr2ndline", "Line");
		FIELD_NAME.put("productSex", "性别");
	}
	
	/**
	 * 页面展示SQL
	 */
	private static final String INVENTORY_ANALYSIS_PAGE_SHOW = "inventory.analysis.page.show";
	
	/**
	 * 默认进入库存分析页面查询对象
	 * 
	 * @param searchFilters
	 *        前端页面查询对象
	 * @param page
	 *        分页对象
	 * @return List< StockAnalysisInfo >
	 */
	public List<StockAnalysisInfo> findByPage(Collection<SearchFilter> searchFilters, Page page)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Collection<SearchFilter> searchFilters " + searchFilters);
		}
		
		Map<String, Object> params = getParams(searchFilters, page);
		
		List<StockAnalysisInfo> analysisInfos = null;
		try
		{
			analysisInfos = sqlDao.queryByPage(params, INVENTORY_ANALYSIS_PAGE_SHOW, page, StockAnalysisInfo.class);
			
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Result analysisInfos List =  " + analysisInfos);
		}
		
		return analysisInfos;
	}
	
	/**
	 * 默认进入库存分析页面查询对象
	 * 
	 * @param searchFilters
	 *        前端页面查询对象
	 * @param page
	 *        分页对象
	 * @return List< StockAnalysisInfo >
	 */
	public <T> List<T> findByExportData(Map<String, Object> params, String sqlKey, Class<T> classz)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Collection<SearchFilter> searchFilters " + params);
		}
		
		List<T> analysisInfos = null;
		try
		{
			analysisInfos = sqlDao.queryListBySql(params, sqlKey, classz);
			
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Result analysisInfos List =  " + analysisInfos);
		}
		
		return analysisInfos;
	}
	
	/**
	 * 根据前端页面，查询参数列表，拼接传入SQL部分参数数值
	 * 
	 * @param searchFilters
	 *        根据前端页面，查询参数列表
	 * @param page
	 *        分页对象，主要使用里面的排序对象
	 * 
	 * @return Map<String, Object>
	 */
	private Map<String, Object> getParams(Collection<SearchFilter> searchFilters, Page page)
	{
		Map<String, Object> params = null;
		if (CollectionUtils.isNotEmpty(searchFilters))
		{
			params = new HashMap<String, Object>();
			boolean isAddQueryDetaulfSalesTime = true;
			for (SearchFilter searchFilter : searchFilters)
			{
				if (null != searchFilter)
				{
					String tmpFieldName = searchFilter.getFieldName();
					params.put(tmpFieldName, searchFilter.getValue());
					
					// 判断是否选择了销售时间字段
					if (FIELD_SQL_START_SALES_TIME.equalsIgnoreCase(tmpFieldName)
							|| FIELD_SQL_END_SALES_TIME.equalsIgnoreCase(tmpFieldName))
					{
						isAddQueryDetaulfSalesTime = false;
					}
				}
			}
			
			if (isAddQueryDetaulfSalesTime)
			{
				addQuerySalesTimeToMap(params);
			}
			
			if (null != page)
			{
				if (StringUtils.isNotEmpty(page.getOrderField()) && StringUtils.isNotEmpty(page.getOrderDirection()))
				{
					params.put("orderField", ConvertPageQueryFieldsToSQL.fieldNameToColumName(page.getOrderField()));
					params.put("orderDirection", page.getOrderDirection());
				}
			}
		}
		
		return params;
	}
	
	/**
	 * 添加查询条件至map集合中，默认销售时间查询范围为：2014-08-30~2014-09-30 ，一个月内
	 * <p>
	 * 
	 * @param params
	 *        构造的SQL 参数对象
	 */
	private void addQuerySalesTimeToMap(Map<String, Object> params)
	{
		if (MapUtils.isNotEmpty(params))
		{
			params.put(FIELD_SQL_END_SALES_TIME, DateUtils.getNow(DateUtils.SIMPLE_DEFAULT_FORMAT));
			params.put(FIELD_SQL_START_SALES_TIME, getLastMonthDateFormatStr());
		}
	}
	
	/**
	 * 获取最近时间往前推一个月，即查询开始时间 *
	 * <p>
	 * 
	 * @return 获取当前时间往前推一个月 ,如：2014-08-30~2014-09-30
	 */
	private Object getLastMonthDateFormatStr()
	{
		String format = null;
		try
		{
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -1);
			
			format = DateUtils.format(calendar.getTime(), DateUtils.SIMPLE_DEFAULT_FORMAT);
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		return format;
	}
	
	/**
	 * 根据排序字段，获取字段最终排放位置
	 * 
	 * @param request
	 *        请求对象
	 * @return String[]
	 */
	public String[] getGroupFieldNamesList(HttpServletRequest request)
	{
		String[] returnSplitGroups = null;
		if (null != request)
		{
			String grupType = request.getParameter(GROUP_TYPE);
			// String grupTypeIndex = request.getParameter(GROUP_TYPE_INDEX);
			// String orderListValues = request.getParameter(ORDER_LIST_VALUE);
			if (StringUtils.isNotEmpty(grupType))
			{
				String[] splitGroupsType = grupType.split(REGEX);
				// String[] splitGrupTypeIndexs = grupTypeIndex.split(REGEX);
				// String[] splitOrderListValues =
				// StringUtils.isNotEmpty(orderListValues) ?
				// orderListValues.split(REGEX)
				// : null;
				
				returnSplitGroups = splitGroupsType;
				
				// if (ArrayUtils.isNotEmpty(splitGroupsType) &&
				// ArrayUtils.isNotEmpty(splitOrderListValues))
				// {
				// // int[] orderList =
				// // getOrderList(splitGrupTypeIndexs.length,
				// // splitOrderListValues,
				// // splitGrupTypeIndexs);
				//					
				// // 记录原始字段下标列表
				// // int[] indexValue = new int[orderList.length];
				// // System.arraycopy(orderList, 0, indexValue, 0,
				// // indexValue.length);
				//					
				// // 排序，默认升序排列
				// // Arrays.sort(orderList);
				//					
				// // returnSplitGroups = getGroups(orderList, indexValue,
				// // splitGroupsType);
				// }
			}
			else
			{
				if (LOGGER.isInfoEnabled())
				{
					// LOGGER.info(" Group type  grupType " + grupType +
					// ",grupTypeIndex = " + grupTypeIndex
					// + ",orderListValues=" + orderListValues);
				}
			}
		}
		
		return returnSplitGroups;
		
	}
	
	/**
	 * 获取排序字段
	 * 
	 * @param length
	 *        选择的字段长度
	 * @param splitOrderListValues
	 *        排序数据下拉列表
	 * @param splitGrupTypeIndexs
	 *        选择的字段下标数组
	 * @return int[]
	 */
	// private int[] getOrderList(int length, String[] splitOrderListValues,
	// String[] splitGrupTypeIndexs)
	// {
	// int[] orderList = new int[length];
	// for (int i = 0; i < length; i++)
	// {
	// try
	// {
	// orderList[i] =
	// Integer.parseInt(splitOrderListValues[Integer.parseInt(splitGrupTypeIndexs[i])]);
	// }
	// catch (Exception e)
	// {
	// LOGGER.error(Exceptions.getStackTraceAsString(e));
	// }
	// }
	//		
	// return orderList;
	// }
	
	/**
	 * 获取排序完成后的字段列表
	 * <p>
	 * 
	 * @param orderList
	 *        排字序后的数据列表，默认升序排列
	 * @param indexValue
	 *        排序之前的原始数据列表
	 * @param splitGroups
	 *        页面选择的字段列表
	 * @return String[]
	 */
	// private String[] getGroups(int[] orderList, int[] indexValue, String[]
	// splitGroups)
	// {
	// if (ArrayUtils.isNotEmpty(orderList) && ArrayUtils.isNotEmpty(indexValue)
	// && ArrayUtils.isNotEmpty(splitGroups))
	// {
	// String[] tmpGroupFieldNames = new String[] {};
	//			
	// for (int i = orderList.length - 1; i >= 0; i--)
	// {
	// String tmpFieldName = getIndeValueByOrder(orderList[i], indexValue,
	// splitGroups);
	// if (!ArrayUtils.contains(tmpGroupFieldNames, tmpFieldName))
	// {
	// tmpGroupFieldNames = ArrayUtils.add(tmpGroupFieldNames, tmpFieldName);
	// }
	// }
	//			
	// return tmpGroupFieldNames;
	// }
	//		
	// return null;
	// }
	//	
	/**
	 * 根据排完序下标列表，获取对应字段,根据命名规则，字母为大写之前添加一个"_"，并把大写字段转换为小写
	 * <p>
	 * 
	 * @param orderBy
	 *        排序之后的下标值
	 * @param indexValue
	 *        原始数据值
	 * @param splitGroups
	 *        选择的字段列表
	 * @return fieldName
	 */
	// private String getIndeValueByOrder(int orderBy, int[] indexValue,
	// String[] splitGroups)
	// {
	// try
	// {
	// for (int i = 0; i < indexValue.length; i++)
	// {
	// if (indexValue[i] == orderBy)
	// {
	// return splitGroups[i];//
	// ConvertPageQueryFieldsToSQL.fieldNameToColumName(splitGroups[i]);
	// }
	// }
	// }
	// catch (Exception e)
	// {
	// LOGGER.error(Exceptions.getStackTraceAsString(e));
	//			
	// }
	//		
	// return null;
	// }
	
	/**
	 * 构造导出对象
	 * 
	 * @param searchFilters
	 *        页面查询条件对象
	 * @param groupOrderList
	 *        字段列表
	 * @param factArriveTime
	 *        是否查询未来库存
	 * @param futureArriveName
	 *        未来库存时间
	 * @param 显示的字段名
	 * @return ExportConfig< StockAnalysisInfoModel >
	 */
	public ExportConfig<StockAnalysisInfoModel> getExportConfig(Collection<SearchFilter> searchFilters,
			String[] groupOrderList, String groupFieldName, String futureArriveName, String factArriveTime)
	{
		ExportConfig<StockAnalysisInfoModel> exportConfig = new ExportConfig<StockAnalysisInfoModel>();
		
		// 构造参数列表
		Map<String, Object> params = getMapParams(searchFilters, groupOrderList, groupFieldName, futureArriveName,
				factArriveTime);
		
		List<StockAnalysisInfoModel> analysisInfoModels = findByExportData(params, STOCK_ANALYSIS_SQL,
				StockAnalysisInfoModel.class);
		// 更新总库存、总吊牌价、总未来库存
		modifyTotalNumberAndPercentage(analysisInfoModels);
		exportConfig.setDisplayProperties(getDisplayProperties(groupFieldName, groupOrderList, futureArriveName,
				factArriveTime));
		exportConfig.setName(StockAnalysisConstants.STOCK_ANALYSIS_NAME);
		exportConfig.setGroupProperties(getGgroupProperties(groupFieldName, groupOrderList));
		exportConfig.setConditionText("");
		exportConfig.setRootCls(StockAnalysisInfoModel.class);
		exportConfig.setHeader(getHeader(groupFieldName, groupOrderList, futureArriveName, factArriveTime));
		exportConfig.setData(analysisInfoModels);
		return exportConfig;
	}
	
	/**
	 * 
	 * 更新总库存、总吊牌价、总未来库存
	 * <p>
	 * 
	 * 
	 * @param analysisInfoModels
	 */
	private void modifyTotalNumberAndPercentage(List<StockAnalysisInfoModel> analysisInfoModels)
	{
		if (CollectionUtils.isNotEmpty(analysisInfoModels))
		{
			Long tmpTotalStockNumber = 0L;
			Double tmpTotalTagPrice = 0.0;
			Long tmpTotalFutureStockNumber = 0L;
			
			for (StockAnalysisInfoModel stockAnalysisInfoModel : analysisInfoModels)
			{
				if (null != stockAnalysisInfoModel)
				{
					Integer stockNumber = stockAnalysisInfoModel.getStockNumber();
					if (null != stockNumber)
					{
						tmpTotalStockNumber += stockNumber;
					}
					
					BigDecimal tagPrice = stockAnalysisInfoModel.getTagPrice();
					if (null != tagPrice)
					{
						stockAnalysisInfoModel.setTagPrice(tagPrice);
						tmpTotalTagPrice += tagPrice.doubleValue();
					}
					
					Long futureStockNumber = stockAnalysisInfoModel.getFutureStockNumber();
					if (null != futureStockNumber)
					{
						tmpTotalFutureStockNumber += futureStockNumber;
					}
				}
				
			}
			
			for (StockAnalysisInfoModel stockAnalysisInfoModel : analysisInfoModels)
			{
				if (null != stockAnalysisInfoModel)
				{
					stockAnalysisInfoModel.setTotalStockNumber(tmpTotalStockNumber);
					stockAnalysisInfoModel.setTotalTagPrice(new BigDecimal(tmpTotalTagPrice));
					stockAnalysisInfoModel.setTotalFutureStockNumber(tmpTotalFutureStockNumber);
					
					stockAnalysisInfoModel.setStockNumberPercentage(stockAnalysisInfoModel.getStockNumberPercentage());
					stockAnalysisInfoModel.setTagPricePercentage(stockAnalysisInfoModel.getTagPricePercentage());
					stockAnalysisInfoModel.setFutureStockNumberPercentage(stockAnalysisInfoModel
							.getFutureStockNumberPercentage());
				}
			}
		}
	}
	
	/**
	 * 获取分组的字段列表
	 * 
	 * @param groupFieldName
	 *        页面选择的字段列表
	 * @param groupOrderList
	 *        排序好的字段列表
	 * @return Vector< String>
	 */
	private Vector<String> getGgroupProperties(String groupFieldName, String[] groupOrderList)
	{
		Vector<String> vector = new Vector<String>();
		// vector.add("inlineOr2ndline");
		if (ArrayUtils.isNotEmpty(groupOrderList))
		{
			CollectionUtils.addAll(vector, groupOrderList);
		}
		
		if (StringUtils.isNotEmpty(groupFieldName))
		{
			return getGroupFieldNames(vector, groupFieldName.split(REGEX));
		}
		
		return vector;
	}
	
	/**
	 * 获取需要展示的字段列表
	 * 
	 * @param groupFieldName
	 *        前端选择列对象
	 * @param groupOrderList
	 *        排序完成的字段列表
	 * @param factArriveTime
	 *        是否查询未来库存
	 * @param futureArriveName
	 *        未来库存时间
	 * @return Vector<String>
	 */
	private Vector<String> getDisplayProperties(String groupFieldName, String[] groupOrderList,
			String futureArriveName, String factArriveTime)
	{
		Vector<String> vector = new Vector<String>();
		
		if (ArrayUtils.isNotEmpty(groupOrderList))
		{
			CollectionUtils.addAll(vector, groupOrderList);
		}
		
		if (StringUtils.isNotEmpty(groupFieldName))
		{
			String[] tmpFieldNames = groupFieldName.split(REGEX);
			for (String tmpFieldName : tmpFieldNames)
			{
				if (!vector.contains(tmpFieldName))
				{
					vector.add(tmpFieldName);
				}
			}
		}
		
		CollectionUtils.addAll(vector, StockAnalysisConstants.STOCK_ANALYSIS_PUBLIC_FIELD_NAME);
		
		// 显示未来库存
		if (isFutureArriveStockNumber(futureArriveName, factArriveTime))
		{
			CollectionUtils.addAll(vector, StockAnalysisConstants.STOCK_ANALYSIS_FUTURE_STOCK_NUMBER__FIELD_NAME);
		}
		
		return vector;
	}
	
	/**
	 * 获取分组的字段列表，并把字段中对象将abc_bbc 转成AbcBbc; 并只添加
	 * 
	 * @param vector
	 * 
	 * @param groupOrderList
	 *        前端页面选择的字段列表
	 * @return Vector< String>
	 */
	private Vector<String> getGroupFieldNames(Vector<String> vector, String[] groupOrderList)
	{
		
		if (ArrayUtils.isNotEmpty(groupOrderList))
		{
			for (String groupFieldName : groupOrderList)
			{
				if (!vector.contains(groupFieldName))
				{
					vector.add(groupFieldName);
				}
			}
		}
		
		return vector;
	}
	
	/**
	 * 标题头
	 * <p>
	 * 
	 * @param groupNames
	 *        前端页面选择的lable名称
	 * @param groupFieldName
	 *        字段列表
	 * @param groupOrderList
	 *        排完序的字段列表
	 * @param factArriveTime
	 * @param futureArriveName
	 * 
	 * @return Vector< String>
	 */
	private Vector<String> getHeader(String groupFieldName, String[] groupOrderList, String futureArriveName,
			String factArriveTime)
	{
		Vector<String> vector = new Vector<String>();
		
		if (StringUtils.isNotEmpty(groupFieldName) && ArrayUtils.isNotEmpty(groupOrderList))
		{
			// 先添加排序后的字段
			for (String groupOrder : groupOrderList)
			{
				getMapHeadTitleName(vector, groupOrder);
			}
			
			String[] groupFieldNames = groupFieldName.split(REGEX);
			if (ArrayUtils.isNotEmpty(groupFieldNames))
			{
				for (int i = 0; i < groupFieldNames.length; i++)
				{
					if (StringUtils.isNotBlank(groupFieldNames[i])
							&& !ArrayUtils.contains(groupOrderList, groupFieldNames[i]))
					{
						getMapHeadTitleName(vector, groupFieldNames[i]);
					}
				}
			}
		}
		
		CollectionUtils.addAll(vector, StockAnalysisConstants.EXCEL_HEAD_TITLE);
		
		// 显示未来库存标题
		if (isFutureArriveStockNumber(futureArriveName, factArriveTime))
		{
			String[] futures = new String[StockAnalysisConstants.EXCEL_FUTURE_HEAD_TITLE.length];
			System.arraycopy(StockAnalysisConstants.EXCEL_FUTURE_HEAD_TITLE, 0, futures, 0, futures.length);
			
			if (ArrayUtils.isNotEmpty(futures))
			{
				futures[0] = factArriveTime + futures[0];
			}
			
			CollectionUtils.addAll(vector, futures);
		}
		
		return vector;
	}
	
	private void getMapHeadTitleName(Vector<String> vector, String groupFieldName)
	{
		if (StringUtils.isNotEmpty(groupFieldName))
		{
			String headerTitleName = FIELD_NAME.get(groupFieldName);
			if (StringUtils.isNotEmpty(headerTitleName))
			{
				vector.add(headerTitleName);
			}
		}
	}
	
	/**
	 * 构造查询中的SQL参数列表
	 * 
	 * @param searchFilters
	 *        页面查询条件对象
	 * @param groupOrderList
	 *        排序字段列表
	 * @param groupFieldName
	 *        数据查询显示的字段名字
	 * @param factArriveTime
	 *        是否查询未来库存
	 * @param futureArriveName
	 *        未来库存时间
	 * @return Map< String, Object >
	 */
	private Map<String, Object> getMapParams(Collection<SearchFilter> searchFilters, String[] groupOrderList,
			String groupFieldName, String futureArriveName, String factArriveTime)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		boolean isAddQueryDetaulfSalesTime = true;
		if (CollectionUtils.isNotEmpty(searchFilters))
		{
			for (SearchFilter filter : searchFilters)
			{
				if (null != filter)
				{
					String tmpFieldName = filter.getFieldName();
					map.put(tmpFieldName, filter.getValue());
					
					// 判断是否选择了销售时间字段
					if (FIELD_SQL_START_SALES_TIME.equalsIgnoreCase(tmpFieldName)
							|| FIELD_SQL_END_SALES_TIME.equalsIgnoreCase(tmpFieldName))
					{
						isAddQueryDetaulfSalesTime = false;
					}
				}
			}
		}
		
		if (isAddQueryDetaulfSalesTime)
		{
			addQuerySalesTimeToMap(map);
		}
		
		if (ArrayUtils.isNotEmpty(groupOrderList))
		{
			String joinStr = ArrayUtils.toString(groupOrderList);
			String asName = SQLProperties.getInstance().getSql(STOCK_ANALYSIS_PRODUCT_INFO_AS_NAME_SQL);
			if (StringUtils.isNotEmpty(asName) && StringUtils.isNotEmpty(joinStr))
			{
				joinStr = joinStr.substring(1, joinStr.lastIndexOf("}"));
				joinStr = joinStr.replaceAll(REGEX, REGEX + asName.trim());
				joinStr = asName.trim() + joinStr;
			}
			
			map.put(GROUP_BY_FIELD_NAMS, ConvertPageQueryFieldsToSQL.fieldNameToColumName(joinStr));
		}
		
		if (StringUtils.isNotEmpty(groupFieldName))
		{
			map.put(GROUP_BY_SHOW_FIELD_NAMS, ConvertPageQueryFieldsToSQL.fieldNameToColumName(groupFieldName));
			setSqlWhere(map, groupFieldName);
		}
		
		// 判断是否选择了未来库存且库存时间大于当前时间
		if (isFutureArriveStockNumber(futureArriveName, factArriveTime))
		{
			// 获取查询未来库存SQL
			String futreArrivesSql = getFutreArrivesSql(map, factArriveTime);
			if (StringUtils.isNotEmpty(futreArrivesSql))
			{
				map.put("futureStockNumber", futreArrivesSql);
			}
		}
		
		return map;
	}
	
	/**
	 * 根据当前查询条件，生成查询未来库存SQL，返回SQL字符串用于拼接
	 * 
	 * @param map
	 *        查询SQL参数列表
	 * @param factArriveTime
	 *        未来到货时间
	 * @return 生成查询未来库存SQL
	 */
	private String getFutreArrivesSql(Map<String, Object> map, String factArriveTime)
	{
		Map<String, Object> futrenMap = new HashMap<String, Object>();
		if (MapUtils.isNotEmpty(map))
		{
			futrenMap.putAll(map);
		}
		
		if (StringUtils.isNotEmpty(factArriveTime))
		{
			// 拼接查询SQL中的参数列表
			futrenMap.put("startPredictArriveTime", DateUtils.getNow(FORMAT_YYYY_MM));
			futrenMap.put("endPredictArriveTime", factArriveTime);
		}
		
		if (MapUtils.isNotEmpty(futrenMap))
		{
			try
			{
				// Object obj = map.get(GROUP_BY_FIELD_NAMS);
				// if (null != obj)
				// {
				// String tmpStr = String.valueOf(obj);
				// map.put(GROUP_BY_FIELD_NAMS, tmpStr.replaceAll("p\\.",
				// "p1."));
				// }
				
				String sql = SQLProperties.getInstance().getSql(STOCK_ANALYSIS_FUTURE_STOCK_NUMBER_SQL);
				
				SQLHelper sqlHelper = new SQLHelper(sql);
				
				return sqlHelper.parepareSQLtext(futrenMap);
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return null;
	}
	
	/**
	 * 判断是否选择了未来库存，并且选择的未来库存时间大于当前时间
	 * 
	 * @param futureArriveName
	 *        需要导出未来库存标识
	 * @param factArriveTime
	 *        选择的导出未来库存时间
	 * @return true: 需要导出未来库存， false:不需要
	 */
	private boolean isFutureArriveStockNumber(String futureArriveName, String factArriveTime)
	{
		if (StringUtils.isNotEmpty(futureArriveName) && StringUtils.isNotEmpty(factArriveTime))
		{
			Date currDate = DateUtils.toDate(DateUtils.getNow(FORMAT_YYYY_MM), FORMAT_YYYY_MM);
			Date selceDate = DateUtils.toDate(factArriveTime, FORMAT_YYYY_MM);
			if (null != selceDate && null != currDate)
			{
				// （逻辑:当选择未来到货并且到货时间不为空，导出分类分析库存时，如果到货时间大于当前时间，则在报表中显示未来到货，
				// 否则，不会显示。未来到货数量=订货数量-到货数量
				if (selceDate.getTime() >= currDate.getTime())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 设置查询条件参数
	 * 
	 * @param map
	 * @param groupFieldName
	 */
	private void setSqlWhere(Map<String, Object> map, String groupFieldName)
	{
		if (null != map && StringUtils.isNotEmpty(groupFieldName))
		{
			String[] splitGroups = groupFieldName.split(REGEX);
			
			for (String groupType : splitGroups)
			{
				if (StringUtils.isNotEmpty(groupType))
				{
					String coloumName = ConvertPageQueryFieldsToSQL.fieldNameToColumName(groupType);
					
					// 对没有下划线的命名字段，特殊处理，防止与其它where中相同命名的SQL冲突
					String tmpColumnName = coloumName;
					if (coloumName.indexOf(UNDER_LINE) == -1)
					{
						tmpColumnName = UNDER_LINE + tmpColumnName;
					}
					
					map.put(tmpColumnName, coloumName);
				}
			}
		}
		
	}
	
	/**
	 * 把Excel转换为HTML
	 * 
	 * @param hssfWorkbook
	 *        工作薄对象
	 * @return 返回转换成功后的文件路径
	 */
	public String getExcelToHtml(HSSFWorkbook hssfWorkbook) throws Exception
	{
		String tmpPath = null;
		if (null != hssfWorkbook)
		{
			String path = new ExcelModelTool().getPathString() + (new Date().getTime());
			FileOutputStream os = null;;
			try
			{
				os = new FileOutputStream(path);
				
				hssfWorkbook.write(os);
				
				tmpPath = Excel2Html.ExcelToHtml(path);
				
				ExcelFileUtils.deleteFile(new File(path));
			}
			catch (FileNotFoundException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
				throw e;
			}
			catch (IOException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
				throw e;
			}
			catch (TransformerException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
				throw e;
			}
			catch (ParserConfigurationException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
				throw e;
			}
			catch (SAXException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
				throw e;
			}
			finally
			{
				if (os != null)
				{
					try
					{
						os.close();
					}
					catch (IOException e)
					{
						LOGGER.error(Exceptions.getStackTraceAsString(e));
					}
				}
			}
			
		}
		
		return tmpPath;
	}
	
	/**
	 * 写入合计数据，
	 * 
	 * @param exportConfig
	 *        导出对象
	 * @param hssfWorkbook
	 *        导出的Excel对象
	 * @param factArriveTime
	 *        是否查询未来库存
	 * @param futureArriveName
	 *        未来库存时间
	 */
	public void writerTotalData(ExportConfig<StockAnalysisInfoModel> exportConfig, HSSFWorkbook hssfWorkbook,
			String futureArriveName, String factArriveTime)
	{
		if (exportConfig != null && null != hssfWorkbook)
		{
			// 获取分组字段列表
			Vector<String> groupVector = exportConfig.getGroupProperties();
			
			// 获取数据列表
			List<StockAnalysisInfoModel> analysisInfoModels = exportConfig.getData();
			
			if (CollectionUtils.isNotEmpty(analysisInfoModels))
			{
				// 开始行下标，默认第1行为标题行
				int startROwIndex = analysisInfoModels.size() + 2;
				
				// 开始列下标
				int startCellIndex = 0;
				
				// 需要跳过的列数
				int breakCellCount = null != groupVector ? groupVector.size() : 0;
				
				// 需要写入的数据对象
				StockAnalysisInfoModel analysisInfoModel = analysisInfoModels.get(0);
				
				boolean isFutureArrivStockNumber = isFutureArriveStockNumber(futureArriveName, factArriveTime);
				writeExcel(analysisInfoModel, startROwIndex, startCellIndex, breakCellCount, hssfWorkbook,
						isFutureArrivStockNumber);
			}
		}
		
	}
	
	/**
	 * 真实写入EXCEL中的数据
	 * 
	 * @param analysisInfoModel
	 *        模型对象
	 * @param startROwIndex
	 *        开始行下标
	 * @param startCellIndex
	 *        开始列下标
	 * @param breakCellCount
	 *        需要跳过的列数
	 * @param hssfWorkbook
	 *        工作薄对象
	 * @param isFutureArrivStockNumber
	 *        ture:选择了未来库存 false:未选择
	 */
	private void writeExcel(StockAnalysisInfoModel analysisInfoModel, int startROwIndex, int startCellIndex,
			int breakCellCount, HSSFWorkbook hssfWorkbook, boolean isFutureArrivStockNumber)
	{
		
		if (null != analysisInfoModel && null != hssfWorkbook)
		{
			// 默认只处理第1 张 sheet
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			
			HSSFRow hssfRow = hssfSheet.createRow(startROwIndex);
			if (null != hssfRow)
			{
				
				createCellAndSetValue(hssfWorkbook, hssfRow, "总计", startCellIndex);
				
				// 写入空行
				for (int i = 1; i <= breakCellCount; i++)
				{
					createCellAndSetValue(hssfWorkbook, hssfRow, " ", i);
				}
				String[] fieldTotalArrays = StockAnalysisConstants.FILED_TOTAL_ARRY;
				
				// 是否需要展示未来库存字段
				if (isFutureArrivStockNumber)
				{
					fieldTotalArrays = ArrayUtils.addAll(fieldTotalArrays,
							StockAnalysisConstants.FILED_FUTURE_STOCK_NUMBER_TOTAL_ARRY);
				}
				
				for (int i = 0; i < fieldTotalArrays.length; i++)
				{
					try
					{
						Object object = com.utils.reflection.ReflectionUtils.getFieldValue(analysisInfoModel,
								fieldTotalArrays[i]);
						createCellAndSetValue(hssfWorkbook, hssfRow, String.valueOf(null == object ? "-" : object),
								breakCellCount + i);
					}
					catch (Exception e)
					{
						LOGGER.error(Exceptions.getStackTraceAsString(e));
					}
				}
				
			}
		}
		
	}
	
	private void createCellAndSetValue(HSSFWorkbook hssfWorkbook, HSSFRow hssfRow, String value, int i)
	{
		HSSFCell hssfCell = hssfRow.createCell(i);
		if (hssfCell != null)
		{
			hssfCell.setCellStyle(ExcelExportByTemplate.initLineStyle(hssfWorkbook));
			hssfCell.setCellValue(value);
		}
	}
}
