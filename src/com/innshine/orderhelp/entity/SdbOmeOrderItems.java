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
 
public class SdbOmeOrderItems  {
	 
    private String itemId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String orderId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String objId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=50)
    private String shopGoodsId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=10)
    private String productId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=50)
    private String shopProductId;
    
	/**
	 * 
	 */
    @Column(length=40)
    private String bn;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String name;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal cost;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal price;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal amount;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal pmtPrice;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal salePrice;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal weight;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String nums;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String sendnum;
    
	/**
	 * 
	 */
    @Column(length=2147483647)
    private String addon;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=50)
    private String itemType;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String score;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String sellCode;
    
	/**
	 * 
	 */
    @Column(length=5)
    private String delete;
    
   
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	 * @param objId the objId to set
	 */
    public void setObjId(String objId){
       this.objId = objId;
    }
    
    /**
     * @return the objId 
     */
    public String getObjId(){
       return this.objId;
    }
	
	/**
	 * @param shopGoodsId the shopGoodsId to set
	 */
    public void setShopGoodsId(String shopGoodsId){
       this.shopGoodsId = shopGoodsId;
    }
    
    /**
     * @return the shopGoodsId 
     */
    public String getShopGoodsId(){
       return this.shopGoodsId;
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
	 * @param name the name to set
	 */
    public void setName(String name){
       this.name = name;
    }
    
    /**
     * @return the name 
     */
    public String getName(){
       return this.name;
    }
	
	/**
	 * @param cost the cost to set
	 */
    public void setCost(BigDecimal cost){
       this.cost = cost;
    }
    
    /**
     * @return the cost 
     */
    public BigDecimal getCost(){
       return this.cost;
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
	
	/**
	 * @param pmtPrice the pmtPrice to set
	 */
    public void setPmtPrice(BigDecimal pmtPrice){
       this.pmtPrice = pmtPrice;
    }
    
    /**
     * @return the pmtPrice 
     */
    public BigDecimal getPmtPrice(){
       return this.pmtPrice;
    }
	
	/**
	 * @param salePrice the salePrice to set
	 */
    public void setSalePrice(BigDecimal salePrice){
       this.salePrice = salePrice;
    }
    
    /**
     * @return the salePrice 
     */
    public BigDecimal getSalePrice(){
       return this.salePrice;
    }
	
	/**
	 * @param weight the weight to set
	 */
    public void setWeight(BigDecimal weight){
       this.weight = weight;
    }
    
    /**
     * @return the weight 
     */
    public BigDecimal getWeight(){
       return this.weight;
    }
	
	/**
	 * @param nums the nums to set
	 */
    public void setNums(String nums){
       this.nums = nums;
    }
    
    /**
     * @return the nums 
     */
    public String getNums(){
       return this.nums;
    }
	
	/**
	 * @param sendnum the sendnum to set
	 */
    public void setSendnum(String sendnum){
       this.sendnum = sendnum;
    }
    
    /**
     * @return the sendnum 
     */
    public String getSendnum(){
       return this.sendnum;
    }
	
	/**
	 * @param addon the addon to set
	 */
    public void setAddon(String addon){
       this.addon = addon;
    }
    
    /**
     * @return the addon 
     */
    public String getAddon(){
       return this.addon;
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
	 * @param score the score to set
	 */
    public void setScore(String score){
       this.score = score;
    }
    
    /**
     * @return the score 
     */
    public String getScore(){
       return this.score;
    }
	
	/**
	 * @param sellCode the sellCode to set
	 */
    public void setSellCode(String sellCode){
       this.sellCode = sellCode;
    }
    
    /**
     * @return the sellCode 
     */
    public String getSellCode(){
       return this.sellCode;
    }
	
	/**
	 * @param delete the delete to set
	 */
    public void setDelete(String delete){
       this.delete = delete;
    }
    
    /**
     * @return the delete 
     */
    public String getDelete(){
       return this.delete;
    }
}
