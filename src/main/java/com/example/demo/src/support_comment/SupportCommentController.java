package com.example.demo.src.support_comment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.support_comment.model.*;
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
    @GetMapping("")
    public BaseResponse<List<GetSupportCommentRes>> getSupportComment(@RequestParam(required = true) Long support_idx) {
        try{
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

    /**
     * 댓글 수정 API
     * [PATCH] /support/modify/:support_comment_idx
     */
    @ResponseBody
    @PatchMapping("/modify/{support_comment_idx}") // 게시글 작성자(userIdx)를 확인해서 맞으면 바꾸도록 할껀데 jwt토큰도 받아서 같이
    public BaseResponse<String> modifySupportComment(@PathVariable("support_comment_idx") long support_comment_idx, @RequestBody SupportComment supportComment) {
        try {
            // PathVariable로 들어온 idx랑 SupportComment.getIdx한 거랑 같을 때만 수정 되도록 예외 처리 해줘야함
            PatchSupportCommentReq patchSupportCommentReq = new PatchSupportCommentReq(support_comment_idx, supportComment.getSupport_comment_content());
            supportCommentService.modifySupportComment(patchSupportCommentReq);

            String result = "댓글이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 댓글 삭제 API
     * [DELETE] /support/delete/:support_comment_idx
     */
    @ResponseBody
    @PatchMapping("/delete/{support_comment_idx}")
    public BaseResponse<String> deleteSupportComment(@PathVariable("support_comment_idx") long support_comment_idx) {
        try {
            DeleteSupportCommentReq deleteSupportCommentReq = new DeleteSupportCommentReq(support_comment_idx);
            supportCommentService.deleteSupportComment(deleteSupportCommentReq);

            String result = "댓글이 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




}
