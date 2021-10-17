package com.ljk.controller.admin;

import com.ljk.pojo.User;
import com.ljk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * description: UserController
 * author: JKL
 * date: 2021/3/10 17:49
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;

    //拿到信息
    @GetMapping("/user")
    public String UserInfo(Model model) {
        User user = userService.getUserInfo();
        model.addAttribute("user", user);
        return "admin/user";
    }

    //    跳转编辑页面
    @GetMapping("/user/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
//        System.out.println(user);

        model.addAttribute("user", user);
        return "admin/user_input";
    }

    // 编辑
    @PostMapping("/user")
    public String editPost(User user, HttpSession session, RedirectAttributes attributes) {

        userService.updateUserInfo(user);

        attributes.addFlashAttribute("message", "修改成功！");
        session.removeAttribute("user");
        return "redirect:/admin/user";
    }
}
