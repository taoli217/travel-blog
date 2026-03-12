package com.travelblog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travelblog.dto.*;
import com.travelblog.entity.*;
import com.travelblog.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final ArticleTagMapper articleTagMapper;

    public Result<?> list(Integer page, Integer size, Long categoryId, String keyword) {
        Page<Article> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        
        wrapper.eq(Article::getStatus, 1); // 只查询已发布的
        
        if (categoryId != null) {
            wrapper.eq(Article::getCategoryId, categoryId);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Article::getTitle, keyword);
        }
        
        wrapper.orderByDesc(Article::getCreatedAt);
        
        IPage<Article> result = articleMapper.selectPage(pageParam, wrapper);
        
        // 转换VO
        List<Map<String, Object>> records = new ArrayList<>();
        for (Article article : result.getRecords()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", article.getId());
            map.put("title", article.getTitle());
            map.put("summary", article.getSummary());
            map.put("coverImage", article.getCoverImage());
            map.put("viewCount", article.getViewCount());
            map.put("likeCount", article.getLikeCount());
            map.put("commentCount", article.getCommentCount());
            map.put("createdAt", article.getCreatedAt());
            
            // 作者信息
            User author = userMapper.selectById(article.getUserId());
            if (author != null) {
                Map<String, Object> authorMap = new HashMap<>();
                authorMap.put("id", author.getId());
                authorMap.put("nickname", author.getNickname());
                authorMap.put("avatar", author.getAvatar());
                map.put("author", authorMap);
            }
            
            // 分类
            if (article.getCategoryId() != null) {
                Category category = categoryMapper.selectById(article.getCategoryId());
                if (category != null) {
                    map.put("category", category.getName());
                }
            }
            
            records.add(map);
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("records", records);
        data.put("total", result.getTotal());
        data.put("page", page);
        data.put("size", size);
        
        return Result.success(data);
    }

    public Result<?> get(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            return Result.error(404, "文章不存在");
        }
        
        // 增加浏览量
        article.setViewCount(article.getViewCount() + 1);
        articleMapper.updateById(article);
        
        Map<String, Object> map = new HashMap<>();
        map.put("id", article.getId());
        map.put("title", article.getTitle());
        map.put("content", article.getContent());
        map.put("summary", article.getSummary());
        map.put("coverImage", article.getCoverImage());
        map.put("viewCount", article.getViewCount());
        map.put("likeCount", article.getLikeCount());
        map.put("createdAt", article.getCreatedAt());
        map.put("updatedAt", article.getUpdatedAt());
        
        // 分类
        if (article.getCategoryId() != null) {
            Category category = categoryMapper.selectById(article.getCategoryId());
            if (category != null) {
                Map<String, Object> catMap = new HashMap<>();
                catMap.put("id", category.getId());
                catMap.put("name", category.getName());
                map.put("category", catMap);
            }
        }
        
        // 作者
        User author = userMapper.selectById(article.getUserId());
        if (author != null) {
            Map<String, Object> authorMap = new HashMap<>();
            authorMap.put("id", author.getId());
            authorMap.put("nickname", author.getNickname());
            authorMap.put("avatar", author.getAvatar());
            map.put("author", authorMap);
        }
        
        return Result.success(map);
    }

    @Transactional
    public Result<?> create(ArticleCreateRequest request) {
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setSummary(request.getSummary());
        article.setCoverImage(request.getCoverImage());
        article.setCategoryId(request.getCategoryId());
        article.setStatus(request.getStatus() != null ? request.getStatus() : 1);
        article.setUserId(1L); // 简化，实际从登录获取
        
        articleMapper.insert(article);
        
        // 关联标签
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            for (Long tagId : request.getTagIds()) {
                ArticleTag at = new ArticleTag();
                at.setArticleId(article.getId());
                at.setTagId(tagId);
                articleTagMapper.insert(at);
            }
        }
        
        return Result.success("发布成功", Map.of("id", article.getId()));
    }

    public Result<?> update(Long id, ArticleCreateRequest request) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            return Result.error(404, "文章不存在");
        }
        
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setSummary(request.getSummary());
        article.setCoverImage(request.getCoverImage());
        article.setCategoryId(request.getCategoryId());
        
        articleMapper.updateById(article);
        
        return Result.success("更新成功");
    }

    public Result<?> delete(Long id) {
        articleMapper.deleteById(id);
        return Result.success("删除成功");
    }

    public Result<?> toggleLike(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            return Result.error(404, "文章不存在");
        }
        
        // 简化版点赞
        article.setLikeCount(article.getLikeCount() + 1);
        articleMapper.updateById(article);
        
        Map<String, Object> data = new HashMap<>();
        data.put("isLiked", true);
        data.put("likeCount", article.getLikeCount());
        
        return Result.success(data);
    }
}
