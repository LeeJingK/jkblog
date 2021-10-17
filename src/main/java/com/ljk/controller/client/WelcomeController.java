package com.ljk.controller.client;

import com.ljk.pojo.Blog;
import com.ljk.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.Session;
import java.util.List;

/**
 * description: WelcomeController
 * author: JKL
 * date: 2021/4/11 16:00
 */
@Controller
public class WelcomeController {
    @Autowired
    BlogService blogService;

    @GetMapping("/")
    public String welcome(Model model){
        List<Blog> blog =  blogService.getAllRecommendBlog();
        model.addAttribute("recommendBlogs", blog);
        return "welcome";
    }

}
