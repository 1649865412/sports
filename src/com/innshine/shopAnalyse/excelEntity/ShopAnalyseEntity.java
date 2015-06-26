package com.innshine.shopAnalyse.excelEntity;

public class ShopAnalyseEntity {	
	
	
	/**
	 * 销售分析种类，系列，line ，性别(导出字段)
	 * 分析类别	销售数量	销售占比	付款金额	付款金额占比	吊牌金额	吊牌金额占比
	 */
	 private String productType ;
	 private String series;
	 private String inlineOr2ndline;
	 private String productSex;
	
	 
	 
	 private String salesNumber;
	 private String salesNumberDiscount;
	 
	 private String salesSum;
	 private String salesSumDiscount;
	 
	 private String salesTagMoney;
	 private String salesTagDiscount;
	 
	 public ShopAnalyseEntity(){}
	 
	 public ShopAnalyseEntity(String type, String salesNumber,
			String salesNumberDiscount, String salesSum,
			String salesSumDiscount, String salesTagMoney,
			String salesTagDiscount) {
		super();
		
		this.productType = type;
		this.series=type;
		this.inlineOr2ndline=type;
		this.productSex=type;
		
		this.salesNumber = salesNumber;
		this.salesNumberDiscount = salesNumberDiscount;
		this.salesSum = salesSum;
		this.salesSumDiscount = salesSumDiscount;
		this.salesTagMoney = salesTagMoney;
		this.salesTagDiscount = salesTagDiscount;
	}
	 
	 
	/**
	 * 销售分析Q季(导出字段)
	 *   分析类别	本月销售数量	本月销售占比	累积销售  售罄率（销售数量/到货数量）
	 */
	 private String quater ;
	 private String nowMonthSalesNumber;
	 private String nowMonthSalesNumberDiscount;
	 private String allSalesSum;
	 private String rateOut;
	 private String arriveNumber;
	 
	 
	 
	public ShopAnalyseEntity(String quater, String nowMonthSalesNumber,
			String nowMonthSalesNumberDiscount, String allSalesSum,
			String rateOut) {
		super();
		this.quater = quater;
		this.nowMonthSalesNumber = nowMonthSalesNumber;
		this.nowMonthSalesNumberDiscount = nowMonthSalesNumberDiscount;
		this.allSalesSum = allSalesSum;
		this.rateOut = rateOut;
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

	public String getArriveNumber() {
		return arriveNumber;
	}


	public void setArriveNumber(String arriveNumber) {
		this.arriveNumber = arriveNumber;
	}


	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSalesNumber() {
		return salesNumber;
	}
	public void setSalesNumber(String salesNumber) {
		this.salesNumber = salesNumber;
	}
	public String getSalesNumberDiscount() {
		return salesNumberDiscount;
	}
	public void setSalesNumberDiscount(String salesNumberDiscount) {
		this.salesNumberDiscount = salesNumberDiscount;
	}
	public String getSalesSum() {
		return salesSum;
	}
	public void setSalesSum(String salesSum) {
		this.salesSum = salesSum;
	}
	public String getSalesSumDiscount() {
		return salesSumDiscount;
	}
	public void setSalesSumDiscount(String salesSumDiscount) {
		this.salesSumDiscount = salesSumDiscount;
	}
	public String getSalesTagMoney() {
		return salesTagMoney;
	}
	public void setSalesTagMoney(String salesTagMoney) {
		this.salesTagMoney = salesTagMoney;
	}
	public String getSalesTagDiscount() {
		return salesTagDiscount;
	}
	public void setSalesTagDiscount(String salesTagDiscount) {
		this.salesTagDiscount = salesTagDiscount;
	}
	public String getQuater() {
		return quater;
	}
	public void setQuater(String quater) {
		this.quater = quater;
	}
	public String getNowMonthSalesNumber() {
		return nowMonthSalesNumber;
	}
	public void setNowMonthSalesNumber(String nowMonthSalesNumber) {
		this.nowMonthSalesNumber = nowMonthSalesNumber;
	}
	public String getNowMonthSalesNumberDiscount() {
		return nowMonthSalesNumberDiscount;
	}
	public void setNowMonthSalesNumberDiscount(String nowMonthSalesNumberDiscount) {
		this.nowMonthSalesNumberDiscount = nowMonthSalesNumberDiscount;
	}
	public String getAllSalesSum() {
		return allSalesSum;
	}
	public void setAllSalesSum(String allSalesSum) {
		this.allSalesSum = allSalesSum;
	}
	public String getRateOut() {
		return rateOut;
	}
	public void setRateOut(String rateOut) {
		this.rateOut = rateOut;
	}
	
	 
	 
}
