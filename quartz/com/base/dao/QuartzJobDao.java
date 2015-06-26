package com.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.base.entity.QrtzJob;

public interface QuartzJobDao extends JpaRepository<QrtzJob, Long>,
		JpaSpecificationExecutor<QrtzJob> {
	
	@Query("FROM QrtzJob d WHERE jobStatus='1'")
	public List<QrtzJob> findAllStartJob();
}
