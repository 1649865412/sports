<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/scheduler/job/list"
	page="${page }">
	<input type="hidden" name="search_LIKE_jobGroup"
		value="${param.search_LIKE_jobGroup}" />
		<input type="hidden" name="search_LIKE_jobName"
		value="${param.search_LIKE_jobName}" />
</dwz:paginationForm>
<script type="text/javascript">
	function doCmd(state, triggerName, group, triggerState,ids) {
	
		if (state == 'pause' && triggerState == '0') {
			alertMsg.error("该Trigger己经暂停！");
			return;
		}
	
		if (state == 'resume' && triggerState != '0') { 
			alertMsg.error("该Trigger正在运行中！");
			return;
		}
	
		//客户端两次编码，服务端再解码，否测中文乱码 
		//triggerName = encodeURIComponent(encodeURIComponent(triggerName));
		//group = encodeURIComponent(encodeURIComponent(group));
		var param = {};
		param.jobtype = 200;
		param.action = state;
		param.triggerName = triggerName;
		param.group = group;
		param.id = ids;
				$.ajax( {
					url : "${contextPath}/scheduler/job/jobstatus",
					type : 'post',
					 dataType: 'json',
					 data:param,
					// timeout: 3000,
					error : function() {
						alertMsg.error("执行失败！"); 
					},
					success : function(ret) {
						if (ret == 0) { 
							alertMsg.correct("执行成功！"); 
							navTabSearch(this);
						} else {
							alertMsg.error("执行失败！"); 
						}
					}
				});
	}
</script>
<form method="post" action="${contextPath }/scheduler/job/list"
	onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent"> 
				<li>
					<label style="width: 100px;">
						job 名称：
					</label>
					<input type="text" name="jobName" class="date"
						readonly="readonly" style="width: 80px"
						value="${param.search_LIKE_jobName}"/>	
				</li>  
				<li>
					<label style="width: 100px;">
						job Group：
					</label>
					<input type="text" name="jobGroup" class="date"
						readonly="readonly" style="width: 80px"
						value="${param.search_LIKE_jobGroup}"/>	
				</li>  
			</ul>

			<div class="subBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="submit">
									搜索
								</button>
							</div>
						</div>
					</li>
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
			<shiro:hasPermission name="Scheduler:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="320" rel="Scheduler_view" href="${contextPath }/scheduler/job/view/{slt_uid}"><span>查看</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="Scheduler:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="320" rel="Scheduler_save" href="${contextPath }/scheduler/job/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Scheduler:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="320" rel="Scheduler_edit" href="${contextPath }/scheduler/job/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Scheduler:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/scheduler/job/delete" title="确认要删除选定Scheduler?"><span>删除</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>

	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22">
					<input type="checkbox" group="ids" class="checkboxCtrl">
				</th>
				<th>
					Job 名称
				</th>
				<th>
					Job 分组
				</th>
				<th>
					Job 状态
				</th>
				<th>
					触发类
				</th>
				<th>
					表达式
				</th>
				<th>
					描述
				</th> 
				<th>
					动作命令
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${qrtzJobs}">
				<tr target="slt_uid" rel="${item.id}">
					<td>
						<input name="ids" value="${item.id}" type="checkbox">
					</td>
					<td>
						${item.jobName}
					</td>
					<td>
						${item.jobGroup}
					</td>
					<td> 
						 <c:if test="${item.jobStatus == 0}">
			禁用
		</c:if>
		<c:if test="${item.jobStatus == 1}">
			启用
		</c:if>
		<c:if test="${item.jobStatus == 2}">
			删除
		</c:if>
					</td>
					<td>
					  ${item.jobClass}
						 
					</td>
					<td>
						${item.cronExpression}
					</td>
					<td>
						${item.remark} 
					</td> 
					<td>
					   <a style="color:blue;" href="javascript:doCmd('pause','${item.jobName}','${item.jobGroup}','${item.jobStatus}','${item.id}')">
					   	暂停
					   </a>
					   <a style="color:blue;" href="javascript:doCmd('resume','${item.jobName}','${item.jobGroup}','${item.jobStatus}','${item.id}')">
					   	恢复
					   </a>
					   <a style="color:blue;" href="javascript:doCmd('remove','${item.jobName}','${item.jobGroup}','${item.jobStatus}','${item.id}')">
					   	删除
					   </a> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页-->
	<dwz:pagination page="${page }" /> 
</div>