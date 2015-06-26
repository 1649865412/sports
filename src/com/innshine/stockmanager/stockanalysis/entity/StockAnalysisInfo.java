package com.innshine.stockmanager.stockanalysis.entity;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 库存分析，页面展示字段实体bean
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class StockAnalysisInfo
{
	/**
	 * 69码
	 */
	private String productUpccode;
	
	/**
	 * 款号（物料编码）
	 */
	private String materialNumber;
	
	/**
	 * Inline/2ndline
	 */
	private String inlineOr2ndline;
	
	/**
	 * LF/PF(系列)
	 */
	private String productLfPf;
	
	/**
	 * 品牌
	 */
	private String productBrand;
	
	/**
	 * 系统编号（款号+宽度）
	 */
	private String systemId;
	
	/**
	 * Q季(季节)
	 */
	private String quater;
	
	/**
	 * 入库时间
	 */
	private String enteringTime;
	
	/**
	 * 订货量
	 */
	private Double orderNumber;
	
	/**
	 * 最近一次到货数量
	 */
	private Double lastArriveNumber;
	
	/**
	 * 最近一次订货数量
	 */
	private Double lastOrderNumber;
	
	/**
	 * 最近一次库存量
	 */
	private Double lastStockNumber;
	
	/**
	 * 总销量
	 */
	private Integer totalSalesNumber;
	
	/**
	 * 到货率
	 */
	private Double arrivalRate;
	
	/**
	 * 动销率
	 */
	private Double dynamicSalesRate;
	
	/**
	 * 售罄率
	 */
	private Double soldRate;
	
	public String getProductBrand()
	{
		return productBrand;
	}
	
	public void setProductBrand(String productBrand)
	{
		this.productBrand = productBrand;
	}
	
	public String getProductUpccode()
	{
		return productUpccode;
	}
	
	public void setProductUpccode(String productUpccode)
	{
		this.productUpccode = productUpccode;
	}
	
	public String getMaterialNumber()
	{
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	public String getInlineOr2ndline()
	{
		return inlineOr2ndline;
	}
	
	public void setInlineOr2ndline(String inlineOr2ndline)
	{
		this.inlineOr2ndline = inlineOr2ndline;
	}
	
	public String getProductLfPf()
	{
		return productLfPf;
	}
	
	public void setProductLfPf(String productLfPf)
	{
		this.productLfPf = productLfPf;
	}
	
	public String getSystemId()
	{
		return systemId;
	}
	
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}
	
	public String getQuater()
	{
		return quater;
	}
	
	public void setQuater(String quater)
	{
		this.quater = quater;
	}
	
	public String getEnteringTime()
	{
		return enteringTime;
	}
	
	public void setEnteringTime(String enteringTime)
	{
		this.enteringTime = enteringTime;
	}
	
	public Double getOrderNumber()
	{
		return orderNumber;
	}
	
	public void setOrderNumber(Double orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	
	public Double getLastArriveNumber()
	{
		return lastArriveNumber;
	}
	
	public void setLastArriveNumber(Double lastArriveNumber)
	{
		this.lastArriveNumber = lastArriveNumber;
	}
	
	public Double getLastOrderNumber()
	{
		return lastOrderNumber;
	}
	
	public void setLastOrderNumber(Double lastOrderNumber)
	{
		this.lastOrderNumber = lastOrderNumber;
	}
	
	public Double getLastStockNumber()
	{
		return lastStockNumber;
	}
	
	public void setLastStockNumber(Double lastStockNumber)
	{
		this.lastStockNumber = lastStockNumber;
	}
	
	public Integer getTotalSalesNumber()
	{
		return totalSalesNumber;
	}
	
	public void setTotalSalesNumber(Integer totalSalesNumber)
	{
		this.totalSalesNumber = totalSalesNumber;
	}
	
	public Double getArrivalRate()
	{
		return null != getDoubleValuePercentile(lastArriveNumber, lastOrderNumber) ? getDoubleValuePercentile(
				lastArriveNumber, lastOrderNumber) : arrivalRate;
	}
	
	public void setArrivalRate(Double arrivalRate)
	{
		this.arrivalRate = arrivalRate;
	}
	
	public Double getDynamicSalesRate()
	{
		Double calDynamicSalesRate = getDoubleValuePercentile(null != totalSalesNumber ? totalSalesNumber.doubleValue()
				: null, lastStockNumber);
		
		if (calDynamicSalesRate == null)
		{
			return dynamicSalesRate;
		}
		
		return calDynamicSalesRate;
	}
	
	public void setDynamicSalesRate(Double dynamicSalesRate)
	{
		this.dynamicSalesRate = dynamicSalesRate;
	}
	
	public Double getSoldRate()
	{
		Double calSoldRate = getDoubleValuePercentile(null != totalSalesNumber ? totalSalesNumber.doubleValue() : null,
				lastArriveNumber);
		
		if (null == calSoldRate)
		{
			return soldRate;
		}
		
		return calSoldRate;
	}
	
	public void setSoldRate(Double soldRate)
	{
		this.soldRate = soldRate;
	}
	
	/**
	 * 计算 <Br>
	 * 售罄率 ：销售中，销售数量/到货数量； <Br>
	 * 到货率 ：到货量/订货数量； <Br>
	 * 动销率 ：销量/库存
	 * 
	 * @param divisor
	 *        除数
	 * @param dividend
	 *        被除数
	 * @return
	 */
	private Double getDoubleValuePercentile(Double divisor, Double dividend)
	{
		if (null != divisor && null != dividend)
		{
			BigDecimal bigDecimal = new BigDecimal(divisor / dividend * 100);
			
			// 默认向上会舍取两位小数
			return bigDecimal.setScale(2, BigDecimal.ROUND_CEILING).doubleValue();
		}
		
		return null;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
