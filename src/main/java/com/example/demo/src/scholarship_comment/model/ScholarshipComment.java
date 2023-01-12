package com.example.demo.src.scholarship_comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScholarshipComment {
    private Long scholarship_comment_idx;
    private Long scholarship_idx;
    private Long user_idx;
    private String scholarship_comment_content;

}
