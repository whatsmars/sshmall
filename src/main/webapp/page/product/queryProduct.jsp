<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>查询产品</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<script type="text/javascript">
function change(index) {
  var brandId = $("#brandsSelect").val();
  $("#brandId").val(brandId);
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/product/list" method="post">
<input type="hidden" id="typeId" name="type.typeId"/>
<input type="hidden" id="brandId" name="brand.brandId"/>
<input type="hidden" name="query" value="true">
  <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"> 
      <td colspan="2" ><font color="#FFFFFF">查询产品：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品名称  ：</div></td>
      <td width="75%"> <input type="text" name="product.name" size="50" maxlength="40"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品类别：</div></td>
      <td width="75%"> <input type="text" id="typeName" name="type.name" value="" disabled size="30"/><font color="#FF0000">*</font> 
        <input type="button" name="select" value="选择..." onClick="window.open('/control/product/selectType?type.typeId=')">
      </td>                                                                                                <!-- ?type.typeId=  防止空指针 -->
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">底(采购)价(元) ：</div></td>
      <td width="75%"> 
在<input type="text" name="minBasePrice" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>
      到 <input type="text" name="maxBasePrice" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>之间
</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">销售价(元) ：</div></td>
      <td width="75%"> 在<input type="text" name="minSalePrice" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>
      到 <input type="text" name="maxSalePrice" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>之间
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">货号 ：</div></td>
      <td width="75%"> <input type="text" name="product.code" size="20" maxlength="30"/>(注:供货商提供的便于产品查找的编号)</td>
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
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="edit" value=" 确 认 " class="frm_btn">
          &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>