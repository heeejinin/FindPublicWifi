package service;

import dto.HistoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryService extends DBTemplate {
    public static Connection conn;
    public static PreparedStatement pstm;
    public static ResultSet rs;


    // Insert History
    public static void insertHistory(String lat, String lnt) {
        conn = null;
        pstm = null;
        rs = null;
        HistoryDTO historyDTO = new HistoryDTO();

        conn = getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
            return; // 연결 실패 시 메서드를 종료
        }

        // sql 쿼리문
        String sql = "INSERT INTO history (LAT, LNT, VIEW_DTTM) "
                + " VALUES (?, ?, NOW()); ";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, lat);
            pstm.setString(2, lnt);

            int history = pstm.executeUpdate();

            if (history < 0) {
                System.out.println("히스토리 테이블에 삽입할 데이터가 없습니다.");
            } else {
                System.out.println("히스토리 테이블에 데이터 입력을 완료했습니다.");
                conn.commit(); // 명시적인 커밋
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } finally {
            DBTemplate.close(rs, pstm, conn);
        }
    }

    // 히스토리 조회
    public static List<HistoryDTO> getHistoryList() {
        conn = null;
        pstm = null;
        rs = null;
        List<HistoryDTO> historyList = new ArrayList<>();

        //DB 연결
        conn = DBTemplate.getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
        }

        // 쿼리문
        String sql = " SELECT * FROM HISTORY ORDER BY HISTORY_ID DESC; ";

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            // 결과 처리
            while (rs.next()) {
                HistoryDTO history = new HistoryDTO(
                        rs.getInt("HISTORY_ID")
                        , rs.getString("LAT")
                        , rs.getString("LNT")
                        , rs.getString("VIEW_DTTM")
                );

                historyList.add(history);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }

        return historyList;
    }

    // 히스토리 삭제
    public void deleteHistory(int id) {
        conn = null;
        pstm = null;
        rs = null;

        conn = getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
            return;
        }

        String sql = "DELETE FROM HISTORY WHERE HISTORY_ID = ? ";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);

            int history = pstm.executeUpdate();

            if (history < 0) {
                System.out.println("삭제할 데이터가 없습니다.");
            } else {
                System.out.printf("%d번 조회 이력 삭제 완료\n", id);
                conn.commit(); // 명시적인 커밋
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }
    }
}
