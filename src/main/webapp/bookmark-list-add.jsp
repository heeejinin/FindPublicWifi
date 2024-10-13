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
        String bmrkId = request.getParameter("gId");
        String mrgNo = request.getParameter("mrgNo");

        String message = null;
        int result = 0;

        // 파라미터 유효성 검사
        if (bmrkId == null || bmrkId.trim().isEmpty()) {
            System.out.println("북마크 ID: "+ bmrkId);
            message = "북마크 데이터 전달 오류";
        } else if (mrgNo == null || mrgNo.trim().isEmpty()) {
            System.out.println("WIFI 번호: "+ mrgNo);
            message = "와이파이 데이터 전달 오류";
        } else {
            try {
                BookmarkService service = new BookmarkService();
                result = service.insertBmrkWifi(bmrkId, mrgNo);
                message = result > 0 ? "북마크를 추가하였습니다." : "북마크 추가에 실패하였습니다.";
            } catch (Exception e) {
                message = "오류가 발생했습니다: " + e.getMessage();
            }
        }
    %>
</body>
<script>
    alert("<%= message %>");
    <% if (message.equals("북마크를 추가하였습니다.")) { %>
        location.href = "bookmark-list.jsp";
    <% } else { %>
        history.back();
    <% } %>
</script>
</html>