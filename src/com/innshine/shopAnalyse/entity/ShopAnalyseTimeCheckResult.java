package com.innshine.shopAnalyse.entity;

import com.base.entity.Idable;

public class ShopAnalyseTimeCheckResult implements java.io.Serializable
{

	/**
	 * 查询结果集实体bean 销售时间纵向分析查询
	 * 
	 */
	private String upccode;
	private String materialNumber;
	private String salesTime;
	private String salesNumber;
	private String disCount;
	private String salesSum;

	private String series;
	private String quater;
	private String productBrand;

	private String productSex;
	private String tagPrice;

	public String getQuater()
	{
		return quater;
	}

	public void setQuater(String quater)
	{
		this.quater = quater;
	}

	public String getProductSex()
	{
		return productSex;
	}

	public void setProductSex(String productSex)
	{
		this.productSex = productSex;
	}

	public String getTagPrice()
	{
		return tagPrice;
	}

	public void setTagPrice(String tagPrice)
	{
		this.tagPrice = tagPrice;
	}

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public String getProductBrand()
	{
		return productBrand;
	}

	public void setProductBrand(String productBrand)
	{
		this.productBrand = productBrand;
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
