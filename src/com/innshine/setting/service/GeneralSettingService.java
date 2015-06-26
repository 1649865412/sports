/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package	com.innshine.setting.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.SqlDao;
import com.base.entity.main.Brand;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.setting.dao.GeneralSettingDAO;
import com.innshine.setting.entity.GeneralSetting;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckResult;

@Service
@Transactional
public class GeneralSettingService  {
	private static final Logger log = LoggerFactory.getLogger(GeneralSettingService.class);
	@Autowired
	private GeneralSettingDAO generalSettingDAO;
	
	@Autowired
	private SqlDao sqlDao;

	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#get(java.lang.Long)  
	 */ 
	 
	public GeneralSetting get(Long id) {
		return generalSettingDAO.findOne(id);
	}

	public GeneralSetting getByBranId(int BranId) {
		return generalSettingDAO.findByBrandId(BranId);
	}
	
	/*
	 * (non-Javadoc) 
	 * @see com.innshine.setting.service.GeneralSettingService#saveOrUpdate(com.innshine.setting.entity.GeneralSetting)  
	 */
 
	public void saveOrUpdate(GeneralSetting generalSetting) {
		generalSettingDAO.save(generalSetting);
	}

	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#delete(java.lang.Long)  
	 */
 
	public void delete(Long id) {
		generalSettingDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#findAll(com.base.util.dwz.Page)  
	 */
	 
	public List<GeneralSetting> findAll(Page page) {
		org.springframework.data.domain.Page<GeneralSetting> springDataPage = generalSettingDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	  
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */ 
	public List<GeneralSetting> findByExample(
			Specification<GeneralSetting> specification, Page page) {
		org.springframework.data.domain.Page<GeneralSetting> springDataPage = generalSettingDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<Brand> getBrandListAll() throws Exception{
		List<Brand> result = sqlDao.queryListBySql(new HashMap(),
				"getAllBrandList",  Brand.class);
		return result;
	}
}
