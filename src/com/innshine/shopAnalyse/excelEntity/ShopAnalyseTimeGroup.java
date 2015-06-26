package com.innshine.shopAnalyse.excelEntity;


public class ShopAnalyseTimeGroup {
	
	
	/**
	 * 时间纵向分析分类实体bean
	 */
	 //种类，Q季，系列，一线还是二线，性别，
     private String productType;
     private String quater;
     private String series;
     private String inlineOr2ndline;
     private String productSex;
     

     private String salesNumber;
     private String stockNumber;
     private String discount;//占比
     private String raiseCount;//增长率
     
     
     
     private String yearOneSalesNumber;
     private String yearOneStockNumber;
     private String yearOneDiscount;//占比
     private String yearOneRaiseCount;//增长率
     
     
     private String yearTwoSalesNumber;
     private String yearTwoStockNumber;
     private String yearTwoDiscount;//占比
     private String yearTwoRaiseCount;//增长率
     
     
     public ShopAnalyseTimeGroup(){	 
     }

     
     
	public String getStockNumber() {
		return stockNumber;
	}



	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}



	public String getRaiseCount() {
		return raiseCount;
	}



	public void setRaiseCount(String raiseCount) {
		this.raiseCount = raiseCount;
	}



	public String getYearOneSalesNumber() {
		return yearOneSalesNumber;
	}



	public void setYearOneSalesNumber(String yearOneSalesNumber) {
		this.yearOneSalesNumber = yearOneSalesNumber;
	}



	public String getYearOneStockNumber() {
		return yearOneStockNumber;
	}



	public void setYearOneStockNumber(String yearOneStockNumber) {
		this.yearOneStockNumber = yearOneStockNumber;
	}



	public String getYearOneDiscount() {
		return yearOneDiscount;
	}



	public void setYearOneDiscount(String yearOneDiscount) {
		this.yearOneDiscount = yearOneDiscount;
	}



	public String getYearOneRaiseCount() {
		return yearOneRaiseCount;
	}



	public void setYearOneRaiseCount(String yearOneRaiseCount) {
		this.yearOneRaiseCount = yearOneRaiseCount;
	}



	public String getYearTwoSalesNumber() {
		return yearTwoSalesNumber;
	}



	public void setYearTwoSalesNumber(String yearTwoSalesNumber) {
		this.yearTwoSalesNumber = yearTwoSalesNumber;
	}



	public String getYearTwoStockNumber() {
		return yearTwoStockNumber;
	}



	public void setYearTwoStockNumber(String yearTwoStockNumber) {
		this.yearTwoStockNumber = yearTwoStockNumber;
	}



	public String getYearTwoDiscount() {
		return yearTwoDiscount;
	}



	public void setYearTwoDiscount(String yearTwoDiscount) {
		this.yearTwoDiscount = yearTwoDiscount;
	}



	public String getYearTwoRaiseCount() {
		return yearTwoRaiseCount;
	}



	public void setYearTwoRaiseCount(String yearTwoRaiseCount) {
		this.yearTwoRaiseCount = yearTwoRaiseCount;
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
