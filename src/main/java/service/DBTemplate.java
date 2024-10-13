package service;

import java.sql.*;

//참고 블로그
//https://datamoney.tistory.com/125
//https://velog.io/@sedo11/%EC%9D%B8%ED%85%94%EB%A6%AC%EC%A0%9C%EC%9D%B4-db%EC%97%B0%EB%8F%99
public class DBTemplate {

    public static Connection getConnection() {
        String driver = "org.mariadb.jdbc.Driver";
        String url = "jdbc:mariadb://localhost:3306/public_wifi";
        String dbUserId = "root";
        String dbPassword = "root";

        //1. 드라이버 로드
        try {
            //드라이버 연결
            Class.forName(driver); // Class.forName()는 예외 처리 set임
        } catch (ClassNotFoundException e) {
            //드라이버 연결실패
            e.printStackTrace();
        }


        //2. 커넥션 객체 생성
        Connection conn = null;

        try {
            //계정 연결
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);
            conn.setAutoCommit(false); //Auto-Commit 해제
        } catch (SQLException e) {
            //계정 연결 실패
            throw new RuntimeException(e);
        }

        return conn;
    }

    //6. 객체 연결 해제(close)
    public static void close(ResultSet rs, PreparedStatement pstm, Connection conn) {
        //rs가 null 아닐때 close
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //stmt가 null 아닐때 close
        try {
            if (pstm != null && !pstm.isClosed()) {
                pstm.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //연결된 상태인지 아닌지 확인 ->연결 상태 확인되면 close
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}