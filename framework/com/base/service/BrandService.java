
package	com.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.dao.BrandDAO;
import com.base.entity.main.Brand;
import com.base.service.component.BusinessCache;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.setting.entity.GeneralSetting;
import com.innshine.setting.dao.GeneralSettingDAO; 

@Service
@Transactional
public class BrandService  {
	private static final Logger log = LoggerFactory.getLogger(BrandService.class);
	
	@Autowired
	private BusinessCache businessCache;
	
	private final static String cacheKey = "__brand_cache";
	private final static String key = "all";
	
	private BrandDAO brandDAO;
	@Autowired
	public void setBrandDAO(BrandDAO brandDAO){
		this.brandDAO = brandDAO;
		cacheBrand();
	}
	
	public void cacheBrand(){
		List<Brand> list = brandDAO.findAll();
		businessCache.put(cacheKey, key, list);
	}
	
	public Brand findByExtendTypeAndExtendId(String extendType,Long extendId){
		  List<Brand> list = findAll();
		  for(Brand br : list){
			  if(extendType.equals(br.getExtendType()) && br.getExtendId() == extendId){
				  return br;
			  }
		  }
		  return null; 
	}
	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#get(java.lang.Long)  
	 */ 
	 
	public Brand get(Long id) {
		return brandDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.innshine.setting.service.GeneralSettingService#saveOrUpdate(com.innshine.setting.entity.GeneralSetting)  
	 */
 
	public void saveOrUpdate(Brand brand) {
		brandDAO.save(brand);
		cacheBrand();
	}

	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#delete(java.lang.Long)  
	 */
 
	public void delete(Long id) {
		brandDAO.delete(id);
		cacheBrand();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#findAll(com.base.util.dwz.Page)  
	 */
	 
	public List<Brand> findAll(Page page) {
		org.springframework.data.domain.Page<Brand> springDataPage = brandDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<Brand> findAll() {
		Object o = businessCache.get(cacheKey, key);
		if(o == null){
			//return new ArrayList<Brand>();
			 List<Brand> list = brandDAO.findAll();
			 return list;
			//return  brandDAO.findAll();
		}else{
			List<Brand> list = (List<Brand>)o;
			
			return list;
		} 
	}
	
	/*
	  
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.GeneralSettingService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */ 
	public List<Brand> findByExample(
			Specification<Brand> specification, Page page) {
		org.springframework.data.domain.Page<Brand> springDataPage = brandDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
