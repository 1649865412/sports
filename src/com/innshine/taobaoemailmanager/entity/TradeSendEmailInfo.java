/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.taobaoemailmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.base.entity.Idable;

@Entity
@Table(name = "taobao_trade_send_email_info")
public class TradeSendEmailInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 邮箱地址
	 */
	@Column(nullable = false, length = 100)
	private String emailAddress;
	
	/**
	 * 用户名称
	 */
	@Column(length = 255)
	private String emailUserName;
	
	/**
	 * 联系电话
	 */
	@Column(length = 50)
	private String phoneNumber;
	
	/**
	 * 登陆用户名
	 */
	@Column(length = 100)
	private String userName;
	
	/**
	 * 用户ID
	 */
	@Column(length = 19)
	private Long userId;
	
	/**
	 * IP地址
	 */
	@Column(length = 100)
	private String userIpAddress;
	
	/**
	 * 是否需要发送
	 */
	@Column(length = 3)
	private Integer emailSend;
	
	/**
	 * 备注
	 */
	@Column(length = 255)
	private String remarks;
	
	/**
	 * 所属组织
	 */
	/*@Column(length = 19)
	private Long organizationId;*/
	

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
	 * @param emailAddress
	 *        the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
	
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return this.emailAddress;
	}
	
	/**
	 * @param emailUserName
	 *        the emailUserName to set
	 */
	public void setEmailUserName(String emailUserName)
	{
		this.emailUserName = emailUserName;
	}
	
	/**
	 * @return the emailUserName
	 */
	public String getEmailUserName()
	{
		return this.emailUserName;
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
	 * @param userIpAddress
	 *        the userIpAddress to set
	 */
	public void setUserIpAddress(String userIpAddress)
	{
		this.userIpAddress = userIpAddress;
	}
	
	/**
	 * @return the userIpAddress
	 */
	public String getUserIpAddress()
	{
		return this.userIpAddress;
	}
	
	/**
	 * @param emailSend
	 *        the emailSend to set
	 */
	public void setEmailSend(Integer emailSend)
	{
		this.emailSend = emailSend;
	}
	
	/**
	 * @return the emailSend
	 */
	public Integer getEmailSend()
	{
		return this.emailSend;
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
	
	public Long getBrandId()
	{
		return brandId;
	}
	
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
