/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.nbsalesdetails.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.innshine.common.utils.ExportFieldsConfigUtils;
import com.innshine.nbsalesdetails.dao.NbSalesDetailsDAO;
import com.innshine.nbsalesdetails.entity.NbSalesDetails;
import com.innshine.nbsalesdetails.model.NbSalesDetailsModel;
import com.innshine.ordermanager.entity.OrderFormInfo;
import com.innshine.ordermanager.service.OrderFormInfoService;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.innshine.productinfo.utils.TemplateConstants;
import com.innshine.stockmanager.stockinfo.entity.StockInfo;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.excel.ExcelToBeanUtils;
import com.utils.validator.ValidatorsDataUtils;

@Service
@Transactional
public class NbSalesDetailsService
{
	/**
	 * 导出默认文件免
	 */
	private static final String DEFAULT_EXPORT_FILE_NAME = "销售信息.xlsx";
	
	private static final String COMMA_SYMBOL = ",";
	
	/**
	 * 配置文件中配置的导出字段列表KEY
	 */
	public static final String EXPORT_FIEDLS_CONFIG_KEY = "nb_sales_details";
	
	private static final Logger LOG = LoggerFactory.getLogger(NbSalesDetailsService.class);
	
	/**
	 * 必填字段
	 */
	public static final String[] VALIDATORS_RULE_FIELD_NAME = new String[] { "productUpccode", "materialNumber",
			"platformId", "salesAmount", "salesNumber", "marketEndTime", "marketStartTime" };
	
	@Autowired
	private NbSalesDetailsDAO nbSalesDetailsDAO;
	
	@Autowired
	private SqlDao sqlDao;
	
	@Autowired
	private OrderFormInfoService orderFormInfoService;
	
	public NbSalesDetails get(Long id)
	{
		return nbSalesDetailsDAO.findOne(id);
	}
	
	public void saveOrUpdate(NbSalesDetails nbSalesDetails)
	{
		nbSalesDetailsDAO.save(nbSalesDetails);
	}
	
	public void delete(Long id)
	{
		nbSalesDetailsDAO.delete(id);
	}
	
	public List<NbSalesDetails> findAll(Page page)
	{
		setPageTypeAndField(page);
		org.springframework.data.domain.Page<NbSalesDetails> springDataPage = nbSalesDetailsDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	private void setPageTypeAndField(Page page)
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
	
	public List<NbSalesDetails> findByExample(Specification<NbSalesDetails> specification, Page page)
	{
		setPageTypeAndField(page);
		org.springframework.data.domain.Page<NbSalesDetails> springDataPage = nbSalesDetailsDAO.findAll(specification,
				PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<NbSalesDetails> findByExample(Specification<NbSalesDetails> specification)
	{
		if (null != specification)
		{
			return nbSalesDetailsDAO.findAll(specification);
		}
		return null;
	}
	
	/**
	 * 解析上传的Excel文件
	 * 
	 * @param file
	 *        上传的文件对象
	 * @param brandId
	 *        所属组织编号
	 * @return Map< NbSalesDetails, String >key： NbSalesDetails 需要提示的错误对象
	 *         value：提示信息
	 * @throws Exception
	 */
	public Map<NbSalesDetails, String> parseExcelData(File file, Long brandId) throws Exception
	{
		Map<NbSalesDetails, String> failedMap = new HashMap<NbSalesDetails, String>();
		// 解析Excel文件
		final List<NbSalesDetails> nbSalesDetails = ExcelToBeanUtils.readFilelModel(file, NbSalesDetailsModel.class,
				NbSalesDetails.class, new String[] {}, false);
		
		// 去除重复
		validatorRepeatData(nbSalesDetails, failedMap);
		
		// 效验数据的完整性
		validatorData(nbSalesDetails, brandId, failedMap);
		
		// 数据入库
		saveOrUpdate(nbSalesDetails);
		
		threadModifyCurrentStockInfo(nbSalesDetails);
		
		return failedMap;
	}
	
	/**
	 * 启用后台线程，异步修改对应产品当前库存信息
	 * 
	 * @param nbSalesDetails
	 */
	private void threadModifyCurrentStockInfo(final List<NbSalesDetails> nbSalesDetails)
	{
		if (CollectionUtils.isNotEmpty(nbSalesDetails))
		{
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					
					List<OrderFormInfo> modifyList = new ArrayList<OrderFormInfo>();
					for (NbSalesDetails info : nbSalesDetails)
					{
						try
						{
							if (null != info)
							{
								OrderFormInfo orderFormInfo = cosStockInfo(info);
								if (null != orderFormInfo)
								{
									modifyList.add(orderFormInfo);
								}
							}
						}
						catch (Exception e)
						{
							LOG.error(Exceptions.getStackTraceAsString(e));
						}
					}
					
					if (CollectionUtils.isNotEmpty(modifyList))
					{
						orderFormInfoService.saveOrUpdate(modifyList);
					}
				}
				
			}).start();
		}
	}
	
	/**
	 * 构造订货库存信息
	 * 
	 * @param info
	 * @return
	 */
	public OrderFormInfo cosStockInfo(NbSalesDetails info)
	{
		OrderFormInfo orderFormInfo = orderFormInfoService.findByUnique(consQueryObjOrderInfo(info));
		
		if (null != orderFormInfo)
		{
			Double salesNumber = null == info.getSalesNumber() ? 0.0 : info.getSalesNumber();
			List<StockInfo> stockInfos = orderFormInfo.getStockInfos();
			
			if (null != stockInfos)
			{
				for (StockInfo stockInfo : stockInfos)
				{
					if (null != stockInfo)
					{
						Integer currStockNumber = null == stockInfo.getCurrentStockNumber() ? 0 : stockInfo
								.getCurrentStockNumber();
						orderFormInfoService.cosnStockLogInfos(stockInfo, false);
						stockInfo.setCurrentStockNumber(currStockNumber - salesNumber.intValue());
					}
				}
			}
			
		}
		
		return orderFormInfo;
	}
	
	public void saveOrUpdate(List<NbSalesDetails> nbSalesDetails)
	{
		if (CollectionUtils.isNotEmpty(nbSalesDetails))
		{
			nbSalesDetailsDAO.save(nbSalesDetails);
		}
		
	}
	
	/**
	 * 去除重复记录，以UPC条码做一，如果找到两条或多条一样的，则以最后一条为最新记录
	 * 
	 * @param failedMap
	 *        记录失败列表
	 * 
	 * @param details
	 *        原始数据列表
	 */
	private void validatorRepeatData(List<NbSalesDetails> details, Map<NbSalesDetails, String> failedMap)
	{
		if (CollectionUtils.isNotEmpty(details))
		{
			
			for (int i = details.size() - 1; i >= 0; i--)
			{
				
				NbSalesDetails nbSalesDetails = details.get(i);
				
				if (null != nbSalesDetails)
				{
					// 计数器，记录重复次数
					int count = 0;
					
					for (int j = details.size() - 1; j >= 0; j--)
					{
						
						NbSalesDetails tmpNBSalesDetails = details.get(j);
						if (null != tmpNBSalesDetails)
						{
							// 内部使用重复记数器
							int repeatCount = 0;
							String tmpStartTime = tmpNBSalesDetails.getMarketStartTime();
							String tmpEndTime = tmpNBSalesDetails.getMarketEndTime();
							String tmpPlatformProductId = tmpNBSalesDetails.getPlatformId();
							String productUpccode = tmpNBSalesDetails.getUpccode();
							String materialNumber=tmpNBSalesDetails.getMaterialNumber();
							
							// UPC+ 平台编号 + 销售开始时间 + 结束时间 做唯一键
							if (StringUtils.isNotBlank(tmpStartTime)
									&& tmpStartTime.equalsIgnoreCase(nbSalesDetails.getMarketStartTime()))
							{
								repeatCount++;
							}
							
							if (StringUtils.isNotBlank(tmpEndTime)
									&& tmpEndTime.equalsIgnoreCase(nbSalesDetails.getMarketEndTime()))
							{
								repeatCount++;
							}
							
							if (StringUtils.isNotBlank(productUpccode)
									&& productUpccode.equalsIgnoreCase(nbSalesDetails.getUpccode()))
							{
								repeatCount++;
							}
							
							if (StringUtils.isNotBlank(tmpPlatformProductId)
									&& tmpPlatformProductId.equalsIgnoreCase(nbSalesDetails.getPlatformId()))
							{
								repeatCount++;
							}
							
							if (StringUtils.isNotBlank(materialNumber)
									&& materialNumber.equalsIgnoreCase(nbSalesDetails.getMaterialNumber()))
							{
								repeatCount++;
							}
							
							// 如果其中三个字段都相同，则认为当前记录为相同，累计计数
							if (repeatCount >= 5)
							{
								count++;
							}
						}
						
					}
					
					if (count >= 2)
					{
						
						LOG.warn(" Remove duplicate records ! Object = " + nbSalesDetails);
						
						failedMap.put(nbSalesDetails, "导入原始数据中该记录存在重复！");
						details.remove(nbSalesDetails);
						
					}
				}
				
			}
		}
	}
	
	/**
	 * 效验数据的完整性
	 * <p>
	 * 
	 * @param olyProductInfos
	 *        导入数据列表
	 * @param brandId
	 *        所属部门编号
	 * @return 返回不满足规则的数据列表，前端页面展示
	 */
	private void validatorData(List<NbSalesDetails> details, Long brandId, Map<NbSalesDetails, String> failedMap)
	{
		if (CollectionUtils.isNotEmpty(details))
		{
			if (null == failedMap)
			{
				failedMap = new HashMap<NbSalesDetails, String>();
			}
			
			String currTime = DateUtils.getNow(DateUtils.DEFAULT_FORMAT);
			List<NbSalesDetails> deleteList = new ArrayList<NbSalesDetails>();
			for (int i = details.size() - 1; i >= 0; i--)
			{
				NbSalesDetails salesDetails = details.get(i);
				
				// 继续效验的标识
				boolean flag = false;
				if (null != salesDetails)
				{
					// 效验是否有空行的情况，解析Excel文件时，会存在空对象，所有内容为空
					if (!ValidatorsDataUtils.isObjectNull(salesDetails) && !flag)
					{
						flag = true;
					}
					
					// 效验必填字段时否为空
					if (!ValidatorsDataUtils.isFieldsRule(VALIDATORS_RULE_FIELD_NAME, salesDetails) && !flag)
					{
						flag = true;
						failedMap.put(salesDetails, "必填字段未填写");
						
					}
					
					salesDetails.setUpdateTime(currTime);
					salesDetails.setBrandId(brandId);
					
					// 效验数据的唯一性
					if (!ValidatorsDataUtils.isUnique(salesDetails, this) && !flag)
					{
						// 默认支持批量修改
						if (com.innshine.common.Constants.DEFAULT_BATCH_MODIFTY)
						{
							NbSalesDetails olySalesDetails = findByUnique(salesDetails);
							if (null != olySalesDetails)
							{
								deleteList.add(olySalesDetails);
							}
						}
						else
						{
							failedMap.put(salesDetails, "数据库中已存在该数据");
							flag = true;
						}
					}
					
					// 效验当前库存信息是否存在
					if (!flag)
					{
						OrderFormInfo formInfo = consQueryObjOrderInfo(salesDetails);
						if (ValidatorsDataUtils.isUnique(formInfo, orderFormInfoService))
						{
							failedMap.put(salesDetails, "订货管理中，无法找到该产品对应当前库存信息");
							flag = true;
						}
					}
					
					if (flag)
					{
						details.remove(salesDetails);
					}
				}
				
			}
			
			deleteByUnique(deleteList);
			
		}
		
	}
	
	/**
	 * 构造订货信息查询对象，根据销售数据对象
	 * 
	 * @param salesDetails
	 *        销售数据对象
	 * @return OrderFormInfo 返回构造成功的订货信息对象
	 */
	public OrderFormInfo consQueryObjOrderInfo(NbSalesDetails salesDetails)
	{
		if (null != salesDetails)
		{
			OrderFormInfo formInfo = new OrderFormInfo();
			formInfo.setUpccode(salesDetails.getUpccode());
			formInfo.setBrandId(salesDetails.getBrandId());
			formInfo.setMaterialNumber(salesDetails.getMaterialNumber());
			return formInfo;
		}
		
		return null;
	}
	
	private void deleteByUnique(List<NbSalesDetails> deleteList)
	{
		if (CollectionUtils.isNotEmpty(deleteList))
		{
			nbSalesDetailsDAO.deleteInBatch(deleteList);
		}
		
	}
	
	/**
	 * 查找唯一性数据UPC+款号+销售时间+销售结束时间+所属组织编号
	 * 
	 * @param salesDetails
	 *        需要验证的待入库对象
	 * @return NbSalesDetails
	 */
	public NbSalesDetails findByUnique(NbSalesDetails salesDetails)
	{
		if (null != salesDetails)
		{
			List<NbSalesDetails> details = nbSalesDetailsDAO
					.findByUpccodeAndPlatformIdAndMarketStartTimeAndMarketEndTimeAndBrandIdAndMaterialNumber(salesDetails
							.getUpccode(), salesDetails.getPlatformId(), salesDetails.getMarketStartTime(),
							salesDetails.getMarketEndTime(), salesDetails.getBrandId(),salesDetails.getMaterialNumber());
			
			if (CollectionUtils.isNotEmpty(details))
			{
				return details.get(0);
			}
			
		}
		
		return null;
	}
	
	public String exportExcel(List<NbSalesDetails> nbSalesDetails)
	{
		String tmpPath = null;
		if (null != nbSalesDetails)
		{
			
			tmpPath = ExcelFileUtils.createExportExcel(nbSalesDetails, NbSalesDetailsModel.class, NbSalesDetails.class,
					TemplateConstants.EXPORT_NB_SALESDETAILS_MODEL_PATH);
		}
		
		return tmpPath;
	}
	
	/**
	 * 根据数据更新时间+分页对象SQL，查询对应数据，并返回对应Class对象数据列表
	 * 
	 * @param <T>
	 *        泛型对象
	 * @param sql
	 *        执行的查询SQL
	 * @param page
	 *        分页对象
	 * @param clazz
	 *        需要返回的实体bean class
	 * 
	 * @return List< clazz > 返回泛型T对象结果列表
	 */
	public <T> List<T> findByUpdateTime(String sql, Page page, Class<T> clazz)
	{
		try
		{
			return sqlDao.queryByPage(sql, page, clazz);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 根据前面选择字段，导出对应数据。<br/>
	 * 如果选择的字段为空，则默认根据模版导出。故则相反
	 * 
	 * @param nbSalesDetails
	 *        原始数据集
	 * @param exportSelectFieldsName
	 *        选择需要导出的字段列表
	 * @param request
	 *        请求对象
	 * @param response
	 *        响应对象
	 * @throws IOException
	 */
	public void exportExcel(List<NbSalesDetails> nbSalesDetails, String exportSelectFieldsName,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// 如果选择的字段为空，则默认根据模版导出
		if (StringUtils.isNotEmpty(exportSelectFieldsName))
		{
			Vector<String> displayFields = getExportFieldNameVector(exportSelectFieldsName);
			Vector<String> headerVector = getExportTitleHeader(exportSelectFieldsName, EXPORT_FIEDLS_CONFIG_KEY);
			
			Workbook workbook = ExcelFileUtils.exportByTemplateToExcel(nbSalesDetails, displayFields, headerVector);
			
			if (null != workbook)
			{
				ExcelFileUtils.excelExport(workbook, request, response, DEFAULT_EXPORT_FILE_NAME);
			}
		}
		else
		{
			ExcelFileUtils.excelExport(request, response, exportExcel(nbSalesDetails));
		}
	}
	
	/**
	 * 根据页面选择的字段列表，从配置文件中反射获取对应标题名称，并存入Vector中。
	 * 
	 * @param exportSelectFieldsName
	 *        页面选择字段列表
	 * @param exportFiedlsConfigKey
	 *        配置文件<export-fields-config.xml>中ID
	 * @return Vector< String>
	 */
	public Vector<String> getExportTitleHeader(String exportSelectFieldsName, String exportFiedlsConfigKey)
	{
		return getExportFieldNameOrTitleToVector(exportSelectFieldsName, exportFiedlsConfigKey, true);
	}
	
	/**
	 * 根据选择的字段，获取转换为对应Field Name，并存储至Vector中
	 * 
	 * @param exportSelectFieldsName
	 *        选择的字段列表
	 * @return Vector< String>
	 */
	public Vector<String> getExportFieldNameVector(String exportSelectFieldsName)
	{
		return getExportFieldNameOrTitleToVector(exportSelectFieldsName, null, false);
	}
	
	/**
	 * 根据选择的字段，获取转换为对应Field Name，并存储至Vector中
	 * 
	 * @param exportSelectFieldsName
	 *        选择的字段列表
	 * @param fieldConfigKey
	 *        配置中<export-fields-config.xml>中ID
	 * 
	 * @param isTitle
	 *        true:获取title ,false:获取name
	 * @return Vector< String>
	 */
	private Vector<String> getExportFieldNameOrTitleToVector(String exportSelectFieldsName, String fieldConfigKey,
			boolean isTitle)
	{
		if (StringUtils.isNotEmpty(exportSelectFieldsName))
		{
			String[] splitFieldNames = exportSelectFieldsName.split(COMMA_SYMBOL);
			
			if (ArrayUtils.isNotEmpty(splitFieldNames))
			{
				Map<String, String> fieldMap = null;
				boolean isTempTitle = false;
				if (isTitle)
				{
					fieldMap = ExportFieldsConfigUtils.getInstance().getFieldsConfig(fieldConfigKey);
					
					if (MapUtils.isNotEmpty(fieldMap))
					{
						isTempTitle = true;
					}
				}
				
				Vector<String> vector = new Vector<String>();
				for (String fieldName : splitFieldNames)
				{
					if (StringUtils.isNotEmpty(fieldName))
					{
						if (isTempTitle && isTitle)
						{
							String title = fieldMap.get(fieldName);
							
							if (StringUtils.isNotEmpty(title))
							{
								vector.add(title);
							}
						}
						else
						{
							vector.add(fieldName);
						}
					}
				}
				
				return vector;
			}
		}
		
		return null;
	}
}
