package com.innshine.stockAnalyse.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.sql.ConvertPageQueryFieldsToSQL;
import com.base.util.dwz.Page;
import com.innshine.stockAnalyse.dao.StockAnaylseDao;
import com.innshine.stockAnalyse.entity.StockTime;
import com.innshine.stockAnalyse.entity.StockTimeList;
import com.innshine.stockAnalyse.util.ParamTool;
import com.innshine.stockAnalyse.util.TimeTool;

@Service
@Transactional
public class StockProductionAnalyseService
{

	@Autowired
	private StockAnaylseDao suitAnaylseDao;

	/**
	 * 库存分析模块(时间维度分析查询)(R日M月Z季Y年)
	 */
	public List<StockTimeList> getTimeGetList(Map<String, Object> param,
			StockTime suitTime, Page page) throws Exception
	{
		param = ParamTool.getShopAnaylseTimeCheckParam(param, suitTime);
		addPageParam(param,page);
		return suitAnaylseDao.getTimeGetList(param, page);
	}
	private  void addPageParam(Map<String, Object> param, Page page)
	{
		if(MapUtils.isNotEmpty(param) && null != page)
		{
			if(StringUtils.isNotEmpty(page.getOrderField()) && StringUtils.isNotEmpty(page.getOrderDirection()))
			{
				String orderField = page.getOrderField();
				param.put("orderField", ConvertPageQueryFieldsToSQL.fieldNameToColumName(orderField));
				param.put("orderDirection", page.getOrderDirection());
			}
		}
		
	}
	/**
	 * 库存分析时间维度查询图数据导出 (R日M月Z季Y年)
	 * 
	 */
	public double[][] timeAnalyseYZMR(Map<String, Object> param,
			String[] xValue, String[] typeValue, String timeType,
			StockTime suitTime) throws Exception
	{
		param = ParamTool.getShopAnaylseTimeCheckParam(param, suitTime);
		param = TimeTool.TimeTypeSql(param, timeType);
		return suitAnaylseDao.timeAnalyseYZMR(param, xValue, typeValue,
				timeType);
	}

	/**
	 * 库存分析时间维度查询表格数据导出(R日M月Z季Y年)
	 */
	public Map<String, List<Object[]>> timeAnalyseDataMap(
			Map<String, Object> param, String[] typeValue, String[] tableDay,
			StockTime suitTime) throws Exception
	{
		param = TimeTool.getMapTimeAll(param, suitTime);
		param.put("condition", "DATE_FORMAT(b.sales_time,'%Y%m%d')");
		// 时间重设为全局即同比的最初年限开始时间到现在的结束时间
		param = ParamTool.timeAnalyseDataMap(param, suitTime);
		return suitAnaylseDao.timeAnalyseDataMap(param, typeValue, tableDay);
	}

}
