/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.setting.entity;

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
@Table(name="general_setting")
public class GeneralSetting implements Idable<Long>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 品牌ID
	 */
    @Column(length=10)
    private Integer brandId;
    
	/**
	 * 品牌名称
	 */
    @Column(length=255)
    private String brandName;
    
	/**
	 * 到货提醒
	 */
    @Column(length=10)
    private Integer arrivalDay;
    
	/**
	 * 安全销售天数
	 */
    @Column(length=10)
    private Integer secritySell;
    
	/**
	 * 库存周转天数
	 */
    @Column(length=10)
    private Integer turnoverDay;
    
	/**
	 * 更新时间
	 */
    @Column(nullable=false, length=0)
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;
    
	/**
	 * 更新人
	 */
    @Column(length=10)
    private Integer updatedUser;
    
	/**
	 * 用户角色
	 */
    @Column(length=10)
    private Integer roleId;
    
    /**
	 * 角色名称
	 */
    @Column(length=255)
    private String roleName;
    
    
    
    public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	@Column(length=20)
    private String  userName;
    
    
    public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param brandId the brandId to set
	 */
    public void setBrandId(Integer brandId){
       this.brandId = brandId;
    }
    
    /**
     * @return the brandId 
     */
    public Integer getBrandId(){
       return this.brandId;
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
	 * @param arrivalDay the arrivalDay to set
	 */
    public void setArrivalDay(Integer arrivalDay){
       this.arrivalDay = arrivalDay;
    }
    
    /**
     * @return the arrivalDay 
     */
    public Integer getArrivalDay(){
       return this.arrivalDay;
    }
	
	/**
	 * @param secritySell the secritySell to set
	 */
    public void setSecritySell(Integer secritySell){
       this.secritySell = secritySell;
    }
    
    /**
     * @return the secritySell 
     */
    public Integer getSecritySell(){
       return this.secritySell;
    }
	
	/**
	 * @param turnoverDay the turnoverDay to set
	 */
    public void setTurnoverDay(Integer turnoverDay){
       this.turnoverDay = turnoverDay;
    }
    
    /**
     * @return the turnoverDay 
     */
    public Integer getTurnoverDay(){
       return this.turnoverDay;
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
	 * @param updatedUser the updatedUser to set
	 */
    public void setUpdatedUser(Integer updatedUser){
       this.updatedUser = updatedUser;
    }
    
    /**
     * @return the updatedUser 
     */
    public Integer getUpdatedUser(){
       return this.updatedUser;
    }
	
	/**
	 * @param roleId the roleId to set
	 */
    public void setRoleId(Integer roleId){
       this.roleId = roleId;
    }
    
    /**
     * @return the roleId 
     */
    public Integer getRoleId(){
       return this.roleId;
    }
}
