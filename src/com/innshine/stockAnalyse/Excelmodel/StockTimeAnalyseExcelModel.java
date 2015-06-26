package com.innshine.stockAnalyse.Excelmodel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.innshine.chart.ChartConstants;
import com.innshine.chart.service.impl.ChartServiceImpl;
import com.innshine.shopAnalyse.util.Excel2Html;
import com.innshine.stockAnalyse.util.Constant;
import com.innshine.stockAnalyse.util.ExcelModelTool;
import com.innshine.stockAnalyse.util.OtherTool;

public class StockTimeAnalyseExcelModel
{// 表格值,点值，图例，横轴

	public static String NAME = "库存情况对比";
	
	public synchronized static void exportExcelPoi(Map<String, List<Object[]>> dataMap, double[][] data,
			String[] fieldNamePlate, String[] fieldName, String[] tableday, HttpServletResponse response,
			HttpServletRequest request) throws ParsePropertyException, IOException
	{
		
		String name = Constant.TIEM_ANAYSE;
		ExcelModelTool.setResponses(request, response, name);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet st = wb.createSheet(name);
		createExcelContent(dataMap, fieldNamePlate, tableday, wb, st);
		
		String imgPath = createChartImage(data, fieldNamePlate, fieldName);
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imgPath));
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = st.createDrawingPatriarch();
		ExcelModelTool.insertImage(wb, patriarch, ExcelModelTool.getImageData(bufferedImage), 0, 0, 20, 25, 0);
		ExcelModelTool.imgDelete(imgPath);
		wb.write(response.getOutputStream());
	}
	
	public synchronized static String previewExcelPoi(Map<String, List<Object[]>> dataMap, double[][] data,
			String[] fieldNamePlate, String[] fieldName, String[] tableday, HttpServletResponse response,
			HttpServletRequest request) throws ParsePropertyException, IOException
	{
		FileOutputStream os = null;
		String str = "";
		try
		{
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet st = wb.createSheet(Constant.TIEM_ANAYSE);
			createExcelContent(dataMap, fieldNamePlate, tableday, wb, st);
			String imgPath = createChartImage(data, fieldNamePlate, fieldName);
			String path = new com.innshine.shopAnalyse.util.ExcelModelTool().getPathString() + Constant.TIEM_ANAYSE;
			
			os = new FileOutputStream(path);
			wb.write(os);
			
			str += getImageHtml(imgPath, request);
			str += Excel2Html.ExcelToHtml(path);
			File sourcefile = new File(path);
			sourcefile.delete();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (null != os)
			{
				os.close();
			}
		}
		
		return str;
		
	}
	
	/**
	 * 把图片拼接起来，以img标签的形式
	 * 
	 * @param imagetPaths
	 *        生成的图片完整路径名
	 * @param request
	 *        请求对象
	 * @return 拼接好的img标签，
	 */
	public static String getImageHtml(String imagetPath, HttpServletRequest request)
	{
		if (StringUtils.isNotEmpty(imagetPath) && null != request)
		{
			// 获取web完整路径
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/excelImg/";
			StringBuffer buffer = new StringBuffer();
			
			try
			{
				File srcFile = new File(imagetPath);
				String tmpPath = basePath + srcFile.getName();
				String image = "<img src='" + tmpPath + "' width='100%' height='400px' />";
				buffer.append(image);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			return buffer.toString();
		}
		
		return "";
	}
	
	private static String createChartImage(double[][] data, String[] fieldNamePlate, String[] fieldName)
	{
		String imgPath = ChartServiceImpl.getImgPathLine(data, fieldNamePlate, fieldName, "时间", "总库存量", "时间纵向库存分析报表",
				ChartConstants.LINE_CHART);
		return imgPath;
	}
	
	private static void createExcelContent(Map<String, List<Object[]>> dataMap, String[] fieldNamePlate,
			String[] tableday, HSSFWorkbook wb, HSSFSheet st)
	{
		HSSFCellStyle normalStyle = ExcelModelTool.getStyle(wb, 10);
		int num = 28;
		int count = 0;
		
		for (int i = num; i <= num + 2; i++)
		{
			HSSFRow row = st.createRow(i);
			for (int j = 0; j <= fieldNamePlate.length * 6; j++)
			{
				HSSFCell cell = row.createCell(j);
				st.setColumnWidth(j, 13 * 256);
				cell.setCellStyle(normalStyle);
				if (i == num && j == 0)
					cell.setCellValue("日期");
				else if (i == num && j == 1)
					cell.setCellValue(NAME);
				else if (i == num + 1 && j % 6 == 1)
				{
					cell.setCellValue(fieldNamePlate[count]);
					count += 1;
				}
				else if (i == num + 2 && j % 6 == 1)
					cell.setCellValue("库存数量");
				else if (i == num + 2 && j % 6 == 2)
					cell.setCellValue("订货数量");
				else if (i == num + 2 && j % 6 == 3)
					cell.setCellValue("出货数量");
				else if (i == num + 2 && j % 6 == 4)
					cell.setCellValue("到货率(%)");
				else if (i == num + 2 && j % 6 == 5)
					cell.setCellValue("动销率(%)");
				else if (i == num + 2 && j % 6 == 0 && j > 0)
					cell.setCellValue("售罄率(%)");
			}
		}
		st.addMergedRegion(new Region(num, (short) 0, 2 + num, (short) (0)));
		st.addMergedRegion(new Region(num, (short) 1, num, (short) (12)));
		for (int m = 0; m <= fieldNamePlate.length; m++)
		{
			st.addMergedRegion(new Region(num + 1, (short) (m * 6 + 1), num + 1, (short) (m * 6 + 6)));
		}
		tableday = OtherTool.concat(tableday, new String[] { "总计" });
		List<HSSFRow> rowList = ExcelModelTool.getRowList(num + 3, num + 2 + tableday.length, st);
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
				for (int p = 0; p < 6; p++)
				{
					HSSFCell cell = row.createCell(p + 6 * k + 1);
					cell.setCellStyle(normalStyle);
					try
					{
						if (p == 0)
						{
							String value = result[p] != null ? result[p] + "" : "-";
							cell.setCellValue(value);
						}
						else
						{
							cell.setCellValue(result[p] != null ? result[p] + "" : "-");
						}
					}
					catch (Exception e)
					{
						cell.setCellValue("-");
					}
				}
			}
			k += 1;
		}
	}
}
