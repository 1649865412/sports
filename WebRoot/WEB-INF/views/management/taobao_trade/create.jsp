<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/taobaoTradeSoldGet/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
	<p>
<label>卖家昵称：</label>
		<input type="text" name="sellerNick" maxlength="50" class="input-medium"/>
	</p>	
	<p>
<label>商品图片绝对途径：</label>
		<input type="text" name="picPath" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>实付金额：</label>
		<input type="text" name="payment" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家是否已评价：</label>
		<input type="text" name="sellerRate" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>邮费：</label>
		<input type="text" name="postFee" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>收货人的姓名：</label>
		<input type="text" name="receiverName" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>收货人的所在省份：</label>
		<input type="text" name="receiverState" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>收货人的详细地址：</label>
		<input type="text" name="receiverAddress" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>收货人的邮编：</label>
		<input type="text" name="receiverZip" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>收货人的手机号码：</label>
		<input type="text" name="receiverMobile" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>收货人的电话号码：</label>
		<input type="text" name="receiverPhone" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家发货时间：</label>
		<input type="text" name="consignTime" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
<label>卖家实际收到的支付宝打款金额：</label>
		<input type="text" name="receivedPayment" maxlength="50" class="input-medium"/>
	</p>	
	<p>
<label>交易编号：</label>
		<input type="text" name="tid" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>商品购买数量：</label>
		<input type="text" name="num" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>商品数字编号：</label>
		<input type="text" name="numIid" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>交易状态：</label>
		<input type="text" name="status" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>交易标题：</label>
		<input type="text" name="title" maxlength="50" class="input-medium"/>
	</p>	
	<p>
<label>交易类型：</label>
		<input type="text" name="type" maxlength="50" class="input-medium"/>
	</p>	
	<p>
<label>商品价格：</label>
		<input type="text" name="price" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>系统优惠金额：</label>
		<input type="text" name="discountFee" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>买家使用积分：</label>
		<input type="text" name="pointFee" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<th title="商品价格乘以数量的总金额">商品金额：</label>
		<input type="text" name="totalFee" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>是否保障速递：</label>
		<input type="text" name="isLgtype" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>表示是否是品牌特卖：</label>
		<input type="text" name="isBrandSale" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>订单是否强制使用物流宝发货：</label>
		<input type="text" name="isForceWlb" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>次日达订单送达时间：</label>
		<input type="text" name="lgAging" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>次日达，三日达等送达类型：</label>
		<input type="text" name="lgAgingType" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>交易创建时间：</label>
		<input type="text" name="created" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
<label>付款时间：</label>
		<input type="text" name="payTime" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
<label>交易修改时：</label>
		<input type="text" name="modified" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
<label>交易结束时间：</label>
		<input type="text" name="endTime" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
<label>买家的支付宝id号：</label>
		<input type="text" name="alipayId" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>支付宝交易号：</label>
		<input type="text" name="alipayNo" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家备注旗帜：</label>
		<input type="text" name="sellerFlag" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>买家昵称：</label>
		<input type="text" name="buyerNick" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>买家下单的地区：</label>
		<input type="text" name="buyerArea" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>订单中是否包含运费险订单：</label>
		<input type="text" name="hasYfx" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>订单的运费险：</label>
		<input type="text" name="yfxFee" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>运费险支付号：</label>
		<input type="text" name="yfxId" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>运费险类型：</label>
		<input type="text" name="yfxType" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>是否有买家留言：</label>
		<input type="text" name="hasBuyerMessage" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>使用信用卡支付金额数：</label>
		<input type="text" name="creditCardFee" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>卡易售垂直表信息：</label>
		<input type="text" name="nutFeature" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>分阶段付款的订单状态：</label>
		<input type="text" name="stepTradeStatus" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>分阶段付款的已付金额：</label>
		<input type="text" name="stepPaidFee" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>订单出现异常问题的时候，给予用户的描述：</label>
		<input type="text" name="markDesc" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>订单将在此时间前发出，主要用于预售订单：</label>
		<input type="text" name="sendTime" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>创建交易时的物流方式：</label>
		<input type="text" name="shippingType" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>卖家手工调整金额：</label>
		<input type="text" name="adjustFee" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>买家获得积分：</label>
		<input type="text" name="buyerObtainPointFee" maxlength="16777215" class="input-medium"/>
	</p>	
	<p>
<label>货到付款服务费：</label>
		<input type="text" name="codFee" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>交易内部来源：</label>
		<input type="text" name="tradeFrom" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>货到付款物流状态：</label>
		<input type="text" name="codStatus" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>交易佣金：</label>
		<input type="text" name="commissionFee" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>买家是否已评价：</label>
		<input type="text" name="buyerRate" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>交易外部来源：</label>
		<input type="text" name="tradeSource" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>卖家是否可以对订单进行评价：</label>
		<input type="text" name="sellerCanRate" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>是否是多次发货的订单：</label>
		<input type="text" name="isPartConsign" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>订单交易是否含有对应的代销采购单：</label>
		<input type="text" name="isDaixiao" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>买家实际使用积分：</label>
		<input type="text" name="realPointFee" maxlength="16777215" class="input-medium"/>
	</p>	
	<p>
<label>收货人的所在城市：</label>
		<input type="text" name="receiverCity" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>收货人的所在地区：</label>
		<input type="text" name="receiverDistrict" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>同步到卖家库的时间：</label>
		<input type="text" name="asyncModified" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
<label>导购宝：</label>
		<input type="text" name="o2o" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>导购员id：</label>
		<input type="text" name="o2oGuideId" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>导购员门店id：</label>
		<input type="text" name="o2oShopId" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>导购员名称：</label>
		<input type="text" name="o2oGuideName" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>导购门店名称：</label>
		<input type="text" name="o2oShopName" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>导购宝提货方式：</label>
		<input type="text" name="o2oDelivery" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>0元购机预授权交易：</label>
		<input type="text" name="zeroPurchase" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>外部订单号：</label>
		<input type="text" name="o2oOutTradeId" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>订单交易是否网厅订单：</label>
		<input type="text" name="isWt" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>付款时使用的支付宝积分的额度：</label>
		<input type="text" name="alipayPoint" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>交易订单下载时间：</label>
		<input type="text" name="updateTime" class="input-medium date validate[required]" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
<label>创建交易接口成功后，返回的支付url：</label>
		<input type="text" name="alipayUrl" maxlength="1000" class="input-medium"/>
	</p>	
	<p>
<label>alipayWarnMsg：</label>
		<input type="text" name="alipayWarnMsg" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>区域id：</label>
		<input type="text" name="areaId" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>物流到货时效截单时间：</label>
		<input type="text" name="arriveCutTime" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>物流到货时效，单位天：</label>
		<input type="text" name="arriveInterval" maxlength="1000" class="input-medium"/>
	</p>	
	<p>
<label>交易中剩余的确认收货金额：</label>
		<input type="text" name="availableConfirmFee" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>买家支付宝账号：</label>
		<input type="text" name="buyerAlipayNo" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>买家货到付款服务费：</label>
		<input type="text" name="buyerCodFee" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>买家邮件地址：</label>
		<input type="text" name="buyerEmail" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>买家备注：</label>
		<input type="text" name="buyerMemo" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>买家备注旗帜：</label>
		<input type="text" name="buyerFlag" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>买家留言：</label>
		<input type="text" name="buyerMessage" maxlength="255" class="input-medium"/>
	</p>	
	<p>
<label>可以评价/可以评价：</label>
		<input type="text" name="canRate" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>物流发货时效，单位小时：</label>
		<input type="text" name="consignInterval" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>电子凭证的垂直信息：</label>
		<input type="text" name="eticketExt" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>快递代收款：</label>
		<input type="text" name="expressAgencyFee" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>是否包含邮费：</label>
		<input type="text" name="hasPostFee" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>发票抬头：</label>
		<input type="text" name="invoiceName" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>发票抬头：</label>
		<input type="text" name="invoiceType" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>是否是3D淘宝交易：</label>
		<input type="text" name="is3d" maxlength="0" class="input-medium"/>
	</p>	
	<p>
<label>天猫点券卡实付款金额,单位分：</label>
		<input type="text" name="pccAf" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>交易促销详细信息：</label>
		<input type="text" name="promotion" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家支付宝账号：</label>
		<input type="text" name="sellerAlipayNo" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家货到付款服务费：</label>
		<input type="text" name="sellerCodFee" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家邮件地址：</label>
		<input type="text" name="sellerEmail" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家备注：</label>
		<input type="text" name="sellerMemo" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家手机：</label>
		<input type="text" name="sellerMobile" maxlength="25" class="input-medium"/>
	</p>	
	<p>
<label>卖家姓名：</label>
		<input type="text" name="sellerName" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>卖家电话：</label>
		<input type="text" name="sellerPhone" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>商品字符串编号：</label>
		<input type="text" name="iid" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>交易快照详细信息：</label>
		<input type="text" name="snapshot" maxlength="100" class="input-medium"/>
	</p>	
	<p>
<label>交易快照地址：</label>
		<input type="text" name="snapshotUrl" maxlength="500" class="input-medium"/>
	</p>	
	<p>
<label>超时到期时间：</label>
		<input type="text" name="timeoutActionTime" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>	
	<p>
<label>交易备注：</label>
		<input type="text" name="tradeMemo" maxlength="255" class="input-medium"/>
	</p>	
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>
