<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>添加类别</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<script language="JavaScript">
function checkfm(){
	if ($("#typeName").val().trim()==""){
		alert("类别名称不能为空！");
		$("#typeName").focus();
	}
	/*
	var reg = /^(\w|[\u4E00-\u9FA5])*$/; 
	if(!$("#typeName").val().match(reg)) { 
		alert("用户名只允许为英文，数字和汉字及下划线的混合,\n请检查是否前后有空格或者其他符号"); 
		$("#typeName").focus(); 
	} */
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/product/type/add" method="post">
<input type="hidden" name="parentId" value="${param.parentId }"/>
<input type="hidden" name="parentName" value="${param.parentName }"/>
<br>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td colspan="2"> <font color="#FFFFFF">添加类别：</font></td></tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">类别名称：</div></td>
      <td width="78%"> <input type="text" id="typeName" name="type.name" onblur="checkfm()" size="50" maxlength="50"/>
        <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">备注(100字以内)：</div></td>
      <td width="78%"> <input type="text" id="typeNote" name="type.note" size="80" maxlength="100"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="SYS_SET" value=" 确 定 " class="frm_btn">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>