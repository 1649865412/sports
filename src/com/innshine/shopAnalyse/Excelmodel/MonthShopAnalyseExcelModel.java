package com.innshine.shopAnalyse.Excelmodel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innshine.productinfo.utils.ExcelFileUtils;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.MonthEntity;
import com.innshine.shopAnalyse.util.DateUtil;
import com.innshine.shopAnalyse.util.ExcelModelTool;
import com.utils.Exceptions;
import com.utils.excel.FileExcelUtils;
import com.utils.excel.FileExcelUtils.ImageType;

public class MonthShopAnalyseExcelModel
{
	
	public static String NAME = "月度统计报表";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MonthShopAnalyseExcelModel.class);
	/**
	 * 表格标题开始行
	 * 
	 */
	private static int TABLETITLE_ROW = 1;
	
	/**
	 * 产品标题开始行
	 * 
	 */
	private static int PRODUCT_TITLE = 2;
	
	/**
	 * 图片插入开始行
	 * 
	 */
	private static int IMAGE_START_ROW = 1;
	
	/**
	 * 图片插入结束行
	 * 
	 */
	private static int IMAGE_END_ROW = 20;
	
	/**
	 * 图片插入开始列
	 * 
	 */
	private static int IMAGE_START_CELL = 0;
	
	/**
	 * 图片插入结束列
	 * 
	 */
	private static int IMAGE_END_CELL = 12;
	
	/**
	 *月报统计表字段
	 * 
	 */
	private static String[] fieldName = new String[] { "销量", "销售额(元)", "销售占比(%)", "增长率(%)" };
	
	public synchronized static HSSFWorkbook exportExcel(Map<String, List<MonthEntity>> datamap, List timeList,
			String name, ShopAnalyseCheckEntity shopExcel, String title, List<String> imagetPaths)
			throws ParsePropertyException, IOException
	{
		int img = shopExcel.getImg();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet st = wb.createSheet(name);
		HSSFCellStyle normalStyle = ExcelModelTool.getStyle(wb, 10);
		
		// add 2014-10-17 问题描述：将销量、销售额分开，折线表时趋势 begin
		// 首先先写入图片
		boolean isImage = writerImage(imagetPaths, wb, st);
		// add 2014-10-17 问题描述：将销量、销售额分开，折线表时趋势 end
		
		int tableStartRow = getTableTitleRow(isImage);
		int titleStartRow = tableStartRow + 1;
		HSSFRow row = st.createRow(tableStartRow);
		row.setHeight((short) (16 * 20));
		
		HSSFRow row1 = st.createRow(titleStartRow);
		HSSFRow row2 = st.createRow(titleStartRow + 1);
		
		HSSFCellStyle titleStyle = ExcelModelTool.getStyle(wb, 12);
		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 13);// 字号
		f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
		titleStyle.setFont(f);
		int n = 0;
		
		for (int i = 0; i < timeList.size() * 4 + 1; i++)
		{
			HSSFCell cell = row.createCell(i);
			if (i < 5)
				cell.setCellStyle(titleStyle);
			HSSFCell cell1 = row1.createCell(i);
			HSSFCell cell2 = row2.createCell(i);
			cell1.setCellStyle(normalStyle);
			cell2.setCellStyle(normalStyle);
			// st.setColumnWidth(i, 17 * 256);
			if (i == 0)
			{
				// st.setColumnWidth(i, 15 * 256);
				// +ExcelModelTool.getMarkedWord(shopExcel.getMarkedType()));
				cell.setCellValue(NAME);
				cell2.setCellValue(StringUtils.isNotEmpty(title) ? title : "");
			}
			else
			{
				cell2.setCellValue(fieldName[i % 4 == 0 ? 3 : (i % 4) - 1]);
				// cell2.setCellValue("hello");
			}
			if ((i - 1) % 4 == 0)
			{
				cell1.setCellValue(timeList.get(n).toString().substring(0, 7));
				n++;
			}
		}
		
		st.addMergedRegion(new Region(tableStartRow, (short) 0, tableStartRow, (short) 4));
		
		for (int i = 0; i < timeList.size(); i++)
		{
			st.addMergedRegion(new Region(titleStartRow, (short) (i * 4 + 1), titleStartRow, (short) (i * 4 + 4)));
		}
		
		// 开始数据处理
		Map<String, List<MonthEntity>> map = datamap;
		List plateList = new ArrayList();
		int i = tableStartRow;// 记录行数
		for (Entry<String, List<MonthEntity>> entry : map.entrySet())
		{
			String key = entry.getKey();
			List<MonthEntity> valueList = entry.getValue();
			HSSFRow rowPlate = st.createRow(i + 3);
			int k = -1;//
			
			for (int j = 0; j < timeList.size() * 4 + 1; j++)
			{
				HSSFCell cell = rowPlate.createCell(j);
				cell.setCellStyle(normalStyle);
				if (j == 0)
				{
					cell.setCellValue(key);
					plateList.add(key);
				}
				else if ((j - 1) % 4 == 0)
				{
					k++;
					cell.setCellValue(DateUtil.changeMoneyType(valueList.get(k).getSales()));
				}
				else if ((j - 1) % 4 == 1)
				{
					cell.setCellValue(DateUtil.changeMoneyType(valueList.get(k).getAmount()));
				}
				else if ((j - 1) % 4 == 2)
				{
					cell.setCellValue(valueList.get(k).getAccounted());
				}
				else if ((j - 1) % 4 == 3)
				{
					cell.setCellValue(valueList.get(k).getRateOfRise());
				}
			}
			i++;
		}
		return wb;
	}
	
	/**
	 *根据写入图片行数， 获取标题开始行
	 * 
	 * @param isImage
	 *        是否写入图片成功
	 * @return
	 */
	private static int getTableTitleRow(boolean isImage)
	{
		if (isImage)
		{
			return IMAGE_END_ROW + 2;
		}
		
		return TABLETITLE_ROW;
	}
	
	/**
	 * 根据图片的完整路径，写入excel
	 * 
	 * @param imagetPaths
	 *        存储图片集体
	 * @param sheet
	 *        工作表对象
	 * @param workbook
	 *        工作薄对象
	 * @return true:写入成功，表示开始行需要往下推移 ，false:写入失败或其它原因，开始行保持不变
	 */
	private static boolean writerImage(List<String> imagetPaths, HSSFWorkbook workbook, HSSFSheet sheet)
	{
		boolean isImage = false;
		if (CollectionUtils.isNotEmpty(imagetPaths))
		{
			for (int i = 0; i < imagetPaths.size(); i++)
			{
				String path = imagetPaths.get(i);
				if (StringUtils.isNotEmpty(path))
				{
					File pictureFile = null;
					
					try
					{
						pictureFile = new File(path);
						FileExcelUtils.addPictureToExcel(sheet, workbook, pictureFile, IMAGE_START_ROW, IMAGE_END_ROW,
								IMAGE_START_CELL + (i > 0 ? IMAGE_END_CELL + 1 : 0), IMAGE_END_CELL
										+ (i > 0 ? IMAGE_END_CELL + 1 : 0), ImageType.PNG.name());
						
						isImage = isImage ? isImage : true;
					}
					catch (Exception e)
					{
						LOG.error(Exceptions.getStackTraceAsString(e));
					}
					finally
					{
						ExcelFileUtils.deleteFile(pictureFile);
					}
					
				}
			}
		}
		return isImage;
	}
}
