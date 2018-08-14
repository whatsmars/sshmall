<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>有才网-用户登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/css/global/header.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function checkName(){
      var name = trim($("#username").val());
      if(name==""){
        $("#checkName").html("请输入用户名");
        return false;
      }else{
	    $("#checkName").html("");
	    return true;
	  }
    }
	
    function checkPass(){
      var password = trim($("#password").val());
      if(checkName()){
	      if(password==""){
	        $("#checkPass").html("请输入密码");
	        return false;
	      }else{
		    $("#checkPass").html("");
		    return true;
		  }
      }
    }
    
    function checkForm(){
      return checkName() && checkPass();
    }
    
    function focusName(){
      if(document.getElementById('checkName').innerHTML!='')
        document.getElementById('checkName').style.display='none';
      if(document.getElementById('error').innerHTML!='') { 
        document.getElementById('error').style.display='none'; 
        document.getElementById('td1').style.display='block'; 
      }
    }
    function focusPass(){
      if(document.getElementById('checkPass').innerHTML!='')
        document.getElementById('checkPass').style.display='none';
    }
  //-->
  </SCRIPT>
<style type="text/css">
<!--
.STYLE1 {color: #666666}
.logintitle H2 {
	DISPLAY: block; FONT-WEIGHT: bold; FONT-SIZE: 14px; MARGIN: 0px 0px 0px 0px;PADDING:0 0 0 10px;color:#525252
}
.ablue2{
	FONT-SIZE: 14px; color:#0066FF
}
-->
</style>

  </head>
  
  <body>
    <jsp:include page="/page/share/simpleHead.jsp"/>
<!-- Head End -->
<TABLE cellSpacing=0 cellPadding=0 width=770 align=center border=0>
  <TBODY>
  <TR><TD background="/images/login/login_03.jpg">&nbsp;</TD></TR>
 </TBODY>
</TABLE>
<form action="/front/user/login" method="post" onsubmit="javascript:return checkForm()">
<input type="hidden" name="toUI" value="${param.toUI }">
<table width="760" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center"><table width="80%" border="0" align="center" cellpadding="10" cellspacing="0">
					<tr>
						<td width="100%" height="50" valign="top"><table width="95%" height="25" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td width="3%"><img src="/images/global/loginicon1.gif" width="16" height="15" align="absBottom"></td>
									<td width="97%" align="left" class="logintitle"><H2>用户登录</H2></td>
								</tr>
							</table>						</td>
					</tr>
					<tr><div id="checkName" style="color:red;"></div></tr>
					<tr><div id="checkPass" style="color:red;"></div></tr>
					<tr><td id="error" style="color:red;text-align:center;"><s:property value="errors.login[0]"/></td><td id="td1" style="display:none;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>&nbsp;</td></tr>
					<tr>
						<td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
								  <td width="40%" align="right" class="c5"><strong>您的用户账号：</strong></td>
								  <td width="27%"> <input name="user.name" onblur="checkName();" onfocus="focusName()" id="username" type="text" size="20" maxlength="20" /></td>
								  <td align="left"></td>
						        </tr>
								<tr>
								  <td colspan="3" align="right" height="15"></td>
						  </tr>
								<tr>
									<td align="right" class="c5"><strong>您的密码：</strong></td>
									<td > <input name="user.password" id="password" onblur="checkPass();" onfocus="focusPass()" type="password" size="20" maxLength="16"></td>
									<td width="33%" align="left"><a href="/page/user/front/sendMail.jsp;" class="ablue" target="_blank">忘记密码了？</a></td>
								</tr>
							</table>						</td>
					</tr>
					<tr>
						<td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td class="STYLE1">注意：如果你在网吧等公众场合的机器上登录本网，请在离开机器前退出，以免账户被他人冒用造成不必要的麻烦！</td>
								</tr>								
							</table>						</td>
					</tr>
					<tr>
						<td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td width="34%" align="right">&nbsp;</td>
									<td width="8%" align="left" valign="middle"><input type="image" name="ImageButton1" src="/images/global/regloginbutton2.jpg" alt="" border="0" />&nbsp;</td>
								    <td width="58%" align="left" valign="middle">如果你还未注册，<span style="font-size:14px"><a href='/page/user/front/register.jsp?toUI=${param.toUI }' class="ablue2">请免费注册</a>！</span></td>
								</tr>
							</table>						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table></form>
<jsp:include page="/page/share/Foot.jsp"/>
  </body>
</html>
