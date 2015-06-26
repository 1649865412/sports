package com.innshine.shopAnalyse.excelEntity;

public class ShopDayEntity {
	
	
	/**
	 * 销售分析日报实体bean
	 */
	  private String salesTime;
	  private String upccode;
	  private String materialNumber;
	  private String line;
	  private String productLfPf;
	  private String quater;
	  private String avgCurrentPrice;
	  
	  private String salesNumber;//数量
	  private String salesSum;//金额
	  private String discount;
	  
	  public ShopDayEntity(){
		  
	  }
 
	public ShopDayEntity(String salesTime, String upccode,
			String materialNumber, String line, String productLfPf,
			String quater, String avgCurrentPrice, String salesNumber,
			String salesSum, String discount) {
		super();
		this.salesTime = salesTime;
		this.upccode = upccode;
		this.materialNumber = materialNumber;
		this.line = line;
		this.productLfPf = productLfPf;
		this.quater = quater;
		this.avgCurrentPrice = avgCurrentPrice;
		this.salesNumber = salesNumber;
		this.salesSum = salesSum;
		this.discount = discount;
	}



	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getSalesTime() {
		return salesTime;
	}
	public void setSalesTime(String salesTime) {
		this.salesTime = salesTime;
	}
	public String getUpccode() {
		return upccode;
	}
	public void setUpccode(String upccode) {
		this.upccode = upccode;
	}
	public String getMaterialNumber() {
		return materialNumber;
	}
	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getProductLfPf() {
		return productLfPf;
	}
	public void setProductLfPf(String productLfPf) {
		this.productLfPf = productLfPf;
	}
	public String getQuater() {
		return quater;
	}
	public void setQuater(String quater) {
		this.quater = quater;
	}
	public String getAvgCurrentPrice() {
		return avgCurrentPrice;
	}
	public void setAvgCurrentPrice(String avgCurrentPrice) {
		this.avgCurrentPrice = avgCurrentPrice;
	}
	public String getSalesNumber() {
		return salesNumber;
	}
	public void setSalesNumber(String salesNumber) {
		this.salesNumber = salesNumber;
	}
	public String getSalesSum() {
		return salesSum;
	}
	public void setSalesSum(String salesSum) {
		this.salesSum = salesSum;
	}
	  

}
