/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.orderhelp.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.base.entity.Idable;


public class SdbOmeDeliveryItemsDetail{
	
    private String detailId;
    
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String deliveryId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String deliveryItemId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String orderId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String orderItemId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String orderObjId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=50)
    private String itemType;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String productId;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String bn;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String number;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal price;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal amount;
    
    
	
	/**
	 * @param deliveryId the deliveryId to set
	 */
    public void setDeliveryId(String deliveryId){
       this.deliveryId = deliveryId;
    }
    
    /**
     * @return the deliveryId 
     */
    public String getDeliveryId(){
       return this.deliveryId;
    }
	
	/**
	 * @param deliveryItemId the deliveryItemId to set
	 */
    public void setDeliveryItemId(String deliveryItemId){
       this.deliveryItemId = deliveryItemId;
    }
    
    /**
     * @return the deliveryItemId 
     */
    public String getDeliveryItemId(){
       return this.deliveryItemId;
    }
	
	/**
	 * @param orderId the orderId to set
	 */
    public void setOrderId(String orderId){
       this.orderId = orderId;
    }
    
    /**
     * @return the orderId 
     */
    public String getOrderId(){
       return this.orderId;
    }
	
	/**
	 * @param orderItemId the orderItemId to set
	 */
    public void setOrderItemId(String orderItemId){
       this.orderItemId = orderItemId;
    }
    
    /**
     * @return the orderItemId 
     */
    public String getOrderItemId(){
       return this.orderItemId;
    }
	
	/**
	 * @param orderObjId the orderObjId to set
	 */
    public void setOrderObjId(String orderObjId){
       this.orderObjId = orderObjId;
    }
    
    /**
     * @return the orderObjId 
     */
    public String getOrderObjId(){
       return this.orderObjId;
    }
	
	/**
	 * @param itemType the itemType to set
	 */
    public void setItemType(String itemType){
       this.itemType = itemType;
    }
    
    /**
     * @return the itemType 
     */
    public String getItemType(){
       return this.itemType;
    }
	
	/**
	 * @param productId the productId to set
	 */
    public void setProductId(String productId){
       this.productId = productId;
    }
    
    /**
     * @return the productId 
     */
    public String getProductId(){
       return this.productId;
    }
	
	/**
	 * @param bn the bn to set
	 */
    public void setBn(String bn){
       this.bn = bn;
    }
    
    /**
     * @return the bn 
     */
    public String getBn(){
       return this.bn;
    }
	
	/**
	 * @param number the number to set
	 */
    public void setNumber(String number){
       this.number = number;
    }
    
    /**
     * @return the number 
     */
    public String getNumber(){
       return this.number;
    }
	
	/**
	 * @param price the price to set
	 */
    public void setPrice(BigDecimal price){
       this.price = price;
    }
    
    /**
     * @return the price 
     */
    public BigDecimal getPrice(){
       return this.price;
    }
	
	/**
	 * @param amount the amount to set
	 */
    public void setAmount(BigDecimal amount){
       this.amount = amount;
    }
    
    /**
     * @return the amount 
     */
    public BigDecimal getAmount(){
       return this.amount;
    }
}
