package com.innshine.shopAnalyse.entity;

public class ShopAnalyseCheckResult
{
	/**
	 * 查询结果集实体bean 销售分析查询
	 * 
	 */
	private String upccode;
	private String materialNumber;
	private String salesTime;
	private String salesNumber;
	private String disCount;
	private String salesSum;
	private String outSalesRate;

	private String productPlatformId;
	private String tagPrice;
	private String productType;
	private String productSex;
	private String productLfPf;
	private String quater;

	private String daysx;

	public String getDaysx()
	{
		return daysx;
	}

	public void setDaysx(String daysx)
	{
		this.daysx = daysx;
	}

	public String getProductPlatformId()
	{
		return productPlatformId;
	}

	public void setProductPlatformId(String productPlatformId)
	{
		this.productPlatformId = productPlatformId;
	}

	public String getTagPrice()
	{
		return tagPrice;
	}

	public void setTagPrice(String tagPrice)
	{
		this.tagPrice = tagPrice;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}

	public String getProductSex()
	{
		return productSex;
	}

	public void setProductSex(String productSex)
	{
		this.productSex = productSex;
	}

	public String getProductLfPf()
	{
		return productLfPf;
	}

	public void setProductLfPf(String productLfPf)
	{
		this.productLfPf = productLfPf;
	}

	public String getQuater()
	{
		return quater;
	}

	public void setQuater(String quater)
	{
		this.quater = quater;
	}

	public String getOutSalesRate()
	{
		return outSalesRate;
	}

	public void setOutSalesRate(String outSalesRate)
	{
		this.outSalesRate = outSalesRate;
	}

	public String getUpccode()
	{
		return upccode;
	}

	public void setUpccode(String upccode)
	{
		this.upccode = upccode;
	}

	public String getMaterialNumber()
	{
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}

	public String getSalesTime()
	{
		return salesTime;
	}

	public void setSalesTime(String salesTime)
	{
		this.salesTime = salesTime;
	}

	public String getSalesNumber()
	{
		return salesNumber;
	}

	public void setSalesNumber(String salesNumber)
	{
		this.salesNumber = salesNumber;
	}

	public String getDisCount()
	{
		return disCount;
	}

	public void setDisCount(String disCount)
	{
		this.disCount = disCount;
	}

	public String getSalesSum()
	{
		return salesSum;
	}

	public void setSalesSum(String salesSum)
	{
		this.salesSum = salesSum;
	}

}
