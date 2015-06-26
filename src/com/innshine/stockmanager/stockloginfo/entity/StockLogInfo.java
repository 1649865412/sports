/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockloginfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;
import com.innshine.stockmanager.stockinfo.entity.StockInfo;

@Entity
@Table(name = "stock_log_info")
public class StockLogInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 库存表主键
	 */
	@Column(nullable = false, length = 10, insertable = false, updatable = false)
	private Long stockId;
	
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
	 * 用户名
	 */
	@Column(length = 25)
	private String userName;
	
	/**
	 * 用户编号
	 */
	@Column(length = 19)
	private Long userId;
	
	/**
	 * IP地址
	 */
	@Column(length = 100)
	private String ipAddress;
	
	/**
	 * 备注
	 */
	@Column(length = 255)
	private String remarks;
	
	/**
	 * 所属组织
	 */
	/*
	 * @Column(length = 10) private Long organizationId;
	 */

	/**
	 * 品牌ID
	 */
	private Long brandId;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stockId")
	private StockInfo stockInfo;
	
	public StockInfo getStockInfo()
	{
		return stockInfo;
	}
	
	public void setStockInfo(StockInfo stockInfo)
	{
		this.stockInfo = stockInfo;
	}
	
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
	 * @param stockId
	 *        the stockId to set
	 */
	public void setStockId(Long stockId)
	{
		this.stockId = stockId;
	}
	
	/**
	 * @return the stockId
	 */
	public Long getStockId()
	{
		return this.stockId;
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
	 * @param userName
	 *        the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return this.userName;
	}
	
	/**
	 * @param userId
	 *        the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	
	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return this.userId;
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
	
	/**
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

	public Long getBrandId()
	{
		return brandId;
	}
	
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	
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
	
	public String getIpAddress()
	{
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
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
