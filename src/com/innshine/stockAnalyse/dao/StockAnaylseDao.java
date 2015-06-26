package com.innshine.stockAnalyse.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.dao.SqlDao;
import com.base.util.dwz.Page;
import com.innshine.stockAnalyse.entity.StockTimeList;
import com.innshine.stockAnalyse.entity.StockTimeYMQD;
import com.innshine.stockAnalyse.util.DateUtil;
import com.innshine.stockAnalyse.util.OtherTool;

@Repository
public class StockAnaylseDao
{

	@Autowired
	private SqlDao sqlDao;

	/**
	 * 库存分析模块(时间维度分析)(R日M月Z季Y年)
	 * 
	 */
	public List<StockTimeList> getTimeGetList(Map<String, Object> param,
			Page page) throws Exception
	{
		List<StockTimeList> result = sqlDao.queryByPageObject(param,
				"suitAnalyse.timeGetList", page, StockTimeList.class);
		return result;
	}

	/**
	 * 库存分析时间维度查询图数据导出 (R日M月Z季Y年)
	 * 
	 */

	public double[][] timeAnalyseYZMR(Map<String, Object> param,
			String[] xValue, String[] typeValue, String timeType)
			throws Exception
	{

		double[][] data = new double[typeValue.length][xValue.length];// 各图例点集
		List<StockTimeYMQD> list = sqlDao.queryListBySql(param,
				"suitAnalyse.salesDetailsTimeYZMR", StockTimeYMQD.class);
		for (int i = 0; i < typeValue.length; i++)
		{
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

						StockTimeYMQD stockTimeYMQD = list.get(m);
						String sqlTime = stockTimeYMQD.getTimeGroup();
						String yearTime = stockTimeYMQD.getYearTime();
						String valueResult = stockTimeYMQD.getStockNumber() != null ? stockTimeYMQD
								.getStockNumber()
								+ ""
								: "0";
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
							String timeDay = (typeValue[i].substring(0, 4) + time
									.replace("-", ""));
							String year = typeValue[i].substring(2, 4);
							if (timeDay.equals(sqlTime)
									&& yearTime.equals(year))
							{
								value = valueResult;
								break;
							}
						}
					}
				}
				data[i][j] = Double.parseDouble(value);
			}
		}
		return data;

	}

	/**
	 * 功能:
	 * <p>
	 * 作者 杨荣忠 2014-9-23 上午10:43:07
	 * 
	 * @param param
	 * @param typeValue
	 * @param tableDay
	 * @return
	 * @throws Exception
	 *             库存 ： 订货量-销售量 
	 *             到货数量：销售数量+库存； 
	 *             售罄率 ：销售中，销售数量/到货数量；
	 *             到货率 ：到货量/订货数量；
	 *             动销率 ：销量/库存
	 */
	public Map<String, List<Object[]>> timeAnalyseDataMap(
			Map<String, Object> param, String[] typeValue, String[] tableDay)
			throws Exception
	{
		Map<String, List<Object[]>> dataMap = Collections
				.synchronizedMap(new LinkedHashMap());

		List<StockTimeYMQD> list = sqlDao.queryListBySql(param,
				"suitAnalyse.salesDetailsTimeYZMR", StockTimeYMQD.class);

		String avgStr = "(平均)";
		for (int i = 0; i < typeValue.length; i++)
		{
			// 库存数量 订货数量 出货数量 到货率(%) 动销率(%) 售罄率(%)
			String year = typeValue[i].substring(0, 4);
			List<Object[]> result = new ArrayList();
			double stockNumber_sum = 0;
			double orderNumber_sum = 0;
			double arriveNumber_sum = 0;

			String avg_arriveRate = "-";
			String avg_dynamicRate = "-";
			String avg_salesoutRate = "-";

			double sum_arriveRate = 0;
			double sum_dynamicRate = 0;
			double sum_salesoutRate = 0;

			int count = 0;

			for (int j = 0; j < tableDay.length; j++)
			{
				Object object[] = new Object[6];
				String time = year + tableDay[j].replace("-", "");
				if (list.size() > 0)
				{
					for (int m = 0; m < list.size(); m++)
					{
						StockTimeYMQD stockTimeYMQD = list.get(m);
						String sqlTime = stockTimeYMQD.getTimeGroup() + "";
						if (time.equals(sqlTime))
						{
							object = getObject(stockTimeYMQD);
							stockNumber_sum += OtherTool.doNum(stockTimeYMQD
									.getStockNumber());
							orderNumber_sum += OtherTool.doNum(stockTimeYMQD
									.getOrderNumber());
							arriveNumber_sum += OtherTool.doNum(stockTimeYMQD
									.getArriveNumber());

							sum_arriveRate += OtherTool.doNum(stockTimeYMQD
									.getArriveRate());
							sum_dynamicRate += OtherTool.doNum(stockTimeYMQD
									.getDynamicRate());
							sum_salesoutRate += OtherTool.doNum(stockTimeYMQD
									.getSalesoutRate());

							count += 1;
							break;
						}
					}
				}
				result.add(object);
			}

			// 所查时间范围内的产品的平均 ：到货率(%) 动销率(%) 售罄率(%)
			if (count != 0)
			{
				avg_arriveRate = avgStr
						+ DateUtil.changeMoneyType(sum_arriveRate / count);
				avg_dynamicRate = avgStr
						+ DateUtil.changeMoneyType(sum_dynamicRate / count);
				avg_salesoutRate = avgStr
						+ DateUtil.changeMoneyType(sum_salesoutRate / count);
			}

			result.add(new Object[] {
					DateUtil.changeMoneyType(stockNumber_sum),
					DateUtil.changeMoneyType(orderNumber_sum),
					DateUtil.changeMoneyType(arriveNumber_sum), avg_arriveRate,
					avg_dynamicRate, avg_salesoutRate, null });
			dataMap.put(typeValue[i], result);
		}
		return dataMap;
	}

	// 此处顺序用于生成excel的时候字段值对应顺序
	// 库存数量 订货数量 出货数量 到货率(%) 动销率(%) 售罄率(%)
	public Object[] getObject(StockTimeYMQD stockTimeYMQD)
	{
		double stockNumber, orderNumber, arriveNumber, arriveRate, dynamicRate, salesoutRate, timeGroup = 0;

		stockNumber = OtherTool.doNum(stockTimeYMQD.getStockNumber());
		orderNumber = OtherTool.doNum(stockTimeYMQD.getOrderNumber());
		arriveNumber = OtherTool.doNum(stockTimeYMQD.getArriveNumber());
		arriveRate = OtherTool.doNum(stockTimeYMQD.getArriveRate());
		dynamicRate = OtherTool.doNum(stockTimeYMQD.getDynamicRate());
		salesoutRate = OtherTool.doNum(stockTimeYMQD.getSalesoutRate());
		timeGroup = OtherTool.doNum(stockTimeYMQD.getTimeGroup());

		Object[] value = { DateUtil.changeMoneyType(stockNumber),
				DateUtil.changeMoneyType(orderNumber),
				DateUtil.changeMoneyType(arriveNumber),
				DateUtil.changeMoneyType(arriveRate),
				DateUtil.changeMoneyType(dynamicRate),
				DateUtil.changeMoneyType(salesoutRate), timeGroup };

		return value;

	}

}
