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

 
public class SdbOmeDlyCorp{
	 
    private String corpId;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String type;
    
	/**
	 * 
	 */
    @Column(length=20)
    private String wmsCode;
    
	/**
	 * 
	 */
    @Column(length=50)
    private String kdapiCode;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String name;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String disabled;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String website;
    
	/**
	 * 
	 */
    @Column(length=200)
    private String requestUrl;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String dailyProcess;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String firstunit;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=8)
    private String continueunit;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String protect;
    
	/**
	 * 
	 */
    @Column(length=6)
    private BigDecimal protectRate;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal minprice;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=2)
    private String setting;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal firstprice;
    
	/**
	 * 
	 */
    @Column(length=20)
    private BigDecimal continueprice;
    
	/**
	 * 
	 */
    @Column(length=2147483647)
    private String dtExpressions;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String dtUseexp;
    
	/**
	 * 
	 */
    @Column(length=2147483647)
    private String areaFeeConf;
    
	/**
	 * 
	 */
    @Column(nullable=false, length=5)
    private String isCod;
    
	/**
	 * 
	 */
    @Column(length=10)
    private String prtTmplId;
    
	/**
	 * 
	 */
    @Column(length=8)
    private String weight;
    
    
	
	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
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
	 * @param wmsCode the wmsCode to set
	 */
    public void setWmsCode(String wmsCode){
       this.wmsCode = wmsCode;
    }
    
    /**
     * @return the wmsCode 
     */
    public String getWmsCode(){
       return this.wmsCode;
    }
	
	/**
	 * @param kdapiCode the kdapiCode to set
	 */
    public void setKdapiCode(String kdapiCode){
       this.kdapiCode = kdapiCode;
    }
    
    /**
     * @return the kdapiCode 
     */
    public String getKdapiCode(){
       return this.kdapiCode;
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
	 * @param website the website to set
	 */
    public void setWebsite(String website){
       this.website = website;
    }
    
    /**
     * @return the website 
     */
    public String getWebsite(){
       return this.website;
    }
	
	/**
	 * @param requestUrl the requestUrl to set
	 */
    public void setRequestUrl(String requestUrl){
       this.requestUrl = requestUrl;
    }
    
    /**
     * @return the requestUrl 
     */
    public String getRequestUrl(){
       return this.requestUrl;
    }
	
	/**
	 * @param dailyProcess the dailyProcess to set
	 */
    public void setDailyProcess(String dailyProcess){
       this.dailyProcess = dailyProcess;
    }
    
    /**
     * @return the dailyProcess 
     */
    public String getDailyProcess(){
       return this.dailyProcess;
    }
	
	/**
	 * @param firstunit the firstunit to set
	 */
    public void setFirstunit(String firstunit){
       this.firstunit = firstunit;
    }
    
    /**
     * @return the firstunit 
     */
    public String getFirstunit(){
       return this.firstunit;
    }
	
	/**
	 * @param continueunit the continueunit to set
	 */
    public void setContinueunit(String continueunit){
       this.continueunit = continueunit;
    }
    
    /**
     * @return the continueunit 
     */
    public String getContinueunit(){
       return this.continueunit;
    }
	
	/**
	 * @param protect the protect to set
	 */
    public void setProtect(String protect){
       this.protect = protect;
    }
    
    /**
     * @return the protect 
     */
    public String getProtect(){
       return this.protect;
    }
	
	/**
	 * @param protectRate the protectRate to set
	 */
    public void setProtectRate(BigDecimal protectRate){
       this.protectRate = protectRate;
    }
    
    /**
     * @return the protectRate 
     */
    public BigDecimal getProtectRate(){
       return this.protectRate;
    }
	
	/**
	 * @param minprice the minprice to set
	 */
    public void setMinprice(BigDecimal minprice){
       this.minprice = minprice;
    }
    
    /**
     * @return the minprice 
     */
    public BigDecimal getMinprice(){
       return this.minprice;
    }
	
	/**
	 * @param setting the setting to set
	 */
    public void setSetting(String setting){
       this.setting = setting;
    }
    
    /**
     * @return the setting 
     */
    public String getSetting(){
       return this.setting;
    }
	
	/**
	 * @param firstprice the firstprice to set
	 */
    public void setFirstprice(BigDecimal firstprice){
       this.firstprice = firstprice;
    }
    
    /**
     * @return the firstprice 
     */
    public BigDecimal getFirstprice(){
       return this.firstprice;
    }
	
	/**
	 * @param continueprice the continueprice to set
	 */
    public void setContinueprice(BigDecimal continueprice){
       this.continueprice = continueprice;
    }
    
    /**
     * @return the continueprice 
     */
    public BigDecimal getContinueprice(){
       return this.continueprice;
    }
	
	/**
	 * @param dtExpressions the dtExpressions to set
	 */
    public void setDtExpressions(String dtExpressions){
       this.dtExpressions = dtExpressions;
    }
    
    /**
     * @return the dtExpressions 
     */
    public String getDtExpressions(){
       return this.dtExpressions;
    }
	
	/**
	 * @param dtUseexp the dtUseexp to set
	 */
    public void setDtUseexp(String dtUseexp){
       this.dtUseexp = dtUseexp;
    }
    
    /**
     * @return the dtUseexp 
     */
    public String getDtUseexp(){
       return this.dtUseexp;
    }
	
	/**
	 * @param areaFeeConf the areaFeeConf to set
	 */
    public void setAreaFeeConf(String areaFeeConf){
       this.areaFeeConf = areaFeeConf;
    }
    
    /**
     * @return the areaFeeConf 
     */
    public String getAreaFeeConf(){
       return this.areaFeeConf;
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
	 * @param prtTmplId the prtTmplId to set
	 */
    public void setPrtTmplId(String prtTmplId){
       this.prtTmplId = prtTmplId;
    }
    
    /**
     * @return the prtTmplId 
     */
    public String getPrtTmplId(){
       return this.prtTmplId;
    }
	
	/**
	 * @param weight the weight to set
	 */
    public void setWeight(String weight){
       this.weight = weight;
    }
    
    /**
     * @return the weight 
     */
    public String getWeight(){
       return this.weight;
    }
}
