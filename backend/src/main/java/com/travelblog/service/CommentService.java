package com.travelblog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travelblog.dto.*;
import com.travelblog.entity.*;
import com.travelblog.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;

    public Result<?> list(Long articleId, Integer page, Integer size) {
        Page<Comment> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId);
        wrapper.isNull(Comment::getParentId);
        wrapper.orderByDesc(Comment::getCreatedAt);
        
        IPage<Comment> result = commentMapper.selectPage(pageParam, wrapper);
        
        List<Map<String, Object>> records = new ArrayList<>();
        for (Comment comment : result.getRecords()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", comment.getId());
            map.put("content", comment.getContent());
            map.put("createdAt", comment.getCreatedAt());
            
            User user = userMapper.selectById(comment.getUserId());
            if (user != null) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", user.getId());
                userMap.put("nickname", user.getNickname());
                userMap.put("avatar", user.getAvatar());
                map.put("user", userMap);
            }
            
            map.put("replies", new ArrayList<>());
            records.add(map);
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("records", records);
        data.put("total", result.getTotal());
        
        return Result.success(data);
    }

    public Result<?> create(Long articleId, CommentCreateRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setArticleId(articleId);
        comment.setUserId(1L);
        comment.setParentId(request.getParentId());
        comment.setReplyToId(request.getReplyToId());
        
        commentMapper.insert(comment);
        
        Article article = articleMapper.selectById(articleId);
        if (article != null) {
            article.setCommentCount(article.getCommentCount() + 1);
            articleMapper.updateById(article);
        }
        
        return Result.success("评论成功", Map.of("id", comment.getId()));
    }
}
