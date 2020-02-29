package com.pfy.cloud.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

            private int uid            ;      // id
            private String username    ;      // 用户名
            private String password    ;      // 密码
            private String nickname    ;      // 昵称
            private String picture     ;      // 头像
            private String sex         ;      // 性别
            private String email       ;      // 邮箱
            private String phone       ;      // 电话
            private String profile    ;      // 个人简介
            private String identity    ;      // 身份
            private String vip         ;      //vip
            private double allMemory   ;      //总空间
            private double useMemory   ;      //已用空间
            private Date createTime    ;      //账号创建时间
            private Date lastLoginTime ;      //最近登录时间
            private double ratio;

}
