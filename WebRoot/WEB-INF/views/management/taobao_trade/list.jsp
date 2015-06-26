<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/taobaoTradeSoldGet/list" page="${page }">
	<input type="hidden" name="search_EQ_tid" value="${param.search_EQ_tid}"/>
	<input type="hidden" name="search_GTE_totalFee" value="${param.search_GTE_totalFee}"/>
	<input type="hidden" name="search_LTE_totalFee" value="${param.search_LTE_totalFee}"/>
	<input type="hidden" name="search_GTE_created" value="${param.search_GTE_created}"/>
	<input type="hidden" name="search_LTE_created" value="${param.search_LTE_created}"/>
	<input type="hidden" name="search_GTE_payTime" value="${param.search_GTE_payTime}"/>
	<input type="hidden" name="search_LTE_payTime" value="${param.search_LTE_payTime}"/>
	<input type="hidden" name="search_GTE_updateTime" value="${param.search_GTE_updateTime}"/>
	<input type="hidden" name="search_LTE_updateTime" value="${param.search_LTE_updateTime}"/>
	<input type="hidden" name="search_GTE_payment" value="${param.search_GTE_payment}"/>
	<input type="hidden" name="search_LTE_payment" value="${param.search_LTE_payment}"/>
	<input type="hidden" name="search_EQ_status" value="${param.search_EQ_status}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/taobaoTradeSoldGet/list" onsubmit="return validateCustomBase(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>交易编号：</label>
					<input type="text" name="search_EQ_tid" value="${param.search_EQ_tid}"/>			
				</li>
					<li>
					<label>商品金额：</label> 
					<input type="text" name="search_GTE_totalFee" class="input-medium validate[custom[number]]" value="${param.search_GTE_totalFee}" style="width: 80px"/>~
					<input type="text" name="search_LTE_totalFee" class="input-medium validate[custom[number]]"   value="${param.search_LTE_totalFee}" style="width: 80px" />
				</li>
				<li>
					<label>实付金额：</label> 
					<input type="text" name="search_GTE_payment" class="input-medium validate[custom[number]]" value="${param.search_GTE_payment}" style="width: 80px"/>~
					<input type="text" name="search_LTE_payment" class="input-medium validate[custom[number]]"   value="${param.search_LTE_payment}" style="width: 80px" />
				</li>
			
				
			</ul>
			<ul  class="searchContent">
				<li>
					<label >交易时间：</label> 
					<input type="text" name="search_GTE_created" class="input-medium date" readonly="readonly" value="${param.search_GTE_created}" style="width: 80px"/>~
					<input type="text" name="search_LTE_created" class="input-medium date"  readonly="readonly"  value="${param.search_LTE_created}" style="width: 80px" />
				</li>
				<li>
					<label>付款时间：</label> 
					<input type="text" name="search_GTE_payTime" class="input-medium date" readonly="readonly" value="${param.search_GTE_payTime}" style="width: 80px"/>~
					<input type="text" name="search_LTE_payTime" class="input-medium date"  readonly="readonly"  value="${param.search_LTE_payTime}" style="width: 80px" />
				</li>
				<li>
					<label>核对时间：</label> 
					<input type="text" name="search_GTE_updateTime" class="input-medium date" readonly="readonly" value="${param.search_GTE_updateTime}" style="width: 80px"/>~
					<input type="text" name="search_LTE_updateTime" class="input-medium date"  readonly="readonly"  value="${param.search_LTE_updateTime}" style="width: 80px" />
				</li>
			</ul>
			<ul class="searchContent">
				<li>
					<label>交易状态：</label> 
				 	<select name="search_EQ_status" class="input-medium select">
				 		<option value="">请选择</option>
					 		<c:if test="${not empty taobaoTradeStatusMap}">
								<c:forEach items="${taobaoTradeStatusMap}" var="mapItems">
							<option value="${mapItems.key}"<c:if test="${param.search_EQ_status eq mapItems.key }"> selected="selected"</c:if>>${mapItems.value}</option>
							</c:forEach>
							</c:if>
				 	</select>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="TradeManagement:view">
				<li><a iconClass="magnifier" target="dialog" max="true" mask="true" width="530" height="250" rel="TTrade_view" href="${contextPath }/management/taobaoTradeSoldGet/view/{slt_uid}"><span>查看订单详情</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="TradeManagement:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="TTrade_save" href="${contextPath }/management/taobaoTradeSoldGet/create"><span>添加订单</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="TradeManagement:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="TTrade_edit" href="${contextPath }/management/taobaoTradeSoldGet/update/{slt_uid}"><span>编辑订单</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="TradeManagement:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/taobaoTradeSoldGet/delete" title="确认要删除选定订单?"><span>删除订单</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="TradeManagement:export">
				<li><a iconClass="page_white_get" target="dwzExport" mask="true" href="${contextPath }/management/taobaoTradeSoldGet/excelExport" title="确定导出这些记录吗?"><span>数据导出</span></a>
			</shiro:hasPermission>
			<shiro:hasPermission name="TradeManagement:view">
				<li><a iconClass="accept" target="navTab"  mask="true" width="830" height="450" href="${contextPath }/management/tradEmailManager/list" title="差异数据接收人管理"><span>差异数据接收人管理</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="187" width="1600"> 
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>交易编号</th>
				<th>交易标题</th>
				<th>购买数量</th>
				<th>商品价格</th>
				<th title="商品价格乘以数量的总金额">商品金额</th>
				<th>实付金额</th>
				<th>收货人姓名</th>
				<th>卖家发货时间</th>
				<th>交易状态</th>
				<th>商品数字编号</th>
				<th>交易创建时间</th>
				<th>付款时间</th>
				<th>买家昵称</th>
				<th>买家下单的地区</th>
				<th>买家备注</th>
				<th>买家留言</th>
				<th>超时到期时间</th>
				<th>交易核对时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${Trades}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.tid}</td>
				<td>${item.title}</td>
				<td>${item.num}</td>
				<td>${item.price}</td>
				<td>${item.totalFee}</td>
				<td>${item.payment}</td>
				<td>${item.receiverName}</td> 
				<td>
					<c:if test="${not empty item.consignTime}">
					 <fmt:formatDate value="${item.consignTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>
					<c:if test="${ empty item.consignTime}">
						&nbsp;
					</c:if>
				</td>
				<td>
				<c:if test="${empty taobaoTradeStatusMap}">
					&nbsp;
				</c:if>
				<c:if test="${not empty taobaoTradeStatusMap}">
					<c:forEach items="${taobaoTradeStatusMap}" var="mapItems">
						<c:if test="${mapItems.key eq item.status}">${mapItems.value}</c:if>
					</c:forEach>
				</c:if>
				</td>
			
				<td>${item.numIid}</td>
				
				<td>
					
					<c:if test="${not empty item.created}">
					
					<fmt:formatDate value="${item.created}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>
					<c:if test="${ empty item.created}">
						&nbsp;
					</c:if>
				</td>
				<td>
					<c:if test="${ empty item.payTime}">
						&nbsp;
					</c:if>
					<c:if test="${not empty item.payTime}">
						<fmt:formatDate value="${item.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>
				</td>
				<td>${item.buyerNick}</td>
				<td>${item.buyerArea}</td>
				<td>${item.buyerMemo}</td>
				<td>${item.buyerMessage}</td>
				<td>
					<c:if test="${ empty item.timeoutActionTime}">
						&nbsp;
					</c:if>
					<c:if test="${not empty item.timeoutActionTime}">
						<fmt:formatDate value="${item.timeoutActionTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>
				</td>
				<td>${item.updateTime}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>