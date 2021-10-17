package com.ljk.controller.admin;

import com.ljk.pojo.User;
import com.ljk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * description: LoginController
 * author: JKL
 * date: 2021/2/21 15:16
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    //    跳转到登陆页面
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    //进行登陆验证
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/ad_index";
        } else {
            attributes.addFlashAttribute("message", "用户名密码错误");
            return "redirect:/admin";
        }
    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");//清空session里的user对象
        return "redirect:/admin";
    }

//    返回首页
    @GetMapping("/index")
    public String returnIndex(){
        return "index";
    }
}
