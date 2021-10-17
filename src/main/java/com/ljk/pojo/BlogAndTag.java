package com.ljk.pojo;

import java.util.Objects;

/**
 * description: 博客和标签关系
 * author: JKL
 * date: 2021/3/6 2:32
 */

public class BlogAndTag {

    private Long tagsId;

    private Long blogsId;

    public BlogAndTag() {
    }

    public BlogAndTag(Long tagsId, Long blogsId) {
        this.tagsId = tagsId;
        this.blogsId = blogsId;
    }

    public Long getBlogsId() {
        return blogsId;
    }

    public void setBlogsId(Long blogsId) {
        this.blogsId = blogsId;
    }


    public Long getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long tagsId) {
        this.tagsId = tagsId;
    }

    @Override
    public String toString() {
        return "BlogAndTag{" +
                "tagsId=" + tagsId +
                ", blogsId=" + blogsId +
                '}';
    }

}