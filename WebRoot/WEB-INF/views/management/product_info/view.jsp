<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
  <style type="text/css">
	p{width:150px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;
</style>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>产品名称：</label>
		<span class="unit">${productInfo.productName}</span>
	</p>
		<p>
		<label>69码：</label>
		<span class="unit">${productInfo.productUpccode}</span>
	</p>
	<p>
		<label>平台ID：</label>
		<span class="unit">${productInfo.productPlatformId}</span>
	</p>
	<p>
		<label>款号：</label>
		<span class="unit">${productInfo.materialNumber}</span>
	</p>
	<p>
		<label>Inline/2ndline：</label>
		<span class="unit">${productInfo.inlineOr2ndline}</span>
	</p>
	<p>
		<label>LF/PF(系列)：</label>
		<span class="unit">${productInfo.productLfPf}</span>
	</p>
	<p>
		<label>系统编号（款号+宽度）：</label>
		<span class="unit">${productInfo.systemId}</span>
	</p>
	<p>
		<label>季节：</label>
		<span class="unit">${productInfo.quater}</span>
	</p>
	<p>
		<label>重量：</label>
		<span class="unit">${productInfo.productWeight}</span>
	</p>
	<p>
		<label>系列：</label>
		<span class="unit">${productInfo.series}</span>
	</p>
	<p>
		<label>吊牌价：</label>
		<span class="unit">${productInfo.tagPrice}</span>
	</p>
	<p>
		<label>材质：</label>
		<span class="unit">${productInfo.materialQuality}</span>
	</p>
	<p>
		<label>商品定位：</label>
		<span class="unit">${productInfo.productPosition}</span>
	</p>
	<p>
		<label>功能：</label>
		<span class="unit">${productInfo.productFunction}</span>
	</p>
	<p>
		<label>上市月份：</label>
		<span class="unit">${productInfo.onMarketMonth}</span>
	</p>
	
	<p>
		<label>上架日期：</label>
		<span class="unit">${productInfo.onSalesTime}</span>
	</p>
	<p>
		<label>品牌：</label>
		<span class="unit">${productInfo.productBrand}</span>
	</p>
	<p>
		<label>产地：</label>
		<span class="unit">${productInfo.productPlace}</span>
	</p>
	<p>
		<label>颜色：</label>
		<span class="unit">${productInfo.productColor}</span>
	</p>
	<p>
		<label>尺码：</label>
		<span class="unit">${productInfo.productSize}</span>
	</p>
	<p>
		<label>类别：</label>
		<span class="unit">${productInfo.productType}</span>
	</p>
	<p>
		<label>性别：</label>
		<span class="unit">${productInfo.productSex}</span>
	</p>
	<p>
		<label>属性：</label>
		<span class="unit">${productInfo.productAttribute}</span>
	</p>
	<p>
		<label>鞋底：</label>
		<span class="unit">${productInfo.shoesBottom}</span>
	</p>
	<p>
		<label>色号：</label>
		<span class="unit">${productInfo.colorId}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${productInfo.updateTime}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${productInfo.remark}</span>
	</p>
	<p style="height: 55px;">
		<label>图片：</label>
		<c:if test="${not empty productInfo.image}">
			<img alt="图片不存在" src="${contextPath }${productInfo.image}" style="width: 160px;height: 55px;">
		</c:if>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>