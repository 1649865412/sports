/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.ordermanager.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;
import com.innshine.stockmanager.stockinfo.entity.StockInfo;

@Entity
@Table(name = "order_form_info")
public class OrderFormInfo implements Idable<Long>
{
	
	/**
	 * 序列
	 */
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
	 * 订货数量
	 */
	@Column(length = 10)
	private Integer orderNumber;
	
	/**
	 * 订货金额
	 */
	@Column(length = 11)
	private Double orderAmount;
	
	/**
	 * 转仓数量
	 */
	@Column(length = 10)
	private Integer transferNumber;
	
	/**
	 * 调货数量
	 */
	@Column(length = 10)
	private Integer transferCargoNumber;
	
	/**
	 * 退货数量
	 */
	@Column(length = 10)
	private Integer returnNumber;
	
	/**
	 * 预计到货期
	 */
	@Column(length = 50)
	private String predictArriveTime;
	
	/**
	 * 样品数量
	 */
	@Column(length = 10)
	private Integer sampleNumber;
	
	/**
	 * 出库单号
	 */
	@Column(length = 100)
	private String storehouseId;
	
	/**
	 * 颜色
	 */
	@Column(length = 25)
	private String color;
	
	/**
	 * 色号
	 */
	@Column(length = 25)
	private String colourNumber;
	
	/**
	 * 尺码
	 */
	@Column(length = 25)
	private String productSize;
	
	/**
	 * 到货数量
	 */
	@Column(length = 10)
	private Integer arrivalNumber;
	
	/**
	 * 到货时间，导入的没有
	 */
	@Column(nullable=false, length=0)
	@Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;
	
	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * 大款
	 */
	@Column(length = 25)
	private String productLarge;
	
	/**
	 * 店铺名称
	 */
	@Column(length = 255)
	private String shopName;
	
	/**
	 * 客户名称
	 */
	@Column(length = 100)
	private String customerName;
	
	/**
	 * 波次号
	 */
	@Column(length = 30)
	private String waveNumber;
	
	/**
	 * 原出库单号
	 */
	@Column(length = 100)
	private String oldStorehouseId;
	
	/**
	 * 备注
	 */
	@Column(length = 255)
	private String remarks;
	
/*	*//**
	 * 所属组织
	 *//*
	@Column(length = 19)
	private Long organizationId;*/
	
	/**
	 * 所属品牌
	 */
	@Column(length = 19)
	private Long brandId;
	
	
	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

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
	
	@OneToMany(mappedBy = "orderFormInfo", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	private List<StockInfo> stockInfos;
	
	public List<StockInfo> getStockInfos()
	{
		return stockInfos;
	}
	
	public void setStockInfos(List<StockInfo> stockInfos)
	{
		this.stockInfos = stockInfos;
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
	 * @param materialNumber
	 *        the materialNumber to set
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
	 * @param platformId
	 *        the platformId to set
	 */
	public void setPlatformId(String platformId)
	{
		this.platformId = platformId;
	}
	
	/**
	 * @return the platformId
	 */
	public String getPlatformId()
	{
		return this.platformId;
	}
	
	/**
	 * @param orderNumber
	 *        the orderNumber to set
	 */
	public void setOrderNumber(Integer orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	
	/**
	 * @return the orderNumber
	 */
	public Integer getOrderNumber()
	{
		return this.orderNumber;
	}
	
	/**
	 * @param orderAmount
	 *        the orderAmount to set
	 */
	public void setOrderAmount(Double orderAmount)
	{
		this.orderAmount = orderAmount;
	}
	
	/**
	 * @return the orderAmount
	 */
	public Double getOrderAmount()
	{
		return this.orderAmount;
	}
	
	/**
	 * @param transferNumber
	 *        the transferNumber to set
	 */
	public void setTransferNumber(Integer transferNumber)
	{
		this.transferNumber = transferNumber;
	}
	
	/**
	 * @return the transferNumber
	 */
	public Integer getTransferNumber()
	{
		return this.transferNumber;
	}
	
	/**
	 * @param returnNumber
	 *        the returnNumber to set
	 */
	public void setReturnNumber(Integer returnNumber)
	{
		this.returnNumber = returnNumber;
	}
	
	/**
	 * @return the returnNumber
	 */
	public Integer getReturnNumber()
	{
		return this.returnNumber;
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
	 * @param sampleNumber
	 *        the sampleNumber to set
	 */
	public void setSampleNumber(Integer sampleNumber)
	{
		this.sampleNumber = sampleNumber;
	}
	
	/**
	 * @return the sampleNumber
	 */
	public Integer getSampleNumber()
	{
		return this.sampleNumber;
	}
	
	/**
	 * @param storehouseId
	 *        the storehouseId to set
	 */
	public void setStorehouseId(String storehouseId)
	{
		this.storehouseId = storehouseId;
	}
	
	/**
	 * @return the storehouseId
	 */
	public String getStorehouseId()
	{
		return this.storehouseId;
	}
	
	/**
	 * @param color
	 *        the color to set
	 */
	public void setColor(String color)
	{
		this.color = color;
	}
	
	/**
	 * @return the color
	 */
	public String getColor()
	{
		return this.color;
	}
	
	/**
	 * @param colourNumber
	 *        the colourNumber to set
	 */
	public void setColourNumber(String colourNumber)
	{
		this.colourNumber = colourNumber;
	}
	
	/**
	 * @return the colourNumber
	 */
	public String getColourNumber()
	{
		return this.colourNumber;
	}
	
	/**
	 * @param productSize
	 *        the productSize to set
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
	 * @param arrivalNumber
	 *        the arrivalNumber to set
	 */
	public void setArrivalNumber(Integer arrivalNumber)
	{
		this.arrivalNumber = arrivalNumber;
	}
	
	/**
	 * @return the arrivalNumber
	 */
	public Integer getArrivalNumber()
	{
		return this.arrivalNumber;
	}
	
	/**
	 * @param productLarge
	 *        the productLarge to set
	 */
	public void setProductLarge(String productLarge)
	{
		this.productLarge = productLarge;
	}
	
	/**
	 * @return the productLarge
	 */
	public String getProductLarge()
	{
		return this.productLarge;
	}
	
	/**
	 * @param shopName
	 *        the shopName to set
	 */
	public void setShopName(String shopName)
	{
		this.shopName = shopName;
	}
	
	/**
	 * @return the shopName
	 */
	public String getShopName()
	{
		return this.shopName;
	}
	
	/**
	 * @param customerName
	 *        the customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	
	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return this.customerName;
	}
	
	/**
	 * @param waveNumber
	 *        the waveNumber to set
	 */
	public void setWaveNumber(String waveNumber)
	{
		this.waveNumber = waveNumber;
	}
	
	/**
	 * @return the waveNumber
	 */
	public String getWaveNumber()
	{
		return this.waveNumber;
	}
	
	/**
	 * @param oldStorehouseId
	 *        the oldStorehouseId to set
	 */
	public void setOldStorehouseId(String oldStorehouseId)
	{
		this.oldStorehouseId = oldStorehouseId;
	}
	
	/**
	 * @return the oldStorehouseId
	 */
	public String getOldStorehouseId()
	{
		return this.oldStorehouseId;
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
	/*
	*//**
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
	}
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
	
	public Integer getTransferCargoNumber()
	{
		return transferCargoNumber;
	}
	
	public void setTransferCargoNumber(Integer transferCargoNumber)
	{
		this.transferCargoNumber = transferCargoNumber;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
