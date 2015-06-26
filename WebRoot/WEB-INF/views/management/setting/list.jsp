<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }//management/setting/list" page="${page }">
	<input type="hidden" name="search_LIKE_brandName" value="${param.search_LIKE_brandName}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }//management/setting/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
			<label >品牌查询:</label>
				<li>
					<input type="text" name="search_LIKE_brandName" value="${param.search_LIKE_brandName}"/>			
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="reset">
									重置
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="GeneralSetting:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="GeneralSetting_view" href="${contextPath }//management/setting/view/{slt_uid}"><span>查看</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="GeneralSetting:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="GeneralSetting_save" href="${contextPath }//management/setting/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="GeneralSetting:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="GeneralSetting_edit" href="${contextPath }//management/setting/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="GeneralSetting:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }//management/setting/delete" title="确认要删除选定GeneralSetting?"><span>删除</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>品牌名称</th>
				<th>到货提醒</th>
				<th>安全销售天数</th>
				<th>库存周转天数</th>
				<th>更新时间</th>
				<th>更新人</th>
				<th>用户角色</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${generalSettings}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.brandName}</td>
				<td>${item.arrivalDay}</td>
				<td>${item.secritySell}</td>
				<td>${item.turnoverDay}</td>
				<td><fmt:formatDate value="${item.updatedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${item.userName}</td>
				<td>${item.roleName}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>