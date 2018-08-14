<%@ page pageEncoding="gbk" import="java.util.Date;"%>
<%@ include file="/page/share/taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>${type.name} 有才网</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="${type.name}">
	<meta http-equiv="description" content="${type.note}">
	
	<link href="/css/global/header01.css" rel="stylesheet" type="text/css">
	<link href="/css/product/list.css" rel="stylesheet" type="text/css" />	
	<link href="/css/global/topsell.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(function(){
  $("#topSale").html("正在加载...");
  $("#scanHistory").html("正在加载...");
  $("#topSale").load("/front/product/findTopSale", {typeId:"${type.typeId}"});
  $("#scanHistory").load("/front/product/findScanHistory");
});

//到指定的分页页面
	function toPage(page){
		$("#currentPage").val(page);
		document.forms['productlist'].submit();
	}
</SCRIPT>
</head>

<body class="ProducTypeHome2" onload="javascript:pageInit()">
	<jsp:include page="/page/share/Head.jsp"/> <!-- 由于Head.jsp里有form表单，故不能将此包含在下面的form表单里 -->
	<form name="productlist" action="/front/product/list" method="post">
	  <input id="currentPage" type="hidden" name="pageCtx.currentPage">
	  <input type="hidden" name="sort" value="${sort }">
	  <input type="hidden" name="type.typeId" value="${type.typeId }">
	  <input type="hidden" name="brandId" value="${brandId }">
	  <input type="hidden" name="showStyle" value="${showStyle }">
	<c:set var="out" value=""/><c:forEach items="${types}" var="type" varStatus="statu">
		<c:if test="${statu.count==1}"><c:set var="out" value=" &gt;&gt; <em>${type.name}</em> ${out}"/></c:if>
		<c:if test="${statu.count>1}"><c:set var="out" value=" &gt;&gt; <a href='/front/product/list?type.typeId=${type.typeId}&sort='>${type.name}</a> ${out}"/></c:if>
	</c:forEach>
    <div id="position">您现在的位置: <a href="/" name="linkHome">有才网</a> 
    <c:out value="${out}" escapeXml="false"></c:out>（${pageCtx.totalRecords}个）
	</div>

    <!--页面左侧分类浏览部分-->
    <div class="browse_left">
         <div class="browse">
            <div class="browse_t">${type.name}</div>
			
				<h2><span class="gray">浏览下级分类</span></h2>
				<ul><c:forEach items="${type.childTypes}" var="childtype">						
				<li class='bj_blue'><a href="/front/product/list?type.typeId=${childtype.typeId}">${childtype.name}</a></li></c:forEach></ul>
				<span class="gray"><c:if test="${!empty type.parent.typeId }"><a href="/front/product/list?type.typeId=${type.parent.typeId }">&nbsp;&nbsp;[返回上级分类]</a></c:if></span>
	     </div>
		 <div class="browse">
	          <div class="browse_t">最畅销${type.name}</div>
			  <ul id="topSale"></ul>
	     </div>
		 <div class="browse">
	          <div class="browse_t">您最近浏览过的商品</div>
			  <ul id="scanHistory"></ul>
	     </div>
    </div>
    <!--页面右侧分类列表部分开始-->
    <oscache:cache> <!-- 缓存产品展示模块 -->
    <div class="browse_right">
         <div class="select_reorder" style="height:60px;">
              <div class="reorder_l">请选择排序方式： <c:if test="${'saleCount_desc'==param.sort}"><strong><em>销量多到少</em></strong></c:if><c:if test="${'saleCount_desc'!=param.sort}">
              <a title='按销量降序' href="/front/product/list?sort=saleCount_desc&type.typeId=${type.typeId}&showStyle=${param.showStyle }">销量多到少</a></c:if>
			  | <c:if test="${'salePrice_desc'==param.sort}"><strong><em>价格高到低</em></strong></c:if><c:if test="${'salePrice_desc'!=param.sort}">
			  <a title='价格高到低' href="/front/product/list?sort=salePrice_desc&type.typeId=${type.typeId}&showStyle=${param.showStyle }">价格高到低</a></c:if>
			  | <c:if test="${'salePrice_asc'==param.sort}"><strong><em>价格低到低</em></strong></c:if><c:if test="${'salePrice_asc'!=param.sort}">
			  <a title='价格低到高' href="/front/product/list?sort=salePrice_asc&type.typeId=${type.typeId}&showStyle=${param.showStyle }">价格低到高</a></c:if>
			  | <c:if test="${empty param.sort}"><strong><em>最近上架时间</em></strong></c:if><c:if test="${!empty param.sort}">
			  <a title='最近上架时间' href="/front/product/list?sort=&type.typeId=${type.typeId}&showStyle=${param.showStyle }">最近上架时间</a></c:if>
			  &nbsp;&nbsp;共<font color="red">${pageCtx.totalRecords }</font>条记录，每页显示<font color='red'>${pageCtx.maxResults }</font>条记录</div>
			  <div class="reorder_r">显示方式：<c:if test="${param.showStyle=='imagetext'}"><strong><em>图文版</em></strong></c:if><c:if test="${param.showStyle!='imagetext'}">
		      <a href="/front/product/list?sort=${param.sort}&type.typeId=${type.typeId}&brandId=${brandId}&showStyle=imagetext&pageCtx.currentPage=${pageCtx.currentPage }">图文版</a></c:if> |
		      <c:if test="${param.showStyle=='imagetext'}"><a href="/front/product/list?sort=${param.sort}&type.typeId=${type.typeId}&brandId=${brandId}&showStyle=image&pageCtx.currentPage=${pageCtx.currentPage }">图片版</a>
		      </c:if><c:if test="${param.showStyle!='imagetext'}"><strong><em>图片版</em></strong></c:if>
		      </div>
		    <div class="brand">
				<div class="FindByHint">按<strong>品牌</strong>选择：</div>
				<ul class="CategoryListTableLevel1"><c:forEach items="${brands}" var="brand">
				<li><c:if test="${brandId == brand.brandId}">${brand.name}</c:if><c:if test="${brandId != brand.brandId}"><a href="/front/product/list?sort=${param.sort}&type.typeId=${type.typeId}&showStyle=${param.showStyle}&brandId=${brand.brandId}">${brand.name}</a></c:if></li></c:forEach>
				</ul>
			 </div>
		</div>
		<div style="text-align:right;"><%@ include file="/page/share/fenye2.jsp" %></div>
	<div class='goods_pic'>
<!---------------------------LOOP START------------------------------>
<s:iterator value="products" var="entry">	
        <div class="detail">
           <div class="goods"><a href="/front/product/detailShow?productId=${entry.productId }"><img title="点击查看详细信息" src="<s:property value="#entry.styles.{productImagePath}[0]" />" alt="${entry.name}" width="140" height="168"  border="0"/></a></div>
           <div><font color='#cc00cc'>${entry.name }</font></div>
           <div class="save_number"><s>￥${entry.marketPrice}</s>　<strong><em>￥${entry.salePrice}</em></strong><br>　节省：￥${entry.savedPrice}</div>
        </div>
</s:iterator>
<!----------------------LOOP END------------------------------->
		<div class='emptybox'></div>
	</div>
	<div style="text-align:right;"><%@ include file="/page/share/fenye2.jsp" %></div>
  </div>
    </oscache:cache>
	<jsp:include page="/page/share/Foot.jsp" />
	</form>
</body>
</html>