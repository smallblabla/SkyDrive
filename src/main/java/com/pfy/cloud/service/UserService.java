package com.pfy.cloud.service;

import com.pfy.cloud.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface UserService {
     User selectUser(String username);
     void addUser(User user);
     void updateUser(User user);
     void updatePassword(String password,int uid);
     void updateLastLoginTime(Date lastLoginTime,int uid);
     void updateUse(double use,double ratio,int uid);
}
