/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.daily.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.entity.main.Brand;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.daily.Constants;
import com.innshine.daily.entity.DailyReportInfo;
import com.innshine.daily.utils.GatherDailyExcelExportUtils;
import com.innshine.daily.dao.DailyReportInfoDAO;
import com.utils.DateUtils;
import com.utils.Exceptions;

@Service
@Transactional
public class DailyReportInfoService
{
	private static final Logger LOG = LoggerFactory.getLogger(DailyReportInfoService.class);
	
	@Autowired
	private DailyReportInfoDAO dailyReportInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.daily.service.DailyReportInfoService#get(java.lang.Long)
	 */

	public DailyReportInfo get(Long id)
	{
		return dailyReportInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.daily.service.DailyReportInfoService#saveOrUpdate(com.innshine
	 * .daily.entity.DailyReportInfo)
	 */

	public DailyReportInfo saveOrUpdate(DailyReportInfo dailyReportInfo)
	{
		return dailyReportInfoDAO.save(dailyReportInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.daily.service.DailyReportInfoService#delete(java.lang.Long)
	 */

	public void delete(Long id)
	{
		dailyReportInfoDAO.delete(id);
	}
	
	public void delete(List<DailyReportInfo> dailyReportInfos)
	{
		dailyReportInfoDAO.deleteInBatch(dailyReportInfos);
	}
	
	public List<DailyReportInfo> findByFileCreateTimeAndBrandId(DailyReportInfo dailyReportInfo, Brand brand)
	{
		if (null != dailyReportInfo)
		{
			return dailyReportInfoDAO
					.findByFileCreateTimeAndBrandId(dailyReportInfo.getFileCreateTime(), brand.getId());
		}
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.daily.service.DailyReportInfoService#findAll(com.base.util
	 * .dwz.Page)
	 */

	public List<DailyReportInfo> findAll(Page page)
	{
		if (null != page)
		{
			page.setOrderField("fileCreateTime");
			page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		}
		org.springframework.data.domain.Page<DailyReportInfo> springDataPage = dailyReportInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @seecom.innshine.daily.service.DailyReportInfoService#findByExample(org.
	 * springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)
	 */
	public List<DailyReportInfo> findByExample(Specification<DailyReportInfo> specification, Page page)
	{
		if (null != page)
		{
			page.setOrderField("fileCreateTime");
			page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		}
		org.springframework.data.domain.Page<DailyReportInfo> springDataPage = dailyReportInfoDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**
	 * 录入生成后的汇总日报信息
	 * 
	 * @param fileAttr
	 *        获取文件名与文件路径数 组，该数组永远只保持两个长度，0：文件名 1:文件完整路径中不包含文件名
	 * @param brand
	 *        品牌对象
	 */
	public DailyReportInfo addGatherDaily(String[] fileAttr, Brand brand)
	{
		if (ArrayUtils.isNotEmpty(fileAttr) && fileAttr.length >= 2)
		{
			DailyReportInfo dailyReportInfo = getSaveDailyReportInfo(fileAttr, brand);
			
			// 如果当天记录已存在则，默认先删除后加，不累计历史记录
			List<DailyReportInfo> dailyReportInfos = findByFileCreateTimeAndBrandId(dailyReportInfo, brand);
			if (CollectionUtils.isNotEmpty(dailyReportInfos))
			{
				delete(dailyReportInfos);
			}
			
			if (null != dailyReportInfo)
			{
				return saveOrUpdate(dailyReportInfo);
			}
		}
		
		return null;
	}
	
	/**
	 * 构造入库对象，默认当前时间往前推一天
	 * 
	 * @param fileAttr
	 *        获取文件名与文件路径数组，该数组永远只保持两个长度，0：文件名 1:文件完整路径中不包含文件名
	 * @param brand
	 *        品牌对象
	 * @return DailyReportInfo对象
	 */
	private DailyReportInfo getSaveDailyReportInfo(String[] fileAttr, Brand brand)
	{
		if (ArrayUtils.isNotEmpty(fileAttr) && fileAttr.length >= 2)
		{
			try
			{
				String fileName = fileAttr[0];
				String filePath = fileAttr[1];
				String fileCreateTime = GatherDailyExcelExportUtils.getDate(-1, DateUtils.SIMPLE_DEFAULT_FORMAT);
				
				DailyReportInfo dailyReportInfo = new DailyReportInfo();
				dailyReportInfo.setDefaultSavePath(filePath);
				dailyReportInfo.setFileCreateName(fileName);
				dailyReportInfo.setFileCreateTime(fileCreateTime);
				dailyReportInfo.setUpdateTime(DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
				dailyReportInfo.setBrandId(brand.getId());
				dailyReportInfo.setSendStauts(Constants.SEND_STATUS_SUCEED);
				return dailyReportInfo;
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return null;
	}
}
