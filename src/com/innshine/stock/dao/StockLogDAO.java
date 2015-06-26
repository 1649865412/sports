/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.stock.entity.StockLog;

public interface StockLogDAO extends JpaRepository<StockLog, Long>, JpaSpecificationExecutor<StockLog> {

}