/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.ordermanager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.ordermanager.entity.OrderFormInfo;

public interface OrderFormInfoDAO extends JpaRepository<OrderFormInfo, Long>, JpaSpecificationExecutor<OrderFormInfo>
{
	/**
	 * 默认按upccode做唯一键
	 * 
	 * @param upccode
	 *        69码
	 * @param brandId
	 *        品牌
	 * @return List< OrderFormInfo >
	 */
	List<OrderFormInfo> findByMaterialNumberAndBrandId(String materialNumber, Long brandId);
	
	/**
	 * 默认按upccode做唯一键
	 * 
	 * @param upccode
	 *        69码
	 * @param materialNumber
	 *        款号(即货号)
	 * @param brandId
	 *        所属组织编号
	 * @return List< OrderFormInfo >
	 */
	List<OrderFormInfo> findByUpccodeAndMaterialNumberAndBrandId(String upccode, String materialNumber,
			Long brandId);
}
