package com.innshine.orderhelp.model;

public class OrderObj {
	public String orderId;
	public String orderBn;
	
	public String sendId;
	public String sendBn;
	
	public String expressBn;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderBn() {
		return orderBn;
	}
	public void setOrderBn(String orderBn) {
		this.orderBn = orderBn;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getSendBn() {
		return sendBn;
	}
	public void setSendBn(String sendBn) {
		this.sendBn = sendBn;
	}
	public String getExpressBn() {
		return expressBn;
	}
	public void setExpressBn(String expressBn) {
		this.expressBn = expressBn;
	}
	public String getExpressCorp() {
		return expressCorp;
	}
	public void setExpressCorp(String expressCorp) {
		this.expressCorp = expressCorp;
	}
	public String expressCorp;
}
