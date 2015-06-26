package com.innshine.stockAnalyse.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.innshine.stockAnalyse.entity.StockTime;

public class OtherTool {


	
	/**
	 * 功能:获取时间纵向分析图的图例与横轴
	 * <p>作者 杨荣忠 2014-9-23 下午04:50:30
	 * @param suitTime
	 * @param timemapNow
	 * @return
	 * @throws ParseException
	 */
	public static Map<String ,String[]> getXline_TypeValue(StockTime suitTime,Map<String, String> timemapNow) throws ParseException{
		
		String SuitselectTimeType=suitTime.getSuitselectTimeType();
		Map<String ,String[]>map =new HashMap(); 
		String firstTime = timemapNow.get("beginTime");
		String endTime = timemapNow.get("endTime");
		
		String[] typeValue = null;// 图例
		String[] xValue = null;// 横轴
		
		if (SuitselectTimeType.equals("M") || SuitselectTimeType.equals("R") || "W".equalsIgnoreCase(SuitselectTimeType))
		{
			if (DateUtil.RMDayCheck(firstTime, endTime)) {
				endTime = DateUtil.getNewDay(45, firstTime);
			}
			xValue = DateUtil.xValueRM(firstTime, endTime);
			typeValue = DateUtil.getTypeValueRM(firstTime, endTime, suitTime
					.getCircle());
		}

		else if (SuitselectTimeType.equals("Z")) {
			xValue = DateUtil.xValueZ(suitTime.getQuarterfirstTime(), suitTime
					.getQuarterendTime());
			typeValue = DateUtil.getTypeValueZ(suitTime.getQuarterfirstTime(),
					suitTime.getQuarterendTime(), suitTime.getYear(), suitTime
							.getCircle());
		}

		else if (SuitselectTimeType.equals("Y")) {
			int Quarterfirst = 1;
			int Quarterendend = 4;
			xValue = DateUtil.xValueY(Quarterfirst, Quarterendend);
			typeValue = DateUtil.getTypeValueY(suitTime.getYear(), suitTime
					.getCircle());

		}
		map.put("typeValue", typeValue);
		map.put("xValue", xValue);
		return map;
	}
	
	

	public static String[] concat(String[] a, String[] b) {
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
    

	public static double doNum(String value) {
		double num = 0;
		try {
			num = value != null ? Double.parseDouble(value + "") : 0;
		} catch (Exception e) {
			num = 0;
		}
		return num;

	}
	
	
	public static List<Integer> getNum(int begin, int end) {
		List<Integer> list = new ArrayList();
		for (int i = begin; i <= end; i++) {
			list.add(i);
		}
		return list;
	}

}
