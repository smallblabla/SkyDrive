package com.pfy.cloud.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DownFile {

    private int fid;              //文件id
    private int uid;              //用户id
    private String file_name;     //文件名
    private String file_size;     //文件大小
    private Date lastDownTime;    //文件最后下载时间
    private int downSum;          //文件下载次数

}
