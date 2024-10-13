<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dto.BookmarkGroupDTO" %>
<%@ page import="service.BookmarkGroupService" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>

<body>
    <h1>북마크 그룹 수정</h1>
    <a href="/">홈</a> |
    <a href="/history.jsp">위치 히스토리 목록</a> |
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="/bookmark-list.jsp">북마크 보기</a> |
    <a href="/bookmark-group.jsp">북마크 그룹 관리</a>

    <%
        String bmrkId = request.getParameter("bmrkId");

        BookmarkGroupService service = new BookmarkGroupService();
        BookmarkGroupDTO group = service.selectBmrkGroup(bmrkId);
      %>

    <form method="post" action="bookmark-group-edit_submit.jsp" >
        <table>
            <tr>
                <th>북마크 이름</th>
                <td>
                    <input type="text" id="bmrkNm" name="bmrkNm" value="<%=group.getBmrkNm()%>" />
                </td>
            </tr>
            <tr>
                <th>순서</th>
                <td>
                    <input type="text" id="seq" name="seq" value="<%=group.getSeq()%>" />
                </td>
            </tr>
            <td colspan="100%" align="center">
                <a href="/bookmark-group.jsp">돌아가기<a> | <button type="submit">수정</button>
                <input type="hidden" name="bmrkId" value="<%=group.getBmrkId()%>">
            </td>
        </table>
	</form>
</body>
</html>
