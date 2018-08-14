<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>修改产品样式</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<script language="JavaScript">
function checkfm(form){
	if ($("#styleName").val().trim()==""){
		alert("样式名称不能为空！");
		$("#styleName").focus();
		return false;
	}
	var image = $("#image").val();
	if(image.trim()!=""){
		var ext = image.substring(image.length-3).toLowerCase();
		if (ext!="jpg" && ext!="gif" && ext!="bmp" && ext!="png"){
			alert("只允许上传gif、jpg、bmp、png！");
			return false; 
		}
	}
	return true;
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/product/style/update" method="post" enctype="multipart/form-data" onsubmit="return checkfm(this)">
<input type="hidden" name="style.styleId" value="${param.styleId }"/>
<input type="hidden" name="product.productId" value="${param.productId }"/>
<input type="hidden" id="size" name="size" value="${param.size }"/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td colspan="2"  > <font color="#FFFFFF">修改产品图片：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">颜色：</div></td>
      <td width="78%"> <input type="text" id="styleName" name="style.name" value="${param.name }" size="50" maxlength="40"/>
        <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">产品图片：</div></td>
      <td width="78%"> <input type="file" id="image" name="fileCtx.file" size="50"><br>
      将图片以宽度等比例压缩，请输入压缩后的宽度（默认160px）：<input type="text" size="4" name="fileCtx.width" value="160" /><br>
      原样式图片:<br><img src="${param.imagePath }" width="100">
      </td>
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