package com.innshine.shopAnalyse.entity;

import java.text.ParseException;

import org.springframework.web.bind.annotation.RequestParam;

import com.innshine.shopAnalyse.util.DateUtil;

public class ShopAnalyseTimeCheckEntity
{

	/**
	 * 功能Bean 销售分析模块(时间维度分析)(R日M月Z季Y年) selectTimeType 查询类型 year 年 firstTime
	 * 日开始时间 MonthBegin 月开始时间 QuarterfirstTime 季开始时间 circle 周期 platid 平台id Bean
	 */
	public String selectTimeType = "R";
	public int year = 2014;
	public String firstTime = "";
	public String endTime = "";
	public String monthBegin = "1";
	public String monthEnd = "12";
	public String quarterfirstTime = "1";
	public String quarterendTime = "4";
	public int circle = 1;
	public String category = "";
	public String series = "";
	//public Long organizationId;
	/**
	 * 品牌ID
	 */
	private Long brandId;
	
	public String quater;

	// 用于时间纵向分析分类属性
	public String shopOptionsTime = "0";
	public String shopOptionsNameTime = "0";
	public String orderListTime = "0";

	public ShopAnalyseTimeCheckEntity() throws ParseException
	{
		/**
		 *销售分析模块默认时间范围当前时间到往前推30天
		 */
		this.firstTime = DateUtil.getNewDay(-30, DateUtil.nowtimeString());
		this.endTime = DateUtil.nowtimeString();
	}

	public ShopAnalyseTimeCheckEntity(String selectTimeType, int year,
			String firstTime, String endTime, String monthBegin,
			String monthEnd, String quarterfirstTime, String quarterendTime,
			int circle, String category, String series, 
			//Long organizationId,
			String quater, String shopOptions, String shopOptionsName,
			String orderList)
	{
		super();
		this.selectTimeType = selectTimeType;
		this.year = year;
		this.firstTime = firstTime;
		this.endTime = endTime;
		this.monthBegin = monthBegin;
		this.monthEnd = monthEnd;
		this.quarterfirstTime = quarterfirstTime;
		this.quarterendTime = quarterendTime;
		this.circle = circle;
		this.category = category;
		this.series = series;
		//this.organizationId = organizationId;
		this.quater = quater;
		this.shopOptionsTime = shopOptions;
		this.shopOptionsNameTime = shopOptionsName;
		this.orderListTime = orderList;
	}

	public String getShopOptionsTime()
	{
		return shopOptionsTime;
	}

	public void setShopOptionsTime(String shopOptionsTime)
	{
		this.shopOptionsTime = shopOptionsTime;
	}

	public String getShopOptionsNameTime()
	{
		return shopOptionsNameTime;
	}

	public void setShopOptionsNameTime(String shopOptionsNameTime)
	{
		this.shopOptionsNameTime = shopOptionsNameTime;
	}

	public String getOrderListTime()
	{
		return orderListTime;
	}

	public void setOrderListTime(String orderListTime)
	{
		this.orderListTime = orderListTime;
	}

	public String getQuater()
	{
		return quater;
	}

	public void setQuater(String quater)
	{
		this.quater = quater;
	}

	public Long getBrandId()
	{
		return brandId;
	}

	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}

	/*	public Long getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}
*/
	public String getSelectTimeType()
	{
		return selectTimeType;
	}

	public void setSelectTimeType(String selectTimeType)
	{
		this.selectTimeType = selectTimeType;
	}

	public int getYear()
	{
		return year;
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

	public int getCircle()
	{
		return circle;
	}

	public void setCircle(int circle)
	{
		this.circle = circle;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

}
