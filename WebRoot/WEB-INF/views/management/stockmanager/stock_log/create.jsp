<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<form method="post" action="${contextPath }/scheduler/monitor/create"
		class="required-validate pageForm"
		onsubmit="return validateCallback(this, dialogReloadNavTab);">
		<div class="pageFormContent" layoutH="58">
			<p>
				<label>
					Trigger名称：
				</label>
				<input type="text" name="p_triggerName" maxlength="20"
					class="input-medium" />
			</p>
			<p>
				<label>
					Trigger分组：
				</label>
				<select name="p_triggerGroup">
					<option value="DEFAULF">
						defaulf
					</option>
					<option value="行政组">
						行政组
					</option>
					<option value="财务组">
						财务组
					</option>
					<option value="开发组">
						开发组
					</option>
				</select>

			</p>
			<p>
				<label>
					开始时间：
				</label>
				<input type="text" id="p_startTime" name="p_startTime" size="20">
			</p>
			<p>
				<label>
					结束时间：
				</label>
				<input type="text" id="p_endTime" name="p_endTime" size="20">
			</p>
			<p>
				<label>
					执行次数：
				</label>
				<input type="text" name="p_repeatCount" size="20">
				次（表示Trigger启动后执行多少次结束，不填写执行一次）
			</p>
			<p>
				<label>
					执行间隔：
				</label>
				<input type="text" name="p_repeatCount" size="20">
				秒（表示Trigger间隔多长时间执行一次，不填写前后两次执行没有时间间隔，直到任务结束）
			</p>

		</div>

		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="submit">
								确定
							</button>
						</div>
					</div>
				</li>
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
	</form>
</div>