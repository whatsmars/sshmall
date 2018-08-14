<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>产品类别管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" href="/css/vip.css" type="text/css">
	<script type="text/javascript" src="/js/jquery.js"></script>
	
	<script language="JavaScript">
<!--
	//到指定的分页页面
	function toPage(page){
		$("#currentPage").val(page);
		document.forms[0].submit();
	}
//-->
</script>
  </head>
  
  <body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
    <form id="typeList" action="/control/product/type/list" method="post">
    <input id="currentPage" type="hidden" name="pageCtx.currentPage">
    
    <!-- 确保翻页时也执行查询 -->
    <input type="hidden" name="type.name" value="${type.name }">
    <input type="hidden" name="query" value="${query }">
    
    <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="6"  bgcolor="6f8ac4" align="right">
     <%@ include file="/page/share/fenye.jsp" %>
     </td></tr>
    <tr>
      <td width="8%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">代号</font></div></td>
      <td width="5%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td width="20%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">产品类别名称</font></div></td>
	  <td width="10%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">创建下级类别</font></div></td>
	  <td width="15%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">所属父类</font></div></td>
	  <td nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">备注</font></div></td>
    </tr>
    <c:forEach items="${types}" var="type">
    <tr>
      <td bgcolor="f5f5f5"> <div align="center">${type.typeId }</div></td>
      <td bgcolor="f5f5f5"> <div align="center"><a href="/page/product/updateProductType.jsp?typeId=${type.typeId }&name=${type.name }&note=${type.note }&currentPage=${pageCtx.currentPage }">
	  <img src="/images/edit.gif" width="15" height="16" border="0"></a></div></td>
      <td bgcolor="f5f5f5"> <div align="center"><a href='/control/product/type/list?parentId=${type.typeId }'>${type.name }</a>
      <c:if test="${fn:length(type.childTypes)>0 }"><font color="red">（有${fn:length(type.childTypes) }个子类）</font></c:if></div></td>
	  <td bgcolor="f5f5f5"> <div align="center"><a href="/page/product/addProductType.jsp?parentId=${type.typeId }&parentName=${type.name }">创建子类别</a></div></td>
	  <td bgcolor="f5f5f5" align="center">${type.parent.name }</td>
	  <td bgcolor="f5f5f5">${type.note }</td>
	</tr>
	</c:forEach>
	<tr> 
      <td bgcolor="f5f5f5" colspan="6" align="center">
        <table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
            <td width="85%">
              <input name="AddDic" type="button" class="frm_btn" id="AddDic" onClick="location.href='/page/product/addProductType.jsp?parentId=${param.parentId }'" value="添加类别"> &nbsp;&nbsp;
			  <input name="query" type="button" class="frm_btn" id="query" onClick="location.href='/page/product/queryProductType.jsp'" value=" 查 询 "> &nbsp;&nbsp;
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </form>
  </body>
</html>
