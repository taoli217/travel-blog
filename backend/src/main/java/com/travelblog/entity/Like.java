package com.travelblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("blog_like")
public class Like {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("article_id")
    private Long articleId;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
