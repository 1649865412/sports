package com.base.dao;

 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.QrtzTriggers;

public interface QuartzDao  extends JpaRepository<QrtzTriggers, Long>,JpaSpecificationExecutor<QrtzTriggers>{
   
}
