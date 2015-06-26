package com.innshine.shopAnalyse.Excelprocess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.innshine.shopAnalyse.util.Excel2Html;
import com.innshine.shopAnalyse.util.ExcelModelTool;
import com.utils.excel.template.ExcelExportByTemplate;
import com.utils.excel.template.ExportConfig;

public class TOP10_Day_ShopAnalyseType_ExcelPorcess
{

	/**
	 *top10,销售日报,销售分析单个,销售分析分类导出
	 * 
	 */
	public synchronized static <T> void exportExcelPoi(ExportConfig<T> config,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception
	{
		ExcelModelTool.setResponses(request, response, config.getName());
		HSSFWorkbook wb = ExcelExportByTemplate.exportByData(config);
		OutputStream out = response.getOutputStream();
		wb.write(out);
	}

	
	
	/**
	 * top10,销售日报,销售分析单个,销售分析分类预览
	 * 
	 */
	public synchronized static <T> String exportPreviewPoi(
			ExportConfig<T> config, HttpServletResponse response,
			HttpServletRequest request) throws Exception
	{
		String str = "";
		HSSFWorkbook wb = ExcelExportByTemplate.exportByData(config);
		String path = new ExcelModelTool().getPathString() + config.getName();
		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();
		str = Excel2Html.ExcelToHtml(path);
		File sourcefile = new File(path);
		sourcefile.delete();
		return str;
	}

}
