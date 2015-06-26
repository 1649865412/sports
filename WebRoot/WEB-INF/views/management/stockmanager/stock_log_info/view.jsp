<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>stockId：</label>
		<span class="unit">${stockLogInfo.stockId}</span>
	</p>
	<p>
		<label>69码：</label>
		<span class="unit">${stockLogInfo.upccode}</span>
	</p>
	<p>
		<label>现有库存数量：</label>
		<span class="unit">${stockLogInfo.currentStockNumber}</span>
	</p>
	<p>
		<label>可用库存数据：</label>
		<span class="unit">${stockLogInfo.availableStockNumber}</span>
	</p>
	<p>
		<label>用户名：</label>
		<span class="unit">${stockLogInfo.userName}</span>
	</p>
	<p>
		<label>用户编号：</label>
		<span class="unit">${stockLogInfo.userId}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${stockLogInfo.remarks}</span>
	</p>
	<p>
		<label>所属组织：</label>
		<span class="unit">${stockLogInfo.brandId}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${stockLogInfo.updateTime}</span>
	</p>
	<p>
		<label>预留字段1：</label>
		<span class="unit">${stockLogInfo.reserve1}</span>
	</p>
	<p>
		<label>预留字段2：</label>
		<span class="unit">${stockLogInfo.reserve2}</span>
	</p>
	<p>
		<label>预留字段3：</label>
		<span class="unit">${stockLogInfo.reserve3}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>