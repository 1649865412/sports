package com.innshine.salesstockdetails.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 从OCS数据库中销售订单清洗成销售信息实体类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class OrderItemsInfo
{
	/**
	 * 订单编号
	 */
	private Long orderId;
	
	/**
	 * 款号
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
	 * 销售价格
	 */
	private Double salePrice;
	
	/**
	 * 产品名称
	 */
	private String name;
	
	/**
	 * 销售数量
	 */
	private Integer nums;
	
	/**
	 * 订单最后更新时间
	 */
	private String lastModified;
	
	/**
	 * 订购金额
	 */
	private Double amount;
	
	/**
	 * 品牌ID
	 */
	private Long brandId;
	
	public Long getOrderId()
	{
		return orderId;
	}
	
	public void setOrderId(Long orderId)
	{
		this.orderId = orderId;
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
	
	public Double getSalePrice()
	{
		return salePrice;
	}
	
	public void setSalePrice(Double salePrice)
	{
		this.salePrice = salePrice;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Integer getNums()
	{
		return nums;
	}
	
	public void setNums(Integer nums)
	{
		this.nums = nums;
	}
	
	public String getLastModified()
	{
		return lastModified;
	}
	
	public void setLastModified(String lastModified)
	{
		this.lastModified = lastModified;
	}
	
	public Double getAmount()
	{
		return amount;
	}
	
	public void setAmount(Double amount)
	{
		this.amount = amount;
	}
	
	public String getMaterialNumber()
	{
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	public Long getBrandId()
	{
		return brandId;
	}
	
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
