package com.ljk.service;

import com.ljk.pojo.Comment;

import java.util.List;

/**
 * description: CommentService
 * author: JKL
 * date: 2021/2/28 19:00
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    int saveComment(Comment comment);

//    查询子评论
//    List<Comment> getChildComment(Long blogId,Long id);

    //删除评论
    void deleteComment(Comment comment,Long id);

}
