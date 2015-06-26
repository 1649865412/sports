package com.innshine.shopAnalyse.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.dao.SqlDao;
import com.base.util.dwz.Page;
import com.innshine.shopAnalyse.entity.ParamList;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckResult;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckResult;
import com.innshine.shopAnalyse.entity.ShopTimeYMQD;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseGroup;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseTimeGroup;
import com.innshine.shopAnalyse.util.OtherTool;

/*******
 * HQL 查询DAO 销售分析功能模块
 * 
 */
@Repository
public class ShopAnalyseDao
{

	@Autowired
	private SqlDao sqlDao;

	/**
	 * 销售分析页面查询(界面所有查询条件)
	 * 
	 */
	public List<ShopAnalyseCheckResult> getShopAnalyseCheckEntityList(
			Map<String, Object> param, Page page) throws Exception
	{
		List<ShopAnalyseCheckResult> result = sqlDao.queryByPageObject(param,
				"shopAnalyse.CheckResult", page, ShopAnalyseCheckResult.class);
		return result;
	}

	/**
	 * 销售分析分类导出
	 * 
	 */
	public List<ShopAnalyseTimeGroup> getShopAnalyseTimeGroup(
			Map<String, Object> param, ShopAnalyseTimeCheckEntity shopTime)
			throws Exception
	{
		String[] SQLArray = OtherTool.getShopTimeAnalyseSQLarray(shopTime);
		String str = OtherTool.addSQL(SQLArray, param);
		List<ShopAnalyseTimeGroup> list = sqlDao.queryListBySql(str,
				ShopAnalyseTimeGroup.class);
		return list;
	}

	/**
	 * 销售分析页面获取相关下拉属性(下拉属性类别编号， <!--
	 * 属性类别编号，0：品类，1：系列，2：产品功效,3:产品类型，4：分销类型,-1全部-->))
	 * 
	 */
	public List<ParamList> getParamTypeList(Map<String, Object> param)
			throws Exception
	{
		return sqlDao.queryListBySql(param, "shopAnalyse.ParamList",
				ParamList.class);
	}

	/**
	 * 销售分析模块(时间维度分析)查询(R日M月Z季Y年)
	 * 
	 */
	public List<ShopAnalyseTimeCheckResult> getShopAnalyseTimeCheckEntityList(
			Map<String, Object> param, Page page) throws Exception
	{
		List<ShopAnalyseTimeCheckResult> result = sqlDao.queryByPageObject(
				param, "shopAnalyse.timeGetList", page,
				ShopAnalyseTimeCheckResult.class);
		return result;
	}

	/**
	 * 销售分析时间维度查询图数据图例(R日M月Z季Y年)
	 * 
	 */
	public List<ShopAnalyseGroup> getTimeTypeValue(Map<String, Object> param)
			throws Exception
	{
		List<ShopAnalyseGroup> list = sqlDao
				.queryListBySql(param, "shopAnalyse.salesDetailsTimeYZMRGroup",
						ShopAnalyseGroup.class);
		return list;
	}

	/**
	 * 销售分析时间维度查询图数据导出 (R日M月Z季Y年) 横轴，图例，类型
	 */
	public double[][] timeAnalyseYZMR(Map<String, Object> param,
			String[] xValue, String[] typeValue, String timeType)
			throws Exception
	{
		double[][] data = new double[typeValue.length][xValue.length];// 各图例点集
		List<ShopTimeYMQD> list = sqlDao.queryListBySql(param,
				"shopAnalyse.salesDetailsTimeYZMRPoint", ShopTimeYMQD.class);
		for (int i = 0; i < typeValue.length; i++)
		{
			String groupType = typeValue[i];
			for (int j = 0; j < xValue.length; j++)
			{
				String value = "0";
				if (list.size() > 0)
				{
					String time = "";
					if (timeType.equals("Z"))
					{
						time = xValue[j].substring(0, xValue[j].length() - 1);
					} else
					{
						time = xValue[j] + "";
					}
					for (int m = 0; m < list.size(); m++)
					{
						ShopTimeYMQD shopTimeYMQD = list.get(m);
						String sqlTime = shopTimeYMQD.getTimeGroup() + "";
						String tagType = shopTimeYMQD.getName();
						String valueResult = shopTimeYMQD.getSalesNumSum() != null ? shopTimeYMQD
								.getSalesNumSum()
								+ ""
								: "0";
						if (tagType.equals(groupType))
						{
							if (timeType.equals("Z"))
							{
								if (time.equals(sqlTime))
								{
									value = valueResult;
									break;
								}
							} else if (timeType.equals("Y"))
							{
								if (time.substring(0, 1).equals(
										sqlTime.substring(0, 1)))
								{
									value = valueResult;
									break;
								}
							} else
							{
								String timevValue = sqlTime.substring(4, 6)
										+ "-" + sqlTime.substring(6);
								if (timevValue.equals(time))
								{
									value = valueResult;
									break;
								}
							}
						}
					}
				}
				data[i][j] = Double.parseDouble(value);
			}
		}
		return data;
	}
}
