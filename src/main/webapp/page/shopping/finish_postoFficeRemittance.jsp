<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>有才网-订单完成</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<LINK href="/css/new_cart.css" rel="stylesheet" type="text/css">
<link href="/css/global/header01.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>

  </head>
  
  <body onload='$("#index").css("background", "");'>
    <jsp:include page="/page/share/Head.jsp"/>
<BR>
<h1>订单号:${order.orderId },应付金额:${order.payableFee }元</h1>
<br>
你选择的付款方式为"邮局汇款",在汇款单上务必写上您的订单号.如下是汇款信息:<br>
收款人:沈红喜
<br>地址:北京市朝阳区左家庄
<br>去<a href="/page/shopping/myOrder.jsp">我的账户</a>查看订单明细<br/>
<jsp:include page="/page/share/Foot.jsp" />
  </body>
</html>
