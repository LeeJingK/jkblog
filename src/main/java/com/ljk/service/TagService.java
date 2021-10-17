package com.ljk.service;

import com.github.pagehelper.Page;
import com.ljk.pojo.Tag;

import java.util.List;

/**
 * description: TagService
 * author: JKL
 * date: 2021/2/23 10:53
 */

public interface TagService {


    int saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> getAllTag();

    //首页展示博客标签
    List<Tag> getBlogTag();

    //从字符串中获取tag集合
    List<Tag> getTagByString(String text);

    int updateTag(Tag tag);

    int deleteTag(Long id);
}
