package com.ljk.controller.client;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljk.pojo.Blog;
import com.ljk.pojo.Tag;
import com.ljk.service.BlogService;
import com.ljk.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * description: TagShowController
 * author: JKL
 * date: 2021/3/1 16:51
 */
@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                        Model model) {
        PageHelper.startPage(pageNum, 10);  //开启分页
        List<Tag> tags = tagService.getBlogTag();
        //-1从导航点过来的
        if (tags != null && tags.size() > 0) {
            if (id == -1) {
                id = tags.get(0).getId();
            }
        }
        List<Blog> blogs = blogService.getByTagId(id);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTagId", id);

        return "tags";
    }

}
