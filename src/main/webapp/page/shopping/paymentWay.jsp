<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD>
<META http-equiv="pragma" content="no-cache">
<META http-equiv="Cache-Control" content="no-cache, must-revalidate">
<META http-equiv="expires" content="Wed, 26 Feb 2006 08:21:57 GMT">
<TITLE>有才网-结算中心：选择支付方式</TITLE>
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<link href="/css/global/paymentWay.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
$(function(){
  if($("#d2").is(":checked")||$("#d3").is(":checked")){
    $("#time").show();
    $("#cod").show();
  }else{
    $("#time").hide();
    $("#cod").hide();
  }
  if($("#d1").is(":checked")||$("#d4").is(":checked")){
    $("#time").hide();
    $("#cod").hide();
  }
  
  $("#d2").click(function(){
    $("#time").show();
    $("#cod").show();
  });
  $("#d3").click(function(){
    $("#time").show();
    $("#cod").show();
  });
  $("#d1").click(function(){
    $("#time").hide();
    $("#cod").hide();
  });
  $("#d4").click(function(){
    $("#time").hide();
    $("#cod").hide();
  });
  
  for(i=1;i<=4;i++){
    if($("#d"+i).val()==$("#deliverWay").val()) {
      $("#d"+i).attr("checked", "checked");
      if(i==2 || i==3) {
        $("#time").show();
        for(j=1;j<=5;j++){
          if($("#r"+j).val()==$("#requirement").val()) {
            $("#r"+j).attr("checked", "checked");
          } 
        }
        $("#cod").show();
      }
    } 
    if($("#p"+i).val()==$("#paymentWay").val()) $("#p"+i).attr("checked", "checked");
  }
})

function sendForm(){
  if($("input[name='deliverWay']:checked").length==0){
    alert("请选择送货方式");
  }else if($("input[name='paymentWay']:checked").length==0){
    alert("请选择支付方式");
  }else{
    document.forms[0].submit();
  }
}
/** 根据值设置对象checked状态为true **/
	function setSelectRadioByValue(radioObject, value){
		if(typeof(radioObject.value)=="undefined"){
			for(var i=0;i<radioObject.length;i++){
				if(radioObject[i].value==value){
					radioObject[i].checked=true;
					break;
				}
			}
		}else{
			if(radioObject.value==value) radioObject.checked=true;
		}
	}
//-->
</SCRIPT>
</HEAD>

<BODY">
<TABLE cellSpacing=0 cellPadding=0 align=center border=0>
  <TBODY>
  <TR>
    <TD><a href="/index.jsp"><IMG src="/images/global/yc.jpg" ></a> 
	&nbsp;&nbsp;<IMG height=36 src="/images/buy/az-s-checkout-payment-banne.gif" > 
	</TD>
  </TR>
  </TBODY>
</TABLE>
<BR>
<form action="/customer/shopping/manage/savePaymentWay" method="post">
<input type="hidden" name="directUrl" value="${param.directUrl }" />

<input id="deliverWay" type="hidden" value="${deliverWay }">
<input id="paymentWay" type="hidden" value="${paymentWay }">
<input id="requirement" type="hidden" value="${requirement }">

<TABLE cellSpacing=0 cellPadding=0 width="66%" align="center" border=0>
  <TBODY>
  <TR>
    <TD>
<SPAN class=h1><STRONG>请选择您的送货与支付方式:</STRONG></SPAN> 
      <TABLE height=31 cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD>
            <DIV align=right><IMG onClick="javascript:sendForm()" height="22" src="/images/buy/az-sfl-shipping-to-this-boo.gif" vspace=5 border=0 style="CURSOR: hand;">
        </DIV></TD></TR>
	 </TBODY></TABLE>
<A name="deliverWay"></A>
      <TABLE cellSpacing=1 cellPadding=1 width="100%" bgColor="#eeeecc" border=0><TBODY>
        <TR>
          <TD bgColor="#ffffff">
            <TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
              <TBODY>
              <TR bgColor="#eeeecc">
                <TD colSpan=2><STRONG>&nbsp;送货方式</STRONG></TD></TR>
				<tr>
				 <TD class="big14" vAlign="middle" align="right" width="10%"><input type="radio" id="d1" name="deliverWay" value="GENERALPOST"/></TD>
				 <TD vAlign="middle" ><B>平邮</B> (费用:10.0元)&nbsp;&nbsp;不支持货到付款，注:费用最低，需要到附近邮局自提，时间稍长</TD>
				</tr>
				<tr>
				<TD class="big14" vAlign="middle" align="right" width="10%"><input type="radio" id="d2" name="deliverWay" value="EXPRESSDELIVERY""/></TD>
				 <TD vAlign="middle" ><B>快递送货上门 </B> (费用:10.0元)&nbsp;&nbsp;支持货到付款 &nbsp;&nbsp;注:200个城市可以到达，部分城市不能到达</TD>
				</tr>
				<tr>
				<TD class="big14" vAlign="middle" align="right" width="10%"><input type="radio" id="d3" name="deliverWay" value="EXIGENCEEXPRESSDELIVERY"" /></TD>
				 <TD vAlign="middle" ><B>加急快递送货上门</B> (费用:10.0元)&nbsp;&nbsp;支持货到付款&nbsp;&nbsp;注:200个城市可以到达，部分城市不能到达</TD>
				</tr>
				<tr>
				 <TD class="big14" vAlign="middle" align="right" width="10%"><input type="radio" id="d4" name="deliverWay" value="EMS"/></TD>
				 <TD vAlign="middle" ><B>国内特快专递EMS</B> (费用:10.0
				 元)&nbsp;&nbsp;不支持货到付款&nbsp;&nbsp;注:适合其他快运无法到达的城市，时间3-5个工作日</TD>
				</tr>
				<tr>
				  <TD colspan="2" vAlign="middle" class="big14">
				  
				  <TABLE cellSpacing=0 cellPadding=3 width="86%" align="center" id="time" border=0>
                    <TBODY>
                      <TR>
                        <TD align=left colSpan=2 style="FONT-WEIGHT: bold; PADDING-BOTTOM: 2px; PADDING-TOP: 2px; BORDER-BOTTOM: #000000 1px solid">时间要求(注:如对送货时间有特别要求请注明)</TD>
                      </TR>
                      <TR>
                        <TD align=right><input type="radio" id="r1" name="requirement" value="工作日、双休日与假日均可送货"/></TD>
                        <TD align=left width="96%">工作日、双休日与假日均可送货</TD>
                      </TR>
                      <TR class=category-row-shaded>
                        <TD align=right><input type="radio" id="r2" name="requirement" value="只双休日、假日送货"/></TD>
                        <TD align=left>只双休日、假日送货(工作日不用送)</TD>
                      </TR>
                      <TR>
                        <TD align=right><input type="radio" id="r3" name="requirement" value="只工作日送货(双休日、假日不用送)"/></TD>
                        <TD align=left>只工作日送货(双休日、假日不用送) (注：写字楼/商用地址客户请选择)</TD>
                      </TR>
                      <TR class=category-row-shaded>
                        <TD align=right><input type="radio" id="r4" name="requirement" value="学校地址/地址白天没人，请尽量安排其他时间送货"/></TD>
                        <TD align=left>学校地址/地址白天没人，请尽量安排其他时间送货 (注：特别安排可能会超出预计送货天数)</TD>
                      </TR>
                      <TR>
                        <TD align=right><input type="radio" id="r5" name="requirement" value="other"></TD>
                        <TD align=left><P>特殊说明：
                           <input type="text" id="other" name="deliverNote" value="${deliverNote }" maxlength="100" size="40" onfocus="javascript:setSelectRadioByValue(this.form.requirement,'other')"/>
                        </P></TD>
                      </TR>
                    </TBODY>
                  </TABLE></TD>
				  </tr>
			</TBODY></TABLE>
		  </TD>
		</TR>
	 </TBODY></TABLE>
<br><A name="paymentWay"></A>
      <TABLE cellSpacing=1 cellPadding=1 width="100%" bgColor=#eeeecc border=0><TBODY>
        <TR>
          <TD bgColor=#ffffff>
            <TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
              <TBODY>
              <TR bgColor=#eeeecc>
                <TD colSpan=2><STRONG>&nbsp;支付方式</STRONG></TD>
			 </TR>
			 <TR>
                <TD class="big14" vAlign="middle" align="right" width="10%"><input type="radio" id="p1" name="paymentWay" value="NET"/> </TD>
                <TD vAlign="middle" ><B>网上支付</B>  易宝支付</TD>
			  </TR>
              <TR id="cod">
                <TD class="big14" vAlign="middle" align="right" width="10%"><input type="radio" id="p2" name="paymentWay" value="COD"/> </TD>
                <TD><B>货到付款</B></TD>
			  </TR>
			  <TR>
                <TD class="big14" vAlign="middle" align="right" width="10%"><input type="radio" id="p3" name="paymentWay" value="BANKREMITTANCE"/> </TD> 
                <TD><B>银行电汇</B>  开户名: 北京惠利至易高科技发展有限公司<BR>开户行名称: 
                  交通银行上地支行<BR>银行帐号: 110060974018001084072</TD></TR>
              <TR>
                <TD class="big14" vAlign="middle" align="right" width="10%"><input type="radio" id="p4" name="paymentWay" value="POSTOFFICEREMITTANCE"/></TD> 
                <TD><B>邮局汇款</B><BR>收款人地址：<FONT COLOR="#FF9900">北京市朝阳区酒仙桥十街坊2号楼巨擎达物业428室</FONT>&nbsp;&nbsp;收款人姓名：<FONT COLOR="#FF9900">沈小姐</FONT>&nbsp;&nbsp;收款人邮编：<FONT COLOR="#FF9900">100016</FONT><BR>请在汇款人简短留言中注明您的订单号/用户名(非常重要)<BR></TD></TR>
			 </TBODY></TABLE>
  </TABLE>
			</TD></TR></TBODY></TABLE><BR>
      <TABLE height=31 cellSpacing=0 cellPadding=0 width="66%" border=0 align="center">
        <TBODY>
        <TR>
          <TD>
            <DIV align=right><IMG onClick="javascript:sendForm()" height="22" src="/images/buy/az-sfl-shipping-to-this-boo.gif" vspace=5 border=0 style="CURSOR: hand;"> 
        </DIV></TD></TR></TBODY></TABLE>
</form>

</BODY></HTML>