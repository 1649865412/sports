package com.innshine.stockAnalyse.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelModelTool
{
	public static void setResponses(HttpServletRequest request,
			HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException
	{
		response.reset();
		response.setContentType(ExcelConstant.APPLICATION_X_MSDOWNLOAD);
		response.setCharacterEncoding(ExcelConstant.DEAULT_ENCODE_UTF_8);
		if (request.getHeader(ExcelConstant.USER_AGENT).toLowerCase().indexOf(
				ExcelConstant.FIREFOX) > 0)
		{
			fileName = new String(fileName
					.getBytes(ExcelConstant.DEAULT_ENCODE_UTF_8),
					ExcelConstant.ENCODE_ISO8859_1);// firefox浏览器
		} else
		{
			fileName = URLEncoder.encode(fileName,
					ExcelConstant.DEAULT_ENCODE_UTF_8)
					.replace(ExcelConstant.BLANK_SPACE_20,
							ExcelConstant.BLANK_CHARACTER);
		}
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
	}

	// 公共样式
	public static HSSFCellStyle getStyle(HSSFWorkbook wb, int fontsize)
	{
		HSSFCellStyle normalStyle = wb.createCellStyle();
		normalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		normalStyle.setWrapText(true);
		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) fontsize);// 字号
		f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
		normalStyle.setFont(f);
		return normalStyle;
	}

	// 自定义的方法,插入某个图片到指定索引的位置
	public static void insertImage(HSSFWorkbook wb, HSSFPatriarch pa,
			byte[] data, int column, int row, int endcolumn, int endrow,
			int index)
	{
		int x1 = index * 250;
		int y1 = 0;
		int x2 = x1 + 255;
		int y2 = 255;
		HSSFClientAnchor anchor = new HSSFClientAnchor(x1, y1, x2, y2,
				(short) column, row, (short) endcolumn, endrow);// 列行
		anchor.setAnchorType(2);
		pa.createPicture(anchor, wb.addPicture(data,
				HSSFWorkbook.PICTURE_TYPE_JPEG));
	}

	// 从图片里面得到字节数组
	public static byte[] getImageData(BufferedImage bi)
	{
		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ImageIO.write(bi, "PNG", bout);
			return bout.toByteArray();
		} catch (Exception exe)
		{
			exe.printStackTrace();
			return null;
		}
	}

	// 生成行list（避免重新生成复盖样式）
	public static List<HSSFRow> getRowList(int begin, int end, HSSFSheet st)
	{
		List list = new ArrayList();
		for (int i = begin; i <= end; i++)
		{
			HSSFRow row = st.createRow(i);
			row.setHeight((short) (18 * 20));
			list.add(row);
		}
		return list;
	}

	public static String getMarkedWord(int MarkedType)
	{
		String str = "";
		if (MarkedType == 1)
		{
			str = Constant.MARKED_WORD;
		}
		return str;
	}

	// 生成data数据
	public static List getdataList(Object[] listObject, int num)
	{
		List list = new ArrayList();
		list.add(num);
		for (int i = 0; i < listObject.length; i++)
		{
			list.add(listObject[i] != null ? listObject[i] : "暂无数据");
		}
		return list;
	}

	// 删除上部生成的图片
	public static void imgDelete(String imgPath)
	{
		File file = new File(imgPath);
		if (file != null)
			file.delete();
	}

}
