package api.taobao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;

/**
 * 物流服务标签 <code>LogisticServiceTag.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Entity
@Table(name = "t_logistic_service_tag")
public class LogisticServiceTag implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 物流服务下的标签属性,多个标签之间有";"分隔
	 */
	private String serviceTag;
	
	/**
	 * 服务类型=编码 平邮=POST 快递=FAST EMS=EMS 消费者选快递时为FAST
	 */
	private String serviceType;
	
	/**
	 * 交易编号 (父订单的交易编号)
	 */
	private Long tid;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getServiceTag()
	{
		return serviceTag;
	}
	
	public void setServiceTag(String serviceTag)
	{
		this.serviceTag = serviceTag;
	}
	
	public String getServiceType()
	{
		return serviceType;
	}
	
	public void setServiceType(String serviceType)
	{
		this.serviceType = serviceType;
	}
	
	public Number getTid()
	{
		return tid;
	}
	
	public void setTid(Long tid)
	{
		this.tid = tid;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
