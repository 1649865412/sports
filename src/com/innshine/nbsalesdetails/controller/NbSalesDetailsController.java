/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.nbsalesdetails.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.util.persistence.SearchFilter;
import com.innshine.common.utils.ExportFieldsConfigUtils;
import com.innshine.nbsalesdetails.common.CreateFaildMessageFileUtils;
import com.innshine.nbsalesdetails.entity.NbSalesDetails;
import com.innshine.nbsalesdetails.service.NbSalesDetailsService;
import com.innshine.nbsalesdetails.timetask.NBSalesInfoDataCleaningService;
import com.innshine.ordermanager.entity.OrderFormInfo;
import com.innshine.ordermanager.service.OrderFormInfoService;
import com.innshine.productinfo.service.ProductInfoService;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.validator.ValidatorsDataUtils;

@Controller
@RequestMapping("/management/nbSalesDetails")
public class NbSalesDetailsController
{
	private static final Logger LOG = LoggerFactory.getLogger(NbSalesDetailsController.class);
	
	@Autowired
	private NbSalesDetailsService nbSalesDetailsService;
	
	@Autowired(required = true)
	private NBSalesInfoDataCleaningService nbSalesInfoDataCleaningService;
	
	@Autowired
	private OrderFormInfoService orderFormInfoService;
	
	@Autowired(required = true)
	private ProductInfoService productInfoService;
	
	private static final String CREATE = "management/nb_sales_details/create";
	private static final String UPDATE = "management/nb_sales_details/update";
	private static final String LIST = "management/nb_sales_details/list";
	private static final String VIEW = "management/nb_sales_details/view";
	private static final String UPLOAD = "management/nb_sales_details/upload";
	private static final String SELECT_FIELD = "management/product_info/select_fields";
	
	@RequiresPermissions("NbSalesDetails:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}销售信息。")
	@RequiresPermissions("NbSalesDetails:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid NbSalesDetails nbSalesDetails)
	{
		// 效验必填字段时否为空
		if (!ValidatorsDataUtils.isFieldsRule(NbSalesDetailsService.VALIDATORS_RULE_FIELD_NAME, nbSalesDetails))
		{
			return AjaxObject.newError("销售信息添加失败，必填字段未填写！").toString();
		}
		// 效验必填字段时否为空
		if (!ValidatorsDataUtils.isUnique(nbSalesDetails, nbSalesDetailsService))
		{
			return AjaxObject.newError("销售信息添加失败，数据库中已存在该记录！").toString();
		}
		
		if (ValidatorsDataUtils.isUnique(nbSalesDetailsService.consQueryObjOrderInfo(nbSalesDetails),
				orderFormInfoService))
		{
			return AjaxObject.newError("销售信息添加失败，请检查，订货管理中无对应库存信息！").toString();
		}
		
		try
		{
			setUpdateTime(nbSalesDetails);
			nbSalesDetailsService.saveOrUpdate(nbSalesDetails);
			
			modiftyOrderFormStockInfo(nbSalesDetails);
		}
		catch (DataIntegrityViolationException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
			return AjaxObject.newError("销售信息修改失败，请检查数据是否已存在！").toString();
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
			return AjaxObject.newError("销售信息修改失败！" + Exceptions.getStackTraceAsString(e)).toString();
			// LOG.error(Exceptions.getStackTraceAsString(e));
			// return AjaxObject.newError("销售信息修改失败，未知异常！").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { nbSalesDetails.getId() }));
		return AjaxObject.newOk("销售信息添加成功！").toString();
	}
	
	private void modiftyOrderFormStockInfo(NbSalesDetails nbSalesDetails)
	{
		OrderFormInfo orderFormInfo = nbSalesDetailsService.cosStockInfo(nbSalesDetails);
		if (null != orderFormInfo)
		{
			orderFormInfoService.saveOrUpdate(orderFormInfo);
		}
	}
	
	private void setUpdateTime(NbSalesDetails nbSalesDetails)
	{
		if (null != nbSalesDetails)
		{
			nbSalesDetails.setUpdateTime(DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		}
	}
	
	@ModelAttribute("preloadNbSalesDetails")
	public NbSalesDetails preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			NbSalesDetails nbSalesDetails = nbSalesDetailsService.get(id);
			return nbSalesDetails;
		}
		return null;
	}
	
	@RequiresPermissions("NbSalesDetails:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		NbSalesDetails nbSalesDetails = nbSalesDetailsService.get(id);
		map.put("nbSalesDetails", nbSalesDetails);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}NbSalesDetails的信息。")
	@RequiresPermissions("NbSalesDetails:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadNbSalesDetails") NbSalesDetails nbSalesDetails)
	{
		setUpdateTime(nbSalesDetails);
		
		// 效验必填字段时否为空
		if (!ValidatorsDataUtils.isFieldsRule(NbSalesDetailsService.VALIDATORS_RULE_FIELD_NAME, nbSalesDetails))
		{
			return AjaxObject.newError("销售信息添加失败，必填字段未填写！").toString();
		}
		
		try
		{
			nbSalesDetailsService.saveOrUpdate(nbSalesDetails);
			
			modiftyOrderFormStockInfo(nbSalesDetails);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
			return AjaxObject.newError("销售信息修改失败！" + Exceptions.getStackTraceAsString(e)).toString();
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { nbSalesDetails.getId() }));
		return AjaxObject.newOk("销售信息修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}NbSalesDetails。")
	@RequiresPermissions("NbSalesDetails:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		nbSalesDetailsService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("销售信息删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}NbSalesDetails。")
	@RequiresPermissions("NbSalesDetails:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			NbSalesDetails nbSalesDetails = nbSalesDetailsService.get(ids[i]);
			nbSalesDetailsService.delete(nbSalesDetails.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("销售信息删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("NbSalesDetails:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<NbSalesDetails> specification = DynamicSpecifications.bySearchFilter(request,
				NbSalesDetails.class);
		List<NbSalesDetails> nbSalesDetailss = nbSalesDetailsService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("nbSalesDetailss", nbSalesDetailss);
		
		return LIST;
	}
	
	@RequiresPermissions("NbSalesDetails:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		NbSalesDetails nbSalesDetails = nbSalesDetailsService.get(id);
		map.put("nbSalesDetails", nbSalesDetails);
		return VIEW;
	}
	
	@RequiresPermissions("NbSalesDetails:upload")
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String preUpload(Map<String, Object> map)
	{
		return UPLOAD;
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequiresPermissions("NbSalesDetails:upload")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> upload(@RequestParam MultipartFile[] files)
	{
		return ExcelFileUtils.fileExcelUpload(files);
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequiresPermissions("NbSalesDetails:upload")
	@RequestMapping(value = "/parseData", method = RequestMethod.POST)
	public ResponseEntity<String> parseFileData(@RequestParam String fileName, Long brandId)
	{
		String uploadPath = ExcelFileUtils.FILE_TEMP_DIR + ExcelFileUtils.UPLOAD
				+ ExcelFileUtils.decodeFileName(fileName);
		
		// 获取文件路径
		File file = new File(uploadPath);
		
		if (!file.exists())
		{
			return new ResponseEntity<String>("上传文件不存在！!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try
		{
			Map<NbSalesDetails, String> failedMap = nbSalesDetailsService.parseExcelData(file, brandId);
			String message = joinMessage(failedMap);
			if (StringUtils.isNotEmpty(message))
			{
				File errorMessageFile = CreateFaildMessageFileUtils.exprotErrorInfo(message);
				return new ResponseEntity<String>(null != errorMessageFile ? errorMessageFile.getName() : null,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
			return new ResponseEntity<String>("对不起，导入失败。可能原因：<\br>1.文档解析失败 <\br>2.文档内容为空<\br>" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		finally
		{
			ExcelFileUtils.deleteFile(file);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * 拼接提示消息
	 * 
	 * @param failedMap
	 *        记录失败的结果列表
	 * @return String 提示信息
	 */
	private String joinMessage(Map<NbSalesDetails, String> failedMap)
	{
		StringBuffer buffer = new StringBuffer();
		if (MapUtils.isNotEmpty(failedMap))
		{
			for (Entry<NbSalesDetails, String> entry : failedMap.entrySet())
			{
				if (null != entry)
				{
					NbSalesDetails nbSalesDetails = entry.getKey();
					
					if (null != nbSalesDetails && StringUtils.isNotEmpty(nbSalesDetails.getUpccode()))
					{
						buffer.append(nbSalesDetails.getUpccode()).append(CreateFaildMessageFileUtils.BLANK_SYMOBL);
						buffer.append(entry.getValue()).append(CreateFaildMessageFileUtils.LINE_FEED_SYMBOL);
					}
				}
			}
		}
		return buffer.toString();
	}
	
	@RequiresPermissions("NbSalesDetails:export")
	@RequestMapping(value = "/dataExport", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportExcelData(HttpServletRequest request, HttpServletResponse response, Page page,
			Map<String, Object> map, String exportSelectFieldsName) throws IOException
	{
		Collection<SearchFilter> filters = DynamicSpecifications.genSearchFilter(request);
		productInfoService.modifyValuesByFieldName(filters);
		
		Specification<NbSalesDetails> specification = DynamicSpecifications.bySearchFilter(NbSalesDetails.class,
				filters);
		List<NbSalesDetails> nbSalesDetails = nbSalesDetailsService.findByExample(specification);
		
		nbSalesDetailsService.exportExcel(nbSalesDetails, exportSelectFieldsName, request, response);
	}
	
	@Log(message = "强制刷新数据成功！。")
	@RequiresPermissions("NbSalesDetails:upload")
	@RequestMapping(value = "/forceRefreshData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String forceRefreshData()
	{
		AjaxObject ajaxObject = new AjaxObject();
		ajaxObject.setCallbackType("");
		try
		{
			nbSalesInfoDataCleaningService.dataCleaningService(true);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("对不起，强制刷新失败，请重试！");
			
		}
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_SUCCESS);
		ajaxObject.setMessage("强制刷新成功！");
		return ajaxObject.toString();
	}
	
	@RequestMapping(value = "/exprotErrorInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public void exprotErrorInfo(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map,
			String exprotErrorInfoFileName) throws FileNotFoundException
	{
		File file = CreateFaildMessageFileUtils.getErrorMessageFile(exprotErrorInfoFileName);
		
		String filePath = null;
		if (null != file)
		{
			filePath = file.getPath();
		}
		ExcelFileUtils.excelExport(request, response, filePath, null);
		
	}
	
	@RequestMapping(value = "/preDataExport", method = { RequestMethod.GET, RequestMethod.POST })
	public String preExportDataFile(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map)
			throws FileNotFoundException
	{
		Map<String, String> fieldConfigMap = ExportFieldsConfigUtils.getInstance().getFieldsConfig(
				NbSalesDetailsService.EXPORT_FIEDLS_CONFIG_KEY);
		map.put("fieldsMap", fieldConfigMap);
		map.put("inputId", "exportSelectFieldsId");
		map.put("submitFormName", "nbSalesDetailsFormName");
		map.put("exportURL", "/management/nbSalesDetails/dataExport");
		return SELECT_FIELD;
	}
}
