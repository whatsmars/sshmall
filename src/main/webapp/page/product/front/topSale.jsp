<%@ page pageEncoding="gbk" %>
<%@ include file="/page/share/taglib.jsp" %>
<UL>
	<c:forEach items="${topSale}" var="t" varStatus="s">
		<LI class="bx">${s.count}.<a href="/front/product/detailShow?productId=${t.productId}" target="_blank" >${t.name}</a></LI></c:forEach>			
</UL>