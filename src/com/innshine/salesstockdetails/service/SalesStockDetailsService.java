/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.salesstockdetails.service;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.nbsalesdetails.service.NbSalesDetailsService;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.innshine.salesstockdetails.dao.SalesStockDetailsDAO;
import com.innshine.salesstockdetails.entity.SalesStockDetails;
import com.utils.Exceptions;

@Service
@Transactional
public class SalesStockDetailsService
{
	private static final Logger LOG = LoggerFactory.getLogger(SalesStockDetailsService.class);
	/**
	 * export-fields-config.xml中配置的ID
	 */
	public static final String EXPORT_FIEDLS_CONFIG_KEY = "sales_stock_details";
	
	/**
	 * 导出的文件名
	 */
	private static final String DEFAULT_EXPORT_FILE_NAME = "销售详情.xlsx";
	
	@Autowired
	private SalesStockDetailsDAO salesStockDetailsDAO;
	
	@Autowired(required = true)
	private NbSalesDetailsService nbSalesDetailsService;
	
	
	public SalesStockDetails findByUnique(SalesStockDetails salesStockDetails)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("SalesStockDetailsService.findByUnique() salesStockDetails =  " + salesStockDetails);
		}
		
		List<SalesStockDetails> details = null;
		
		if (null != salesStockDetails)
		{
			details = findByUpccodeAndPlatformIdAndSalesTimeAndBrandId(salesStockDetails);
			
		}
		
		if (LOG.isDebugEnabled())
		{
			LOG.debug("SalesStockDetailsService.findByUnique() Return Object =  " + details);
		}
		
		return CollectionUtils.isNotEmpty(details) ? details.get(0) : null;
	}
	
	
	private List<SalesStockDetails> findByUpccodeAndPlatformIdAndSalesTimeAndBrandId(
			SalesStockDetails salesStockDetails)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("findByUpccodeAndPlatformIdAndSalesTimeAndBrandId() salesStockDetails =  "
					+ salesStockDetails);
		}
		
		List<SalesStockDetails> details = null;
		if (null != salesStockDetails)
		{
			details = salesStockDetailsDAO.findByUpccodeAndPlatformIdAndSalesTimeAndBrandIdAndMaterialNumber(salesStockDetails
					.getUpccode(), salesStockDetails.getPlatformId(), salesStockDetails.getSalesTime(),
					salesStockDetails.getBrandId(),salesStockDetails.getMaterialNumber());
			
		}
		
		if (LOG.isDebugEnabled())
		{
			LOG.debug("SalesStockDetailsService.findByUnique() Return Object =  " + details);
		}
		
		return details;
	}
	
	public SalesStockDetails findByUniqueOrParams(String upccode, String platformId, String salesTime,
			Long brandId,String materialNumber)
	{
		List<SalesStockDetails> details = salesStockDetailsDAO.findByUpccodeAndPlatformIdAndSalesTimeAndBrandIdAndMaterialNumber(
				upccode, platformId, salesTime, brandId,materialNumber);
		
		return CollectionUtils.isNotEmpty(details) ? details.get(0) : null;
	}
	
	public void deleteByUnique(List<SalesStockDetails> details)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("deleteByUnique() salesStockDetails =  " + details);
		}
		
		if (CollectionUtils.isNotEmpty(details))
		{
			salesStockDetailsDAO.deleteInBatch(details);
		}
	}
	
	public void deleteByUnique(SalesStockDetails details)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("deleteByUnique() salesStockDetails =  " + details);
		}
		
		if (null != details)
		{
			try
			{
				salesStockDetailsDAO.delete(details);
				salesStockDetailsDAO.flush();
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
	}
	
	public SalesStockDetails get(Long id)
	{
		return salesStockDetailsDAO.findOne(id);
	}
	
	public void saveOrUpdate(SalesStockDetails salesStockDetails)
	{
		salesStockDetailsDAO.save(salesStockDetails);
	}
	
	public void saveOrUpdate(List<SalesStockDetails> salesStockDetails)
	{
		salesStockDetailsDAO.save(salesStockDetails);
	}
	
	public void delete(Long id)
	{
		salesStockDetailsDAO.delete(id);
	}
	
	public List<SalesStockDetails> findAll(Page page)
	{
		if (null != page)
		{
			page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
			page.setOrderField("updateTime");
		}
		
		org.springframework.data.domain.Page<SalesStockDetails> springDataPage = salesStockDetailsDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<SalesStockDetails> findByExample(Specification<SalesStockDetails> specification, Page page)
	{
		if (null != page)
		{
			if (StringUtils.isEmpty(page.getOrderField()) && StringUtils.isEmpty(page.getOrderDirection()))
			{
				page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
				page.setOrderField("updateTime");
			}
		}
		org.springframework.data.domain.Page<SalesStockDetails> springDataPage = salesStockDetailsDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<SalesStockDetails> findByExample(Specification<SalesStockDetails> specification)
	{
		return salesStockDetailsDAO.findAll(specification);
	}
	
	/**
	 * 根据前面选择字段，导出对应数据。<br/>
	 * 如果选择的字段为空，则默认根据模版导出。故则相反
	 * 
	 * @param salesDetails
	 *        原始数据集
	 * @param exportSelectFieldsName
	 *        选择需要导出的字段列表
	 * @param request
	 *        请求对象
	 * @param response
	 *        响应对象
	 * @throws IOException
	 */
	public void exportExcel(List<SalesStockDetails> salesDetails, String exportSelectFieldsName,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// 如果选择的字段为空，则默认根据模版导出
		if (StringUtils.isNotEmpty(exportSelectFieldsName))
		{
			Vector<String> displayFields = nbSalesDetailsService.getExportFieldNameVector(exportSelectFieldsName);
			Vector<String> headerVector = nbSalesDetailsService.getExportTitleHeader(exportSelectFieldsName,
					EXPORT_FIEDLS_CONFIG_KEY);
			
			Workbook workbook = ExcelFileUtils.exportByTemplateToExcel(salesDetails, displayFields, headerVector);
			
			if (null != workbook)
			{
				ExcelFileUtils.excelExport(workbook, request, response, DEFAULT_EXPORT_FILE_NAME);
			}
		}
	}
}
