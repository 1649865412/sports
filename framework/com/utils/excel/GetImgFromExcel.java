package com.utils.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innshine.productinfo.utils.ExcelFileUtils;
import com.utils.DateUtils;

/**
 * * 获取excel中 图片，并得到图片位置，支持03 07 多sheet <code>GetImgFromExcel.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class GetImgFromExcel
{
	private static final String POINT = ".";
	
	/**
	 * 下划线
	 */
	public static final String UNDER_LINE = "_";
	
	/**
	 * 文件上传临时目录
	 */
	// public static final String FILE_TEMP_DIR = System.getProperty("user.dir")
	// + "/excel_import_image/";
	
	public static final String FILE_TEMP_DIR = "/excel_import_images/";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(GetImgFromExcel.class);
	
	/**
	 * 获取Excel2003图片
	 * 
	 * @param sheetNum
	 *        当前sheet编号
	 * @param sheet
	 *        当前sheet对象
	 * @param workbook
	 *        工作簿对象
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 * @throws IOException
	 */
	public static Map<String, PictureData> getSheetPictrues03(int sheetNum, HSSFSheet sheet, HSSFWorkbook workbook)
	{
		
		Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
		List<HSSFPictureData> pictures = workbook.getAllPictures();
		if (pictures.size() != 0)
		{
			for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren())
			{
				HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
				if (shape instanceof HSSFPicture)
				{
					if (null != shape)
					{
						HSSFPicture pic = (HSSFPicture) shape;
						int pictureIndex = pic.getPictureIndex() - 1;
						HSSFPictureData picData = pictures.get(pictureIndex);
						String picIndex = String.valueOf(sheetNum) + UNDER_LINE + String.valueOf(anchor.getRow1())
								+ UNDER_LINE + String.valueOf(anchor.getCol1());
						sheetIndexPicMap.put(picIndex, picData);
					}
				}
			}
			return sheetIndexPicMap;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 获取Excel2007图片
	 * 
	 * @param sheetNum
	 *        当前sheet编号
	 * @param sheet
	 *        当前sheet对象
	 * @param workbook
	 *        工作簿对象
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 */
	public static Map<String, PictureData> getSheetPictrues07(int sheetNum, XSSFSheet sheet, XSSFWorkbook workbook)
	{
		Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
		
		for (POIXMLDocumentPart dr : sheet.getRelations())
		{
			if (dr instanceof XSSFDrawing)
			{
				XSSFDrawing drawing = (XSSFDrawing) dr;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes)
				{
					XSSFPicture pic = (XSSFPicture) shape;
					if (null != pic)
					{
						XSSFClientAnchor anchor = pic.getPreferredSize();
						if (null != anchor)
						{
							CTMarker ctMarker = anchor.getFrom();
							
							// 添加图片索引
							String picIndex = String.valueOf(sheetNum) + UNDER_LINE + ctMarker.getRow() + UNDER_LINE
									+ ctMarker.getCol();
							sheetIndexPicMap.put(picIndex, pic.getPictureData());
						}
					}
				}
			}
		}
		
		return sheetIndexPicMap;
	}
	
	/**
	 * 根据workbook对象，调用2003、2007 解析方法，并返回拼接的Map
	 * key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 * 
	 * @param defaultSheet
	 *        默认工作表，用于做索引key（defaultSheet_rowIndex_cellIndex）
	 * @param sheet1
	 *        工作表对象
	 * @param workbook
	 *        工作薄对象
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 */
	public static Map<String, PictureData> getSheetPictrues(int defaultSheet, Sheet sheet1, Workbook workbook)
	{
		if (workbook instanceof HSSFWorkbook)
		{
			return getSheetPictrues03(defaultSheet, (HSSFSheet) sheet1, (HSSFWorkbook) workbook);
		}
		else if (workbook instanceof XSSFWorkbook)
		{
			return getSheetPictrues07(defaultSheet, (XSSFSheet) sheet1, (XSSFWorkbook) workbook);
		}
		
		return null;
	}
	
	/**
	 * 写入图片至文件夹中
	 * 
	 * @param 从Excel中读取的图片对象
	 * @return 文件存入成功的完整路径
	 */
	public static String writeImageToFile(PictureData pictureData, String fileName)
	{
		if (null != pictureData)
		{
			// 数据流
			byte[] byteData = pictureData.getData();
			
			// 文件后辍
			String ext = pictureData.suggestFileExtension();
			
			FileOutputStream out = null;
			try
			{
				// 按日期命名文件夹2014-09-02
				String pathDate = DateUtils.getNow(DateUtils.SIMPLE_DEFAULT_FORMAT);
				
				// 获取当前工程子目录
				String webPath = ExcelFileUtils.getExcelModelPath(FILE_TEMP_DIR);
				
				// 判断路径是否存在，存在则直接返回
				String path = mkdir(webPath, pathDate);
				
				// 存储的文件名
				String tmpFileName = "/" + fileName + UNDER_LINE + (System.currentTimeMillis()) + POINT + ext;
				File file = null;
				if (StringUtils.isNotEmpty(path))
				{
					file = new File(path + tmpFileName);
					out = new FileOutputStream(file);
					out.write(byteData);
				}
				
				return null == file ? null : FILE_TEMP_DIR + pathDate + tmpFileName;
				
			}
			catch (Exception e)
			{
				LOG.error(e.getMessage());
			}
			finally
			{
				if (null != out)
				{
					try
					{
						out.close();
					}
					catch (IOException e)
					{
						LOG.error(e.getMessage());
					}
				}
			}
		}
		return null;
		
	}
	
	/**
	 * 递归获取创建文件名
	 * <p>
	 * 
	 * @param file
	 *        默认文件名对象
	 * @return 不存在的文件对象
	 */
	// private static File getFileName(File file)
	// {
	// if (null != file)
	// {
	// if (file.exists())
	// {
	// String fileName = file.getName();
	// if (NumberUtils.isDigits(fileName))
	// {
	// file = new File(file.getPath().replace(fileName,
	// String.valueOf(Long.parseLong(fileName) + 1)));
	// getFileName(file);
	// }
	// }
	// else
	// {
	// return file;
	// }
	// }
	//		
	// return file;
	// }
	
	/**
	 * 判断文件夹是否存在，不存在则创建，有则返回完整路径
	 * 
	 * @param pathDate
	 * 
	 * @param fileTempDir
	 *        需要检测的文件路径
	 * @return 返回完整路径
	 */
	private static String mkdir(String tmpPath, String pathDate)
	{
		if (StringUtils.isNotBlank(tmpPath))
		{
			File file = new File(tmpPath + File.separator + pathDate);
			
			if (!file.exists())
			{
				try
				{
					file.mkdirs();
				}
				catch (SecurityException e)
				{
					LOG.error(e.getMessage());
				}
				catch (Exception e)
				{
					LOG.error(e.getMessage());
				}
			}
			
			return file.getPath();
		}
		return null;
	}
}
