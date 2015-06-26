package com.base.dao;

 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.QrtzJobDetails;

public interface QrtzJobDetailsDao  extends JpaRepository<QrtzJobDetails, Long>,JpaSpecificationExecutor<QrtzJobDetails>{
   
}
