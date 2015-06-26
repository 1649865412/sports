<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<span id="include"> <span id="suitIncludeDayTime"> <span
		id="begintimetype"><input type="text" name="firstTime"
				class="date" value="${suitTime.firstTime}" readonly="readonly"
				style="width: 70px" /> </span> 至 <span id="endtimetype"><input
				type="text" name="endTime" class="date" value="${suitTime.endTime}"
				readonly="readonly" style="width: 70px" /> </span> </span> <span
				id="suitIncludeYear"> <select class='fsfbrtl-select' name='year'
			id='year'>
			<option value='2013'
				<c:if test="${suitTime.year=='2013'}">selected="selected"</c:if>>
				2013
			</option>
			<option value='2014'
				<c:if test="${suitTime.year=='2014'}">selected="selected"</c:if>>
				2014
			</option>
			<option value='2015'
				<c:if test="${suitTime.year=='2015'}">selected="selected"</c:if>>
				2015
			</option>
			<option value='2016'
				<c:if test="${suitTime.year=='2016'}">selected="selected"</c:if>>
				2016
			</option>
		</select>年&nbsp;&nbsp;&nbsp; </span> <span id="suitIncludeMonth"> <select
			class='fsfbrtl-select' name='monthBegin' id='month'>
			<c:forEach var="numList" items="${numList}" varStatus="status">
				<option value="${status.index+1}" ${suitTime.monthBegin==
					status.index+1 ? 'selected="selected"':'' }>
					${status.index+1}
				</option>
			</c:forEach>
		</select>至 <select class='fsfbrtl-select' name='monthEnd' id='month'>
			<c:forEach var="numList" items="${numList}" varStatus="status">
				<option value="${status.index+1}" ${suitTime.monthEnd==
					status.index+1 ? 'selected="selected"':'' }>
					${status.index+1}
				</option>
			</c:forEach>
		</select> 月 </span> <span id="suitIncludeQuarter"> <select
			class='fsfbrtl-select' name='quarterfirstTime' id='month'>
			<c:forEach var="numList" items="${numList}" varStatus="status">
				<c:if test="${status.index+1<=4}">
					<option value="${status.index+1}" ${suitTime.quarterfirstTime==
						status.index+1 ? 'selected="selected"':'' }>
						${status.index+1}
					</option>
				</c:if>
			</c:forEach>
		</select> 至 <select class='fsfbrtl-select' name='quarterendTime' id='month'>
			<c:forEach var="numList" items="${numList}" varStatus="status">
				<c:if test="${status.index+1<=4}">
					<option value="${status.index+1}" ${suitTime.quarterendTime==
						status.index+1 ? 'selected="selected"':'' }>
						${status.index+1}
					</option>
				</c:if>
			</c:forEach>
		</select>季 </span> </span>
