package com.pfy.cloud.utils;

import java.io.File;

public class test {

    public static void main(String[] args) {

        File file = new File("E:\\新建文件夹\\30.mp4");
        System.out.println(AboutFile.getFileSize(file));
        System.out.println(AboutFile.formatFileSize(AboutFile.getFileSize(file)));
    }
}
