package com.utils.excel.template;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innshine.chart.service.impl.ChartServiceImpl;
import com.innshine.shopAnalyse.excelEntity.ImgPathLine;
import com.innshine.shopAnalyse.util.ExcelModelTool;

public class ExcelExportByTemplate {
	private static final Logger log = LoggerFactory.getLogger(ExcelExportByTemplate.class);
	public static final String outFileName = "fileout";
	private ExcelExportByTemplate() {
	}

	/************************
	 * 基于jxls 格式的模板导出
	 * 
	 * @param <T>
	 * @param templatepath
	 * @param destDir
	 * @param list
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static <T> String export(String templatepath, String destDir,
			List<T> list, Class<T> cls) throws Exception {
		InputStream is = new BufferedInputStream(new FileInputStream(
				templatepath));
		XLSTransformer transformer = new XLSTransformer();
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("list", list);
		Workbook resultWorkbook = transformer.transformXLS(is, beans);
		is.close();
		createDir(destDir); 
		String ext = suffix(templatepath);
		String savePath = destDir + "/"+outFileName+"." + ext;
		saveWorkbook(resultWorkbook, savePath);
		return savePath;
	}
	
	public static String suffix(String path){
		int lastIndex = path.lastIndexOf(".");
		String ext = "xls";
		if (lastIndex >= 0) {
			ext = path.substring(lastIndex + 1).toLowerCase();
		}
		return ext;
	}

	
	
	/************************
	 * 基于POI格式的模板导出,可指定属性跨行导出；
	 * 查询的数据先要将跨行的数据排序
	 * 具体参照样列
	 * @param ExportConfig<T> 
	 * @return
	 * @throws Exception
	 */
	public static <T> String exportBy(ExportConfig<T> config) throws Exception {
		String templatepath = config.getTemplatePath();
		
		String ext = suffix(templatepath);//生成后缀
		String destDir = config.getDestDir();
		createDir(destDir); //生成 文件存放路径
		String destFile = destDir+"/fileout."+ext;
		
		
		int startRow = config.getStartRow();
		List<T> list = config.getData();
		Class<T> cls = config.getRootCls();
		Vector<String> groupProperties = config.getGroupProperties();
		Vector<String> displayProperties = config.getDisplayProperties();
		int startCell = config.getStartCell();
		boolean read = config.isRead();
		
		List<RowDefine> rowDefines = GroupList.group(list, displayProperties,
				groupProperties, cls);

		File file = new File(templatepath);
		InputStream inputStream = new FileInputStream(file);
		Workbook workbook;
		if (isXls(templatepath)) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			workbook = new XSSFWorkbook(inputStream);
		}
		CellStyle style = initLineStyle(workbook);//表格样式
		Sheet sheet1 = workbook.getSheetAt(config.getWriteSheet());  
		 
		int endRow =  list.size(); 
		 
		List<Step> rowRecord = new ArrayList<Step>();
		for (int i = 0; i < endRow; i++) {
			int startRowIndex = i+startRow;
			Row row = sheet1.getRow(startRowIndex);
			if(!read){
				row = sheet1.createRow(startRowIndex);
			}
				
			RowDefine rowDefine = rowDefines.get(i);
			//RowDefine rowDefine =null;
			List<CellDefine> cellDefines = rowDefine.getCells();
			int cellLen = cellDefines.size();
			for (int j = 0; j < cellLen; j++) {
				CellDefine cellDefine =  cellDefines.get(j);
				String property = cellDefine.getProperty(); 
				int colRow = cellDefine.getColRow();
				int stepCell = j+startCell; 
				if(colRow > 1){ 
					Step perStep = Step.foundStep(property,rowRecord);
					 
					if(perStep == null){
						Step step = new Step(property,i, i+colRow,stepCell,stepCell);
						rowRecord.add(step);
						saveCellValue(read,stepCell,row,style,cellDefine.getValue());
						 
						 
					}else{
						//int start = perStep.getStart();
						int end = perStep.getEnd();
						if(i > end){
							Step step = new Step(property,i, i+colRow,stepCell,stepCell);
							rowRecord.add(step);
							saveCellValue(read,stepCell,row,style,cellDefine.getValue());
						} 
					}
				}else{
					saveCellValue(read,stepCell,row,style,cellDefine.getValue());
				}
				
			}
		}
		for(Step step : rowRecord){
			int start = step.getStart()+startRow;
			int end = step.getEnd()+startRow -1;
			sheet1.addMergedRegion(new CellRangeAddress(start,end,step.getColStart(),step.getColEnd()));
		}
		  
		saveWorkbook(workbook,destFile); 
		inputStream.close(); 
		return destFile;
	 
	}
	
	/**
	 * 功能:分类两个以上的，最后一列不合并
	 * <p>作者 杨荣忠 2014-9-29 下午12:06:23
	 * @param groupProperties
	 * @return
	 */
	public static Vector<String>  getNewGroupProperties(Vector<String> groupProperties ){
		Vector<String> groupPropertiesNew=new Vector<String>();
		if(groupProperties!=null){
			int size=groupProperties.size();
			if(size>=2){
				//groupPropertiesNew=groupProperties.remove(size);
				for(int i=0;i<size-1;i++){
					groupPropertiesNew.add(groupProperties.get(i));
				}
				
			}
		}
		return groupPropertiesNew;
	}
	
	/**
	 * 功能:1.7版销售价格分析导出方法
	 * <p>作者 杨荣忠 2014-10-31 下午04:23:17
	 * @param <T>
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public static<T> HSSFWorkbook  exportByData(ExportConfig<T>config) throws Exception {
		List<T> list = config.getData();
		Class<T> cls = config.getRootCls();
		
		Vector<String> groupProperties =config.getGroupProperties() ;
		
		Vector<String> displayProperties = config.getDisplayProperties();
		Vector<String> header = config.getHeader();
		
		int startCell = config.getStartCell();
		int startRow = config.getStartRow();
		
		boolean read = false;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet1 = workbook.createSheet("sheet1");
		CellStyle style = initLineStyle(workbook);  
		
		
		//忠，增加图
		ImgPathLine  imgPathLine=config.getImgPathLine();
		writeSheetImg(imgPathLine,sheet1,workbook,startRow);
		//忠，增加标题与条件
	 	writeSheetTitle(sheet1,header,startRow-1,startCell,style,config ); 	
		
		writeSheetHeader(sheet1,header,startRow,startCell,style,config );	
		
		startRow++;	
		if(list!=null){
			int endRow =  list.size(); 
			
			List<RowDefine> rowDefines=GroupList.group(list, displayProperties, groupProperties, cls);
			
			List<Step> rowRecord = new ArrayList<Step>();
			
			
			for (int i = 0; i < endRow; i++) {
				int startRowIndex = i+startRow;
				Row  row = sheet1.createRow(startRowIndex);
				  
				RowDefine rowDefine = rowDefines.get(i);
				List<CellDefine> cellDefines = rowDefine.getCells();
				
				int cellLen = cellDefines.size();
				for (int j = 0; j < cellLen; j++) {
					CellDefine cellDefine =  cellDefines.get(j);
					String property = cellDefine.getProperty(); 
					int colRow = cellDefine.getColRow();
					int stepCell = j+startCell; 
					
					if(colRow > 1){ 
						Step perStep = Step.getSameNewStep(property,rowRecord);
							Step step = new Step(property,i, i+colRow,stepCell,stepCell);
							rowRecord.add(step);
							saveCellValue(read,stepCell,row,style,cellDefine.getValue());
					}else{
						saveCellValue(read,stepCell,row,style,cellDefine.getValue());
					}
				}
			}
			
			for(Step step : rowRecord){
			//	System.out.println("============="+step.getProperty());
				int start = step.getStart()+startRow;
				int end = step.getEnd()+startRow -1;
				
				//忠：参数：起始行号，终止行号， 起始列号，终止列号
				sheet1.addMergedRegion(new CellRangeAddress(start,end,step.getColStart(),step.getColEnd()));
			}
		}  
	
	//	InputStream in = save(workbook);
		return workbook;
	}
	
	
	
	public static <T> InputStream exportByDataInputStream(ExportConfig<T> config) throws Exception {
		HSSFWorkbook workbook= exportByData(config);
		InputStream in = save(workbook);
		return in;
		
	}
	
	
	public static<T> void exportByDataToExcel(ExportConfig<T> config, HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook= exportByData(config);
		OutputStream out = response.getOutputStream();
		workbook.write(out);
	}
	
	
	private static InputStream save(HSSFWorkbook workbook) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			InputStream bis = new ByteArrayInputStream(bos.toByteArray());
			return bis;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private static<T> void writeSheetHeader(HSSFSheet sheet,Vector<String> header,int startRow,int startCell,CellStyle style,ExportConfig<T>config ) {
		HSSFRow row = sheet.createRow(startRow);
		int columnWidth=config.getColumnWidth();
		int cellIndex = startCell;
		for (String head : header) { 
			HSSFCell cell = row.createCell(cellIndex);
		    sheet.setColumnWidth(cellIndex, columnWidth * 256); 
			cell.setCellValue(head);
			cell.setCellStyle(style);
			cellIndex ++ ;
		}
	}
	
	
	public  static String getExcelName(String name){
		String str="";
		int index=name.indexOf(".");
		str=name.substring(0,index);
		return str;
	}	
	

	private static void writeSheetImg(ImgPathLine  imgPathLine,HSSFSheet st,HSSFWorkbook wb,int startRow) throws FileNotFoundException, IOException{
		if(imgPathLine!=null){
			String imgPath = ChartServiceImpl.getImgPathLine(imgPathLine);
			BufferedImage bufferedImage = ImageIO
					.read(new FileInputStream(imgPath));
			// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = st.createDrawingPatriarch();
			ExcelModelTool.insertImage(wb, patriarch, ExcelModelTool
					.getImageData(bufferedImage), 0, 0, startRow-5, startRow-5, 0);// 列行
			ExcelModelTool.imgDelete(imgPath);
		}
	}
	
	
	//忠：设置表格title
	private static<T> void writeSheetTitle(HSSFSheet sheet,Vector<String> header,int startRowTitle,int startCell,
			CellStyle style,ExportConfig<T>config ) {
		HSSFRow row = sheet.createRow(startRowTitle);
		//增加文件name属性与查询过滤条件
		String name=config.getName();
		int columnWidth=config.getColumnWidth();
		String contiditonText=config.getConditionText();
		String space=" ";
		
		int cellIndex =startCell;
		
		for (String head : header) { 
			HSSFCell cell = row.createCell(cellIndex);
		    sheet.setColumnWidth(cellIndex,columnWidth * 256); 
			cell.setCellStyle(style);
			if(cellIndex==startCell){
				cell.setCellValue(getExcelName(name)+space+contiditonText);
			}
			cellIndex ++ ;
		}
		
		sheet.addMergedRegion(new Region(startRowTitle, (short) 0, startRowTitle,
				(short) (cellIndex-1)));
	}
	
	
	private static void saveCellValue(boolean read,int cellIndex,Row row,CellStyle style,String value){
		Cell cell = row.getCell(cellIndex); 
		if(!read){
			cell = row.createCell(cellIndex);
			cell.setCellStyle(style);
		} 
		//判断值为空的时候"-"
		if(value==null||value.equals("")){
			cell.setCellValue("-");
		}else{
			cell.setCellValue(value);
		}
	//	try{
			//转金钱格式
			// cell.setCellValue(DateUtil.changeMoneyType(Double.parseDouble(value)));
			 
		/*}
		catch(Exception e){
			 cell.setCellValue(value);
		}*/
	}
	 
	
	public static CellStyle initLineStyle(Workbook wb){
		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return style;  
	}
	
	public static CellStyle yellowStyle(Workbook wb){
		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN); 
		style.setFillBackgroundColor((short)Color.yellow.getAlpha());
		return style;  
	}

	public static boolean isXls(String filePath) {
		int lastIndex = filePath.lastIndexOf(".");
		String ext = "xls";
		if (lastIndex >= 0) {
			ext = filePath.substring(lastIndex + 1).toLowerCase();
		}
		return ext.equals("xls");
	}

	public static void createDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void saveWorkbook(Workbook resultWorkbook, String fileName)
			throws IOException {
		OutputStream os = new BufferedOutputStream(new FileOutputStream(
				fileName));
		resultWorkbook.write(os);
		os.flush();
		os.close();
	}
}
 