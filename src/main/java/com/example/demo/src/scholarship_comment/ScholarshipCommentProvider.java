package com.example.demo.src.scholarship_comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.scholarship_comment.model.GetScholarshipCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ScholarshipCommentProvider {
    private final ScholarshipCommentDao scholarshipCommentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public ScholarshipCommentProvider(ScholarshipCommentDao scholarshipCommentDao) {
        this.scholarshipCommentDao = scholarshipCommentDao;
    }
    // ******************************************************************************
//
//    // 해당 이메일이 이미 User Table에 존재하는지 확인
//    public int checkEmail(String email) throws BaseException {
//        try {
//            return userDao.checkEmail(email);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


    // User들의 정보를 조회
    public List<GetScholarshipCommentRes> getScholarshipComment(Long scholarship_idx) throws BaseException {
        try {
            List<GetScholarshipCommentRes> getScholarshipCommentRes = scholarshipCommentDao.getScholarshipComment(scholarship_idx);
            return getScholarshipCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    // 해당 nickname을 갖는 User들의 정보 조회
//    public List<GetScholarshipRes> getScholarshipByIdx(Long scholarship_idx) throws BaseException {
//        try {
//            List<GetScholarshipRes> getScholarshipRes = scholarshipDao.getScholarshipByIdx(scholarship_idx);
//            return getScholarshipRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


//    // 해당 userIdx를 갖는 User의 정보 조회
//    public GetUserRes getUser(int userIdx) throws BaseException {
//        try {
//            GetUserRes getUserRes = userDao.getUser(userIdx);
//            return getUserRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
