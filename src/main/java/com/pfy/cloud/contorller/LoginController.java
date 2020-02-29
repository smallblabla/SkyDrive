package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.UserServiceImpl;
import com.pfy.cloud.utils.AboutTime;
import com.pfy.cloud.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/isLogin")
    public String toBlog(@RequestParam String username,
                         @RequestParam String password,
                         HttpServletRequest request,
                         RedirectAttributes attributes) {
        User user = userService.selectUser(username);
        System.out.println(username+password);
        if (user != null) {
            if (user.getPassword().equals(MD5Utils.code(password)) ){
                user.setPassword(null);
                request.getSession().setAttribute("user", user);
                userService.updateLastLoginTime(AboutTime.getTime(),user.getUid());
                return "redirect:/index";
            } else {
                attributes.addFlashAttribute("message", "用户名和密码错误");
                return "redirect:/login";
            }
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
