<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>汇尚电子商务商品管理系统</title>
		<link rel="icon" href="${contextPath }/public/favicon.ico" mce_href="${contextPath }/public/favicon.ico" type="image/x-icon" >
		<link rel="shortcut icon" href="${contextPath }/public/favicon.ico"/>
		<link href="${contextPath}/styles/dwz/themes/default/style.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/dwz/themes/css/core.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/dwz/themes/css/icons.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/dwz/themes/css/print.css"
			rel="stylesheet" type="text/css" media="print" />
		<link
			href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/ztree/css/zTreeStyle.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/uploadify/css/uploadify.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link
			href="${contextPath}/styles/treeTable/themes/default/treeTable.css"
			rel="stylesheet" type="text/css" />
		<link href="${contextPath}/styles/keta/css/keta.css" rel="stylesheet"
			type="text/css" />
		<!--[if IE]>
<link href="${contextPath}/styles/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
		<!--[if lte IE 9]>
<script src="${contextPath}/styles/dwz/js/speedup.js" type="text/javascript"></script>
<![endif]-->

		<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js"
			type="text/javascript">
</script>
		<script src="${contextPath}/styles/dwz/js/jquery.bgiframe.js"
			type="text/javascript">
</script>

		<script src="${contextPath}/styles/treeTable/jquery.treeTable.min.js"
			type="text/javascript">
</script>
		<%-- form验证 --%>
		<script
			src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js"
			type="text/javascript" charset="utf-8">
</script>
		<script
			src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js"
			type="text/javascript" charset="utf-8">
</script>
		<script src="${contextPath}/styles/keta/js/json2.js"
			type="text/javascript" charset="utf-8">
</script>



		<%--
<script src="${contextPath}/styles/dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.ui.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.theme.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.tab.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.resize.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.stable.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.database.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.effects.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.panel.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.history.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.combox.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.print.js" type="text/javascript"></script>
--%>

		<script src="${contextPath}/styles/dwz/js/dwz.min.js"
			type="text/javascript">
</script>
		<script src="${contextPath}/styles/dwz/js/dwz.switchEnv.js"
			type="text/javascript">
</script>
		<script src="${contextPath}/styles/dwz/js/dwz.regional.zh.js"
			type="text/javascript">
</script>
		<%-- 自定义JS --%>
		<script src="${contextPath}/styles/dwz/js/customer.js"
			type="text/javascript">
</script>
		<script src="${contextPath}/styles/keta/js/keta.js"
			type="text/javascript">
</script>
		<%-- upload --%>
		<script
			src="${contextPath}/styles/uploadify/scripts/jquery.uploadify.min.js"
			type="text/javascript">
</script>
		<%-- zTree --%>
		<script
			src="${contextPath}/styles/ztree/js/jquery.ztree.all-3.5.min.js"
			type="text/javascript">
</script>

		<script type="text/javascript">
$(function() {
	DWZ.init("${contextPath}/styles/dwz/dwz.frag.xml", {
		loginUrl : "${contextPath}/login/timeout",
		loginTitle : "登录", // 弹出登录对话框
		debug : false, // 调试模式 【true|false】
		callback : function() {
			initEnv();
			$("#themeList").theme( {
				themeBase : "${contextPath}/styles/dwz/themes"
			});
		}
	});
});

function changeBrand(obj) {
	var changObj = $.trim($(obj).val());
	$.ajax( {
		url : '${contextPath }/management/index/changBrand?brandId='
				+ changObj + "&rand=" + (new Date().getTime()),
		type : 'post',
		dataType : 'json',
		success : function(data, textStatus) {
			alertMsg.correct('切换成功 ！');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});

}
</script>
	</head>
	<body scroll="no">
		<div id="layout">
			<div id="header">
				<div class="headerNav">
					<a class="logo" href="${contextPath}/management/index">Logo</a>
					<ul class="nav">
						<li>
							<a href="${contextPath}/management/index">主页</a>
						</li>
						<li>
							<a href="${contextPath}/management/index/updateBase"
								target="dialog" mask="true" width="550" height="250">修改用户信息</a>
						</li>
						<li>
							<a href="${contextPath}/management/index/updatePwd"
								target="dialog" mask="true" width="500" height="200">修改密码</a>
						</li>
						<li>
							<a href="${contextPath}/logout">退出</a>
						</li>
					</ul>
					<br />
					<p
						style="margin-top: 45px; margin-right: 20px; font-size: 14px; float: right">
						 
						 
							<span>品牌：</span>
							<c:if test="${oneBrand != null}">
								 ${oneBrand}
							</c:if> 
							
							<c:if test="${oneBrand == null and not empty brands }">
							<select style="width: 120px" onchange="changeBrand(this)">
								<%--<c:if test="${login_user.admin}">
									<option value="">
										请选择
									</option>
								</c:if>
								--%>
								<option value="">
										请选择
									</option>
									<c:forEach items="${brands}" var="item">
									 <option value="${item.id}"
											<c:if test="${not empty curr_brandId && curr_brandId eq item.id}">selected="selected" </c:if>>
											${item.brandName}
									  </option> 
								</c:forEach>
							</select>
							</c:if>
					 
						您好 ${login_user.username }，欢迎进入商品管理系统！
					</p>
					<%-- 
			<ul class="themeList" id="themeList">
				<li theme="default"><div class="selected">blue</div></li>
				<li theme="green"><div>green</div></li>
				<li theme="purple"><div>purple</div></li>
				<li theme="silver"><div>silver</div></li>
				<li theme="azure"><div>天蓝</div></li>
			</ul>
			--%>
				</div>

				<div id="navMenu">
					<ul>
						<c:forEach var="level1" items="${menuModule.children }"
							varStatus="menuStatus">
							<c:if
								test="${level1.id!=10&&level1.id!=12&&level1.id!=15&&level1.id!=27}">
								<li
									<c:if test="${menuStatus.index == 0}">class="selected"</c:if>>
									<a
										href="${contextPath}/management/index/retrievalSubMenu/${level1.id }"><span>${level1.name
											}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div id="leftside">
				<div id="sidebar_s">
					<div class="collapse">
						<div class="toggleCollapse">
							<div></div>
						</div>
					</div>
				</div>
				<div id="sidebar">
					<div class="toggleCollapse">
						<h2>
							&nbsp;
						</h2>
						<div>
							collapse
						</div>
					</div>
					<div class="accordion" fillSpace="sideBar">
						<c:if test="${firstMenuModule != null}">
							<div class="accordionContent">
								<ul class="tree treeFolder expand">
									<c:forEach var="level2" items="${firstMenuModule.children}">
										<c:if test="${level2.id!=5}">
											<li>
												<dwz:menuAccordion child="${level2 }"
													urlPrefix="${contextPath }" />
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div id="container">
				<div id="navTab" class="tabsPage">
					<div class="tabsPageHeader">
						<div class="tabsPageHeaderContent">
							<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
							<ul class="navTab-tab">
								<li tabid="main" class="main">
									<a href="javascript:void(0)"><span><span
											class="home_icon">主页</span>
									</span>
									</a>
								</li>
							</ul>
						</div>
						<div class="tabsLeft">
							left
						</div>
						<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
						<div class="tabsRight">
							right
						</div>
						<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
						<div class="tabsMore">
							more
						</div>
					</div>
					<ul class="tabsMoreList">
						<li>
							<a href="javascript:void(0)">主页</a>
						</li>
					</ul>
					<div class="navTab-panel tabsPageContent layoutBox">
						<div class="page unitBox" id="index_page">
							<div class="accountInfo" >
							 
								<div class="right">
									<p>
										<fmt:formatDate value="<%=new Date() %>"
											pattern="yyyy-MM-dd EEEE" />
									</p>
								</div>
								<p>
									<span>系统概要汇总 </span>
								</p>
							</div>
							
							
							
							<div class="pageFormContent" layouth="50">
							
							
							<span>
								 <fieldset  style="width:400px;float:left;margin-right:12px">
								 	<legend style="color:red">今日信息</legend>
								 	<c:forEach items="${brands}" var="item">
								 		<p><strong>${item.brandName}</strong></p>
								 		<c:forEach items="${DayOrderArriver}" var="dayOrderArriver">
								 			<c:if test="${item.id == dayOrderArriver.brandId}">
								 				<p><a  target="navTab"  href="${contextPath}/management/orderManager/list">订货数量：
								 				${dayOrderArriver.orderNum}
								 				 金额：
								 				 <c:if test="${dayOrderArriver.orderPrice!=null}">
						<fmt:formatNumber value="${dayOrderArriver.orderPrice}" pattern="###,###.##"  minFractionDigits="2"/>
							</c:if></p></a>
								 				<!-- <p><a  target="navTab"  href="${contextPath}/management/orderManager/list">到货数量：
								 				${dayOrderArriver.arriverNum} 
								 				金额：
								 					<c:if test="${dayOrderArriver.arriverPrice!=null}">
						<fmt:formatNumber value="${dayOrderArriver.arriverPrice}" pattern="###,###.##"  minFractionDigits="2"/>
							</c:if></p></a> -->
								 			</c:if>
								 		</c:forEach>
								 		<c:forEach items="${DaySellAndStock}" var="daySellAndStock">
								 			<c:if test="${item.id == daySellAndStock.brandId}">
								 				<!--<p><a  target="navTab"  href="${contextPath}/management/salesStockDetails/list">销售数量：${daySellAndStock.saleNum} 
								 				金额：
								 				<c:if test="${daySellAndStock.saleAmount!=null}">
						<fmt:formatNumber value="${daySellAndStock.saleAmount}" pattern="###,###.##"  minFractionDigits="2"/>
							</c:if></p></a>-->
								 				<p><a  target="navTab"  href="${contextPath}/management/stockAnalysis/list">库存数量：${daySellAndStock.stockNumber} 
								 				金额：
								 				<c:if test="${daySellAndStock.stockAmount!=null}">
						<fmt:formatNumber value="${daySellAndStock.stockAmount}" pattern="###,###.##"  minFractionDigits="2"/>
							</c:if></p></a>
								 			</c:if>
								 		</c:forEach>
								 		<br/>
								 	</c:forEach>
								 	
								 </fieldset>
							
								 <fieldset style="width:550px">
								 	<legend style="color:red">监控信息</legend>
								 	<c:forEach items="${brands}" var="item">
								 		<p><strong>${item.brandName}</strong></p>
								 		<c:forEach items="${arriveTip}" var="at">
								 			<c:if test="${item.id == at.brandId}">
								 				<p><a   target="navTab"   href="${contextPath}/management/orderManager/list">有${at.arriveNum==null?0:at.arriveNum} 个产品将到货</a></p> 
								 			</c:if>
								 		</c:forEach>
								 		<c:forEach items="${securtySaleNums}" var="ssn">
								 			<c:if test="${item.id == ssn.brandId}">
								 				<p>有${ssn.securitySaleNums==null?0:ssn.securitySaleNums} 个产品安全库存低于预设值</p> 
								 			</c:if>
								 		</c:forEach>
								 		<c:forEach items="${stockTurnoverNums}" var="stn">
								 			<c:if test="${item.id == stn.brandId}">
								 				<p>有${stn.turnoverNum==null?0:stn.turnoverNu} 个产品库存周转低于预设值</p> 
								 			</c:if>
								 		</c:forEach>
								 	</c:forEach>	
								 </fieldset>
								 </span>
								 
								 
								 
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="footer">
			Copyright&copy; 版权所有 汇尚电子商务有限公司
		</div>
	</body>
</html>