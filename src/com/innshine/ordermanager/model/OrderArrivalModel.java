package com.innshine.ordermanager.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * 到货单导入模版
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
/**
 *  <code>OrderArrivalModel.java</code>
 *  <p>
 *  <p>Copyright  2014 All right reserved.
 *  @author 杨荣忠 时间 2014-10-28 下午03:10:42	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
public class OrderArrivalModel
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
	 * 出库单号
	 */
	
	private String storehouseId;
	
	/**
	 * 原出库单号
	 */
	private String oldStorehouseId;
	
	/**
	 * 颜色
	 */
	
	private String color;
	
	/**
	 * 色号
	 */
	private String colourNumber;
	
	/**
	 * 尺码
	 */
	private String productSize;
	
	/**
	 * 到货数量
	 */
	private Integer arrivalNumber;
	
	/**
	 * 大款
	 */
	private String productLarge;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 波次号
	 */
	private String waveNumber;
	
	/**
	 * 备注
	 */
	private String remarks;
	
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

	public String getStorehouseId()
	{
		return storehouseId;
	}
	
	public void setStorehouseId(String storehouseId)
	{
		this.storehouseId = storehouseId;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public String getColourNumber()
	{
		return colourNumber;
	}
	
	public void setColourNumber(String colourNumber)
	{
		this.colourNumber = colourNumber;
	}
	
	public String getProductSize()
	{
		return productSize;
	}
	
	public void setProductSize(String productSize)
	{
		this.productSize = productSize;
	}
	
	public Integer getArrivalNumber()
	{
		return arrivalNumber;
	}
	
	public void setArrivalNumber(Integer arrivalNumber)
	{
		this.arrivalNumber = arrivalNumber;
	}
	
	public String getProductLarge()
	{
		return productLarge;
	}
	
	public void setProductLarge(String productLarge)
	{
		this.productLarge = productLarge;
	}
	
	public String getShopName()
	{
		return shopName;
	}
	
	public void setShopName(String shopName)
	{
		this.shopName = shopName;
	}
	
	public String getCustomerName()
	{
		return customerName;
	}
	
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	
	public String getWaveNumber()
	{
		return waveNumber;
	}
	
	public void setWaveNumber(String waveNumber)
	{
		this.waveNumber = waveNumber;
	}
	
	public String getOldStorehouseId()
	{
		return oldStorehouseId;
	}
	
	public void setOldStorehouseId(String oldStorehouseId)
	{
		this.oldStorehouseId = oldStorehouseId;
	}
	
	public String getRemarks()
	{
		return remarks;
	}
	
	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
