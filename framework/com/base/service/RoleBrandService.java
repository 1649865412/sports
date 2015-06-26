
package	com.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.dao.BrandDAO;
import com.base.dao.RoleBrandDAO;
import com.base.entity.main.Brand;
import com.base.entity.main.RoleBrand;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.setting.entity.GeneralSetting;
import com.innshine.setting.dao.GeneralSettingDAO; 

@Service
@Transactional
public class RoleBrandService  {
	private static final Logger log = LoggerFactory.getLogger(RoleBrandService.class);
	@Autowired
	private RoleBrandDAO roleBrandDAO;

	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#get(java.lang.Long)  
	 */ 
	 
	public RoleBrand get(Long id) {
		return roleBrandDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.innshine.setting.service.GeneralSettingService#saveOrUpdate(com.innshine.setting.entity.GeneralSetting)  
	 */
 
	public void saveOrUpdate(RoleBrand roleBrand) {
		roleBrandDAO.save(roleBrand);
	}

	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#delete(java.lang.Long)  
	 */
 
	public void delete(Long id) {
		roleBrandDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#findAll(com.base.util.dwz.Page)  
	 */
	 
	public List<RoleBrand> findAll(Page page) {
		org.springframework.data.domain.Page<RoleBrand> springDataPage = roleBrandDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<RoleBrand> findAll() { 
		return roleBrandDAO.findAll();
	}
	
	/*
	  
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */ 
	public List<RoleBrand> findByExample(
			Specification<RoleBrand> specification, Page page) {
		org.springframework.data.domain.Page<RoleBrand> springDataPage = roleBrandDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	
	public List<RoleBrand> findByRoleId(Long roleId){
		return roleBrandDAO.findByRoleId(roleId);
	}
}
