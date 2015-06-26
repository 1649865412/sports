package com.innshine.shopAnalyse.Excelprocess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.innshine.chart.service.impl.ChartServiceImpl;
import com.innshine.shopAnalyse.excelEntity.ImgPathLine;
import com.innshine.shopAnalyse.util.Excel2Html;
import com.innshine.shopAnalyse.util.ExcelModelTool;
import com.innshine.stockAnalyse.Excelmodel.StockTimeAnalyseExcelModel;
import com.utils.excel.template.ExcelExportByTemplate;
import com.utils.excel.template.ExportConfig;

public class TimeAnalyseProcess
{
	
	/**
	 *时间纵向分析分类导出
	 * 
	 */
	public synchronized static <T> void exportExcelPoi(ExportConfig<T> config, HttpServletResponse response,
			HttpServletRequest request) throws Exception
	{
		ExcelModelTool.setResponses(request, response, config.getName());
		HSSFWorkbook wb = ExcelExportByTemplate.exportByData(config);
		OutputStream out = response.getOutputStream();
		wb.write(out);
	}
	
	/**
	 *时间纵向分析分类导出
	 * 
	 */
	public synchronized static <T> String previewExcelPoi(ExportConfig<T> config, ImgPathLine imgPathLine,
			HttpServletRequest request) throws Exception
	{
		String str = "";
		FileOutputStream os = null;
		try
		{
			HSSFWorkbook wb = ExcelExportByTemplate.exportByData(config);
			String path = new ExcelModelTool().getPathString() + config.getName();
			
			String imgPath = ChartServiceImpl.getImgPathLine(imgPathLine);
			
			os = new FileOutputStream(path);
			wb.write(os);
			os.close();
			str += StockTimeAnalyseExcelModel.getImageHtml(imgPath, request);
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
	
}
