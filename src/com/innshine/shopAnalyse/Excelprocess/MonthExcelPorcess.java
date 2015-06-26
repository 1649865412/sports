package com.innshine.shopAnalyse.Excelprocess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.innshine.shopAnalyse.Excelmodel.MonthExcelModel;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.MonthEntity;
import com.innshine.shopAnalyse.util.Excel2Html;
import com.innshine.shopAnalyse.util.ExcelModelTool;

public class MonthExcelPorcess extends MonthExcelModel
{

	/**
	 * 月报导出
	 * 
	 */
	public synchronized static void exportExcelPoi(
			Map<String, List<MonthEntity>> datamap, List timeList,
			HttpServletResponse response, HttpServletRequest request,
			String name, ShopAnalyseCheckEntity shopExcel) throws Exception
	{
		ExcelModelTool.setResponses(request, response, name);
		HSSFWorkbook wb = exportExcel(datamap, timeList, name, shopExcel);
		OutputStream out = response.getOutputStream();
		wb.write(out);
	}

	/**
	 *月报预览
	 * 
	 */
	public synchronized static String exportPreviewPoi(
			Map<String, List<MonthEntity>> datamap, List timeList,
			HttpServletResponse response, HttpServletRequest request,
			String name, ShopAnalyseCheckEntity shopExcel) throws Exception
	{
		String str = "";
		HSSFWorkbook wb = exportExcel(datamap, timeList, name, shopExcel);
		String path = new ExcelModelTool().getPathString() + name;
		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();
		str = Excel2Html.ExcelToHtml(path);
		String imgPath = getImgPath(shopExcel.getImg(), datamap, timeList);
		int lastIndex = imgPath.lastIndexOf(File.separator);
		String imgName = imgPath.substring(lastIndex + 1);
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/excelImg/" + imgName;
		File sourcefile = new File(path);
		sourcefile.delete();
		String strImg = "<img src='" + basePath
				+ "' width='1100px' height='600px'><br>";
		strImg += str;
		return strImg;
	}
}
