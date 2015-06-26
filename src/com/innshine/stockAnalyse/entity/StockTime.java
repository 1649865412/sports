package com.innshine.stockAnalyse.entity;

import java.text.ParseException;

import com.innshine.stockAnalyse.util.DateUtil;

public class StockTime
{

	/**
	 * 功能Bean 库存分析模块(时间维度分析)(R日M月Z季Y年) selectTimeType 查询类型 year 年 firstTime
	 * 日开始时间 MonthBegin 月开始时间 QuarterfirstTime 季开始时间 circle 周期 platid 平台id
	 */

	public String suitselectTimeType = "R";
	public int year = 2014;
	public String firstTime = "";
	public String endTime = "";
	public String monthBegin = "1";
	public String monthEnd = "12";
	public String quarterfirstTime = "1";
	public String quarterendTime = "4";
	public int circle = 1;

	private String series;

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

	private String quater;

	//public Long organizationId;
	/**
	 * 品牌ID
	 */
	private Long brandId;

	public StockTime() throws ParseException
	{
		/**
		 *库存分析模块默认时间范围当前时间到往前推30天
		 */
		this.firstTime = DateUtil.getNewDay(-30, DateUtil.nowtimeString());
		this.endTime = DateUtil.nowtimeString();
		// this.firstTime= "2014-06-01";
		// this.endTime= "2015-01-01";
	}

	public String getSuitselectTimeType()
	{
		return suitselectTimeType;
	}

	public void setSuitselectTimeType(String suitselectTimeType)
	{
		this.suitselectTimeType = suitselectTimeType;
	}

	public String getMonthBegin()
	{
		return monthBegin;
	}

	public void setMonthBegin(String monthBegin)
	{
		this.monthBegin = monthBegin;
	}

	public String getMonthEnd()
	{
		return monthEnd;
	}

	public void setMonthEnd(String monthEnd)
	{
		this.monthEnd = monthEnd;
	}

	public String getQuarterfirstTime()
	{
		return quarterfirstTime;
	}

	public void setQuarterfirstTime(String quarterfirstTime)
	{
		this.quarterfirstTime = quarterfirstTime;
	}

	public String getQuarterendTime()
	{
		return quarterendTime;
	}

	public void setQuarterendTime(String quarterendTime)
	{
		this.quarterendTime = quarterendTime;
	}

/*	public Long getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}*/

	
	public int getYear()
	{
		return year;
	}

	public Long getBrandId()
	{
		return brandId;
	}

	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public String getFirstTime()
	{
		return firstTime;
	}

	public void setFirstTime(String firstTime)
	{
		this.firstTime = firstTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public int getCircle()
	{
		return circle;
	}

	public void setCircle(int circle)
	{
		this.circle = circle;
	}

}
