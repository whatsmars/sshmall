<%@ page pageEncoding="UTF-8"%>
<%@include file="/page/share/taglib.jsp" %>

<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript">
<!--
$(function(){
  if('${type.typeId}'=='') {
    $("#index").css("background", "pink");
  }else{
    $("#t${type.typeId}").css("background", "pink");
    $("#index").css("background", "");
  }
})

function EnterPress(e){ //传入 event 
  var e = e || window.event; 
  if(e.keyCode == 13){ 
    document.forms[0].submit(); 
  } 
} 
//-->
</script>
<div id="Head">
  <div id="HeadTop">
    <div id="Logo"><a href="/" target=_top><img alt=中国最大、最安全的图书网上交易平台！ src="/images/global/yc.jpg" border=0 /></a> </div>
    <div id="HeadNavBar">
      <ul>
        <li class="NoSep"><a id="MyBuyCar"  href="/shopping/cart/list" ><font color="blue"><Strong>购物车</Strong></font></a> </li>
        <li><a href="/page/user/front/register.jsp?toUI=${param.toUI }" >新用户注册</a> </li>
        <li><a href="/page/user/front/login.jsp">用户登录</a> </li>
         <c:if test="${!empty sessionScope.user}"> <li><a href="/front/user/logout" >退出</a> </li></c:if>
        <li><a href="/page/shopping/myOrder.jsp">我的订单</a>服务热线：222-88888888 </li>
      </ul>
    </div>
  </div>
  <div id="ChannelMenu">
	<UL id="ChannelMenuItems">
		<LI id="MenuHome"><a href="/index.jsp"><span id="index">首页</span></a></LI>
		<LI id="ProducType1Home"><a href="/front/product/list?type.typeId=1"><span id="t1">${application.type1 }</span></a></LI>
		<LI id="ProducType1Home"><a href="/front/product/list?type.typeId=2"><span id="t2">${application.type2 }</span></a></LI>
		<LI id="ProducType3Home"><a href="/front/product/list?type.typeId=3"><span id="t3">${application.type3 }</span></a></LI>
		<LI id="ProducType8Home"><a href="/front/product/list?type.typeId=4"><span id="t4">${application.type4 }</span></a></LI>
	</UL>
<!--  SearchBox -->
<div id="SearchBox">
	  <div id="SearchBoxTop">
		  <div id="SearchForm">
			<form action="/front/product/search" method="post" name="search" id="search">
			 <span class="name">商品搜索: </span><input id="word" name="word" value="${word }" onkeypress="EnterPress(event)" onkeydown="EnterPress()" accesskey="s" size="100" maxlength="100"/>
			 <input type="submit" value="搜 索" id="DoSearch"/>
			</form>
		  </div>
	  </div>
      <div id="HotKeywords">
			<ul>
				<li><span>您好<c:if test="${!empty sessionScope.user}"><b>${sessionScope.user.name }</b></c:if>，欢迎来到有才网！</span></li>
			</ul>
      </div>
   </div>
</div><!-- End SearchBox -->
</div>
<!-- Head End -->