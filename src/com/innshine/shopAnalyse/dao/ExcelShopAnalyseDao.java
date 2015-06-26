package com.innshine.shopAnalyse.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.dao.SqlDao;
import com.base.dao.sql.ConvertPageQueryFieldsToSQL;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.MonthEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseGroup;
import com.innshine.shopAnalyse.excelEntity.ShopDayEntity;
import com.innshine.shopAnalyse.excelEntity.TOP10Entity;
import com.innshine.shopAnalyse.util.DateUtil;
import com.innshine.shopAnalyse.util.OtherTool;
import com.innshine.shopAnalyse.util.TimeTool;
import com.utils.reflection.ReflectionUtils;

/**
 * 处理销售分析报表导出dao
 * 
 */
@Repository
public class ExcelShopAnalyseDao
{

	private int SHOP_MONTH = 0;
	private int SHOP_ANALYSE_MONTH = 1;

	@Autowired
	private SqlDao sqlDao;

	/**
	 * 功能:top10数据获取
	 * <p>
	 * 作者 admin 2014-9-23 上午09:31:10
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TOP10Entity> getTop10List(Map<String, Object> param)
			throws Exception
	{
		return (sqlDao.queryListBySql(param, "shopAnalyse.top10",
				TOP10Entity.class));

	}

	/**
	 * 功能:
	 * <p>
	 * 作者 admin 2014-9-23 上午09:31:28
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ShopDayEntity> getShopDay(Map<String, Object> param)
			throws Exception
	{
		return (sqlDao.queryListBySql(param, "shopAnalyse.getShopDay",
				ShopDayEntity.class));
	}

	/**
	 * 功能:导出月报/月度统计 表格数据
	 * <p>
	 * 作者 admin 2014-9-23 上午09:31:35
	 * 
	 * @param param
	 * @param timelist
	 * @param type
	 *            :0是月报，type:1是月度统计
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<MonthEntity>> getMonthAnalse(
			Map<String, Object> param, List timelist, int type)
			throws Exception
	{

		Map<String, List<MonthEntity>> map = Collections
				.synchronizedMap(new LinkedHashMap());
		List<String> monthList = OtherTool.getMonthShanlyse(timelist);
		param = TimeTool.MonthGetTime(monthList, param, timelist);
		// 求各Q季各月的情况
		List<MonthEntity> quter_TimeList = sqlDao.queryListBySql(param,
				"shopAnalyse.getShopMonth", MonthEntity.class);

		List<Double> sumList = OtherTool.getMonthShanlyseSum(quter_TimeList,
				monthList);
		for (int i = 0; i < quter_TimeList.size(); i++)
		{
			List<MonthEntity> list = new ArrayList();
			for (int j = 0; j < monthList.size(); j++)
			{
				String month = monthList.get(j);
				String quater = quter_TimeList.get(i).getQuater();
				double sales = 0;// 数量
				double amount = 0;// 金额
				double accounted = 0;// 占比
				String rateRise = "-";// 金额增长率（本月除以上个月-1）*100%
				for (int m = 0; m < quter_TimeList.size(); m++)
				{
					MonthEntity monthEntity = quter_TimeList.get(m);
					String Quater = monthEntity.getQuater();
					String TimeMonth = monthEntity.getTimeMonth();
					if (Quater.equals(quater) && TimeMonth.equals(month))
					{
						rateRise = OtherTool.getMonthRateRise(quter_TimeList,
								monthEntity);
						sales = OtherTool.doNum(monthEntity.getAllSales());// 累加销售数量
						amount = OtherTool.doNum(monthEntity.getAllAmount()); // 累加销售额
						Double MonthAllPlate = sumList.get(j); // 当月各Q季总销售额

						// 销售占比=当月每天累加销售额/当月各Q季总销售额
						if (MonthAllPlate > 0)
						{
							accounted = Double
									.parseDouble(DateUtil
											.changeMoneyType((amount / MonthAllPlate) * 100));
						}
						break;
					}
				}
				MonthEntity monthData = new MonthEntity();
				if (type == 0)
				{
					monthData = new MonthEntity(sales, amount, accounted);// 销量// 销售额// 销售占比
				} else
				{
					monthData = new MonthEntity(sales, amount, accounted,
							rateRise);// 销量 销售额 销售占比,金额增长率
				}
				list.add(monthData);
			}
			map.put(quter_TimeList.get(i).getQuater(), list);
		}
		map.put("合计", OtherTool.getCollectMonthData(quter_TimeList, monthList,
				type));
		return map;
	}
	/**
	 * 功能:导出月报/月度统计 表格数据
	 * <p>
	 * 
	 * @param param
	 * @param timelist
	 * @param type
	 *            :0是月报，type:1是月度统计
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<MonthEntity>> getMonthAnalse(
			Map<String, Object> param, List timelist, int type
			,ShopAnalyseCheckEntity shopAnalyseCheckEntity)
			throws Exception
	{
		
		Map<String, List<MonthEntity>> map = Collections
		.synchronizedMap(new LinkedHashMap());
		List<String> monthList = OtherTool.getMonthShanlyse(timelist);
		param = TimeTool.MonthGetTime(monthList, param, timelist);
		// 求各Q季各月的情况
		List<MonthEntity> quter_TimeList = sqlDao.queryListBySql(param,
				"shopAnalyse.getShopMonth", MonthEntity.class);
		
		List<Double> sumList = OtherTool.getMonthShanlyseSum(quter_TimeList,
				monthList);
		for (int i = 0; i < quter_TimeList.size(); i++)
		{
			List<MonthEntity> list = new ArrayList();
			String quater = getValue(quter_TimeList.get(i),shopAnalyseCheckEntity);
			for (int j = 0; j < monthList.size(); j++)
			{
				String month = monthList.get(j);
				double sales = 0;// 数量
				double amount = 0;// 金额
				double accounted = 0;// 占比
				String rateRise = "-";// 金额增长率（本月除以上个月-1）*100%
				for (int m = 0; m < quter_TimeList.size(); m++)
				{
					MonthEntity monthEntity = quter_TimeList.get(m);
					String Quater = getValue(monthEntity,shopAnalyseCheckEntity);
					String TimeMonth = monthEntity.getTimeMonth();
					if (Quater.equals(quater) && TimeMonth.equals(month))
					{
						rateRise = OtherTool.getMonthRateRise(quter_TimeList,
								monthEntity,shopAnalyseCheckEntity);
						sales = OtherTool.doNum(monthEntity.getAllSales());// 累加销售数量
						amount = OtherTool.doNum(monthEntity.getAllAmount()); // 累加销售额
						Double MonthAllPlate = sumList.get(j); // 当月各Q季总销售额
						
						// 销售占比=当月每天累加销售额/当月各Q季总销售额
						if (MonthAllPlate > 0)
						{
							accounted = Double
							.parseDouble(DateUtil
									.changeMoneyType((amount / MonthAllPlate) * 100));
						}
						break;
					}
				}
				MonthEntity monthData = new MonthEntity();
				if (type == 0)
				{
					monthData = new MonthEntity(sales, amount, accounted);// 销量// 销售额// 销售占比
				} else
				{
					monthData = new MonthEntity(sales, amount, accounted,
							rateRise);// 销量 销售额 销售占比,金额增长率
				}
				list.add(monthData);
			}
			map.put(quater, list);
		}
		map.put("合计", OtherTool.getCollectMonthData(quter_TimeList, monthList,
				type));
		return map;
			}
	
	
	
	/**
	 * 根据页面选择的字段，通过反射获取对应字段值
	 * @param monthEntity 导出excel对象
	 * @param shopAnalyseCheckEntity 前端页面参数对象
	 * @return String
	 */
	public static String getValue(MonthEntity monthEntity, ShopAnalyseCheckEntity shopAnalyseCheckEntity)
	{
		String value = "";
		try
		{
			if(null != monthEntity && null != shopAnalyseCheckEntity)
			{
				String monthlyFieldName =  shopAnalyseCheckEntity.getMonthlyFieldName();
				if(StringUtils.isNotEmpty(monthlyFieldName))
				{	
					monthlyFieldName = monthlyFieldName.indexOf("_") != -1 ?ConvertPageQueryFieldsToSQL.columnNameToFieldName(monthlyFieldName):monthlyFieldName;
					Object obj = ReflectionUtils.getFieldValue(monthEntity, monthlyFieldName);
					if(null != obj)
					{
						value = String.valueOf(obj);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return value;
	}

	/**
	 * 功能:销售分析+单个分类数据获取
	 * <p>
	 * 作者 admin 2014-9-23 上午09:31:41
	 * 
	 * @param param
	 * @param groupSelect
	 * @return
	 * @throws Exception
	 */
	public List<ShopAnalyseEntity> getShopAnalyseOne(Map<String, Object> param,
			String groupSelect[]) throws Exception
	{
		List<ShopAnalyseEntity> resultList = null;
		if (OtherTool.checkShopAnalyseQ(groupSelect[0]))
		{
			resultList = sqlDao.queryListBySql(param,
					"shopAnalyse.productQuater", ShopAnalyseEntity.class);
		} else
		{
			resultList = sqlDao.queryListBySql(param,
					"shopAnalyse.productType", ShopAnalyseEntity.class);
		}
		return resultList;
	}

	/**
	 * 功能: 销售分析+分类数据获取
	 * <p>
	 * 作者 admin 2014-9-23 上午09:31:46
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ShopAnalyseGroup> getShopAnalyseGroup(Map<String, Object> param)
			throws Exception
	{
		List<ShopAnalyseGroup> resultList = sqlDao.queryListBySql(param,
				"shopAnalyse.productGroupType", ShopAnalyseGroup.class);
		return resultList;
	}

	/**
	 * 根据参数列表，查询对应数据
	 * 
	 * @param <T>
	 *        返回的数据对象
	 * @param param
	 *        map SQL参数列表
	 * @param sqlKey
	 *        配置 文件中的KEY
	 * @param classzz
	 *        返回的数据对象Class
	 * @return List< T>
	 * @throws Exception
	 */
	public <T> List<T> queryDataByKeyAndParams(Map<String, Object> param, String sqlKey, Class<T> classzz)
			throws Exception
	{
		return sqlDao.queryListBySql(param, sqlKey, classzz);
	}
}
