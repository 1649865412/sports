package com.innshine.shopAnalyse.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innshine.shopAnalyse.Excelprocess.MonthExcelPorcess;
import com.innshine.shopAnalyse.Excelprocess.MonthShopAnalyseExcelPorcess;
import com.innshine.shopAnalyse.Excelprocess.TOP10_Day_ShopAnalyseType_ExcelPorcess;
import com.innshine.shopAnalyse.checkInterface.AchieveInterface;
import com.innshine.shopAnalyse.dao.ExcelShopAnalyseDao;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.MonthEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseGroup;
import com.innshine.shopAnalyse.excelEntity.ShopDayEntity;
import com.innshine.shopAnalyse.excelEntity.TOP10Entity;
import com.innshine.shopAnalyse.util.DateUtil;
import com.innshine.shopAnalyse.util.ExcelConstant;
import com.innshine.shopAnalyse.util.OtherTool;
import com.innshine.shopAnalyse.util.ParamTool;
import com.utils.excel.template.ExportConfig;

/**
 * Excel报表导出业务类
 */
@Service("excelShopProductionAnalyseService")
@Transactional
public class ExcelShopProductionAnalyseService extends BaseExcelShopAnaylse
		implements AchieveInterface
{

	private int SHOP_MONTH = 0;

	private int SHOP_ANALYSE_MONTH = 1;

	@Autowired
	private ExcelShopAnalyseDao excelShopAnalyseDao;
	
	@Autowired
	private MonthlyCustomAnalyseChartService monthlyCustomAnalyseChartService;

	/**
	 * top10
	 */
	public void getTop10List(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		param = ParamTool.getShopAnaylseCheckParam(param,
				shopAnalyseCheckEntity);
		List<TOP10Entity> TOP10EntityList = ParamTool
				.TOP10EntityListGetIndexAndDiscount(excelShopAnalyseDao
						.getTop10List(param));
		ExportConfig<TOP10Entity> config = getTop10ListConfig(TOP10EntityList,
				shopAnalyseCheckEntity);
		TOP10_Day_ShopAnalyseType_ExcelPorcess.exportExcelPoi(config, response,
				request);
	}

	/**
	 * 销售日报
	 */
	public void getShopDay(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		param = ParamTool.getShopAnaylseCheckParam(param,
				shopAnalyseCheckEntity);
		List<ShopDayEntity> ShopDayEntityList = ParamTool
				.ShopDayEntityGetDiscount(excelShopAnalyseDao.getShopDay(param));
		ExportConfig<ShopDayEntity> config = getShopDayEntityListConfig(
				ShopDayEntityList, shopAnalyseCheckEntity);
		TOP10_Day_ShopAnalyseType_ExcelPorcess.exportExcelPoi(config, response,
				request);
	}

	/**
	 * 月报
	 */
	public void getShopMonth(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{

		param = ParamTool.getShopAnaylseCheckParam(param,
				shopAnalyseCheckEntity);

		List timelist = DateUtil.getMonthList(shopAnalyseCheckEntity
				.getBeginTime(), shopAnalyseCheckEntity.getEndTime());
		Map<String, List<MonthEntity>> ShopDayEntityMap = excelShopAnalyseDao
				.getMonthAnalse(param, timelist, SHOP_MONTH);
		MonthExcelPorcess.exportExcelPoi(ShopDayEntityMap, timelist, response,
				request, ExcelConstant.SHOP_MONTH, shopAnalyseCheckEntity);
	}

	/**
	 * 月度统计
	 * 
	 * @throws Exception
	 * 
	 */
	public void getMonthAnalse(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		param = ParamTool.getShopAnaylseCheckParam(param,
				shopAnalyseCheckEntity);
		List timelist = DateUtil.getMonthList(shopAnalyseCheckEntity
				.getBeginTime(), shopAnalyseCheckEntity.getEndTime());

		Map<String, List<MonthEntity>> ShopDayEntityMap = excelShopAnalyseDao
				.getMonthAnalse(param, timelist, SHOP_ANALYSE_MONTH,shopAnalyseCheckEntity);

		// add 2014-10-17 问题描述：将销量、销售额分开，折线表时趋势 begin
		// 生成拆线图，并返回生成后的拆线图路径 
		List<String> imagetPaths = monthlyCustomAnalyseChartService.createChart(param, shopAnalyseCheckEntity);
		// add 2014-10-17 问题描述：将销量、销售额分开，折线表时趋势 end
		
		// modifty 2014-10-17 问题描述：将销量、销售额分开，折线表时趋势 begin
		MonthShopAnalyseExcelPorcess.exportExcelPoi(ShopDayEntityMap, timelist,
				response, request, ExcelConstant.SHOP_ANAYLSE_MONTH,
				shopAnalyseCheckEntity
				,PreviewExcelShopProductionAnalyseService.getMonthlyTitleByFieldName(shopAnalyseCheckEntity),imagetPaths);
		
		// modifty 2014-10-17 问题描述：将销量、销售额分开，折线表时趋势  end
	}

	/**
	 *销售分析
	 */
	public void getShopAnalse(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{	
		// modifty 2014-10-15  添加页面查询参数 begin 
		 param =  ParamTool.getShopAnaylseCheckParam(param, shopAnalyseCheckEntity);
		// modifty  2014-10-15  添加页面查询参数 end 
		
		param = ParamTool.getShopAnaylseCheckParamGroup(param,
				shopAnalyseCheckEntity);
		
		String ShopOptions = shopAnalyseCheckEntity.getShopOptions();
		String OrderList = shopAnalyseCheckEntity.getOrderList();
		String[] groupSelect = OtherTool.getShopAnalyseParam(ShopOptions,
				OrderList);
		
		param = ParamTool.ShopAnslyseParam(param, groupSelect);
		
		if (groupSelect.length == 1)
		{
			// 单个导出
			List<ShopAnalyseEntity> ShopAnalyseEntityList = OtherTool
					.ShopAnalyseEntityGetSumAndDiscountCheck(
							excelShopAnalyseDao.getShopAnalyseOne(param,
									groupSelect), groupSelect);
			ExportConfig<ShopAnalyseEntity> config = getShopAnalyseEntityListConfig(
					ShopAnalyseEntityList, groupSelect, shopAnalyseCheckEntity);
			TOP10_Day_ShopAnalyseType_ExcelPorcess.exportExcelPoi(config,
					response, request);
		} else
		{
			// 分类导出
			List<ShopAnalyseGroup> ShopAnalyseEntityList = ParamTool
					.ShopAnalyseEntityGetGroupSumAndDiscountCheck(excelShopAnalyseDao
							.getShopAnalyseGroup(param),groupSelect);
			ExportConfig<ShopAnalyseGroup> config = getShopAnalyseEntityListGroupConfig(
					ShopAnalyseEntityList, groupSelect, shopAnalyseCheckEntity);
			TOP10_Day_ShopAnalyseType_ExcelPorcess.exportExcelPoi(config,
					response, request);
		}
	}

	/**
	 *月报摘要
	 */
	@Override
	public void getShopMonthPoint(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception
	{

	}

}
