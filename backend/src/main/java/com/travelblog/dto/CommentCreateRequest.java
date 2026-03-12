package com.travelblog.dto;

import lombok.Data;

@Data
public class CommentCreateRequest {
    private String content;
    private Long parentId;
    private Long replyToId;
}
