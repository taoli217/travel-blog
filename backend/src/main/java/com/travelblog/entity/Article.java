package com.travelblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    private String content;
    private String summary;
    private String coverImage;
    
    @TableField("view_count")
    private Integer viewCount;
    
    @TableField("like_count")
    private Integer likeCount;
    
    @TableField("comment_count")
    private Integer commentCount;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("category_id")
    private Long categoryId;
    
    private Integer status; // 0-草稿, 1-已发布
    
    @TableField("is_featured")
    private Integer isFeatured; // 0-否, 1-是
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
