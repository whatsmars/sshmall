<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>有才网-用户注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/css/global/header.css" rel="stylesheet" type="text/css">
	<link href="/css/global/reg.css" rel="stylesheet" type="text/css">
	<style type="">
	.msg {color:red;}
	</style>
	<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
	<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
	<SCRIPT language=JavaScript src="/js/ajaxUtils.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  function checkName() {
    var name = trim($("#username").val());
    if(name==""){
      $("#checkName").html("用户名不能为空");
	  return false;
    }else if(byteLength(name)<4 || byteLength(name)>20){
	  $("#checkName").html("用户名应为4-20位字符");
	  return false;
	}else {
	  $("#username").focus(function(){
	    $("#checkPass").html("");
	    $("#checkEmail").html("");
	    $("#checkCode").html("");
	  });
	  
	  /********************************************/
	  $("#checkName").html("检验用户名是否已被注册...");
	  Ajax.sendRequest("POST", "/front/user/checkName?username=" + $("#username").val(), null, callback);
	  /*
	  $.ajax({
        type:"POST",
        url:"/front/user/checkName",
        data:"user.name=" + $("#username").val(),
        dataType:"json",
        success:function(data){
          $("#checkName").html(data.msg);
        }
      });*/
      if($("#checkName").text()=="该用户名已注册") return false;
      else return true;
      
	}
	function callback(data){
	  var msg = data.text;
	  if(msg=="恭喜，该用户名可注册") $("#checkName").css("color", "green");
	  $("#checkName").html(msg);
	}
  }
  
  function checkPass() {
    var password = trim($("#password").val());
    if(password==""){
      $("#checkPass").html("密码不能为空");
	  return false;
    }else if(byteLength(password)<6 || byteLength(password)>16){
	  $("#checkPass").html("密码长度应为6-16位");
	  return false;
	}else{
	  $("#checkPass").html("");
	  $("#password").focus(function(){
	    $("#checkName").html("");
	    $("#checkRepass").html("");
	    $("#checkEmail").html("");
	    $("#checkCode").html("");
	  });
	  return true;
	}
  }
  
  function checkRepass() {
    var password = trim($("#password").val());
    if(checkPass()&&trim($("#repassword").val())!=""&&password!=trim($("#repassword").val())){
	  $("#checkRepass").html("两次输入的密码不一致");
	  return false;
	}else{
	  $("#checkRepass").html("");
	  $("#repassword").focus(function(){
	    $("#checkName").html("");
	    $("#checkEmail").html("");
	    $("#checkCode").html("");
	  });
	  return true;
	}
  }
  
  //email验证
    function checkEmail(){
	  var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  if(!filter.test($("#email").val())){
	    $("#checkEmail").html("请输入邮箱或检查邮箱格式");
	    return false;
	  }else{
	    $("#checkEmail").html("");
	    $("#email").focus(function(){
		    $("#checkName").html("");
		    $("#checkPass").html("");
		    $("#checkRepass").html("");
		    $("#checkCode").html("");
	    });
	    return true;
	  }
    }
    
    function checkCode(){
      if($("#regCode").val()==""){
        $("#checkCode").html("请输入验证码");
        return false;
      }else{
        $("#regCode").focus(function(){
		    $("#checkName").html("");
		    $("#checkPass").html("");
		    $("#checkRepass").html("");
		    $("#checkEmail").html("");
	    });
        
        Ajax.sendRequest("POST", "/front/user/checkCode?regCode=" + $("#regCode").val(), null, callback2);
        if($("#checkCode").text()=="输入验证码错误，请重新输入") return false;
        else  return true;
      }
      
    }
    function callback2(data){
      var msg = data.text;
      if(msg=="输入验证码正确") $("#checkCode").css("color", "green");
      $("#checkCode").html(msg);
    }
    
    $(function(){
      $("#changeCode").click(function(){
        $("#codeimg").attr("src", "/front/user/regCode?d=" + new Date().getTime());
      });
      $("#codeimg").click(function(){
        $("#codeimg").attr("src", "/front/user/regCode?d=" + new Date().getTime());
      });
    
      $("#tijiao").click(function(){
        if(checkName() && checkPass() && checkRepass() && checkEmail() && checkCode()) {
          document.forms[0].submit();}
      });
    });
  </SCRIPT>
  </head>
  
  <body>
    <jsp:include page="/page/share/simpleHead.jsp"/>
<!-- 检证用户是否存在表单 end -->
<TABLE cellSpacing=0 cellPadding=0 width=770 align=center border=0>
  <TBODY>
  <TR><TD background="/images/login/login_03.jpg">&nbsp;</TD></TR>
  <TR>
    <TD height=15>&nbsp;</TD>
  </TR>
  <TR>
    <TD height=30><img src="/images/login/zc.gif" width="128" height="31" /></TD>
  </TR>
 </TBODY>
</TABLE>

<div id="Content">
<form id="fm" action="/front/user/register" method="post">
<input type="hidden" name="toUI" value="${param.toUI }">
<div id="Main">
  <ul id="FormRegStep1_Account" class="RegForm">
        <li class="Title">以下<span class="STYLE1">均为</span>必填项　</li>
		
		<li>
		   <div class="Hint">会员名：</div>
        <div class="Input">
		  	<input name="user.name" type="text" id="username" onblur="checkName();" onkeyup="value=value.replace(/[\W]/g,'')" size="24" value="" tabindex="1"  maxLength="20"/> <br />
  		  </div>
          <div class="Info">
        		<div id="username_info">4-20个字符(包括英文字母、数字、下划线，不能含有汉字)，一旦注册成功会员名不能修改。</div>
			    <div id="checkName" style="color:red;"></div>
          </div>
        <div class="HackBox"></div>
		</li>
 
	    <li>
            <div class="Hint">密码：</div>
            <div class="Input">
              <input name="user.password" onblur="checkPass();" id="password" type="password" onkeyup="value=value.replace(/[\W]/g,'')" size="24" maxlength="16" tabindex="2"/>
            </div>
            <div class="Info">
                <div id="password_info">密码由6-16个字符组成，请使用英文字母加数字或字符的组合密码</div>
                <div id="checkPass" style="color:red;"></div>
            </div>
            <div class="HackBox"></div>
          </li>
    
          <li>
            <div class="Hint">再输入一遍密码：</div>
            <div class="Input">
              <input name="repassword" onblur="checkRepass();" id="repassword" type="password" size="24" maxlength="16" tabindex="3"/>
            </div>
            <div class="Info">
                 <div id="confirm_password_info">请再输入一遍您上面输入的密码。</div><br>
                 <div id="checkRepass" style="color:red;"></div>
            </div>
            <div class="HackBox"></div>
          </li>
		  <li>
            <div class="Hint">电子邮件：</div>
            <div class="Input">
              <input name="user.email" id="email" onblur="checkEmail();" type="text" size="28" maxlength="45" tabindex="3"/>
            </div>
            <div class="Info">
            <div id="email_info">没有电子邮件？推荐使用<a href="https://reg.cn.yahoo.com/cnreg/cnreg_first?id=78001" target="_blank">雅虎3.5G免费超大邮箱</a>、<a href="http://mail.sogou.com/register.jsp" target="_blank">搜狐邮箱</a>和<a href="http://reg.126.com/reg1.jsp" target="_blank">网易邮箱</a>。<br></div>
            <div id="checkEmail" style="color:red;"></div>
            </div>
            <div class="HackBox"></div>
          </li>
		  <li>
            <div class="Hint">请输入验证码：</div>
            <div class="Input">
              <input name="regCode" id="regCode" onblur="checkCode();" type="text" size="25" maxlength="25" tabindex="3"/>
            </div>
            <img id="codeimg" src="/front/user/regCode"> 看不清验证码,<a id="changeCode" href="#">换一张</a>
            <div id="checkCode" style="color:red;"></div>
            <div class="HackBox"></div>
          </li>
	</ul><p class="SubmitBox">
	<input type="button" id="tijiao" value="提交注册信息" tabindex="8" />
	</p></div>
	</form>
</div>
<jsp:include page="/page/share/Foot.jsp" />
  </body>
</html>
