package com.ljk.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * description: Tag
 * author: JKL
 * date: 2021/2/19 20:59
 */
public class Tag implements Serializable {

    private Long id;

    private String name;
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
