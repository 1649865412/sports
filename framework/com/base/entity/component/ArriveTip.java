package com.base.entity.component;

import java.io.Serializable;

public class ArriveTip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String arriveNum;
	private String brandId;
	
	public String getArriveNum() {
		return arriveNum;
	}
	public void setArriveNum(String arriveNum) {
		this.arriveNum = arriveNum;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
}
