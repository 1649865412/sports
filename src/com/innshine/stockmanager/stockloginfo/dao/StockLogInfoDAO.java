/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockloginfo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.stockmanager.stockloginfo.entity.StockLogInfo;

public interface StockLogInfoDAO extends JpaRepository<StockLogInfo, Long>, JpaSpecificationExecutor<StockLogInfo> {

}