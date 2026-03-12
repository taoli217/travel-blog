package com.travelblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travelblog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
