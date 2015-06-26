/**
 *  code generation
 */
package com.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.main.RoleBrand;
import com.base.entity.main.RolePermission;

public interface RoleBrandDAO extends JpaRepository<RoleBrand, Long>, JpaSpecificationExecutor<RoleBrand> {

	/**
	 * @param id
	 * @return
	 */
	List<RoleBrand> findByRoleId(Long roleId);

}