<%@ page pageEncoding="UTF-8"%>
<%@ include file="/page/share/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>有才网 图书搜索</title>
	<link href="/css/global/header01.css" rel="stylesheet" type="text/css" />
	<link href="/css/product/list.css" rel="stylesheet" type="text/css" />
	<link href="/css/global/topsell.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/js/jquery.js"></script>
<script>
$(function(){
  $("#scanHistory").html("正在加载...");
  $("#scanHistory").load("/front/product/findScanHistory");
});
</script>
</head>

<body class="ProducTypeHome2" onload='$("#index").css("background", "");'>
	<jsp:include page="/page/share/Head.jsp"/>
    <div id="position">您现在的位置: <a href="/" name="linkHome">有才网</a> &gt;&gt; <em>产品查询结果</em> （${pageCtx.totalRecords}个）
	</div>

    <!--页面左侧分类浏览部分-->
    <div class="browse_left">
		 <div class="browse">
	          <div class="browse_t">您最近浏览过的商品</div>
			  <ul id="scanHistory"></ul>
	     </div>
    </div>
    <!--页面右侧分类列表部分开始-->
    <div class="browse_right">
	     <div id="divNaviTop" class="number">
	          <div class="number_l">以下查询到<span class='number_white'>${pageCtx.totalRecords}</span>条结果　每页显示<span class="number_white">${pageCtx.maxResults}</span>条</div>
		      <div class="turnpage">
                <div><em>第${pageCtx.currentPage}页</em></div>
		      </div>
	     </div>
<!---------------------------LOOP START------------------------------>
<s:iterator value="products" var="entry">
		<div class="goodslist">
          <div class="goods"><a href="/front/product/detailShow?productId=${entry.productId }" target="_blank">
            <img title="点击查看详细信息" src='<s:property value="#entry.styles.{productImagePath}[0]" />' alt="${entry.name}" width="140" height="168"  border="0"/></a>
          </div>
          <div class="goods_right">
               <h2><a href="/front/product/detailShow?productId=${entry.productId }" target="_blank" title="点击查看详细信息">${entry.name} [<c:forEach items="${entry.styles}" var="style">${style.name},</c:forEach>...]</a>&nbsp;&nbsp;&nbsp;/*${entry.type.name }*/ <c:if test="${!empty entry.brand}">品牌：${entry.brand.name}</c:if></h2>
	           <div class="content"><c:out value="${fn:substring(entry.description,0,200) }" escapeXml="false"/></div>
          </div>
          <div class="empty_box"></div>
          <div class="message_bottom" style="text-align:right;">
			   <div class="buy"><s>￥${entry.marketPrice}</s>　<strong><em>￥${entry.salePrice}</em></strong>　节省：${entry.savedPrice}</div>
	      </div>
        </div>
</s:iterator>
<!----------------------LOOP END------------------------------->
	     <div id="divNaviBottom" class="page_number">
	     <div class="turnpage turnpage_bottom">
	     <c:if test="${pageCtx.pageList.startPage!=1}"><a href="topage('1')">首页</a></c:if>
	     <c:if test="${pageCtx.prePage!=0}"><a href="topage('${pageCtx.prePage}')">上一页</a></c:if>
	     <c:forEach begin="${pageCtx.pageList.startPage}" end="${pageCtx.pageList.endPage}" var="p">
           <c:if test="${pageCtx.currentPage==p}"><div class='red'>${p}</div></c:if>
           <c:if test="${pageCtx.currentPage!=p}"><div class="page"><a href="topage('${p}')">[${p}]</a></div></c:if>
         </c:forEach>
         <c:if test="${pageCtx.nextPage!=0}"><a href="topage('${pageCtx.nextPage}')">下一页</a></c:if>
         <c:if test="${pageCtx.pageList.endPage!=pageCtx.totalPages}"><a href="topage('${pageCtx.totalPages }')">尾页</a></c:if>
		<div>&nbsp;&nbsp;</div>跳转到第
		<select name="selectPage" class="kuang" onchange="javaScript:topage(this.value)">
				<c:forEach begin="1" end="${pageCtx.totalPages}" var="p">
				<option value="${p}" <c:if test="${pageCtx.currentPage==p}">selected</c:if>> ${p} </option></c:forEach>
		</select>页
		<script>
		<!--
		function topage(p){
			window.location.href='/front/product/search?pageCtx.currentPage='+p+"&word=${param.word }";
		}
		//-->
		</script>
         </div>
	     </div>
    </div>
	<jsp:include page="/page/share/Foot.jsp" />
</body>
</html>