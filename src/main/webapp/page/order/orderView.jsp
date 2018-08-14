<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单查看</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
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
.frm_btn
{
    BORDER-TOP-WIDTH: 1px;
    BORDER-LEFT-WIDTH: 1px;
    BORDER-BOTTOM-WIDTH: 1px;
    COLOR: #ffffff;
    BACKGROUND-COLOR: #4289cb;
    BORDER-RIGHT-WIDTH: 1px
}
-->
</style>
<script  src="/js/FoshanRen.js"></script>
	<script type="text/javascript">
	function ActionEvent(methodName, orderId){
	    window.location.href = '/control/order/'+ methodName+ '?order.orderId='+ orderId;
	}
	function deleteOrderItem(oitemId, orderId){
		if(confirm('\n您确认删除该项吗?')){
			window.location.href ="/control/order/deleteOrderItem?oitemId="+ oitemId+"&order.orderId="+orderId;
		}
	}
	</script>   
  </head>
  
  <body>
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
          <td colspan="4" bgcolor="#FFFFFF"><strong>订购者信息 </strong> <c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}"><a href="/page/order/updateBuyer.jsp?orderId=${order.orderId }&buyerId=${buyer.buyerId }&name=${buyer.buyerName }&gender=${buyer.gender }&address=${buyer.address }&postalcode=${buyer.postalcode }&phone=${buyer.phone }&mobile=${buyer.mobile }">修改</a></c:if></td>
          <td align="center" bgcolor="#FFFFFF">支付方式</td>
          <td colspan="2" bgcolor="#FFFFFF">${order.paymentWay.name }  <c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}"><a href="/page/order/updatePaymentWay.jsp?orderId=${order.orderId }&paymentWay=${order.paymentWay }&deliverWay=${acceptor.deliverWay }">修改</a></c:if></td>
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
          <td colspan="4" bgcolor="#FFFFFF"><strong>收货人信息</strong> <c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}"><a href="/page/order/updateAcceptor.jsp?orderId=${order.orderId }&acceptorId=${acceptor.acceptorId }&name=${acceptor.acceptorName }&gender=${acceptor.gender }&address=${acceptor.address }&postalcode=${acceptor.postalcode }&email=${acceptor.email }&phone=${acceptor.phone }&mobile=${acceptor.mobile }">修改</a></c:if></td>
          <td align="center" bgcolor="#FFFFFF">送货方式</td>
          <td colspan="2" bgcolor="#FFFFFF">${acceptor.deliverWay.name }  <c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}"><a href="/page/order/updateDeliverWay.jsp?orderId=${order.orderId }&deliverWay=${acceptor.deliverWay }&paymentWay=${order.paymentWay }">修改</a></c:if></td>
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
          <td align="center" bgcolor="#FFFFFF">${item.amount } <c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}"><a href="/page/order/updateProductAmount.jsp?oitemId=${item.itemId }&orderId=${order.orderId }&amount=${item.amount }">修改</a></c:if></td>
          <td align="center" bgcolor="#FFFFFF"><c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}"><a href="JavaScript:deleteOrderItem('${item.itemId }','${order.orderId }')">删除</a></c:if></td>
        </tr>
</c:forEach>
        <tr>
          <td colspan="7" align="right" bgcolor="#FFFFFF"><p>商品合计：￥${order.productTotalPrice }元&nbsp;&nbsp;配送费：￥${order.deliverFee }元 <c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}"><a href="/page/order/updateDeliverFee.jsp?orderId=${order.orderId}&deliverFee=${order.deliverFee }">修改</a></c:if>&nbsp;&nbsp;订单合计：￥${order.totalPrice }元<br />
            
			&nbsp;&nbsp;<strong>应付金额：</strong>￥${order.payableFee }元</p>          </td>
        </tr>
      </table></td>
  </tr>
</table>
<br />
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="2">
  <tr>
    <td width="15%" bgcolor="#FFFFFF">
    <c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}">
	<input type="button" class="frm_btn" value="取消订单" onclick="JavaScript:ActionEvent('cancelOrder', '${order.orderId }')"/>&nbsp;
     </c:if><c:if test="${order.state=='WAITCONFIRM'}">
    <input type="button" class="frm_btn" value="审核通过" onclick="JavaScript:ActionEvent('confirmOrder', '${order.orderId }')"/>&nbsp;	
    </c:if>
    <c:if test="${order.state=='WAITPAYMENT' || (order.state=='DELIVERED' && order.paymentWay=='COD')}">
    <input type="button" class="frm_btn" value="财务确认已付款" onclick="JavaScript:ActionEvent('confirmPayment', '${order.orderId }')"/>&nbsp;	
    </c:if>
    <c:if test="${order.state=='ADMEASUREPRODUCT'}">
    <input type="button" class="frm_btn" value="等待发货" onclick="JavaScript:ActionEvent('turnWaitDeliver', '${order.orderId }')"/>&nbsp;	
    </c:if>
    <c:if test="${order.state=='WAITDELIVER'}">
    <input type="button" class="frm_btn" value="已经发货" onclick="JavaScript:ActionEvent('turnDelivered', '${order.orderId }')"/>&nbsp;	
    </c:if>
    <c:if test="${order.state=='DELIVERED' && order.paymentWay!='COD'}">
    <input type="button" class="frm_btn" value="已经收货" onclick="JavaScript:ActionEvent('turnReceived', '${order.orderId }')"/>&nbsp;	
    </c:if>
    <input type="button" class="frm_btn" value="打印订单" onclick="JavaScript:winOpen('/control/order/showPrintUI?order.orderId=${order.orderId }','打印',700,450)"/>&nbsp;
	<c:if test="${order.state!='RECEIVED' && order.state!='CANCEL'}">
	<input type="button" class="frm_btn" value="解锁退出" onclick="JavaScript:window.location.href='/control/order/unlockOrder?order.orderId=${order.orderId }'"/>
	</c:if>
	</td>
  </tr>
</table>
<br />
<table width="90%" border="0" align="center" cellpadding="2" cellspacing="2">
  <tr>
    <td colspan="2"  bgcolor="6f8ac4"><FONT COLOR="#FFFFFF">客服留言</FONT> &nbsp; <input type="button" class="frm_btn" value="客服留言" onclick="JavaScript:window.location.href='/control/order/showAddMessageUI?orderId=${order.orderId}'"/></td>
  </tr>
  <tr>
    <td width="30%" align="center" bgcolor="#FFFFCC">留言者/时间</td>
    <td width="70%" align="center" bgcolor="#FFFFCC">内容</td>
  </tr>
  <c:forEach items="${order.msgs}" var="msg">
  <tr>
    <td>${msg.empName } / ${msg.createDate }</td>
    <td >${msg.content }</td>
  </tr>
  <tr><td colspan="2" height="1" bgcolor="#BBC9FF"></td></tr></c:forEach>
</table>
<p>&nbsp;</p>
  </body>
</html>
