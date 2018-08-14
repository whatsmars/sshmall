<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>员工查询</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/employee/list" method="post">
<input type="hidden" name="query" value="true">
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td colspan="2"  > <font color="#FFFFFF">查询条件：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">用户名：</div></td>
      <td width="78%"> <input type="text" name="employee.name" size="30" maxlength="20"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">姓名：</div></td>
      <td width="78%"> <input type="text" name="employee.realname" size="15" maxlength="10"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">部门：</div></td>
      <td width="78%"> <select name="departmentId" id="departSelect">
      	<option value="">请选择部门</option>
      	<c:forEach var="depart" items="${departments}">
          <option value="${depart.departmentId}">${depart.name}</option>
          </c:forEach>
        </select></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">是否在职：</div></td>
      <td width="78%"> <input type="radio" name="visible" value="true"/>是
      <input type="radio" name="visible" value="false"/>否</td>
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