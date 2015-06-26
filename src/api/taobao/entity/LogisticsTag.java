package api.taobao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;

/**
 * 物流的服务标签
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */

@Entity
@Table(name = "t_logistics_tag")
public class LogisticsTag implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 主订单或子订单的订单号
	 */
	private String orderId;
	
	/**
	 * 服务标签
	 */
	@Transient
	private List<LogisticServiceTag> logisticServiceTag;
	
	/**
	 * 交易编号 (父订单的交易编号)
	 */
	private Long tid;
	
	public Long getTid()
	{
		return tid;
	}
	
	public void setTid(Long tid)
	{
		this.tid = tid;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getOrderId()
	{
		return orderId;
	}
	
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}
	
	public List<LogisticServiceTag> getLogisticServiceTag()
	{
		return logisticServiceTag;
	}
	
	public void setLogisticServiceTag(List<LogisticServiceTag> logisticServiceTag)
	{
		this.logisticServiceTag = logisticServiceTag;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
