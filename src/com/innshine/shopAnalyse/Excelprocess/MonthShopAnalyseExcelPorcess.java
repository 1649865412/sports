package com.innshine.shopAnalyse.Excelprocess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.innshine.shopAnalyse.Excelmodel.MonthShopAnalyseExcelModel;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.excelEntity.MonthEntity;
import com.innshine.shopAnalyse.util.Excel2Html;
import com.innshine.shopAnalyse.util.ExcelModelTool;

public class MonthShopAnalyseExcelPorcess extends MonthShopAnalyseExcelModel
{
	
	/**
	 * 月报统计导出
	 * 
	 */
	public synchronized static void exportExcelPoi(Map<String, List<MonthEntity>> datamap, List timeList,
			HttpServletResponse response, HttpServletRequest request, String name, ShopAnalyseCheckEntity shopExcel,
			String title, List<String> imagetPaths) throws Exception
	{
		ExcelModelTool.setResponses(request, response, name);
		HSSFWorkbook wb = exportExcel(datamap, timeList, name, shopExcel, title, imagetPaths);
		OutputStream out = response.getOutputStream();
		wb.write(out);
	}
	
	/**
	 *月报统计预览
	 * 
	 * @param imagetPaths
	 * 
	 */
	public synchronized static String exportPreviewPoi(Map<String, List<MonthEntity>> datamap, List timeList,
			HttpServletResponse response, HttpServletRequest request, String name, ShopAnalyseCheckEntity shopExcel,
			String title, List<String> imagetPaths) throws Exception
	{
		String str = "";
		HSSFWorkbook wb = exportExcel(datamap, timeList, name, shopExcel, title, null);
		String path = new ExcelModelTool().getPathString() + name;
		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();
		
		// 获得图片，拼接成imag标签，以谢谢页面展示
		str = getImageHtml(imagetPaths, request);
		str += Excel2Html.ExcelToHtml(path);
		File sourcefile = new File(path);
		sourcefile.delete();
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
	private static String getImageHtml(List<String> imagetPaths, HttpServletRequest request)
	{
		if (CollectionUtils.isNotEmpty(imagetPaths) && null != request)
		{
			// 获取web完整路径
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/excelImg/";
			StringBuffer buffer = new StringBuffer();
			for (String path : imagetPaths)
			{
				if (StringUtils.isNotEmpty(path))
				{
					try
					{
						File srcFile = new File(path);
						String tmpPath = basePath + srcFile.getName();
						String image = "<img src='" + tmpPath + "' width='50%' height='400px' />";
						buffer.append(image);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			
			return buffer.toString();
		}
		
		return "";
	}
}
