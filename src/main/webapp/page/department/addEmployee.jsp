<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<html>
<head>
<title>添加员工</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<SCRIPT language=JavaScript src="/js/jquery.js"></SCRIPT>
<SCRIPT language=JavaScript src="/js/ajaxUtils.js"></SCRIPT>
<script language="JavaScript">
function checkName() {
    var name = trim($("#name").val());
    if(name==""){
      $("#checkName").html("用户名不能为空");
	  return false;
    }else if(byteLength(name)<4 || byteLength(name)>20){
	  $("#checkName").html("用户名应为4-20位字符");
	  return false;
	}else {
	  
	  /********************************************/
	  $("#checkName").html("检验用户名是否已被注册...");
	  Ajax.sendRequest("POST", "/control/employee/checkName?name=" + $("#name").val(), null, callback);
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
	  $("#checkPass").html("密码长度应为6-20位");
	  return false;
	}else{
	  $("#checkPass").html("");
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
	  return true;
	}
  }

function Formfield(id, label, minlen, maxlen){
	this.id=id;
	this.label=label;
	this.minlen=minlen;
	this.maxlen=maxlen;
}
function checkfm(form){
	var list  = new Array(
	  new Formfield("realname", "员工姓名", 2, 10),new Formfield("cardNo", "身份证号码", 18, 18)
	  ,new Formfield("birthday", "身份证出生日期", 10, 10),new Formfield("address", "身份证地址", 2, 50),new Formfield("phone", "联系电话", 11, 18));
	for(var i=0;i<list.length;i++){
	    var fieldobj = $("#"+list[i].id);
	    objvalue = trim(fieldobj.val());
		if(objvalue==""){
			alert(list[i].label+ "不能为空");
			if(fieldobj.type!="hidden" && fieldobj.focus()) fieldobj.focus();
			return false;
		}else if(byteLength(objvalue)<list[i].minlen || byteLength(objvalue)>list[i].maxlen){
			if(list[i].minlen != list[i].maxlen) alert(list[i].label+"的长度必须在"+list[i].minlen+"到"+list[i].maxlen+"之间");
			else alert(list[i].label+"的长度必须为"+list[i].minlen+"位");
			return false;
		}
	}
	
	var imagefile = $("#empImagePath").val();
	if(imagefile!=""){
	  var ext = imagefile.substring(imagefile.length-3).toLowerCase();
	  if (ext!="jpg" && ext!="gif" && ext!="bmp" && ext!="png"){
		alert("只允许上传gif、jpg、bmp、png！");
		return false; 
	  }
	}
	
	if($("input[name='departmentId']:checked").length==0){
	  alert('请选择部门');
	  return false;
	}
	
    return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/employee/add" method="post" enctype="multipart/form-data" onsubmit="return checkfm(this);">
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td colspan="2"  > <font color="#FFFFFF">添加员工：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">登录账号：</div></td>
      <td width="78%"> <input type="text" id="name" name="employee.name" size="32" maxlength="32" onblur="checkName();"/>(账号的长度必须在4到20之间,不能用中文)<font color="#FF0000">*</font>
       <div id="checkName" style="color:red;"></div></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">登录密码：</div></td>
      <td width="78%"> <input type="password" id="password" name="employee.password" onblur="checkPass()" size="32" maxlength="32"/>(密码的长度必须在6到20之间,不能用中文)
        <font color="#FF0000">*</font><div id="checkPass" style="color:red;"></div></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">再次输入登录密码：</div></td>
      <td width="78%"> <input type="password" id="repassword" onblur="checkRepass()" size="32" maxlength="32"/>
        <font color="#FF0000">*</font><div id="checkRepass" style="color:red;"></div></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">员工姓名：</div></td>
      <td width="78%"> <input type="text" id="realname" name="employee.realname" size="10" maxlength="10"/>
        <font color="#FF0000">*</font>员工姓名长度必须在2到10之间</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">性别：</div></td>
      <td width="78%"> <input type="radio" name="gender" value="MAN">男
      <input type="radio" name="gender" value="WOMEN">女</td>
    </tr> 
	<tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">员工照片：</div></td>
      <td width="78%"> <input type="file" id="empImagePath" name="fileCtx.file" size="50"><br>
      将图片以宽度等比例压缩，请输入压缩后的宽度（默认160px）：<input type="text" size="4" name="fileCtx.width" value="160" />
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">身份证号码：</div></td>
      <td width="78%"> <input type="text" id="cardNo" name="idCard.cardNo" size="20" maxlength="18"/>
        <font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">身份证出生日期：</div></td>
      <td width="78%"> <input type="text" id="birthday" name="idCard.birthday" size="20" maxlength="18"/>
        <font color="#FF0000">*</font>格式：1989-03-09</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">身份证地址：</div></td>
      <td width="78%"> <input type="text" id="address" name="idCard.address" size="60" maxlength="100"/>
        <font color="#FF0000">*</font></td>
    </tr>  
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">联系电话：</div></td>
      <td width="78%"> <input type="text" id="phone" name="employee.phone" size="20" maxlength="18"/><font color="#FF0000">*</font></td>
    </tr> 
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">电子邮件：</div></td>
      <td width="78%"> <input type="text" name="employee.email" size="20" maxlength="18"/></td>
    </tr> 
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">学历：</div></td>
      <td width="78%"> <input type="text" name="employee.degree" size="10" maxlength="5"/></td>
    </tr>
     <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">毕业院校：</div></td>
      <td width="78%"> <input type="text" name="employee.school" size="20" maxlength="20"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">所在部门：</div></td>
      <td><!-- 每8个部门,用<br>进行分行 -->
      <c:forEach items="${departments}" var="department" varStatus="statu">      
      	<input type="radio" name="departmentId" value="${department.departmentId}">${department.name}	
      	<c:if test="${statu.count%8==0}"><br></c:if>
      </c:forEach>
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="SYS_SET" value=" 确 定 " class="frm_btn">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>