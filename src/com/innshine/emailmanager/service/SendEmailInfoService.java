/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.emailmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.emailmanager.entity.SendEmailInfo;
import com.innshine.emailmanager.dao.SendEmailInfoDAO;

@Service
@Transactional
public class SendEmailInfoService
{
	private static final Logger LOG = LoggerFactory.getLogger(SendEmailInfoService.class);
	@Autowired
	private SendEmailInfoDAO sendEmailInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.emailmanager.service.SendEmailInfoService#get(java.lang.
	 * Long)
	 */

	public SendEmailInfo get(Long id)
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

	public void saveOrUpdate(SendEmailInfo sendEmailInfo)
	{
		sendEmailInfoDAO.save(sendEmailInfo);
	}
	
	public List<SendEmailInfo> findByBrandId(Long brandId)
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

	public List<SendEmailInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<SendEmailInfo> springDataPage = sendEmailInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<SendEmailInfo> findAll()
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
	public List<SendEmailInfo> findByExample(Specification<SendEmailInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<SendEmailInfo> springDataPage = sendEmailInfoDAO.findAll(specification,
				PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
