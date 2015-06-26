package com.base.entity.component;

import java.io.Serializable;

public class TurnoverNum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String brandId;
	private String turnoverNum;
	
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getTurnoverNum() {
		return turnoverNum;
	}
	public void setTurnoverNum(String turnoverNum) {
		this.turnoverNum = turnoverNum;
	}
}
