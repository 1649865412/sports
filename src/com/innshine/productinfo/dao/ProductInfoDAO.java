/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.productinfo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.productinfo.entity.ProductInfo;

public interface ProductInfoDAO extends JpaRepository<ProductInfo, Long>, JpaSpecificationExecutor<ProductInfo>
{
	List<ProductInfo> findByProductUpccodeAndBrandIdAndMaterialNumber(String productUpccode, Long brandId,String  materialNumber);
}