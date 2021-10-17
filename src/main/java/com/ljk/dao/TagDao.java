package com.ljk.dao;

import com.ljk.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: TagDao
 * author: JKL
 * date: 2021/3/15 2:21
 */
@Mapper
@Repository
public interface TagDao {
    int saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> getAllTag();

    List<Tag> getBlogTag();

    int updateTag(Tag tag);

    int deleteTag(Long id);
}
