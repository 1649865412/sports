/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package	com.base.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.QuartzJobDao;
import com.base.dao.SqlDao;
import com.base.entity.QrtzJob;
import com.base.entity.main.Brand;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils; 

@Service
@Transactional
public class QuartzJobService  {
	private static final Logger log = LoggerFactory.getLogger(QuartzJobService.class);
	@Autowired
	private QuartzJobDao quartzJobDao;
	  
	@Autowired
	private SqlDao sqlDao;

	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.QrtzJobService#get(java.lang.Long)  
	 */ 
	 
	public QrtzJob get(Long id) {
		return quartzJobDao.findOne(id);
	}

	 
	
	/*
	 * (non-Javadoc) 
	 * @see com.innshine.setting.service.QrtzJobService#saveOrUpdate(com.innshine.setting.entity.QrtzJob)  
	 */
 
	public void saveOrUpdate(QrtzJob generalSetting) {
		quartzJobDao.save(generalSetting);
	}

	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.QrtzJobService#delete(java.lang.Long)  
	 */
 
	public void delete(Long id) {
		quartzJobDao.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.QrtzJobService#findAll(com.base.util.dwz.Page)  
	 */
	 
	public List<QrtzJob> findAll(Page page) {
		org.springframework.data.domain.Page<QrtzJob> springDataPage = quartzJobDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	  
	 * (non-Javadoc)
	 * @see com.innshine.setting.service.QrtzJobService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */ 
	public List<QrtzJob> findByExample(
			Specification<QrtzJob> specification, Page page) {
		org.springframework.data.domain.Page<QrtzJob> springDataPage = quartzJobDao.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	 
}
