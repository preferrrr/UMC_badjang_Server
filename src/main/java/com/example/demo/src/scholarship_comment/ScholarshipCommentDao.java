package com.example.demo.src.scholarship_comment;

import com.example.demo.src.scholarship_comment.model.GetScholarshipCommentRes;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ScholarshipCommentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    // 댓글 작성
    public long createScholarshipComment(PostScholarshipCommentReq postScholarshipCommentReq) {
        String createScholarshipCommentQuery = "insert into Scholarship_Comment(scholarship_idx, user_idx, scholarship_comment_content) VALUES (?,?,?)"; // 실행될 동적 쿼리문
        Object[] createScholarshipCommentParams = new Object[]{postScholarshipCommentReq.getScholarship_idx(), postScholarshipCommentReq.getUser_idx(), postScholarshipCommentReq.getScholarship_comment_content()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createScholarshipCommentQuery, createScholarshipCommentParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 BoardIdx번호를 반환한다.
    }
//
//
//
//    // 회원정보 변경
//    public int modifyBoard(PatchBoardReq patchBoardReq) {
//        String modifyBoardQuery = "update Board set title = ? ,content = ? where boardIdx = ? ";
//        Object[] modifyBoardParams = new Object[]{patchBoardReq.getTitle(),patchBoardReq.getContent(),patchBoardReq.getBoardIdx()}; // 주입될 값들(nickname, userIdx) 순
//
//        return this.jdbcTemplate.update(modifyBoardQuery, modifyBoardParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
//    }
//
//    public int deleteBoardByBoardIdx(DeleteBoardReq deleteBoardReq) {
//        String deleteBoardByBoardIdxQuery = "delete from Board where boardIdx = ";
//        Object[] deleteBoardByBoardIdxParams = new Object[]{deleteBoardReq.getBoardIdx()};
//
//        return this.jdbcTemplate.update(deleteBoardByBoardIdxQuery,deleteBoardByBoardIdxParams);
//
//    }
//
//
//
    // 전체 게시글 조회
//    public List<GetScholarshipCommentRes> getScholarshipComment(Long scholarship_idx) {
//        String getScholarshipCommentQuery = "select * from Scholarship_Comment where scholarship_idx = ?";
//        Long getScholarshipCommentParams = scholarship_idx;
//        return this.jdbcTemplate.query(getScholarshipCommentQuery,
//                (rs, rowNum) -> new GetScholarshipCommentRes(
//                        rs.getLong("scholarship_comment_idx"),
//                        rs.getLong("scholarship_idx"),
//                        rs.getLong("user_idx"),
//                        rs.getString("scholarship_comment_content"),
//                        rs.getString("scholarship_comment_updateAt")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
//                ,getScholarshipCommentParams
//        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
//    }
    //
    // userIdx를 통해 특정 유저가 쓴 게시글 모두 조회
    public List<GetScholarshipCommentRes> getScholarshipComment(Long scholarship_idx) {
        String getScholarshipCommentQuery = "select * from Scholarship_Comment where scholarship_idx = ?";
        Long getScholarshipCommentParams = scholarship_idx;
        return this.jdbcTemplate.query(getScholarshipCommentQuery,
                (rs, rowNum) -> new GetScholarshipCommentRes(
                        rs.getLong("scholarship_comment_idx"),
                        rs.getLong("scholarship_idx"),
                        rs.getLong("user_idx"),
                        rs.getString("scholarship_comment_content"),
                        rs.getString("scholarship_comment_updateAt")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getScholarshipCommentParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
//
//
    // boardIdx로 해당 게시글 한개 조회
//    public GetBoardRes getBoardByBoardIdx(long boardIdx) {
//        String getBoardByBoardIdxQuery = "select * from Board where boardIdx = ?";
//        long getBoardByBoardIdxParams = boardIdx;
//        return this.jdbcTemplate.queryForObject(getBoardByBoardIdxQuery, // 한개만 가져오면 queryForObject
//                (rs,rowNum) -> new GetBoardRes (
//                        rs.getLong("boardIdx"),
//                        rs.getLong("userIdx"),
//                        rs.getString("title"),
//                        rs.getString("content")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
//                getBoardByBoardIdxParams);
//    }

//
//
//
//    public List<GetBoardRes> getBoardListPaging(Criteria criteria) {
//        int pageStart = criteria.getPage();
//        //int perPageNum = pageMaker.getCriteria().getPerPageNum();
//        String getBoardListPagingQuery = "select * from Board order by boardIdx desc limit ?,5";
//        //int getBoardListPagingParams = pageMaker.getStartPage();
//        return this.jdbcTemplate.query(getBoardListPagingQuery, // 한개만 가져오면 queryForObject
//                (rs,rowNum) -> new GetBoardRes (
//                        rs.getLong("boardIdx"),
//                        rs.getLong("userIdx"),
//                        rs.getString("title"),
//                        rs.getString("content")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
//                pageStart);
//    }
}
