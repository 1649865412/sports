/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.productinfo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.SqlDao;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.base.util.persistence.SearchFilter;
import com.innshine.common.LogInfoOrganizationFilter;
import com.innshine.common.utils.ExportFieldsConfigUtils;
import com.innshine.productinfo.dao.ProductInfoDAO;
import com.innshine.productinfo.entity.ProductInfo;
import com.innshine.productinfo.model.ProductInfoModel;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.innshine.productinfo.utils.ProductInfoExportUtils;
import com.innshine.productinfo.utils.TemplateConstants;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.excel.ExcelToBeanUtils;
import com.utils.validator.ValidatorsDataUtils;

@Service
@Transactional
public class ProductInfoService
{
	private static final String COMMAN_SYBOL = ",";
	
	/**
	 * 导出的文件名
	 */
	private static final String EXPORT_FILE_NAME = "产品信息.xlsx";
	
	/**
	 * 配置文件中配置的导出字段列表KEY
	 */
	public static final String EXPORT_FIEDLS_CONFIG_KEY = "product_info";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ProductInfoService.class);
	
	/**
	 * 图片字段列表
	 */
	private static final String[] IMAGE_FIELD_NAMS = new String[] { "image" };
	
	/**
	 * 必填字段列表
	 */
	private static final String[] VALIDATORS_FIELD_NAME = new String[] { "productUpccode", "tagPrice" ,"materialNumber"};
	
	/**
	 * QueryType 下拉框填充SQL 参数名称与QueryType 枚举对应
	 */
	private static final String QUERY_SELECT_FIELD_NAME = "fieldName";
	
	/**
	 * 下拉框填充SQL
	 */
	private static final String PRODUCTINFO_SELECT_SQL_KEY = "productinfo.select.sql";
	
	/**
	 * 分销类型、品类、系列、产品功效、 产品类型
	 * 
	 * @version 1.0 </br>最后修改人 无
	 */
	public enum QueryType
	{
		SERIES, PRODUCT_TYPE, STATUS, INLINE_OR2NDLINE, PRODUCT_LF_PF, QUATER, PRODUCT_FUNCTION, PRODUCT_BRAND
	}
	
	@Autowired
	private SqlDao sqlDao;
	
	@Autowired
	private ProductInfoDAO productInfoDAO;
	
	public ProductInfo get(Long id)
	{
		return productInfoDAO.findOne(id);
	}
	
	public void saveOrUpdate(ProductInfo productInfo)
	{
		productInfoDAO.save(productInfo);
	}
	
	public void saveOrUpdate(List<ProductInfo> productInfos)
	{
		productInfoDAO.save(productInfos);
	}
	
	public void delete(Long id)
	{
		deleteFile(productInfoDAO.findOne(id));
		productInfoDAO.delete(id);
	}
	
	public void delete(List<ProductInfo> productInfos)
	{
		if (CollectionUtils.isNotEmpty(productInfos))
		{
			productInfoDAO.deleteInBatch(productInfos);
		}
	}
	
	public List<ProductInfo> findAll(Page page)
	{
		setPageAndFiled(page);
		org.springframework.data.domain.Page<ProductInfo> springDataPage = productInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	private void setPageAndFiled(Page page)
	{
		if (null != page)
		{
			if (StringUtils.isEmpty(page.getOrderField()) && StringUtils.isEmpty(page.getOrderDirection()))
			{
				page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
				page.setOrderField("updateTime");
			}
		}
		
	}
	
	public List<ProductInfo> findByExample(Specification<ProductInfo> specification, Page page)
	{
		setPageAndFiled(page);
		org.springframework.data.domain.Page<ProductInfo> springDataPage = productInfoDAO.findAll(specification,
				PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<ProductInfo> findByExample(Specification<ProductInfo> specification)
	{
		return productInfoDAO.findAll(specification);
	}
	
	/**
	 * 解析Excel文件并返回相应失败结果，如正常则返回空
	 * <p>
	 * 
	 * @param file
	 *        文件路径
	 * @param brandId
	 *        所属组织编号
	 * @return Map< ProductInfo, String > key : ProductInfo 基础产品信息 value:错误提示
	 */
	public Map<ProductInfo, String> parseExcelData(File file, Long brandId)
	{
		try
		{
			List<ProductInfo> productInfos = ExcelToBeanUtils.readFilelModel(file, ProductInfoModel.class,
					ProductInfo.class, IMAGE_FIELD_NAMS, true);
			
			Map<ProductInfo, String> failedMap = new HashMap<ProductInfo, String>();
			
			// 去除重复数据记录
			validatorRepeatData(productInfos, failedMap);
			
			// 效验数据完整性
			validatorData(productInfos, brandId, failedMap);
			
			// 数据正式入库
			saveOrUpdate(productInfos);
			
			return failedMap;
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		return null;
	}
	
	/**
	 * 效验数据的完整性，如唯一、必填项等 ，并返回最终的失败结果集
	 * 
	 * @param productInfos
	 *        需要效验的原始数据结果集
	 * @param brandId
	 *        所属组织编号
	 * @param failedMap
	 *        记录失败的结果集
	 * @return Map< ProductInfo, String > key : ProductInfo 基础产品信息 value:错误提示
	 */
	private void validatorData(List<ProductInfo> productInfos, Long brandId, Map<ProductInfo, String> failedMap)
	{
		if (CollectionUtils.isNotEmpty(productInfos))
		{
			String currTime = DateUtils.getNow(DateUtils.DEFAULT_FORMAT);
			// 记录数据库中需要删除的对象列表
			List<ProductInfo> deleteList = new ArrayList<ProductInfo>();
			for (int i = productInfos.size() - 1; i >= 0; i--)
			{
				ProductInfo productInfo = productInfos.get(i);
				
				// 继续效验的标识
				boolean flag = false;
				if (null != productInfo)
				{
					// 效验是否有空行的情况，解析Excel文件时，会存在空对象，所有内容为空
					if (!ValidatorsDataUtils.isObjectNull(productInfo) && !flag)
					{
						flag = true;
					}
					
					// 效验必填字段时否为空
					if (!ValidatorsDataUtils.isFieldsRule(VALIDATORS_FIELD_NAME, productInfo) && !flag)
					{
						flag = true;
						failedMap.put(productInfo, "必填字段未填写或填写错误!");
						
					}
					
					productInfo.setUpdateTime(currTime);
					productInfo.setBrandId(brandId);
					// 效验数据的唯一性
					if (!ValidatorsDataUtils.isUnique(productInfo, this) && !flag)
					{
						// 默认支持批量修改
						if (com.innshine.common.Constants.DEFAULT_BATCH_MODIFTY)
						{
							ProductInfo productInfo2 = findByUnique(productInfo);
							if (productInfo2 != null)
							{
								deleteList.add(productInfo2);
							}
						}
						else
						{
							failedMap.put(productInfo, "数据已存在!");
							flag = true;
						}
					}
					
					if (flag)
					{
						productInfos.remove(productInfo);
					}
				}
			}
			
			deleteByUnique(deleteList);
			deleteFile(deleteList);
			
		}
	}
	
	/**
	 * 删除对应图片文件
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	private void deleteFile(Object obj)
	{
		if (null != obj)
		{
			if (obj instanceof List)
			{
				List<ProductInfo> productInfos = (List<ProductInfo>) obj;
				if (CollectionUtils.isNotEmpty(productInfos))
				{
					for (ProductInfo productInfo : productInfos)
					{
						if (null != productInfo)
						{
							if (StringUtils.isNotEmpty(productInfo.getImage()))
							{
								
								ExcelFileUtils.deleteFile(new File(ExcelFileUtils.getExcelModelPath("")
										+ productInfo.getImage()));
							}
						}
					}
				}
			}
			else if (obj instanceof ProductInfo)
			{
				ProductInfo productInfo = (ProductInfo) obj;
				if (StringUtils.isNotEmpty(productInfo.getImage()))
				{
					
					ExcelFileUtils.deleteFile(new File(ExcelFileUtils.getExcelModelPath("") + productInfo.getImage()));
				}
			}
		}
		
	}
	
	/**
	 * 效验数据是否唯一
	 * 
	 * @param productInfo
	 *        需要效验的数据对象
	 * @return ProductInfo为null则认为唯一，故则相反
	 */
	public ProductInfo findByUnique(ProductInfo productInfo)
	{
		List<ProductInfo> productInfos = findBySkuAndBrandIdAndMaterialNumber(productInfo.getProductUpccode(), productInfo
				.getBrandId(),productInfo.getMaterialNumber());
		if (CollectionUtils.isNotEmpty(productInfos))
		{
			return productInfos.get(0);
		}
		
		return null;
	}
	
	public List<ProductInfo> findBySkuAndBrandIdAndMaterialNumber(String upc, Long brandId,String materialNumber )
	{
		return productInfoDAO.findByProductUpccodeAndBrandIdAndMaterialNumber(upc, brandId,materialNumber);
	}
	
	/**
	 * 删除当前已存在数据，默认为删除 + 更新操作，先删后加
	 * 
	 * @param deleteList
	 *        需要删除的对象列表
	 */
	private void deleteByUnique(List<ProductInfo> deleteList)
	{
		if (null != deleteList && !deleteList.isEmpty())
		{
			try
			{
				delete(deleteList);
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			
		}
	}
	
	/**
	 * 效验导入的原始数据中，存在重复数据问题
	 * 
	 * @param productInfs
	 *        原始数据集合
	 * @param failedMap
	 *        记录失败的结果集
	 */
	private void validatorRepeatData(List<ProductInfo> productInfs, Map<ProductInfo, String> failedMap)
	{
		if (CollectionUtils.isNotEmpty(productInfs))
		{
			for (int i = productInfs.size() - 1; i >= 0; i--)
			{
				ProductInfo productInfo = productInfs.get(i);
				
				if (null != productInfo)
				{
					// 计数器，记录重复次数
					int count = 0;
					
					for (int j = productInfs.size() - 1; j >= 0; j--)
					{
						ProductInfo tmpProductInfo = productInfs.get(j);
						if (null != tmpProductInfo)
						{
							if ( checkUpCode( productInfo, tmpProductInfo)&&checkMaterNumber( productInfo, tmpProductInfo))
							{
								count++;
							}
						}
						
					}
					
					if (count >= 2)
					{
						failedMap.put(productInfo, "该数据存在重复记录！");
						
						// 清除第一个重复记录
						productInfs.remove(i);
					}
				}
			}
		}
	}
	
	/**
	 * 功能:69码验证
	 * <p>作者 杨荣忠 2014-10-27 下午04:58:07
	 * @param productInfo
	 * @param tmpProductInfo
	 * @return
	 */
	public boolean checkUpCode(ProductInfo productInfo,ProductInfo tmpProductInfo){
		boolean flag=false;
		if (StringUtils.isNotBlank(productInfo.getProductUpccode())
				&& productInfo.getProductUpccode().equalsIgnoreCase(
						tmpProductInfo.getProductUpccode()))
		{
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 功能:款号验证
	 * <p>作者 杨荣忠 2014-10-27 下午04:58:10
	 * @param productInfo
	 * @param tmpProductInfo
	 * @return
	 */
	public boolean checkMaterNumber(ProductInfo productInfo,ProductInfo tmpProductInfo){
		boolean flag=false;
		if (StringUtils.isNotBlank(productInfo.getMaterialNumber())
				&& productInfo.getMaterialNumber().equalsIgnoreCase(
						tmpProductInfo.getMaterialNumber()))
		{
			flag=true;
		}
		return flag;
	}
	
	
	
	
	/**
	 * 导出数据
	 * 
	 * @param productInfos
	 *        需要导出的原始数据列表
	 * @return 完整的文件路径
	 */
	public String exportDataFile(List<ProductInfo> productInfos)
	{
		if (null != productInfos)
		{
			Class<ProductInfoModel> prClass = ProductInfoModel.class;
			List<ProductInfoModel> productInfoModels = ExcelFileUtils.reflectSetValue(productInfos, prClass,
					ProductInfo.class);
			
			return ProductInfoExportUtils.exportByTemplateToExcel(new File(ExcelFileUtils
					.getExcelModelPath(TemplateConstants.EXPORT_PRODUCT_MODEL_PATH_NB)), productInfoModels, prClass
					.getDeclaredFields(), true, IMAGE_FIELD_NAMS);
		}
		
		return null;
	}
	
	/**
	 * 
	 * 根据前端页面选择的字段列表，导出对应数据
	 * 
	 * @param productInfos
	 *        需要导出的原始数据列表功能
	 * @param exportSelectFieldsName
	 *        选择需要导出的字段列表
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	public void exportDataFile(List<ProductInfo> productInfos, String exportSelectFieldsName,
			HttpServletRequest request, HttpServletResponse response) throws IOException, FileNotFoundException
	{
		if (null != productInfos)
		{
			if (StringUtils.isNotEmpty(exportSelectFieldsName))
			{
				String[] regSelectFieldsNames = exportSelectFieldsName.split(COMMAN_SYBOL);
				Vector<String> header = getHeader(regSelectFieldsNames);
				Vector<String> fieldNames = new Vector<String>();
				CollectionUtils.addAll(fieldNames, regSelectFieldsNames);
				
				Workbook workbook = ProductInfoExportUtils.exportByTemplateToExcel(productInfos, fieldNames, header,
						true, IMAGE_FIELD_NAMS);
				
				ExcelFileUtils.excelExport(workbook, request, response, EXPORT_FILE_NAME);
			}
			else
			{
				try
				{
					ExcelFileUtils.excelExport(request, response, exportDataFile(productInfos));
				}
				catch (FileNotFoundException e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
					throw e;
				}
			}
		}
		
	}
	
	/**
	 * 构造标题头对象
	 * 
	 * @param exportSelectFieldsName
	 *        以逗号分割的字段数组
	 * @return Vector< String >
	 */
	private Vector<String> getHeader(String[] exportSelectFieldsName)
	{
		if (ArrayUtils.isNotEmpty(exportSelectFieldsName))
		{
			
			Map<String, String> fieldMap = ExportFieldsConfigUtils.getInstance().getFieldsConfig(
					EXPORT_FIEDLS_CONFIG_KEY);
			
			if (MapUtils.isNotEmpty(fieldMap))
			{
				Vector<String> header = new Vector<String>();
				for (String fieldName : exportSelectFieldsName)
				{
					if (StringUtils.isNotEmpty(fieldName))
					{
						String title = fieldMap.get(fieldName);
						
						if (StringUtils.isNotEmpty(title))
						{
							header.add(title);
						}
					}
				}
				
				return header;
				
			}
			
		}
		
		return null;
	}
	
	/**
	 * 根据用户所属组织，查询对应品类、系列等值
	 * 
	 * @param brandId
	 *        所属品牌编号
	 * @param queryType
	 *        品类、系列等枚举类 QueryType对应
	 * @return List<ProductInfo>
	 */
	public List<ProductInfo> findByBrandIdAndFiledName(String brandId, QueryType queryType)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put(QUERY_SELECT_FIELD_NAME, queryType.toString());
		
		if (StringUtils.isNotBlank(brandId))
		{
			param.put(LogInfoOrganizationFilter.SAVE_FIELD_NAME_BRAND_ID, brandId);
		}
		try
		{
			return sqlDao.queryListBySql(param, PRODUCTINFO_SELECT_SQL_KEY, ProductInfo.class);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 修改search_IN_id参数值，默认值为1,2，需要转换为Object[]对象数组<br/>
	 * 注：只修改为IN查询的参数，转换为String[]数组，其它不处理
	 * 
	 * @param filters
	 *        前端页面的参数对象
	 */
	public void modifyValuesByFieldName(Collection<SearchFilter> filters)
	{
		modifyValuesByFieldName(filters, null);
	}
	
	/**
	 * 修改search_IN_id参数值，默认值为1,2，需要转换为Object[]对象数组<br/>
	 * 注：只修改为IN查询的参数，转换为String[]数组，其它不处理
	 * 
	 * @param eqFieldName
	 *        字段名称，默认为ID
	 * @param filters
	 *        前端页面的参数对象
	 */
	public void modifyValuesByFieldName(Collection<SearchFilter> filters, String eqFieldName)
	{
		if (CollectionUtils.isNotEmpty(filters))
		{
			if (StringUtils.isEmpty(eqFieldName))
			{
				eqFieldName = "id";
			}
			
			for (SearchFilter searchFilter : filters)
			{
				if (null != searchFilter)
				{
					String fieldName = searchFilter.getFieldName();
					Object value = searchFilter.getValue();
					if (StringUtils.isNotEmpty(fieldName) && eqFieldName.equalsIgnoreCase(fieldName)
							&& SearchFilter.Operator.IN.equals(searchFilter.getOperator()))
					{
						searchFilter.setValue(getObjectArray(value));
						
					}
				}
				
			}
		}
	}
	
	/**
	 * 返回以逗号分割好的String[]，默认只处理name="id" and Operator.IN
	 * 
	 * @param value
	 *        页面传入参数
	 * @return String[]
	 */
	private Object getObjectArray(Object value)
	{
		if (null != value && value instanceof String)
		{
			String tmpValue = String.valueOf(value);
			
			return tmpValue.split(COMMAN_SYBOL);
		}
		
		return value;
	}
	
}
