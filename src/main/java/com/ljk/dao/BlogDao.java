package com.ljk.dao;

import com.ljk.pojo.Blog;
import com.ljk.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * description: BlogDao
 * author: JKL
 * date: 2021/2/23 22:21
 */
@Mapper
@Repository
public interface BlogDao {

    List<Blog> getAllBlog();

    int saveBlog(Blog blog);

    Blog getBlogById(Long id);

    Type getTypeById(Long id);

    int updateBlog(Blog blog);

    void deleteBlog(Long id);

    List<Blog> searchAllBlog(Blog blog);

    List<Blog> getIndexBlog();

    List<Blog> getAllRecommendBlog();

    List<Blog> getSearchBlog(String query);

    Blog getDetailedBlog(Long id);

    void saveViews(@Param("views") int views, @Param("id") Long id);

    List<Blog> getByTypeId(Long id);

    List<Blog> getByTagId(Long id);

    void saveBlogAndTag(Map<String, Long> map);

    List<String> findGroupYear();

    List<Blog> findByYear(String year);

    void getCommentCountById(Long blogId);


}
