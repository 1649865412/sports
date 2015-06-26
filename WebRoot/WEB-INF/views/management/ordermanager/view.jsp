<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>69码：</label>
		<span class="unit">${orderFormInfo.upccode}</span>
	</p>
	<p>
		<label>款号：</label>
		<span class="unit">${orderFormInfo.materialNumber}</span>
	</p>
	<p>
		<label>平台ID：</label>
		<span class="unit">${orderFormInfo.platformId}</span>
	</p>
	<p>
		<label>订货数量：</label>
		<span class="unit">${orderFormInfo.orderNumber}</span>
	</p>
	<p>
		<label>订货金额：</label>
		<span class="unit">${orderFormInfo.orderAmount}</span>
	</p>
	<p>
		<label>转仓数量：</label>
		<span class="unit">${orderFormInfo.transferNumber}</span>
	</p>
	<p>
		<label>调货数量：</label>
		<span class="unit">${orderFormInfo.transferCargoNumber}</span>
	</p>
	<p>
		<label>退货数量：</label>
		<span class="unit">${orderFormInfo.returnNumber}</span>
	</p>
	<p>
		<label>预计到货期：</label>
		<span class="unit">${orderFormInfo.predictArriveTime}</span>
	</p>
	<p>
		<label>样品数量：</label>
		<span class="unit">${orderFormInfo.sampleNumber}</span>
	</p>
	<p>
		<label>出库单号：</label>
		<span class="unit">${orderFormInfo.storehouseId}</span>
	</p>
	<p>
		<label>颜色：</label>
		<span class="unit">${orderFormInfo.color}</span>
	</p>
	<p>
		<label>色号：</label>
		<span class="unit">${orderFormInfo.colourNumber}</span>
	</p>
	<p>
		<label>尺码：</label>
		<span class="unit">${orderFormInfo.productSize}</span>
	</p>
	<p>
		<label>到货数量：</label>
		<span class="unit">${orderFormInfo.arrivalNumber}</span>
	</p>
	<p>
		<label>大款：</label>
		<span class="unit">${orderFormInfo.productLarge}</span>
	</p>
	<p>
		<label>店铺名称：</label>
		<span class="unit">${orderFormInfo.shopName}</span>
	</p>
	<p>
		<label>客户名称：</label>
		<span class="unit">${orderFormInfo.customerName}</span>
	</p>
	<p>
		<label>波次号：</label>
		<span class="unit">${orderFormInfo.waveNumber}</span>
	</p>
	<p>
		<label>原出库单号：</label>
		<span class="unit">${orderFormInfo.oldStorehouseId}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${orderFormInfo.remarks}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${orderFormInfo.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>