package com.innshine.ordermanager.entity;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 前端页面展示实体bean
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class OrderPageInfo
{
	/**
	 * 订货表主键
	 */
	private Long id;
	
	/**
	 * 69码
	 */
	private String upccode;
	
	/**
	 * 款号（物料编码）
	 */
	private String materialNumber;
	
	/**
	 * 平台产品ID
	 */
	private String platformId;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * Q季(季节)
	 */
	private String quater;
	
	/**
	 * 类别
	 */
	private String productType;
	
	/**
	 * 吊牌价
	 */
	private Double tagPrice;
	
	/**
	 * 订货数量
	 */
	private Integer orderNumber;
	
	/**
	 * 订货金额
	 */
	private BigDecimal orderAmount;
	
	/**
	 * 到货数量
	 */
	private Integer arrivalNumber;
	
	/**
	 * 现有库存数量
	 */
	private Integer currentStockNumber;
	
	/**
	 * 可用库存数据
	 */
	private Integer availableStockNumber;
	
	/**
	 * 可用库存金额
	 */
	private BigDecimal availableStockMoney;
	
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
	
	public Integer getOrderNumber()
	{
		return orderNumber;
	}
	
	public void setOrderNumber(Integer orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	
	public BigDecimal getOrderAmount()
	{
		return null != orderAmount ? orderAmount.setScale(2, BigDecimal.ROUND_HALF_UP) : orderAmount;
	}
	
	public void setOrderAmount(BigDecimal orderAmount)
	{
		this.orderAmount = orderAmount;
	}
	
	public Integer getCurrentStockNumber()
	{
		return currentStockNumber;
	}
	
	public void setCurrentStockNumber(Integer currentStockNumber)
	{
		this.currentStockNumber = currentStockNumber;
	}
	
	public Integer getAvailableStockNumber()
	{
		return availableStockNumber;
	}
	
	public void setAvailableStockNumber(Integer availableStockNumber)
	{
		this.availableStockNumber = availableStockNumber;
	}
	
	public String getProductName()
	{
		return productName;
	}
	
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	
	public Double getTagPrice()
	{
		return tagPrice;
	}
	
	public void setTagPrice(Double tagPrice)
	{
		this.tagPrice = tagPrice;
	}
	
	public BigDecimal getAvailableStockMoney()
	{
		return null != availableStockMoney ? availableStockMoney.setScale(2, BigDecimal.ROUND_HALF_UP)
				: availableStockMoney;
	}
	
	public void setAvailableStockMoney(BigDecimal availableStockMoney)
	{
		this.availableStockMoney = availableStockMoney;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Integer getArrivalNumber()
	{
		return arrivalNumber;
	}
	
	public void setArrivalNumber(Integer arrivalNumber)
	{
		this.arrivalNumber = arrivalNumber;
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
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
