<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
  <style type="text/css">
	p{width:150px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;}
</style>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
			<label>
				卖家昵称：
			</label>
			<span class="unit">${Trade.sellerNick}</span>
		</p>
		<p>
			<label>
				实付金额：
			</label>
			<span class="unit">${Trade.payment}</span>
		</p>
		<p>
			<label>
				卖家是否已评价：
			</label>
			<span class="unit">${Trade.sellerRate}</span>
		</p>
		<p>
			<label>
				邮费：
			</label>
			<span class="unit">${Trade.postFee}</span>
		</p>
		<p>
			<label>
				收货人的姓名：
			</label>
			<span class="unit">${Trade.receiverName}</span>
		</p>
		<p>
			<label>
				收货人的所在省份：
			</label>
			<span class="unit">${Trade.receiverState}</span>
		</p>
		<p>
			<label>
				收货人的详细地址：
			</label>
			<span class="unit" title="${Trade.receiverAddress}">${Trade.receiverAddress}</span>
		</p>
		<p>
			<label>
				收货人的邮编：
			</label>
			<span class="unit">${Trade.receiverZip}</span>
		</p>
		<p>
			<label>
				收货人的手机号码：
			</label>
			<span class="unit">${Trade.receiverMobile}</span>
		</p>
		<p>
			<label>
				收货人的电话号码：
			</label>
			<span class="unit">${Trade.receiverPhone}</span>
		</p>
		<p>
			<label>
				卖家发货时间：
			</label>
			<span class="unit">${Trade.consignTime}</span>
		</p>
		<p>
			<label>
				实收支付宝打款金额：
			</label>
			<span class="unit">${Trade.receivedPayment}</span>
		</p>
		<p>
			<label>
				交易编号：
			</label>
			<span class="unit">${Trade.tid}</span>
		</p>
		<p>
			<label>
				商品购买数量：
			</label>
			<span class="unit">${Trade.num}</span>
		</p>
		<p>
			<label>
				商品数字编号：
			</label>
			<span class="unit">${Trade.numIid}</span>
		</p>
		<p>
			<label>
				交易状态：
			</label>
			<span class="unit">
			  <c:if test="${empty taobaoTradeStatusMap}">
					&nbsp;
				</c:if>
				<c:if test="${not empty taobaoTradeStatusMap}">
					<c:forEach items="${taobaoTradeStatusMap}" var="mapItems">
						<c:if test="${mapItems.key eq Trade.status}">${mapItems.value}</c:if>
					</c:forEach>
				</c:if>
			</span>
		</p>
		<p>
			<label>
				交易标题：
			</label>
			<span class="unit">${Trade.title}</span>
		</p>
		<p>
			<label>
				交易类型：
			</label>
			<span class="unit">${Trade.type}</span>
		</p>
		<p>
			<label>
				商品价格：
			</label>
			<span class="unit">${Trade.price}</span>
		</p>
		<p>
			<label>
				系统优惠金额：
			</label>
			<span class="unit">${Trade.discountFee}</span>
		</p>
		<p>
			<label>
				买家使用积分：
			</label>
			<span class="unit">${Trade.pointFee}</span>
		</p>
		<p>
			<th title="商品价格乘以数量的总金额">
				商品金额：
				</label>
				<span class="unit">${Trade.totalFee}</span>
		</p>
		<p>
			<label>
				是否保障速递：
			</label>
			<span class="unit">${Trade.isLgtype}</span>
		</p>
		<p>
			<label>
				表示是否是品牌特卖：
			</label>
			<span class="unit">${Trade.isBrandSale}</span>
		</p>
		<p>
			<label>
				否强制使用物流宝发货：
			</label>
			<span class="unit">${Trade.isForceWlb}</span>
		</p>
		<p>
			<label>
				次日达订单送达时间：
			</label>
			<span class="unit">${Trade.lgAging}</span>
		</p>
		<p>
			<label>
				次日达，三日达等送达类型：
			</label>
			<span class="unit">${Trade.lgAgingType}</span>
		</p>
		<p>
			<label>
				交易创建时间：
			</label>
			<span class="unit">${Trade.created}</span>
		</p>
		<p>
			<label>
				付款时间：
			</label>
			<span class="unit">${Trade.payTime}</span>
		</p>
		<p>
			<label>
				交易修改时：
			</label>
			<span class="unit">${Trade.modified}</span>
		</p>
		<p>
			<label>
				交易结束时间：
			</label>
			<span class="unit">${Trade.endTime}</span>
		</p>
		<p>
			<label>
				买家的支付宝id号：
			</label>
			<span class="unit">${Trade.alipayId}</span>
		</p>
		<p>
			<label>
				支付宝交易号：
			</label>
			<span class="unit">${Trade.alipayNo}</span>
		</p>
		<p>
			<label>
				卖家备注旗帜：
			</label>
			<span class="unit">${Trade.sellerFlag}</span>
		</p>
		<p>
			<label>
				买家昵称：
			</label>
			<span class="unit">${Trade.buyerNick}</span>
		</p>
		<p>
			<label>
				买家下单的地区：
			</label>
			<span class="unit">${Trade.buyerArea}</span>
		</p>
		<p>
			<label>
				是否包含运费险订单：
			</label>
			<span class="unit">${Trade.hasYfx}</span>
		</p>
		<p>
			<label>
				订单的运费险：
			</label>
			<span class="unit">${Trade.yfxFee}</span>
		</p>
		<p>
			<label>
				运费险支付号：
			</label>
			<span class="unit">${Trade.yfxId}</span>
		</p>
		<p>
			<label>
				运费险类型：
			</label>
			<span class="unit">${Trade.yfxType}</span>
		</p>
		<p>
			<label>
				是否有买家留言：
			</label>
			<span class="unit">${Trade.hasBuyerMessage}</span>
		</p>
		<p>
			<label>
				使用信用卡支付金额数：
			</label>
			<span class="unit">${Trade.creditCardFee}</span>
		</p>
		<p>
			<label>
				卡易售垂直表信息：
			</label>
			<span class="unit">${Trade.nutFeature}</span>
		</p>
		<p>
			<label>
				分阶段付款的订单状态：
			</label>
			<span class="unit">${Trade.stepTradeStatus}</span>
		</p>
		<p>
			<label>
				分阶段付款的已付金额：
			</label>
			<span class="unit">${Trade.stepPaidFee}</span>
		</p>
		<p>
			<label>
				订单出现异常问题的时候，给予用户的描述：
			</label>
			<span class="unit">${Trade.markDesc}</span>
		</p>
		<p>
			<label>
				订单将在此时间前发出，主要用于预售订单：
			</label>
			<span class="unit">${Trade.sendTime}</span>
		</p>
		<p>
			<label>
				创建交易时的物流方式：
			</label>
			<span class="unit">${Trade.shippingType}</span>
		</p>
		<p>
			<label>
				卖家手工调整金额：
			</label>
			<span class="unit">${Trade.adjustFee}</span>
		</p>
		<p>
			<label>
				买家获得积分：
			</label>
			<span class="unit">${Trade.buyerObtainPointFee}</span>
		</p>
		<p>
			<label>
				货到付款服务费：
			</label>
			<span class="unit">${Trade.codFee}</span>
		</p>
		<p>
			<label>
				交易内部来源：
			</label>
			<span class="unit">${Trade.tradeFrom}</span>
		</p>
		<p>
			<label>
				货到付款物流状态：
			</label>
			<span class="unit">${Trade.codStatus}</span>
		</p>
		<p>
			<label>
				交易佣金：
			</label>
			<span class="unit">${Trade.commissionFee}</span>
		</p>
		<p>
			<label>
				买家是否已评价：
			</label>
			<span class="unit">${Trade.buyerRate}</span>
		</p>
		<p>
			<label>
				交易外部来源：
			</label>
			<span class="unit">${Trade.tradeSource}</span>
		</p>
		<p>
			<label>
				对订单进行评价：
			</label>
			<span class="unit">${Trade.sellerCanRate}</span>
		</p>
		<p>
			<label>
				是否是多次发货的订单：
			</label>
			<span class="unit">${Trade.isPartConsign}</span>
		</p>
		<p>
			<label>
				是否含有代销采购单：
			</label>
			<span class="unit">${Trade.isDaixiao}</span>
		</p>
		<p>
			<label>
				买家实际使用积分：
			</label>
			<span class="unit">${Trade.realPointFee}</span>
		</p>
		<p>
			<label>
				收货人的所在城市：
			</label>
			<span class="unit">${Trade.receiverCity}</span>
		</p>
		<p>
			<label>
				收货人的所在地区：
			</label>
			<span class="unit">${Trade.receiverDistrict}</span>
		</p>
		<p>
			<label>
				同步到卖家库的时间：
			</label>
			<span class="unit">${Trade.asyncModified}</span>
		</p>
		<p>
			<label>
				导购宝：
			</label>
			<span class="unit">${Trade.o2o}</span>
		</p>
		<p>
			<label>
				导购员id：
			</label>
			<span class="unit">${Trade.o2oGuideId}</span>
		</p>
		<p>
			<label>
				导购员门店id：
			</label>
			<span class="unit">${Trade.o2oShopId}</span>
		</p>
		<p>
			<label>
				导购员名称：
			</label>
			<span class="unit">${Trade.o2oGuideName}</span>
		</p>
		<p>
			<label>
				导购门店名称：
			</label>
			<span class="unit">${Trade.o2oShopName}</span>
		</p>
		<p>
			<label>
				导购宝提货方式：
			</label>
			<span class="unit">${Trade.o2oDelivery}</span>
		</p>
		<p>
			<label>
				0元购机预授权交易：
			</label>
			<span class="unit">${Trade.zeroPurchase}</span>
		</p>
		<p>
			<label>
				外部订单号：
			</label>
			<span class="unit">${Trade.o2oOutTradeId}</span>
		</p>
		<p>
			<label>
				订单交易是否网厅订单：
			</label>
			<span class="unit">${Trade.isWt}</span>
		</p>
		<p>
			<label>
				交易订单下载时间：
			</label>
			<span class="unit">${Trade.updateTime}</span>
		</p>
		<p>
			<label>
				创建交易接口成功后，返回的支付url：
			</label>
			<span class="unit">${Trade.alipayUrl}</span>
		</p>
		<p>
			<label>
				alipayWarnMsg：
			</label>
			<span class="unit">${Trade.alipayWarnMsg}</span>
		</p>
		<p>
			<label>
				区域id：
			</label>
			<span class="unit">${Trade.areaId}</span>
		</p>
		<p>
			<label>
				物流到货时效截单时间：
			</label>
			<span class="unit">${Trade.arriveCutTime}</span>
		</p>
		<p>
			<label>
				物流到货时效，单位天：
			</label>
			<span class="unit">${Trade.arriveInterval}</span>
		</p>
		<p>
			<label>
				交易中剩余的确认收货金额：
			</label>
			<span class="unit">${Trade.availableConfirmFee}</span>
		</p>
		<p>
			<label>
				买家支付宝账号：
			</label>
			<span class="unit">${Trade.buyerAlipayNo}</span>
		</p>
		<p>
			<label>
				买家货到付款服务费：
			</label>
			<span class="unit">${Trade.buyerCodFee}</span>
		</p>
		<p>
			<label>
				买家邮件地址：
			</label>
			<span class="unit">${Trade.buyerEmail}</span>
		</p>
		<p>
			<label>
				买家备注：
			</label>
			<span class="unit">${Trade.buyerMemo}</span>
		</p>
		<p>
			<label>
				买家备注旗帜：
			</label>
			<span class="unit">${Trade.buyerFlag}</span>
		</p>
		<p>
			<label>
				买家留言：
			</label>
			<span class="unit">${Trade.buyerMessage}</span>
		</p>
		<p>
			<label>
				可以评价/可以评价：
			</label>
			<span class="unit">${Trade.canRate}</span>
		</p>
		<p>
			<label>
				物流发货时效，单位小时：
			</label>
			<span class="unit">${Trade.consignInterval}</span>
		</p>
		<p>
			<label>
				电子凭证的垂直信息：
			</label>
			<span class="unit">${Trade.eticketExt}</span>
		</p>
		<p>
			<label>
				快递代收款：
			</label>
			<span class="unit">${Trade.expressAgencyFee}</span>
		</p>
		<p>
			<label>
				是否包含邮费：
			</label>
			<span class="unit">${Trade.hasPostFee}</span>
		</p>
		<p>
			<label>
				发票抬头：
			</label>
			<span class="unit">${Trade.invoiceName}</span>
		</p>
		<p>
			<label>
				发票抬头：
			</label>
			<span class="unit">${Trade.invoiceType}</span>
		</p>
		<p>
			<label>
				是否是3D淘宝交易：
			</label>
			<span class="unit">${Trade.is3D}</span>
		</p>
		<p>
			<label>
				天猫点券卡实付款金额,单位分：
			</label>
			<span class="unit">${Trade.pccAf}</span>
		</p>
		<p>
			<label>
				交易促销详细信息：
			</label>
			<span class="unit">${Trade.promotion}</span>
		</p>
		<p>
			<label>
				卖家支付宝账号：
			</label>
			<span class="unit">${Trade.sellerAlipayNo}</span>
		</p>
		<p>
			<label>
				卖家货到付款服务费：
			</label>
			<span class="unit">${Trade.sellerCodFee}</span>
		</p>
		<p>
			<label>
				卖家邮件地址：
			</label>
			<span class="unit">${Trade.sellerEmail}</span>
		</p>
		<p>
			<label>
				卖家备注：
			</label>
			<span class="unit">${Trade.sellerMemo}</span>
		</p>
		<p>
			<label>
				卖家手机：
			</label>
			<span class="unit">${Trade.sellerMobile}</span>
		</p>
		<p>
			<label>
				卖家姓名：
			</label>
			<span class="unit">${Trade.sellerName}</span>
		</p>
		<p>
			<label>
				卖家电话：
			</label>
			<span class="unit">${Trade.sellerPhone}</span>
		</p>
		<p>
			<label>
				商品字符串编号：
			</label>
			<span class="unit">${Trade.iid}</span>
		</p>
		<p>
			<label>
				交易快照详细信息：
			</label>
			<span class="unit">${Trade.snapshot}</span>
		</p>
		<p>
			<label>
				交易快照地址：
			</label>
			<span class="unit">${Trade.snapshotUrl}</span>
		</p>
		<p>
			<label>
				超时到期时间：
			</label>
			<span class="unit">${Trade.timeoutActionTime}</span>
		</p>
		<p>
			<label>
				交易备注：
			</label>
			<span class="unit">${Trade.tradeMemo}</span>
		</p>
	</div>

	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							关闭
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
