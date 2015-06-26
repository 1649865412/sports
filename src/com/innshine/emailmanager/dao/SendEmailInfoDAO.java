/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.emailmanager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.emailmanager.entity.SendEmailInfo;

public interface SendEmailInfoDAO extends JpaRepository<SendEmailInfo, Long>, JpaSpecificationExecutor<SendEmailInfo>
{
	public List<SendEmailInfo> findByBrandId(Long brandId);
}
