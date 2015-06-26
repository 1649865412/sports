<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/productInfo/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${productInfo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>产品名称：</label>
		<input type="text" name="productName" maxlength="100" value="${productInfo.productName}" class="input-medium" />
	</p>
	<p>
		<label>69码：</label>
		<input type="text" name="productUpccode" maxlength="100" value="${productInfo.productUpccode}" class="input-medium readonly validate[required] required readonly" readonly="readonly"/>
	</p>
	<p>
		<label>平台ID：</label>
		<input type="text" name="productPlatformId" maxlength="100" value="${productInfo.productPlatformId}" class="input-medium"/>
	</p>
	<p>
		<label>款号：</label>
		<input type="text" name="materialNumber" maxlength="100" value="${productInfo.materialNumber}" class="input-medium validate[required] required readonly"  readonly="readonly"/>
	</p>
	<p>
		<label>Inline/2ndline：</label>
		<input type="text" name="inlineOr2ndline" maxlength="30" value="${productInfo.inlineOr2ndline}" class="input-medium"/>
	</p>
	<p>
		<label>LF/PF(系列)：</label>
		<input type="text" name="productLfPf" maxlength="100" value="${productInfo.productLfPf}" class="input-medium"/>
	</p>
	<p>
		<label>系统编号：</label>
		<input type="text" name="systemId" maxlength="100" value="${productInfo.systemId}" class="input-medium"/>
	</p>
	<p>
		<label>季节：</label>
		<input type="text" name="quater" maxlength="30" value="${productInfo.quater}" class="input-medium"/>
	</p>
	<p>
		<label>重量：</label>
		<input type="text" name="productWeight" maxlength="30" value="${productInfo.productWeight}" class="input-medium"/>
	</p>
	<p>
		<label>系列：</label>
		<input type="text" name="series" maxlength="50" value="${productInfo.series}" class="input-medium"/>
	</p>
	<p>
		<label>吊牌价：</label>
		<input type="text" name="tagPrice" maxlength="11" value="${productInfo.tagPrice}" class="input-medium validate[required] validate[custom[number] required"/>
	</p>
	<p>
		<label>材质：</label>
		<input type="text" name="materialQuality" maxlength="30" value="${productInfo.materialQuality}" class="input-medium"/>
	</p>
	<p>
		<label>商品定位：</label>
		<input type="text" name="productPosition" maxlength="30" value="${productInfo.productPosition}" class="input-medium"/>
	</p>
	<p>
		<label>功能：</label>
		<input type="text" name="productFunction" maxlength="30" value="${productInfo.productFunction}" class="input-medium"/>
	</p>
	<p>
		<label>上市月份：</label>
		<input type="text" name="onMarketMonth" maxlength="30" value="${productInfo.onMarketMonth}" class="readonly input-medium date" readonly="readonly" datefmt="yyyy-MM" style="width: 142px;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>
	<p>
		<label>上架日期：</label>
		<input type="text" name="onSalesTime" maxlength="100" value="${productInfo.onSalesTime}" class="input-medium date readonly" readonly="readonly" style="width: 142px;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>
	<p>
		<label>品牌：</label>
		<input type="text" name="productBrand" maxlength="30" value="${productInfo.productBrand}" class="input-medium"/>
	</p>
	<p>
		<label>产地：</label>
		<input type="text" name="productPlace" maxlength="100" value="${productInfo.productPlace}" class="input-medium"/>
	</p>
	<p>
		<label>颜色：</label>
		<input type="text" name="productColor" maxlength="30" value="${productInfo.productColor}" class="input-medium"/>
	</p>
	<p>
		<label>尺码：</label>
		<input type="text" name="productSize" maxlength="30" value="${productInfo.productSize}" class="input-medium"/>
	</p>
	<p>
		<label>类别：</label>
		<input type="text" name="productType" maxlength="30" value="${productInfo.productType}" class="input-medium"/>
	</p>
	<p>
		<label>性别：</label>
			<select name="productSex" class="select input-medium">
			<option value="男" <c:if test="${productInfo.productSex eq '男'}">selected="selected"</c:if>>男</option>
			<option value="女" <c:if test="${productInfo.productSex eq '女'}">selected="selected"</c:if>>女</option>
			<option value="中性" <c:if test="${productInfo.productSex eq '中性'}">selected="selected"</c:if>>中性</option>
		</select>
	</p>
	<p>
		<label>属性：</label>
		<input type="text" name="productAttribute" maxlength="30" value="${productInfo.productAttribute}" class="input-medium"/>
	</p>
	<p>
		<label>鞋底：</label>
		<input type="text" name="shoesBottom" maxlength="30" value="${productInfo.shoesBottom}" class="input-medium"/>
	</p>
	<p>
		<label>色号：</label>
		<input type="text" name="colorId" maxlength="30" value="${productInfo.colorId}" class="input-medium"/>
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remark" maxlength="30" value="${productInfo.remark}" class="input-medium"/>
	</p>
	<p>
		<label>图片：</label>
		<!-- <input type="file" name="uploadImage"  class="input-medium"/>&nbsp;支持png格式 -->
		<c:if test="${not empty productInfo.image}">
			<c:set var="tmpImagePath" value="${contextPath}${productInfo.image}"/>
		</c:if>
			<img alt="图片不存在" src="${tmpImagePath}" style="width: 160px;height: 55px; ${empty tmpImagePath ? 'display:none':''}" id="show_upload_image" />

		<a target="dialog" mask="true"  href="${contextPath }/management/productInfo/uploadImage" ><img  src="${contextPath}/styles/dwz/themes/css/images/icons/arrow_up.png" />上传图片</a>
		<input type="hidden" name="image" id="image" value="${productInfo.image}"/>
	</p>

	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>