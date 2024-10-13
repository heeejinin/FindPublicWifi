package service;

import dto.BookmarkDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkService extends DBTemplate {
    public static Connection conn;
    public static PreparedStatement pstm;
    public static ResultSet rs;


    // 북마크 와이파이 추가
    public static int insertBmrkWifi(String bmrkId, String mrgNo) {
        conn = null;
        pstm = null;
        rs = null;
        int success = 0;

        BookmarkDTO bookmark = new BookmarkDTO();

        conn = getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
            return success;
        }

        // sql 쿼리문
        String sql = " INSERT INTO BOOKMARK_LIST (BMRK_ID, WIFI_NO, BMRK_NM, WIFI_NM, REG_DTTM) " +
                " SELECT  " +
                "    bg.BMRK_ID, w.X_SWIFI_MGR_NO, bg.BMRK_NM," +
                "    w.X_SWIFI_MAIN_NM, NOW() " +
                " FROM BOOKMARK_GROUP bg " +
                " CROSS JOIN WIFI w " +
                " WHERE bg.BMRK_ID = ? " +
                " AND w.X_SWIFI_MGR_NO = ?; ";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(bmrkId));
            pstm.setString(2, mrgNo);

            success = pstm.executeUpdate();

            if (success < 0) {
                System.out.println("북마크 테이블에 삽입할 데이터가 없습니다.");
            } else {
                System.out.println("북마크 테이블에 데이터 입력을 완료했습니다.");
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
        return success;
    }

    // 북마크 DTO 반환
    public static BookmarkDTO getBookmark(String id) {
        conn = null;
        pstm = null;
        rs = null;
        BookmarkDTO bookmark = new BookmarkDTO();


        //DB 연결
        conn = DBTemplate.getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
        }

        // 쿼리문
        String sql = " SELECT * FROM BOOKMARK_LIST WHERE ID =?; ";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(id));
            rs = pstm.executeQuery();

            // 결과 처리
            while (rs.next()) {
                bookmark.setId(rs.getInt("ID"));
                bookmark.setBmrkId(rs.getInt("BMRK_ID"));
                bookmark.setWifiNo(rs.getString("WIFI_NO"));
                bookmark.setBmrkNm(rs.getString("BMRK_NM"));
                bookmark.setWifiNm(rs.getString("WIFI_NM"));
                bookmark.setRegDttm(rs.getString("REG_DTTM"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }

        return bookmark;
    }


    // 북마크 리스트 반환
    public static List<BookmarkDTO> getBookmarkList() {
        conn = null;
        pstm = null;
        rs = null;
        List<BookmarkDTO> bookmarkList = new ArrayList<>();

        //DB 연결
        conn = DBTemplate.getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
        }

        // 쿼리문
        String sql = " SELECT * FROM BOOKMARK_LIST ORDER BY ID; ";

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            // 결과 처리
            while (rs.next()) {
                BookmarkDTO bookmark = new BookmarkDTO(
                        rs.getInt("ID")
                        , rs.getInt("BMRK_ID")
                        , rs.getString("WIFI_NO")
                        , rs.getString("BMRK_NM")
                        , rs.getString("WIFI_NM")
                        , rs.getString("REG_DTTM")
                );
                bookmarkList.add(bookmark);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }

        return bookmarkList;
    }

    // 북마크 그룹 삭제
    public static int deleteBookmark(String id) {
        conn = null;
        pstm = null;
        rs = null;
        int success = 0;

        conn = getConnection();

        String sql = "DELETE FROM BOOKMARK_LIST WHERE ID = ?; ";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(id));

            success = pstm.executeUpdate();

            if (success < 0) {
                System.out.println("삭제할 북마크가 없습니다.");
            } else {
                System.out.printf("%d번 북마크 삭제 완료\n", Integer.parseInt(id));
                conn.commit(); // 명시적인 커밋
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }
        return success;
    }
}
