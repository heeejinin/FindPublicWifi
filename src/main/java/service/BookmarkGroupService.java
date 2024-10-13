package service;

import dto.BookmarkGroupDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupService extends DBTemplate {
    public static Connection conn;
    public static PreparedStatement pstm;
    public static ResultSet rs;


    // 북마크 그룹 추가
    public static int insertBmrkGroup(String bmrkNm, String seq) {
        conn = null;
        pstm = null;
        rs = null;
        int success = 0;

        BookmarkGroupDTO groupDTO = new BookmarkGroupDTO();

        conn = getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
            return success;
        }

        // sql 쿼리문
        String sql = "INSERT INTO BOOKMARK_GROUP (BMRK_NM, SEQ, REG_DTTM) "
                + " VALUES (?, ?, NOW()); ";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, bmrkNm);
            pstm.setInt(2, Integer.parseInt(seq));

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

    // 북마크 그룹 조회
    public static BookmarkGroupDTO selectBmrkGroup(String id) {
        conn = null;
        pstm = null;
        rs = null;

        BookmarkGroupDTO group = new BookmarkGroupDTO();

        //DB 연결
        conn = DBTemplate.getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
        }

        // 쿼리문
        String sql = " SELECT * FROM BOOKMARK_GROUP WHERE BMRK_ID =?; ";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(id));

            rs = pstm.executeQuery();

            // 결과 처리
            while (rs.next()) {
                group.setBmrkId(rs.getInt("BMRK_ID"));
                group.setBmrkNm(rs.getString("BMRK_NM"));
                group.setSeq(rs.getInt("SEQ"));
                group.setRegDttm(rs.getString("REG_DTTM"));
                group.setEditDttm(rs.getString("EDIT_DTTM"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }

        return group;
    }

    // 북마크 그룹 리스트 반환
    public static List<BookmarkGroupDTO> getBmrkGroupList() {
        conn = null;
        pstm = null;
        rs = null;
        List<BookmarkGroupDTO> groupList = new ArrayList<>();

        //DB 연결
        conn = DBTemplate.getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
        }

        // 쿼리문
        String sql = " SELECT * FROM BOOKMARK_GROUP ORDER BY SEQ; ";

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            // 결과 처리
            while (rs.next()) {
                BookmarkGroupDTO group = new BookmarkGroupDTO(
                        rs.getInt("BMRK_ID")
                        , rs.getString("BMRK_NM")
                        , rs.getInt("SEQ")
                        , rs.getString("REG_DTTM")
                        , rs.getString("EDIT_DTTM")
                );
                groupList.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBTemplate.close(rs, pstm, conn); // 자원 해제
        }

        return groupList;
    }


    // 북마크 그룹 수정
    public static int updateBmrkGroup(String bmrkId, String bmrkNm, String seq) {
        conn = null;
        pstm = null;
        rs = null;
        int success = 0;

        BookmarkGroupDTO groupDTO = new BookmarkGroupDTO();

        conn = getConnection();
        if (conn == null) {
            System.out.println("DB 연결 실패");
            return success;
        }

        // sql 쿼리문
        String sql = "UPDATE BOOKMARK_GROUP SET BMRK_NM = ?, SEQ = ?, EDIT_DTTM = NOW() "
                + " WHERE BMRK_ID = ?; ";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, bmrkNm);
            pstm.setInt(2, Integer.parseInt(seq));
            pstm.setInt(3, Integer.parseInt(bmrkId));

            success = pstm.executeUpdate();

            if (success < 0) {
                System.out.println("북마크 데이블에 수정할 데이터가 없습니다.");
            } else {
                System.out.println("북마크 테이블에 데이터 수정을 완료했습니다.");
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

    // 북마크 그룹 삭제
    public static int deleteBmrkGroup(String id) {
        conn = null;
        pstm = null;
        rs = null;
        int success = 0;

        conn = getConnection();

        String sql = "DELETE FROM BOOKMARK_GROUP WHERE BMRK_ID = ?; ";

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(id));

            success = pstm.executeUpdate();

            if (success < 0) {
                System.out.println("삭제할 북마크 그룹 데이터가 없습니다.");
            } else {
                System.out.printf("%d번 북마크 그룹 삭제 완료\n", Integer.parseInt(id));
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
