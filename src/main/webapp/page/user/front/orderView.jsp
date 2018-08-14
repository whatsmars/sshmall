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
	<style>
.highlight{   
    color : #fff;   
    background : green;   
}   
	</style>
  </head>
  
  <body>
    <jsp:include page="/page/share/Head.jsp"/>
    <hr><div style="font-size:16;">
   <center>[<b>如想修改订单，请致电我们的客服：222-88888888</b>]<input type="button" style="background:pink;" value=" 返 回 " onclick="history.back()"></center><br>
    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#333333">
  <tr>
    <td><table width="100%" height="25" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td width="59%"><strong>订单号:</strong>${order.orderId } <font color="red">(${order.state.name })</font></td>
        <td width="41%" align="right"><strong>订购时间:</strong>${order.createDate }</td>
      </tr>
    </table>
      <table width="100%" border="0" align="center" cellpadding="3" cellspacing="2">
        <tr>
          <td colspan="4" bgcolor="#FFFFFF"><strong>订购者信息 </strong> <a href="">修改</a></td>
          <td align="center" bgcolor="#FFFFFF">支付方式</td>
          <td colspan="2" bgcolor="#FFFFFF">${order.paymentWay.name }  <a href="">修改</a></td>
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
          <td colspan="4" bgcolor="#FFFFFF"><strong>收货人信息</strong> <a href="">修改</a></td>
          <td align="center" bgcolor="#FFFFFF">送货方式</td>
          <td colspan="2" bgcolor="#FFFFFF">${acceptor.deliverWay.name }  <a href="">修改</a></td>
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
          <td colspan="3" align="center" bgcolor="#FFFFFF">${item.productName } <font color="red">[${item.styleName }]</font></td>
          <td align="center" bgcolor="#FFFFFF">￥${item.productPrice }</td>
          <td align="center" bgcolor="#FFFFFF">${item.amount } <a href="">修改</a></td>
          <td align="center" bgcolor="#FFFFFF"><a href="">删除</a></td>
        </tr>
</c:forEach>
        <tr>
          <td colspan="7" align="right" bgcolor="#FFFFFF"><p>商品合计：￥${order.productTotalPrice }元&nbsp;&nbsp;配送费：￥${order.deliverFee }元 <a href="">修改</a>&nbsp;&nbsp;订单合计：￥${order.totalPrice }元<br />
            
			&nbsp;&nbsp;<strong>应付金额：</strong>￥${order.payableFee }元</p>          </td>
        </tr>
      </table></td>
  </tr>
</table>
    </div>
  </body>
</html>
