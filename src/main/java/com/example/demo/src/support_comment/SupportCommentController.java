package com.example.demo.src.support_comment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentReq;
import com.example.demo.src.scholarship_comment.model.PostScholarshipCommentRes;
import com.example.demo.src.support_comment.model.GetSupportCommentRes;
import com.example.demo.src.support_comment.model.PostSupportCommentReq;
import com.example.demo.src.support_comment.model.PostSupportCommentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.POST_COMMENT_EMPTY_CONTENT;

@RestController
@RequestMapping("/support/comment")
public class SupportCommentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SupportCommentProvider supportCommentProvider;
    @Autowired
    private final SupportCommentService supportCommentService;

    public SupportCommentController(SupportCommentProvider supportCommentProvider, SupportCommentService supportCommentService){
        this.supportCommentProvider = supportCommentProvider;
        this.supportCommentService = supportCommentService;
    }

    /**
     * 지원금 댓글 조회 API
     * [GET] /support/comment?support_idx
     * @return BaseResponse<List<GetSupportCommentRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetSupportCommentRes>> getSupportComment(@RequestParam(required = true) Long support_idx) {
        try{
            // Get Users
            List<GetSupportCommentRes> getSupportCommentRes = supportCommentProvider.getSupportComment(support_idx);
            return new BaseResponse<>(getSupportCommentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 댓글 작성 API
     * [POST] /new-comment
     */
    // Body
    @ResponseBody
    @PostMapping("/new-comment")
    public BaseResponse<PostSupportCommentRes> createSupportComment(@RequestBody PostSupportCommentReq postSupportCommentReq) {
        if (postSupportCommentReq.getSupport_comment_content() == null) {
            return new BaseResponse<>(POST_COMMENT_EMPTY_CONTENT);
        }
        try {
            PostSupportCommentRes postSupportCommentRes = supportCommentService.createSupportComment(postSupportCommentReq);
            return new BaseResponse<>(postSupportCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}