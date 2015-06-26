/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.setting.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.ordermanager.entity.OrderFormInfo;
import com.innshine.setting.entity.GeneralSetting;

    
public interface GeneralSettingDAO extends JpaRepository<GeneralSetting, Long>, JpaSpecificationExecutor<GeneralSetting> {
	
	GeneralSetting findByBrandId(int brandId);
}