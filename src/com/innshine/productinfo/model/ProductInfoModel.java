package com.innshine.productinfo.model;

import javax.persistence.Column;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProductInfoModel
{
	/**
	 * 69码
	 */
	@Column(length = 100)
	private String productUpccode;
	
	/**
	 * 款号（物料编码）
	 */
	@Column(length = 100)
	private String materialNumber;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * Inline/2ndline
	 */
	@Column(length = 30)
	private String inlineOr2ndline;
	
	/**
	 * LF/PF(系列)
	 */
	@Column(length = 100)
	private String productLfPf;
	
	/**
	 * 系统编号（款号+宽度）
	 */
	@Column(length = 100)
	private String systemId;
	
	/**
	 * Q季(季节)
	 */
	@Column(length = 30)
	private String quater;
	
	/**
	 * 重量
	 */
	@Column(length = 30)
	private String productWeight;
	
	/**
	 * 系列
	 */
	@Column(length = 50)
	private String series;
	
	/**
	 * 吊牌价
	 */
	@Column(length = 11)
	private Double tagPrice;
	
	/**
	 * 材质
	 */
	@Column(length = 30)
	private String materialQuality;
	
	/**
	 * 商品定位
	 */
	@Column(length = 30)
	private String productPosition;
	
	/**
	 * 功能
	 */
	@Column(length = 30)
	private String productFunction;
	
	/**
	 * 上市月份
	 */
	@Column(length = 30)
	private String onMarketMonth;
	
	/**
	 * 图片
	 */
	@Column(length = 65535)
	private String image;
	
	/**
	 * 上架日期
	 */
	@Column(length = 100)
	private String onSalesTime;
	
	/**
	 * 品牌
	 */
	@Column(length = 30)
	private String productBrand;
	
	/**
	 * 产地
	 */
	@Column(length = 100)
	private String productPlace;
	
	/**
	 * 颜色
	 */
	@Column(length = 30)
	private String productColor;
	
	/**
	 * 尺码
	 */
	@Column(length = 30)
	private String productSize;
	
	/**
	 * 类别
	 */
	@Column(length = 30)
	private String productType;
	
	/**
	 * 性别
	 */
	@Column(length = 30)
	private String productSex;
	
	/**
	 * 属性
	 */
	@Column(length = 30)
	private String productAttribute;
	
	/**
	 * 鞋底
	 */
	@Column(length = 30)
	private String shoesBottom;
	
	/**
	 * 色号
	 */
	@Column(length = 30)
	private String colorId;
	
	/**
	 * 平台ID
	 */
	@Column(length = 100)
	private String productPlatformId;
	
	/**
	 * 备注
	 */
	@Column(length = 30)
	private String remark;
	
	public String getProductUpccode()
	{
		return productUpccode;
	}
	
	public void setProductUpccode(String productUpccode)
	{
		this.productUpccode = productUpccode;
	}
	
	public String getMaterialNumber()
	{
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	public String getInlineOr2ndline()
	{
		return inlineOr2ndline;
	}
	
	public void setInlineOr2ndline(String inlineOr2ndline)
	{
		this.inlineOr2ndline = inlineOr2ndline;
	}
	
	public String getProductLfPf()
	{
		return productLfPf;
	}
	
	public void setProductLfPf(String productLfPf)
	{
		this.productLfPf = productLfPf;
	}
	
	public String getSystemId()
	{
		return systemId;
	}
	
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}
	
	public String getQuater()
	{
		return quater;
	}
	
	public void setQuater(String quater)
	{
		this.quater = quater;
	}
	
	public String getProductWeight()
	{
		return productWeight;
	}
	
	public void setProductWeight(String productWeight)
	{
		this.productWeight = productWeight;
	}
	
	public String getSeries()
	{
		return series;
	}
	
	public void setSeries(String series)
	{
		this.series = series;
	}
	
	public Double getTagPrice()
	{
		return tagPrice;
	}
	
	public void setTagPrice(Double tagPrice)
	{
		this.tagPrice = tagPrice;
	}
	
	public String getMaterialQuality()
	{
		return materialQuality;
	}
	
	public void setMaterialQuality(String materialQuality)
	{
		this.materialQuality = materialQuality;
	}
	
	public String getProductPosition()
	{
		return productPosition;
	}
	
	public void setProductPosition(String productPosition)
	{
		this.productPosition = productPosition;
	}
	
	public String getProductFunction()
	{
		return productFunction;
	}
	
	public void setProductFunction(String productFunction)
	{
		this.productFunction = productFunction;
	}
	
	public String getOnMarketMonth()
	{
		return onMarketMonth;
	}
	
	public void setOnMarketMonth(String onMarketMonth)
	{
		this.onMarketMonth = onMarketMonth;
	}
	
	public String getImage()
	{
		return image;
	}
	
	public void setImage(String image)
	{
		this.image = image;
	}
	
	public String getOnSalesTime()
	{
		return onSalesTime;
	}
	
	public void setOnSalesTime(String onSalesTime)
	{
		this.onSalesTime = onSalesTime;
	}
	
	public String getProductBrand()
	{
		return productBrand;
	}
	
	public void setProductBrand(String productBrand)
	{
		this.productBrand = productBrand;
	}
	
	public String getProductPlace()
	{
		return productPlace;
	}
	
	public void setProductPlace(String productPlace)
	{
		this.productPlace = productPlace;
	}
	
	public String getProductColor()
	{
		return productColor;
	}
	
	public void setProductColor(String productColor)
	{
		this.productColor = productColor;
	}
	
	public String getProductSize()
	{
		return productSize;
	}
	
	public void setProductSize(String productSize)
	{
		this.productSize = productSize;
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
	
	public String getProductAttribute()
	{
		return productAttribute;
	}
	
	public void setProductAttribute(String productAttribute)
	{
		this.productAttribute = productAttribute;
	}
	
	public String getShoesBottom()
	{
		return shoesBottom;
	}
	
	public void setShoesBottom(String shoesBottom)
	{
		this.shoesBottom = shoesBottom;
	}
	
	public String getColorId()
	{
		return colorId;
	}
	
	public void setColorId(String colorId)
	{
		this.colorId = colorId;
	}
	
	public String getProductPlatformId()
	{
		return productPlatformId;
	}
	
	public void setProductPlatformId(String productPlatformId)
	{
		this.productPlatformId = productPlatformId;
	}
	
	public String getRemark()
	{
		return remark;
	}
	
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	public String getProductName()
	{
		return productName;
	}
	
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
