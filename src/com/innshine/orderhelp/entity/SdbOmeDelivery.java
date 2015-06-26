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


public class SdbOmeDelivery {
	 
    private String deliveryId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=19)
    private Long idxSplit;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String skunum;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String itemnum;
    
	/**
	 * 
	 */
    @Column(length=65535)
    private String bnscontent;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=32)
    private String deliveryBn;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String memberId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isProtect;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal costProtect;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isCod;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String delivery;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String logiId;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String logiCode;
    
	/**
	 * 
	 */
    @Column(length=100)
    private String logiName;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String logiNo;
    
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
    @Column(length=50)
    private String shipProvince;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String shipCity;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String shipDistrict;
    
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
    @Column(length=50)
    private String shipMobile;
    
	/**
	 * 
	 */
    @Column(length=150)
    private String shipEmail;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String createTime;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String status;
    
	/**
	 * 
	 */
    @Column(length=2147483647)
    private String memo;
    
	/**
	 * 
	 */
    @Column(length=5)
    private String disabled;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String branchId;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String stockStatus;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String delivStatus;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String expreStatus;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String verify;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String packagel;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String picking;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String process;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal netWeight;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal weight;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String lastModified;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String deliveryTime;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal deliveryCostExpect;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal deliveryCostActual;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String parentId;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String bindKey;
    
	/**
	 * 
	 */
    @Column(length=7)
    private String type;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isBind;
    
	/**
	 * 
	 */
    @Column(length=32)
    private String shopId;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String orderCreatetime;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String pause;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String shipTime;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String opId;
    
	/**
	 * 
	 */
    @Column(length=30)
    private String opName;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal codFee;
    
	/**
	 * 
	 */
    @Column(length=7)
    private String syncStatus;
    
	/**
	 * 
	 */
    @Column(length=7)
    private String syncDeliveryStatus;
    
  
	
	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public void setPackagel(String packagel) {
		this.packagel = packagel;
	}

	/**
	 * @param idxSplit the idxSplit to set
	 */
    public void setIdxSplit(Long idxSplit){
       this.idxSplit = idxSplit;
    }
    
    /**
     * @return the idxSplit 
     */
    public Long getIdxSplit(){
       return this.idxSplit;
    }
	
	/**
	 * @param skunum the skunum to set
	 */
    public void setSkunum(String skunum){
       this.skunum = skunum;
    }
    
    /**
     * @return the skunum 
     */
    public String getSkunum(){
       return this.skunum;
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
	 * @param bnscontent the bnscontent to set
	 */
    public void setBnscontent(String bnscontent){
       this.bnscontent = bnscontent;
    }
    
    /**
     * @return the bnscontent 
     */
    public String getBnscontent(){
       return this.bnscontent;
    }
	
	/**
	 * @param deliveryBn the deliveryBn to set
	 */
    public void setDeliveryBn(String deliveryBn){
       this.deliveryBn = deliveryBn;
    }
    
    /**
     * @return the deliveryBn 
     */
    public String getDeliveryBn(){
       return this.deliveryBn;
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
	 * @param delivery the delivery to set
	 */
    public void setDelivery(String delivery){
       this.delivery = delivery;
    }
    
    /**
     * @return the delivery 
     */
    public String getDelivery(){
       return this.delivery;
    }
	
	/**
	 * @param logiId the logiId to set
	 */
    public void setLogiId(String logiId){
       this.logiId = logiId;
    }
    
    /**
     * @return the logiId 
     */
    public String getLogiId(){
       return this.logiId;
    }
	
	/**
	 * @param logiCode the logiCode to set
	 */
    public void setLogiCode(String logiCode){
       this.logiCode = logiCode;
    }
    
    /**
     * @return the logiCode 
     */
    public String getLogiCode(){
       return this.logiCode;
    }
	
	/**
	 * @param logiName the logiName to set
	 */
    public void setLogiName(String logiName){
       this.logiName = logiName;
    }
    
    /**
     * @return the logiName 
     */
    public String getLogiName(){
       return this.logiName;
    }
	
	/**
	 * @param logiNo the logiNo to set
	 */
    public void setLogiNo(String logiNo){
       this.logiNo = logiNo;
    }
    
    /**
     * @return the logiNo 
     */
    public String getLogiNo(){
       return this.logiNo;
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
	 * @param shipProvince the shipProvince to set
	 */
    public void setShipProvince(String shipProvince){
       this.shipProvince = shipProvince;
    }
    
    /**
     * @return the shipProvince 
     */
    public String getShipProvince(){
       return this.shipProvince;
    }
	
	/**
	 * @param shipCity the shipCity to set
	 */
    public void setShipCity(String shipCity){
       this.shipCity = shipCity;
    }
    
    /**
     * @return the shipCity 
     */
    public String getShipCity(){
       return this.shipCity;
    }
	
	/**
	 * @param shipDistrict the shipDistrict to set
	 */
    public void setShipDistrict(String shipDistrict){
       this.shipDistrict = shipDistrict;
    }
    
    /**
     * @return the shipDistrict 
     */
    public String getShipDistrict(){
       return this.shipDistrict;
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
	 * @param stockStatus the stockStatus to set
	 */
    public void setStockStatus(String stockStatus){
       this.stockStatus = stockStatus;
    }
    
    /**
     * @return the stockStatus 
     */
    public String getStockStatus(){
       return this.stockStatus;
    }
	
	/**
	 * @param delivStatus the delivStatus to set
	 */
    public void setDelivStatus(String delivStatus){
       this.delivStatus = delivStatus;
    }
    
    /**
     * @return the delivStatus 
     */
    public String getDelivStatus(){
       return this.delivStatus;
    }
	
	/**
	 * @param expreStatus the expreStatus to set
	 */
    public void setExpreStatus(String expreStatus){
       this.expreStatus = expreStatus;
    }
    
    /**
     * @return the expreStatus 
     */
    public String getExpreStatus(){
       return this.expreStatus;
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
	 * @param package the package to set
	 */
    public void setPackage(String packagel){
       this.packagel = packagel;
    }
    
    /**
     * @return the package 
     */
    public String getPackagel(){
       return this.packagel;
    }
	
	/**
	 * @param picking the picking to set
	 */
    public void setPicking(String picking){
       this.picking = picking;
    }
    
    /**
     * @return the picking 
     */
    public String getPicking(){
       return this.picking;
    }
	
	/**
	 * @param process the process to set
	 */
    public void setProcess(String process){
       this.process = process;
    }
    
    /**
     * @return the process 
     */
    public String getProcess(){
       return this.process;
    }
	
	/**
	 * @param netWeight the netWeight to set
	 */
    public void setNetWeight(BigDecimal netWeight){
       this.netWeight = netWeight;
    }
    
    /**
     * @return the netWeight 
     */
    public BigDecimal getNetWeight(){
       return this.netWeight;
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
	 * @param deliveryTime the deliveryTime to set
	 */
    public void setDeliveryTime(String deliveryTime){
       this.deliveryTime = deliveryTime;
    }
    
    /**
     * @return the deliveryTime 
     */
    public String getDeliveryTime(){
       return this.deliveryTime;
    }
	
	/**
	 * @param deliveryCostExpect the deliveryCostExpect to set
	 */
    public void setDeliveryCostExpect(BigDecimal deliveryCostExpect){
       this.deliveryCostExpect = deliveryCostExpect;
    }
    
    /**
     * @return the deliveryCostExpect 
     */
    public BigDecimal getDeliveryCostExpect(){
       return this.deliveryCostExpect;
    }
	
	/**
	 * @param deliveryCostActual the deliveryCostActual to set
	 */
    public void setDeliveryCostActual(BigDecimal deliveryCostActual){
       this.deliveryCostActual = deliveryCostActual;
    }
    
    /**
     * @return the deliveryCostActual 
     */
    public BigDecimal getDeliveryCostActual(){
       return this.deliveryCostActual;
    }
	
	/**
	 * @param parentId the parentId to set
	 */
    public void setParentId(String parentId){
       this.parentId = parentId;
    }
    
    /**
     * @return the parentId 
     */
    public String getParentId(){
       return this.parentId;
    }
	
	/**
	 * @param bindKey the bindKey to set
	 */
    public void setBindKey(String bindKey){
       this.bindKey = bindKey;
    }
    
    /**
     * @return the bindKey 
     */
    public String getBindKey(){
       return this.bindKey;
    }
	
	/**
	 * @param type the type to set
	 */
    public void setType(String type){
       this.type = type;
    }
    
    /**
     * @return the type 
     */
    public String getType(){
       return this.type;
    }
	
	/**
	 * @param isBind the isBind to set
	 */
    public void setIsBind(String isBind){
       this.isBind = isBind;
    }
    
    /**
     * @return the isBind 
     */
    public String getIsBind(){
       return this.isBind;
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
	 * @param orderCreatetime the orderCreatetime to set
	 */
    public void setOrderCreatetime(String orderCreatetime){
       this.orderCreatetime = orderCreatetime;
    }
    
    /**
     * @return the orderCreatetime 
     */
    public String getOrderCreatetime(){
       return this.orderCreatetime;
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
	 * @param opName the opName to set
	 */
    public void setOpName(String opName){
       this.opName = opName;
    }
    
    /**
     * @return the opName 
     */
    public String getOpName(){
       return this.opName;
    }
	
	/**
	 * @param codFee the codFee to set
	 */
    public void setCodFee(BigDecimal codFee){
       this.codFee = codFee;
    }
    
    /**
     * @return the codFee 
     */
    public BigDecimal getCodFee(){
       return this.codFee;
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
	 * @param syncDeliveryStatus the syncDeliveryStatus to set
	 */
    public void setSyncDeliveryStatus(String syncDeliveryStatus){
       this.syncDeliveryStatus = syncDeliveryStatus;
    }
    
    /**
     * @return the syncDeliveryStatus 
     */
    public String getSyncDeliveryStatus(){
       return this.syncDeliveryStatus;
    }
}
