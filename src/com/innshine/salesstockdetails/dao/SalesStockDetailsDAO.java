/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.salesstockdetails.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.salesstockdetails.entity.SalesStockDetails;

public interface SalesStockDetailsDAO extends JpaRepository<SalesStockDetails, Long>,
		JpaSpecificationExecutor<SalesStockDetails>
{
	List<SalesStockDetails> findByUpccodeAndPlatformIdAndSalesTimeAndBrandIdAndMaterialNumber(String upccode, String platformId,
			String salesTime, Long brandId,String materialNumber);
}