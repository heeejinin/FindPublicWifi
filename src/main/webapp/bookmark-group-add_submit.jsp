<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="service.BookmarkGroupService" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
    <%
        // Post 방식 한글 인코딩 처리
        request.setCharacterEncoding("UTF-8");

        // 전달받은 매개변수 처리
        String bmrkNm = request.getParameter("bmrkNm");
        String seq = request.getParameter("seq");

        String message = null;
        int result = 0;

        // 파라미터 유효성 검사
        if (bmrkNm == null || bmrkNm.trim().isEmpty()) {
            message = "북마크 이름을 입력해 주세요.";
        } else if (seq == null || seq.trim().isEmpty()) {
            message = "순서를 입력해 주세요.";
        } else {
            try {
                BookmarkGroupService service = new BookmarkGroupService();
                result = service.insertBmrkGroup(bmrkNm, seq);
                message = result > 0 ? "북마크 그룹 추가에 성공하였습니다." : "북마크 그룹 추가에 실패하였습니다..";
            } catch (Exception e) {
                message = "오류가 발생했습니다: " + e.getMessage();
            }
        }
    %>
</body>
<script>
    alert("<%= message %>");
    <% if (message.equals("북마크 그룹 추가에 성공하였습니다.")) { %>
        location.href = "bookmark-group.jsp";
    <% } else { %>
        history.back();
    <% } %>
</script>
</html>