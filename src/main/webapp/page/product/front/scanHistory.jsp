<%@ page pageEncoding="UTF-8" %>
<%@ include file="/page/share/taglib.jsp" %>
<UL>
	<c:forEach items="${history}" var="p" varStatus="s">
		<LI class="bx">${s.count}.<a href="/front/product/detailShow?productId=${p.productId}" target="_blank" >${p.name}</a></LI></c:forEach>			
</UL>