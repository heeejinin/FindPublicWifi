<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="service.BookmarkService" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
    <%
        // Post 방식 한글 인코딩 처리
        request.setCharacterEncoding("UTF-8");

        // 전달받은 매개변수 처리
        String id = request.getParameter("id");

        String message = null;
        int result = 0;

        try {
            BookmarkService service = new BookmarkService();
            result = service.deleteBookmark(id);
            message = result > 0 ? "북마크를 삭제했습니다." : "북마크 삭제에 실패했습니다.";
        } catch (Exception e) {
            message = "오류가 발생했습니다: " + e.getMessage();
        }
    %>
</body>
<script>
    alert("<%= message %>");
    <% if (message.equals("북마크를 삭제했습니다.")) { %>
        location.href = "bookmark-list.jsp";
    <% } else { %>
        history.back();
    <% } %>
</script>
</html>