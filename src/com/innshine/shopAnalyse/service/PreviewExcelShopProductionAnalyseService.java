package com.innshine.shopAnalyse.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innshine.common.utils.ExportFieldsConfigUtils;
import com.innshine.shopAnalyse.Excelprocess.MonthExcelPorcess;
import com.innshine.shopAnalyse.Excelprocess.MonthShopAnalyseExcelPorcess;
import com.innshine.shopAnalyse.Excelprocess.TOP10_Day_ShopAnalyseType_ExcelPorcess;
import com.innshine.shopAnalyse.checkInterface.PreviewAchieveInterface;
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
 * Excel报表预览业务类
 */
@Service("previewExcelShopProductionAnalyseService")
@Transactional
public class PreviewExcelShopProductionAnalyseService extends
		BaseExcelShopAnaylse implements PreviewAchieveInterface
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
	public String getTop10List(Map<String, Object> param,
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
		String str = TOP10_Day_ShopAnalyseType_ExcelPorcess.exportPreviewPoi(
				config, response, request);
		return str;
	}

	/**
	 * 销售日报
	 */
	public String getShopDay(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		param = ParamTool.getShopAnaylseCheckParam(param,
				shopAnalyseCheckEntity);
		List<ShopDayEntity> ShopDayEntityList = ParamTool
				.ShopDayEntityGetDiscount(excelShopAnalyseDao.getShopDay(param));
		ExportConfig<ShopDayEntity> config = getShopDayEntityListConfig(
				ShopDayEntityList, shopAnalyseCheckEntity);
		String str = TOP10_Day_ShopAnalyseType_ExcelPorcess.exportPreviewPoi(
				config, response, request);
		return str;

	}

	/**
	 * 月报
	 */
	public String getShopMonth(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		param = ParamTool.getShopAnaylseCheckParam(param,
				shopAnalyseCheckEntity);
		List timelist = DateUtil.getMonthList(shopAnalyseCheckEntity
				.getBeginTime(), shopAnalyseCheckEntity.getEndTime());
		Map<String, List<MonthEntity>> ShopDayEntityMap = excelShopAnalyseDao
				.getMonthAnalse(param, timelist, SHOP_MONTH);
		String str = MonthExcelPorcess.exportPreviewPoi(ShopDayEntityMap,
				timelist, response, request, ExcelConstant.SHOP_MONTH,
				shopAnalyseCheckEntity);
		return str;

	}

	/**
	 * 月度统计
	 * 
	 * @throws Exception
	 * 
	 */
	public String getMonthAnalse(Map<String, Object> param,
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
		String str = MonthShopAnalyseExcelPorcess.exportPreviewPoi(
				ShopDayEntityMap, timelist, response, request,
				ExcelConstant.SHOP_ANAYLSE_MONTH, shopAnalyseCheckEntity,getMonthlyTitleByFieldName(shopAnalyseCheckEntity),imagetPaths);
		// modifty 2014-10-17 问题描述：将销量、销售额分开，折线表时趋势 end
		
		return str;	
	}

	/**
	 * 获取月度分析报表，标题列名
	 * @param shopAnalyseCheckEntity 页面参数对象
	 * @return 标题名
	 */
	public static String getMonthlyTitleByFieldName(ShopAnalyseCheckEntity shopAnalyseCheckEntity)
	{	
		if(null !=shopAnalyseCheckEntity)
		{
			// 页面选择的字段
			String monthlyFieldName = shopAnalyseCheckEntity.getMonthlyFieldName();
			
			// export-fields-config.xml 配置文件中配置的字段列表
			Map<String, String>  fieldsMap = ExportFieldsConfigUtils.getInstance().getFieldsConfig("shop.analyse.monthly");
			if(MapUtils.isNotEmpty(fieldsMap))
			{
				return fieldsMap.get(monthlyFieldName);
			}
		}
		
		return "";
	}

	/**
	 *销售分析
	 */
	public String getShopAnalse(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		String str = "";
		
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
			// 单个预览
			List<ShopAnalyseEntity> ShopAnalyseEntityList = OtherTool
					.ShopAnalyseEntityGetSumAndDiscountCheck(
							excelShopAnalyseDao.getShopAnalyseOne(param,
									groupSelect), groupSelect);
			ExportConfig<ShopAnalyseEntity> config = getShopAnalyseEntityListConfig(
					ShopAnalyseEntityList, groupSelect, shopAnalyseCheckEntity);
			str = TOP10_Day_ShopAnalyseType_ExcelPorcess.exportPreviewPoi(
					config, response, request);
		} else
		{
			// 分类预览
			List<ShopAnalyseGroup> ShopAnalyseEntityList = ParamTool
					.ShopAnalyseEntityGetGroupSumAndDiscountCheck(excelShopAnalyseDao
							.getShopAnalyseGroup(param),groupSelect);
			ExportConfig<ShopAnalyseGroup> config = getShopAnalyseEntityListGroupConfig(
					ShopAnalyseEntityList, groupSelect, shopAnalyseCheckEntity);
			str = TOP10_Day_ShopAnalyseType_ExcelPorcess.exportPreviewPoi(
					config, response, request);
		}
		return str;
	}

	/**
	 *月报摘要
	 */
	public String getShopMonthPoint(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		return "";

	}

}
