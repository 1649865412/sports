package com.innshine.daily.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.base.dao.sql.ReflectionUtils;
import com.base.entity.main.Brand;
import com.innshine.daily.Constants;
import com.innshine.productinfo.utils.ExcelFileUtils;
import com.innshine.shopAnalyse.util.Excel2Html;
import com.utils.DateUtils;
import com.utils.Exceptions;

/**
 * 汇总日报导出excel工作类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class GatherDailyExcelExportUtils
{
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GatherDailyExcelExportUtils.class);
	
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
	 * @return String[] [0]: 文件名 [1]:文件完整路径
	 */
	public static String[] saveFile(Workbook workbook, Brand brand)
	{
		return saveFile(workbook, null, brand);
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
	public static String[] saveFile(Workbook workbook, String fileName, Brand brand)
	{
		if (null == workbook)
		{
			return null;
		}
		
		String currentDate = getDate(-1, DateUtils.SIMPLE_DEFAULT_FORMAT);
		
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
			String path = Constants.DEFAULT_SAVE_PATH + currentDate;
			
			// 文件明以 年-月-日_品牌ID_品牌名称 命名
			tmpFileName = tmpFileName + Constants.UNDER_LINE + brand.getId()
					+ (StringUtils.isNotEmpty(brand.getBrandName()) ? Constants.UNDER_LINE + brand.getBrandName() : "")
					+ getSuffix(tmpFileName, workbook);
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
	 * 把时间往前推或往后推，根据opt参数，可以-、+、*
	 * 
	 * @return
	 */
	public static String getDate(int opt, String fomate)
	{
		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.DATE, opt);
		return DateUtils.format(calendar.getTime(), fomate);
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
	private static String getSuffix(String tmpFileName, Workbook workbook)
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
	 * 创建工作薄对象
	 * 
	 * @param type
	 *        根据类型(Constants.XSSF_WORKBOOK_TYPE)创建xssf、hssf workbokk对象
	 * @return Workbook
	 */
	public static Workbook createWorkbook(int type)
	{
		if (Constants.XSSF_WORKBOOK_TYPE == type)
		{
			return new XSSFWorkbook();
		}
		else
		{
			return new HSSFWorkbook();
		}
	}
	
	/**
	 * 创建工作表
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheetName
	 *        工作表名称
	 * @return org.apache.poi.ss.usermodel.Sheet
	 */
	public static Sheet createSheet(Workbook workbook, String sheetName)
	{
		if (null != workbook)
		{
			return workbook.createSheet(StringUtils.isNotEmpty(sheetName) ? sheetName : Constants.DEFAULT_SHEET_NAME);
		}
		
		return null;
	}
	
	/**
	 * 创建工作表，默认创建方式，工作表名称为 “汇总日报详情”
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @return org.apache.poi.ss.usermodel.Sheet
	 */
	public static Sheet createSheet(Workbook workbook)
	{
		if (null != workbook)
		{
			return createSheet(workbook, null);
		}
		
		return null;
	}
	
	/**
	 * 写入数据至excel文件中
	 * 
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @param startRow
	 *        开始行
	 * @param startCell
	 *        开始列
	 * @param dataList
	 *        数据结果
	 * @param title
	 *        标题数组
	 * @param fieldNames
	 *        需要导入的字段列表
	 */
	public static <T> void writerExcel(Workbook workbook, Sheet sheet, int startRow, int startCell, List<T> dataList,
			String[] title, String[] fieldNames)

	{
		if (null != workbook && null != sheet && null != dataList && null != fieldNames)
		{
			// 写入表格标题
			writerHeaderTitle(workbook, sheet, startRow, startCell, title);
			
			// 写入内容
			writerSheetContent(workbook, sheet, startRow, startCell, dataList, fieldNames);
		}
	}
	
	/**
	 * 写入表格标题
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @param startRow
	 *        开始行
	 * @param startCell
	 *        开始列
	 * @param dataList
	 *        数据结果集
	 * @param fieldNames
	 *        需要导入的字段列表
	 */
	private static <T> void writerSheetContent(Workbook workbook, Sheet sheet, int startRow, int startCell,
			List<T> dataList, String[] fieldNames)
	{
		if (null != workbook && null != sheet && null != dataList && null != fieldNames)
		{
			if (CollectionUtils.isNotEmpty(dataList))
			{
				int rowIndex = 0;
				CellStyle cellStyle = getCellStyle(workbook);
				for (T t : dataList)
				{
					Row row = sheet.getRow(startRow + rowIndex);
					row = null == row ? sheet.createRow(startRow + rowIndex) : row;
					
					int cellIndex = 0;
					for (String fieldName : fieldNames)
					{
						Cell cell = row.createCell(cellIndex + startCell);
						String tmpValue = Constants.CROSS_BAR;
						if (null != t)
						{
							try
							{
								java.lang.reflect.Field obj = ReflectionUtils.getAccessibleField(t, fieldName);
								if (null != obj)
								{
									Object tmp = obj.get(t);
									if (obj.getType() == java.util.Date.class)
									{
										tmpValue = dateValue(tmp);
									}
									else
									{
										
										tmpValue = null != tmp ? tmp.toString() : tmpValue;
									}
								}
							}
							catch (Exception e)
							{
								LOGGER.error(Exceptions.getStackTraceAsString(e));
							}
						}
						
						cell.setCellValue(tmpValue);
						cell.setCellStyle(cellStyle);
						cellIndex++;
					}
					
					rowIndex++;
				}
			}
		}
	}
	
	// 格式化日期处理
	private static String dateValue(Object value)
	{
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = String.valueOf(value);
		Date date = (Date) value;
		try
		{
			if (datestr == null || datestr.equals("null") || datestr.trim().equals(""))
			{
				return "";
			}
			else if (datestr.length() <= 10)
			{
				return DATE_FORMAT_SHORT.format(date);
			}
			else
			{
				return DATE_FORMAT.format(date);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 写入表格标题
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheet
	 *        工作表对象
	 * @param startRow
	 *        开始行
	 * @param startCell
	 *        开始列
	 * @param title
	 *        标题数组
	 */
	private static void writerHeaderTitle(Workbook workbook, Sheet sheet, int startRow, int startCell, String[] title)
	{
		int tmpStartRow = startRow > 0 ? startRow - 1 : startRow;
		if (null != sheet)
		{
			if (ArrayUtils.isNotEmpty(title))
			{
				CellStyle cellStyle = getHeaderTitleCellStyle(workbook);
				Row row = sheet.getRow(tmpStartRow);
				row = null == row ? sheet.createRow(tmpStartRow) : row;
				for (int i = 0; i < title.length; i++)
				{
					Cell cell = row.createCell(startCell + i);
					if (null != cell)
					{
						
						// if ("&".equalsIgnoreCase(title[i]))
						// {
						// setClientAnchor(sheet, cell, row);
						// }
						// else
						// {
						cell.setCellValue(title[i]);
						cell.setCellStyle(cellStyle);
						// }
					}
				}
			}
		}
	}
	
	/**
	 * 设置公共样式
	 * <p>
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @return CellStyle 返回列样式
	 */
	public static CellStyle getCellStyle(Workbook workbook)
	{
		CellStyle xssfCellStyle = workbook.createCellStyle();
		xssfCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		return xssfCellStyle;
	}
	
	/**
	 * 设置公共样式，标题头字体加粗
	 * <p>
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @return CellStyle 返回列样式
	 */
	public static CellStyle getHeaderTitleCellStyle(Workbook workbook)
	{
		CellStyle cellStyle = getCellStyle(workbook);
		Font font = workbook.createFont();
		// font.setFontHeightInPoints((short) 24); // 字体大小
		// font.setFontName("楷体");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		cellStyle.setFont(font);
		return cellStyle;
		
	}
	
	public static void setClientAnchor(Sheet sheet, Cell cell, Row row)
	{
		Drawing patriarch = sheet.createDrawingPatriarch();
		ClientAnchor anchor = getClientAnchor(sheet);
		
		// ((HSSFClientAnchor) anchor).setAnchor((short) (cell.getColumnIndex()
		// + 1), row.getRowNum(), 0, 0, (short) (cell
		// .getColumnIndex() + 2), row.getRowNum() + 1, 0, 0);
		//		
		// int x1, int y1, short col2, int row2, int x2, int y2
		anchor.setCol1((cell.getColumnIndex() + 1));
		anchor.setRow1(row.getRowNum());
		anchor.setDx1(0);
		anchor.setDy1(0);
		anchor.setCol2(cell.getColumnIndex() + 2);
		anchor.setRow2(row.getRowNum() + 1);
		anchor.setDx2(0);
		anchor.setDy2(0);
		
		patriarch.createAnchor((short) (cell.getColumnIndex() + 1), row.getRowNum(), 0, 0, (short) (cell
				.getColumnIndex() + 2), row.getRowNum() + 1, 0, 0);
	}
	
	/**
	 * 功能:
	 * <p>
	 * 作者 admin 2014-10-27 下午01:38:59
	 * 
	 * @param sheet
	 * @return
	 */
	public static ClientAnchor getClientAnchor(Sheet sheet)
	{
		if (sheet instanceof HSSFSheet)
		{
			return new HSSFClientAnchor();
		}
		else if (sheet instanceof XSSFSheet)
		{
			return new XSSFClientAnchor();
		}
		
		return null;
	}
	
	/**
	 * 把Excel转换为HTML
	 * 
	 * @param excelPath
	 *        excel文件完整路径
	 * @return 转换后的HTML
	 */
	public static String excelToHtml(String excelPath)
	{
		File file = new File(excelPath);
		if (file.exists())
		{
			try
			{
				return Excel2Html.ExcelToHtml(excelPath);
			}
			catch (TransformerException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
			catch (IOException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
			catch (ParserConfigurationException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
			catch (SAXException e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return "";
	}
}
