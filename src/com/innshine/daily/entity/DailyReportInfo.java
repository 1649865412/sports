/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.daily.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.base.entity.Idable;

@Entity
@Table(name = "daily_report_info")
public class DailyReportInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 创建时间
	 */
	@Column(nullable = false, length = 25)
	private String fileCreateTime;
	
	/**
	 * 文件名称
	 */
	@Column(nullable = false, length = 255)
	private String fileCreateName;
	
	/**
	 * 默认存放路径
	 */
	@Column(length = 1000)
	private String defaultSavePath;
	
	/**
	 * 备注
	 */
	@Column(length = 255)
	private String remarks;
	
	/**
	 * 所属组织
	 */
	/*
	 * @Column(length = 19) private Long organizationId;
	 */

	/**
	 * 数据更新时间
	 */
	@Column(length = 25)
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
	private Long brandId;
	
	/**
	 * 发送状态 1 ：发送成功 0：发送失败
	 */
	private Integer sendStauts;
	
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
	 * @param fileCreateTime
	 *        the fileCreateTime to set
	 */
	public void setFileCreateTime(String fileCreateTime)
	{
		this.fileCreateTime = fileCreateTime;
	}
	
	/**
	 * @return the fileCreateTime
	 */
	public String getFileCreateTime()
	{
		return this.fileCreateTime;
	}
	
	/**
	 * @param fileCreateName
	 *        the fileCreateName to set
	 */
	public void setFileCreateName(String fileCreateName)
	{
		this.fileCreateName = fileCreateName;
	}
	
	/**
	 * @return the fileCreateName
	 */
	public String getFileCreateName()
	{
		return this.fileCreateName;
	}
	
	/**
	 * @param defaultSavePath
	 *        the defaultSavePath to set
	 */
	public void setDefaultSavePath(String defaultSavePath)
	{
		this.defaultSavePath = defaultSavePath;
	}
	
	/**
	 * @return the defaultSavePath
	 */
	public String getDefaultSavePath()
	{
		return this.defaultSavePath;
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
	
	public Long getBrandId()
	{
		return brandId;
	}
	
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	

	public Integer getSendStauts()
	{
		return sendStauts;
	}

	public void setSendStauts(Integer sendStauts)
	{
		this.sendStauts = sendStauts;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
