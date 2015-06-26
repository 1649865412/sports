/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.productinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;

@Entity
@Table(name = "product_info")
public class ProductInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
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
	@Column(length = 255)
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
	 * 品牌ID
	 */
	@Column(length = 20)
	private Long brandId;
	
	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

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
	 * 数据更新时间
	 */
	@Column(length = 30)
	private String updateTime;
	
/*	*//**
	 * 所属组织
	 *//*
	@Column(length = 10)
	private Long organizationId;
	*/

	
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
	
	/**
	 * 预留字段1
	 */
	@Column(length = 255)
	private String reserve1;
	
	/**
	 * 预留字段2
	 */
	@Column(length = 255)
	private String reserve2;
	
	/**
	 * 预留字段3
	 */
	@Column(length = 255)
	private String reserve3;
	
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 * @param productUpccode
	 *            the productUpccode to set
	 */
	public void setProductUpccode(String productUpccode)
	{
		this.productUpccode = productUpccode;
	}
	
	/**
	 * @return the productUpccode
	 */
	public String getProductUpccode()
	{
		return this.productUpccode;
	}
	
	/**
	 * @param materialNumber
	 *            the materialNumber to set
	 */
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	/**
	 * @return the materialNumber
	 */
	public String getMaterialNumber()
	{
		return this.materialNumber;
	}
	
	/**
	 * @param inlineOr2ndline
	 *            the inlineOr2ndline to set
	 */
	public void setInlineOr2ndline(String inlineOr2ndline)
	{
		this.inlineOr2ndline = inlineOr2ndline;
	}
	
	/**
	 * @return the inlineOr2ndline
	 */
	public String getInlineOr2ndline()
	{
		return this.inlineOr2ndline;
	}
	
	/**
	 * @param productLfPf
	 *            the productLfPf to set
	 */
	public void setProductLfPf(String productLfPf)
	{
		this.productLfPf = productLfPf;
	}
	
	/**
	 * @return the productLfPf
	 */
	public String getProductLfPf()
	{
		return this.productLfPf;
	}
	
	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}
	
	/**
	 * @return the systemId
	 */
	public String getSystemId()
	{
		return this.systemId;
	}
	
	/**
	 * @param quater
	 *            the quater to set
	 */
	public void setQuater(String quater)
	{
		this.quater = quater;
	}
	
	/**
	 * @return the quater
	 */
	public String getQuater()
	{
		return this.quater;
	}
	
	/**
	 * @param productWeight
	 *            the productWeight to set
	 */
	public void setProductWeight(String productWeight)
	{
		this.productWeight = productWeight;
	}
	
	/**
	 * @return the productWeight
	 */
	public String getProductWeight()
	{
		return this.productWeight;
	}
	
	/**
	 * @param series
	 *            the series to set
	 */
	public void setSeries(String series)
	{
		this.series = series;
	}
	
	/**
	 * @return the series
	 */
	public String getSeries()
	{
		return this.series;
	}
	
	/**
	 * @param tagPrice
	 *            the tagPrice to set
	 */
	public void setTagPrice(Double tagPrice)
	{
		this.tagPrice = tagPrice;
	}
	
	/**
	 * @return the tagPrice
	 */
	public Double getTagPrice()
	{
		return this.tagPrice;
	}
	
	/**
	 * @param materialQuality
	 *            the materialQuality to set
	 */
	public void setMaterialQuality(String materialQuality)
	{
		this.materialQuality = materialQuality;
	}
	
	/**
	 * @return the materialQuality
	 */
	public String getMaterialQuality()
	{
		return this.materialQuality;
	}
	
	/**
	 * @param productPosition
	 *            the productPosition to set
	 */
	public void setProductPosition(String productPosition)
	{
		this.productPosition = productPosition;
	}
	
	/**
	 * @return the productPosition
	 */
	public String getProductPosition()
	{
		return this.productPosition;
	}
	
	/**
	 * @param productFunction
	 *            the productFunction to set
	 */
	public void setProductFunction(String productFunction)
	{
		this.productFunction = productFunction;
	}
	
	/**
	 * @return the productFunction
	 */
	public String getProductFunction()
	{
		return this.productFunction;
	}
	
	/**
	 * @param onMarketMonth
	 *            the onMarketMonth to set
	 */
	public void setOnMarketMonth(String onMarketMonth)
	{
		this.onMarketMonth = onMarketMonth;
	}
	
	/**
	 * @return the onMarketMonth
	 */
	public String getOnMarketMonth()
	{
		return this.onMarketMonth;
	}
	
	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image)
	{
		this.image = image;
	}
	
	/**
	 * @return the image
	 */
	public String getImage()
	{
		return this.image;
	}
	
	/**
	 * @param onSalesTime
	 *            the onSalesTime to set
	 */
	public void setOnSalesTime(String onSalesTime)
	{
		this.onSalesTime = onSalesTime;
	}
	
	/**
	 * @return the onSalesTime
	 */
	public String getOnSalesTime()
	{
		return this.onSalesTime;
	}
	
	/**
	 * @param productBrand
	 *            the productBrand to set
	 */
	public void setProductBrand(String productBrand)
	{
		this.productBrand = productBrand;
	}
	
	/**
	 * @return the productBrand
	 */
	public String getProductBrand()
	{
		return this.productBrand;
	}
	
	/**
	 * @param productPlace
	 *            the productPlace to set
	 */
	public void setProductPlace(String productPlace)
	{
		this.productPlace = productPlace;
	}
	
	/**
	 * @return the productPlace
	 */
	public String getProductPlace()
	{
		return this.productPlace;
	}
	
	/**
	 * @param productColor
	 *            the productColor to set
	 */
	public void setProductColor(String productColor)
	{
		this.productColor = productColor;
	}
	
	/**
	 * @return the productColor
	 */
	public String getProductColor()
	{
		return this.productColor;
	}
	
	/**
	 * @param productSize
	 *            the productSize to set
	 */
	public void setProductSize(String productSize)
	{
		this.productSize = productSize;
	}
	
	/**
	 * @return the productSize
	 */
	public String getProductSize()
	{
		return this.productSize;
	}
	
	/**
	 * @param productType
	 *            the productType to set
	 */
	public void setProductType(String productType)
	{
		this.productType = productType;
	}
	
	/**
	 * @return the productType
	 */
	public String getProductType()
	{
		return this.productType;
	}
	
	/**
	 * @param productSex
	 *            the productSex to set
	 */
	public void setProductSex(String productSex)
	{
		this.productSex = productSex;
	}
	
	/**
	 * @return the productSex
	 */
	public String getProductSex()
	{
		return this.productSex;
	}
	
	/**
	 * @param productAttribute
	 *            the productAttribute to set
	 */
	public void setProductAttribute(String productAttribute)
	{
		this.productAttribute = productAttribute;
	}
	
	/**
	 * @return the productAttribute
	 */
	public String getProductAttribute()
	{
		return this.productAttribute;
	}
	
	/**
	 * @param shoesBottom
	 *            the shoesBottom to set
	 */
	public void setShoesBottom(String shoesBottom)
	{
		this.shoesBottom = shoesBottom;
	}
	
	/**
	 * @return the shoesBottom
	 */
	public String getShoesBottom()
	{
		return this.shoesBottom;
	}
	
	/**
	 * @param colorId
	 *            the colorId to set
	 */
	public void setColorId(String colorId)
	{
		this.colorId = colorId;
	}
	
	/**
	 * @return the colorId
	 */
	public String getColorId()
	{
		return this.colorId;
	}
	
	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}
	
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime()
	{
		return this.updateTime;
	}
	
/*	*//**
	 * @param organizationId
	 *            the organizationId to set
	 *//*
	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}
	
	*//**
	 * @return the organizationId
	 *//*
	public Long getOrganizationId()
	{
		return this.organizationId;
	}*/
	
	/**
	 * @param productPlatformId
	 *            the productPlatformId to set
	 */
	public void setProductPlatformId(String productPlatformId)
	{
		this.productPlatformId = productPlatformId;
	}
	
	/**
	 * @return the productPlatformId
	 */
	public String getProductPlatformId()
	{
		return this.productPlatformId;
	}
	
	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	/**
	 * @return the remark
	 */
	public String getRemark()
	{
		return this.remark;
	}
	
	/**
	 * @param reserve1
	 *            the reserve1 to set
	 */
	public void setReserve1(String reserve1)
	{
		this.reserve1 = reserve1;
	}
	
	/**
	 * @return the reserve1
	 */
	public String getReserve1()
	{
		return this.reserve1;
	}
	
	/**
	 * @param reserve2
	 *            the reserve2 to set
	 */
	public void setReserve2(String reserve2)
	{
		this.reserve2 = reserve2;
	}
	
	/**
	 * @return the reserve2
	 */
	public String getReserve2()
	{
		return this.reserve2;
	}
	
	/**
	 * @param reserve3
	 *            the reserve3 to set
	 */
	public void setReserve3(String reserve3)
	{
		this.reserve3 = reserve3;
	}
	
	/**
	 * @return the reserve3
	 */
	public String getReserve3()
	{
		return this.reserve3;
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
