package com.example.demo.src.scholarship_comment;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.scholarship_comment.model.*;
import com.example.demo.src.support_comment.model.PatchSupportCommentReq;
import com.example.demo.src.support_comment.model.SupportComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.POST_COMMENT_EMPTY_CONTENT;

@RestController
@RequestMapping("/scholarship/comment")
public class ScholarshipCommentCotroller {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ScholarshipCommentProvider scholarshipCommentProvider;
    @Autowired
    private final ScholarshipCommentService scholarshipCommentService;

    public ScholarshipCommentCotroller(ScholarshipCommentProvider scholarshipCommentProvider, ScholarshipCommentService scholarshipCommentService){
        this.scholarshipCommentProvider = scholarshipCommentProvider;
        this.scholarshipCommentService = scholarshipCommentService;
    }

    /**
     * 댓글 조회 API
     * [GET] /scholarship/comment?scholarship_idx=
     * @return BaseResponse<List<getScholarshipCommentRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetScholarshipCommentRes>> getScholarshipComment(@RequestParam(required = true) Long scholarship_idx) {
        try{
            // Get Users
            List<GetScholarshipCommentRes> getScholarshipCommentRes = scholarshipCommentProvider.getScholarshipComment(scholarship_idx);
            return new BaseResponse<>(getScholarshipCommentRes);
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
    public BaseResponse<PostScholarshipCommentRes> createScholarshipComment(@RequestBody PostScholarshipCommentReq postScholarshipCommentReq) {
        if (postScholarshipCommentReq.getScholarship_comment_content() == null) {
            return new BaseResponse<>(POST_COMMENT_EMPTY_CONTENT);
        }
        try {
            PostScholarshipCommentRes postScholarshipCommentRes = scholarshipCommentService.createScholarshipComment(postScholarshipCommentReq);
            return new BaseResponse<>(postScholarshipCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 댓글 수정 API
     * [PATCH] /scholarship/:scholarship_comment_idx
     */
    @ResponseBody
    @PatchMapping("/{scholarship_comment_idx}") // 게시글 작성자(userIdx)를 확인해서 맞으면 바꾸도록 할껀데 jwt토큰도 받아서 같이
    public BaseResponse<String> modifyScholarshipComment(@PathVariable("scholarship_comment_idx") long scholarship_comment_idx, @RequestBody ScholarshipComment scholarshipComment) {
        try {
            // PathVariable로 들어온 idx랑 SupportComment.getIdx한 거랑 같을 때만 수정 되도록 예외 처리 해줘야함
            PatchScholarshipCommentReq patchScholarshipCommentReq = new PatchScholarshipCommentReq(scholarship_comment_idx, scholarshipComment.getScholarship_comment_content());
            scholarshipCommentService.modifyScholarshipComment(patchScholarshipCommentReq);

            String result = "댓글이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    /**
//     * 회원가입 API
//     * [POST] /users
//     * @return BaseResponse<PostUserRes>
//     */
//    // Body
//    @ResponseBody
//    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
//    @PostMapping("")
//    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
//        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
//        if(postUserReq.getUserAccount() == null){
//            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
//        }
//        //이메일 정규표현
//        if(!isRegexEmail(postUserReq.getUserAccount())){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
//        try{
//            PostUserRes postUserRes = userService.createUser(postUserReq);
//            return new BaseResponse<>(postUserRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 로그인 API
//     * [POST] /users/logIn
//     * @return BaseResponse<PostLoginRes>
//     */
//    @ResponseBody
//    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
//    @PostMapping("/logIn")
//    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
//        try{
//            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
//            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
//            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
//            return new BaseResponse<>(postLoginRes);
//        } catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
}
