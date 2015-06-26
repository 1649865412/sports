package com.innshine.stockmanager.stockanalysis.entity;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * 库存综合分析导出实体bean
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class StockAnalysisInfoModel
{
	/**
	 * 种类
	 */
	private String productType;
	/**
	 * Q季
	 */
	private String quater;
	
	/**
	 * 系列
	 */
	private String series;
	
	/**
	 * 一线还是二线
	 */
	private String inlineOr2ndline;
	
	/**
	 * 性别
	 */
	private String productSex;
	
	/**
	 * 库存量
	 */
	private Integer stockNumber;
	
	/**
	 * 总库存量
	 */
	private Long totalStockNumber;
	
	/**
	 * 百分比 (库存量 / 总库存量)
	 */
	private Double stockNumberPercentage;
	
	/**
	 * 吊牌金额
	 */
	private BigDecimal tagPrice;
	
	/**
	 * 吊牌总金额
	 */
	private BigDecimal totalTagPrice;
	
	/**
	 * 百分比 (吊牌金额/ 吊牌总金额)
	 */
	private Double tagPricePercentage;
	
	/**
	 * 未来库存量
	 */
	private Long futureStockNumber;
	
	/**
	 * 未来总库存量
	 */
	private Long totalFutureStockNumber;
	
	/**
	 * 百分比 (未来库存量/ 未来总库存量)
	 */
	private Double futureStockNumberPercentage;
	
	/**
	 * 无意义字段，主要为了方便添加总计是使用，默认值为100
	 */
	private String percentage = "100";
	
	public String getProductType()
	{
		return productType;
	}
	
	public void setProductType(String productType)
	{
		this.productType = productType;
	}
	
	public String getQuater()
	{
		return quater;
	}
	
	public void setQuater(String quater)
	{
		this.quater = quater;
	}
	
	public String getSeries()
	{
		return series;
	}
	
	public void setSeries(String series)
	{
		this.series = series;
	}
	
	public String getInlineOr2ndline()
	{
		return inlineOr2ndline;
	}
	
	public void setInlineOr2ndline(String inlineOr2ndline)
	{
		this.inlineOr2ndline = inlineOr2ndline;
	}
	
	public String getProductSex()
	{
		return productSex;
	}
	
	public void setProductSex(String productSex)
	{
		this.productSex = productSex;
	}
	
	public Integer getStockNumber()
	{
		return stockNumber;
	}
	
	public void setStockNumber(Integer stockNumber)
	{
		this.stockNumber = stockNumber;
	}
	
	public Long getTotalStockNumber()
	{
		return totalStockNumber;
	}
	
	public void setTotalStockNumber(Long totalStockNumber)
	{
		this.totalStockNumber = totalStockNumber;
	}
	
	public Double getStockNumberPercentage()
	{
		if (null != stockNumber && null != totalStockNumber)
		{
			BigDecimal bigDecimal = new BigDecimal(stockNumber.doubleValue() / totalStockNumber.doubleValue() * 100);
			stockNumberPercentage = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		return stockNumberPercentage;
	}
	
	public void setStockNumberPercentage(Double stockNumberPercentage)
	{
		this.stockNumberPercentage = stockNumberPercentage;
	}
	
	public BigDecimal getTagPrice()
	{
		return null != tagPrice ? tagPrice.setScale(2, BigDecimal.ROUND_HALF_UP) : tagPrice;
	}
	
	public void setTagPrice(BigDecimal tagPrice)
	{
		this.tagPrice = tagPrice;
	}
	
	public BigDecimal getTotalTagPrice()
	{
		return totalTagPrice;
	}
	
	public void setTotalTagPrice(BigDecimal totalTagPrice)
	{
		this.totalTagPrice = totalTagPrice;
	}
	
	public Double getTagPricePercentage()
	{
		if (null != tagPrice && null != totalTagPrice)
		{
			
			BigDecimal bigDecimal = new BigDecimal(tagPrice.doubleValue() / totalTagPrice.doubleValue() * 100);
			tagPricePercentage = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return tagPricePercentage;
	}
	
	public void setTagPricePercentage(Double tagPricePercentage)
	{
		this.tagPricePercentage = tagPricePercentage;
	}
	
	public Long getFutureStockNumber()
	{
		return futureStockNumber;
	}
	
	public void setFutureStockNumber(Long futureStockNumber)
	{
		this.futureStockNumber = futureStockNumber;
	}
	
	public Long getTotalFutureStockNumber()
	{
		return totalFutureStockNumber;
	}
	
	public void setTotalFutureStockNumber(Long totalFutureStockNumber)
	{
		this.totalFutureStockNumber = totalFutureStockNumber;
	}
	
	public Double getFutureStockNumberPercentage()
	{
		if (null != futureStockNumber && null != totalFutureStockNumber)
		{
			BigDecimal bigDecimal = new BigDecimal(futureStockNumber.doubleValue()
					/ totalFutureStockNumber.doubleValue() * 100);
			futureStockNumberPercentage = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		return futureStockNumberPercentage;
	}
	
	public void setFutureStockNumberPercentage(Double futureStockNumberPercentage)
	{
		this.futureStockNumberPercentage = futureStockNumberPercentage;
	}
	
	public String getPercentage()
	{
		return percentage;
	}
	
	public void setPercentage(String percentage)
	{
		this.percentage = percentage;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
