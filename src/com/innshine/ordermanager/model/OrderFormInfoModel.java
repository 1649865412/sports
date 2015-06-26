package com.innshine.ordermanager.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 订货单模板导入属性列表 <code>OrderFormInfoModel.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class OrderFormInfoModel
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
	 * 订货数量
	 */
	private Integer orderNumber;
	
	/**
	 * 订货金额
	 */
	private Double orderAmount;
	
	/**
	 * 转仓数量
	 */
	private Integer transferNumber;
	
	/**
	 * 退货数量
	 */
	private Integer returnNumber;
	/**
	 * 调货数量
	 */
	private Integer transferCargoNumber;
	
	/**
	 * 样品数量
	 */
	private Integer sampleNumber;
	
	/**
	 * 预计到货期
	 */
	private String predictArriveTime;
	
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
	
	public Double getOrderAmount()
	{
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount)
	{
		this.orderAmount = orderAmount;
	}
	
	public Integer getTransferNumber()
	{
		return transferNumber;
	}
	
	public void setTransferNumber(Integer transferNumber)
	{
		this.transferNumber = transferNumber;
	}
	
	public Integer getReturnNumber()
	{
		return returnNumber;
	}
	
	public void setReturnNumber(Integer returnNumber)
	{
		this.returnNumber = returnNumber;
	}
	
	public Integer getSampleNumber()
	{
		return sampleNumber;
	}
	
	public void setSampleNumber(Integer sampleNumber)
	{
		this.sampleNumber = sampleNumber;
	}
	
	public String getPredictArriveTime()
	{
		return predictArriveTime;
	}
	
	public void setPredictArriveTime(String predictArriveTime)
	{
		this.predictArriveTime = predictArriveTime;
	}
	
	public Integer getTransferCargoNumber()
	{
		return transferCargoNumber;
	}
	
	public void setTransferCargoNumber(Integer transferCargoNumber)
	{
		this.transferCargoNumber = transferCargoNumber;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
