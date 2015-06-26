package com.innshine.shopAnalyse.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseGroup;
import com.innshine.shopAnalyse.excelEntity.ShopDayEntity;
import com.innshine.shopAnalyse.excelEntity.TOP10Entity;
import com.innshine.shopAnalyse.util.ConditionText;
import com.innshine.shopAnalyse.util.ExcelConstant;
import com.innshine.shopAnalyse.util.OtherTool;
import com.innshine.shopAnalyse.util.ParamTool;
import com.utils.excel.template.ExportConfig;

public class BaseExcelShopAnaylse
{
	/**
	 * 功能: top10报表对象
	 * <p>
	 * 作者 杨荣忠 2014-9-23 上午09:36:49
	 * 
	 * @param TOP10EntityList
	 * @param shopAnalyseCheckEntity
	 * @return
	 * @throws Exception
	 */
	public ExportConfig<TOP10Entity> getTop10ListConfig(
			List<TOP10Entity> TOP10EntityList,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		ExportConfig<TOP10Entity> config = new ExportConfig<TOP10Entity>();

		Vector<String> displayProperties = OtherTool
				.getHeaderAndDisplayProperties(ExcelConstant.DISPLAY_TOP_10);
		Vector<String> header = OtherTool
				.getHeaderAndDisplayProperties(ExcelConstant.TOP_10_HEAD);
		config.setName(ExcelConstant.TOP_10);
		config.setConditionText(ConditionText
				.ChangeText(shopAnalyseCheckEntity));
		config.setRootCls(TOP10Entity.class);
		config.setDisplayProperties(displayProperties);
		config.setHeader(header);
		config.setData(TOP10EntityList);
		config = OtherTool.top10_daySales_Column(config, shopAnalyseCheckEntity
				.getTopColumn());
		config.setStartRow(1);
		return config;
	}

	/**
	 * 功能:日报报表对象
	 * <p>
	 * 作者 杨荣忠 2014-9-23 上午09:37:28
	 * 
	 * @param ShopDayEntityList
	 * @param shopAnalyseCheckEntity
	 * @return
	 * @throws Exception
	 */
	public ExportConfig<ShopDayEntity> getShopDayEntityListConfig(
			List<ShopDayEntity> ShopDayEntityList,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		ExportConfig<ShopDayEntity> config = new ExportConfig<ShopDayEntity>();
		Vector<String> displayProperties = OtherTool
				.getHeaderAndDisplayProperties(ExcelConstant.DISPLAY_SHOP_DAY);
		Vector<String> header = OtherTool
				.getHeaderAndDisplayProperties(ExcelConstant.SHOP_DAY_HEAD);
		config.setName(ExcelConstant.SHOP_DAY);
		config.setConditionText(ConditionText
				.ChangeText(shopAnalyseCheckEntity));
		config.setRootCls(ShopDayEntity.class);
		config.setDisplayProperties(displayProperties);
		config.setHeader(header);
		config.setData(ShopDayEntityList);
		config = OtherTool.top10_daySales_Column(config, shopAnalyseCheckEntity
				.getDaySalesColumn());
		config.setStartRow(1);
		return config;
	}

	/**
	 * 功能:销售分析+单个分类导出
	 * <p>
	 * 作者 杨荣忠 2014-9-23 上午09:37:47
	 * 
	 * @param ShopAnalyseEntityList
	 * @param groupSelect
	 * @param shopAnalyseCheckEntity
	 * @return
	 * @throws Exception
	 */
	public ExportConfig<ShopAnalyseEntity> getShopAnalyseEntityListConfig(
			List<ShopAnalyseEntity> ShopAnalyseEntityList,
			String[] groupSelect, ShopAnalyseCheckEntity shopAnalyseCheckEntity)
			throws Exception
	{
		ExportConfig<ShopAnalyseEntity> config = new ExportConfig<ShopAnalyseEntity>();
		String[] head = null;
		String[] display = null;
		if (OtherTool.checkShopAnalyseQ(groupSelect[0]))
		{
			// Q 季
			head = ExcelConstant.SHOP_ANALYSE_QUARTER;
			display = ExcelConstant.DISPLAY_SHOP_ANALYSE_QUARTER;

			// Q季字段太少，扩展屏幕
			config.setColumnWidth(24);
		} else
		{
			// 系列，line ，性别(导出字段)（单独）
			head = ExcelConstant.SHOP_ANALYSE_GROUP_OTHER_ONE_HEAD;
			display = OtherTool.concat(OtherTool.getUpcase(groupSelect),
					ExcelConstant.DISPLAY_SHOP_ANALYSE_GROUP_OTHER_ONE);
		}

		Vector<String> displayProperties = OtherTool
				.getHeaderAndDisplayProperties(display);
		Vector<String> header = OtherTool.getHeaderAndDisplayProperties(head);

		config.setName(ExcelConstant.SHOP_ANAYLSE);
		config.setConditionText(ConditionText
				.ChangeText(shopAnalyseCheckEntity));
		config.setRootCls(ShopAnalyseEntity.class);
		config.setDisplayProperties(displayProperties);
		config.setHeader(header);
		config.setData(ShopAnalyseEntityList);
		config.setStartRow(1);
		return config;
	}

	/**
	 * 功能:销售分析+分类导出
	 * <p>
	 * 作者 杨荣忠 2014-9-23 上午09:38:11
	 * 
	 * @param ShopAnalyseEntityList
	 * @param groupSelect
	 * @param shopAnalyseCheckEntity
	 * @return
	 * @throws Exception
	 */
	public ExportConfig<ShopAnalyseGroup> getShopAnalyseEntityListGroupConfig(
			List<ShopAnalyseGroup> ShopAnalyseEntityList, String[] groupSelect,
			ShopAnalyseCheckEntity shopAnalyseCheckEntity) throws Exception
	{
		ExportConfig<ShopAnalyseGroup> config = new ExportConfig<ShopAnalyseGroup>();

		String[] head = OtherTool.concat(OtherTool
				.getShopAnalyseGroupStr(groupSelect),
				ExcelConstant.SHOP_ANALYSE_GROUP);

		String[] display = OtherTool.concat(OtherTool.getUpcase(groupSelect),
				ExcelConstant.DISPLAY_SHOP_ANALYSE_GROUP);

		Vector<String> displayProperties = OtherTool
				.getHeaderAndDisplayProperties(display);
		Vector<String> header = OtherTool.getHeaderAndDisplayProperties(head);

		Vector<String> groupProperties = OtherTool
				.getHeaderAndDisplayProperties(OtherTool.getUpcase(groupSelect));

		config.setColumnWidth(27);
		config.setName(ExcelConstant.SHOP_ANAYLSE);
		config.setConditionText(ConditionText
				.ChangeText(shopAnalyseCheckEntity));
		config.setRootCls(ShopAnalyseGroup.class);
		config.setDisplayProperties(displayProperties);
		config.setGroupProperties(groupProperties);
		config.setHeader(header);
		config.setData(ShopAnalyseEntityList);
		config.setStartRow(1);
		return config;
	}

}
