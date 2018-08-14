<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>欢迎来到有才网</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="有才网,天涯明月刀,转生术">
	<meta http-equiv="description" content="真的爱你">
	<link href="/css/global/header01.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/js/jquery.js"></script>
	<script>
$(function(){
  setInterval(updateTime, 1000);
})

 function updateTime(){
   document.getElementById('now').innerHTML = new Date().toLocaleString();
 }
	</script>
  </head>
  
  <body>
    <jsp:include page="/page/share/Head.jsp"/>
    <hr>
    <div style="font-size:16;">
   <span id="now" style="color:red;font-weight:bold;"></span>
    <br><br>
    <b>如想修改订单，请致电我们的客服：222-88888888</b><br>
    </div>
  </body>
</html>
