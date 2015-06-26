/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.salesstockdetails.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;

@Entity
@Table(name = "sales_stock_details")
public class SalesStockDetails implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 69码
	 */
	@Column(length = 30)
	private String upccode;
	
	/**
	 * 款号（物料编码）
	 */
	@Column(length = 100)
	private String materialNumber;
	
	/**
	 * 平台ID
	 */
	@Column(length = 100)
	private String platformId;
	
	/**
	 * 销售时间
	 */
	@Column(length = 30)
	private String salesTime;
	
	/**
	 * 销量
	 */
	@Column(length = 12)
	private Double salesNumber;
	
	/**
	 * 成交价（均价）
	 */
	@Column(length = 12)
	private Double avgCurrentPrice;
	
	/**
	 * 销售金额
	 */
	@Column(length = 12)
	private BigDecimal salesAmount;
	
	/**
	 * 仓库
	 */
	@Column(length = 30)
	private String storeHouse;
	
	/**
	 * 入库时间
	 */
	@Column(length = 30)
	private String enteringTime;
	
	/**
	 * 订货量
	 */
	@Column(length = 12)
	private Double orderNumber;
	
	/**
	 * 库存量
	 */
	@Column(length = 12)
	private Double stockNumber;
	
	/**
	 * 到货量
	 */
	@Column(length = 12)
	private Double arriveNumber;
	
	/**
	 * 预计到货期
	 */
	@Column(length = 30)
	private String predictArriveTime;
	
	/**
	 * 实际到货时间
	 */
	@Column(length = 30)
	private String factArriveTime;
	
	/**
	 * 备注
	 */
	@Column(length = 255)
	private String remarks;
	
	/**
	 * 所属组织
	 */
/*	@Column(length = 10)
	private Long organizationId;
	*/
	/**
	 * 数据更新时间
	 */
	@Column(length = 50)
	private String updateTime;
	
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
	 * 品牌ID
	 */
	@Column(length = 20)
	private Long brandId;
	
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	
	/**
	 * @param id
	 *        the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 * @param upccode
	 *        the upccode to set
	 */
	public void setUpccode(String upccode)
	{
		this.upccode = upccode;
	}
	
	/**
	 * @return the upccode
	 */
	public String getUpccode()
	{
		return this.upccode;
	}
	
	/**
	 * @param salesTime
	 *        the salesTime to set
	 */
	public void setSalesTime(String salesTime)
	{
		this.salesTime = salesTime;
	}
	
	/**
	 * @return the salesTime
	 */
	public String getSalesTime()
	{
		return this.salesTime;
	}
	
	/**
	 * @param salesNumber
	 *        the salesNumber to set
	 */
	public void setSalesNumber(Double salesNumber)
	{
		this.salesNumber = salesNumber;
	}
	
	/**
	 * @return the salesNumber
	 */
	public Double getSalesNumber()
	{
		return this.salesNumber;
	}
	
	/**
	 * @param avgCurrentPrice
	 *        the avgCurrentPrice to set
	 */
	public void setAvgCurrentPrice(Double avgCurrentPrice)
	{
		this.avgCurrentPrice = avgCurrentPrice;
	}
	
	/**
	 * @return the avgCurrentPrice
	 */
	public Double getAvgCurrentPrice()
	{
		return this.avgCurrentPrice;
	}
	
	/**
	 * @param salesAmount
	 *        the salesAmount to set
	 */
	public void setSalesAmount(BigDecimal salesAmount)
	{
		this.salesAmount = salesAmount;
	}
	
	/**
	 * @return the salesAmount
	 */
	public BigDecimal getSalesAmount()
	{
		return null != salesAmount ? salesAmount.setScale(2, BigDecimal.ROUND_HALF_UP) : salesAmount;
	}
	
	/**
	 * @param storeHouse
	 *        the storeHouse to set
	 */
	public void setStoreHouse(String storeHouse)
	{
		this.storeHouse = storeHouse;
	}
	
	/**
	 * @return the storeHouse
	 */
	public String getStoreHouse()
	{
		return this.storeHouse;
	}
	
	/**
	 * @param enteringTime
	 *        the enteringTime to set
	 */
	public void setEnteringTime(String enteringTime)
	{
		this.enteringTime = enteringTime;
	}
	
	/**
	 * @return the enteringTime
	 */
	public String getEnteringTime()
	{
		return this.enteringTime;
	}
	
	/**
	 * @param orderNumber
	 *        the orderNumber to set
	 */
	public void setOrderNumber(Double orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	
	/**
	 * @return the orderNumber
	 */
	public Double getOrderNumber()
	{
		return this.orderNumber;
	}
	
	/**
	 * @param stockNumber
	 *        the stockNumber to set
	 */
	public void setStockNumber(Double stockNumber)
	{
		this.stockNumber = stockNumber;
	}
	
	/**
	 * @return the stockNumber
	 */
	public Double getStockNumber()
	{
		return this.stockNumber;
	}
	
	/**
	 * @param arriveNumber
	 *        the arriveNumber to set
	 */
	public void setArriveNumber(Double arriveNumber)
	{
		this.arriveNumber = arriveNumber;
	}
	
	/**
	 * @return the arriveNumber
	 */
	public Double getArriveNumber()
	{
		return this.arriveNumber;
	}
	
	/**
	 * @param predictArriveTime
	 *        the predictArriveTime to set
	 */
	public void setPredictArriveTime(String predictArriveTime)
	{
		this.predictArriveTime = predictArriveTime;
	}
	
	/**
	 * @return the predictArriveTime
	 */
	public String getPredictArriveTime()
	{
		return this.predictArriveTime;
	}
	
	/**
	 * @param factArriveTime
	 *        the factArriveTime to set
	 */
	public void setFactArriveTime(String factArriveTime)
	{
		this.factArriveTime = factArriveTime;
	}
	
	/**
	 * @return the factArriveTime
	 */
	public String getFactArriveTime()
	{
		return this.factArriveTime;
	}
	
	/**
	 * @param remarks
	 *        the remarks to set
	 */
	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}
	
	/**
	 * @return the remarks
	 */
	public String getRemarks()
	{
		return this.remarks;
	}
	
/*	*//**
	 * @param organizationId
	 *        the organizationId to set
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
	 * @param updateTime
	 *        the updateTime to set
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
	
	/**
	 * @param reserve1
	 *        the reserve1 to set
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
	 *        the reserve2 to set
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
	 *        the reserve3 to set
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
	
	public String getMaterialNumber()
	{
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	public String getPlatformId()
	{
		return platformId;
	}
	
	public void setPlatformId(String platformId)
	{
		this.platformId = platformId;
	}
	
	public Long getBrandId()
	{
		return brandId;
	}
	
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
