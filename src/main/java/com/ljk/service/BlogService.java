package com.ljk.service;

import com.github.pagehelper.PageInfo;
import com.ljk.pojo.Blog;
import org.jboss.logging.BasicLogger;

import java.util.List;
import java.util.Map;

/**
 * description: BlogService
 * author: JKL
 * date: 2021/2/23 22:14
 */
public interface BlogService {

    List<Blog> getAllBlog();

    int saveBlog(Blog blog);

    Blog getBlogById(Long id);

    int updateBlog(Blog blog);

    void deleteBlog(Long id);

    List<Blog> searchAllBlog(Blog blog);

    PageInfo<Blog> getIndexBlog(int pageNum, int pageSize);

    List<Blog> getAllRecommendBlog();

    List<Blog> getSearchBlog(String query);

    Blog getDetailedBlog(Long id);

    void saveViews(int views, Long id);

    List<Blog> getByTypeId(Long id);

    List<Blog> getByTagId(Long id);

    Map<String, List<Blog>> archiveBlog();

    int countBlog();


}
