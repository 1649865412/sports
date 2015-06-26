package com.innshine.ordermanager.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 从OCS数据库中获取入库数量表
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class OcsStockItemsInfo
{
	
	/**
	 * 款号（货号）
	 */
	private String materialNumber;
	
	/**
	 * 产品ID
	 */
	private String productId;
	
	/**
	 * 条形码
	 */
	private String barcode;
	
	/**
	 * 入库价格
	 */
	private Double iostockPrice;
	
	private Long brandId;
	
	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	/**
	 * 产品名称
	 */
	private String name;
	
	/**
	 * 入库数量 = 已入库数量+不良品数量(即到货数量)
	 */
	private Integer stockNums;
	
	/**
	 * 出入库时间(即到货时间)
	 */
	private String stockTime;
	
	/**
	 * 出入库id
	 */
	private Long iostockId;
	
	public String getMaterialNumber()
	{
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	public String getProductId()
	{
		return productId;
	}
	
	public void setProductId(String productId)
	{
		this.productId = productId;
	}
	
	public String getBarcode()
	{
		return barcode;
	}
	
	public void setBarcode(String barcode)
	{
		this.barcode = barcode;
	}
	
	public Double getIostockPrice()
	{
		return iostockPrice;
	}
	
	public void setIostockPrice(Double iostockPrice)
	{
		this.iostockPrice = iostockPrice;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Integer getStockNums()
	{
		return stockNums;
	}
	
	public void setStockNums(Integer stockNums)
	{
		this.stockNums = stockNums;
	}
	
	public String getStockTime()
	{
		return stockTime;
	}
	
	public void setStockTime(String stockTime)
	{
		this.stockTime = stockTime;
	}
	
	public Long getIostockId()
	{
		return iostockId;
	}
	
	public void setIostockId(Long iostockId)
	{
		this.iostockId = iostockId;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
