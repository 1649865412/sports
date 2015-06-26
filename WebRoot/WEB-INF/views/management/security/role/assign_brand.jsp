<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<style>
<!--
.treeTable input[type=text] {
	border: 0;
	background: transparent;
	width: 100%;
}
-->
</style>
<script type="text/javascript">
 
</script>

<div class="pageContent">
	<form>
		<input type="hidden" name="id" value="${role.id }">
		<div class="pageFormContent" layoutH="56">
			<dl>
				<dt>
					角色名称：
				</dt>
				<dd>
					<input type="text" name="name"
						class="input-medium validate[required,maxSize[64] required"
						maxlength="64" value="${role.name }" readonly="readonly" />
				</dd>
			</dl>
			<dl>
				<dt>
					描述：
				</dt>
				<dd>
					<input type="text" name="description"
						class="input-medium validate[maxSize[256]" maxlength="256"
						value="${role.description }" readonly="readonly" />
				</dd>
			</dl>
			<div class="divider"></div>
					<table class="table" layoutH="137" width="100%">
						<thead>
							<tr>
								<th width="22">
									<input type="checkbox" group="ids" class="checkboxCtrl">
								</th>
								
								<th>
									品牌名称
								</th>
		                         <th>扩展系统</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${brands}">
								<tr target="slt_uid" rel="${item.id}">
								   
									<td>
										<input name="ids" value="${item.id}" type="checkbox"  
											<c:forEach var="selfrow" items="${rowBrand}">
											  <c:if test="${selfrow == item.id}"> checked  </c:if> 
											</c:forEach> 
										  >
									</td>
									<td>
										${item.brandName}
									</td>
		                           <td>${item.extendType}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
		</div>

		<div class="formBar">
			<ul>
				<li>
					 
							 <a class="add"  target="selectedTodo" rel="ids" href="${contextPath }/management/security/role/assignBrandSave/${role.id}"  ><span>确定</span></a> 
						 
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