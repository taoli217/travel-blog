package com.travelblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String content;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("article_id")
    private Long articleId;
    
    @TableField("parent_id")
    private Long parentId;
    
    @TableField("reply_to_id")
    private Long replyToId;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
