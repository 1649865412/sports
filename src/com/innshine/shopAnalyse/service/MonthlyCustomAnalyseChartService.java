package com.innshine.shopAnalyse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jfree.data.general.Dataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innshine.chart.ChartConstants;
import com.innshine.chart.DataParams;
import com.innshine.chart.service.ChartService;
import com.innshine.shopAnalyse.dao.ExcelShopAnalyseDao;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.MonthEntity;
import com.utils.CalendarUtils;
import com.utils.Exceptions;

/**
 * 月度统计功能，生成图表服务类 <br/>
 * <br/>
 * <code>MonthlyCustomAnalyseService.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class MonthlyCustomAnalyseChartService
{
	
	// %V 周 (01-53) 星期日是一周的第一天，与 %X 使用
	// %v 周 (01-53) 星期一是一周的第一天，与 %x 使用
	// %X 年，其中的星期日是周的第一天，4 位，与 %V 使用
	// %x 年，其中的星期一是周的第一天，4 位，与 %v 使用
	// * 按年-周显示
	/**
	 * 按年-周显示
	 */
	private static final String SQL_FOMATE_WEEK = "%x-%v";
	
	/**
	 * 按年-月
	 */
	private static final String SQL_FOMATE_YEAR_MONTH = "%Y-%m";
	
	/**
	 * 按年
	 */
	private static final String SQL_FOMATE_YEAR = "%Y";
	
	/**
	 * 以年-月-日
	 */
	private static final String DEFAULT_SQL_FOMATE = "%Y-%m-%d";
	
	/**
	 * SQL中的参数名称
	 */
	private static final String SQL_FORMATE_DATE = "formateDate";
	
	/**
	 * 说明
	 */
	private static final String SALES_NUMBER_Y_TITLE = "单位:个";
	
	/**
	 * 说明
	 */
	private static final String SALES_AMOUT_Y_TITLE = "单位：元";
	
	/**
	 * 空
	 */
	private static final String BLANK_CHARACTER = "";
	
	/**
	 * 画图查询SQL KEY
	 */
	private static final String SHOP_ANALYSE_SHOP_MONTH_CHART_SQL = "shopAnalyse.shop.month.chart.sql";
	
	/**
	 * 图表标题 销售价格
	 */
	private static final String SALES_PRICE_PLATFORM_PRICE_TITLE = "销售数量";
	
	/**
	 * 图表标题 销售金额
	 */
	private static final String SALES_PRICE_AMOUT_TITLE = "销售金额";
	
	/**
	 * 下划线
	 */
	private static final String UNDER_LINE = "_";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MonthlyCustomAnalyseChartService.class);
	
	/**
	 * 查询数据DAO
	 */
	@Autowired
	private ExcelShopAnalyseDao excelShopAnalyseDao;
	
	/**
	 * 画图对象
	 */
	@Autowired(required = true)
	private ChartService chartService;
	
	/**
	 * 生成拆线图，并返回生成后的拆线图路径
	 * <p>
	 * 
	 * @param params
	 *        SQL参数列表
	 * @param shopAnalyseCheckEntity
	 *        页面参数对象
	 * @return List< String> 存储的路径
	 */
	public List<String> createChart(Map<String, Object> params, ShopAnalyseCheckEntity shopAnalyseCheckEntity)
	{
		
		// 格式化查询时间
		formatQueryDate(params, shopAnalyseCheckEntity);
		
		// 画图之前数据准备
		List<MonthEntity> monthlyDataList = queryChartData(params);
		
		// 生成图片并记录图片生成后的完整路径
		List<String> list = createImage(monthlyDataList, shopAnalyseCheckEntity);
		
		return list;
	}
	
	/**
	 * 根据开始与结束时间，生成SQL日期格式，如：%Y-%m
	 * 
	 * @param params
	 *        查询SQL参数列表
	 * @param shopAnalyseCheckEntity
	 *        页面参数对象
	 */
	private void formatQueryDate(Map<String, Object> params, ShopAnalyseCheckEntity shopAnalyseCheckEntity)
	{
		if (MapUtils.isNotEmpty(params) && null != shopAnalyseCheckEntity)
		{
			String beginTime = shopAnalyseCheckEntity.getBeginTime();
			String endTime = shopAnalyseCheckEntity.getEndTime();
			
			// 根据两日期之差，返回日期格式
			String formatStr = getFormat(beginTime, endTime);
			
			if (StringUtils.isNotEmpty(formatStr))
			{
				params.put(SQL_FORMATE_DATE, formatStr);
			}
		}
	}
	
	/**
	 * 根据两日期之差，返回对应日期格式，默认返回%Y-%m-%d。<br />
	 * 1. 默认1个月内：%Y-%m-%d <br />
	 * 2.>=6个月 按周展示，<br />
	 * 3.>6个月且<=3年按月显示<br />
	 * 4.>3年按年显示
	 * 
	 * @param beginTime
	 *        查询开始时间
	 * @param endTime
	 *        查询结束时间
	 * @return SQL日期格式化，如：%Y-%m-%d
	 */
	private String getFormat(String beginTime, String endTime)
	{
		return SQL_FOMATE_YEAR_MONTH;
		
		// if (StringUtils.isNotEmpty(beginTime) &&
		// StringUtils.isNotEmpty(endTime))
		// {
		// // Date beginDate = DateUtils.toDate(beginTime);
		// // Date endDate = DateUtils.toDate(endTime);
		// try
		// {
		// // 获取两个日期相差天数
		// long differDay = CalendarUtils.getDays(endTime, beginTime);
		//				
		// if (differDay <= 31)
		// {
		// return DEFAULT_SQL_FOMATE;
		// }
		// else if (differDay > 31 && differDay <= (30 * 6))
		// {
		// return SQL_FOMATE_WEEK;
		// }
		// else if (differDay > (30 * 6) && differDay <= (365 * 3))
		// {
		// return SQL_FOMATE_YEAR_MONTH;
		// }
		// else
		// {
		// return SQL_FOMATE_YEAR;
		// }
		// }
		// catch (Exception e)
		// {
		// LOG.error(Exceptions.getStackTraceAsString(e));
		// return SQL_FOMATE_YEAR;
		// }
		// }
		//		
		// return DEFAULT_SQL_FOMATE;
	}
	
	/**
	 * 生成图片并记录图片完整路径
	 * 
	 * @param monthlyDataList
	 *        图片数据
	 * @param shopAnalyseCheckEntity
	 *        前端页面查询参数
	 * @param title
	 *        图表标题
	 * @param xdes
	 *        x轴说明
	 * @param ydes
	 *        y轴说明
	 * @return List< String> 存储图片完整路径列表
	 */
	private List<String> createImage(List<MonthEntity> monthlyDataList, ShopAnalyseCheckEntity shopAnalyseCheckEntity)
	{
		List<String> imagPaths = new Vector<String>();
		
		if (null != monthlyDataList)
		{
			// 图例
			List<String> legendList = new ArrayList<String>();
			
			// 横轴
			List<String> transverseList = new ArrayList<String>();
			
			// 记录横轴与图例对应 key = 图例+横轴时间辍
			Map<String, MonthEntity> map = new HashMap<String, MonthEntity>();
			
			for (int i = 0; i < monthlyDataList.size(); i++)
			{
				MonthEntity monthlyEntity = monthlyDataList.get(i);
				if (null != monthlyEntity)
				{
					String legendValue = ExcelShopAnalyseDao.getValue(monthlyEntity, shopAnalyseCheckEntity);
					String formateSalesTime = monthlyEntity.getFormateSalesTime();
					
					// 图例
					if (StringUtils.isNotEmpty(legendValue) && !legendList.contains(legendValue))
					{
						legendList.add(legendValue);
					}
					
					// 横轴
					if (StringUtils.isNotEmpty(formateSalesTime) && !transverseList.contains(formateSalesTime))
					{
						transverseList.add(formateSalesTime);
					}
					
					map.put(legendValue + UNDER_LINE + formateSalesTime, monthlyEntity);
				}
			}
			
			// 清空原始数据
			monthlyDataList.clear();
			monthlyDataList = null;
			
			// 销量
			double[][] datas = consChartDatas(map, legendList, transverseList, false);
			String salesNumberImagePath = createImageAndReturnPath(legendList, transverseList, datas,
					SALES_PRICE_PLATFORM_PRICE_TITLE, BLANK_CHARACTER, SALES_NUMBER_Y_TITLE);
			imagPaths.add(salesNumberImagePath);
			
			// 销量金额
			double[][] disDatas = consChartDatas(map, legendList, transverseList, true);
			String salesAmoutImagePath = createImageAndReturnPath(legendList, transverseList, disDatas,
					SALES_PRICE_AMOUT_TITLE, BLANK_CHARACTER, SALES_AMOUT_Y_TITLE);
			
			imagPaths.add(salesAmoutImagePath);
			
			map.clear();
		}
		
		return imagPaths;
	}
	
	/**
	 * 生成拆线图,并返回图片生成之后的完整路径
	 * <p>
	 * 
	 * @param legendList
	 *        图例列表
	 * @param transverseList
	 *        横轴显示列表
	 * @param datas
	 *        填充数据列表
	 * @param title
	 *        标题
	 * @param x
	 *        X轴显示说明
	 * @param y
	 *        Y轴显示说明
	 * @return 图片完整路径
	 */
	private String createImageAndReturnPath(List<String> legendList, List<String> transverseList, double[][] datas,
			String title, String x, String y)
	{
		String tmpPath = null;
		
		try
		{
			// 初始化填充数据
			DataParams p = new DataParams(datas, legendList.toArray(new String[legendList.size()]), transverseList
					.toArray(new String[transverseList.size()]), ChartConstants.LINE_CHART);
			
			LOG.info("create Chart DatasParams = " + p);
			Dataset dataset = chartService.consDataParamList(p);
			tmpPath = chartService.createChart(dataset, title, null == x ? BLANK_CHARACTER : x,
					null == y ? BLANK_CHARACTER : y, p.getChartType());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return tmpPath;
	}
	
	/**
	 * 构造画图图表填充二维数组
	 * 
	 * @param map
	 *        填充的数据集合
	 * @param legendList
	 *        图例集合
	 * @param transverseList
	 *        横轴列表
	 * @param isAmount
	 *        是否生成销售金额 ，ture 填充 flase 填充销售数量
	 * @return double[][] 填充完成的二维数组
	 */
	private double[][] consChartDatas(Map<String, MonthEntity> map, List<String> legendList,
			List<String> transverseList, boolean isAmount)
	{
		double[][] datas = new double[legendList.size()][transverseList.size()];
		
		for (int i = 0; i < legendList.size(); i++)
		{
			String legendStr = legendList.get(i);
			if (StringUtils.isNotBlank(legendStr))
			{
				for (int k = 0; k < transverseList.size(); k++)
				{
					String tran = transverseList.get(k);
					
					if (StringUtils.isNotBlank(tran))
					{
						MonthEntity monthEntity = map.get(legendStr + UNDER_LINE + tran);
						if (null != monthEntity)
						{
							double tmeValue = getDoubleValue(isAmount, monthEntity);
							
							datas[i][k] = tmeValue;
							
						}
					}
				}
			}
		}
		return datas;
	}
	
	/**
	 * 获取double数值，如果值为null，则默认补0
	 * 
	 * @param isAmount
	 *        是否生金额 ，ture 填充 flase 填充均价
	 * @param monthEntity
	 *        数据对象
	 * @return double
	 */
	private double getDoubleValue(boolean isAmount, MonthEntity monthEntity)
	{
		double tmeValue = 0.0;
		
		if (!isAmount && StringUtils.isNotEmpty(monthEntity.getAllSales()))
		{
			String allSales = monthEntity.getAllSales();
			tmeValue = NumberUtils.isNumber(allSales) ? Double.parseDouble(allSales) : 0;
		}
		else
		{
			if (StringUtils.isNotEmpty(monthEntity.getAllAmount()))
			{
				String allAmount = monthEntity.getAllAmount();
				tmeValue = NumberUtils.isNumber(allAmount) ? Double.parseDouble(allAmount) : 0;
				
			}
		}
		return tmeValue;
	}
	
	/**
	 * 根据页面参数，查询对应数据
	 * 
	 * @param params
	 *        页面参数列表
	 * @return List< MonthEntity>
	 */
	private List<MonthEntity> queryChartData(Map<String, Object> params)
	{
		try
		{
			if (MapUtils.isNotEmpty(params))
			{
				return excelShopAnalyseDao.queryDataByKeyAndParams(params, SHOP_ANALYSE_SHOP_MONTH_CHART_SQL,
						MonthEntity.class);
			}
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
}
