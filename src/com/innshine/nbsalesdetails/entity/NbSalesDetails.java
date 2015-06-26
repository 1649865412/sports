/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.nbsalesdetails.entity;

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
@Table(name = "nb_sales_details")
public class NbSalesDetails implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 69码
	 */
	@Column(length = 100)
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
	 * 销售起止时间
	 */
	@Column(length = 100)
	private String marketStartTime;
	
	/**
	 * 销售结束时间
	 */
	@Column(length = 100)
	private String marketEndTime;
	
	/**
	 * 销售数量
	 */
	@Column(length = 11)
	private Double salesNumber;
	
	/**
	 * 成交价（均价）
	 */
	@Column(length = 11)
	private Double avgCurrentPrice;
	
	/**
	 * 销售金额
	 */
	@Column(length = 11)
	private BigDecimal salesAmount;
	
	/**
	 * 仓库
	 */
	@Column(length = 100)
	private String storeHouse;
	
	/**
	 * 入库时间
	 */
	@Column(length = 30)
	private String enteringTime;
	
	/**
	 * 订货量
	 */
	@Column(length = 11)
	private Double orderNumber;
	
	/**
	 * 库存量
	 */
	@Column(length = 11)
	private Double stockNumber;
	
	/**
	 * 到货量
	 */
	@Column(length = 11)
	private Double arriveNumber;
	
	/**
	 * 预计到货期
	 */
	@Column(length = 50)
	private String predictArriveTime;
	
	/**
	 * 实际到货时间
	 */
	@Column(length = 50)
	private String factArriveTime;
	
	/**
	 * 备注
	 */
	@Column(length = 255)
	private String remarks;
	
	/**
	 * 所属组织
	 */
	/*@Column(length = 10)
	private Long organizationId;*/

	/**
	 * 数据更新时间
	 */
	@Column(length = 100)
	private String updateTime;
	
	/**
	 * 预留字段1
	 */
	@Column(length = 100)
	private String reserve1;
	
	/**
	 * 预留字段2
	 */
	@Column(length = 100)
	private String reserve2;
	
	/**
	 * 预留字段3
	 */
	@Column(length = 100)
	private String reserve3;
	
	/**
	 * 品牌ID
	 */
	@Column(length = 20)
	private Long brandId;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
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
	
	public String getPlatformId()
	{
		return platformId;
	}
	
	public void setPlatformId(String platformId)
	{
		this.platformId = platformId;
	}
	
	public String getMarketStartTime()
	{
		return marketStartTime;
	}
	
	public void setMarketStartTime(String marketStartTime)
	{
		this.marketStartTime = marketStartTime;
	}
	
	public String getMarketEndTime()
	{
		return marketEndTime;
	}
	
	public void setMarketEndTime(String marketEndTime)
	{
		this.marketEndTime = marketEndTime;
	}
	
	public Double getSalesNumber()
	{
		return salesNumber;
	}
	
	public void setSalesNumber(Double salesNumber)
	{
		this.salesNumber = salesNumber;
	}
	
	public Double getAvgCurrentPrice()
	{
		return avgCurrentPrice;
	}
	
	public void setAvgCurrentPrice(Double avgCurrentPrice)
	{
		this.avgCurrentPrice = avgCurrentPrice;
	}
	
	public BigDecimal getSalesAmount()
	{
		return null != salesAmount ? salesAmount.setScale(2, BigDecimal.ROUND_HALF_UP) : salesAmount;
	}
	
	public void setSalesAmount(BigDecimal salesAmount)
	{
		this.salesAmount = salesAmount;
	}
	
	public String getStoreHouse()
	{
		return storeHouse;
	}
	
	public void setStoreHouse(String storeHouse)
	{
		this.storeHouse = storeHouse;
	}
	
	public String getEnteringTime()
	{
		return enteringTime;
	}
	
	public void setEnteringTime(String enteringTime)
	{
		this.enteringTime = enteringTime;
	}
	
	public Double getOrderNumber()
	{
		return orderNumber;
	}
	
	public void setOrderNumber(Double orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	
	public Double getStockNumber()
	{
		return stockNumber;
	}
	
	public void setStockNumber(Double stockNumber)
	{
		this.stockNumber = stockNumber;
	}
	
	public Double getArriveNumber()
	{
		return arriveNumber;
	}
	
	public void setArriveNumber(Double arriveNumber)
	{
		this.arriveNumber = arriveNumber;
	}
	
	public String getPredictArriveTime()
	{
		return predictArriveTime;
	}
	
	public void setPredictArriveTime(String predictArriveTime)
	{
		this.predictArriveTime = predictArriveTime;
	}
	
	public String getFactArriveTime()
	{
		return factArriveTime;
	}
	
	public void setFactArriveTime(String factArriveTime)
	{
		this.factArriveTime = factArriveTime;
	}
	
	public String getRemarks()
	{
		return remarks;
	}
	
	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}
	
/*	public Long getOrganizationId()
	{
		return organizationId;
	}
	
	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}*/
	
	public String getUpdateTime()
	{
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}
	
	public String getReserve1()
	{
		return reserve1;
	}
	
	public void setReserve1(String reserve1)
	{
		this.reserve1 = reserve1;
	}
	
	public String getReserve2()
	{
		return reserve2;
	}
	
	public void setReserve2(String reserve2)
	{
		this.reserve2 = reserve2;
	}
	
	public String getReserve3()
	{
		return reserve3;
	}
	
	public void setReserve3(String reserve3)
	{
		this.reserve3 = reserve3;
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
