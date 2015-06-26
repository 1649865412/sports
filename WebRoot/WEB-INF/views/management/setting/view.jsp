<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>品牌ID：</label>
		<span class="unit">${generalSetting.brandId}</span>
	</p>
	<p>
		<label>品牌名称：</label>
		<span class="unit">${generalSetting.brandName}</span>
	</p>
	<p>
		<label>到货提醒：</label>
		<span class="unit">${generalSetting.arrivalDay}</span>
	</p>
	<p>
		<label>安全销售天数：</label>
		<span class="unit">${generalSetting.secritySell}</span>
	</p>
	<p>
		<label>库存周转天数：</label>
		<span class="unit">${generalSetting.turnoverDay}</span>
	</p>
	<p>
		<label>更新时间：</label>
		<span class="unit">${generalSetting.updatedTime}</span>
	</p>
	<p>
		<label>更新人：</label>
		<span class="unit">${generalSetting.userName}</span>
	</p>
	<p>
		<label>用户角色：</label>
		<span class="unit">${generalSetting.roleId}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>