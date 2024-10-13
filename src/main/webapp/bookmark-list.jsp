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
    <h1>북마크 목록</h1>
    <a href="/">홈</a> |
    <a href="/history.jsp">위치 히스토리 목록</a> |
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="/bookmark-list.jsp">북마크 보기</a> |
    <a href="/bookmark-group.jsp">북마크 그룹 관리</a>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>와이파이명</th>
                <th>등록일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
        <%

            BookmarkService service = new BookmarkService();
            List<BookmarkDTO> list = service.getBookmarkList();

            if (list == null && list.isEmpty()) {
        %>
            <tr style="text-align: center;">
               <td colspan="100%">북마크가 존재하지 않습니다.</td>
            </tr>
        <%
            } else {
                for (BookmarkDTO bookmark : list) {
        %>
            <tr>
                <td><%=bookmark.getBmrkId()%></td>
                <td><%=bookmark.getBmrkNm()%></td>
                <td>
                    <a href="detail_wifi.jsp?mrgNo=<%=bookmark.getWifiNo()%>">
                    <%=bookmark.getWifiNm()%></td>
                <td><%=bookmark.getRegDttm()%></td>
                <td style="text-align: center;">
                    <a href="/bookmark-delete.jsp?id=<%=bookmark.getId()%>">삭제</a>
                </td>
            </tr>
        <% } } %>
        </tbody>
	</table>
</body>
</html>
