package com.ljk.controller.client;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljk.pojo.Blog;
import com.ljk.pojo.Type;
import com.ljk.service.BlogService;
import com.ljk.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * description: TypeShowController
 * author: JKL
 * date: 2021/3/1 16:51
 */
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;


    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                        Model model) {
        PageHelper.startPage(pageNum, 5);

        List<Type> types = typeService.getBlogType();
        //-1从导航点过来的
        if (types != null && types.size() > 0) {
            if (id == -1) {
                id = types.get(0).getId();
            }
        }

        List<Blog> blogs = blogService.getByTypeId(id);

        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTypeId", id);

        return "types";
    }
}
