<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<span id="includeDayTime"> <span id="begintimetype"><input
			type="text" name="firstTime" class="date"
			value="${shopAnalyseTimeCheckEntity.firstTime}" readonly="readonly"
			style="width: 70px" /> </span> 至 <span id="endtimetype"><input
			type="text" name="endTime" class="date"
			value="${shopAnalyseTimeCheckEntity.endTime}" readonly="readonly"
			style="width: 70px" /> </span> </span>

<span id="includeYear"> <select class='fsfbrtl-select'
		name='year' id='year'>
		<option value='2013'
			<c:if test="${shopAnalyseTimeCheckEntity.year=='2013'}">selected="selected"</c:if>>
			2013
		</option>
		<option value='2014'
			<c:if test="${shopAnalyseTimeCheckEntity.year=='2014'}">selected="selected"</c:if>>
			2014
		</option>
		<option value='2015'
			<c:if test="${shopAnalyseTimeCheckEntity.year=='2015'}">selected="selected"</c:if>>
			2015
		</option>
		<option value='2016'
			<c:if test="${shopAnalyseTimeCheckEntity.year=='2016'}">selected="selected"</c:if>>
			2016
		</option>
	</select>年&nbsp;&nbsp;&nbsp; </span>
<span id="includeMonth"> <select class='fsfbrtl-select'
		name='MonthBegin' id='month'>
		<c:forEach var="numList" items="${numList}" varStatus="status">
			<option value="${status.index+1}"
				${shopAnalyseTimeCheckEntity.monthBegin==
				status.index+1 ? 'selected="selected"':'' }>
				${status.index+1}
			</option>
		</c:forEach>
	</select>至 <select class='fsfbrtl-select' name='MonthEnd' id='month'>
		<c:forEach var="numList" items="${numList}" varStatus="status">
			<option value="${status.index+1}"
				${shopAnalyseTimeCheckEntity.monthEnd==
				status.index+1 ? 'selected="selected"':'' }>
				${status.index+1}
			</option>
		</c:forEach>
	</select> 月 </span>

<span id="includeQuarter"> <select class='fsfbrtl-select'
		name='QuarterfirstTime' id='month'>
		<c:forEach var="numList" items="${numList}" varStatus="status">
			<c:if test="${status.index+1<=4}">
				<option value="${status.index+1}"
					${shopAnalyseTimeCheckEntity.quarterfirstTime==
					status.index+1 ? 'selected="selected"':'' }>
					${status.index+1}
				</option>
			</c:if>
		</c:forEach>
	</select> 至 <select class='fsfbrtl-select' name='QuarterendTime' id='month'>
		<c:forEach var="numList" items="${numList}" varStatus="status">
			<c:if test="${status.index+1<=4}">
				<option value="${status.index+1}"
					${shopAnalyseTimeCheckEntity.quarterendTime==
					status.index+1 ? 'selected="selected"':'' }>
					${status.index+1}
				</option>
			</c:if>
		</c:forEach>
	</select>季 </span>
</span>
