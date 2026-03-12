package com.travelblog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.travelblog.dto.*;
import com.travelblog.entity.*;
import com.travelblog.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    public Result<?> list() {
        List<Category> categories = categoryMapper.selectList(null);
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Category cat : categories) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cat.getId());
            map.put("name", cat.getName());
            map.put("description", cat.getDescription());
            
            // 统计文章数
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Article::getCategoryId, cat.getId());
            map.put("articleCount", articleMapper.selectCount(wrapper));
            
            result.add(map);
        }
        
        return Result.success(result);
    }

    public Result<?> create(CategoryCreateRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setSort(0);
        
        categoryMapper.insert(category);
        
        return Result.success("创建成功", Map.of("id", category.getId()));
    }
}
