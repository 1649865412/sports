<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }//management/setting/list" page="${page }">
	<input type="hidden" name="search_LIKE_brandName" value="${param.search_LIKE_brandName}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }//management/security/brand/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					品牌名称：<input type="text" name="search_LIKE_brandName" value="${param.search_LIKE_brandName}"/>			
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
			<shiro:hasPermission name="Brand:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="Brand_view" href="${contextPath }//management/security/brand/view/{slt_uid}"><span>查看</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="Brand:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="Brand_save" href="${contextPath }//management/security/brand/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Brand:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="Brand_edit" href="${contextPath }//management/security/brand/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Brand:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }//management/security/brand/delete" title="确认要删除选定品牌?"><span>删除</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				 
				<th>品牌名称</th>
			    <th>扩展系统</th>
			    <th>对应ID</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${brands}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				 
				<td>${item.brandName}</td>
			    <td>${item.extendType}</td>
			    <td>${item.extendId}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>