package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dto.WifiDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static service.HistoryService.insertHistory;

public class WifiService extends DBTemplate {
    public static Connection conn;
    public static PreparedStatement pstm;
    public static ResultSet rs;

    // index.jsp
    // Insert Wifi
    public static int insertWifi(JsonArray jsonArray) throws SQLException {
        conn = null;
        pstm = null;
        rs = null;
        int count = 0;

        // DB 연결
        conn = getConnection();
        if (conn == null) {
            throw new SQLException("데이터베이스 연결 실패");
        }

        // sql 쿼리문
        String sql = " insert into wifi " +
                " (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, " +
                " X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, " +
                " X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) " +
                " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";


        try {
            pstm = conn.prepareStatement(sql);

            // insert 내용
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject wifi = (JsonObject) jsonArray.get(i).getAsJsonObject();

                pstm.setString(1, wifi.get("X_SWIFI_MGR_NO").getAsString());
                pstm.setString(2, wifi.get("X_SWIFI_WRDOFC").getAsString());
                pstm.setString(3, wifi.get("X_SWIFI_MAIN_NM").getAsString());
                pstm.setString(4, wifi.get("X_SWIFI_ADRES1").getAsString());
                pstm.setString(5, wifi.get("X_SWIFI_ADRES2").getAsString());
                pstm.setString(6, wifi.get("X_SWIFI_INSTL_FLOOR").getAsString());
                pstm.setString(7, wifi.get("X_SWIFI_INSTL_TY").getAsString());
                pstm.setString(8, wifi.get("X_SWIFI_INSTL_MBY").getAsString());
                pstm.setString(9, wifi.get("X_SWIFI_SVC_SE").getAsString());
                pstm.setString(10, wifi.get("X_SWIFI_CMCWR").getAsString());
                pstm.setString(11, wifi.get("X_SWIFI_CNSTC_YEAR").getAsString());
                pstm.setString(12, wifi.get("X_SWIFI_INOUT_DOOR").getAsString());
                pstm.setString(13, wifi.get("X_SWIFI_REMARS3").getAsString());
                pstm.setString(14, wifi.get("LAT").getAsString());
                pstm.setString(15, wifi.get("LNT").getAsString());
                pstm.setString(16, wifi.get("WORK_DTTM").getAsString());

                pstm.addBatch(); //쿼리를 메모리에 쌓아둠 (이때 실제로 DB에 쿼리가 실행되지 않음)
                pstm.clearParameters(); //파라미터를 초기화하여, 다음 데이터를 삽입할 준비

                if ((i + 1) % 1000 == 0) {
                    int[] result = pstm.executeBatch(); // 1000개 단위로 배치 실행
                    count += result.length;
                    conn.commit();   // 커밋 후 데이터베이스에 최종 반영
                }
            }

            // 반복문을 빠져나온 후에도 남아 있는 데이터 배치 실행
            int[] result = pstm.executeBatch();
            count += result.length; // 남은 배치 데이터 처리
            conn.commit(); // 마지막 남은 데이터를 커밋

        } catch (SQLException e) {
            e.printStackTrace();

            try {
                conn.rollback(); // 오류 발생 시 rollback
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }

        return count; //24567
    }


    // 내 위치와 가까운 Wi-Fi 20개 가져오는 함수
    public static List<WifiDTO> getNearestWifi(String userLat, String userLnt) {
        conn = null;
        pstm = null;
        rs = null;
        List<WifiDTO> wifiList = new ArrayList<>();

        // 히스토리 추가
        insertHistory(userLat, userLnt);

        //DB 연결
        conn = getConnection();

        // Haversine 공식이 적용된 거리 계산 쿼리
        String sql = " SELECT *, " +
                " round(6371 * acos(cos(radians(?)) * cos(radians(LAT)) * cos(radians(LNT) " +
                " -radians(?)) + sin(radians(?)) * sin(radians(LAT))), 4) " +
                " AS DISTANCE " +
                " FROM WIFI " +
                " ORDER BY DISTANCE " +
                " LIMIT 20; ";

        try {
            pstm = conn.prepareStatement(sql);

            // 위도 경도 값을 double로 변환 후 쿼리에 바인딩
            pstm.setDouble(1, Double.parseDouble(userLat));
            pstm.setDouble(2, Double.parseDouble(userLnt));
            pstm.setDouble(3, Double.parseDouble(userLat));

            rs = pstm.executeQuery();

            // 결과 처리
            while (rs.next()) {
                WifiDTO wifi = WifiDTO.builder()
                        .distance(rs.getDouble("DISTANCE"))
                        .X_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"))
                        .X_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"))
                        .X_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"))
                        .X_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"))
                        .X_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"))
                        .X_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"))
                        .X_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"))
                        .X_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"))
                        .X_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"))
                        .X_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"))
                        .X_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"))
                        .X_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"))
                        .X_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"))
                        .LAT(rs.getString("LAT"))
                        .LNT(rs.getString("LNT"))
                        .WORK_DTTM(rs.getString("WORK_DTTM"))
                        .build();

                wifiList.add(wifi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }

        return wifiList;
    }

    // ============================= detail_wifi.jsp =============================
    // wifi_Info 조회
    public static List<WifiDTO> getWifiInfo(String mrgNo) {
        conn = null;
        pstm = null;
        rs = null;
        List<WifiDTO> wifiInfo = new ArrayList<>();

        //DB 연결
        conn = getConnection();

        // select 쿼리
        String sql = " SELECT * FROM WIFI WHERE X_SWIFI_MGR_NO = ?; ";

        // 스테이트먼트 객체 생성
        try {
            pstm = conn.prepareStatement(sql);

            // 위도 경도 값을 double로 변환 후 쿼리에 바인딩
            pstm.setString(1, mrgNo);


            rs = pstm.executeQuery();


            // 결과 처리
            while (rs.next()) {
                WifiDTO wifi = WifiDTO.builder()
                        .X_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"))
                        .X_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"))
                        .X_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"))
                        .X_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"))
                        .X_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"))
                        .X_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"))
                        .X_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"))
                        .X_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"))
                        .X_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"))
                        .X_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"))
                        .X_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"))
                        .X_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"))
                        .X_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"))
                        .LAT(rs.getString("LAT"))
                        .LNT(rs.getString("LNT"))
                        .WORK_DTTM(rs.getString("WORK_DTTM"))
                        .build();

                wifiInfo.add(wifi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }

        return wifiInfo;
    }
}