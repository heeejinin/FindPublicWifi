<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dto.BookmarkDTO" %>
<%@ page import="service.BookmarkService" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
    <h1>북마크 삭제</h1>
    <a href="/">홈</a> |
    <a href="/history.jsp">위치 히스토리 목록</a> |
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="/bookmark-list.jsp">북마크 보기</a> |
    <a href="/bookmark-group.jsp">북마크 그룹 관리</a>
    </br></br>
    <p>북마크를 삭제하시겠습니까?</p>

    <%
        String id = request.getParameter("id");

        BookmarkService service = new BookmarkService();
        BookmarkDTO bookmark = service.getBookmark(id);
    %>

    <form method="post" action="bookmark-delete_submit.jsp" >
    <table>
        <tr>
            <th>북마크 이름</th>
            <td><%=bookmark.getBmrkNm()%></td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td><%=bookmark.getWifiNm()%></td>
        </tr>
        <tr>
            <th>등록일자</th>
            <td><%=bookmark.getRegDttm()%></td>
        </tr>
        <tr align="center">
            <td colspan="100%">
                <a href="/bookmark-list.jsp">돌아가기<a> | <button type="submit">삭제</button>
                <input type="hidden" name="id" value="<%=bookmark.getId()%>">
            </td>
        </tr>
    </table>
</body>
</html>
