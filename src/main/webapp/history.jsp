<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dto.HistoryDTO" %>
<%@ page import="service.HistoryService" %>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>

<!DOCTYPE html>
<html>
<head>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
	<h1>위치 히스토리 목록</h1>
	<a href = "/">홈</a> |
	<a href="/history.jsp">위치 히스토리 목록</a> |
	<a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
	</br></br>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<%
			    HistoryService service = new HistoryService();
                List<HistoryDTO> historyList = service.getHistoryList();

                String historyId = request.getParameter("id");
                if (historyId != null) {
                    int hId = Integer.parseInt(historyId);
                    service.deleteHistory(hId);
                }

			    if(historyList == null && historyList.isEmpty()){
            %>
			<tr style="text-align: center;">
               <td colspan="100%">조회하신 와이파이 이력이 없습니다.</td>
            </tr>

			<%
			    }else{
				    for(HistoryDTO history: historyList){
			%>
			<tr>
               <td><%=history.getHistoryId()%></td>
               <td><%=history.getLat()%></td>
               <td><%=history.getLnt()%></td>
               <td><%=history.getViewDttm()%></td>
               <td style="text-align: center;">
                    <button onclick="deleteHistory(<%=history.getHistoryId()%>)">삭제</button>
               </td>
            </tr>
            <% }} %>
		</tbody>
	</table>
</body>
<script>
    function deleteHistory(ID) {
        if (confirm(ID +"데이터를 삭제하시겠습니까?")) {
            $.ajax({
                url: "http://localhost:8080/history.jsp",
                data: {id : ID},
                success: function () {
                    location.reload();
                },
                error: function (request, status, error) {
                    alert("code: " + request.status + "\n"+ "message: " + request.responseText + "\n" + "error: " + error);
                }
            })
        }
    }
</script>
</html>