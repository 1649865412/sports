/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.orderhelp.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.base.entity.Idable;


public class SdbOmeDeliveryItems {
	
    public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	private String itemId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String deliveryId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String productId;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String shopProductId;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String bn;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String productName;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String number;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String verify;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String verifyNum;
    
  
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
	 * @param shopProductId the shopProductId to set
	 */
    public void setShopProductId(String shopProductId){
       this.shopProductId = shopProductId;
    }
    
    /**
     * @return the shopProductId 
     */
    public String getShopProductId(){
       return this.shopProductId;
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
	 * @param productName the productName to set
	 */
    public void setProductName(String productName){
       this.productName = productName;
    }
    
    /**
     * @return the productName 
     */
    public String getProductName(){
       return this.productName;
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
	 * @param verify the verify to set
	 */
    public void setVerify(String verify){
       this.verify = verify;
    }
    
    /**
     * @return the verify 
     */
    public String getVerify(){
       return this.verify;
    }
	
	/**
	 * @param verifyNum the verifyNum to set
	 */
    public void setVerifyNum(String verifyNum){
       this.verifyNum = verifyNum;
    }
    
    /**
     * @return the verifyNum 
     */
    public String getVerifyNum(){
       return this.verifyNum;
    }
}
