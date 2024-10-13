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
    <h1>북마크 그룹</h1>
    <a href="/">홈</a> |
    <a href="/history.jsp">위치 히스토리 목록</a> |
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="/bookmark-list.jsp">북마크 보기</a> |
    <a href="/bookmark-group.jsp">북마크 그룹 관리</a>
    </br></br>

    <button onclick="location.href = 'bookmark-group-add.jsp'">북마크 그룹 이름 추가</button>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>순서</th>
                <th>등록일자</th>
                <th>수정일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
        <%
            BookmarkGroupService service = new BookmarkGroupService();
            List<BookmarkGroupDTO> groupList = service.getBmrkGroupList();
            if(groupList == null && groupList.isEmpty()){
        %>
            <tr style="text-align: center;">
               <td colspan="100%">북마크 그룹이 없습니다.</td>
            </tr>
        <%
            }else{
                for(BookmarkGroupDTO group: groupList){
                String edit = group.getEditDttm() == null ? "" : group.getEditDttm();
        %>
        <tr>
            <td><%=group.getBmrkId()%></td>
            <td><%=group.getBmrkNm()%></td>
            <td><%=group.getSeq()%></td>
            <td><%=group.getRegDttm()%></td>
            <td><%=edit%></td>
            <td style="text-align: center;">
                <a href="/bookmark-group-edit.jsp?bmrkId=<%=group.getBmrkId()%>">수정</a>
                <a href="/bookmark-group-delete_submit.jsp?bmrkId=<%=group.getBmrkId()%>">삭제</a>
            </td>
        </tr>
        <% } } %>
        </tbody>
	</table>
</body>
</html>
