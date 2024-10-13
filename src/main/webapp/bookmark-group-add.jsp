<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
    <h1>북마크 그룹 추가</h1>
    <a href="/">홈</a> |
    <a href="/history.jsp">위치 히스토리 목록</a> |
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="/bookmark-list.jsp">북마크 보기</a> |
    <a href="/bookmark-group.jsp">북마크 그룹 관리</a>

    <%
        String bmrkNm = request.getParameter("bmrkNm");
        String seq = request.getParameter("seq");

        if (bmrkNm==null || seq==null || bmrkNm.isEmpty() || seq.isEmpty()) { bmrkNm="" ; seq="" ; }

    %>
    <form method="post" action="bookmark-group-add_submit.jsp" >
        <table>
            <tr>
                <th>북마크 이름</th>
                <td>
                    <input type="text" id="bmrkNm" name="bmrkNm" value="<%=bmrkNm%>" />
                </td>
            </tr>
            <tr>
                <th>순서</th>
                <td>
                    <input type="text" id="seq" name="seq" value="<%=seq%>" />
                </td>
            </tr>
            <tr align="center">
                <td colspan="100%">
                    <button type="submit">추가</button>
                <td>
            </tr>
        </table>
	</form>
</body>
</html>