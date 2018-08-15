<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>${product.name}-有才网</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="${product.name}">
	<link href="/css/global/header01.css" rel="stylesheet" type="text/css">
	<link href="/css/product/product.css" rel="stylesheet" type="text/css">
	<link href="/css/global/topcommend.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(function(){
  $("#commenddetail").html("正在加载...");
  $("#scanHistory").html("正在加载...");
  $("#commenddetail").load("/front/product/findTopSale", {typeId:"${product.type.parent.typeId}"});
  $("#scanHistory").load("/front/product/findScanHistory");
});
function change(index) {
  var color = $("#colorSelect").val();
  var productId = $("#productId").val();
  location.href="/front/product/detailShow?&color=" + color + "&productId=" + productId;
}
</SCRIPT>

<link rel="stylesheet" href="/css/product/MagicZoom.css" type="text/css" media="screen" />
    <style>
	img {
		border-width: 0px;
	}
	</style>
<script src="/js/mz-packed.js" type="text/javascript"></script>
  </head>

  <body onload='$("#index").css("background", "");'>
<jsp:include page="/page/share/Head.jsp"/>
<div id="ContentBody"><!-- 页面主体 -->
<c:set var="out" value="&gt;&gt; <em>${product.name}</em>"/><c:forEach items="${types}" var="t" varStatus="s">
		<c:set var="out" value=" &gt;&gt; <a href='/front/product/list?type.typeId=${t.typeId}'>${t.name}</a> ${out}"/>
	</c:forEach>
	 	 <div id="position"> 您现在的位置：<a href="/" name="linkHome">有才网</a> <span id="uc_cat_spnPath"><c:out value="${out}" escapeXml="false"></c:out></span></div>
 <div class="browse_left"><!-- 页面主体 左边 -->
        <!-- 浏览过的商品 -->
	 <div class="browse">
	      <div class="browse_t">您最近浏览过的商品</div>
	      <ul id="scanHistory"></ul>
	 </div>
	<!--精品推荐 start -->
	<DIV id="topcommend" align="left">
	       <DIV id="newtop"><IMG height=13 src="/images/global/sy2.gif" width=192></DIV>
	       <DIV id="newlist">
		  <DIV id="newmore">
		    <DIV class="title">精品推荐</DIV>
		  </DIV>
			<span id="commenddetail">
			</span>
		</DIV>
	</DIV>
</div><!-- 页面主体 左边end -->

 <div id="Right" ><!-- 页面主体 右边 -->
<form action="/shopping/cart/buy" method="post">
<INPUT TYPE="hidden" id="productId" name="productId" value="${param.productId}">
<INPUT TYPE="hidden" id="typeId" name="typeId" value="${param.typeId }">
<INPUT TYPE="hidden" id="styleId" name="styleId" value="${styleId }">
    <div id="browse_left">
      <div style="float:left;">
      <a href='<s:property value="style.productPrototypeImagePath" />' title="MagicZoom: Super bike" class="MagicZoom"><img src='<s:property value="style.productImagePath" />'/></a>
      </div>
	  <div class="right_right" style="float:left;">
	    <div class="right_title"><b>${product.name}</b></div>
	    <div class=""><ul><li class="li2">商品编号：${product.productId}<font color="#CC0000">（电话订购专用）</font> <c:if test="${!empty product.brand}">品牌：${product.brand.name}</c:if></li></ul></div>
	    <br><div class="">颜色：
	    <s:if test="styles.size>1">
	    <select id="colorSelect" onchange="change(this.selectedIndex);">
	      <s:iterator value="styles" var="s">
	      <option <c:if test="${s==style}">selected</c:if>>${s.name }</option></s:iterator>
	    </select>&nbsp;
	    </s:if>
	    <s:else><s:property value="styles.{name}[0]"/>&nbsp; </s:else>
	    </div>
		<div class="right_desc">
		  <ul>
			<li style="width:320px;">市场价：<s>${product.marketPrice}</s> 元 <font color='#ff6f02'>本站价：<b>${product.salePrice} 元</b></font> 节省：<font color='#ff6f02'>${product.savedPrice }</font> 元										</li>
		  	<li class="right_img"><INPUT TYPE="image" SRC="/images/global/sale.gif"></li>
			<li class="guopiprice">[ <IMG src="/images/global/2j4.gif" border="0">&nbsp;<A href="#" target="_blank">配送说明</A> ]&nbsp;&nbsp;&nbsp;&nbsp;[ <IMG src="/images/global/2j4.gif" border="0">&nbsp;<A href="#" target="_blank">付款方式</A> ]</li>
		  </ul>
	    </div>
      </div>
  </div>
</form>
<div class='right_blank'></div><div class='right_title1'>商品说明</div><div class='right_content'>${product.description}</div>
</div><!-- 页面主体 右边 end -->

</div><!-- 页面主体 end -->
<jsp:include page="/page/share/Foot.jsp" />
</body>
</html>