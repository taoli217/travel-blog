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
public class TagService {

    private final TagMapper tagMapper;
    private final ArticleTagMapper articleTagMapper;

    public Result<?> list() {
        List<Tag> tags = tagMapper.selectList(null);
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Tag tag : tags) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", tag.getId());
            map.put("name", tag.getName());
            
            // 统计文章数
            LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTag::getTagId, tag.getId());
            map.put("articleCount", articleTagMapper.selectCount(wrapper));
            
            result.add(map);
        }
        
        return Result.success(result);
    }
}
