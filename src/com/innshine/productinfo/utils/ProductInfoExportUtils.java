package com.innshine.productinfo.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.dao.sql.ReflectionUtils;
import com.utils.Exceptions;
import com.utils.excel.FieldUtils;
import com.utils.excel.FileExcelUtils;

/**
 * 基础信息数据导出工具类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class ProductInfoExportUtils
{
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ProductInfoExportUtils.class);
	
	/**
	 * 根据模版导出Excel列表，默认忽略表头。只生成1张表工作表
	 * <p>
	 * 
	 * @param templateFile
	 *        模版文件
	 * @param dataList
	 *        数据列表
	 * @param sheetName
	 *        工作薄名称
	 * @param isImage
	 *        是否需要导入图片 true：有图片导入
	 * @param fieldNames
	 *        哪些字段为存储图片路径
	 * @return 生成后的Excel文件，完整路径
	 */
	public static <M> String exportByTemplateToExcel(File templateFile, List<M> dataList, Field[] fields,
			boolean isImage, String[] fieldNames)
	{
		if (null == templateFile || null == dataList || ArrayUtils.isEmpty(fields))
		{
			return null;
			
		}
		
		File targerFile = new File(ExcelFileUtils.FILE_TEMP_DIR + ExcelFileUtils.EXPORT + (System.currentTimeMillis())
				+ ExcelFileUtils.UNDER_LINE + templateFile.getName());
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try
		{
			
			// 目录是否存在
			if (!ExcelFileUtils.createDir(targerFile.getParent()))
			{
				return null;
			}
			
			// 创建模版文件
			if (!ExcelFileUtils.createModelFile(templateFile, targerFile))
			{
				return null;
			}
			
			inputStream = new FileInputStream(targerFile);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
			
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			// xssfSheet.setDefaultRowHeight((short) 600);
			M obj = null;
			
			for (int i = 0; i < dataList.size(); i++)
			{
				obj = dataList.get(i);
				
				if (null != obj)
				{
					
					int startRowIndex = i + 1;
					// 创建行对象
					XSSFRow xssfRow = xssfSheet.createRow((startRowIndex));
					xssfRow.setHeight((short) 600);
					
					for (int j = 0; j < fields.length; j++)
					{
						Field field = fields[j];
						
						XSSFCell xssfCell = xssfRow.createCell(j);
						if (null != field)
						{
							field.setAccessible(true);
							Object objValue = FieldUtils.findFieldValue(field.get(obj), field.getType());
							String tmpValue = String.valueOf(objValue);
							// 过滤0 与0.0的数值
							if (null != objValue
									&& (ExcelFileUtils.INTEGER_ZERO.equalsIgnoreCase(tmpValue) || ExcelFileUtils.DOUBLE_ZERO
											.equalsIgnoreCase(tmpValue)))
							{
								tmpValue = "";
							}
							
							// 是否需要写入图片
							if (isImage && isWriteImageField(field, fieldNames))
							{
								writeImage(objValue, xssfSheet, xssfWorkbook, startRowIndex, j, xssfCell);
							}
							else
							{
								
								xssfCell.setCellValue(tmpValue);
							}
						}
						else
						{
							xssfCell.setCellValue("");
						}
						
						xssfCell.setCellStyle(ExcelFileUtils.getXssfCellStyle(xssfWorkbook));
						
					}
					
				}
			}
			
			outputStream = new FileOutputStream(targerFile);
			
			// 写入数据
			xssfWorkbook.write(outputStream);
			
		}
		catch (IOException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (IllegalArgumentException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (IllegalAccessException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		finally
		{
			ExcelFileUtils.closeable(new Closeable[] { inputStream, outputStream });
		}
		
		return targerFile.getPath();
	}
	
	/**
	 * 根据模版导出Excel列表，默认忽略表头。只生成1张表工作表
	 * <p>
	 * 
	 * @param dataList
	 *        数据列表
	 * @param isImage
	 *        是否需要导入图片 true：有图片导入
	 * @param fieldNames
	 *        哪些字段为存储图片路径
	 * @param display
	 *        显示的字段列表
	 * @return 生成后的Excel文件，完整路径
	 */
	public static <T> Workbook exportByTemplateToExcel(List<T> dataList, Vector<String> display, Vector<String> header,
			boolean isImage, String[] fieldNames)
	{
		if (null == dataList || CollectionUtils.isEmpty(display))
		{
			return null;
			
		}
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try
		{
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			
			XSSFSheet xssfSheet = xssfWorkbook.createSheet("sheet1");
			
			XSSFCellStyle xssfCellStyle = ExcelFileUtils.getXssfCellStyle(xssfWorkbook);
			
			// 写入标题行
			writeSheetTitle(xssfSheet, header, 0, xssfCellStyle);
			
			T obj = null;
			
			for (int i = 0; i < dataList.size(); i++)
			{
				obj = dataList.get(i);
				
				if (null != obj)
				{
					
					int startRowIndex = i + 1;
					// 创建行对象
					XSSFRow xssfRow = xssfSheet.createRow((startRowIndex));
					xssfRow.setHeight((short) 600);
					
					for (int j = 0; j < display.size(); j++)
					{
						String displayFieldName = display.get(j);
						Object objValue = ReflectionUtils.getFieldValue(obj, displayFieldName);
						
						XSSFCell xssfCell = xssfRow.createCell(j);
						if (null != objValue)
						{
							String tmpValue = String.valueOf(objValue);
							
							// 过滤0 与0.0的数值
							if (null != objValue
									&& (ExcelFileUtils.INTEGER_ZERO.equalsIgnoreCase(tmpValue) || ExcelFileUtils.DOUBLE_ZERO
											.equalsIgnoreCase(tmpValue)))
							{
								tmpValue = "";
							}
							
							// 是否需要写入图片
							if (isImage && isWriteImageField(displayFieldName, fieldNames))
							{
								writeImage(objValue, xssfSheet, xssfWorkbook, startRowIndex, j, xssfCell);
							}
							else
							{
								
								xssfCell.setCellValue(tmpValue);
							}
						}
						else
						{
							xssfCell.setCellValue("");
						}
						
						xssfCell.setCellStyle(ExcelFileUtils.getXssfCellStyle(xssfWorkbook));
						
					}
					
				}
			}
			// 写入数据
			return xssfWorkbook;
			
		}
		catch (IllegalArgumentException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		finally
		{
			ExcelFileUtils.closeable(new Closeable[] { inputStream, outputStream });
		}
		
		return null;
	}
	
	/**
	 * 写入标题行
	 * 
	 * @param xssfSheet
	 *        工作表对象
	 * @param header
	 *        表头
	 * @param startRow
	 *        开始行
	 * @param xssfCellStyle
	 */
	private static void writeSheetTitle(XSSFSheet xssfSheet, Vector<String> header, int startRow,
			XSSFCellStyle xssfCellStyle)
	{
		if (CollectionUtils.isNotEmpty(header))
		{
			XSSFRow xssfRow = xssfSheet.createRow(startRow);
			for (int i = 0; i < header.size(); i++)
			{
				XSSFCell xssfCell = xssfRow.createCell(i);
				xssfCell.setCellValue(header.get(i));
				xssfCell.setCellStyle(xssfCellStyle);
			}
		}
	}
	
	/**
	 * 写入图片至对应行列中
	 * 
	 * @param objValue
	 *        数据库中存储的图片相对路径
	 * @param xssfSheet
	 *        工作表对象
	 * @param xssfWorkbook
	 *        工作薄对象
	 * @param rowIndex
	 *        行下标
	 * @param columnIndex
	 *        列下标
	 * @param xssfCell
	 *        列对象
	 */
	public static void writeImage(Object objValue, XSSFSheet xssfSheet, XSSFWorkbook xssfWorkbook, int rowIndex,
			int columnIndex, XSSFCell xssfCell)
	{
		if (null != objValue)
		{
			// 获取文件存储的相对路径
			String path = String.valueOf(objValue);
			
			if (StringUtils.isNotEmpty(path))
			{
				// 获取图片存储的绝对路径
				// String imagePath =
				// "D:\\develop\\apache-tomcat-6.0.41\\webapps\\plan.jpg";
				String imagePath = ExcelFileUtils.getExcelModelPath("") + path;
				
				// 图片文件对象
				File pictureFile = new File(imagePath);
				
				if (null != xssfCell)
				{
					xssfCell.setCellValue(" ");
					
				}
				
				if (pictureFile.exists())
				{
					// 获取文件后辍
					String fileExt = com.utils.FileUtils.getFileExt(pictureFile.getName());
					
					try
					{
						FileExcelUtils.addPictureToExcel2(xssfSheet, xssfWorkbook, pictureFile, rowIndex, rowIndex + 1,
								columnIndex, columnIndex + 1, fileExt);
						
					}
					catch (Exception e)
					{
						LOG.error(Exceptions.getStackTraceAsString(e));
					}
				}
				
			}
		}
	}
	
	/**
	 * 判断当前字段是否需要写入图片
	 * 
	 * @param displayFieldName
	 *        字段对象
	 * @param fieldNames
	 *        对象中包含图片的字段列表，默认为存储图片相对路径
	 * @return true：是需要写入图片的字段 ,false:不需要
	 */
	private static boolean isWriteImageField(String displayFieldName, String[] fieldNames)
	{
		if (null != displayFieldName && ArrayUtils.isNotEmpty(fieldNames))
		{
			if (ArrayUtils.contains(fieldNames, displayFieldName))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断当前字段是否需要写入图片
	 * 
	 * @param displayFieldName
	 *        字段对象
	 * @param fieldNames
	 *        对象中包含图片的字段列表，默认为存储图片相对路径
	 * @return true：是需要写入图片的字段 ,false:不需要
	 */
	private static boolean isWriteImageField(Field displayFieldName, String[] fieldNames)
	{
		if (null != displayFieldName && ArrayUtils.isNotEmpty(fieldNames))
		{
			if (ArrayUtils.contains(fieldNames, displayFieldName.getName()))
			{
				return true;
			}
		}
		
		return false;
	}
}
