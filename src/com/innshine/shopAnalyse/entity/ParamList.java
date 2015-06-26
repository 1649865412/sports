package com.innshine.shopAnalyse.entity;

public class ParamList
{
	public String name;
	//public Long organizationId;
	/**
	 * 品牌ID
	 */
	private Long brandId;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getBrandId()
	{
		return brandId;
	}

	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}

/*	public Long getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}*/

}
