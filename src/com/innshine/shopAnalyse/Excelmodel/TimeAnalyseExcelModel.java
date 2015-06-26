package com.innshine.shopAnalyse.Excelmodel;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.innshine.chart.ChartConstants;
import com.innshine.chart.service.impl.ChartServiceImpl;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.ImgPathLine;
import com.innshine.shopAnalyse.util.DateUtil;
import com.innshine.shopAnalyse.util.ExcelConstant;
import com.innshine.shopAnalyse.util.ExcelModelTool;
import com.innshine.shopAnalyse.util.OtherTool;

public class TimeAnalyseExcelModel
{// 表格值,点值，图例，横轴

	public static String NAME = "销售情况对比";

	/**
	 * 表格标题开始行
	 * 
	 */
	private static int TABLETITLE_ROW = 28;

	private static String Name = ExcelConstant.TIME_ANALYSE;

	// 表格值,点值，图例，横轴, 销售分析导出表 纵列
	public synchronized static void exportExcelPoi(
			Map<String, List<Object[]>> dataMap, ImgPathLine imgPathLine,
			String[] fieldNamePlate, HttpServletResponse response,
			HttpServletRequest request, String[] tableCoulumnDay,
			ShopAnalyseCheckEntity shopExcel) throws ParsePropertyException,
			IOException
	{

		ExcelModelTool.setResponses(request, response, Name);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet st = wb.createSheet(Name);
		HSSFCellStyle normalStyle = ExcelModelTool.getStyle(wb, 10);
		int num = TABLETITLE_ROW;
		int count = 0;

		// 表格标题
		for (int i = num; i <= num + 2; i++)
		{
			HSSFRow row = st.createRow(i);
			for (int j = 0; j <= fieldNamePlate.length * 4; j++)
			{
				HSSFCell cell = row.createCell(j);
				st.setColumnWidth(j, 13 * 254);
				cell.setCellStyle(normalStyle);
				if (i == num && j == 0)
					cell.setCellValue("日期");
				else if (i == num && j == 1)
					// cell.setCellValue(NAME+ExcelModelTool.getMarkedWord(shopExcel.getMarkedType()));
					cell.setCellValue(NAME);
				else if (i == num + 1 && j % 4 == 1)
				{
					cell.setCellValue(fieldNamePlate[count]);
					count += 1;
				} else if (i == num + 2 && j % 4 == 1)
					cell.setCellValue("销量");
				else if (i == num + 2 && j % 4 == 2)
					cell.setCellValue("占比");
				else if (i == num + 2 && j % 4 == 3)
					cell.setCellValue("库存量");
				else if (i == num + 2 && j % 4 == 0 && j > 0)
					cell.setCellValue("增长率");
			}
		}
		st.addMergedRegion(new Region(num, (short) 0, 2 + num, (short) (0)));
		st.addMergedRegion(new Region(num, (short) 1, num, (short) (8)));
		for (int m = 0; m <= fieldNamePlate.length; m++)
		{
			st.addMergedRegion(new Region(num + 1, (short) (m * 4 + 1),
					num + 1, (short) (m * 4 + 4)));
		}

		// 表格每天的记录行
		String[] tableday = OtherTool.concat(tableCoulumnDay,
				new String[] { "总计" });
		List<HSSFRow> rowList = ExcelModelTool.getRowList(num + 3, num + 2
				+ tableday.length, st);
		int k = 0;

		for (Entry<String, List<Object[]>> entry : dataMap.entrySet())
		{
			String key = entry.getKey();
			List<Object[]> valueList = entry.getValue();
			for (int j = 0; j < rowList.size(); j++)
			{
				HSSFRow row = rowList.get(j);
				HSSFCell cell0 = row.createCell(0);
				cell0.setCellStyle(normalStyle);
				cell0.setCellValue(tableday[j]);
				Object[] result = valueList.get(j);
				for (int p = 0; p < 4; p++)
				{
					HSSFCell cell = row.createCell(p + 4 * k + 1);
					cell.setCellStyle(normalStyle);
					if (p >= 4 && j != rowList.size() - 1)
					{
						try
						{
							cell.setCellValue(result[p + 1] != null ? DateUtil
									.changeMoneyType(Double
											.parseDouble(result[p + 1] + ""))
									: "暂无");
						} catch (Exception e)
						{
							cell.setCellValue("暂无");
						}
					} else
					{
						try
						{
							cell.setCellValue(result[p] != null ? DateUtil
									.changeMoneyType(Double
											.parseDouble(result[p] + ""))
									: "暂无");
						} catch (Exception e)
						{
							cell.setCellValue("暂无");
						}
					}
				}
			}
			k += 1;
		}

		String imgPath = ChartServiceImpl.getImgPathLine(imgPathLine);
		BufferedImage bufferedImage = ImageIO
				.read(new FileInputStream(imgPath));
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = st.createDrawingPatriarch();
		ExcelModelTool.insertImage(wb, patriarch, ExcelModelTool
				.getImageData(bufferedImage), 0, 0, 20, 25, 0);
		ExcelModelTool.imgDelete(imgPath);
		wb.write(response.getOutputStream());
	}
}
