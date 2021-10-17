package com.ljk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljk.pojo.Type;
import com.ljk.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * description: TypeController
 * author: JKL
 * date: 2021/2/22 15:27
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 10);
        List<Type> allType = typeService.getAllType();
        //封装分页结果对象
        PageInfo<Type> pageInfo = new PageInfo<Type>(allType);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    //到分类新增页面
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types_input";
    }

    //到分类编辑页面
    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("type", type);
        return "admin/types_input";
    }

    //新增
    @PostMapping("/types")
    public String savePost(Type type, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            attributes.addFlashAttribute("message", "分类名称重复，请换一个！");
        } else {
            typeService.saveType(type);
            attributes.addFlashAttribute("message", "添加成功！");
        }
        return "redirect:/admin/types";
    }

    //修改
    @PostMapping("/types/{id}")
    public String editPost(@PathVariable Long id, Type type, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            attributes.addFlashAttribute("message", "分类名称重复，请换一个！");
        } else {
            typeService.updateType(type);
            attributes.addFlashAttribute("message", "修改成功！");
        }
        return "redirect:/admin/types";
    }
    //删除

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
