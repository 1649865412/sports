/**
 *  code generation
 */
package com.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.main.Brand;
import com.base.entity.main.Role;

public interface BrandDAO extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

	public List<Brand> findByExtendTypeAndExtendId(String extendType,Long extendId);
}