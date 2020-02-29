package com.pfy.cloud.service.impl;

import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserServiceImpl {

    @Autowired
    private UserService userService;

    public User selectUser(String username) {
        User user = userService.selectUser(username);
        if (user != null) return user;
        return null;
    }

    public void addUser(User user){
        userService.addUser(user);
    }

    public void updateUser(User user){
        userService.updateUser(user);
    }

    public void updatePassword(String password,int uid){
        userService.updatePassword(password,uid);
    }

    public void updateLastLoginTime(Date lastLoginTime, int uid){
        userService.updateLastLoginTime(lastLoginTime,uid);
    }

    public void updateUse(double use,double ratio,int uid){
        userService.updateUse(use,ratio,uid);
    }
}
