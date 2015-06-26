package com.innshine.ordermanager.service;

/**
 * 定义订单类型类 <code>OrderType.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public enum OrderType
{
	/**
	 * 订货单
	 */
	/**
	 **/
	ORDER_INDENT_TYPE("订货单", 1), ORDER_ARRIVAL_TYPE("到货单", 2);
	
	private String name;
	
	private int type;
	
	private OrderType(String name, int type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
}
