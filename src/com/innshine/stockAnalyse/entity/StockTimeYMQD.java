package com.innshine.stockAnalyse.entity;

public class StockTimeYMQD
{

	/**
	 * 功能bean 库存分析时间维度图数据与表格数据导出 (Y年下的季度Z查询) (季度Z下的月查询) (月下的日查询) (日查询) (R日M月Z季Y年
	 * 库存数量 订货数量 出货数量 到货率(%) 动销率(%) 售罄率(%)
	 */
	private String stockNumber;
	private String orderNumber;
	private String arriveNumber;

	private String arriveRate;
	private String dynamicRate;
	private String salesoutRate;

	private String timeGroup;
	private String yearTime;

	public String getYearTime()
	{
		return yearTime;
	}

	public void setYearTime(String yearTime)
	{
		this.yearTime = yearTime;
	}

	public String getStockNumber()
	{
		return stockNumber;
	}

	public void setStockNumber(String stockNumber)
	{
		this.stockNumber = stockNumber;
	}

	public String getOrderNumber()
	{
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public String getArriveNumber()
	{
		return arriveNumber;
	}

	public void setArriveNumber(String arriveNumber)
	{
		this.arriveNumber = arriveNumber;
	}

	public String getArriveRate()
	{
		return arriveRate;
	}

	public void setArriveRate(String arriveRate)
	{
		this.arriveRate = arriveRate;
	}

	public String getDynamicRate()
	{
		return dynamicRate;
	}

	public void setDynamicRate(String dynamicRate)
	{
		this.dynamicRate = dynamicRate;
	}

	public String getSalesoutRate()
	{
		return salesoutRate;
	}

	public void setSalesoutRate(String salesoutRate)
	{
		this.salesoutRate = salesoutRate;
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
