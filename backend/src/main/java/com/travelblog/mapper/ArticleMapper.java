package com.travelblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travelblog.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
