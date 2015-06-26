/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.productinfo.controller;

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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.innshine.productinfo.entity.ProductInfo;
import com.innshine.productinfo.service.ProductInfoService;
import com.innshine.productinfo.service.ProductInfoService.QueryType;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.FileUtils;
import com.utils.excel.GetImgFromExcel;
import com.utils.validator.ValidatorsDataUtils;

@Controller
@RequestMapping("/management/productInfo")
public class ProductInfoController
{
	private static final Logger LOG = LoggerFactory.getLogger(ProductInfoController.class);
	@Autowired
	private ProductInfoService productInfoService;
	
	private static final String CREATE = "management/product_info/create";
	private static final String UPDATE = "management/product_info/update";
	private static final String LIST = "management/product_info/list";
	private static final String VIEW = "management/product_info/view";
	private static final String UPLOAD = "management/product_info/upload";
	private static final String[] IMAGE_FILE_TYPE = new String[] { "png", "jpeg", "jpg", "bmp" };
	private static final String UPLOAD_IMAGE = "management/product_info/upload_image";
	private static final String SELECT_FIELD = "management/product_info/select_fields";
	
	@RequiresPermissions("ProductInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}产品信息。")
	@RequiresPermissions("ProductInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid ProductInfo productInfo)
	{
		setUpdateTimeAndImagePath(productInfo);
		
		// 效验数据的唯一性
		if (!ValidatorsDataUtils.isUnique(productInfo, productInfoService))
		{
			return AjaxObject.newError("产品信息添加失败，数据已存在！").toString();
		}
		
		productInfoService.saveOrUpdate(productInfo);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { productInfo.getId() }));
		return AjaxObject.newOk("产品信息添加成功！").toString();
	}
	
	 
	
	/**
	 * 移动图片，并给image属性赋值，图片的相对路径
	 * 
	 * @param productInfo
	 */
	private void setUpdateTimeAndImagePath(ProductInfo productInfo)
	{
		if (null != productInfo)
		{
			String imagePath = productInfo.getImage();
			moveFile(imagePath);
			
			if (StringUtils.isNotEmpty(imagePath))
			{
				if (imagePath.toUpperCase().indexOf(GetImgFromExcel.FILE_TEMP_DIR.toUpperCase()) == -1)
				{
					productInfo.setImage(GetImgFromExcel.FILE_TEMP_DIR
							+ DateUtils.getNow(DateUtils.SIMPLE_DEFAULT_FORMAT) + "/" + productInfo.getImage());
				}
			}
			productInfo.setUpdateTime(DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		}
	}
	
	/**
	 * 把图片从tmp文件夹，移动至带年-月-日格式的文件夹内
	 * 
	 * @param image
	 *        图片名称
	 */
	private void moveFile(String image)
	{
		if (StringUtils.isNotBlank(image))
		{
			try
			{
				File sourceFile = new File(ExcelFileUtils
						.getExcelModelPath(GetImgFromExcel.FILE_TEMP_DIR + "tmp" + "/")
						+ image);
				File destFile = new File(ExcelFileUtils.getExcelModelPath(GetImgFromExcel.FILE_TEMP_DIR)
						+ DateUtils.getNow(DateUtils.SIMPLE_DEFAULT_FORMAT) + File.separator + image);
				
				if (ExcelFileUtils.createDir(destFile.getPath()))
				{
					ExcelFileUtils.deleteFile(destFile);
					
					if (sourceFile.exists())
					{
						org.apache.commons.io.FileUtils.moveFile(sourceFile, destFile);
					}
					
					ExcelFileUtils.deleteFile(sourceFile);
				}
			}
			catch (IOException e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
	}
	
	@ModelAttribute("preloadProductInfo")
	public ProductInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			ProductInfo productInfo = productInfoService.get(id);
			return productInfo;
		}
		return null;
	}
	
	@RequiresPermissions("ProductInfo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		ProductInfo productInfo = productInfoService.get(id);
		map.put("productInfo", productInfo);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}产品的信息。")
	@RequiresPermissions("ProductInfo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadProductInfo") ProductInfo productInfo)
	{
		setUpdateTimeAndImagePath(productInfo);
		productInfoService.saveOrUpdate(productInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { productInfo.getId() }));
		return AjaxObject.newOk("产品信息修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}产品信息。")
	@RequiresPermissions("ProductInfo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		productInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("产品信息删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}产品信息。")
	@RequiresPermissions("ProductInfo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			ProductInfo productInfo = productInfoService.get(ids[i]);
			productInfoService.delete(productInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("产品信息删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("ProductInfo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map, String brandId)
	{
		Specification<ProductInfo> specification = DynamicSpecifications.bySearchFilter(request, ProductInfo.class);
		List<ProductInfo> productInfos = productInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("productInfos", productInfos);
		map.put("QUATERS", productInfoService.findByBrandIdAndFiledName(brandId, QueryType.QUATER));
		map.put("PRODUCT_TYPES", productInfoService.findByBrandIdAndFiledName(brandId,
				QueryType.PRODUCT_TYPE));
		map.put("SERIES", productInfoService.findByBrandIdAndFiledName(brandId, QueryType.SERIES));
		
		return LIST;
	}
	
	@RequiresPermissions("ProductInfo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		ProductInfo productInfo = productInfoService.get(id);
		map.put("productInfo", productInfo);
		return VIEW;
	}
	
	@RequiresPermissions("ProductInfo:upload")
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String preUpload(Map<String, Object> map, String requestUrl)
	{
		map.put("request_url", requestUrl);
		return UPLOAD;
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequiresPermissions("ProductInfo:upload")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> upload(@RequestParam MultipartFile[] files)
	{
		return ExcelFileUtils.fileExcelUpload(files);
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public ResponseEntity<String> uploadImage(@RequestParam MultipartFile[] files, Map<String, Object> map)
	{
		return fileExcelUpload(files);
	}
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.GET)
	public String preUploadImage()
	{
		return UPLOAD_IMAGE;
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequiresPermissions("ProductInfo:upload")
	@RequestMapping(value = "/parseData", method = { RequestMethod.GET, RequestMethod.POST })
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
		
		Map<ProductInfo, String> failedMap = productInfoService.parseExcelData(file, brandId);
		
		try
		{
			String failedStr = joinMessage(failedMap);
			
			if (StringUtils.isNotEmpty(failedStr))
			{
				File errorMessageFile = CreateFaildMessageFileUtils.exprotErrorInfo(failedStr);
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
	 * 拼接错误提示信息
	 * 
	 * @param failedMap
	 *        记录的失败结 果集
	 * @return 拼接好的失败提示信息
	 */
	private String joinMessage(Map<ProductInfo, String> failedMap)
	{
		if (MapUtils.isNotEmpty(failedMap))
		{
			StringBuffer buffer = new StringBuffer();
			for (Entry<ProductInfo, String> entry : failedMap.entrySet())
			{
				if (null != entry)
				{
					String message = entry.getValue();
					ProductInfo productInfo = entry.getKey();
					if (null != productInfo)
					{
						buffer.append(productInfo.getProductUpccode()).append(CreateFaildMessageFileUtils.BLANK_SYMOBL);
						buffer.append(message).append(CreateFaildMessageFileUtils.LINE_FEED_SYMBOL);
					}
				}
			}
			
			return buffer.toString();
		}
		
		return null;
	}
	
	/**
	 * 处理Excel文件上传
	 * <p>
	 * 
	 * @param files
	 *        文件列表
	 * @param map
	 * @return ResponseEntity<String>
	 */
	public static ResponseEntity<String> fileExcelUpload(MultipartFile[] files)
	{
		String uploadPath = ExcelFileUtils.getExcelModelPath(GetImgFromExcel.FILE_TEMP_DIR + "tmp/");
		
		if (!ExcelFileUtils.createDir(uploadPath))
		{
			return new ResponseEntity<String>("创建文件上传路径失败，" + uploadPath, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		for (MultipartFile file : files)
		{
			String fileExt = FileUtils.getFileExt(file.getOriginalFilename());
			if (!ArrayUtils.contains(IMAGE_FILE_TYPE, fileExt))
			{
				return new ResponseEntity<String>("只允许上传" + ArrayUtils.toString(ExcelFileUtils.FILE_TYPE) + "格式的文件！",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if (file.getSize() > 25165824)
			{
				return new ResponseEntity<String>("只允许上传3M以内的图标！", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			File uploadedFile = new File(uploadPath, file.getOriginalFilename());
			if (uploadedFile.exists())
			{
				uploadedFile.delete();
			}
			
			try
			{
				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
				LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { file.getOriginalFilename() }));
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
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
				ProductInfoService.EXPORT_FIEDLS_CONFIG_KEY);
		map.put("fieldsMap", fieldConfigMap);
		map.put("inputId", "exportSelectFieldsId");
		map.put("submitFormName", "productInfoFormName");
		map.put("exportURL", "/management/productInfo/dataExport");
		return SELECT_FIELD;
	}
	
	@RequestMapping(value = "/dataExport", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportDataFile(HttpServletRequest request, HttpServletResponse response, String exportSelectFieldsName)
			throws IOException
	{
		Collection<SearchFilter> filters = DynamicSpecifications.genSearchFilter(request);
		productInfoService.modifyValuesByFieldName(filters);
		
		Specification<ProductInfo> specification = DynamicSpecifications.bySearchFilter(ProductInfo.class, filters);
		List<ProductInfo> productInfos = productInfoService.findByExample(specification);
		
		productInfoService.exportDataFile(productInfos, exportSelectFieldsName, request, response);
	}
}
