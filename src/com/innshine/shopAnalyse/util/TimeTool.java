package com.innshine.shopAnalyse.util;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckEntity;

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
			ShopAnalyseTimeCheckEntity shopTime) throws Exception {
			Map<String, String> timemapEnd = getMapTime(shopTime, 0);
			Map<String, String> timemapbegin = getMapTime(shopTime, shopTime
					.getCircle());
			param.put("beginTime", timemapbegin.get("beginTime"));
			param.put("endTime", timemapEnd.get("endTime"));
			return param;
	}

	
	/**
	 * 功能:获取月报与月度统计的时间总的查询范围，用于一次性查库后再在代码里筛选
	 * <p>作者 杨荣忠 2014-9-23 上午10:44:30
	 * @param monthList
	 * @param param
	 * @param timelist
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> MonthGetTime(List<String> monthList,
			Map<String, Object> param, List timelist) throws ParseException {
		if (monthList.size() == 1) {
			param.put("beginTime", timelist.get(0));
			param.put("endTime", DateUtil.nextMonth(timelist.get(0) + ""));

		} else {
			param.put("beginTime", timelist.get(0));
			param.put("endTime", timelist.get(timelist.size() - 1));
			param.put("endTime",DateUtil.nextMonth((String) param.get("endTime")));
		}
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
					"FLOOR((DATE_FORMAT(a.sales_time, '%m')+2)/3)");
		} else if (timeType.equals("Z")) {
			param.put("condition", "DATE_FORMAT(a.sales_time,'%m')");
		} else {
			param.put("condition", "DATE_FORMAT(a.sales_time,'%Y%m%d')");
		}
		return param;
	}

	
	/**
	 * 功能:如果有一个时间为空，则默认时间为当前时间向前推30天
	 * <p>作者 杨荣忠 2014-9-23 上午10:45:43
	 * @param param
	 * @param timeType
	 * @return
	 */
	public static ShopAnalyseTimeCheckEntity check(
			ShopAnalyseTimeCheckEntity shopTime) throws ParseException {
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
			ShopAnalyseTimeCheckEntity shopTime, int circle) throws Exception {
		Map<String, String> timemap = Collections
				.synchronizedMap(new LinkedHashMap());
		String timeType = shopTime.getSelectTimeType();
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
		if (timeType.equals("R")) {
			timemap = DateUtil.shopTimeAnalyseCheck(DateUtil.getfulletimeDay(
					shopTime.getFirstTime(), shopTime.getEndTime(), circle),
					timeType);
		}
		if (timeType.equals("Y")) {
			timemap = DateUtil.getfulletimeYear(year);
		}
		return timemap;
	}

	
	
	
	/**
	 * 功能: 默认查询范围当前月一號至目前时间,如只有一个时间，则以上或者下个月为时间范围
	 * <p>作者 杨荣忠 2014-9-23 上午10:49:59
	 * @param param
	 * @param shopAnalyseEntity
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> getParamTime(Map<String, Object> param,
			ShopAnalyseCheckEntity shopAnalyseEntity) throws ParseException {
		// 如果只有一个时间范围，则
		String beginTime = shopAnalyseEntity.getBeginTime();
		String endTime = shopAnalyseEntity.getEndTime();
		if (!(beginTime == "" && endTime == "")) {
			if (beginTime != "" && endTime == "") {
				endTime = DateUtil.nextMonth(beginTime);
			} else if (beginTime == "" && endTime != "") {
				beginTime = DateUtil.lastMonth(endTime);
			}
		} else {
			// 默认查询范围当前月一號至目前时间
			beginTime = DateUtil.nowmonthfirst();
			endTime = DateUtil.nowtimeString();
		}
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		return param;
	}
	
	
	
	/**
	 * 功能:时间纵向分析同比时间的总范围
	 * <p>作者 杨荣忠 2014-9-23 上午10:50:48
	 * @param shopTime
	 * @param param
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> timeAnalyseCompareYearParam(ShopAnalyseTimeCheckEntity shopTime,
			  Map<String, Object> param) throws ParseException{
		   int circle=shopTime.getCircle();
		   String beginTime = (String) param.get("beginTime");
		   String endTime = (String) param.get("endTime");
		   
		   param.put("beginTimeOneYear", DateUtil.comparYearTime(beginTime,-1));
		   param.put("endTimeOneYear", DateUtil.comparYearTime(endTime,-1));
		   if(circle==1){
 
		   }if(circle==2){
			   param.put("beginTimeTwoYear",  DateUtil.comparYearTime(beginTime,-2));
			   param.put("endTimeTwoYear", DateUtil.comparYearTime(endTime,-2));
		   }
		   return param;
	}
		
	
	public static String junitText(String str) {
		 // str="hello";
		return str;
	}

}
