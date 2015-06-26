<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>69码：</label>
		<span class="unit">${salesStockDetails.upccode}</span>
	</p>
	<p>
		<label>销售时间：</label>
		<span class="unit">${salesStockDetails.salesTime}</span>
	</p>
	<p>
		<label>销量：</label>
		<span class="unit">${salesStockDetails.salesNumber}</span>
	</p>
	<p>
		<label>成交价（均价）：</label>
		<span class="unit">${salesStockDetails.avgCurrentPrice}</span>
	</p>
	<p>
		<label>销售金额：</label>
		<span class="unit">${salesStockDetails.salesAmount}</span>
	</p>
	<p>
		<label>仓库：</label>
		<span class="unit">${salesStockDetails.storeHouse}</span>
	</p>
	<p>
		<label>入库时间：</label>
		<span class="unit">${salesStockDetails.enteringTime}</span>
	</p>
	<p>
		<label>订货量：</label>
		<span class="unit">${salesStockDetails.orderNumber}</span>
	</p>
	<p>
		<label>库存量：</label>
		<span class="unit">${salesStockDetails.stockNumber}</span>
	</p>
	<p>
		<label>到货量：</label>
		<span class="unit">${salesStockDetails.arriveNumber}</span>
	</p>
	<p>
		<label>预计到货期：</label>
		<span class="unit">${salesStockDetails.predictArriveTime}</span>
	</p>
	<p>
		<label>实际到货时间：</label>
		<span class="unit">${salesStockDetails.factArriveTime}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${salesStockDetails.remarks}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${salesStockDetails.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>