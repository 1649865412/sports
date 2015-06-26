package com.innshine.stockmanager.stockanalysis;

/**
 * 库存分析常量类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public interface StockAnalysisConstants
{
	/**
	 * 库存分析详情名称
	 */
	String EXCEL_NAME = "库存分析";
	
	/**
	 * 表格头标题
	 */
	String[] EXCEL_HEAD_TITLE = { "库存数量", "占比(%)", "吊牌价金额", "占比(%)" };
	/**
	 * 表格头标题
	 */
	String[] EXCEL_FUTURE_HEAD_TITLE = { "库存", "占比(%)" };
	
	/**
	 * Ecxel工作表名称
	 */
	String STOCK_ANALYSIS_NAME = "库存综合分析详情.xls";
	
	/**
	 * 需要展示的字段列表
	 */
	String[] STOCK_ANALYSIS_PUBLIC_FIELD_NAME = new String[] { "stockNumber", "stockNumberPercentage", "tagPrice",
			"tagPricePercentage" };
	/**
	 * 需要展示的未来库存字段列表
	 */
	String[] STOCK_ANALYSIS_FUTURE_STOCK_NUMBER__FIELD_NAME = new String[] { "futureStockNumber",
			"futureStockNumberPercentage" };
	/**
	 * 需要展示的字段列表
	 */
	String[] STOCK_ANALYSIS_FIELD_NAME = new String[] { "futureStockNumber", "futureStockNumberPercentage" };
	
	/**
	 * 总计导出字段
	 */
	String[] FILED_TOTAL_ARRY = new String[] { "totalStockNumber", "percentage", "totalTagPrice", "percentage" };
	
	/**
	 * 总计导出字段 future_stock_number
	 */
	String[] FILED_FUTURE_STOCK_NUMBER_TOTAL_ARRY = new String[] { "totalFutureStockNumber", "percentage", };
}
