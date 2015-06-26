/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockinfo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;
import com.innshine.ordermanager.entity.OrderFormInfo;
import com.innshine.stockmanager.stockloginfo.entity.StockLogInfo;

@Entity
@Table(name = "stock_info")
public class StockInfo implements Idable<Long>
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
	 * 现有库存数量
	 */
	@Column(length = 10)
	private Integer currentStockNumber;
	
	/**
	 * 可用库存数据
	 */
	@Column(length = 10)
	private Integer availableStockNumber;
	
	/**
	 * 备注
	 */
	@Column(length = 255)
	private String remarks;
	
	/**
	 * 品牌ID
	 */
	private Long brandId;
	
	/**
	 * 所属组织
	 */
	/*
	 * @Column(length = 19) private Long organizationId;
	 */
	/**
	 * 数据更新时间
	 */
	@Column(length = 100)
	private String updateTime;
	
	/**
	 * 订货单表
	 */
	@Column(length = 10, insertable = false, updatable = false, nullable = false)
	private Long orderId;
	
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
	
	@OneToMany(mappedBy = "stockInfo", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	private List<StockLogInfo> stockLogInfos = new ArrayList<StockLogInfo>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "orderId")
	private OrderFormInfo orderFormInfo;
	
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
	 * @param currentStockNumber
	 *        the currentStockNumber to set
	 */
	public void setCurrentStockNumber(Integer currentStockNumber)
	{
		this.currentStockNumber = currentStockNumber;
	}
	
	/**
	 * @return the currentStockNumber
	 */
	public Integer getCurrentStockNumber()
	{
		return this.currentStockNumber;
	}
	
	/**
	 * @param availableStockNumber
	 *        the availableStockNumber to set
	 */
	public void setAvailableStockNumber(Integer availableStockNumber)
	{
		this.availableStockNumber = availableStockNumber;
	}
	
	/**
	 * @return the availableStockNumber
	 */
	public Integer getAvailableStockNumber()
	{
		return this.availableStockNumber;
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
	
	public Long getBrandId()
	{
		return brandId;
	}
	
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	
	/*	*//**
	 * @param organizationId
	 *        the organizationId to set
	 */
	/*
	 * public void setOrganizationId(Long organizationId) { this.organizationId
	 * = organizationId; }
	 *//**
	 * @return the organizationId
	 */
	/*
	 * public Long getOrganizationId() { return this.organizationId; }
	 */
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
	 * @param orderId
	 *        the orderId to set
	 */
	public void setOrderId(Long orderId)
	{
		this.orderId = orderId;
	}
	
	/**
	 * @return the orderId
	 */
	public Long getOrderId()
	{
		return this.orderId;
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
	
	public List<StockLogInfo> getStockLogInfos()
	{
		return stockLogInfos;
	}
	
	public void setStockLogInfos(List<StockLogInfo> stockLogInfos)
	{
		this.stockLogInfos = stockLogInfos;
	}
	
	public OrderFormInfo getOrderFormInfo()
	{
		return orderFormInfo;
	}
	
	public void setOrderFormInfo(OrderFormInfo orderFormInfo)
	{
		this.orderFormInfo = orderFormInfo;
	}
	
	public String getMaterialNumber()
	{
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
