package com.innshine.shopAnalyse.entity;

public class ShopTimeYMQD
{

	/**
	 * 功能bean 销售分析时间维度图数据与表格数据导出(Y年下的季度Z查询) (季度Z下的月查询) (月下的日查询)(日查询) R日M月Z季Y年
	 * 
	 */
	private String salesNumSum;
	private String salesDiscountMoneySum;
	private String salesMoneySum;
	private String avgDiscount;
	private String timeGroup;
	private String stockNum;
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStockNum()
	{
		return stockNum;
	}

	public void setStockNum(String stockNum)
	{
		this.stockNum = stockNum;
	}

	public String getSalesNumSum()
	{
		return salesNumSum;
	}

	public void setSalesNumSum(String salesNumSum)
	{
		this.salesNumSum = salesNumSum;
	}

	public String getSalesDiscountMoneySum()
	{
		return salesDiscountMoneySum;
	}

	public void setSalesDiscountMoneySum(String salesDiscountMoneySum)
	{
		this.salesDiscountMoneySum = salesDiscountMoneySum;
	}

	public String getSalesMoneySum()
	{
		return salesMoneySum;
	}

	public void setSalesMoneySum(String salesMoneySum)
	{
		this.salesMoneySum = salesMoneySum;
	}

	public String getAvgDiscount()
	{
		return avgDiscount;
	}

	public void setAvgDiscount(String avgDiscount)
	{
		this.avgDiscount = avgDiscount;
	}

	public String getTimeGroup()
	{
		return timeGroup;
	}

	public void setTimeGroup(String timeGroup)
	{
		this.timeGroup = timeGroup;
	}

}
