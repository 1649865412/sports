package com.base.entity.component;

import java.io.Serializable;

public class DaySellAndStock implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String saleNum;
	private String saleAmount;
	private String stockNumber;
	private String stockAmount;
	private String brandId;
	
	public String getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(String saleNum) {
		this.saleNum = saleNum;
	}
	public String getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmout(String saleAmount) {
		this.saleAmount = saleAmount;
	}
	public String getStockNumber() {
		return stockNumber;
	}
	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}
	public String getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(String stockAmount) {
		this.stockAmount = stockAmount;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
}
