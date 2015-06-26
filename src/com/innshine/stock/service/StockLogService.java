/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package	com.innshine.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.stock.entity.StockLog;
import com.innshine.stock.dao.StockLogDAO; 

@Service
@Transactional
public class StockLogService  {
	private static final Logger log = LoggerFactory.getLogger(StockLogService.class);
	@Autowired
	private StockLogDAO stockLogDAO;

	/*
	 * (non-Javadoc)
	 * @see com.innshine.stock.service.StockLogService#get(java.lang.Long)  
	 */ 
	 
	public StockLog get(Long id) {
		return stockLogDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.innshine.stock.service.StockLogService#saveOrUpdate(com.innshine.stock.entity.StockLog)  
	 */
 
	public void saveOrUpdate(StockLog stockLog) {
		stockLogDAO.save(stockLog);
	}
	public void saveOrUpdate(List<StockLog> stockLogs) {
		stockLogDAO.save(stockLogs);
	}

	/*
	 * (non-Javadoc)
	 * @see com.innshine.stock.service.StockLogService#delete(java.lang.Long)  
	 */
 
	public void delete(Long id) {
		stockLogDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.innshine.stock.service.StockLogService#findAll(com.base.util.dwz.Page)  
	 */
	 
	public List<StockLog> findAll(Page page) {
		org.springframework.data.domain.Page<StockLog> springDataPage = stockLogDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	  
	 * (non-Javadoc)
	 * @see com.innshine.stock.service.StockLogService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */ 
	public List<StockLog> findByExample(
			Specification<StockLog> specification, Page page) {
		org.springframework.data.domain.Page<StockLog> springDataPage = stockLogDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
