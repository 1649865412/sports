package com.innshine.stockAnalyse.util;

import java.util.Map;

import com.innshine.stockAnalyse.util.TimeTool;
import com.innshine.stockAnalyse.entity.StockTime;

public class ParamTool {
	
	
	public static Map<String, Object> getShopAnaylseTimeCheckParam(
			Map<String, Object> param,StockTime StockTime)
			throws Exception {
		StockTime = TimeTool.check(StockTime);
		Map<String, String> timemap = TimeTool.getMapTime(
				StockTime, 0);

		param.put("beginTime", timemap.get("beginTime"));
		param.put("endTime", timemap.get("endTime"));

		param = timeAnalyseDataMap(param, StockTime);

		return param;
	}

	
	public static Map<String, Object> timeAnalyseDataMap(
			Map<String, Object> param,
			StockTime shopAnalyseTimeCheckEntity) {
		
		String quater = shopAnalyseTimeCheckEntity.getQuater();
		String series = shopAnalyseTimeCheckEntity.getSeries();
		
		Long brandId = shopAnalyseTimeCheckEntity.getBrandId();
		if (quater != null && (quater != ""))
			param.put("quater", quater);
		if (series != null && (series != ""))
			param.put("series", series);
		if (brandId != null)
			param.put("brandId", brandId);
		return param;
	}

}
