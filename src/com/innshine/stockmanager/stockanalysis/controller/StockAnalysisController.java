/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockanalysis.controller;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.util.persistence.SearchFilter;
import com.innshine.productinfo.service.ProductInfoService;
import com.innshine.productinfo.service.ProductInfoService.QueryType;
import com.innshine.shopAnalyse.util.ExcelModelTool;
import com.innshine.stockmanager.stockanalysis.entity.StockAnalysisInfo;
import com.innshine.stockmanager.stockanalysis.entity.StockAnalysisInfoModel;
import com.innshine.stockmanager.stockanalysis.service.StockAnalysisService;
import com.utils.excel.template.ExcelExportByTemplate;
import com.utils.excel.template.ExportConfig;

@Controller
@RequestMapping("/management/stockAnalysis")
public class StockAnalysisController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StockAnalysisController.class);
	private static final String LIST = "management/stockmanager/stock_analysis/list";
	private static final String GROUP_SELECT_PAGE = "management/stockmanager/stock_analysis/group_type_select";
	private static final String PREVIEW_EXCEL_PAGE = "management/stockmanager/stock_analysis/preview";
	
	@Autowired
	private StockAnalysisService stockAnalysisService;
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@RequiresPermissions("StockAnalysis:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map, String brandId)
	{
		Collection<SearchFilter> searchFilters = DynamicSpecifications.genSearchFilter(request);
		List<StockAnalysisInfo> analysisInfos = stockAnalysisService.findByPage(searchFilters, page);
		String futureArriveName = request.getParameter(StockAnalysisService.FUTURE_ARRIVE_FIELD_NAME);
		String factArriveTime = request.getParameter(StockAnalysisService.FUTURE_ARRIVE_TIME_NAME);
		map.put("page", page);
		map.put("analysisInfos", analysisInfos);
		map.put("SERIES", productInfoService.findByBrandIdAndFiledName(brandId, QueryType.SERIES));
		map.put("INLINE_OR2NDLINES", productInfoService.findByBrandIdAndFiledName(brandId,
				QueryType.INLINE_OR2NDLINE));
		map.put("PRODUCT_BRANDS", productInfoService.findByBrandIdAndFiledName(brandId,
				QueryType.PRODUCT_BRAND));
		map.put("futureArriveName", futureArriveName);
		map.put("factArriveTime", factArriveTime);
		
		return LIST;
	}
	
	@RequiresPermissions("StockAnalysis:view")
	@RequestMapping(value = "/preGoTogroupTypePage", method = { RequestMethod.GET, RequestMethod.POST })
	public String goTogroupTypePage(String exportType, Map<String, Object> map)
	{
		map.put("exportType", exportType);
		
		return GROUP_SELECT_PAGE;
	}
	
	@RequiresPermissions("StockAnalysis:view")
	@RequestMapping(value = "/exportExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportExcel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		Collection<SearchFilter> searchFilters = DynamicSpecifications.genSearchFilter(request);
		
		String grupType = request.getParameter(StockAnalysisService.GROUP_TYPE);
		String futureArriveName = request.getParameter(StockAnalysisService.FUTURE_ARRIVE_FIELD_NAME);
		String factArriveTime = request.getParameter(StockAnalysisService.FUTURE_ARRIVE_TIME_NAME);
		String[] groupOrderList = stockAnalysisService.getGroupFieldNamesList(request);
		
		ExportConfig<StockAnalysisInfoModel> exportConfig = stockAnalysisService.getExportConfig(searchFilters,
				groupOrderList, grupType, futureArriveName, factArriveTime);
		
		HSSFWorkbook hssfWorkbook = ExcelExportByTemplate.exportByData(exportConfig);
		
		stockAnalysisService.writerTotalData(exportConfig, hssfWorkbook, futureArriveName, factArriveTime);
		
		if (null != hssfWorkbook)
		{
			ExcelModelTool.setResponses(request, response, exportConfig.getName());
			OutputStream out = response.getOutputStream();
			hssfWorkbook.write(out);
		}
		
	}
	
	@RequiresPermissions("StockAnalysis:view")
	@RequestMapping(value = "/previewExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public String previewExcel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		Collection<SearchFilter> searchFilters = DynamicSpecifications.genSearchFilter(request);
		String grupType = request.getParameter(StockAnalysisService.GROUP_TYPE);
		String futureArriveName = request.getParameter(StockAnalysisService.FUTURE_ARRIVE_FIELD_NAME);
		String factArriveTime = request.getParameter(StockAnalysisService.FUTURE_ARRIVE_TIME_NAME);
		String[] groupOrderList = stockAnalysisService.getGroupFieldNamesList(request);
		
		ExportConfig<StockAnalysisInfoModel> exportConfig = stockAnalysisService.getExportConfig(searchFilters,
				groupOrderList, grupType, futureArriveName, factArriveTime);
		
		HSSFWorkbook hssfWorkbook = ExcelExportByTemplate.exportByData(exportConfig);
		
		stockAnalysisService.writerTotalData(exportConfig, hssfWorkbook, futureArriveName, factArriveTime);
		
		String path = stockAnalysisService.getExcelToHtml(hssfWorkbook);
		
		map.put("ExcelToHtmlPath", path);
		map.put("futureArriveName", futureArriveName);
		map.put("factArriveTime", factArriveTime);
		
		return PREVIEW_EXCEL_PAGE;
	}
	
}
