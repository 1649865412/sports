package com.innshine.daily.entity;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 销售、库存查询日报查询实体bean
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class DailyEntityInfo
{
	/**
	 * 销售数量
	 */
	private Long salesNumber;
	
	/**
	 * 总销售数量
	 */
	private Long totalSalesNumber;
	
	/**
	 * 销售占比 (销售数量/销售总量)
	 */
	private Double totalSalesNumberPercentage;
	
	/**
	 * 累计销售数量
	 */
	private Long summarySalesNumber;
	
	/**
	 * 总累计销售数量
	 */
	private Long totalSummarySalesNumber;
	
	/**
	 * 累计销售数量占比 ( 累计销售数量/累计销售数量总量)
	 */
	private Double totalSummarySalesNumberPercentage;
	
	/**
	 * 付款金额
	 */
	private BigDecimal amountToPay;
	
	/**
	 * 总付款金额
	 */
	private BigDecimal totalAmountToPay;
	
	/**
	 * 付款金额占比 （付款金额/ 付款金额总额）
	 */
	private Double totalAmountToPayPercentage;
	
	/**
	 * 累计全年付款金额
	 */
	private BigDecimal yearAmountToPay;
	
	/**
	 * 累计全年总付款金额
	 */
	private BigDecimal totalYearAmountToPay;
	
	/**
	 * 累计全年付款金额占比 （付款金额/ 付款金额总额）
	 */
	private Double totalYearAmountToPayPercentage;
	
	/**
	 * 吊牌金额
	 */
	private BigDecimal tagPrice;
	
	/**
	 * 总吊牌金额
	 */
	private BigDecimal totalTagPrice;
	
	/**
	 * 吊牌金额占比(吊牌金额 / 吊牌金额)
	 */
	private Double totalTagPricePercentage;
	
	/**
	 * 平均折扣(付款金额/吊牌金额)
	 */
	private Double avgDiscount;
	
	/**
	 * 销售占比
	 */
	private Double salesPercentage;
	
	/**
	 * 售罄率(销售数量/到货数量)
	 */
	private Double soldRate;
	
	/**
	 * 销售占比()
	 */
	private Double salesRete;
	
	/**
	 * 系列
	 */
	private String series;
	
	/**
	 * 季节
	 */
	private String quater;
	
	/**
	 * 种类
	 */
	private String productType;
	
	/**
	 * 
	 * inline/line
	 */
	private String inlineOr2ndline;
	
	/**
	 * 性别
	 */
	private String productSex;
	
	/**
	 * 库存数量
	 */
	private Long stockNumber;
	
	/**
	 * 总库存数量
	 */
	private Long totalStockNumber;
	
	/**
	 * 库存占比( 库存数量/总库存数量)
	 */
	private Double stockNumberPercentage;
	
	/**
	 * 到货率（到货量/订货数量；）
	 */
	private Double arrivalRate;
	
	public Long getSalesNumber()
	{
		return salesNumber;
	}
	
	public void setSalesNumber(Long salesNumber)
	{
		this.salesNumber = salesNumber;
	}
	
	public Long getTotalSalesNumber()
	{
		return totalSalesNumber;
	}
	
	public void setTotalSalesNumber(Long totalSalesNumber)
	{
		this.totalSalesNumber = totalSalesNumber;
	}
	
	public Double getTotalSalesNumberPercentage()
	{
		return getDivisor(salesNumber, totalSalesNumber, totalSalesNumberPercentage);
	}
	
	public void setTotalSalesNumberPercentage(Double totalSalesNumberPercentage)
	{
		this.totalSalesNumberPercentage = totalSalesNumberPercentage;
	}
	
	public Long getSummarySalesNumber()
	{
		return summarySalesNumber;
	}
	
	public void setSummarySalesNumber(Long summarySalesNumber)
	{
		this.summarySalesNumber = summarySalesNumber;
	}
	
	public Long getTotalSummarySalesNumber()
	{
		return totalSummarySalesNumber;
	}
	
	public void setTotalSummarySalesNumber(Long totalSummarySalesNumber)
	{
		this.totalSummarySalesNumber = totalSummarySalesNumber;
	}
	
	public Double getTotalSummarySalesNumberPercentage()
	{
		return getDivisor(summarySalesNumber, totalSummarySalesNumber, totalSummarySalesNumberPercentage);
	}
	
	public void setTotalSummarySalesNumberPercentage(Double totalSummarySalesNumberPercentage)
	{
		this.totalSummarySalesNumberPercentage = totalSummarySalesNumberPercentage;
	}
	
	public BigDecimal getAmountToPay()
	{
		return amountToPay;
	}
	
	public void setAmountToPay(BigDecimal amountToPay)
	{
		this.amountToPay = amountToPay;
	}
	
	public BigDecimal getTotalAmountToPay()
	{
		return null != totalAmountToPay ? totalAmountToPay.setScale(2, BigDecimal.ROUND_HALF_UP) : totalAmountToPay;
	}
	
	public void setTotalAmountToPay(BigDecimal totalAmountToPay)
	{
		this.totalAmountToPay = totalAmountToPay;
	}
	
	public Double getTotalAmountToPayPercentage()
	{
		if (null != amountToPay && null != totalAmountToPay)
		{
			return getDivisor(amountToPay, totalAmountToPay, totalAmountToPayPercentage);
		}
		return totalAmountToPayPercentage;
	}
	
	public void setTotalAmountToPayPercentage(Double totalAmountToPayPercentage)
	{
		this.totalAmountToPayPercentage = totalAmountToPayPercentage;
	}
	
	public BigDecimal getTagPrice()
	{
		return tagPrice;
	}
	
	public void setTagPrice(BigDecimal tagPrice)
	{
		this.tagPrice = tagPrice;
	}
	
	public BigDecimal getTotalTagPrice()
	{
		return null != totalTagPrice ? totalTagPrice.setScale(2, BigDecimal.ROUND_HALF_UP) : totalTagPrice;
	}
	
	public void setTotalTagPrice(BigDecimal totalTagPrice)
	{
		this.totalTagPrice = totalTagPrice;
	}
	
	public Double getAvgDiscount()
	{
		return avgDiscount;
	}
	
	public void setAvgDiscount(Double avgDiscount)
	{
		this.avgDiscount = avgDiscount;
	}
	
	public Double getSalesPercentage()
	{
		return salesPercentage;
	}
	
	public void setSalesPercentage(Double salesPercentage)
	{
		this.salesPercentage = salesPercentage;
	}
	
	public Double getSoldRate()
	{
		return soldRate;
	}
	
	public void setSoldRate(Double soldRate)
	{
		this.soldRate = soldRate;
	}
	
	public Double getSalesRete()
	{
		return salesRete;
	}
	
	public void setSalesRete(Double salesRete)
	{
		this.salesRete = salesRete;
	}
	
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
	
	public String getProductType()
	{
		return productType;
	}
	
	public void setProductType(String productType)
	{
		this.productType = productType;
	}
	
	public String getInlineOr2ndline()
	{
		return inlineOr2ndline;
	}
	
	public void setInlineOr2ndline(String inlineOr2ndline)
	{
		this.inlineOr2ndline = inlineOr2ndline;
	}
	
	public Long getStockNumber()
	{
		return stockNumber;
	}
	
	public void setStockNumber(Long stockNumber)
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
		return getDivisor(stockNumber, totalStockNumber, stockNumberPercentage);
	}
	
	public void setStockNumberPercentage(Double stockNumberPercentage)
	{
		this.stockNumberPercentage = stockNumberPercentage;
	}
	
	public Double getTotalTagPricePercentage()
	{
		if (null != tagPrice && null != totalTagPrice)
		{
			return getDivisor(tagPrice, totalTagPrice, totalTagPricePercentage);
		}
		
		return totalTagPricePercentage;
	}
	
	public void setTotalTagPricePercentage(Double totalTagPricePercentage)
	{
		this.totalTagPricePercentage = totalTagPricePercentage;
	}
	
	public BigDecimal getYearAmountToPay()
	{
		return yearAmountToPay;
	}
	
	public void setYearAmountToPay(BigDecimal yearAmountToPay)
	{
		this.yearAmountToPay = yearAmountToPay;
	}
	
	public BigDecimal getTotalYearAmountToPay()
	{
		return null != totalYearAmountToPay ? totalYearAmountToPay.setScale(2, BigDecimal.ROUND_HALF_UP)
				: totalYearAmountToPay;
	}
	
	public void setTotalYearAmountToPay(BigDecimal totalYearAmountToPay)
	{
		this.totalYearAmountToPay = totalYearAmountToPay;
	}
	
	public Double getTotalYearAmountToPayPercentage()
	{
		return getDivisor(yearAmountToPay, totalYearAmountToPay, totalYearAmountToPayPercentage);
	}
	
	public void setTotalYearAmountToPayPercentage(Double totalYearAmountToPayPercentage)
	{
		this.totalYearAmountToPayPercentage = totalYearAmountToPayPercentage;
	}
	
	public String getProductSex()
	{
		return productSex;
	}
	
	public void setProductSex(String productSex)
	{
		this.productSex = productSex;
	}
	
	public Double getArrivalRate()
	{
		return arrivalRate;
	}
	
	public void setArrivalRate(Double arrivalRate)
	{
		this.arrivalRate = arrivalRate;
	}
	
	/**
	 * 求两数之商，并保存两位小数返回
	 * 
	 * @param div1
	 *        除数
	 * @param div2
	 *        被除数
	 * @param defaultValue
	 *        如果两者都为null，则返回该值
	 * @return 两数之商，并保存两位小数返回
	 */
	private Double getDivisor(Double div1, Double div2, Double defaultValue)
	{
		if (null != div1 && null != div2)
		{
			
			BigDecimal bigDecimal = new BigDecimal((div1 / div2) * 100);
			
			return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		return defaultValue;
	}
	
	/**
	 * 求两数之商，并保存两位小数返回
	 * 
	 * @param div1
	 *        除数
	 * @param div2
	 *        被除数
	 * @param defaultValue
	 *        如果两者都为null，则返回该值
	 * @return 两数之商，并保存两位小数返回
	 */
	private Double getDivisor(Long div1, Long div2, Double defaultValue)
	{
		if (null != div1 && null != div2)
		{
			Double tmpDiv1 = div1.doubleValue();
			Double tmpDiv2 = div2.doubleValue();
			
			return getDivisor(tmpDiv1, tmpDiv2, defaultValue);
		}
		
		return defaultValue;
	}
	
	/**
	 * 求两数之商，并保存两位小数返回
	 * 
	 * @param div1
	 *        除数
	 * @param div2
	 *        被除数
	 * @param defaultValue
	 *        如果两者都为null，则返回该值
	 * @return 两数之商，并保存两位小数返回
	 */
	private Double getDivisor(BigDecimal div1, BigDecimal div2, Double defaultValue)
	{
		if (null != div1 && null != div2)
		{
			
			Double tmpDiv1 = div1.doubleValue();
			Double tmpDiv2 = div2.doubleValue();
			
			return getDivisor(tmpDiv1, tmpDiv2, defaultValue);
		}
		
		return defaultValue;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
