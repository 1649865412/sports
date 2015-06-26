/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.ordermanager.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.innshine.nbsalesdetails.common.CreateFaildMessageFileUtils;
import com.innshine.ordermanager.entity.OrderFormInfo;
import com.innshine.ordermanager.entity.OrderPageInfo;
import com.innshine.ordermanager.service.OrderFormInfoService;
import com.innshine.ordermanager.service.OrderType;
import com.innshine.productinfo.service.ProductInfoService;
import com.innshine.productinfo.service.ProductInfoService.QueryType;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.validator.ValidatorsDataUtils;

@Controller
@RequestMapping("/management/orderManager")
public class OrderFormInfoController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderFormInfoController.class);
	
	@Autowired
	private OrderFormInfoService orderFormInfoService;
	
	@Autowired
	private ProductInfoService productInfoService;
	
	private static final String CREATE = "management/ordermanager/create";
	private static final String UPDATE = "management/ordermanager/update";
	private static final String LIST = "management/ordermanager/list";
	private static final String VIEW = "management/ordermanager/view";
	private static final String ORDER_TYPE = "management/ordermanager/order_type";
	private static final String UPLOAD = "management/ordermanager/upload";
	
	@RequiresPermissions("OrderFormInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}订货信息。")
	@RequiresPermissions("OrderFormInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid OrderFormInfo orderFormInfo, Long brandId)
	{
		// 效验数据的唯一性
		if (!ValidatorsDataUtils.isFieldsRule(OrderFormInfoService.VALIDATORS_RULE_FIELD_NAME, orderFormInfo))
		{
			return AjaxObject.newError("订货信息添加失败，必填字段未填写！").toString();
		}
		
		// 效验数据的唯一性
		if (!ValidatorsDataUtils.isUnique(orderFormInfo, orderFormInfoService))
		{
			return AjaxObject.newError("订货信息添加失败，该记录已存在！").toString();
		}
		
		saveOrUpdate(orderFormInfo, brandId);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { orderFormInfo.getId() }));
		return AjaxObject.newOk("订货信息添加成功！").toString();
	}
	
	private void saveOrUpdate(OrderFormInfo orderFormInfo, Long brandId)
	{
		if (null != orderFormInfo)
		{
			orderFormInfo.setUpdateTime(DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
			List<OrderFormInfo> orderFormInfos = orderFormInfoService.operatorOrderFormInfo(orderFormInfo,
					brandId);
			if (CollectionUtils.isNotEmpty(orderFormInfos))
			{
				orderFormInfoService.saveOrUpdate(orderFormInfos);
			}
		}
	}
	
	@ModelAttribute("preloadOrderFormInfo")
	public OrderFormInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			OrderFormInfo orderFormInfo = orderFormInfoService.get(id);
			return orderFormInfo;
		}
		return null;
	}
	
	@RequiresPermissions("OrderFormInfo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		OrderFormInfo orderFormInfo = orderFormInfoService.get(id);
		map.put("orderFormInfo", orderFormInfo);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}的订货信息。")
	@RequiresPermissions("OrderFormInfo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadOrderFormInfo") OrderFormInfo orderFormInfo, Long brandId)
	{
		// 效验数据的唯一性
		if (!ValidatorsDataUtils.isFieldsRule(OrderFormInfoService.VALIDATORS_RULE_FIELD_NAME, orderFormInfo))
		{
			return AjaxObject.newError("订货信息添加失败，必填字段未填写！").toString();
		}
		
		saveOrUpdate(orderFormInfo, brandId);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { orderFormInfo.getId() }));
		return AjaxObject.newOk("订货信息修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}订货信息。")
	@RequiresPermissions("OrderFormInfo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		orderFormInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("订货信息删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}订货信息。")
	@RequiresPermissions("OrderFormInfo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			OrderFormInfo orderFormInfo = orderFormInfoService.get(ids[i]);
			orderFormInfoService.delete(orderFormInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("订货信息删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("OrderFormInfo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map, String brandId)
	{
		Collection<SearchFilter> searchFilters = DynamicSpecifications.genSearchFilter(request);
		List<OrderPageInfo> orderFormInfos = orderFormInfoService.findByCustomPage(searchFilters, page);
		
		map.put("page", page);
		map.put("orderFormInfos", orderFormInfos);
		map.put("QUATERS", productInfoService.findByBrandIdAndFiledName(brandId, QueryType.QUATER));
		map.put("PRODUCT_TYPES", productInfoService.findByBrandIdAndFiledName(brandId,
				QueryType.PRODUCT_TYPE));
		return LIST;
	}
	
	@RequiresPermissions("OrderFormInfo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		OrderFormInfo orderFormInfo = orderFormInfoService.get(id);
		map.put("orderFormInfo", orderFormInfo);
		return VIEW;
	}
	
	@SuppressWarnings("unused")
	@RequiresPermissions("OrderFormInfo:upload")
	@RequestMapping(value = "/templateDownload", method = { RequestMethod.GET })
	private String preTemplateDownload(Map<String, Object> map)
	{
		map.put("orderTypes", OrderType.values());
		map.put("request_url", "templateDownload");
		map.put("isDownload", true);
		return ORDER_TYPE;
	}
	
	@RequiresPermissions("OrderFormInfo:upload")
	@RequestMapping(value = "/templateDownload", method = { RequestMethod.POST })
	public void templateDownload(HttpServletRequest request, HttpServletResponse response, String orderType)
			throws FileNotFoundException
	{
		String path = orderFormInfoService.getModelPath(orderType);
		
		ExcelFileUtils.fileExport(request, response, path);
		
	}
	
	@RequiresPermissions("OrderFormInfo:upload")
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String preUpload(Map<String, Object> map, String orderType)
	{
		map.put("orderType", orderType);
		return UPLOAD;
	}
	
	@RequiresPermissions("OrderFormInfo:upload")
	@RequestMapping(value = "/openUploadPage", method = RequestMethod.GET)
	public String openUpload(Map<String, Object> map)
	{
		map.put("isDownload", false);
		map.put("orderTypes", OrderType.values());
		return ORDER_TYPE;
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequiresPermissions("OrderFormInfo:upload")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> upload(@RequestParam MultipartFile[] files)
	{
		return ExcelFileUtils.fileExcelUpload(files);
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequiresPermissions("OrderFormInfo:upload")
	@RequestMapping(value = "/parseData", method = RequestMethod.POST)
	public ResponseEntity<String> parseFileData(@RequestParam String fileName, Long brandId, String orderType)
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
			Map<OrderFormInfo, String> failedMap = orderFormInfoService.parseExcelData(file, brandId, orderType);
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
			LOGGER.error(Exceptions.getStackTraceAsString(e));
			return new ResponseEntity<String>("对不起，导入失败。<br>可能原因：<br>1.文档解析失败 <br>2.文档内容为空<br>",
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
	private String joinMessage(Map<OrderFormInfo, String> failedMap)
	{
		StringBuffer buffer = new StringBuffer();
		if (MapUtils.isNotEmpty(failedMap))
		{
			// String prefix = "69码：";
			for (Entry<OrderFormInfo, String> entry : failedMap.entrySet())
			{
				if (null != entry)
				{
					OrderFormInfo orderFormInfo = entry.getKey();
					if (null != orderFormInfo && StringUtils.isNotEmpty(orderFormInfo.getUpccode()))
					{
						// if (buffer.indexOf(prefix) == -1)
						// {
						// buffer.append(prefix);
						// }
						
						buffer.append(orderFormInfo.getUpccode()).append(CreateFaildMessageFileUtils.BLANK_SYMOBL);
						buffer.append(entry.getValue()).append(CreateFaildMessageFileUtils.LINE_FEED_SYMBOL);
					}
				}
			}
		}
		return buffer.toString();
	}
	
	@Log(message = "导出文件成功。")
	@RequiresPermissions("OrderFormInfo:export")
	@RequestMapping(value = "/dataExport", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportExcelData(HttpServletRequest request, HttpServletResponse response, Page page,
			Map<String, Object> map) throws FileNotFoundException
	{
		Collection<SearchFilter> searchFilters = DynamicSpecifications.genSearchFilter(request);
		String filePath = orderFormInfoService.exportExcel(searchFilters);
		ExcelFileUtils.excelExport(request, response, filePath);
		
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
	
}
