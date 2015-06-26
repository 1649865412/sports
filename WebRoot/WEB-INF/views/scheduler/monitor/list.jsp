<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/scheduler/monitor/list"
	page="${page }">
	<input type="hidden" name="search_LIKE_id"
		value="${param.search_LIKE_triggerName}" />
</dwz:paginationForm>
<script type="text/javascript">
	function doCmd(state, triggerName, group, triggerState) {
	
		if (state == 'pause' && triggerState == 'PAUSED') {
			alertMsg.error("该Trigger己经暂停！");
			return;
		}
	
		if (state == 'resume' && triggerState != 'PAUSED') { 
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
				$.ajax( {
					url : "${contextPath}/scheduler/monitor/jobstatus",
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
<form method="post" action="${contextPath }/scheduler/monitor/list"
	onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent"> 
				<li>
					<label style="width: 100px;">
						Trigger 名称：
					</label>
					<input type="text" name="triggerName" class="date"
						readonly="readonly" style="width: 80px"
						value="${param.search_LIKE_triggerName}"/>	
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
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="Scheduler_view" href="${contextPath }/scheduler/monitor/view/{slt_uid}"><span>查看</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="Scheduler:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="Scheduler_save" href="${contextPath }/scheduler/monitor/create"><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Scheduler:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="Scheduler_edit" href="${contextPath }/scheduler/monitor/update/{slt_uid}"><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Scheduler:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/scheduler/monitor/delete" title="确认要删除选定Scheduler?"><span>删除</span></a></li>
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
					Trigger 名称
				</th>
				<th>
					Trigger 分组
				</th>
				<th>
					下次执行时间
				</th>
				<th>
					上次执行时间
				</th>
				<th>
					优先级
				</th>
				<th>
					Trigger 状态
				</th>
				 
				<th>
					开始时间
				</th>
				<th>
					结束时间
				</th>
				<th>
					动作命令
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${qrtzTriggerss}">
				<tr target="slt_uid" rel="${item.id}">
					<td>
						<input name="ids" value="${item.id}" type="checkbox">
					</td>
					<td>
						${item.triggerName}
					</td>
					<td>
						${item.triggerGroup}
					</td>
					<td>
                 ${item.nextFireTime}
						 
					</td>
					<td>
					  ${item.prevFireTime}
						 
					</td>
					<td>
						${item.priority}
					</td>
					<td>
						${item.statu}
					</td>
				 
					<td>
						${item.startTime}
					</td>
					<td>
						${item.endTime}
					</td>

					<td>
					   <a style="color:blue;" href="javascript:doCmd('pause','${item.triggerName}','${item.triggerGroup}','${item.statu}')">
					   	暂停
					   </a>
					   <a style="color:blue;" href="javascript:doCmd('resume','${item.triggerName}','${item.triggerGroup}','${item.statu}')">
					   	恢复
					   </a>
					   <a style="color:blue;" href="javascript:doCmd('remove','${item.triggerName}','${item.triggerGroup}','${item.statu}')">
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