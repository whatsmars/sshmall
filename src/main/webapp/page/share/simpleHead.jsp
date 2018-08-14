<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>
<div id="Head">
  <div id="HeadTop">
    <div id="Logo"><a href="/" target=_top><img alt="中国最大、最安全的户外用品、体育用品网上交易平台！" src="/images/global/yc.jpg" border=0 /></a> </div>
    <div id="HeadNavBar">
      <ul>
        <li class="NoSep"><a id="MyBuyCar"  href="/shopping/cart/list" ><font color="blue"><Strong>购物车</Strong></font></a> </li>
        <li><a href="/page/user/front/register.jsp?toUI=${param.toUI }" >新用户注册</a> </li>
        <li><a href="/page/user/front/login.jsp">用户登录</a> </li>
        <c:if test="${!empty sessionScope.user}"> <li><a href="/front/user/logout" >退出</a> </li></c:if>
        <li><a href="/page/shopping/myOrder.jsp">我的订单</a> </li>
        <li class="phone">服务热线：222-88888888</li>
      </ul>
    </div>
  </div>
</div>
<!-- Head End -->