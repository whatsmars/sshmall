<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>修改订单的配送方式</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<script language="JavaScript">
$(function(){
  for(i=1;i<=4;i++) {
    if($("#deliverWay").val()==$("#d"+i).val()) $("#d"+i).attr("checked", "checked");
  }
})
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/order/updateDeliverWay" method="post">
  <input type="hidden" name="order.orderId" value="${param.orderId }"/>
  <input type="hidden" id="deliverWay" value="${param.deliverWay }"/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td > <font color="#FFFFFF">修改订单的配送方式：</font></td>
    </tr>
 <c:if test="${param.paymentWay!='COD'}">
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio" id="d1" name="deliverWay" value="GENERALPOST"/>平邮</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio" id="d2" name="deliverWay" value="EMS"/>国内特快专递EMS</td>
    </tr>
 </c:if>
     <tr bgcolor="f5f5f5"> 
      <td><input type="radio" id="d3" name="deliverWay" value="EXPRESSDELIVERY"/>快递送货上门</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio" id="d4" name="deliverWay" value="EXIGENCEEXPRESSDELIVERY"/>加急快递送货上门</td>
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