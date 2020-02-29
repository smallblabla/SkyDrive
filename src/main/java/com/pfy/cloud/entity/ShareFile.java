package com.pfy.cloud.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ShareFile {

    private int id                  ;      //文件id
    private int uid                 ;      //用户id
    private String file_name        ;      //文件名
    private String file_type        ;      //文件类型
    private String file_size        ;      //文件大小
    private String md5              ;      //md5
    private String real_position    ;      //文件真实路径
    private String show_position    ;      //文件显示路径
    private String share_state      ;      //分享状态
    private String password         ;      //提取码
    private Date create_time        ;      //创建时间
    private Date delete_time        ;      //删除时间
    private String picture          ;      //图片
}
