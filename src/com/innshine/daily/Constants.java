package com.innshine.daily;

import java.io.File;

/**
 * 汇总日报常量类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class Constants
{
	/**
	 * 构造参数类型 ，按类别构造
	 */
	public static final int COS_PARAMS_PRODUCT_TYPE = 1;
	
	/**
	 * 查询结束时间
	 */
	public static final String SQL_QUERY_END_TIME = "endTime";
	
	/**
	 * 查询开始时间
	 */
	public static final String SQL_QUERY_START_TIME = "startTime";
	
	/**
	 * 查询按年统计
	 */
	public static final String SQL_QUERY_YEAR = "year";
	
	/**
	 * 查询参数，按字段 group by 与显示
	 */
	public static final String SQL_QUERY_FIELD_NAME = "fieldName";
	
	/**
	 * 查询参数，brandId
	 */
	public static final String SQL_QUERY_BRAND_ID = "brandId";
	
	/**
	 * 日期格式化
	 */
	public static final String FORMAT_YYYY_MM = "yyyy-MM";
	
	/**
	 * 日期格式化
	 */
	public static final String FORMAT_YYYY = "yyyy";
	
	/**
	 * 按类型 统计：销售数量、付款金额、吊牌金额、平均折扣 -
	 */
	public static final String SQL_SALES_PRODUCT_TYPE_DAILY = "sales.product.type.daily.sql";
	
	/**
	 * 按Q季 ，统计：本月销售数量、累计销售、售馨率
	 */
	public static final String SQL_SALES_QUATER_DAILY = "sales.quater.daily.sql";
	
	/**
	 * 按系列 统计：销售数量、付款金额、吊牌金额、平均折扣 、累计全年
	 */
	public static final String SQL_SERIES_QUATER_DAILY = "sales.series.daily.sql";
	
	/**
	 * 按性别 统计：销售数量、占比
	 */
	public static final String SQL_SERIES_SEX_DAILY = "sales.product.sex.daily.sql";
	
	/**
	 * 统计库存
	 */
	public static final String SQL_STOCK_DAILY = "sales.stock.daily.sql";
	
	/**
	 * Q季到货率
	 */
	public static final String SQL_ARRIVAL_RATE_DAILY = "gather.arrival.daily.sql";
	
	/**
	 * 下划线
	 */
	public static final String UNDER_LINE = "_";
	
	/**
	 * 横杆
	 */
	public static final String CROSS_BAR = "-";
	
	/**
	 * 点
	 */
	public static final String POINT_SYMBOL = ".";
	
	/**
	 * 逗号
	 */
	public static final String SEMICOLON_SYMBOL = ";";
	
	/**
	 * 合计
	 */
	public static final String TOTAL_TITLE = "合计";
	
	/**
	 * 发送Email 1
	 */
	public static final int SEND_MAIL = 1;
	
	/**
	 * 不发送Email 0
	 */
	public static final int SEND_NOT_MAIL = 0;
	
	/**
	 * 类别
	 */
	public static final String FIELD_NAME_PRODUCT_TYPE = "productType";
	
	/**
	 * Q季
	 */
	public static final String FIELD_NAME_QUATER_TYPE = "quater";
	
	/**
	 * 系列
	 */
	public static final String FIELD_NAME_SERIES_TYPE = "series";
	
	/**
	 * 性别
	 */
	public static final String FIELD_NAME_SEX_TYPE = "productSex";
	
	/**
	 * XSSF 对象
	 */
	public static final int XSSF_WORKBOOK_TYPE = 1;
	
	/**
	 * XSSF 对象
	 */
	public static final int HSSF_WORKBOOK_TYPE = 2;
	
	/**
	 * 03 excel文件后辍
	 */
	public static final String SUFFIX_EXCEL_2003 = ".xls";
	
	/**
	 * 07 excel文件后辍
	 */
	public static final String SUFFIX_EXCEL_2007 = ".xlsx";
	
	/**
	 * 默认sheet名称
	 */
	public static final String DEFAULT_SHEET_NAME = "汇总日报详情";
	
	/**
	 * 默认开始行，开始列
	 */
	public static final int SHEET_DEFAULT_START_COLUMN = 1;
	
	/**
	 * 默认开始，开始行
	 */
	public static final int SHEET_DEFAULT_START_ROW = 2;
	
	/**
	 * 按系列写入，开始行
	 */
	public static final int SHEET_SERIES_START_ROW = 2;
	
	/**
	 * 默认合计行为1行
	 */
	public static final int SHEET_DEFAULT_TOTAL_ROW = 1;
	
	/**
	 * 按系列写入，开始行
	 */
	public static final int SHEET_STOCK_ROW = SHEET_SERIES_START_ROW + 6;
	
	/**
	 * 默认存放 路径
	 */
	public static final String DEFAULT_SAVE_PATH = System.getProperty("user.dir") + File.separator + "gather_daily"
			+ File.separator;
	
	/**
	 * 季节说明数组
	 */
	public static final String[] SHEET_TITLE_SEASON = new String[] { "Q1", "Q2", "Q3", "Q4" };
	
	/**
	 * 到货率说明
	 */
	public static final String SHEET_TITLE_ARRIVAL_RETE = "到货率";
	
	/**
	 * 百分号
	 */
	public static final String SHEET_TITLE_PER_CENT = "%";
	
	/**
	 * 邮件发送失败
	 */
	public static final int SEND_STATUS_FAILED = 0;
	
	/**
	 * 邮件发送成功 succeed
	 */
	public static final int SEND_STATUS_SUCEED = 1;
	
	/**
	 * 销售通用标题行
	 */
	public static String[] SHEET_SALES_TITLE = new String[] { "", "销售数量", "占比(%)", "付款金额", "占比(%)", "吊牌价金额", "平均折扣(%)" };
	
	/**
	 * 按产品类别字段列表
	 */
	public static String[] ARRAY_FIELD_NAME_PRODUCT_TYPE = new String[] { FIELD_NAME_PRODUCT_TYPE, "salesNumber",
			"totalSalesNumberPercentage", "amountToPay", "totalAmountToPayPercentage", "tagPrice", "avgDiscount" };
	
	/**
	 * 按Q类标题行
	 */
	public static String[] SHEET_SALES_QUATER_TITLE = new String[] { "SEASON", "本月销售", "占比(%)", "累计销售", "占比(%)",
			"售罄率(%)" };
	
	/**
	 * 按Q类字段列表
	 */
	public static String[] ARRAY_FIELD_NAME_QUATER = new String[] { FIELD_NAME_QUATER_TYPE, "salesNumber",
			"totalSalesNumberPercentage", "summarySalesNumber", "totalSummarySalesNumberPercentage", "soldRate" };
	/**
	 * 按系列标题行
	 */
	public static String[] SHEET_SALES_SERIES_TITLE = new String[] { "累计全年", "占比(%)" };
	
	/**
	 * 按系列字段列表
	 */
	public static String[] ARRAY_FIELD_NAME_SERIES = new String[] { FIELD_NAME_SERIES_TYPE, "salesNumber",
			"totalSalesNumberPercentage", "amountToPay", "totalAmountToPayPercentage", "tagPrice", "avgDiscount",
			"yearAmountToPay", "totalYearAmountToPayPercentage" };
	
	/**
	 * 按性别标题行
	 */
	public static String[] SHEET_SALES_SEX_TITLE = new String[] { "", "销售占比(%)" };
	
	/**
	 * 按性别字段列表
	 */
	public static String[] ARRAY_FIELD_NAME_SEX = new String[] { FIELD_NAME_SEX_TYPE, "totalSalesNumberPercentage" };
	
	/**
	 * 按Q季 库存分析标题行
	 */
	public static String[] SHEET_STOCK_QUATER_TITLE = new String[] { "SEASON", "库存数量", "占比(%)", "吊牌价金额", "占比(%)" };
	
	/**
	 * 按 库存分析标题行
	 */
	public static String[] SHEET_STOCK_DEFAULT_TITLE = new String[] { "", "库存数量", "占比(%)", "吊牌价金额", "占比(%)" };
	
	/**
	 * 按Q季 库存分析字段列表
	 */
	public static String[] ARRAY_FIELD_NAME_DEFAULT_QUATER = new String[] { "stockNumber", "stockNumberPercentage",
			"tagPrice", "totalTagPricePercentage" };
}
