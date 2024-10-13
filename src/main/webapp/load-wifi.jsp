<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="service.APIService" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <%
        // 와이파이 개수
        APIService apiService = new APIService();
        int count = apiService.getPublicWifiJson();
    %>
    <div>
        <%
            //와이파이 개수 화면
            if (count > 0) {
        %>
            <div style="text-align: center;">
                <h1 style="margin: 20px 0"><%=count%>개의 데이터를 성공적으로 저장했습니다.</h1>
                <a href="http://localhost:8080">홈으로 돌아가기</a>
            </div>
        <% } else { %>
            <h1 style="text-align: center">데이터 저장 실패</h1>
        <% } %>
    </div>

</body>
</html>