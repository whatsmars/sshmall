<%@ page pageEncoding="UTF-8"%>
<!-- 仿优酷评论分页1---分页特点：当前页码总是位于页面列表的中间
     总记录数：80条 | 每页显示：8条 | 总页数：10  1 2 3 4 5 6 7 8 9 10 -->
<font color="#FFFFFF">
总记录数:${pageCtx.totalRecords}条 | 每页显示:${pageCtx.maxResults}条 | 总页数:${pageCtx.totalPages}页</font>　
<c:forEach begin="${pageCtx.pageList.startPage}" end="${pageCtx.pageList.endPage}" var="p">
    <c:if test="${pageCtx.currentPage==p}"><b><font color="#FFFFFF">${p} </font></b></c:if>
    <c:if test="${pageCtx.currentPage!=p}"><a href="javascript:toPage('${p}')" class="a03">[${p}] </a></c:if>
</c:forEach>


