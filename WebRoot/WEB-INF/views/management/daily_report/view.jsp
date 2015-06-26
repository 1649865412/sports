<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>创建时间：</label>
		<span class="unit">${dailyReportInfo.fileCreateTime}</span>
	</p>
	<p>
		<label>文件名称：</label>
		<span class="unit">${dailyReportInfo.fileCreateName}</span>
	</p>
	<p>
		<label>默认存放路径：</label>
		<span class="unit">${dailyReportInfo.defaultSavePath}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${dailyReportInfo.remarks}</span>
	</p>
	<p>
		<label>所属组织：</label>
		<span class="unit">${dailyReportInfo.brandId}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${dailyReportInfo.updateTime}</span>
	</p>
	<p>
		<label>预留字段1：</label>
		<span class="unit">${dailyReportInfo.reserve1}</span>
	</p>
	<p>
		<label>预留字段2：</label>
		<span class="unit">${dailyReportInfo.reserve2}</span>
	</p>
	<p>
		<label>预留字段3：</label>
		<span class="unit">${dailyReportInfo.reserve3}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>