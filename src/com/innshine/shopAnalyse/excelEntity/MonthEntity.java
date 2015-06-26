package com.innshine.shopAnalyse.excelEntity;

public class MonthEntity {
	

	/**
	 * Excel月报报表数据预览与导出Bean(导出字段)
	 */
    private double sales;
    private double amount;
    private double  accounted;
    private double num;
    
    /**
	 * Excel月报统计报表金额增长率
	 */
    private String rateOfRise;
    private String timeYear;
	
    /**
	 * Excel月报报表数据预览与导出Bean(查询bean字段)
	 */
    private String quater;
    private String series;
    private String productType;
    private String inlineOr2ndline;
    private String productSex;
    private String timeMonth;
    private String  allSales;
    private String allAmount;
    private String formateSalesTime;
    
    
    public MonthEntity(){};
    
	public MonthEntity(double sales, double amount, double accounted) {
		super();
		this.sales = sales;
		this.amount = amount;
		this.accounted = accounted;
	}
	
	public MonthEntity(double sales, double amount, double accounted,String rateOfRise) {
		super();
		this.sales = sales;
		this.amount = amount;
		this.accounted = accounted;
		this.rateOfRise=rateOfRise;
	}
	

	public String getTimeYear() {
		return timeYear;
	}

	public void setTimeYear(String timeYear) {
		this.timeYear = timeYear;
	}


	public String getRateOfRise() {
		return rateOfRise;
	}

	public void setRateOfRise(String rateOfRise) {
		this.rateOfRise = rateOfRise;
	}

	public String getQuater() {
		return quater;
	}
	public void setQuater(String quater) {
		this.quater = quater;
	}
	public String getTimeMonth() {
		return timeMonth;
	}
	public void setTimeMonth(String timeMonth) {
		this.timeMonth = timeMonth;
	}
	public String getAllSales() {
		return allSales;
	}
	public void setAllSales(String allSales) {
		this.allSales = allSales;
	}
	public String getAllAmount() {
		return allAmount;
	}
	public void setAllAmount(String allAmount) {
		this.allAmount = allAmount;
	}



	public double getSales() {
		return sales;
	}
	public void setSales(double sales) {
		this.sales = sales;
	}


	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAccounted() {
		return accounted;
	}
	public void setAccounted(double accounted) {
		this.accounted = accounted;
	}

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}

	public String getInlineOr2ndline()
	{
		return inlineOr2ndline;
	}

	public void setInlineOr2ndline(String inlineOr2ndline)
	{
		this.inlineOr2ndline = inlineOr2ndline;
	}

	public String getProductSex()
	{
		return productSex;
	}

	public void setProductSex(String productSex)
	{
		this.productSex = productSex;
	}

	public String getFormateSalesTime()
	{
		return formateSalesTime;
	}

	public void setFormateSalesTime(String formateSalesTime)
	{
		this.formateSalesTime = formateSalesTime;
	}
	
}
