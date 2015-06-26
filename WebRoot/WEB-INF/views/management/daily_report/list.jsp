<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/dailyReport/list" page="${page }" name="dailyReportInfoFormName">
	<input type="hidden" name="search_GTE_fileCreateTime" value="${param.search_GTE_fileCreateTime}"/>
	<input type="hidden" name="search_LTE_fileCreateTime" value="${param.search_LTE_fileCreateTime}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/dailyReport/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>时间：</label>
					<input type="text" name="search_GTE_fileCreateTime"  value="${param.search_GTE_fileCreateTime}" class="input-medium date" readonly="readonly" style="width: 80px"/>
					~<input type="text" name="search_LTE_fileCreateTime"   value="${param.search_LTE_fileCreateTime}" class="input-medium date" readonly="readonly" style="width: 80px"/>
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
<form id="downLoadForm" name="downLoadForm" action="${contextPath }/management/dailyReport/downLoad">
	<!-- <input type="hidden" name="id" id="downLoadId"/> -->
</form> 
<div class="pageContent">
	
	
	<div class="panelBar">
		<ul class="toolBar">
			<!-- 
			<shiro:hasPermission name="DailyReportInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="DailyReportInfo_view" href="${contextPath }/management/dailyReport/view/{slt_uid}"><span>查看DailyReportInfo</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="DailyReportInfo:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="DailyReportInfo_save" href="${contextPath }/management/dailyReport/create"><span>添加DailyReportInfo</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="DailyReportInfo:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="DailyReportInfo_edit" href="${contextPath }/management/dailyReport/update/{slt_uid}"><span>编辑DailyReportInfo</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="DailyReportInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/dailyReport/delete" title="确认要删除选定DailyReportInfo?"><span>删除DailyReportInfo</span></a></li>
			</shiro:hasPermission>
			 -->
			 <shiro:hasPermission name="DailyReportInfo:view">
				<li><a iconClass="accept" target="navTab"  mask="true" width="830" height="450" href="${contextPath }/management/emailManager/list" title="汇总日报接收人管理"><span>汇总日报接收人管理</span></a></li>
			</shiro:hasPermission>
			 <shiro:hasPermission name="DailyReportInfo:view">
				<li><a iconClass="user_go"  mask="true" width="830" height="450" href="javascript:;" title="预览" onclick="preview()"><span>预览</span></a></li>
				</shiro:hasPermission>
			</ul>
			
		</div>
		 
	 
	<table class="table" layoutH="137" width="100%" align="center"> 
		<thead>
			<tr>
				 <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th> 
				<th>时间</th>
				<th>操作</th>
				<th>数据更新时间</th>
			</tr>
		</thead>
		<tbody>
		<!-- "${contextPath }/management/dailyReport/downloadFile/{slt_uid}" -->
			<c:forEach var="item" items="${dailyReportInfos}">
			 	<tr > 
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td><a href="javascript:" onclick='downloadFile(${item.id})'>${item.fileCreateTime}</a></td>
				<td><a href="javascript:" onclick='downloadFile(${item.id})'>下载文件</a></td>
				<td>${item.updateTime}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>
<script type="text/javascript" defer="defer">
	function downloadFile(id)
	{
		$("#downLoadId").attr({"value":id});
		$("#downLoadForm").attr({"action":"${contextPath }/management/dailyReport/downLoad/"+id});
		$("#downLoadForm").submit();
	}
	//${contextPath }/management/dailyReport/preview/{slt_uid}
	function preview()
	{
		var idsObject = $('input[name="ids"]:checked');
		if(idsObject.size() <= 0)
		{
			alertMsg.error("请选择需要预览的文件！");
			return ;
		}
		
		var ids = [];
		$(idsObject).each(function() {
			ids.push($(this).val());
		});
			
		$.pdialog.open("${contextPath }/management/dailyReport/preview/"+ids[0], "preview_day_report", "预览",{width:800,height:750,max:true,mask:true,resizable:true,drawable:true,fresh:true});
	}
</script>