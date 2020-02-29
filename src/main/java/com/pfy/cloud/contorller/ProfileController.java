package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/profile")
    public String profile(){
        return "profile";
    }

    @RequestMapping("/changeProfile")
    public String changeProfile(@RequestParam String nickname,
                                @RequestParam String identity,
                                @RequestParam String sex,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam String profile,
                                HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        user.setNickname(nickname);
        user.setIdentity(identity);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhone(phone);
        user.setProfile(profile);
        userService.updateUser(user);
        return "/profile";
    }

    @RequestMapping("/changePortrait")
    public String changePortrait(@RequestParam Integer id,
                                 HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        user.setPicture("/img/portrait/"+id+".jpg");
        userService.updateUser(user);
        return "/profile";
    }

    @RequestMapping("/changePassword")
    public String changePassword(@RequestParam String password,
                                 HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        userService.updatePassword(password,user.getUid());
        request.getSession().removeAttribute("user");
        return "/login";
    }

    @RequestMapping("/password")
    public String password(){
        return "changePassword";
    }
}
