package api.taobao.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innshine.daily.Constants;
import com.innshine.daily.utils.GatherDailyExcelExportUtils;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;

/**
 * 交易信息excel导出工具类 <code>ExcelUtils.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class ExcelUtils
{
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);
	/**
	 * 默认sheet名称
	 */
	private static final String DEFAULT_SHEET_NAME = "sheet1";
	
	/**
	 * 默认开始行
	 */
	private static final int DEFAULT_START_CELL = 0;
	
	/**
	 * 开始列
	 */
	private static final int DEFAULT_START_ROW = 1;
	
	/**
	 * 默认存放 路径
	 */
	private static final String DEFAULT_SAVE_PATH = System.getProperty("user.dir") + File.separator + "trade_diff"
			+ File.separator;
	
	/**
	 * 生成07版excel文件
	 * 
	 * @param <T>
	 * @param dataList
	 *        导出excel数据对象
	 * @param title
	 *        行标题
	 * @param fieldNames
	 *        属性列表
	 * @param sheetName
	 *        生成的sheet名称
	 * @return InputStream
	 */
	public static <T> Workbook writerExcel(List<T> dataList, String[] title, String[] fieldNames, String sheetName)
	{
		if (null != dataList && ArrayUtils.isNotEmpty(title) && ArrayUtils.isNotEmpty(fieldNames))
		{
			sheetName = StringUtils.isEmpty(sheetName) ? DEFAULT_SHEET_NAME : sheetName;
			
			Workbook workbook = GatherDailyExcelExportUtils.createWorkbook(Constants.XSSF_WORKBOOK_TYPE);
			Sheet sheet = GatherDailyExcelExportUtils.createSheet(workbook, sheetName);
			
			GatherDailyExcelExportUtils.writerExcel(workbook, sheet, DEFAULT_START_ROW, DEFAULT_START_CELL, dataList,
					title, fieldNames);
			
			return workbook;
		}
		
		return null;
		
	}
	
	/**
	 * 保存到文件，并返回文件名与文件路径，数组保持两个长度
	 * <p>
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param fileName
	 *        文件名
	 * @param brand
	 *        品牌对象
	 * @return String[] [0] : 文件名 [1]:文件完整路径
	 */
	public static String[] saveFile(Workbook workbook, String fileName)
	{
		if (null == workbook)
		{
			return null;
		}
		
		String currentDate = DateUtils.getNow("yyyyMMddHHmmss");
		
		String tmpFileName = "";
		
		if (StringUtils.isNotEmpty(fileName))
		{
			tmpFileName = fileName;
		}
		else
		{
			tmpFileName = currentDate;
		}
		
		FileOutputStream fileOutputStream = null;
		
		try
		{
			String path = DEFAULT_SAVE_PATH;
			
			tmpFileName = tmpFileName + getSuffix(tmpFileName, workbook);
			File file = new File(path + File.separator + tmpFileName);
			
			if (ExcelFileUtils.createDir(path))
			{
				
				fileOutputStream = new FileOutputStream(file);
				workbook.write(fileOutputStream);
				
				return new String[] { tmpFileName, path };
			}
			
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		finally
		{
			ExcelFileUtils.closeable(fileOutputStream);
		}
		
		return null;
	}
	
	/**
	 * 获取文件后辍，如果文件为带“.”符号，则认为默认带后辍，不添加，故则相反。
	 * 
	 * @param tmpFileName
	 *        文件名
	 * @param workbook
	 *        excel对象
	 * @return 文件名后辍
	 */
	public static String getSuffix(String tmpFileName, Workbook workbook)
	{
		if (StringUtils.isNotEmpty(tmpFileName))
		{
			if (tmpFileName.indexOf(Constants.POINT_SYMBOL) == -1)
			{
				if (workbook instanceof HSSFWorkbook)
				{
					return Constants.SUFFIX_EXCEL_2003;
				}
				else if (workbook instanceof XSSFWorkbook)
				{
					return Constants.SUFFIX_EXCEL_2007;
				}
				else
				{
					return Constants.SUFFIX_EXCEL_2007;
				}
				
			}
		}
		
		return "";
	}
	
	/**
	 * 保存Excel到InputStream
	 * 
	 * @param workbook
	 * @return
	 */
	private static OutputStream save(Workbook workbook)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try
		{
			workbook.write(bos);
			// InputStream bis = new ByteArrayInputStream(bos.toByteArray());
			return bos;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
