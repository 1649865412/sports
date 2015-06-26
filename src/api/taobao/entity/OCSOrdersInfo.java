package api.taobao.entity;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 从OCS数据库中销售订单信息实体类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class OCSOrdersInfo
{
	/**
	 * 订单编号 (真正的订单号，可外部传入或内部生成)
	 */
	private String orderBn;
	
	/**
	 * 订单id (内部自增关联用)
	 */
	private String orderId;
	/**
	 * 订单总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 下单时间
	 */
	private String createTime;
	
	public String getOrderBn()
	{
		return orderBn;
	}
	
	public void setOrderBn(String orderBn)
	{
		this.orderBn = orderBn;
	}
	
	public String getOrderId()
	{
		return orderId;
	}
	
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}
	
	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}
	
	public String getCreateTime()
	{
		return createTime;
	}
	
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
