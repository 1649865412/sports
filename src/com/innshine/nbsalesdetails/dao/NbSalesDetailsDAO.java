/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.nbsalesdetails.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.nbsalesdetails.entity.NbSalesDetails;

public interface NbSalesDetailsDAO extends JpaRepository<NbSalesDetails, Long>,
		JpaSpecificationExecutor<NbSalesDetails>
{
	
	List<NbSalesDetails> findByUpccodeAndPlatformIdAndMarketStartTimeAndMarketEndTimeAndBrandIdAndMaterialNumber(
			String upccode, String platformId, String marketStartTime, String marketEndTime, Long brandId,String materialNumber);
	
}