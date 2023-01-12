package com.example.demo.src.support_comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.scholarship_comment.ScholarshipCommentDao;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentRes;
import com.example.demo.src.support_comment.model.PostSupportCommentReq;
import com.example.demo.src.support_comment.model.PostSupportCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class SupportCommentService {
    private final SupportCommentDao supportCommentDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SupportCommentService(SupportCommentDao supportCommentDao) {
        this.supportCommentDao = supportCommentDao;
    }


    /**
     * 댓글 작성 API
     */
    public PostSupportCommentRes createSupportComment(PostSupportCommentReq postSupportCommentReq) throws BaseException {
        try {
            long support_comment_idx = supportCommentDao.createSupportComment(postSupportCommentReq);
            return new PostSupportCommentRes(support_comment_idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
