package com.innshine.shopAnalyse.util;

import java.util.List;

public class Constant {


	/**
	 * 导出提示语
	 * 
	 */
	public static String MARKED_WORD = "（此分析使用模糊数据，请注意）";

	/**
	 * 销售分析页面获取相关下拉属性
	 * 
	 */
	public static List ParamAll;

	

	/**
	 * 时间维度查询 季度查询转成月
	 */
	public static final String[][] Z_MONTH = new String[][] {
			{ "01月", "02月", "03月" }, { "04月", "05月", "06月" },
			{ "07月", "08月", "09月" }, { "10月", "11月", "12月" } };



	/**
	 * 系统产品下拉属性(下拉属性类别编号， (Q季，LF/PF，系列))，可放置内存
	 * 
	 */
	public static String PARAM_ARRAY[] = new String[] { "quater",
			"product_lf_pf", "series" };

	
	
	/**
	 * 销售分析分类 如要增加新分类，加这里根据前台顺序加上对应数据库字段即可
	 * 种类，Q季，系列，一线还是二线，性别
	 */
	public static String GROUP_NAME_CHECK[] = new String[] { "product_type",
			"quater", "series", "inline_or2ndline", "product_sex" };

	
	/**
	 * 时间纵向分析执行SQL语句块
	 * 种类，Q季，系列，一线还是二线，性别，默认同比一年
	 */
	public static   String SQLArray_DEFAULT[]={"shopAnalyse.salesDetailsTimeYZMRNEW.top",
			"shopAnalyse.salesDetailsTimeYZMRNEW.Bottomtop",
			"shopAnalyse.salesDetailsTimeYZMRNEW.bottom"};
	
	/**
	 * 时间纵向分析执行SQL语句块
	 * 种类，Q季，系列，一线还是二线，性别，同比两年
	 */
	public static   String SQLArray_ALL []={"shopAnalyse.salesDetailsTimeYZMRNEW.top",
			"shopAnalyse.salesDetailsTimeYZMRNEW.Middletop",
			"shopAnalyse.salesDetailsTimeYZMRNEW.Bottomtop",
			"shopAnalyse.salesDetailsTimeYZMRNEW.middle",
			"shopAnalyse.salesDetailsTimeYZMRNEW.bottom"};
}
