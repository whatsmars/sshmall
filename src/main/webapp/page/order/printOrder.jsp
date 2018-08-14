<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>订单查看</title>
<script src="/js/FoshanRen.js"></script>
<style type="text/css">
<!--
body {font-size: 12px;line-height:16px}
a:link { color: #3300FF; 
     text-decoration: underline;  }    
    
a {color: #3300FF; 
     text-decoration: underline; }
     
a:hover { color: #FF6600; 
           text-decoration: underline; }

A.subnav:link {
	FONT-SIZE: 12px; COLOR: #330000; LINE-HEIGHT: 155%; TEXT-DECORATION: none
}
A.subnav:visited {
	FONT-SIZE: 12px; COLOR: #330000; LINE-HEIGHT: 155%; TEXT-DECORATION: none
}
A.subnav:active {
	FONT-SIZE: 12px; COLOR: #330000; LINE-HEIGHT: 155%; TEXT-DECORATION: none
}
A.subnav:hover {
	FONT-SIZE: 12px; COLOR: #330000; LINE-HEIGHT: 155%; TEXT-DECORATION: underline
}
-->
</style>
</head>
<body onload="javascript:window.print()">
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#333333">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td colspan="2" align="center"><h1 style="margin:15px auto">当当网发货单</h1></td>
      </tr>
      <tr>
        <td width="59%"><strong>订单号:</strong>${order.orderId } <font color="red">(${order.state.name })</font></td>
        <td width="41%" align="right"><strong>订购时间:</strong>${order.createDate }</td>
      </tr>
    </table>
      <table width="100%" border="0" align="center" cellpadding="3" cellspacing="2">
        <tr>
          <td colspan="4" bgcolor="#FFFFFF"><strong>订购者信息 </strong></td>
          <td align="center" bgcolor="#FFFFFF">支付方式</td>
          <td colspan="2" bgcolor="#FFFFFF">${order.paymentWay.name }</td>
        </tr>
        <tr>
          <td width="13%" align="center" bgcolor="#FFFFFF">姓名</td>
          <td width="24%" bgcolor="#FFFFFF">${buyer.buyerName }（${buyer.gender.name }）</td>
          <td width="12%" align="center" bgcolor="#FFFFFF">联系电话</td>
          <td width="18%" bgcolor="#FFFFFF">${buyer.phone }</td>
          <td width="12%" align="center" bgcolor="#FFFFFF">联系手机</td>
          <td colspan="2" bgcolor="#FFFFFF">${buyer.mobile }</td>
        </tr>
        <tr>
          <td align="center" bgcolor="#FFFFFF">地址</td>
          <td colspan="3" bgcolor="#FFFFFF">${buyer.address }</td>
          <td align="center" bgcolor="#FFFFFF">邮编</td>
          <td colspan="2" bgcolor="#FFFFFF">${buyer.postalcode }</td>
        </tr>
        <tr>
          <td align="center" bgcolor="#FFFFFF">其他要求</td>
          <td colspan="6" bgcolor="#FFFFFF">${order.note}</td>
        </tr>
        <tr>
          <td colspan="4" bgcolor="#FFFFFF"><strong>收货人信息</strong></td>
          <td align="center" bgcolor="#FFFFFF">送货方式</td>
          <td colspan="2" bgcolor="#FFFFFF">${acceptor.deliverWay.name }</td>
        </tr>
        <tr>
          <td align="center" bgcolor="#FFFFFF">姓名</td>
          <td bgcolor="#FFFFFF">${acceptor.acceptorName }</td>
          <td align="center" bgcolor="#FFFFFF">联系电话</td>
          <td bgcolor="#FFFFFF">${acceptor.phone }</td>
          <td align="center" bgcolor="#FFFFFF">联系手机</td>
          <td colspan="2" bgcolor="#FFFFFF">${acceptor.mobile }</td>
        </tr>
        <tr>
          <td align="center" bgcolor="#FFFFFF">地址</td>
          <td colspan="3" bgcolor="#FFFFFF">${acceptor.address }</td>
          <td align="center" bgcolor="#FFFFFF">邮编</td>
          <td colspan="2" bgcolor="#FFFFFF">${acceptor.postalcode }</td>
        </tr>
        <tr>
          <td align="center" bgcolor="#FFFFFF">时间要求</td>
          <td colspan="6" bgcolor="#FFFFFF">${acceptor.requirement }</td>
        </tr>
        <tr>
          <td colspan="4" bgcolor="#FFFFFF"><strong>订购的商品</strong></td>
          <td align="center" bgcolor="#FFFFFF"></td>
          <td colspan="2" bgcolor="#FFFFFF">
		  </td>
        </tr>
        <tr>
          <td align="center" bgcolor="#FFFFFF">商品编号</td>
          <td colspan="3" align="center" bgcolor="#FFFFFF">商品名称</td>
          <td align="center" bgcolor="#FFFFFF">单价</td>
          <td width="16%" align="center" bgcolor="#FFFFFF">数量</td>
          <td width="5%" align="center" bgcolor="#FFFFFF">&nbsp;</td>
        </tr>
<c:forEach items="${order.items}" var="item">
        <tr>
          <td align="center" bgcolor="#FFFFFF">${item.productId }</td>
          <td colspan="3" align="center" bgcolor="#FFFFFF">${item.productName }</td>
          <td align="center" bgcolor="#FFFFFF">￥${item.productPrice }</td>
          <td align="center" bgcolor="#FFFFFF">${item.amount } </td>
          <td align="center" bgcolor="#FFFFFF"></td>
        </tr>
</c:forEach>
        <tr>
          <td colspan="7" align="right" bgcolor="#FFFFFF"><p>商品合计：￥${order.productTotalPrice }元&nbsp;&nbsp;配送费：￥${order.deliverFee }元 &nbsp;&nbsp;订单合计：￥${order.totalPrice }元<br />
            
			&nbsp;&nbsp;<strong>应付金额：</strong>￥${order.payableFee }元</p>          </td>
        </tr>
      </table></td>
  </tr>
</table>

<p>&nbsp;</p>
</body>
</html>