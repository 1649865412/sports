<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm
	action="${contextPath }/management/stockmanager/stock_log/list"
	page="${page }">
	<input type="hidden" name="search_LIKE_id"
		value="${param.search_LIKE_id}" />
</dwz:paginationForm>

<form method="post"
	action="${contextPath }/management/stockmanager/stock_log/list"
	onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>
						69码：
					</label>
					<input type="text" name="search_LIKE_upccode"
						value="${param.search_LIKE_upccode}" />
				</li>
				<li>
					<label>
						款号：
					</label>
					<input type="text" name="search_LIKE_materialNumber"
						value="${param.search_LIKE_materialNumber}" />
				</li>
				<li>
					<label>
						库类型：
					</label>
					<select name="search_LIKE_optType">
					   <option value="">--请选择--</option>
					   <c:forEach items="${stockType}" var="st">
					   		<option  value="${st.key }"  <c:if test="${param.search_LIKE_optType == st.key}">selected="selected"</c:if> >${st.value }</option>
					   </c:forEach> 
					</select> 
				</li>
				
			</ul>
			<ul class="searchContent">
				<li>
					<label>
						品牌：
					</label>
					<select name="search_EQ_brandId">
					   <option value="">--请选择--</option>
					   <c:forEach items="${brands}" var="brand">
					   		<option  value="${brand.id }"  <c:if test="${param.search_EQ_brandId == brand.id}">selected="selected"</c:if> >${brand.brandName }</option>
					   </c:forEach> 
					</select> 
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
			 
		</ul>
	</div>

	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th width="22">
					<input type="checkbox" group="ids" class="checkboxCtrl">
				</th>
				<th>
					69码
				</th>
				<th>
					款号
				</th>
				<th>
					数量
				</th>
				<th>
					操作类型
				</th>
				<th>
					更新人
				</th>
				<th>
					更新时间
				</th>
				<th>
					发生时间
				</th>
				<th>
					品牌
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${stockLogs}">
				<tr target="slt_uid" rel="${item.id}">
					<td>
						<input name="ids" value="${item.id}" type="checkbox">
					</td>
					<td>
						${item.upccode}
					</td>
					<td>
						${item.materialNumber}
					</td>
					<td>
					${item.num}	
					</td>
					<td>
					<c:if test="${item.optType==1}">
					  订货
					</c:if>
					<c:if test="${item.optType==2}">
					 到货
					</c:if>
					</td>
					<td>
						${item.updatedUser}
					</td>
					<td>
						<fmt:formatDate value="${item.updatedTime}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<fmt:formatDate value="${item.optTime}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<c:forEach var="allBrand"  items="${allBrandList}">
					   <c:if test="${item.brandId==allBrand.id}">
						${allBrand.brandName}
						</c:if>
					  </c:forEach>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>