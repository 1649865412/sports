package com.base.entity.component;

import java.io.Serializable;

public class SecurtySaleNum implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String securitySaleNums;
	private String brandId;
	
	public String getSecuritySaleNums() {
		return securitySaleNums;
	}
	public void setSecuritySaleNums(String securitySaleNums) {
		this.securitySaleNums = securitySaleNums;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	
}
