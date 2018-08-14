<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>修改订单的支付方式</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<script language="JavaScript">
$(function(){
  for(i=1;i<=4;i++) {
    if($("#paymentWay").val()==$("#p"+i).val()) $("#p"+i).attr("checked", "checked");
  }
})
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/order/updatePaymentWay" method="post">
  <input type="hidden" name="order.orderId" value="${param.orderId }"/>
  <input type="hidden" id="paymentWay" value="${param.paymentWay }"/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td > <font color="#FFFFFF">修改订单的支付方式：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio" id="p1" name="paymentWay" value="NET"/>网上支付</td>
    </tr>
    <c:if test="${param.deliverWay!='GENERALPOST' && param.deliverWay!='EMS' }">
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio" id="p2" name="paymentWay" value="COD"/>货到付款</td>
    </tr></c:if>
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio" id="p3" name="paymentWay" value="BANKREMITTANCE"/>银行电汇</td>
    </tr>
     <tr bgcolor="f5f5f5"> 
      <td><input type="radio" id="p4" name="paymentWay" value="POSTOFFICEREMITTANCE"/>邮局汇款</td>
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