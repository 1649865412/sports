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

 
public class SdbOmeOrders {
	 
    private String orderId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=32)
    private String orderBn;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String crc32OrderBn;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String relateOrderBn;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String memberId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=2)
    private String confirm;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=2)
    private String confirmAllow;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=13)
    private String processStatus;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=7)
    private String status;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=2)
    private String payStatus;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String payTime;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=2)
    private String shipStatus;
    
	/**
	 * 
	 */
    @Column(length=3)
    private Integer stockShortage;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=2)
    private String isDelivery;
    
	/**
	 * 
	 */
    @Column(length=100)
    private String shipping;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String payBn;
    
	/**
	 * 
	 */
    @Column(length=100)
    private String payment;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal weight;
    
	/**
	 * 
	 */
    @Column(length=2147483647)
    private String tostr;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String itemnum;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String createtime;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String downloadTime;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String finishTime;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String lastModified;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String outerLastmodify;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String shopId;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String shopType;
    
	/**
	 * 
	 */
    @Column(length=15)
    private String ip;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String shipName;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String shipArea;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String shipAddr;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String shipZip;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String shipTel;
    
	/**
	 * 
	 */
    @Column(length=150)
    private String shipEmail;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String shipTime;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String shipMobile;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String consignerName;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String consignerArea;
    
	/**
	 * 
	 */
    @Column(length=100)
    private String consignerAddr;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String consignerZip;
    
	/**
	 * 
	 */
    @Column(length=150)
    private String consignerEmail;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String consignerMobile;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String consignerTel;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal costItem;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isTax;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal costTax;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String taxCompany;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal costFreight;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isProtect;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal costProtect;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isCod;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isFail;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String editStatus;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal costPayment;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String currency;
    
	/**
	 * 
	 */
    @Column(length=10)
    private BigDecimal curRate;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal scoreU;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal scoreG;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal discount;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal pmtGoods;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal pmtOrder;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal totalAmount;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=20)
    private BigDecimal finalAmount;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal payed;
    
	/**
	 * 
	 */
    @Column(length=2147483647)
    private String customMark;
    
	/**
	 * 
	 */
    @Column(length=2147483647)
    private String markText;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String disabled;
    
	/**
	 * 
	 */
    @Column(length=3)
    private String markType;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String taxNo;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String dtBegin;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isAnti;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String groupId;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String opId;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String dispatchTime;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String orderLimitTime;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String couponsName;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String referId;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String referUrl;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String referTime;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String cReferId;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String cReferUrl;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String cReferTime;
    
	/**
	 * 
	 */
    @Column(length=5)
    private String abnormal;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String printFinish;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String source;
    
	/**
	 * 
	 */
    @Column(length=5)
    private String pause;
    
	/**
	 * 
	 */
    @Column(length=5)
    private String isModify;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal oldAmount;
    
	/**
	 * 
	 */
    @Column(length=9)
    private String orderType;
    
	/**
	 * 
	 */
    @Column(length=7)
    private String tradeType;
    
	/**
	 * 
	 */
    @Column(length=16)
    private String orderJobNo;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isAuto;
    
	/**
	 * 
	 */
    @Column(length=5)
    private String isMergeDelivery;
    
	/**
	 * 
	 */
    @Column(length=9)
    private String controlStatus;
    
	/**
	 * 
	 */
    @Column(length=9)
    private String dispatchStatus;
    
	/**
	 * 
	 */
    @Column(length=7)
    private String syncStatus;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String autoconfirmErrorType;
    
	/**
	 * 
	 */
    @Column(length=255)
    private String autoconfirmErrorMsg;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isOversold;
    
    /**
	 * @return the id
	 */
	public String getOrderId() {
		return orderId;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	 * @param crc32OrderBn the crc32OrderBn to set
	 */
    public void setCrc32OrderBn(String crc32OrderBn){
       this.crc32OrderBn = crc32OrderBn;
    }
    
    /**
     * @return the crc32OrderBn 
     */
    public String getCrc32OrderBn(){
       return this.crc32OrderBn;
    }
	
	/**
	 * @param relateOrderBn the relateOrderBn to set
	 */
    public void setRelateOrderBn(String relateOrderBn){
       this.relateOrderBn = relateOrderBn;
    }
    
    /**
     * @return the relateOrderBn 
     */
    public String getRelateOrderBn(){
       return this.relateOrderBn;
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
	 * @param confirm the confirm to set
	 */
    public void setConfirm(String confirm){
       this.confirm = confirm;
    }
    
    /**
     * @return the confirm 
     */
    public String getConfirm(){
       return this.confirm;
    }
	
	/**
	 * @param confirmAllow the confirmAllow to set
	 */
    public void setConfirmAllow(String confirmAllow){
       this.confirmAllow = confirmAllow;
    }
    
    /**
     * @return the confirmAllow 
     */
    public String getConfirmAllow(){
       return this.confirmAllow;
    }
	
	/**
	 * @param processStatus the processStatus to set
	 */
    public void setProcessStatus(String processStatus){
       this.processStatus = processStatus;
    }
    
    /**
     * @return the processStatus 
     */
    public String getProcessStatus(){
       return this.processStatus;
    }
	
	/**
	 * @param status the status to set
	 */
    public void setStatus(String status){
       this.status = status;
    }
    
    /**
     * @return the status 
     */
    public String getStatus(){
       return this.status;
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
	 * @param payTime the payTime to set
	 */
    public void setPayTime(String payTime){
       this.payTime = payTime;
    }
    
    /**
     * @return the payTime 
     */
    public String getPayTime(){
       return this.payTime;
    }
	
	/**
	 * @param shipStatus the shipStatus to set
	 */
    public void setShipStatus(String shipStatus){
       this.shipStatus = shipStatus;
    }
    
    /**
     * @return the shipStatus 
     */
    public String getShipStatus(){
       return this.shipStatus;
    }
	
	/**
	 * @param stockShortage the stockShortage to set
	 */
    public void setStockShortage(Integer stockShortage){
       this.stockShortage = stockShortage;
    }
    
    /**
     * @return the stockShortage 
     */
    public Integer getStockShortage(){
       return this.stockShortage;
    }
	
	/**
	 * @param isDelivery the isDelivery to set
	 */
    public void setIsDelivery(String isDelivery){
       this.isDelivery = isDelivery;
    }
    
    /**
     * @return the isDelivery 
     */
    public String getIsDelivery(){
       return this.isDelivery;
    }
	
	/**
	 * @param shipping the shipping to set
	 */
    public void setShipping(String shipping){
       this.shipping = shipping;
    }
    
    /**
     * @return the shipping 
     */
    public String getShipping(){
       return this.shipping;
    }
	
	/**
	 * @param payBn the payBn to set
	 */
    public void setPayBn(String payBn){
       this.payBn = payBn;
    }
    
    /**
     * @return the payBn 
     */
    public String getPayBn(){
       return this.payBn;
    }
	
	/**
	 * @param payment the payment to set
	 */
    public void setPayment(String payment){
       this.payment = payment;
    }
    
    /**
     * @return the payment 
     */
    public String getPayment(){
       return this.payment;
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
	 * @param tostr the tostr to set
	 */
    public void setTostr(String tostr){
       this.tostr = tostr;
    }
    
    /**
     * @return the tostr 
     */
    public String getTostr(){
       return this.tostr;
    }
	
	/**
	 * @param itemnum the itemnum to set
	 */
    public void setItemnum(String itemnum){
       this.itemnum = itemnum;
    }
    
    /**
     * @return the itemnum 
     */
    public String getItemnum(){
       return this.itemnum;
    }
	
	/**
	 * @param createtime the createtime to set
	 */
    public void setCreatetime(String createtime){
       this.createtime = createtime;
    }
    
    /**
     * @return the createtime 
     */
    public String getCreatetime(){
       return this.createtime;
    }
	
	/**
	 * @param downloadTime the downloadTime to set
	 */
    public void setDownloadTime(String downloadTime){
       this.downloadTime = downloadTime;
    }
    
    /**
     * @return the downloadTime 
     */
    public String getDownloadTime(){
       return this.downloadTime;
    }
	
	/**
	 * @param finishTime the finishTime to set
	 */
    public void setFinishTime(String finishTime){
       this.finishTime = finishTime;
    }
    
    /**
     * @return the finishTime 
     */
    public String getFinishTime(){
       return this.finishTime;
    }
	
	/**
	 * @param lastModified the lastModified to set
	 */
    public void setLastModified(String lastModified){
       this.lastModified = lastModified;
    }
    
    /**
     * @return the lastModified 
     */
    public String getLastModified(){
       return this.lastModified;
    }
	
	/**
	 * @param outerLastmodify the outerLastmodify to set
	 */
    public void setOuterLastmodify(String outerLastmodify){
       this.outerLastmodify = outerLastmodify;
    }
    
    /**
     * @return the outerLastmodify 
     */
    public String getOuterLastmodify(){
       return this.outerLastmodify;
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
	 * @param shopType the shopType to set
	 */
    public void setShopType(String shopType){
       this.shopType = shopType;
    }
    
    /**
     * @return the shopType 
     */
    public String getShopType(){
       return this.shopType;
    }
	
	/**
	 * @param ip the ip to set
	 */
    public void setIp(String ip){
       this.ip = ip;
    }
    
    /**
     * @return the ip 
     */
    public String getIp(){
       return this.ip;
    }
	
	/**
	 * @param shipName the shipName to set
	 */
    public void setShipName(String shipName){
       this.shipName = shipName;
    }
    
    /**
     * @return the shipName 
     */
    public String getShipName(){
       return this.shipName;
    }
	
	/**
	 * @param shipArea the shipArea to set
	 */
    public void setShipArea(String shipArea){
       this.shipArea = shipArea;
    }
    
    /**
     * @return the shipArea 
     */
    public String getShipArea(){
       return this.shipArea;
    }
	
	/**
	 * @param shipAddr the shipAddr to set
	 */
    public void setShipAddr(String shipAddr){
       this.shipAddr = shipAddr;
    }
    
    /**
     * @return the shipAddr 
     */
    public String getShipAddr(){
       return this.shipAddr;
    }
	
	/**
	 * @param shipZip the shipZip to set
	 */
    public void setShipZip(String shipZip){
       this.shipZip = shipZip;
    }
    
    /**
     * @return the shipZip 
     */
    public String getShipZip(){
       return this.shipZip;
    }
	
	/**
	 * @param shipTel the shipTel to set
	 */
    public void setShipTel(String shipTel){
       this.shipTel = shipTel;
    }
    
    /**
     * @return the shipTel 
     */
    public String getShipTel(){
       return this.shipTel;
    }
	
	/**
	 * @param shipEmail the shipEmail to set
	 */
    public void setShipEmail(String shipEmail){
       this.shipEmail = shipEmail;
    }
    
    /**
     * @return the shipEmail 
     */
    public String getShipEmail(){
       return this.shipEmail;
    }
	
	/**
	 * @param shipTime the shipTime to set
	 */
    public void setShipTime(String shipTime){
       this.shipTime = shipTime;
    }
    
    /**
     * @return the shipTime 
     */
    public String getShipTime(){
       return this.shipTime;
    }
	
	/**
	 * @param shipMobile the shipMobile to set
	 */
    public void setShipMobile(String shipMobile){
       this.shipMobile = shipMobile;
    }
    
    /**
     * @return the shipMobile 
     */
    public String getShipMobile(){
       return this.shipMobile;
    }
	
	/**
	 * @param consignerName the consignerName to set
	 */
    public void setConsignerName(String consignerName){
       this.consignerName = consignerName;
    }
    
    /**
     * @return the consignerName 
     */
    public String getConsignerName(){
       return this.consignerName;
    }
	
	/**
	 * @param consignerArea the consignerArea to set
	 */
    public void setConsignerArea(String consignerArea){
       this.consignerArea = consignerArea;
    }
    
    /**
     * @return the consignerArea 
     */
    public String getConsignerArea(){
       return this.consignerArea;
    }
	
	/**
	 * @param consignerAddr the consignerAddr to set
	 */
    public void setConsignerAddr(String consignerAddr){
       this.consignerAddr = consignerAddr;
    }
    
    /**
     * @return the consignerAddr 
     */
    public String getConsignerAddr(){
       return this.consignerAddr;
    }
	
	/**
	 * @param consignerZip the consignerZip to set
	 */
    public void setConsignerZip(String consignerZip){
       this.consignerZip = consignerZip;
    }
    
    /**
     * @return the consignerZip 
     */
    public String getConsignerZip(){
       return this.consignerZip;
    }
	
	/**
	 * @param consignerEmail the consignerEmail to set
	 */
    public void setConsignerEmail(String consignerEmail){
       this.consignerEmail = consignerEmail;
    }
    
    /**
     * @return the consignerEmail 
     */
    public String getConsignerEmail(){
       return this.consignerEmail;
    }
	
	/**
	 * @param consignerMobile the consignerMobile to set
	 */
    public void setConsignerMobile(String consignerMobile){
       this.consignerMobile = consignerMobile;
    }
    
    /**
     * @return the consignerMobile 
     */
    public String getConsignerMobile(){
       return this.consignerMobile;
    }
	
	/**
	 * @param consignerTel the consignerTel to set
	 */
    public void setConsignerTel(String consignerTel){
       this.consignerTel = consignerTel;
    }
    
    /**
     * @return the consignerTel 
     */
    public String getConsignerTel(){
       return this.consignerTel;
    }
	
	/**
	 * @param costItem the costItem to set
	 */
    public void setCostItem(BigDecimal costItem){
       this.costItem = costItem;
    }
    
    /**
     * @return the costItem 
     */
    public BigDecimal getCostItem(){
       return this.costItem;
    }
	
	/**
	 * @param isTax the isTax to set
	 */
    public void setIsTax(String isTax){
       this.isTax = isTax;
    }
    
    /**
     * @return the isTax 
     */
    public String getIsTax(){
       return this.isTax;
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
	 * @param taxCompany the taxCompany to set
	 */
    public void setTaxCompany(String taxCompany){
       this.taxCompany = taxCompany;
    }
    
    /**
     * @return the taxCompany 
     */
    public String getTaxCompany(){
       return this.taxCompany;
    }
	
	/**
	 * @param costFreight the costFreight to set
	 */
    public void setCostFreight(BigDecimal costFreight){
       this.costFreight = costFreight;
    }
    
    /**
     * @return the costFreight 
     */
    public BigDecimal getCostFreight(){
       return this.costFreight;
    }
	
	/**
	 * @param isProtect the isProtect to set
	 */
    public void setIsProtect(String isProtect){
       this.isProtect = isProtect;
    }
    
    /**
     * @return the isProtect 
     */
    public String getIsProtect(){
       return this.isProtect;
    }
	
	/**
	 * @param costProtect the costProtect to set
	 */
    public void setCostProtect(BigDecimal costProtect){
       this.costProtect = costProtect;
    }
    
    /**
     * @return the costProtect 
     */
    public BigDecimal getCostProtect(){
       return this.costProtect;
    }
	
	/**
	 * @param isCod the isCod to set
	 */
    public void setIsCod(String isCod){
       this.isCod = isCod;
    }
    
    /**
     * @return the isCod 
     */
    public String getIsCod(){
       return this.isCod;
    }
	
	/**
	 * @param isFail the isFail to set
	 */
    public void setIsFail(String isFail){
       this.isFail = isFail;
    }
    
    /**
     * @return the isFail 
     */
    public String getIsFail(){
       return this.isFail;
    }
	
	/**
	 * @param editStatus the editStatus to set
	 */
    public void setEditStatus(String editStatus){
       this.editStatus = editStatus;
    }
    
    /**
     * @return the editStatus 
     */
    public String getEditStatus(){
       return this.editStatus;
    }
	
	/**
	 * @param costPayment the costPayment to set
	 */
    public void setCostPayment(BigDecimal costPayment){
       this.costPayment = costPayment;
    }
    
    /**
     * @return the costPayment 
     */
    public BigDecimal getCostPayment(){
       return this.costPayment;
    }
	
	/**
	 * @param currency the currency to set
	 */
    public void setCurrency(String currency){
       this.currency = currency;
    }
    
    /**
     * @return the currency 
     */
    public String getCurrency(){
       return this.currency;
    }
	
	/**
	 * @param curRate the curRate to set
	 */
    public void setCurRate(BigDecimal curRate){
       this.curRate = curRate;
    }
    
    /**
     * @return the curRate 
     */
    public BigDecimal getCurRate(){
       return this.curRate;
    }
	
	/**
	 * @param scoreU the scoreU to set
	 */
    public void setScoreU(BigDecimal scoreU){
       this.scoreU = scoreU;
    }
    
    /**
     * @return the scoreU 
     */
    public BigDecimal getScoreU(){
       return this.scoreU;
    }
	
	/**
	 * @param scoreG the scoreG to set
	 */
    public void setScoreG(BigDecimal scoreG){
       this.scoreG = scoreG;
    }
    
    /**
     * @return the scoreG 
     */
    public BigDecimal getScoreG(){
       return this.scoreG;
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
	 * @param pmtGoods the pmtGoods to set
	 */
    public void setPmtGoods(BigDecimal pmtGoods){
       this.pmtGoods = pmtGoods;
    }
    
    /**
     * @return the pmtGoods 
     */
    public BigDecimal getPmtGoods(){
       return this.pmtGoods;
    }
	
	/**
	 * @param pmtOrder the pmtOrder to set
	 */
    public void setPmtOrder(BigDecimal pmtOrder){
       this.pmtOrder = pmtOrder;
    }
    
    /**
     * @return the pmtOrder 
     */
    public BigDecimal getPmtOrder(){
       return this.pmtOrder;
    }
	
	/**
	 * @param totalAmount the totalAmount to set
	 */
    public void setTotalAmount(BigDecimal totalAmount){
       this.totalAmount = totalAmount;
    }
    
    /**
     * @return the totalAmount 
     */
    public BigDecimal getTotalAmount(){
       return this.totalAmount;
    }
	
	/**
	 * @param finalAmount the finalAmount to set
	 */
    public void setFinalAmount(BigDecimal finalAmount){
       this.finalAmount = finalAmount;
    }
    
    /**
     * @return the finalAmount 
     */
    public BigDecimal getFinalAmount(){
       return this.finalAmount;
    }
	
	/**
	 * @param payed the payed to set
	 */
    public void setPayed(BigDecimal payed){
       this.payed = payed;
    }
    
    /**
     * @return the payed 
     */
    public BigDecimal getPayed(){
       return this.payed;
    }
	
	/**
	 * @param customMark the customMark to set
	 */
    public void setCustomMark(String customMark){
       this.customMark = customMark;
    }
    
    /**
     * @return the customMark 
     */
    public String getCustomMark(){
       return this.customMark;
    }
	
	/**
	 * @param markText the markText to set
	 */
    public void setMarkText(String markText){
       this.markText = markText;
    }
    
    /**
     * @return the markText 
     */
    public String getMarkText(){
       return this.markText;
    }
	
	/**
	 * @param disabled the disabled to set
	 */
    public void setDisabled(String disabled){
       this.disabled = disabled;
    }
    
    /**
     * @return the disabled 
     */
    public String getDisabled(){
       return this.disabled;
    }
	
	/**
	 * @param markType the markType to set
	 */
    public void setMarkType(String markType){
       this.markType = markType;
    }
    
    /**
     * @return the markType 
     */
    public String getMarkType(){
       return this.markType;
    }
	
	/**
	 * @param taxNo the taxNo to set
	 */
    public void setTaxNo(String taxNo){
       this.taxNo = taxNo;
    }
    
    /**
     * @return the taxNo 
     */
    public String getTaxNo(){
       return this.taxNo;
    }
	
	/**
	 * @param dtBegin the dtBegin to set
	 */
    public void setDtBegin(String dtBegin){
       this.dtBegin = dtBegin;
    }
    
    /**
     * @return the dtBegin 
     */
    public String getDtBegin(){
       return this.dtBegin;
    }
	
	/**
	 * @param isAnti the isAnti to set
	 */
    public void setIsAnti(String isAnti){
       this.isAnti = isAnti;
    }
    
    /**
     * @return the isAnti 
     */
    public String getIsAnti(){
       return this.isAnti;
    }
	
	/**
	 * @param groupId the groupId to set
	 */
    public void setGroupId(String groupId){
       this.groupId = groupId;
    }
    
    /**
     * @return the groupId 
     */
    public String getGroupId(){
       return this.groupId;
    }
	
	/**
	 * @param opId the opId to set
	 */
    public void setOpId(String opId){
       this.opId = opId;
    }
    
    /**
     * @return the opId 
     */
    public String getOpId(){
       return this.opId;
    }
	
	/**
	 * @param dispatchTime the dispatchTime to set
	 */
    public void setDispatchTime(String dispatchTime){
       this.dispatchTime = dispatchTime;
    }
    
    /**
     * @return the dispatchTime 
     */
    public String getDispatchTime(){
       return this.dispatchTime;
    }
	
	/**
	 * @param orderLimitTime the orderLimitTime to set
	 */
    public void setOrderLimitTime(String orderLimitTime){
       this.orderLimitTime = orderLimitTime;
    }
    
    /**
     * @return the orderLimitTime 
     */
    public String getOrderLimitTime(){
       return this.orderLimitTime;
    }
	
	/**
	 * @param couponsName the couponsName to set
	 */
    public void setCouponsName(String couponsName){
       this.couponsName = couponsName;
    }
    
    /**
     * @return the couponsName 
     */
    public String getCouponsName(){
       return this.couponsName;
    }
	
	/**
	 * @param referId the referId to set
	 */
    public void setReferId(String referId){
       this.referId = referId;
    }
    
    /**
     * @return the referId 
     */
    public String getReferId(){
       return this.referId;
    }
	
	/**
	 * @param referUrl the referUrl to set
	 */
    public void setReferUrl(String referUrl){
       this.referUrl = referUrl;
    }
    
    /**
     * @return the referUrl 
     */
    public String getReferUrl(){
       return this.referUrl;
    }
	
	/**
	 * @param referTime the referTime to set
	 */
    public void setReferTime(String referTime){
       this.referTime = referTime;
    }
    
    /**
     * @return the referTime 
     */
    public String getReferTime(){
       return this.referTime;
    }
	
	/**
	 * @param cReferId the cReferId to set
	 */
    public void setCReferId(String cReferId){
       this.cReferId = cReferId;
    }
    
    /**
     * @return the cReferId 
     */
    public String getCReferId(){
       return this.cReferId;
    }
	
	/**
	 * @param cReferUrl the cReferUrl to set
	 */
    public void setCReferUrl(String cReferUrl){
       this.cReferUrl = cReferUrl;
    }
    
    /**
     * @return the cReferUrl 
     */
    public String getCReferUrl(){
       return this.cReferUrl;
    }
	
	/**
	 * @param cReferTime the cReferTime to set
	 */
    public void setCReferTime(String cReferTime){
       this.cReferTime = cReferTime;
    }
    
    /**
     * @return the cReferTime 
     */
    public String getCReferTime(){
       return this.cReferTime;
    }
	
	/**
	 * @param abnormal the abnormal to set
	 */
    public void setAbnormal(String abnormal){
       this.abnormal = abnormal;
    }
    
    /**
     * @return the abnormal 
     */
    public String getAbnormal(){
       return this.abnormal;
    }
	
	/**
	 * @param printFinish the printFinish to set
	 */
    public void setPrintFinish(String printFinish){
       this.printFinish = printFinish;
    }
    
    /**
     * @return the printFinish 
     */
    public String getPrintFinish(){
       return this.printFinish;
    }
	
	/**
	 * @param source the source to set
	 */
    public void setSource(String source){
       this.source = source;
    }
    
    /**
     * @return the source 
     */
    public String getSource(){
       return this.source;
    }
	
	/**
	 * @param pause the pause to set
	 */
    public void setPause(String pause){
       this.pause = pause;
    }
    
    /**
     * @return the pause 
     */
    public String getPause(){
       return this.pause;
    }
	
	/**
	 * @param isModify the isModify to set
	 */
    public void setIsModify(String isModify){
       this.isModify = isModify;
    }
    
    /**
     * @return the isModify 
     */
    public String getIsModify(){
       return this.isModify;
    }
	
	/**
	 * @param oldAmount the oldAmount to set
	 */
    public void setOldAmount(BigDecimal oldAmount){
       this.oldAmount = oldAmount;
    }
    
    /**
     * @return the oldAmount 
     */
    public BigDecimal getOldAmount(){
       return this.oldAmount;
    }
	
	/**
	 * @param orderType the orderType to set
	 */
    public void setOrderType(String orderType){
       this.orderType = orderType;
    }
    
    /**
     * @return the orderType 
     */
    public String getOrderType(){
       return this.orderType;
    }
	
	/**
	 * @param tradeType the tradeType to set
	 */
    public void setTradeType(String tradeType){
       this.tradeType = tradeType;
    }
    
    /**
     * @return the tradeType 
     */
    public String getTradeType(){
       return this.tradeType;
    }
	
	/**
	 * @param orderJobNo the orderJobNo to set
	 */
    public void setOrderJobNo(String orderJobNo){
       this.orderJobNo = orderJobNo;
    }
    
    /**
     * @return the orderJobNo 
     */
    public String getOrderJobNo(){
       return this.orderJobNo;
    }
	
	/**
	 * @param isAuto the isAuto to set
	 */
    public void setIsAuto(String isAuto){
       this.isAuto = isAuto;
    }
    
    /**
     * @return the isAuto 
     */
    public String getIsAuto(){
       return this.isAuto;
    }
	
	/**
	 * @param isMergeDelivery the isMergeDelivery to set
	 */
    public void setIsMergeDelivery(String isMergeDelivery){
       this.isMergeDelivery = isMergeDelivery;
    }
    
    /**
     * @return the isMergeDelivery 
     */
    public String getIsMergeDelivery(){
       return this.isMergeDelivery;
    }
	
	/**
	 * @param controlStatus the controlStatus to set
	 */
    public void setControlStatus(String controlStatus){
       this.controlStatus = controlStatus;
    }
    
    /**
     * @return the controlStatus 
     */
    public String getControlStatus(){
       return this.controlStatus;
    }
	
	/**
	 * @param dispatchStatus the dispatchStatus to set
	 */
    public void setDispatchStatus(String dispatchStatus){
       this.dispatchStatus = dispatchStatus;
    }
    
    /**
     * @return the dispatchStatus 
     */
    public String getDispatchStatus(){
       return this.dispatchStatus;
    }
	
	/**
	 * @param syncStatus the syncStatus to set
	 */
    public void setSyncStatus(String syncStatus){
       this.syncStatus = syncStatus;
    }
    
    /**
     * @return the syncStatus 
     */
    public String getSyncStatus(){
       return this.syncStatus;
    }
	
	/**
	 * @param autoconfirmErrorType the autoconfirmErrorType to set
	 */
    public void setAutoconfirmErrorType(String autoconfirmErrorType){
       this.autoconfirmErrorType = autoconfirmErrorType;
    }
    
    /**
     * @return the autoconfirmErrorType 
     */
    public String getAutoconfirmErrorType(){
       return this.autoconfirmErrorType;
    }
	
	/**
	 * @param autoconfirmErrorMsg the autoconfirmErrorMsg to set
	 */
    public void setAutoconfirmErrorMsg(String autoconfirmErrorMsg){
       this.autoconfirmErrorMsg = autoconfirmErrorMsg;
    }
    
    /**
     * @return the autoconfirmErrorMsg 
     */
    public String getAutoconfirmErrorMsg(){
       return this.autoconfirmErrorMsg;
    }
	
	/**
	 * @param isOversold the isOversold to set
	 */
    public void setIsOversold(String isOversold){
       this.isOversold = isOversold;
    }
    
    /**
     * @return the isOversold 
     */
    public String getIsOversold(){
       return this.isOversold;
    }
}
