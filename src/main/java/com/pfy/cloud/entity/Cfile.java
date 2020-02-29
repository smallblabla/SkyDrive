package com.pfy.cloud.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Cfile {

            private int fid                ;            //文件id
            private int uid                ;            //用户id
            private int father_fid         ;            //父级文件夹id
            private String file_name       ;            //文件名称
            private String file_type       ;            //文件类型
            private String file_size       ;            //文件大小
            private String md5             ;            //文件md5值
            private String real_position   ;            //文件真实路径
            private String show_position   ;            //文件前端显示路径
            private boolean encryption     ;            //是否加密
            private String share_state     ;            //文件公开分享状态
            private int delete_state       ;            //文件删除状态
            private Date create_time       ;            //文件创建时间
            private Date change_time       ;            //最近操作时间
            private String picture         ;            //图片

}
