package com.innshine.stockAnalyse.entity;

public class StockTimeList
{

	/**
	 * 库存分析模块时间维度查询页面Bean
	 */
	private String productName;
	private String productType;
	private String orderNumber;
	private String arriveNumber;
	private String arriveRate;
	private String dynamicRate;
	private String salesoutRate;
	private String stockNumber;
	private String series;
	private String quater;

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public String getQuater()
	{
		return quater;
	}

	public void setQuater(String quater)
	{
		this.quater = quater;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
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

	public String getStockNumber()
	{
		return stockNumber;
	}

	public void setStockNumber(String stockNumber)
	{
		this.stockNumber = stockNumber;
	}

}
