<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.WifiDTO" %>
<%@ page import="service.WifiService" %>
<%@ page import="dto.BookmarkGroupDTO" %>
<%@ page import="service.BookmarkGroupService" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
    <h1>와이파이 정보 구하기</h1>
    <a href="/">홈</a> |
    <a href="/history.jsp">위치 히스토리 목록</a> |
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="/bookmark-list.jsp">북마크 보기</a> |
    <a href="/bookmark-group.jsp">북마크 그룹 관리</a>
    </br></br>
    <%
        // 전달받은 매개변수 처리
        String mrgNo = request.getParameter("mrgNo");
        String distance = request.getParameter("distance");

        // 북마크에서 보는 경우 거리 전달 X
        if (distance == null) { distance="-"; }

        WifiService wifiService = new WifiService();
        List<WifiDTO> wifiList = wifiService.getWifiInfo(mrgNo);

        BookmarkGroupService bmrkService = new BookmarkGroupService();
        List<BookmarkGroupDTO> bmrkList = bmrkService.getBmrkGroupList();
    %>

    <form method="post" action="bookmark-list-add.jsp" id="bmrkList">
        <select name="gId">
            <option value="none" selected>북마크 그룹 이름 선택</option>
            <% for(BookmarkGroupDTO bookmark : bmrkList) { %>
            <option id="bmrkId" value="<%=bookmark.getBmrkId()%>">
                <%=bookmark.getBmrkNm()%>
            </option>
            <% } %>
        </select>
        <input type="submit" value="북마크 추가하기">
        <input type="hidden" id="mrgNo" name="mrgNo" value="<%=mrgNo%>">
    </form>

    <table>
        <tr>
            <th>거리(km)</th>
            <td><%=distance%></td>
        </tr>
        <%
            // wifiList가 비어있지 않은 경우
            if (!wifiList.isEmpty()) {
                for (WifiDTO wifi : wifiList) {
        %>
        <tr>
            <th>관리번호</th>
            <td><%=wifi.getX_SWIFI_MGR_NO()%></td>
        </tr>

        <tr>
            <th>자치구</th>
            <td><%=wifi.getX_SWIFI_WRDOFC()%></td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td><%=wifi.getX_SWIFI_MAIN_NM()%></td>
        </tr>

        <tr>
            <th>도로명 주소</th>
            <td><%=wifi.getX_SWIFI_ADRES1()%></td>
        </tr>
        <tr>
            <th>상세 주소</th>
            <td><%=wifi.getX_SWIFI_ADRES2()%></td>
        </tr>
        <tr>
            <th>설치 위치(층)</th>
            <td><%=wifi.getX_SWIFI_INSTL_FLOOR()%></td>
        </tr>
        <tr>
            <th>설치 유형</th>
            <td><%=wifi.getX_SWIFI_INSTL_TY()%></td>
        </tr>
        <tr>
            <th>설치 기관</th>
            <td><%=wifi.getX_SWIFI_INSTL_MBY()%></td>
        </tr>
        <tr>
            <th>서비스 구분</th>
            <td><%=wifi.getX_SWIFI_SVC_SE()%></td>
        </tr>
        <tr>
            <th>망 종류</th>
            <td><%=wifi.getX_SWIFI_CMCWR()%></td>
        </tr>
        <tr>
            <th>설치 년도</th>
            <td><%=wifi.getX_SWIFI_CNSTC_YEAR()%></td>
        </tr>
        <tr>
            <th>실내 외 구분</th>
            <td><%=wifi.getX_SWIFI_INOUT_DOOR()%></td>
        </tr>
        <tr>
            <th>WIFI 접속 환경</th>
            <td><%=wifi.getX_SWIFI_REMARS3()%></td>
        </tr>
        <tr>
            <th>x좌표</th>
            <td><%=wifi.getLAT()%></td>
        </tr>
        <tr>
            <th>y좌표</th>
            <td><%=wifi.getLNT()%></td>
        </tr>
        <tr>
            <th>작업일자</th>
            <td><%=wifi.getWORK_DTTM()%></td>
        </tr>

        <%
                }
            } else {
        %>
        <tr style="text-align: center;">
            <td colspan="100%">와이파이 정보를 찾을 수 없습니다.</td>
        </tr>
        <% } %>
    </table>
</body>
</html>