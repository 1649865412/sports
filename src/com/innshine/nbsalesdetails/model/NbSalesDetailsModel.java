/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.nbsalesdetails.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NbSalesDetailsModel
{
	/**
	 * 69码
	 */
	private String upccode;
	
	/**
	 * 款号（物料编码）
	 */
	private String materialNumber;
	
	/**
	 * 平台ID
	 */
	private String platformId;
	
	/**
	 * 销售起止时间
	 */
	private String marketStartTime;
	
	/**
	 * 销售结束时间
	 */
	private String marketEndTime;
	
	/**
	 * 销售数量
	 */
	private Double salesNumber;
	
	/**
	 * 成交价（均价）
	 */
	private Double avgCurrentPrice;
	
	/**
	 * 销售金额
	 */
	private BigDecimal salesAmount;
	
	/**
	 * 仓库
	 */
	private String storeHouse;
	
	/**
	 * 入库时间
	 */
	private String enteringTime;
	
	/**
	 * 订货量
	 */
	private Double orderNumber;
	
	/**
	 * 库存量
	 */
	private Double stockNumber;
	
	/**
	 * 到货量
	 */
	private Double arriveNumber;
	
	/**
	 * 预计到货期
	 */
	private String predictArriveTime;
	
	/**
	 * 实际到货时间
	 */
	private String factArriveTime;
	
	public String getUpccode()
	{
		return upccode;
	}
	
	public void setUpccode(String upccode)
	{
		this.upccode = upccode;
	}
	
	public String getMaterialNumber()
	{
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	public String getPlatformId()
	{
		return platformId;
	}
	
	public void setPlatformId(String platformId)
	{
		this.platformId = platformId;
	}
	
	public String getMarketStartTime()
	{
		return marketStartTime;
	}
	
	public void setMarketStartTime(String marketStartTime)
	{
		this.marketStartTime = marketStartTime;
	}
	
	public String getMarketEndTime()
	{
		return marketEndTime;
	}
	
	public void setMarketEndTime(String marketEndTime)
	{
		this.marketEndTime = marketEndTime;
	}
	
	public Double getSalesNumber()
	{
		return this.salesNumber;
	}
	
	public void setSalesNumber(Double salesNumber)
	{
		this.salesNumber = salesNumber;
	}
	
	public Double getAvgCurrentPrice()
	{
		return avgCurrentPrice;
	}
	
	public void setAvgCurrentPrice(Double avgCurrentPrice)
	{
		this.avgCurrentPrice = avgCurrentPrice;
	}
	
	public BigDecimal getSalesAmount()
	{
		return null != salesAmount ? salesAmount.setScale(2, BigDecimal.ROUND_HALF_UP) : salesAmount;
	}
	
	public void setSalesAmount(BigDecimal salesAmount)
	{
		this.salesAmount = salesAmount;
	}
	
	public String getStoreHouse()
	{
		return storeHouse;
	}
	
	public void setStoreHouse(String storeHouse)
	{
		this.storeHouse = storeHouse;
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
	
	public Double getStockNumber()
	{
		return stockNumber;
	}
	
	public void setStockNumber(Double stockNumber)
	{
		this.stockNumber = stockNumber;
	}
	
	public Double getArriveNumber()
	{
		return arriveNumber;
	}
	
	public void setArriveNumber(Double arriveNumber)
	{
		this.arriveNumber = arriveNumber;
	}
	
	public String getPredictArriveTime()
	{
		return predictArriveTime;
	}
	
	public void setPredictArriveTime(String predictArriveTime)
	{
		this.predictArriveTime = predictArriveTime;
	}
	
	public String getFactArriveTime()
	{
		return factArriveTime;
	}
	
	public void setFactArriveTime(String factArriveTime)
	{
		this.factArriveTime = factArriveTime;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
