/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockinfo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.stockmanager.stockinfo.entity.StockInfo;

public interface StockInfoDAO extends JpaRepository<StockInfo, Long>, JpaSpecificationExecutor<StockInfo> {

}