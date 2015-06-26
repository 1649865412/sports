package com.innshine.daily.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.utils.LogInfoUtilService;

import com.base.dao.sql.ConvertPageQueryFieldsToSQL;
import com.base.entity.main.Brand;
import com.base.service.BrandService;
import com.base.util.MailSenderService;
import com.innshine.daily.Constants;
import com.innshine.daily.dao.GatherDailyDao;
import com.innshine.daily.entity.DailyEntityInfo;
import com.innshine.daily.entity.DailyReportInfo;
import com.innshine.daily.utils.GatherDailyExcelExportUtils;
import com.innshine.emailmanager.service.SendEmailInfoService;
import com.utils.CalendarUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.reflection.ReflectionUtils;

/**
 * 汇总日报服务类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class GatherDailyService
{
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GatherDailyService.class);
	
	@Autowired
	private GatherDailyDao gatherDailyDao;
	
	@Autowired
	private DailyReportInfoService dailyReportInfoService;
	
	@Autowired
	private SendEmailInfoService emailInfoService;
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@Autowired
	private BrandService brandService;
	
	/**
	 * 日记记录业务类
	 * 
	 */
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	
	/**
	 * 执行汇总日志功能
	 */
	public void gatherDailyDoTask()
	{
		LOGGER.info("//=============================GatherDaily Info Data Cleaning Start=================================//");
		LOGGER.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		logInfoUtilService.info("汇总日报定时任务开始");
		try
		{
			gatherDaily();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			logInfoUtilService.error("汇总日报定时作业任务，请联系程序员");
		}
		
		LOGGER.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOGGER.info("//=============================GatherDaily  Info Data Cleaning End=================================//");
		logInfoUtilService.info("汇总日报定时任务结束");
		
	}
	
	/**
	 * 日报汇总
	 * <p>
	 */
	private void gatherDaily()
	{
		// 品牌列表
		List<Brand> brands = brandService.findAll();
		if (CollectionUtils.isNotEmpty(brands))
		{
			for (Brand brand : brands)
			{
				if (null != brand)
				{
					// 创建工作薄
					Workbook workbook = GatherDailyExcelExportUtils.createWorkbook(Constants.XSSF_WORKBOOK_TYPE);
					
					// 创建工作表
					Sheet sheet = GatherDailyExcelExportUtils.createSheet(workbook);
					
					// 记录上一次的已创建行数
					int preRowSize = 0;
					
					// 按类型 统计：销售数量、付款金额、吊牌金额、平均折扣
					int rowSzie = createProductTypeTable(workbook, sheet, brand);
					
					// 记录首次总行数,加一个合计，不包含标题行
					preRowSize = rowSzie;
					
					// 按Q季 ，统计：本月销售数量、累计销售、售馨率
					rowSzie = createQuaterTable(workbook, sheet, Constants.SHEET_DEFAULT_START_ROW,
							Constants.SHEET_SALES_TITLE.length, Constants.SHEET_DEFAULT_START_COLUMN, brand);
					
					// 两次创建为同行表格，开始列不一样，故只记录最大行数,并追加1行合计行。
					preRowSize = getRowSzie(preRowSize, rowSzie, preRowSize);
					
					// 按系列 统计：销售数量、付款金额、吊牌金额、平均折扣 、累计全年
					int tmpSeriesSize = createSeriesTable(workbook, sheet, preRowSize,
							Constants.SHEET_DEFAULT_START_ROW, brand);
					
					// 按性别 统计：销售数量、占比
					int tmpSexSize = createSexTable(workbook, sheet, preRowSize, Constants.SHEET_DEFAULT_START_ROW,
							getSeriesHeaderTitle().length, Constants.SHEET_DEFAULT_START_COLUMN,
							Constants.SHEET_SERIES_START_ROW, brand);
					
					preRowSize = getRowSzie(preRowSize, tmpSeriesSize, tmpSexSize);
					
					// 获取到货率
					createArrivalReteTable(workbook, sheet, preRowSize, Constants.SHEET_DEFAULT_START_ROW
							+ Constants.SHEET_SERIES_START_ROW + 3, brand);
					
					int stockStartRow = Constants.SHEET_DEFAULT_START_ROW + Constants.SHEET_STOCK_ROW;
					int stockStartColumn = Constants.SHEET_STOCK_DEFAULT_TITLE.length
							+ Constants.SHEET_DEFAULT_START_COLUMN + 1;
					
					// 按Q季 库存数据分析报表 ，统计库存数量、吊牌价金额
					int tmpQuaterStockSize = createStockTable(workbook, sheet, preRowSize + stockStartRow,
							Constants.SHEET_DEFAULT_START_COLUMN, Constants.FIELD_NAME_QUATER_TYPE,
							Constants.SHEET_STOCK_QUATER_TITLE, getStockFieldNames(Constants.FIELD_NAME_QUATER_TYPE),
							brand);
					
					// 按类别 库存数据分析报表 ，统计库存数量、吊牌价金额
					int tmpProductTypeStockSize = createStockTable(workbook, sheet, preRowSize + stockStartRow,
							stockStartColumn, Constants.FIELD_NAME_PRODUCT_TYPE, Constants.SHEET_STOCK_DEFAULT_TITLE,
							getStockFieldNames(Constants.FIELD_NAME_PRODUCT_TYPE), brand);
					
					preRowSize = getRowSzie(preRowSize, tmpQuaterStockSize, tmpProductTypeStockSize);
					
					// 按系列库存数据分析报表 ，统计库存数量、吊牌价金额
					createStockTable(workbook, sheet, preRowSize + stockStartRow + Constants.SHEET_DEFAULT_START_ROW,
							Constants.SHEET_DEFAULT_START_COLUMN, Constants.FIELD_NAME_SERIES_TYPE,
							Constants.SHEET_STOCK_DEFAULT_TITLE, getStockFieldNames(Constants.FIELD_NAME_SERIES_TYPE),
							brand);
					
					// 获取文件名与文件路径数组，该数组永远只保持两个长度，0：文件名 1:文件完整路径中不包含文件名
					String[] fileAttr = GatherDailyExcelExportUtils.saveFile(workbook, brand);
					
					// 记录入库
					DailyReportInfo saveInfo = dailyReportInfoService.addGatherDaily(fileAttr, brand);
					
					Thread sendMailThread = new Thread(new GatherDailySendMailThread(fileAttr, emailInfoService,
							mailSenderService, brand, saveInfo, dailyReportInfoService));
					
					sendMailThread.start();
					
					// try
					// {
					// sendMailThread.join();
					// }
					// catch (InterruptedException e)
					// {
					// e.printStackTrace();
					// }
					
				}
			}
		}
	}
	
	/**
	 * 生成到货率报表
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @param preRowSize
	 *        已创建的总行数
	 * @param defaultStartRow
	 *        默认开始行
	 */
	private void createArrivalReteTable(Workbook workbook, Sheet sheet, int preRowSize, int defaultStartRow, Brand brand)
	{
		if (null != workbook && null != sheet)
		{
			Map<String, Object> params = new HashMap<String, Object>();
			
			// 获取查询参数,以当前Q季第一天+最后一天，组成查询开始+结束时间
			getQueryTime(params, true, brand);
			
			List<DailyEntityInfo> dailyEntityInfos = gatherDailyDao.queryGatherDailyData(
					Constants.SQL_ARRIVAL_RATE_DAILY, params, DailyEntityInfo.class);
			if (null != dailyEntityInfos)
			{
				// 获取标题行
				String[] headerTitle = getSeasonArrivalRate(dailyEntityInfos);
				
				GatherDailyExcelExportUtils.writerExcel(workbook, sheet, preRowSize + defaultStartRow,
						Constants.SHEET_DEFAULT_START_COLUMN, dailyEntityInfos, headerTitle, new String[] {});
			}
			
		}
	}
	
	/**
	 * 获取按Q季，统计到货的标题行
	 * 
	 * @param dailyEntityInfos
	 *        查询原始数据
	 * @return 标题行
	 */
	private String[] getSeasonArrivalRate(List<DailyEntityInfo> dailyEntityInfos)
	{
		String[] tmpTitle = new String[] {};
		
		Calendar calendar = Calendar.getInstance();
		
		// 年份加Q季字段，如：2014Q1
		String seasonStr = getThisSeason(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
		
		tmpTitle = ArrayUtils.add(tmpTitle, seasonStr + Constants.SHEET_TITLE_ARRIVAL_RETE);
		
		if (CollectionUtils.isNotEmpty(dailyEntityInfos))
		{
			DailyEntityInfo dailyEntityInfo = dailyEntityInfos.get(0);
			if (null != dailyEntityInfo && null != dailyEntityInfo.getArrivalRate())
			{
				tmpTitle = ArrayUtils.add(tmpTitle, dailyEntityInfo.getArrivalRate() + Constants.SHEET_TITLE_PER_CENT);
			}
		}
		
		return tmpTitle;
	}
	
	/**
	 * 获取累计的创建行数，默认多计算一个合计行数
	 * 
	 * @param preRowSize
	 *        上一次创建行数
	 * @param tmpSize1
	 * @param tmpSize2
	 * @return
	 */
	private int getRowSzie(int preRowSize, int tmpSize1, int tmpSize2)
	{
		// 同上原理，只记录最大行数， 并追加1行合计行。
		preRowSize += tmpSize1 > tmpSize2 ? tmpSize1 : tmpSize2;
		preRowSize += Constants.SHEET_DEFAULT_TOTAL_ROW;
		return preRowSize;
	}
	
	/**
	 * 拼接库存分析报表的Field Name
	 * 
	 * @param fielName
	 *        字段名称
	 * @return String [] 接好的字段列表
	 */
	private String[] getStockFieldNames(String fielName)
	{
		String[] tmpArray = Constants.ARRAY_FIELD_NAME_DEFAULT_QUATER;
		if (StringUtils.isNotEmpty(fielName))
		{
			try
			{
				tmpArray = ArrayUtils.add(tmpArray, 0, fielName);
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return tmpArray;
	}
	
	/**
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @param preRowSize
	 *        已创建的总行数
	 * @param startColumn
	 *        开始列
	 * @param fieldName
	 *        需要查询的字段名，与实体bean属性名保持一致
	 * @param stockTitle
	 *        标题数组
	 * @param fieldNames
	 *        需要显示的字段数组
	 * @param brand
	 * @return int型 该记录创建行数
	 */
	private int createStockTable(Workbook workbook, Sheet sheet, int preRowSize, int startColumn, String fieldName,
			String[] stockTitle, String[] fieldNames, Brand brand)
	{
		int rowSize = 1;
		
		// 构造查询参数
		List<DailyEntityInfo> dailyEntityInfos = getQueryData(Constants.SQL_STOCK_DAILY, DailyEntityInfo.class,
				fieldName, brand);
		
		if (null != dailyEntityInfos)
		{
			rowSize += dailyEntityInfos.size();
			
			// 添加占比，添加总计
			modiftyPercentageAndAddTotal(dailyEntityInfos, fieldName);
			
			GatherDailyExcelExportUtils.writerExcel(workbook, sheet, preRowSize, startColumn, dailyEntityInfos,
					stockTitle, fieldNames);
			
		}
		
		return rowSize;
	}
	
	/**
	 * 按性别 统计：销售数量、占比
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @param preRowSize
	 *        已创建的总行数
	 * @param sheetDefaultStartRow
	 *        默认开始行数
	 * @param preEndColumn
	 *        上次创建结束列
	 * @param sheetDefaultStartColumn
	 *        默认开始列数
	 * @param sheetSeriesStartRow
	 *        系列默认开始行
	 * @param brand
	 * @return int型 该记录创建行数
	 */
	private int createSexTable(Workbook workbook, Sheet sheet, int preRowSize, int sheetDefaultStartRow,
			int preEndColumn, int sheetDefaultStartColumn, int sheetSeriesStartRow, Brand brand)
	{
		int rowSize = 0;
		
		// 构造查询参数
		List<DailyEntityInfo> dailyEntityInfos = getQueryData(Constants.SQL_SERIES_SEX_DAILY, DailyEntityInfo.class,
				brand);
		
		if (null != dailyEntityInfos)
		{
			rowSize = dailyEntityInfos.size();
			
			// 添加占比，添加总计
			modiftyPercentageAndAddTotal(dailyEntityInfos, Constants.FIELD_NAME_SEX_TYPE);
			
			GatherDailyExcelExportUtils.writerExcel(workbook, sheet, preRowSize + sheetDefaultStartRow
					+ sheetSeriesStartRow, sheetDefaultStartColumn + preEndColumn + 1, dailyEntityInfos,
					Constants.SHEET_SALES_SEX_TITLE, Constants.ARRAY_FIELD_NAME_SEX);
			
		}
		
		return rowSize;
	}
	
	/**
	 * 根据上一张表格开始行数，创建对应按系列分析报表
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @param rowSzie
	 *        上一个报表数据行数
	 * @param preStartRow
	 *        上一个报表开始行
	 * @param brand
	 * @return row size 总数据行数
	 */
	private int createSeriesTable(Workbook workbook, Sheet sheet, int rowSzie, int preStartRow, Brand brand)
	{
		int rowSize = 0;
		
		// 构造查询参数
		List<DailyEntityInfo> dailyEntityInfos = getQueryData(Constants.SQL_SERIES_QUATER_DAILY, DailyEntityInfo.class,
				brand);
		
		if (null != dailyEntityInfos)
		{
			rowSize = dailyEntityInfos.size();
			
			// 添加占比，添加总计
			modiftyPercentageAndAddTotal(dailyEntityInfos, Constants.FIELD_NAME_SERIES_TYPE);
			
			GatherDailyExcelExportUtils.writerExcel(workbook, sheet, rowSzie + preStartRow
					+ Constants.SHEET_SERIES_START_ROW, Constants.SHEET_DEFAULT_START_COLUMN, dailyEntityInfos,
					getSeriesHeaderTitle(), Constants.ARRAY_FIELD_NAME_SERIES);
			
		}
		
		return rowSize;
	}
	
	/**
	 * 获取系列的标题头
	 * 
	 * @return
	 */
	private String[] getSeriesHeaderTitle()
	{
		
		String[] commTitle = Constants.SHEET_SALES_TITLE;
		String[] seriesTitle = Constants.SHEET_SALES_SERIES_TITLE;
		
		if (ArrayUtils.isNotEmpty(commTitle) && ArrayUtils.isNotEmpty(seriesTitle))
		{
			try
			{
				String[] tmpArrays = new String[] {};
				
				tmpArrays = ArrayUtils.addAll(tmpArrays, commTitle);
				tmpArrays = ArrayUtils.addAll(tmpArrays, seriesTitle);
				
				return tmpArrays;
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		return seriesTitle;
	}
	
	/**
	 * 根据之前生成的excel报表，开始行、开始列、结束列，生成按季销售报表
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @param preRowStart
	 *        上一个报表开始行
	 * @param preTotalColumn
	 *        上一个报表总列数
	 * @param preStartColumn
	 *        上一个报表开始列
	 * @param brand
	 * @return int 该报表总行数
	 */
	private int createQuaterTable(Workbook workbook, Sheet sheet, int preRowStart, int preTotalColumn,
			int preStartColumn, Brand brand)

	{
		int rowSize = 0;
		
		// 构造查询参数
		List<DailyEntityInfo> dailyEntityInfos = getQueryData(Constants.SQL_SALES_QUATER_DAILY, DailyEntityInfo.class,
				brand);
		
		if (null != dailyEntityInfos)
		{
			rowSize = dailyEntityInfos.size();
			
			// 添加占比，添加总计
			modiftyPercentageAndAddTotal(dailyEntityInfos, Constants.FIELD_NAME_QUATER_TYPE);
			
			GatherDailyExcelExportUtils.writerExcel(workbook, sheet, Constants.SHEET_DEFAULT_START_ROW, preStartColumn
					+ preTotalColumn + 1, dailyEntityInfos, Constants.SHEET_SALES_QUATER_TITLE,
					Constants.ARRAY_FIELD_NAME_QUATER);
			
		}
		
		return rowSize;
	}
	
	/**
	 * 按类型，生成销售信息对应报表
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @return 该表格创建行数
	 */
	private int createProductTypeTable(Workbook workbook, Sheet sheet, Brand brand)
	{
		// 获取按类型生成的表格，需要生成多少行数据
		int rowSize = 0;
		
		// 构造查询参数
		List<DailyEntityInfo> dailyEntityInfos = getQueryData(Constants.SQL_SALES_PRODUCT_TYPE_DAILY,
				DailyEntityInfo.class, brand);
		
		if (null != dailyEntityInfos)
		{
			rowSize = dailyEntityInfos.size();
			
			// 添加占比，添加总计
			modiftyPercentageAndAddTotal(dailyEntityInfos, Constants.FIELD_NAME_PRODUCT_TYPE);
			
			GatherDailyExcelExportUtils.writerExcel(workbook, sheet, Constants.SHEET_DEFAULT_START_ROW,
					Constants.SHEET_DEFAULT_START_COLUMN, dailyEntityInfos, Constants.SHEET_SALES_TITLE,
					Constants.ARRAY_FIELD_NAME_PRODUCT_TYPE);
			
		}
		
		return rowSize;
	}
	
	/**
	 * 
	 * 根据SQL KEY , 查询相应数据
	 * 
	 * @param sqlKey
	 *        SQL key
	 * @return List< DailyEntityInfo >
	 */
	private <T> List<T> getQueryData(String sqlKey, Class<T> clazz, Brand brand)
	{
		return getQueryData(sqlKey, clazz, null, brand);
	}
	
	/**
	 * 
	 * 根据SQL KEY , 查询相应数据
	 * 
	 * @param sqlKey
	 *        SQL key
	 * @return List< DailyEntityInfo >
	 */
	private <T> List<T> getQueryData(String sqlKey, Class<T> classz, String fieldName, Brand brand)
	{
		try
		{
			Map<String, Object> params = StringUtils.isNotEmpty(fieldName) ? getParams(0, fieldName, brand)
					: getParams(brand);
			
			// 查询数据
			return gatherDailyDao.queryGatherDailyData(sqlKey, params, classz);
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return new ArrayList<T>();
	}
	
	/**
	 * 自动计算统计列的总计与占比，并追加一个合计对象
	 * 
	 * @param dailyEntityInfos
	 *        原始数据查询对象
	 * @param fieldName
	 *        添加合计的字段名
	 */
	private void modiftyPercentageAndAddTotal(List<DailyEntityInfo> dailyEntityInfos, String fieldName)
	{
		if (null != dailyEntityInfos)
		{
			double totalAmountToPay = 0.0;
			long totalSalesNumber = 0;
			long totalStockNumber = 0;
			long totalYearlySalesNumber = 0;
			double totalTagPrice = 0;
			double totalYearAmountToPay = 0;
			long totalSummarySalesNumer = 0;
			
			for (DailyEntityInfo dailyEntityInfo : dailyEntityInfos)
			{
				if (null != dailyEntityInfo)
				{
					BigDecimal amountToPay = dailyEntityInfo.getAmountToPay();
					Long salesNumber = dailyEntityInfo.getSalesNumber();
					Long stockNumber = dailyEntityInfo.getStockNumber();
					Long yearlySalesNumber = dailyEntityInfo.getSummarySalesNumber();
					BigDecimal tagPrice = dailyEntityInfo.getTagPrice();
					BigDecimal yearAmountToPay = dailyEntityInfo.getYearAmountToPay();
					Long summarySalesNumber = dailyEntityInfo.getSummarySalesNumber();
					
					if (null != amountToPay)
					{
						totalAmountToPay += amountToPay.doubleValue();
					}
					
					if (null != salesNumber)
					{
						totalSalesNumber += salesNumber;
					}
					
					if (null != stockNumber)
					{
						totalStockNumber += stockNumber;
					}
					if (null != yearlySalesNumber)
					{
						totalYearlySalesNumber += yearlySalesNumber;
					}
					
					if (null != tagPrice)
					{
						totalTagPrice += tagPrice.doubleValue();
					}
					
					if (null != yearAmountToPay)
					{
						totalYearAmountToPay += yearAmountToPay.doubleValue();
					}
					
					if (null != summarySalesNumber)
					{
						totalSummarySalesNumer += summarySalesNumber;
					}
				}
				
			}
			
			// 更新总合字段
			modfityTotalValue(totalAmountToPay, totalSalesNumber, totalStockNumber, totalYearlySalesNumber,
					totalTagPrice, dailyEntityInfos, totalYearAmountToPay, totalSummarySalesNumer);
			
			// 追加合计字段说明
			addToToal(dailyEntityInfos, fieldName);
		}
	}
	
	/**
	 * 给每个统计最后，追加一个合计字段
	 * <p>
	 * 
	 * @param dailyEntityInfos
	 *        原始数据集合
	 * @param fieldName
	 *        字段名称
	 */
	private void addToToal(List<DailyEntityInfo> dailyEntityInfos, String fieldName)
	{
		if (null != dailyEntityInfos && StringUtils.isNotEmpty(fieldName))
		{
			try
			{
				DailyEntityInfo addDailyEntityInfo = new DailyEntityInfo();
				
				if (fieldName.indexOf(Constants.UNDER_LINE) != -1)
				{
					fieldName = ConvertPageQueryFieldsToSQL.columnNameToFieldName(fieldName);
				}
				
				// 给字段添加一个合计说明
				ReflectionUtils.setFieldValue(addDailyEntityInfo, fieldName, Constants.TOTAL_TITLE);
				
				DailyEntityInfo dailyEntityInfo = CollectionUtils.isNotEmpty(dailyEntityInfos) ? dailyEntityInfos
						.get(0) : null;
				
				// 追加一条总计结果
				modfityTotalValue(dailyEntityInfo, addDailyEntityInfo);
				
				dailyEntityInfos.add(addDailyEntityInfo);
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
			
		}
	}
	
	/**
	 * 更新总合字段
	 * 
	 * @param totalAmountToPay
	 *        总付款金额
	 * @param totalSalesNumber
	 *        总销售数据
	 * @param totalStockNumber
	 *        总库存量
	 * @param totalYearlySalesNumber
	 *        总年度统计
	 * @param totalTagPrice
	 *        总吊牌价
	 * @param dailyEntityInfos
	 *        原始数据
	 * @param totalYearAmountToPay
	 * @param totalSummarySalesNumer
	 */
	private void modfityTotalValue(double totalAmountToPay, long totalSalesNumber, long totalStockNumber,
			long totalYearlySalesNumber, double totalTagPrice, List<DailyEntityInfo> dailyEntityInfos,
			double totalYearAmountToPay, long totalSummarySalesNumer)
	{
		if (CollectionUtils.isNotEmpty(dailyEntityInfos))
		{
			for (DailyEntityInfo dailyEntityInfo : dailyEntityInfos)
			{
				if (null != dailyEntityInfo)
				{
					dailyEntityInfo.setTotalAmountToPay(new BigDecimal(totalAmountToPay));
					dailyEntityInfo.setTotalSalesNumber(totalSalesNumber);
					dailyEntityInfo.setTotalStockNumber(totalStockNumber);
					dailyEntityInfo.setTotalTagPrice(new BigDecimal(totalTagPrice));
					dailyEntityInfo.setTotalSummarySalesNumber(totalSummarySalesNumer);
					dailyEntityInfo.setTotalYearAmountToPay(new BigDecimal(totalYearAmountToPay));
					
					// 设置百分比
					dailyEntityInfo.setTotalAmountToPayPercentage(dailyEntityInfo.getTotalAmountToPayPercentage());
					dailyEntityInfo.setTotalSalesNumberPercentage(dailyEntityInfo.getTotalSalesNumberPercentage());
					dailyEntityInfo.setStockNumberPercentage(dailyEntityInfo.getStockNumberPercentage());
					dailyEntityInfo.setTotalTagPricePercentage(dailyEntityInfo.getTotalTagPricePercentage());
					dailyEntityInfo.setTotalSummarySalesNumberPercentage(dailyEntityInfo
							.getTotalSummarySalesNumberPercentage());
					dailyEntityInfo.setTotalYearAmountToPayPercentage(dailyEntityInfo
							.getTotalYearAmountToPayPercentage());
				}
			}
		}
	}
	
	/**
	 * 
	 * 给列表中添加总计
	 * 
	 * @param srcEntityInfo
	 *        原始对象
	 * @param destEntityInfo
	 *        追加的对象
	 */
	private void modfityTotalValue(DailyEntityInfo srcEntityInfo, DailyEntityInfo destEntityInfo)
	{
		if (null != srcEntityInfo && null != destEntityInfo)
		{
			destEntityInfo.setSalesNumber(srcEntityInfo.getTotalSalesNumber());
			destEntityInfo.setStockNumber(srcEntityInfo.getTotalStockNumber());
			destEntityInfo.setTagPrice(srcEntityInfo.getTotalTagPrice());
			destEntityInfo.setAmountToPay(srcEntityInfo.getTotalAmountToPay());
			destEntityInfo.setYearAmountToPay(srcEntityInfo.getTotalYearAmountToPay());
			destEntityInfo.setSummarySalesNumber(srcEntityInfo.getTotalSummarySalesNumber());
			
			// 设置百分比
			destEntityInfo.setTotalAmountToPayPercentage(100.0);
			destEntityInfo.setTotalSalesNumberPercentage(100.0);
			destEntityInfo.setStockNumberPercentage(100.0);
			destEntityInfo.setTotalTagPricePercentage(100.0);
			destEntityInfo.setTotalSummarySalesNumberPercentage(100.0);
			destEntityInfo.setTotalYearAmountToPayPercentage(100.0);
		}
		
	}
	
	/**
	 * 构造查询参数，根据类型
	 * 
	 * @param cosParamsType
	 *        构造查询的参数类型
	 * @param fieldName
	 *        库存分析传入的字段名称
	 * @return Map< String, Object >
	 */
	private Map<String, Object> getParams(Brand brand)
	{
		return getParams(0, null, brand);
	}
	
	/**
	 * 构造查询参数，根据类型
	 * 
	 * @param cosParamsType
	 *        构造查询的参数类型
	 * @param fieldName
	 *        库存分析传入的字段名称
	 * @return Map< String, Object >
	 */
	// private Map<String, Object> getParams(int cosParamsType)
	// {
	// return getParams(cosParamsType, null);
	// }
	
	/**
	 * 构造查询参数，根据类型
	 * 
	 * @param cosParamsType
	 *        构造查询的参数类型
	 * @param fieldName
	 *        库存分析传入的字段名称，需要带下划线
	 * @param brand
	 *        k品牌
	 * @return Map< String, Object >
	 */
	private Map<String, Object> getParams(int cosParamsType, String fieldName, Brand brand)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		
		// 获取查询时间，开始时间+结束时间
		getQueryTime(params, brand);
		
		// 字段如果不空，则默认添加该字段
		if (StringUtils.isNotEmpty(fieldName))
		{
			fieldName = fieldName.indexOf(Constants.UNDER_LINE) == -1 ? ConvertPageQueryFieldsToSQL
					.fieldNameToColumName(fieldName) : fieldName;
			
			params.put(Constants.SQL_QUERY_FIELD_NAME, fieldName);
		}
		
		return params;
	}
	
	/**
	 * 获取年+Q季的说明
	 * 
	 * @param month
	 *        月
	 * @param year
	 *        年份
	 * @return 年份加Q季字段，如：2014Q1
	 */
	private String getThisSeason(int month, int year)
	{
		if (month > 0 && year > 0)
		{
			try
			{
				String formatSeason = String.valueOf(year);
				if (month >= 1 && month <= 3)
				{
					formatSeason += Constants.SHEET_TITLE_SEASON[0];
				}
				if (month >= 4 && month <= 6)
				{
					formatSeason += Constants.SHEET_TITLE_SEASON[1];
				}
				if (month >= 7 && month <= 9)
				{
					formatSeason += Constants.SHEET_TITLE_SEASON[2];
				}
				if (month >= 10 && month <= 12)
				{
					formatSeason += Constants.SHEET_TITLE_SEASON[3];
				}
				
				return formatSeason;
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return "";
	}
	
	/**
	 * 获取查询时间，开始时间+结束时间
	 * <p>
	 * 
	 * @param params
	 * @param brand
	 */
	private void getQueryTime(Map<String, Object> params, Brand brand)
	{
		getQueryTime(params, false, brand);
	}
	
	/**
	 * 获取查询时间，开始时间+结束时间
	 * <p>
	 * 
	 * @param params
	 *        存储查询参数的Map
	 * @param isSeasonArrivalRate
	 *        true:求当前Q季的到货率，false:相反，正常查询
	 * @param brand
	 * 
	 */
	private void getQueryTime(Map<String, Object> params, boolean isSeasonArrivalRate, Brand brand)
	{
		if (null != params)
		{
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			Date date = calendar.getTime();
			
			String endTime = isSeasonArrivalRate ? CalendarUtils.getThisSeasonFinallyTime(month + 1, year) : DateUtils
					.format(date, DateUtils.SIMPLE_DEFAULT_FORMAT);
			String startTime = isSeasonArrivalRate ? CalendarUtils.getThisSeasonFirstTime(month + 1, year)
					: getStartTime(date);
			
			if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime))
			{
				params.put(Constants.SQL_QUERY_START_TIME, startTime);
				params.put(Constants.SQL_QUERY_END_TIME, endTime);
			}
			
			if (null != brand && brand.getId() != null)
			{
				params.put(Constants.SQL_QUERY_BRAND_ID, brand.getId());
			}
			
			params.put(Constants.SQL_QUERY_YEAR, year);
		}
	}
	
	/**
	 * 获取开始时间，往前推一天，如果参数Date为空，则默认取当前时间往前推一天
	 * <p>
	 * 
	 * @param date
	 *        当前系统时间
	 * @return
	 */
	private String getStartTime(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		if (null != date)
		{
			calendar.setTime(date);
		}
		else
		{
			calendar.setTime(new Date());
		}
		
		calendar.add(Calendar.DATE, -1);
		
		return DateUtils.format(calendar.getTime(), DateUtils.SIMPLE_DEFAULT_FORMAT);
	}
}
