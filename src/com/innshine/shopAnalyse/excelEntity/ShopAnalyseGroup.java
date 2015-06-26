package com.innshine.shopAnalyse.excelEntity;


public class ShopAnalyseGroup {
	
	
     private String productType;
     
     private String quater;
     private String series;
     private String inlineOr2ndline;
     private String productSex;
     private String salesNumber;
     private String discount;//占比
     private String name;
     
     public ShopAnalyseGroup(){
    	 
     }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getQuater() {
		return quater;
	}

	public void setQuater(String quater) {
		this.quater = quater;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getInlineOr2ndline() {
		return inlineOr2ndline;
	}

	public void setInlineOr2ndline(String inlineOr2ndline) {
		this.inlineOr2ndline = inlineOr2ndline;
	}

	public String getProductSex() {
		return productSex;
	}

	public void setProductSex(String productSex) {
		this.productSex = productSex;
	}

	public String getSalesNumber() {
		return salesNumber;
	}

	public void setSalesNumber(String salesNumber) {
		this.salesNumber = salesNumber;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
     
     
}
