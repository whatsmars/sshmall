<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>添加产品</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<script>
function Formfield(id, label){
	this.id=id;
	this.label=label;
}
function verifyForm(){
	var list  = new Array(new Formfield("name", "产品名称"),
	new Formfield("typeName", "产品类别"),
	new Formfield("basePrice", "产品底价"),new Formfield("marketPrice", "产品市场价"),
	new Formfield("salePrice", "产品销售价"),//new Formfield("description", "产品简介"),
	new Formfield("styleName", "产品图片的样式"),new Formfield("productImagePath", "产品图片"));
	for(var i=0;i<list.length;i++){
	    var fieldobj = $("#"+list[i].id);
		if(trim(fieldobj.val())==""){
			alert(list[i].label+ "不能为空");
			if(fieldobj.type!="hidden" && fieldobj.focus()) fieldobj.focus();
			return false;
		}
	}
	var imagefile = $("#productImagePath").val();
	var ext = imagefile.substring(imagefile.length-3).toLowerCase();
	if (ext!="jpg" && ext!="gif" && ext!="bmp" && ext!="png"){
		alert("只允许上传gif、jpg、bmp、png！");
		return false; 
	}
    return true;
}

$(function(){
  $("#confirm").click(function(){
    if (verifyForm()){
      document.forms[0].submit();
    } 
  });
});

function change(index) {
  var brandId = $("#brandsSelect").val();
  $("#brandId").val(brandId);
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form id="fm" action="/control/product/add" enctype="multipart/form-data" method="post">
  <input type="hidden" id="typeId" name="type.typeId"/>
  <input type="hidden" id="brandId" name="brand.brandId"/>
  <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"> 
      <td colspan="2" ><font color="#FFFFFF">添加产品：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品名称  ：</div></td>
      <td width="75%"> <input type="text" id="name" name="product.name" size="50" maxlength="40"/><font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品类别：</div></td>
      <td width="75%"> <input type="text" id="typeName" name="type.name" disabled size="30"/><font color="#FF0000">*</font> 
        <input type="button" name="select" value="选择..." onClick="winOpen('/control/product/selectType?type.typeId=', 'select type', 200, 400)">
        (<a href="/control/product/type/list" target="_blank">添加产品类别</a>)
      </td>                                                                                                <!-- ?type.typeId=  防止空指针 -->
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">底(采购)价 ：</div></td>
      <td width="75%"> <input type="text" id="basePrice" name="product.basePrice" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">市场价 ：</div></td>
      <td width="75%"> <input type="text" id="marketPrice" name="product.marketPrice" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">销售价 ：</div></td>
      <td width="75%"> <input type="text" id="salePrice" name="product.salePrice" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">货号 ：</div></td>
      <td width="75%"> <input type="text" id="code" name="product.code" size="20" maxlength="30"/>(注:供货商提供的便于产品查找的编号)</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right"><font color="#FF0000">*</font>产品图片 ：</div></td>
      <td width="75%"> 颜色：<input type="text" id="styleName" name="style.name" size="10">
      图片<input type="file" id="productImagePath" name="fileCtx.file" size="30">
      压缩图片大小（默认160px）<input type="text" size="4" name="fileCtx.width" value="160" /></td>
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
      <td width="75%"> <input type="text" id="buyExplain" name="product.buyExplain" size="35" maxlength="30" /></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%" valign="top"> <div align="right">产品简介<font color="#FF0000">*</font> ：</div></td>
      <td width="75%"><!-- 
      <textarea id="description" name="product.description" cols="80" rows="12"></textarea>
       -->
      <FCK:editor instanceName="product.description" basePath="/fckeditor" value="" width="70%" height="280px;"></FCK:editor>
      </td>
	</tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="button" id="confirm" name="Add" value=" 确 认 " class="frm_btn">
          &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>