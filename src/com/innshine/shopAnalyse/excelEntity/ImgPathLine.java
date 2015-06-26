package com.innshine.shopAnalyse.excelEntity;

public class ImgPathLine {
	
	
	/**
	 * 报表组件线状图与柱状图bean
	 */
	private double[][] data;
	private String[] rowKeys;
	private String[] columnKeys;
	private String xname;
	private String yname;
	private String reportName;
	private int imgtype;
	
	
	public ImgPathLine(){};
	

	
	public ImgPathLine(double[][] data, String[] rowKeys, String[] columnKeys,
			String xname, String yname, String reportName, int imgtype) {
		super();
		this.data = data;
		this.rowKeys = rowKeys;
		this.columnKeys = columnKeys;
		this.xname = xname;
		this.yname = yname;
		this.reportName = reportName;
		this.imgtype = imgtype;
	}



	public double[][] getData() {
		return data;
	}
	public void setData(double[][] data) {
		this.data = data;
	}
	public String[] getRowKeys() {
		return rowKeys;
	}
	public void setRowKeys(String[] rowKeys) {
		this.rowKeys = rowKeys;
	}
	public String[] getColumnKeys() {
		return columnKeys;
	}
	public void setColumnKeys(String[] columnKeys) {
		this.columnKeys = columnKeys;
	}
	public String getXname() {
		return xname;
	}
	public void setXname(String xname) {
		this.xname = xname;
	}
	public String getYname() {
		return yname;
	}
	public void setYname(String yname) {
		this.yname = yname;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public int getImgtype() {
		return imgtype;
	}
	public void setImgtype(int imgtype) {
		this.imgtype = imgtype;
	}
	
	
}
