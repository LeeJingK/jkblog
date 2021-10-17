package com.ljk.controller.client;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljk.pojo.Blog;
import com.ljk.pojo.Comment;
import com.ljk.pojo.Tag;
import com.ljk.pojo.Type;
import com.ljk.service.BlogService;
import com.ljk.service.CommentService;
import com.ljk.service.TagService;
import com.ljk.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * description: IndexController
 * author: JKL
 * date: 2021/2/18 21:20
 */
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    //首页获取列表
    @GetMapping("/index")
    public String index(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                        @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {

        List<Type> listType = typeService.getBlogType();
        List<Tag> listTag = tagService.getBlogTag();
        List<Blog> recommendBlog = blogService.getAllRecommendBlog();

//        PageHelper.startPage(pageNum, 2);
//        PageInfo<Blog> pageInfo = new PageInfo<>(list);

        model.addAttribute("pageInfo", blogService.getIndexBlog(pageNum,pageSize));
        model.addAttribute("types", listType);
        model.addAttribute("tags", listTag);
        model.addAttribute("recommendBlogs", recommendBlog);
        return "index";
    }

    //搜索
    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query, Model model) {

//        PageHelper.startPage(pageNum, 5);
        List<Blog> searchBlog = blogService.getSearchBlog(query);
        PageInfo pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    //进入博客
    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable Long id, Model model) {

        Blog blog = blogService.getDetailedBlog(id);
        List<Comment> comments = commentService.listCommentByBlogId(id);
        int views = blog.getViews();
        blogService.saveViews(++views, id);
        model.addAttribute("comments", comments);
        model.addAttribute("blog", blog);
        return "blog";
    }
}
