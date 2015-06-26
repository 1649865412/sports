/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockloginfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.stockmanager.stockloginfo.entity.StockLogInfo;
import com.innshine.stockmanager.stockloginfo.dao.StockLogInfoDAO;

@Service
@Transactional
public class StockLogInfoService
{
	private static final Logger log = LoggerFactory.getLogger(StockLogInfoService.class);
	@Autowired
	private StockLogInfoDAO stockLogInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.stockmanager.stockloginfo.service.StockLogInfoService#get
	 * (java.lang.Long)
	 */

	public StockLogInfo get(Long id)
	{
		return stockLogInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.innshine.stockmanager.stockloginfo.service.StockLogInfoService#
	 * saveOrUpdate(com.innshine.stockmanager.stockloginfo.entity.StockLogInfo)
	 */

	public void saveOrUpdate(StockLogInfo stockLogInfo)
	{
		stockLogInfoDAO.save(stockLogInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.stockmanager.stockloginfo.service.StockLogInfoService#delete
	 * (java.lang.Long)
	 */

	public void delete(Long id)
	{
		stockLogInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.stockmanager.stockloginfo.service.StockLogInfoService#findAll
	 * (com.base.util.dwz.Page)
	 */

	public List<StockLogInfo> findAll(Page page)
	{
		setPageAndFiled(page);
		org.springframework.data.domain.Page<StockLogInfo> springDataPage = stockLogInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
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
	 * 
	 * (non-Javadoc)
	 * 
	 * @seecom.innshine.stockmanager.stockloginfo.service.StockLogInfoService#
	 * findByExample(org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<StockLogInfo> findByExample(Specification<StockLogInfo> specification, Page page)
	{
		setPageAndFiled(page);
		org.springframework.data.domain.Page<StockLogInfo> springDataPage = stockLogInfoDAO.findAll(specification,
				PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
