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
 
public class SdbOmeSalesItems{
 
    private String itemId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private String saleId;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String iostockId;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String shopId;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String shopName;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String orderId;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String orderBn;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String productId;
    
	/**
	 * 
	 */
    @Column(length=80)
    private String goodsCat;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String brandName;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=32)
    private String bn;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String name;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=7)
    private Long nums;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal costPrice;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal costAmount;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal grossSales;
    
	/**
	 * 
	 */
    @Column(length=10)
    private BigDecimal grossSalesRate;
    
	/**
	 * 
	 */
    @Column(length=20)
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
    private BigDecimal discountAmount;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal additionalCosts;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal salesPrice;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal salesAmount;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String branchId;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String branchName;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String salesTime;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String salesCode;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer salesType;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String createTime;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=3)
    private Integer status;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=3)
    private Integer actualStatus;
     
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @param saleId the saleId to set
	 */
    public void setSaleId(String saleId){
       this.saleId = saleId;
    }
    
    /**
     * @return the saleId 
     */
    public String getSaleId(){
       return this.saleId;
    }
	
	/**
	 * @param iostockId the iostockId to set
	 */
    public void setIostockId(String iostockId){
       this.iostockId = iostockId;
    }
    
    /**
     * @return the iostockId 
     */
    public String getIostockId(){
       return this.iostockId;
    }
	
	/**
	 * @param shopId the shopId to set
	 */
    public void setShopId(String shopId){
       this.shopId = shopId;
    }
    
    /**
     * @return the shopId 
     */
    public String getShopId(){
       return this.shopId;
    }
	
	/**
	 * @param shopName the shopName to set
	 */
    public void setShopName(String shopName){
       this.shopName = shopName;
    }
    
    /**
     * @return the shopName 
     */
    public String getShopName(){
       return this.shopName;
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
	 * @param orderBn the orderBn to set
	 */
    public void setOrderBn(String orderBn){
       this.orderBn = orderBn;
    }
    
    /**
     * @return the orderBn 
     */
    public String getOrderBn(){
       return this.orderBn;
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
	 * @param goodsCat the goodsCat to set
	 */
    public void setGoodsCat(String goodsCat){
       this.goodsCat = goodsCat;
    }
    
    /**
     * @return the goodsCat 
     */
    public String getGoodsCat(){
       return this.goodsCat;
    }
	
	/**
	 * @param brandName the brandName to set
	 */
    public void setBrandName(String brandName){
       this.brandName = brandName;
    }
    
    /**
     * @return the brandName 
     */
    public String getBrandName(){
       return this.brandName;
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
	 * @param nums the nums to set
	 */
    public void setNums(Long nums){
       this.nums = nums;
    }
    
    /**
     * @return the nums 
     */
    public Long getNums(){
       return this.nums;
    }
	
	/**
	 * @param costPrice the costPrice to set
	 */
    public void setCostPrice(BigDecimal costPrice){
       this.costPrice = costPrice;
    }
    
    /**
     * @return the costPrice 
     */
    public BigDecimal getCostPrice(){
       return this.costPrice;
    }
	
	/**
	 * @param costAmount the costAmount to set
	 */
    public void setCostAmount(BigDecimal costAmount){
       this.costAmount = costAmount;
    }
    
    /**
     * @return the costAmount 
     */
    public BigDecimal getCostAmount(){
       return this.costAmount;
    }
	
	/**
	 * @param grossSales the grossSales to set
	 */
    public void setGrossSales(BigDecimal grossSales){
       this.grossSales = grossSales;
    }
    
    /**
     * @return the grossSales 
     */
    public BigDecimal getGrossSales(){
       return this.grossSales;
    }
	
	/**
	 * @param grossSalesRate the grossSalesRate to set
	 */
    public void setGrossSalesRate(BigDecimal grossSalesRate){
       this.grossSalesRate = grossSalesRate;
    }
    
    /**
     * @return the grossSalesRate 
     */
    public BigDecimal getGrossSalesRate(){
       return this.grossSalesRate;
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
	 * @param discountAmount the discountAmount to set
	 */
    public void setDiscountAmount(BigDecimal discountAmount){
       this.discountAmount = discountAmount;
    }
    
    /**
     * @return the discountAmount 
     */
    public BigDecimal getDiscountAmount(){
       return this.discountAmount;
    }
	
	/**
	 * @param additionalCosts the additionalCosts to set
	 */
    public void setAdditionalCosts(BigDecimal additionalCosts){
       this.additionalCosts = additionalCosts;
    }
    
    /**
     * @return the additionalCosts 
     */
    public BigDecimal getAdditionalCosts(){
       return this.additionalCosts;
    }
	
	/**
	 * @param salesPrice the salesPrice to set
	 */
    public void setSalesPrice(BigDecimal salesPrice){
       this.salesPrice = salesPrice;
    }
    
    /**
     * @return the salesPrice 
     */
    public BigDecimal getSalesPrice(){
       return this.salesPrice;
    }
	
	/**
	 * @param salesAmount the salesAmount to set
	 */
    public void setSalesAmount(BigDecimal salesAmount){
       this.salesAmount = salesAmount;
    }
    
    /**
     * @return the salesAmount 
     */
    public BigDecimal getSalesAmount(){
       return this.salesAmount;
    }
	
	/**
	 * @param branchId the branchId to set
	 */
    public void setBranchId(String branchId){
       this.branchId = branchId;
    }
    
    /**
     * @return the branchId 
     */
    public String getBranchId(){
       return this.branchId;
    }
	
	/**
	 * @param branchName the branchName to set
	 */
    public void setBranchName(String branchName){
       this.branchName = branchName;
    }
    
    /**
     * @return the branchName 
     */
    public String getBranchName(){
       return this.branchName;
    }
	
	/**
	 * @param salesTime the salesTime to set
	 */
    public void setSalesTime(String salesTime){
       this.salesTime = salesTime;
    }
    
    /**
     * @return the salesTime 
     */
    public String getSalesTime(){
       return this.salesTime;
    }
	
	/**
	 * @param salesCode the salesCode to set
	 */
    public void setSalesCode(String salesCode){
       this.salesCode = salesCode;
    }
    
    /**
     * @return the salesCode 
     */
    public String getSalesCode(){
       return this.salesCode;
    }
	
	/**
	 * @param salesType the salesType to set
	 */
    public void setSalesType(Integer salesType){
       this.salesType = salesType;
    }
    
    /**
     * @return the salesType 
     */
    public Integer getSalesType(){
       return this.salesType;
    }
	
	/**
	 * @param createTime the createTime to set
	 */
    public void setCreateTime(String createTime){
       this.createTime = createTime;
    }
    
    /**
     * @return the createTime 
     */
    public String getCreateTime(){
       return this.createTime;
    }
	
	/**
	 * @param status the status to set
	 */
    public void setStatus(Integer status){
       this.status = status;
    }
    
    /**
     * @return the status 
     */
    public Integer getStatus(){
       return this.status;
    }
	
	/**
	 * @param actualStatus the actualStatus to set
	 */
    public void setActualStatus(Integer actualStatus){
       this.actualStatus = actualStatus;
    }
    
    /**
     * @return the actualStatus 
     */
    public Integer getActualStatus(){
       return this.actualStatus;
    }
}
