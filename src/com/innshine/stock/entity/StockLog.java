/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stock.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.base.entity.Idable;

@Entity
@Table(name="stock_log")
public class StockLog implements Idable<Long>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 69码
	 */
    @Column(length=20)
    private String upccode;
    
	/**
	 * 款号
	 */
    @Column(length=100)
    private String materialNumber;
    
	/**
	 * 数量
	 */
    @Column(length=19)
    private Long num;
    
	/**
	 * 操作类型(订货1、到货2)
	 */
    @Column(length=20)
    private String optType;
    
	/**
	 * 更新人
	 */
    @Column(length=100)
    private String updatedUser;
    
	/**
	 * 更新时间
	 */
    @Column(nullable=false, length=0)
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;
    
	/**
	 * 发生时间
	 */
    @Column(nullable=false, length=0)
	@Temporal(TemporalType.TIMESTAMP)
    private Date optTime;
    
	/**
	 * 
	 */
    @Column(length=19)
    private Long brandId;
    
    /**
	 * 预计到货期
	 */
	@Column(length = 50)
	private String predictArriveTime;
	
	 /**
	 * 金额
	 */
	@Column(length = 50)
	private Double  price;
	
    public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	public String getPredictArriveTime() {
		return predictArriveTime;
	}

	public void setPredictArriveTime(String predictArriveTime) {
		this.predictArriveTime = predictArriveTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param upccode the upccode to set
	 */
    public void setUpccode(String upccode){
       this.upccode = upccode;
    }
    
    /**
     * @return the upccode 
     */
    public String getUpccode(){
       return this.upccode;
    }
	
	/**
	 * @param materialNumber the materialNumber to set
	 */
    public void setMaterialNumber(String materialNumber){
       this.materialNumber = materialNumber;
    }
    
    /**
     * @return the materialNumber 
     */
    public String getMaterialNumber(){
       return this.materialNumber;
    }
	
	/**
	 * @param num the num to set
	 */
    public void setNum(Long num){
       this.num = num;
    }
    
    /**
     * @return the num 
     */
    public Long getNum(){
       return this.num;
    }
	
	/**
	 * @param optType the optType to set
	 */
    public void setOptType(String optType){
       this.optType = optType;
    }
    
    /**
     * @return the optType 
     */
    public String getOptType(){
       return this.optType;
    }
	
	/**
	 * @param updatedUser the updatedUser to set
	 */
    public void setUpdatedUser(String updatedUser){
       this.updatedUser = updatedUser;
    }
    
    /**
     * @return the updatedUser 
     */
    public String getUpdatedUser(){
       return this.updatedUser;
    }
	
	/**
	 * @param updatedTime the updatedTime to set
	 */
    public void setUpdatedTime(Date updatedTime){
       this.updatedTime = updatedTime;
    }
    
    /**
     * @return the updatedTime 
     */
    public Date getUpdatedTime(){
       return this.updatedTime;
    }
	
	/**
	 * @param optTime the optTime to set
	 */
    public void setOptTime(Date optTime){
       this.optTime = optTime;
    }
    
    /**
     * @return the optTime 
     */
    public Date getOptTime(){
       return this.optTime;
    }
	
	/**
	 * @param brandId the brandId to set
	 */
    public void setBrandId(Long brandId){
       this.brandId = brandId;
    }
    
    /**
     * @return the brandId 
     */
    public Long getBrandId(){
       return this.brandId;
    }
}
