package com.innshine.shopAnalyse.Excelmodel;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.innshine.chart.ChartConstants;
import com.innshine.chart.service.impl.ChartServiceImpl;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.MonthEntity;
import com.innshine.shopAnalyse.util.DateUtil;
import com.innshine.shopAnalyse.util.ExcelModelTool;

public class MonthExcelModel
{

	public static String NAME = "月度报表";

	/**
	 * 表格标题开始行
	 * 
	 */
	private static int TABLETITLE_ROW = 30;

	/**
	 * 产品标题开始行
	 * 
	 */
	private static int PRODUCT_TITLE = 31;

	private static String[] fieldName = new String[] { "销量", "销售额(元)",
			"销售占比(%)" };

	public synchronized static HSSFWorkbook exportExcel(
			Map<String, List<MonthEntity>> datamap, List timeList, String name,
			ShopAnalyseCheckEntity shopExcel) throws ParsePropertyException,
			IOException
	{
		int img = shopExcel.getImg();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet st = wb.createSheet(name);
		HSSFCellStyle normalStyle = ExcelModelTool.getStyle(wb, 10);

		HSSFRow row = st.createRow(TABLETITLE_ROW);
		row.setHeight((short) (16 * 20));

		HSSFRow row1 = st.createRow(PRODUCT_TITLE);
		HSSFRow row2 = st.createRow(PRODUCT_TITLE + 1);

		HSSFCellStyle titleStyle = ExcelModelTool.getStyle(wb, 12);
		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 13);// 字号
		f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
		titleStyle.setFont(f);
		int n = 0;

		for (int i = 0; i < timeList.size() * 3 + 1; i++)
		{
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			HSSFCell cell1 = row1.createCell(i);
			HSSFCell cell2 = row2.createCell(i);
			cell1.setCellStyle(normalStyle);
			cell2.setCellStyle(normalStyle);
			st.setColumnWidth(i, 17 * 256);
			if (i == 0)
			{
				st.setColumnWidth(i, 15 * 256);
				// +ExcelModelTool.getMarkedWord(shopExcel.getMarkedType()));
				cell.setCellValue(NAME);
				cell2.setCellValue("Q季");
			} else
			{
				cell2.setCellValue(fieldName[i % 3 == 0 ? 2 : (i % 3) - 1]);
			}
			if ((i - 1) % 3 == 0)
			{
				cell1.setCellValue(timeList.get(n).toString().substring(0, 7));
				n++;
			}
		}

		st.addMergedRegion(new Region(TABLETITLE_ROW, (short) 0,
				TABLETITLE_ROW, (short) (timeList.size() * 2)));

		for (int i = 0; i < timeList.size(); i++)
		{
			st.addMergedRegion(new Region(PRODUCT_TITLE, (short) (i * 3 + 1),
					PRODUCT_TITLE, (short) (i * 3 + 3)));
		}

		// 开始数据处理
		Map<String, List<MonthEntity>> map = datamap;
		List plateList = new ArrayList();
		int i = TABLETITLE_ROW;// 记录行数
		for (Entry<String, List<MonthEntity>> entry : map.entrySet())
		{
			String key = entry.getKey();
			List<MonthEntity> valueList = entry.getValue();
			HSSFRow rowPlate = st.createRow(i + 3);
			int k = -1;//

			for (int j = 0; j < timeList.size() * 3 + 1; j++)
			{
				HSSFCell cell = rowPlate.createCell(j);
				cell.setCellStyle(normalStyle);
				if (j == 0)
				{
					cell.setCellValue(key);
					plateList.add(key);
				} else if ((j - 1) % 3 == 0)
				{
					k++;
					cell.setCellValue(DateUtil.changeMoneyType(valueList.get(k)
							.getSales()));
				} else if ((j - 1) % 3 == 1)
				{
					cell.setCellValue(DateUtil.changeMoneyType(valueList.get(k)
							.getAmount()));
				} else if ((j - 1) % 3 == 2)
				{
					cell.setCellValue(valueList.get(k).getAccounted());
				}
			}
			i++;
		}
		String imgPath = getImgPath(img, map, timeList);
		BufferedImage bufferedImage = ImageIO
				.read(new FileInputStream(imgPath));
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = st.createDrawingPatriarch();
		ExcelModelTool.insertImage(wb, patriarch, ExcelModelTool
				.getImageData(bufferedImage), 0, 0, 11, 27, 0);
		ExcelModelTool.imgDelete(imgPath);
		return wb;

	}

	// img:0柱状图片 1饼状图
	public static String getImgPath(int img,
			Map<String, List<MonthEntity>> map, List timeList)
	{
		String imgPath = "";

		List plateList = getPlateList(map, timeList, img);
		int k = 0;
		if (img == 0)
		{
			double[][] data = new double[plateList.size()][timeList.size()];

			for (Entry<String, List<MonthEntity>> entry : map.entrySet())
			{
				List<MonthEntity> valueList = entry.getValue();
				for (int p = 0; p < valueList.size(); p++)
				{
					data[k][p] = valueList.get(p).getAmount();
				}
				k++;
			}
			imgPath = ChartServiceImpl.getImgPathLine(data,
					(String[]) plateList.toArray(new String[plateList.size()]),
					(String[]) timeList.toArray(new String[timeList.size()]),
					"时间", "销售额(元)", "MonthReport", ChartConstants.BAR_CHART);
		}

		if (img == 1)
		{
			double[] data = new double[plateList.size()];
			for (Entry<String, List<MonthEntity>> entry : map.entrySet())
			{
				if (k != map.size() - 1)
				{
					double sum = 0;
					List<MonthEntity> valueList = entry.getValue();
					for (int p = 0; p < valueList.size(); p++)
					{
						sum += valueList.get(p).getAmount();
					}
					data[k] = sum;
					k++;
				}
			}

			imgPath = ChartServiceImpl.getImgPathPie(data, (String[]) plateList
					.toArray(new String[plateList.size()]),
					"MonthReport(Sales_Amount),单位:元");
		}
		return imgPath;
	}

	public static List getPlateList(Map<String, List<MonthEntity>> map,
			List timeList, int imgType)
	{
		List plateList = new ArrayList();

		for (Entry<String, List<MonthEntity>> entry : map.entrySet())
		{
			String key = entry.getKey();
			List<MonthEntity> valueList = entry.getValue();
			for (int j = 0; j < timeList.size() * 3 + 1; j++)
			{
				if (j == 0)
				{
					plateList.add(key);
				}
			}
		}
		if (imgType == 1)
			plateList.remove(plateList.size() - 1);
		return plateList;
	}
}
