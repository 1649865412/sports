package com.base.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

 
@Entity
@Table(name = "qrtz_job_details")
public class QrtzJobDetails implements Idable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	  
	private String schedName;
	private String jobName;
	private String jobGroup;
	private String description; 
	private String jobClassName; 
	private String isDurable;
	private String isNonconcurrent; 
	
	public String getSchedName() {
		return schedName;
	}
	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getIsDurable() {
		return isDurable;
	}
	public void setIsDurable(String isDurable) {
		this.isDurable = isDurable;
	}
	public String getIsNonconcurrent() {
		return isNonconcurrent;
	}
	public void setIsNonconcurrent(String isNonconcurrent) {
		this.isNonconcurrent = isNonconcurrent;
	}
	public String getIsUpdateData() {
		return isUpdateData;
	}
	public void setIsUpdateData(String isUpdateData) {
		this.isUpdateData = isUpdateData;
	}
	public String getRequestsRecovery() {
		return requestsRecovery;
	}
	public void setRequestsRecovery(String requestsRecovery) {
		this.requestsRecovery = requestsRecovery;
	}
	public String getJobData() {
		return jobData;
	}
	public void setJobData(String jobData) {
		this.jobData = jobData;
	}

	private String isUpdateData; 
	private String requestsRecovery; 
	private String jobData;
	
}
