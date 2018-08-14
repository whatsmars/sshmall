<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<oscache:flush scope="application" />   <!-- 清空页面缓存 -->
<html>
<head>
<title>产品列表</title>
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<script type="text/javascript" src="/js/jquery.js"></script>
	<script language="JavaScript">
<!--
	//到指定的分页页面
	function toPage(page){
		$("#currentPage").val(page);
		document.forms[0].submit();
	}
	
	function selectAll(){
      var ids = document.getElementsByName("productIds");
      if(document.forms[0].all.checked){
        for(i=0; i<ids.length; i++){
          ids[i].checked = "checked";
        }
      }else{
        for(i=0; i<ids.length; i++){
          ids[i].checked = "";
        }
      }
    }
	
	function actionEvent(action){ 
		var form = document.forms[0];
		if(hasSelected(form.all, form.productIds)){
			form.action="/control/product/" + action;
			$("#currentPage").remove();
			$("#query").remove();
			form.submit();
		}else{
			alert("请选择要操作的记录");
		}
	}
	
	/*
	 * 判断是否选择了记录
     */
	function hasSelected(allobj,items){
	    var state = allobj.checked;
	    if(items.length){
	    	for(var i=0;i<items.length;i++){
	    		if(items[i].checked) return true;
	    	}
	    }else{
	    	if(items.checked) return true;
	    }
	    return false;
	}
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<form action="/control/product/list" method="post">
  <input id="currentPage" type="hidden" name="pageCtx.currentPage">
  
  <!-- 确保翻页时也执行查询 -->
  <input id="query" type="hidden" name="query" value="${query }">
  <input type="hidden" name="product.name" value="${product.name }"/>
  <input type="hidden" name="type.typeId" value="${type.typeId }"/>
  <input type="hidden" name="minBasePrice" value="${minBasePrice }"/>
  <input type="hidden" name="maxBasePrice" value="${maxBasePrice }"/>
  <input type="hidden" name="minSalePrice" value="${minSalePrice }"/>
  <input type="hidden" name="maxSalePrice" value="${maxSalePrice }"/>
  <input type="hidden" name="product.code" value="${product.code }"/>
  <input type="hidden" name="brand.brandId" value="${brand.brandId }"/>

  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="11"  bgcolor="6f8ac4" align="right">
    	<%@ include file="/page/share/fenye.jsp" %>
   </td></tr>
    <tr>
      <td width="7%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">产品ID</font></div></td>
      <td width="8%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">货号</font></div></td>
      <td width="5%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td width="20%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">产品名称</font></div></td>
      <td width="6%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">人气</font></div></td>
	  <td width="10%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">所属分类</font></div></td>
	  <td width="7%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">底价</font></div></td>
	  <td width="7%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">销售价</font></div></td>
	  <td width="6%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">在售</font></div></td>
	  <td width="6%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">推荐</font></div></td>
	  <td width="12%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">图片</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<c:forEach items="${products}" var="entry">
    <tr>
      <td bgcolor="f5f5f5"> &nbsp;&nbsp;&nbsp;<INPUT TYPE="checkbox" NAME="productIds" value="${entry.productId}">${entry.productId }</td>
      <td bgcolor="f5f5f5"> <div align="center">${entry.code }</div></td>
      <td bgcolor="f5f5f5"> <div align="center"><a href="/control/product/brand/simpleList?listVar=updateProduct&product.productId=${entry.productId }&brandId=${entry.brand.brandId}">
	  <img src="/images/edit.gif" width="15" height="16" border="0"></a></div></td>
	  <td bgcolor="f5f5f5"> <div align="center">${entry.name }</div></td>
	  <td bgcolor="f5f5f5"> <div align="center">${entry.clickCount }</div></td>
	  <td bgcolor="f5f5f5"> <div align="center">${entry.type.name }</div></td>
	  <td bgcolor="f5f5f5"> <div align="center">${entry.basePrice }</div></td>
	  <td bgcolor="f5f5f5"> <div align="center">${entry.salePrice }</div></td>
	  <td bgcolor="f5f5f5" align="center"><c:if test="${entry.visible}">在售</c:if><c:if test="${!entry.visible}">停售</c:if></td>
	  <td bgcolor="f5f5f5" align="center"><c:if test="${entry.commend}">推荐</c:if><c:if test="${!entry.commend}">--</c:if></td>
	  <td bgcolor="f5f5f5"> <div align="center"><a href="/control/product/style/list?product.productId=${entry.productId}">产品图片管理</a></div></td>
	</tr>
</c:forEach>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="11" align="center">
        <table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="8%">&nbsp;&nbsp;&nbsp;<INPUT TYPE="checkbox" <c:if test="${fn:length(products)<1}">disabled="disabled"</c:if>
             name="all" onclick="selectAll();"/>全选</td>
            <td width="92%">
              <input type="button" class="frm_btn" onClick="location.href='/control/product/brand/simpleList?listVar=addProduct'" value="添加产品"> &nbsp;&nbsp;
			  <input name="query" <c:if test="${fn:length(products)<1}">disabled="disabled"</c:if> type="button" class="frm_btn" id="query" onClick="location.href='/control/product/brand/simpleList?listVar=queryProduct'" value=" 查 询 "> &nbsp;&nbsp;
              <input name="visible" <c:if test="${fn:length(products)<1}">disabled="disabled"</c:if> type="button"
               class="frm_btn" onClick="javascript:actionEvent('visible')" value=" 上 架 "> &nbsp;&nbsp;
              <input name="disvisible" type="button" <c:if test="${fn:length(products)<1}">disabled="disabled"</c:if> class="frm_btn" 
              onClick="javascript:actionEvent('disvisible')" value=" 下 架 "> &nbsp;&nbsp;
              <input name="commend" type="button" <c:if test="${fn:length(products)<1}">disabled="disabled"</c:if> class="frm_btn" 
              onClick="javascript:actionEvent('commend')" value=" 推 荐 "> &nbsp;&nbsp;
              <input name="uncommend" type="button" <c:if test="${fn:length(products)<1}">disabled="disabled"</c:if> class="frm_btn"
				onClick="javascript:actionEvent('uncommend')" value=" 不推荐 "> &nbsp;&nbsp;
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
</body>
</html>