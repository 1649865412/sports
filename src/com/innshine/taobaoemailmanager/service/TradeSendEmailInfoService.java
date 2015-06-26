/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.taobaoemailmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.taobaoemailmanager.dao.TradeSendEmailInfoDAO;
import com.innshine.taobaoemailmanager.entity.TradeSendEmailInfo;

@Service
@Transactional
public class TradeSendEmailInfoService
{
	private static final Logger LOG = LoggerFactory.getLogger(TradeSendEmailInfoService.class);
	@Autowired
	private TradeSendEmailInfoDAO sendEmailInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.emailmanager.service.SendEmailInfoService#get(java.lang.
	 * Long)
	 */

	public TradeSendEmailInfo get(Long id)
	{
		return sendEmailInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.emailmanager.service.SendEmailInfoService#saveOrUpdate(com
	 * .innshine.emailmanager.entity.SendEmailInfo)
	 */

	public void saveOrUpdate(TradeSendEmailInfo sendEmailInfo)
	{
		sendEmailInfoDAO.save(sendEmailInfo);
	}
	
	public List<TradeSendEmailInfo> findByBrandId(Long brandId)
	{
		return sendEmailInfoDAO.findByBrandId(brandId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.emailmanager.service.SendEmailInfoService#delete(java.lang
	 * .Long)
	 */

	public void delete(Long id)
	{
		sendEmailInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.emailmanager.service.SendEmailInfoService#findAll(com.base
	 * .util.dwz.Page)
	 */

	public List<TradeSendEmailInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<TradeSendEmailInfo> springDataPage = sendEmailInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<TradeSendEmailInfo> findAll()
	{
		return sendEmailInfoDAO.findAll();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.emailmanager.service.SendEmailInfoService#findByExample(
	 * org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<TradeSendEmailInfo> findByExample(Specification<TradeSendEmailInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<TradeSendEmailInfo> springDataPage = sendEmailInfoDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
