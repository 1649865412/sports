<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
   <style type="text/css">
	p{width:150px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;
</style>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>69码：</label>
		<span class="unit">${nbSalesDetails.upccode}</span>
	</p>
	<p>
		<label>款号：</label>
		<span class="unit">${nbSalesDetails.materialNumber}</span>
	</p>
	<p>
		<label>平台ID：</label>
		<span class="unit">${nbSalesDetails.platformId}</span>
	</p>
	<p>
		<label>销售起止时间：</label>
		<span class="unit">${nbSalesDetails.marketStartTime}</span>
	</p>
	<p>
		<label>销售结束时间：</label>
		<span class="unit">${nbSalesDetails.marketEndTime}</span>
	</p>
	<p>
		<label>销售数量：</label>
		<span class="unit">${nbSalesDetails.salesNumber}</span>
	</p>
	<p>
		<label>成交价（均价）：</label>
		<span class="unit">${nbSalesDetails.avgCurrentPrice}</span>
	</p>
	<p>
		<label>销售金额：</label>
		<span class="unit">${nbSalesDetails.salesAmount}</span>
	</p>
	<p>
		<label>仓库：</label>
		<span class="unit">${nbSalesDetails.storeHouse}</span>
	</p>
	<p>
		<label>入库时间：</label>
		<span class="unit">${nbSalesDetails.enteringTime}</span>
	</p>
	<p>
		<label>订货量：</label>
		<span class="unit">${nbSalesDetails.orderNumber}</span>
	</p>
	<p>
		<label>库存量：</label>
		<span class="unit">${nbSalesDetails.stockNumber}</span>
	</p>
	<p>
		<label>到货量：</label>
		<span class="unit">${nbSalesDetails.arriveNumber}</span>
	</p>
	<p>
		<label>预计到货期：</label>
		<span class="unit">${nbSalesDetails.predictArriveTime}</span>
	</p>
	<p>
		<label>实际到货时间：</label>
		<span class="unit">${nbSalesDetails.factArriveTime}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${nbSalesDetails.remarks}</span>
	</p>
	
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${nbSalesDetails.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>