/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.salesstockdetails.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.util.persistence.SearchFilter;
import com.innshine.common.utils.ExportFieldsConfigUtils;
import com.innshine.productinfo.service.ProductInfoService;
import com.innshine.salesstockdetails.entity.SalesStockDetails;
import com.innshine.salesstockdetails.service.SalesStockDetailsService;

@Controller
@RequestMapping("/management/salesStockDetails")
public class SalesStockDetailsController
{
	private static final Logger log = LoggerFactory.getLogger(SalesStockDetailsController.class);
	@Autowired
	private SalesStockDetailsService salesStockDetailsService;
	@Autowired
	private ProductInfoService productInfoService;
	
	private static final String CREATE = "management/sales_stock_details/create";
	private static final String UPDATE = "management/sales_stock_details/update";
	private static final String LIST = "management/sales_stock_details/list";
	private static final String VIEW = "management/sales_stock_details/view";
	private static final String SELECT_FIELD = "management/product_info/select_fields";
	
	@RequiresPermissions("SalesStockDetails:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}SalesStockDetails。")
	@RequiresPermissions("SalesStockDetails:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid SalesStockDetails salesStockDetails)
	{
		salesStockDetailsService.saveOrUpdate(salesStockDetails);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { salesStockDetails.getId() }));
		return AjaxObject.newOk("SalesStockDetails添加成功！").toString();
	}
	
	@ModelAttribute("preloadSalesStockDetails")
	public SalesStockDetails preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			SalesStockDetails salesStockDetails = salesStockDetailsService.get(id);
			return salesStockDetails;
		}
		return null;
	}
	
	@RequiresPermissions("SalesStockDetails:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		SalesStockDetails salesStockDetails = salesStockDetailsService.get(id);
		map.put("salesStockDetails", salesStockDetails);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}SalesStockDetails的信息。")
	@RequiresPermissions("SalesStockDetails:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadSalesStockDetails") SalesStockDetails salesStockDetails)
	{
		salesStockDetailsService.saveOrUpdate(salesStockDetails);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { salesStockDetails.getId() }));
		return AjaxObject.newOk("SalesStockDetails修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}SalesStockDetails。")
	@RequiresPermissions("SalesStockDetails:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		salesStockDetailsService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("SalesStockDetails删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}SalesStockDetails。")
	@RequiresPermissions("SalesStockDetails:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			SalesStockDetails salesStockDetails = salesStockDetailsService.get(ids[i]);
			salesStockDetailsService.delete(salesStockDetails.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("SalesStockDetails删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("SalesStockDetails:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<SalesStockDetails> specification = DynamicSpecifications.bySearchFilter(request,
				SalesStockDetails.class);
		List<SalesStockDetails> salesStockDetailss = salesStockDetailsService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("salesStockDetailss", salesStockDetailss);
		
		return LIST;
	}
	
	@RequiresPermissions("SalesStockDetails:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		SalesStockDetails salesStockDetails = salesStockDetailsService.get(id);
		map.put("salesStockDetails", salesStockDetails);
		return VIEW;
	}
	
	@RequiresPermissions("SalesStockDetails:export")
	@RequestMapping(value = "/preDataExport", method = { RequestMethod.GET, RequestMethod.POST })
	public String preExportDataFile(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map)
			throws FileNotFoundException
	{
		Map<String, String> fieldConfigMap = ExportFieldsConfigUtils.getInstance().getFieldsConfig(
				SalesStockDetailsService.EXPORT_FIEDLS_CONFIG_KEY);
		map.put("fieldsMap", fieldConfigMap);
		map.put("inputId", "exportSelectFieldsId");
		map.put("submitFormName", "salesStockDetailsFormName");
		map.put("exportURL", "/management/salesStockDetails/dataExport");
		return SELECT_FIELD;
	}
	
	@RequiresPermissions("SalesStockDetails:export")
	@RequestMapping(value = "/dataExport", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportExcelData(HttpServletRequest request, HttpServletResponse response, Page page,
			Map<String, Object> map, String exportSelectFieldsName) throws IOException
	{

		Collection<SearchFilter> filters = DynamicSpecifications.genSearchFilter(request);
		productInfoService.modifyValuesByFieldName(filters);
		
		Specification<SalesStockDetails> specification = DynamicSpecifications.bySearchFilter(SalesStockDetails.class,filters);
		List<SalesStockDetails> salesStockDetails = salesStockDetailsService.findByExample(specification);
		
		salesStockDetailsService.exportExcel(salesStockDetails, exportSelectFieldsName, request, response);
		
	}
}
