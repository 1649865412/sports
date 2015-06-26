package com.innshine.shopAnalyse.util;

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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class ExcelModelTool {

	public static void setResponses(HttpServletRequest request,
			HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException {

		response.reset();
		response.setContentType(ExcelConstant.APPLICATION_X_MSDOWNLOAD);
		response.setCharacterEncoding(ExcelConstant.DEAULT_ENCODE_UTF_8);
		if (request.getHeader(ExcelConstant.USER_AGENT).toLowerCase().indexOf(
				ExcelConstant.FIREFOX) > 0) {
			fileName = new String(fileName
					.getBytes(ExcelConstant.DEAULT_ENCODE_UTF_8),
					ExcelConstant.ENCODE_ISO8859_1);// firefox浏览器
		} else {
			fileName = URLEncoder.encode(fileName,
					ExcelConstant.DEAULT_ENCODE_UTF_8)
					.replace(ExcelConstant.BLANK_SPACE_20,
							ExcelConstant.BLANK_CHARACTER);
		}
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
	}

	// 公共样式
	public static HSSFCellStyle getStyle(HSSFWorkbook wb, int fontsize) {
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
			int index) {
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
	public static byte[] getImageData(BufferedImage bi) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ImageIO.write(bi, "PNG", bout);
			return bout.toByteArray();
		} catch (Exception exe) {
			exe.printStackTrace();
			return null;
		}
	}

	// 生成行list（避免重新生成复盖样式）begin，end 对应索引号
	public static List<HSSFRow> getRowList(int begin, int end, HSSFSheet st) {
		List list = new ArrayList();
		for (int i = begin; i <= end; i++) {
			HSSFRow row = st.createRow(i);
			row.setHeight((short) (18 * 20));
			list.add(row);
		}
		return list;
	}

	// 删除上部生成的图片
	public static void imgDelete(String imgPath) {
		File file = new File(imgPath);
		if (file != null)
			file.delete();
	}

	// 总和：
	public static Double getSum(List<Object[]> list, int num) {
		Double SalesAccounted = 0.0;
		List sumList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			try {
				SalesAccounted += list.get(i)[num] != null ? Float
						.parseFloat(list.get(i)[num] + "") : 0;
			} catch (Exception e) {
				SalesAccounted += 0;
			}
		}
		return SalesAccounted;
	}

	// 生成data数据
	public static List getdataList(Object[] listObject, int num) {
		List list = new ArrayList();
		list.add(num);
		for (int i = 0; i < listObject.length; i++) {
			list.add(listObject[i] != null ? listObject[i] : "暂无数据");
		}
		return list;
	}

	public static String getMarkedWord(int MarkedType) {
		String str = "";
		if (MarkedType == 1) {
			str = Constant.MARKED_WORD;
		}
		return str;
	}

	/**
	 * 创建提示标题
	 * 
	 */
	public static void createRowPoint(int cloumnBegin, int cloumnEnd,
			int rowNum, HSSFSheet st, HSSFCellStyle normalStyle,
			String PointTitle) {
		HSSFRow row = st.createRow(rowNum);
		for (int i = cloumnBegin; i <= cloumnEnd; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(normalStyle);
			if (i == cloumnBegin) {
				cell.setCellValue(PointTitle);
			}
		}
		st.addMergedRegion(new Region(rowNum, (short) cloumnBegin, rowNum,
				(short) (cloumnEnd)));
	}

	/**
	 * 创建文件生成路径+文件名的前缀
	 * 
	 * @param destDirName
	 *            目标目录名
	 * @return
	 */
	public String getPathString() {
		String url = this.getClass().getResource("").getPath().replaceAll(
				"%20", " ");

		String tempPath = url.substring(0, url.indexOf("WEB-INF"))
				+ "excelPreview/";

		if (createDir(tempPath)) {
			tempPath = tempPath + DateUtil.getTimeMillis();
		}
		return tempPath;
	}

	/**
	 * 创建目录
	 * 
	 * @param destDirName
	 *            目标目录名
	 * @return 目录创建成功返回true，或目录已创建返回true ，否则返回false
	 */
	public boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			// System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
			return true;
		}
		// endsWith(String suffix)测试此字符串是否以指定的后缀结束
		// public static final String separator 与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。
		// 此字符串只包含一个字符，即 separatorChar。即为"\"后缀结尾。
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建单个目录
		if (dir.mkdirs()) {
			// System.out.println("创建父目录和子目录" + destDirName + "成功！");
			return true;
		} else {
			// System.out.println("创建指定父目录中的子目录" + destDirName + "失败！");
			return false;
		}
	}
}
