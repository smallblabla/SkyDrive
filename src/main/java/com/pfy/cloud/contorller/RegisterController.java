package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.UserServiceImpl;
import com.pfy.cloud.utils.AboutTime;
import com.pfy.cloud.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    private UserServiceImpl userService;

    private double allMemory = 2;

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/isRegister")
    public String toRegister(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            RedirectAttributes attributes,
            Model model
    ){
        if (userService.selectUser(username) != null){
            attributes.addFlashAttribute("message", "用户名已被注册");
            return "redirect:/register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.code(password));
        user.setEmail(email);
        user.setAllMemory(allMemory);
        user.setCreateTime(AboutTime.getTime());
        user.setPicture("/img/portrait/1.jpg");
        userService.addUser(user);
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        return "login";
    }

}
