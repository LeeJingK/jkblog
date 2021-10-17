package com.ljk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljk.pojo.Tag;
import com.ljk.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * description: TagController
 * author: JKL
 * date: 2021/2/23 12:36
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 10);
        List<Tag> allTag = tagService.getAllTag();
        //得到分页结果对象
        PageInfo<Tag> pageInfo = new PageInfo<>(allTag);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/tags";
    }

    //到分类新增页面
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags_input";
    }

    //到分类编辑页面
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags_input";
    }

    //新增
    @PostMapping("/tags")
    public String savePost(Tag tag, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            attributes.addFlashAttribute("message", "分类名称重复，请换一个！");
            return "redirect:/admin/tags/input";
        } else {
            attributes.addFlashAttribute("message", "添加成功！");
        }
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }

    //修改
    @PostMapping("/tags/{id}")
    public String editPost(@PathVariable Long id, Tag tag, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            attributes.addFlashAttribute("message", "标签名称重复，请换一个！");
            return "redirect:/admin/tags/input";
        } else {
            attributes.addFlashAttribute("message", "修改成功！");
        }
        tagService.updateTag(tag);
        return "redirect:/admin/tags";
    }

    //删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

}
