<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>部门显示</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<script type="text/javascript" src="/js/jquery.js"></script>
	<script language="JavaScript">
<!--
	//到指定的分页页面
	function toPage(page){
		$("#currentPage").val(page);
		document.forms[0].submit();
	}
</script>
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<form action="/control/product/list" method="post">
  <input id="currentPage" type="hidden" name="pageCtx.currentPage">
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="4" bgcolor="6f8ac4" align="right">
    	<%@ include file="/page/share/fenye.jsp" %>
   </td></tr>
    <tr>
      <td width="30%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">代号</font></div></td>
      <td width="8%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">名称</font></div></td>
      <td width="10%" bgcolor="6f8ac4"></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<c:forEach items="${departments}" var="entry">
    <tr>
      <td bgcolor="f5f5f5"> <div align="center">${entry.departmentId}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
      <yc:permission module="department" privilege="update">
      <a href="/page/department/updateDepartment.jsp?departmentId=${entry.departmentId}&name=${entry.name }">
	  <img src="/images/edit.gif" width="15" height="16" border="0"></a>
	  </yc:permission>
	  </div></td>
      <td bgcolor="f5f5f5"> <div align="center">${entry.name}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
         <yc:permission module="department" privilege="delete">
         <a href="#" onclick="if(confirm('确定删除吗？')) location.href='/control/department/delete?departmentId=${entry.departmentId}'">删除</a>
         </yc:permission>
      </div></td>
	</tr>
</c:forEach>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="4" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
              <td width="85%">
              <yc:permission module="department" privilege="add">
              <input type="button" class="frm_btn" onClick="javascript:window.location.href='/page/department/addDepartment.jsp'" value="添加部门"> &nbsp;&nbsp;
              </yc:permission>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</form>
</body>
</html>