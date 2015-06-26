/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.stockmanager.stockinfo.entity.StockInfo;
import com.innshine.stockmanager.stockinfo.dao.StockInfoDAO;

@Service
@Transactional
public class StockInfoService
{
	private static final Logger log = LoggerFactory.getLogger(StockInfoService.class);
	@Autowired
	private StockInfoDAO stockInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.stockmanager.stockinfo.service.StockInfoService#get(java
	 * .lang.Long)
	 */

	public StockInfo get(Long id)
	{
		return stockInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.stockmanager.stockinfo.service.StockInfoService#saveOrUpdate
	 * (com.innshine.stockmanager.stockinfo.entity.StockInfo)
	 */

	public void saveOrUpdate(StockInfo stockInfo)
	{
		stockInfoDAO.save(stockInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.stockmanager.stockinfo.service.StockInfoService#delete(java
	 * .lang.Long)
	 */

	public void delete(Long id)
	{
		stockInfoDAO.delete(id);
	}
	
	private void setPageAndFiled(Page page)
	{
		if (null != page)
		{
			page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
			page.setOrderField("updateTime");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.stockmanager.stockinfo.service.StockInfoService#findAll(
	 * com.base.util.dwz.Page)
	 */

	public List<StockInfo> findAll(Page page)
	{
		setPageAndFiled(page);
		org.springframework.data.domain.Page<StockInfo> springDataPage = stockInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.stockmanager.stockinfo.service.StockInfoService#findByExample
	 * (org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<StockInfo> findByExample(Specification<StockInfo> specification, Page page)
	{
		setPageAndFiled(page);
		org.springframework.data.domain.Page<StockInfo> springDataPage = stockInfoDAO.findAll(specification, PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
