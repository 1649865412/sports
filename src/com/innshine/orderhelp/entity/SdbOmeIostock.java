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

 
public class SdbOmeIostock {
	 
    private String iostockId;
    
	public String getIostockId() {
		return iostockId;
	}

	public void setIostockId(String iostockId) {
		this.iostockId = iostockId;
	}

	/**
	 * 
	 */
    @Column(nullable=false, length=32)
    private String iostockBn;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String typeId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String branchId;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String originalBn;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String originalId;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String originalItemId;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String supplierId;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String supplierName;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=32)
    private String bn;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal iostockPrice;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal unitCost;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal inventoryCost;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal nowUnitCost;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal nowInventoryCost;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String nowNum;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String nums;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal costTax;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String oper;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String createTime;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String iotime;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String operator;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String settleMethod;
    
	/**
	 * 
	 */
    @Column(length=2)
    private String settleStatus;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String settleOperator;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String settleTime;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String settleNum;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String settlementBn;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal settlementMoney;
    
	/**
	 * 
	 */
    @Column(length=65535)
    private String memo;
     
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
	 * @param typeId the typeId to set
	 */
    public void setTypeId(String typeId){
       this.typeId = typeId;
    }
    
    /**
     * @return the typeId 
     */
    public String getTypeId(){
       return this.typeId;
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
	 * @param originalBn the originalBn to set
	 */
    public void setOriginalBn(String originalBn){
       this.originalBn = originalBn;
    }
    
    /**
     * @return the originalBn 
     */
    public String getOriginalBn(){
       return this.originalBn;
    }
	
	/**
	 * @param originalId the originalId to set
	 */
    public void setOriginalId(String originalId){
       this.originalId = originalId;
    }
    
    /**
     * @return the originalId 
     */
    public String getOriginalId(){
       return this.originalId;
    }
	
	/**
	 * @param originalItemId the originalItemId to set
	 */
    public void setOriginalItemId(String originalItemId){
       this.originalItemId = originalItemId;
    }
    
    /**
     * @return the originalItemId 
     */
    public String getOriginalItemId(){
       return this.originalItemId;
    }
	
	/**
	 * @param supplierId the supplierId to set
	 */
    public void setSupplierId(String supplierId){
       this.supplierId = supplierId;
    }
    
    /**
     * @return the supplierId 
     */
    public String getSupplierId(){
       return this.supplierId;
    }
	
	/**
	 * @param supplierName the supplierName to set
	 */
    public void setSupplierName(String supplierName){
       this.supplierName = supplierName;
    }
    
    /**
     * @return the supplierName 
     */
    public String getSupplierName(){
       return this.supplierName;
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
	 * @param iostockPrice the iostockPrice to set
	 */
    public void setIostockPrice(BigDecimal iostockPrice){
       this.iostockPrice = iostockPrice;
    }
    
    /**
     * @return the iostockPrice 
     */
    public BigDecimal getIostockPrice(){
       return this.iostockPrice;
    }
	
	/**
	 * @param unitCost the unitCost to set
	 */
    public void setUnitCost(BigDecimal unitCost){
       this.unitCost = unitCost;
    }
    
    /**
     * @return the unitCost 
     */
    public BigDecimal getUnitCost(){
       return this.unitCost;
    }
	
	/**
	 * @param inventoryCost the inventoryCost to set
	 */
    public void setInventoryCost(BigDecimal inventoryCost){
       this.inventoryCost = inventoryCost;
    }
    
    /**
     * @return the inventoryCost 
     */
    public BigDecimal getInventoryCost(){
       return this.inventoryCost;
    }
	
	/**
	 * @param nowUnitCost the nowUnitCost to set
	 */
    public void setNowUnitCost(BigDecimal nowUnitCost){
       this.nowUnitCost = nowUnitCost;
    }
    
    /**
     * @return the nowUnitCost 
     */
    public BigDecimal getNowUnitCost(){
       return this.nowUnitCost;
    }
	
	/**
	 * @param nowInventoryCost the nowInventoryCost to set
	 */
    public void setNowInventoryCost(BigDecimal nowInventoryCost){
       this.nowInventoryCost = nowInventoryCost;
    }
    
    /**
     * @return the nowInventoryCost 
     */
    public BigDecimal getNowInventoryCost(){
       return this.nowInventoryCost;
    }
	
	/**
	 * @param nowNum the nowNum to set
	 */
    public void setNowNum(String nowNum){
       this.nowNum = nowNum;
    }
    
    /**
     * @return the nowNum 
     */
    public String getNowNum(){
       return this.nowNum;
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
	 * @param costTax the costTax to set
	 */
    public void setCostTax(BigDecimal costTax){
       this.costTax = costTax;
    }
    
    /**
     * @return the costTax 
     */
    public BigDecimal getCostTax(){
       return this.costTax;
    }
	
	/**
	 * @param oper the oper to set
	 */
    public void setOper(String oper){
       this.oper = oper;
    }
    
    /**
     * @return the oper 
     */
    public String getOper(){
       return this.oper;
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
	 * @param iotime the iotime to set
	 */
    public void setIotime(String iotime){
       this.iotime = iotime;
    }
    
    /**
     * @return the iotime 
     */
    public String getIotime(){
       return this.iotime;
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
	 * @param settleMethod the settleMethod to set
	 */
    public void setSettleMethod(String settleMethod){
       this.settleMethod = settleMethod;
    }
    
    /**
     * @return the settleMethod 
     */
    public String getSettleMethod(){
       return this.settleMethod;
    }
	
	/**
	 * @param settleStatus the settleStatus to set
	 */
    public void setSettleStatus(String settleStatus){
       this.settleStatus = settleStatus;
    }
    
    /**
     * @return the settleStatus 
     */
    public String getSettleStatus(){
       return this.settleStatus;
    }
	
	/**
	 * @param settleOperator the settleOperator to set
	 */
    public void setSettleOperator(String settleOperator){
       this.settleOperator = settleOperator;
    }
    
    /**
     * @return the settleOperator 
     */
    public String getSettleOperator(){
       return this.settleOperator;
    }
	
	/**
	 * @param settleTime the settleTime to set
	 */
    public void setSettleTime(String settleTime){
       this.settleTime = settleTime;
    }
    
    /**
     * @return the settleTime 
     */
    public String getSettleTime(){
       return this.settleTime;
    }
	
	/**
	 * @param settleNum the settleNum to set
	 */
    public void setSettleNum(String settleNum){
       this.settleNum = settleNum;
    }
    
    /**
     * @return the settleNum 
     */
    public String getSettleNum(){
       return this.settleNum;
    }
	
	/**
	 * @param settlementBn the settlementBn to set
	 */
    public void setSettlementBn(String settlementBn){
       this.settlementBn = settlementBn;
    }
    
    /**
     * @return the settlementBn 
     */
    public String getSettlementBn(){
       return this.settlementBn;
    }
	
	/**
	 * @param settlementMoney the settlementMoney to set
	 */
    public void setSettlementMoney(BigDecimal settlementMoney){
       this.settlementMoney = settlementMoney;
    }
    
    /**
     * @return the settlementMoney 
     */
    public BigDecimal getSettlementMoney(){
       return this.settlementMoney;
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
}
