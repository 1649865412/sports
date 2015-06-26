package com.innshine.stockAnalyse.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;



public class Constant {
	
	
	/**
	 * 导出提示语
	 * 
	 */
	public static String  MARKED_WORD="（此分析使用模糊数据，请注意）";
	
	
	/**
	 * 库存分析页面获取相关下拉属性
	 * 
	 */
	public static List ParamAll ;

	
	

	/**
	 * 库存时间分析导出报表.xls
	 * 
	 */
	public static String TIEM_ANAYSE = "库存时间分析导出报表.xls";
	
	/**
	 * 时间维度查询
	 * 季度查询转成月 
	 */
	public static final String[][] Z_MONTH = new String[][] {
			{ "01月", "02月", "03月" }, { "04月", "05月", "06月" },
			{ "07月", "08月", "09月" }, { "10月", "11月", "12月" } };
	


}
