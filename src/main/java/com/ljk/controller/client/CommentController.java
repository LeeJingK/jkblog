package com.ljk.controller.client;

import com.ljk.pojo.Blog;
import com.ljk.pojo.Comment;
import com.ljk.pojo.User;
import com.ljk.service.BlogService;
import com.ljk.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * description: CommentController
 * author: JKL
 * date: 2021/2/28 18:42
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    //展示留言
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    //    新增评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session, Model model) {

        Long blogId = comment.getBlog().getId();
        User user = (User) session.getAttribute("user");
        //绑定博客与评论
        comment.setBlog(blogService.getDetailedBlog(blogId));
        comment.setBlogId(blogId);
        //如果管理员已登录
        if (user != null) {
//            emojiConverter.toAlias(comment.getContent());//将聊天内容进行转义
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            //设置头像
            comment.setAvatar(avatar);
        }
        if (comment.getParentComment().getId() != null) {
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.saveComment(comment);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    //    删除评论
    @GetMapping("/comment/{blogId}/{id}/delete")
    public String delete(@PathVariable Long blogId, @PathVariable Long id, Comment comment, RedirectAttributes attributes, Model model) {
        commentService.deleteComment(comment, id);
        Blog blog = blogService.getDetailedBlog(blogId);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("blog", blog);
        model.addAttribute("comments", comments);
        return "blog";
    }

}
