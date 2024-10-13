<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.WifiDTO" %>
<%@ page import="service.WifiService" %>

<html>

<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>

<body>
    <h1>와이파이 정보 구하기</h1>
    <a href="/">홈</a> |
    <a href="/history.jsp">위치 히스토리 목록</a> |
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
    </br></br>

    <%
        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");
        if (lat==null || lnt==null || lat.isEmpty() || lnt.isEmpty()) { lat="0.0" ; lnt="0.0" ; }
    %>

    <label>
        LAT: <input type="text" id="lat" name="lat" value="<%=lat%>" />
    </label>,
    <label>
        LNT: <input type="text" id="lnt" name="lnt" value="<%=lnt%>" />
    </label>

    <button type="button" onclick="getLocation()">내 위치 가져오기</button>
    <button type="button" onclick="getNearestWifi()">근처 WIFI 정보 보기</button>
    </br></br>

    <table>
        <thead>
            <tr>
                <th>거리(Km)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명주소</th>
                <th>상세주소</th>
                <th>설치위치(층)</th>
                <th>설치유형</th>
                <th>설치기관</th>
                <th>서비스구분</th>
                <th>망종류</th>
                <th>설치년도</th>
                <th>실내외구분</th>
                <th>WIFI접속환경</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>작업일자</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (!lat.equals("0.0") && !lnt.equals("0.0")) { try {
                WifiService wifiService = new WifiService();
                List<WifiDTO> list = wifiService.getNearestWifi(lat, lnt);

                if (list != null && !list.isEmpty()) {
                for (WifiDTO wifi : list) {
            %>
            <tr>
                <td>
                    <%=wifi.getDistance()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_MGR_NO()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_WRDOFC()%>
                </td>
                <td>
                    <a
                        href="detail_wifi.jsp?mrgNo=<%=wifi.getX_SWIFI_MGR_NO()%>&distance=<%=wifi.getDistance()%>">
                        <%=wifi.getX_SWIFI_MAIN_NM()%>
                    </a>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_ADRES1()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_ADRES2()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_INSTL_FLOOR()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_INSTL_TY()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_INSTL_MBY()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_SVC_SE()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_CMCWR()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_CNSTC_YEAR()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_INOUT_DOOR()%>
                </td>
                <td>
                    <%=wifi.getX_SWIFI_REMARS3()%>
                </td>
                <td>
                    <%=wifi.getLAT()%>
                </td>
                <td>
                    <%=wifi.getLNT()%>
                </td>
                <td>
                    <%=wifi.getWORK_DTTM()%>
                </td>
            </tr>
            <% } } else { %>
                <tr style="text-align: center;">
                    <td colspan="100%">근처 와이파이 정보를 찾을 수 없습니다.</td>
                </tr>
                <% } } catch (Exception e) { %>
                    <tr style="text-align: center;">
                        <td colspan="100%">오류가 발생했습니다: <%=e.getMessage()%>
                        </td>
                    </tr>
                    <% } } else { %>
                        <tr style="text-align: center;">
                            <td colspan="100%">위치 정보를 입력한 후 조회해주세요.</td>
                        </tr>
                    <% } %>
        </tbody>
    </table>
</body>

<script>
    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                document.getElementById('lat').value = position.coords.latitude;
                document.getElementById('lnt').value = position.coords.longitude;
            });
        } else {
            alert("위치 정보를 가져올 수 없습니다. \n 위치정보를 직접 입력해 주세요.");
        }
    }

    function getNearestWifi() {
        let lat = document.getElementById("lat").value;
        let lnt = document.getElementById("lnt").value;

        if (lat !== "" && lnt !== "") {
            window.location.assign("http://localhost:8080?lat=" + lat + "&lnt=" + lnt);
        } else {
            alert("위치 정보를 입력하신 후에 조회해주세요.")
        }
    }
</script>

</html>