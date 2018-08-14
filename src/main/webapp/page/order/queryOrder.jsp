<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>查询订单</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<script language="JavaScript">
function checkfm(form){
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/order/list" method="post" onsubmit="return checkfm(this)">
<input type="hidden" name="query" value="true"/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td colspan="2"  > <font color="#FFFFFF">查询订单：</font></td>
    </tr>
     <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">订单号：</div></td>
      <td width="78%"> <input type="text" name="order.orderId" size="20" maxlength="16"/></td>
    </tr>
   <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">订单状态：</div></td>
      <td width="78%"><select name="order.state">
        <option value="">=====全部状态=====</option>
      	<option value="CANCEL">已取消</option>
      	<option value="WAITCONFIRM">待审核</option>
      	<option value="WAITPAYMENT">等待付款</option>
      	<option value="ADMEASUREPRODUCT">正在配货</option>
      	<option value="WAITDELIVER">等待发货</option>
      	<option value="DELIVERED">已发货</option>
      	<option value="RECEIVED">已收货</option>
      </select></td>
    </tr> 
   <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">用户名：</div></td>
      <td width="78%"> <input type="text" name="order.user.name" size="20" maxlength="20"/></td>
    </tr> 
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">收货人名称：</div></td>
      <td width="78%"> <input type="text" name="acceptor.acceptorName" size="20" maxlength="30"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">购买者姓名：</div></td>
      <td width="78%"> <input type="text" name="buyer.buyerName" size="20" maxlength="10"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="SYS_SET" value=" 查 询 " class="frm_btn">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>