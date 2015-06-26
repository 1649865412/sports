package com.utils.excel.template;

import java.util.List;
import java.util.Vector;

import com.innshine.shopAnalyse.excelEntity.ImgPathLine;

public class ExportConfig<T> { 
	private Class<T> rootCls;
	private List<T> data;
	private Vector<String> displayProperties;
	private Vector<String> groupProperties;
	private Vector<String> header;
	
	private String templatePath;
	private String destDir;
	
	
	private Integer startRow = 1;
	private Integer startCell = 0;
	
	private Integer writeSheet = 0;
	//read 为 true 时，只填写数据，不创建行，列
	private boolean read = false;
	
	//忠
	private String name;// "XX报表.xls";
	private String conditionText;//过滤条件;
	private ImgPathLine imgPathLine;//图 
	private int columnWidth=16;
	

	
	public int getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}
	public ImgPathLine getImgPathLine() {
		return imgPathLine;
	}
	public void setImgPathLine(ImgPathLine imgPathLine) {
		this.imgPathLine = imgPathLine;
	}
	public String getConditionText() {
		return conditionText;
	}
	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Vector<String> getHeader() {
		return header;
	}
	public void setHeader(Vector<String> header) {
		this.header = header;
	}
	
	public Integer getStartCell() {
		return startCell;
	}
	public void setStartCell(Integer startCell) {
		this.startCell = startCell;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public Integer getWriteSheet() {
		return writeSheet;
	}
	public void setWriteSheet(Integer writeSheet) {
		this.writeSheet = writeSheet;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Class<T> getRootCls() {
		return rootCls;
	}
	/*************
	 * 基础类
	 * @param rootCls
	 */
	public void setRootCls(Class<T> rootCls) {
		this.rootCls = rootCls;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Vector<String> getDisplayProperties() {
		return displayProperties;
	}
	public void setDisplayProperties(Vector<String> displayProperties) {
		this.displayProperties = displayProperties;
	}
	public Vector<String> getGroupProperties() {
		return groupProperties;
	}
	public void setGroupProperties(Vector<String> groupProperties) {
		this.groupProperties = groupProperties;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getDestDir() {
		return destDir;
	}
	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}
	
}
