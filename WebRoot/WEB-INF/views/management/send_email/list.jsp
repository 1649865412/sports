<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/emailManager/list" page="${page }">
	<input type="hidden" name="search_LIKE_emailAddress" value="${param.search_LIKE_emailAddress}"/>
	<input type="hidden" name="search_LIKE_emailUserName" value="${param.search_LIKE_emailUserName}"/>
	<input type="hidden" name="search_EQ_emailSend" value="${param.search_EQ_emailSend}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/emailManager/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>邮箱地址：</label>
					<input type="text" name="search_LIKE_emailAddress" value="${param.search_LIKE_emailAddress}" alt="支持模糊查询"/>			
				</li>
				<li>
				<label>用户名：</label>
					<input type="text" name="search_LIKE_emailUserName" value="${param.search_LIKE_emailUserName}" alt="支持模糊查询"/>			
				</li>
				<li>
				<label>状态： </label>
					<select name="search_EQ_emailSend" class="input-medium">
					<option value="" selected="selected">请选择</option>
					<option value="1" <c:if test="${not empty  param.search_EQ_emailSend && param.search_EQ_emailSend eq 1 }"> selected="selected"</c:if>>发送</option>
					<option value="0" <c:if test="${not empty  param.search_EQ_emailSend && param.search_EQ_emailSend eq 0 }"> selected="selected"</c:if>>不发送</option>
					</select>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="DailyReportInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="830" height="450" rel="SendEmailInfo_view" href="${contextPath }/management/emailManager/view/{slt_uid}"><span>查看接收人信息</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="DailyReportInfo:view">
				<li><a class="add" target="dialog" mask="true" width="830" height="450" rel="SendEmailInfo_save" href="${contextPath }/management/emailManager/create"><span>添加接收人信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="DailyReportInfo:view">
				<li><a class="edit" target="dialog" mask="true" width="830" height="450" rel="SendEmailInfo_edit" href="${contextPath }/management/emailManager/update/{slt_uid}"><span>编辑接收人信息</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="DailyReportInfo:view">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/emailManager/delete" title="确认要删除选定接收人信息?"><span>删除接收人信息</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>邮箱地址</th>
				<th>用户名称</th>
				<th>联系方式</th>
				<th>状态</th>
				<th>备注</th>
				<th>数据更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${sendEmailInfos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.emailAddress}</td>
				<td>${item.emailUserName}</td>
				<td>${sendEmailInfo.phoneNumber}</td>
				<td>${item.emailSend eq 1 ? '发送':'不发送'}</td>
				<td>${item.remarks}</td>
				<td>${item.updateTime}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>