/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.daily.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.daily.entity.DailyReportInfo;

public interface DailyReportInfoDAO extends JpaRepository<DailyReportInfo, Long>,
		JpaSpecificationExecutor<DailyReportInfo>
{
	public List<DailyReportInfo> findByFileCreateTimeAndBrandId(String fileCreateTime, Long brandId);
}
