package com.example.demo.src.scholarship_comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ScholarshipCommentService {
    private final ScholarshipCommentDao scholarshipCommentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ScholarshipCommentService(ScholarshipCommentDao scholarshipCommentDao) {
        this.scholarshipCommentDao = scholarshipCommentDao;
    }


    /**
     * 댓글 작성 API
     */
    public PostScholarshipCommentRes createScholarshipComment(PostScholarshipCommentReq postScholarshipCommentReq) throws BaseException {
        try {
            long scholarship_comment_idx = scholarshipCommentDao.createScholarshipComment(postScholarshipCommentReq);
            return new PostScholarshipCommentRes(scholarship_comment_idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
