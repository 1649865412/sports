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
 
public class SdbOmeSales{
 
    private String saleId;
    
	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	/**
	 * 
	 */
    @Column(nullable=false, length=32)
    private String saleBn;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=32)
    private String iostockBn;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String saleTime;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal saleAmount;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal cost;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal goodsAmount;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal goodsDiscount;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal deliveryCost;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal additionalCosts;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal deposit;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal discount;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String operator;
    
	/**
	 * 
	 */
    @Column(length=65535)
    private String memo;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String memberId;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String branchId;
    
	/**
	 * 
	 */
    @Column(length=2)
    private String payStatus;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String shopId;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String createTime;
    
    
	/**
	 * @param saleBn the saleBn to set
	 */
    public void setSaleBn(String saleBn){
       this.saleBn = saleBn;
    }
    
    /**
     * @return the saleBn 
     */
    public String getSaleBn(){
       return this.saleBn;
    }
	
	/**
	 * @param iostockBn the iostockBn to set
	 */
    public void setIostockBn(String iostockBn){
       this.iostockBn = iostockBn;
    }
    
    /**
     * @return the iostockBn 
     */
    public String getIostockBn(){
       return this.iostockBn;
    }
	
	/**
	 * @param saleTime the saleTime to set
	 */
    public void setSaleTime(String saleTime){
       this.saleTime = saleTime;
    }
    
    /**
     * @return the saleTime 
     */
    public String getSaleTime(){
       return this.saleTime;
    }
	
	/**
	 * @param saleAmount the saleAmount to set
	 */
    public void setSaleAmount(BigDecimal saleAmount){
       this.saleAmount = saleAmount;
    }
    
    /**
     * @return the saleAmount 
     */
    public BigDecimal getSaleAmount(){
       return this.saleAmount;
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
	 * @param goodsAmount the goodsAmount to set
	 */
    public void setGoodsAmount(BigDecimal goodsAmount){
       this.goodsAmount = goodsAmount;
    }
    
    /**
     * @return the goodsAmount 
     */
    public BigDecimal getGoodsAmount(){
       return this.goodsAmount;
    }
	
	/**
	 * @param goodsDiscount the goodsDiscount to set
	 */
    public void setGoodsDiscount(BigDecimal goodsDiscount){
       this.goodsDiscount = goodsDiscount;
    }
    
    /**
     * @return the goodsDiscount 
     */
    public BigDecimal getGoodsDiscount(){
       return this.goodsDiscount;
    }
	
	/**
	 * @param deliveryCost the deliveryCost to set
	 */
    public void setDeliveryCost(BigDecimal deliveryCost){
       this.deliveryCost = deliveryCost;
    }
    
    /**
     * @return the deliveryCost 
     */
    public BigDecimal getDeliveryCost(){
       return this.deliveryCost;
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
	 * @param deposit the deposit to set
	 */
    public void setDeposit(BigDecimal deposit){
       this.deposit = deposit;
    }
    
    /**
     * @return the deposit 
     */
    public BigDecimal getDeposit(){
       return this.deposit;
    }
	
	/**
	 * @param discount the discount to set
	 */
    public void setDiscount(BigDecimal discount){
       this.discount = discount;
    }
    
    /**
     * @return the discount 
     */
    public BigDecimal getDiscount(){
       return this.discount;
    }
	
	/**
	 * @param operator the operator to set
	 */
    public void setOperator(String operator){
       this.operator = operator;
    }
    
    /**
     * @return the operator 
     */
    public String getOperator(){
       return this.operator;
    }
	
	/**
	 * @param memo the memo to set
	 */
    public void setMemo(String memo){
       this.memo = memo;
    }
    
    /**
     * @return the memo 
     */
    public String getMemo(){
       return this.memo;
    }
	
	/**
	 * @param memberId the memberId to set
	 */
    public void setMemberId(String memberId){
       this.memberId = memberId;
    }
    
    /**
     * @return the memberId 
     */
    public String getMemberId(){
       return this.memberId;
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
	 * @param payStatus the payStatus to set
	 */
    public void setPayStatus(String payStatus){
       this.payStatus = payStatus;
    }
    
    /**
     * @return the payStatus 
     */
    public String getPayStatus(){
       return this.payStatus;
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
}
