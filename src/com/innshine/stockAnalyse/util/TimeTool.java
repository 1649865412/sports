package com.innshine.stockAnalyse.util;

import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckEntity;
import com.innshine.stockAnalyse.entity.StockTime;

public class TimeTool {

	/**
	 * 功能:求对比查询的时间总范围 (R:日 Z:季 M:月 Y：年),用于一次性查库后再在代码里筛选
	 * <p>作者 杨荣忠 2014-9-23 上午09:49:43
	 * @param param
	 * @param shopTime
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getMapTimeAll(Map<String, Object> param,
			StockTime shopTime) throws Exception {
			Map<String, String> timemapEnd = getMapTime(shopTime, 0);
			Map<String, String> timemapbegin = getMapTime(shopTime, shopTime
					.getCircle());
			param.put("beginTime", timemapbegin.get("beginTime"));
			param.put("endTime", timemapEnd.get("endTime"));
			return param;
	}


	
	/**
	 * 功能:(R:日 Z:季 M:月 Y：年)查询SQL
	 * <p>作者 杨荣忠 2014-9-23 上午10:45:43
	 * @param param
	 * @param timeType
	 * @return
	 */
	public static Map<String, Object> TimeTypeSql(Map<String, Object> param,
			String timeType) {
		if (timeType.equals("Y")) {
			param.put("condition",
					"FLOOR((DATE_FORMAT(b.sales_time, '%m')+2)/3)");
		} else if (timeType.equals("Z")) {
			param.put("condition", "DATE_FORMAT(b.sales_time,'%m')");
		} else {
			param.put("condition", "DATE_FORMAT(b.sales_time,'%Y%m%d')");
		}
		return param;
	}

	public static StockTime check(
			StockTime shopTime) throws ParseException {
		String begin = shopTime.getFirstTime();
		String end = shopTime.getEndTime();
		if (begin.trim().equals("") || end.trim().equals("")) {
			shopTime.setFirstTime(DateUtil.getNewDay(-30, DateUtil
					.nowtimeString()));
			shopTime.setEndTime(DateUtil.nowtimeString());
		}
		return shopTime;
	}

	
	
	/**
	 * 功能:时间纵向分析时间处理R:日 Z:季 M:月 Y：年
	 * <p>作者 杨荣忠 2014-9-23 上午10:46:46
	 * @param shopTime
	 * @param circle
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getMapTime(
			StockTime shopTime, int circle) throws Exception {
		Map<String, String> timemap = Collections
				.synchronizedMap(new LinkedHashMap());
		String timeType = shopTime.getSuitselectTimeType();
		int year = shopTime.getYear() - circle;
		// 如果是日月类型，则以结束时间为准不超过一年的范围
		if (timeType.equals("M")) {
			timemap = DateUtil.shopTimeAnalyseCheck(DateUtil.getfulletimeMonth(
					shopTime.getMonthBegin(), shopTime.getMonthEnd(), year),
					timeType);
		}
		if (timeType.equals("Z")) {
			timemap = DateUtil.getfulletimeJidu(shopTime.getQuarterfirstTime(),
					shopTime.getQuarterendTime(), year);
		}
		// 如果是日月类型，则以结束时间为准不超过一年的范围
		if (timeType.equals("R") || "W".equalsIgnoreCase(timeType)) {
			timemap = DateUtil.shopTimeAnalyseCheck(DateUtil.getfulletimeDay(
					shopTime.getFirstTime(), shopTime.getEndTime(), circle),
					timeType);
		}
		if (timeType.equals("Y")) {
			timemap = DateUtil.getfulletimeYear(year);
		}
		return timemap;
	}
	
	
	
	public static String junitText(String str) {
		return str;
	}

}
