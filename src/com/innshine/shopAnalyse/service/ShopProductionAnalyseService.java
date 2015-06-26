package com.innshine.shopAnalyse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.sql.ConvertPageQueryFieldsToSQL;
import com.base.util.dwz.Page;
import com.innshine.shopAnalyse.dao.ShopAnalyseDao;
import com.innshine.shopAnalyse.entity.ParamList;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckResult;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckResult;
import com.innshine.shopAnalyse.excelEntity.ImgPathLine;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseGroup;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseTimeGroup;
import com.innshine.shopAnalyse.util.Constant;
import com.innshine.shopAnalyse.util.ExcelConstant;
import com.innshine.shopAnalyse.util.OtherTool;
import com.innshine.shopAnalyse.util.ParamTool;
import com.innshine.shopAnalyse.util.TimeTool;
import com.utils.excel.template.ExportConfig;

/**
 * 销售分析模块非报表操作业务类
 */

@Service
@Transactional
public class ShopProductionAnalyseService extends BaseExcelShopAnaylse
{

	/**
	 * 处理销售分析业务共公dao
	 * 
	 */
	@Autowired
	private ShopAnalyseDao shopAnalyseDao;

	/**
	 * 查询销售分析页面+(界面查询条件)
	 * 
	 */
	public List<ShopAnalyseCheckResult> getShopAnalyseCheckEntityList(
			Map<String, Object> param,
			ShopAnalyseCheckEntity shopAnalyseEntity, Page page)
			throws Exception
	{
		param = ParamTool.getShopAnaylseCheckParam(param, shopAnalyseEntity);
		
		// 添加页面自定义排序参数
		modiftyParams(param,page);
		
		return shopAnalyseDao.getShopAnalyseCheckEntityList(param, page);
	}
	
	/**
	 * 根据Page对象中的 page.getOrderField() page.getOrderDirection()，添加排序参数
	 * @param param 页面参数列表
	 * @param page 分页对象
	 */
	private void modiftyParams(Map<String, Object> param, Page page)
	{
		if(MapUtils.isNotEmpty(param) && null != page)
		{
			if(StringUtils.isNotEmpty(page.getOrderField()) && StringUtils.isNotEmpty(page.getOrderDirection()))
			{
				param.put("orderField", ConvertPageQueryFieldsToSQL.fieldNameToColumName(page.getOrderField()));
				param.put("orderDirection", page.getOrderDirection());
			}
		}
		
	}

	/**
	 * 销售分析模块(时间维度分析)(R日M月Z季Y年) selectTimeType 查询类型 year 年 firstTime 日开始时间
	 * MonthBegin 月开始时间 QuarterfirstTime 季开始时间 circle 周期 platid 平台id
	 */
	public List<ShopAnalyseTimeCheckResult> getShopAnalyseTimeCheckEntityList(
			Map<String, Object> param, ShopAnalyseTimeCheckEntity shopTime,
			Page page) throws Exception
	{
		param = ParamTool.getShopAnaylseTimeCheckParam(param, shopTime);
		addPageParam(param,page);
		return shopAnalyseDao.getShopAnalyseTimeCheckEntityList(param, page);
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
	 * 销售分析时间维度图数据导出 (R日M月Z季Y年)
	 */
	public double[][] timeAnalyseYZMR(Map<String, Object> param,
			String[] xValue, String[] typeValue,
			ShopAnalyseTimeCheckEntity shopTime) throws Exception
	{
		param = TimeTool.TimeTypeSql(param, shopTime.getSelectTimeType());
		param = ParamTool.timeAnalyseDataMapGroup(param, shopTime);
		return shopAnalyseDao.timeAnalyseYZMR(param, xValue, typeValue,
				shopTime.getSelectTimeType());
	}

	/**
	 * 销售分析时间维度图图例数据 (R日M月Z季Y年)
	 */
	public String[] getTimeTypeValue(Map<String, Object> param,
			ShopAnalyseTimeCheckEntity shopTime) throws Exception
	{
		param = ParamTool.timeAnalyseDataMapGroup(param, shopTime);
		param = OtherTool.getShopAnaylseTimeLastGroup(param, shopTime);
		List<ShopAnalyseGroup> list = shopAnalyseDao.getTimeTypeValue(param);
		String[] typeValue = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			typeValue[i] = list.get(i).getName();
		}
		return typeValue;
	}

	/**
	 * 销售分析页面获取相关下拉属性 (Q季，LF/PF，系列)
	 * 
	 */
	public List<List<ParamList>> getParamTypeList(Map<String, Object> param)
			throws Exception
	{
		List<List<ParamList>> paramList = new ArrayList();
		for (int i = 0; i < Constant.PARAM_ARRAY.length; i++)
		{
			param.put("ParamName", Constant.PARAM_ARRAY[i]);
			List<ParamList> list = shopAnalyseDao.getParamTypeList(param);
			paramList.add(list);
		}
		return paramList;
	}

	/**
	 * 销售分析时间维度导出 (R日M月Z季Y年)
	 */
	public ExportConfig<ShopAnalyseTimeGroup> getConfig(
			Map<String, Object> param, ImgPathLine imgPathLine,
			ShopAnalyseTimeCheckEntity shopTime) throws Exception
	{
		ExportConfig<ShopAnalyseTimeGroup> config = new ExportConfig<ShopAnalyseTimeGroup>();

		// 基本的查询过滤条件
		param = ParamTool.timeAnalyseDataMapGroup(param, shopTime);
		// 此处补上同比的各自时间
		param = TimeTool.timeAnalyseCompareYearParam(shopTime, param);
		
		// 分类类型条件参数
		String ShopOptions = shopTime.getShopOptionsNameTime();
		String OrderList = shopTime.getOrderListTime();
		
		String[] groupSelect = OtherTool.getShopAnalyseParam(ShopOptions,
				OrderList);
		param = ParamTool.ShopAnslyseTimeParam(param, groupSelect);

		List<ShopAnalyseTimeGroup> list = shopAnalyseDao
				.getShopAnalyseTimeGroup(param, shopTime);
		// 增加占比与增长率计算
		List<ShopAnalyseTimeGroup> shopAnalyseTimeGroup = ParamTool
				.getTimeAnalyse(list, shopTime);

		String[] head = OtherTool.ShopTimeAnalyseHead(shopTime, groupSelect,
				param);
		String[] display = OtherTool.ShopTimeAnalyseDisplay(shopTime,
				groupSelect);
		Vector<String> displayProperties = OtherTool
				.getHeaderAndDisplayProperties(display);
		Vector<String> header = OtherTool.getHeaderAndDisplayProperties(head);
		Vector<String> groupProperties = OtherTool
				.getHeaderAndDisplayProperties(OtherTool.getUpcase(groupSelect));

		config.setDisplayProperties(displayProperties);
		config.setName(ExcelConstant.SHOP_TIME_ANAYLSE);
		config.setGroupProperties(groupProperties);
		config.setConditionText(OtherTool.ShopTimeAnalyseText(param));
		config.setRootCls(ShopAnalyseTimeGroup.class);
		config.setDisplayProperties(displayProperties);
		config.setImgPathLine(imgPathLine);
		config.setHeader(header);
		config.setData(shopAnalyseTimeGroup);

		// 0到25行是图的所占行数
		config.setStartRow(25);
		return config;
	}

}
