package com.base.entity.component;

import java.io.Serializable;

public class DayOrderArriver implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderNum;
	private String orderPrice;
	private String arriverNum;
	private String arriverPrice;
	private String brandId;
	
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getArriverNum() {
		return arriverNum;
	}
	public void setArriverNum(String arriverNum) {
		this.arriverNum = arriverNum;
	}
	public String getArriverPrice() {
		return arriverPrice;
	}
	public void setArriverPrice(String arriverPrice) {
		this.arriverPrice = arriverPrice;
	}
	public String getBrandId()
	{
		return brandId;
	}
	public void setBrandId(String brandId)
	{
		this.brandId = brandId;
	}
	
}
