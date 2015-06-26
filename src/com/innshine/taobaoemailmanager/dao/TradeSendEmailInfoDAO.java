/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.taobaoemailmanager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.taobaoemailmanager.entity.TradeSendEmailInfo;

public interface TradeSendEmailInfoDAO extends JpaRepository<TradeSendEmailInfo, Long>, JpaSpecificationExecutor<TradeSendEmailInfo>
{
	public List<TradeSendEmailInfo> findByBrandId(Long brandId);
}
