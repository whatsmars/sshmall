<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>修改产品</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<script language="javascript" type="text/javascript">
function Formfield(id, label){
	this.id=id;
	this.label=label;
}
function verifyForm(objForm){
	var list  = new Array(new Formfield("name", "产品名称"),new Formfield("typeName", "产品类型"),
	new Formfield("basePrice", "产品底价"),new Formfield("marketPrice", "产品市场价"),
	new Formfield("salePrice", "产品销售价"));
	for(var i=0;i<list.length;i++){
		if($("#"+list[i].id).val().trim()==""){
			alert(list[i].label+ "不能为空");
			if(objfield.type!="hidden" && objfield.focus()) objfield.focus();
			return false;
		}
	}
    return true;
}
function confirmSubmit(objForm){
	if (verifyForm(objForm)) objForm.submit();
} 

function change(index) {
  var brandId = $("#brandsSelect").val();
  $("#brandId").val(brandId);
}

$(function(){
  var brandId = $("#brandId").val();
  $("#brandsSelect").val(brandId);
});
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/product/update" enctype="multipart/form-data" method="post">
  <input type="hidden" id="typeId" name="type.typeId" value="${product.type.typeId }"/>
  <input type="hidden" id="brandId" name="brand.brandId" value="${param.brandId }"/>
  <input type="hidden" name="product.productId" value="${product.productId }"/>

  <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"> 
      <td colspan="2" ><font color="#FFFFFF">修改产品：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品名称  ：</div></td>
      <td width="75%"> <input type="text" id="name" name="product.name" value="${product.name }" size="50" maxlength="40"/><font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品类别：</div></td>
      <td width="75%"> <input type="text" id="typeName" name="type.name" value="${product.type.name }" disabled size="30"/><font color="#FF0000">*</font> 
        <input type="button" name="select" value="选择..." onClick="window.open('/control/product/selectType?type.typeId=')">
        (<a href="/control/product/type/list" target="_blank">添加产品类别</a>)
      </td>                                                                                                <!-- ?type.typeId=  防止空指针 -->
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">底(采购)价 ：</div></td>
      <td width="75%"> <input type="text" id="basePrice" name="product.basePrice" value="${product.basePrice }" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">市场价 ：</div></td>
      <td width="75%"> <input type="text" id="marketPrice" name="product.marketPrice" value="${product.marketPrice }" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">销售价 ：</div></td>
      <td width="75%"> <input type="text" id="salePrice" name="product.salePrice" value="${product.salePrice }" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">货号 ：</div></td>
      <td width="75%"> <input type="text" id="code" name="product.code" value="${product.code }" size="20" maxlength="30"/>(注:供货商提供的便于产品查找的编号)</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">品牌 ：</div></td>
      <td width="75%"> 
        <select name="brands" style="width:130px" id="brandsSelect" onchange="change(this.selectedIndex);"> 
          <option value="0" selected>--请选择--</option>
          <c:forEach var="brand" items="${brands}">
          <option value="${brand.brandId}">${brand.name}</option>
          </c:forEach>
        </select> 
      (<a href="/control/product/brand/list" target="_blank">添加品牌</a>)</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">购买说明 ：</div></td>
      <td width="75%"> <input type="text" id="buyExplain" name="product.buyExplain" value="${product.buyExplain }" size="35" maxlength="30" /></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%" valign="top"> <div align="right">产品简介<font color="#FF0000">*</font> ：</div></td>
      <td width="75%">
      <!-- 
      <textarea id="description" name="product.description" cols="80" rows="12">${param.description }</textarea>
       -->
      <FCK:editor instanceName="product.description" basePath="/fckeditor" value="${product.description }" width="70%" height="280px;"></FCK:editor>
      </td>
	</tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="button" name="edit" value=" 确 认 " class="frm_btn" onClick="javascript:confirmSubmit(this.form)">
          &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>