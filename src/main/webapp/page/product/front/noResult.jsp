<%@ page pageEncoding="gbk"%>
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

<body class="ProducTypeHome2" onload="javascript:pageInit()">
	<jsp:include page="/page/share/Head.jsp"/>
    <div id="position">您现在的位置: <a href="/" name="linkHome">有才网</a> &gt;&gt; <em>产品查询结果</em> （${pageCtx.totalRecords}个）
	</div>

    <!--页面左侧分类浏览部分-->
    <div class="browse_left">
		 <div class="browse">
	          <div class="browse_t">您浏览过的商品</div>
			  <ul id="scanHistory"></ul>
	     </div>
    </div>
    
    <div class="browse_right">
	     noRsult...
    </div>
	<jsp:include page="/page/share/Foot.jsp" />
</body>
</html>