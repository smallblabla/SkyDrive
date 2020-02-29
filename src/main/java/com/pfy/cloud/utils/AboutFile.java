package com.pfy.cloud.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class AboutFile {

    public static void deleteFileAll(File file) {
        if (file.exists()) {
            File files[] = file.listFiles();
            int len = files.length;
            for (int i = 0; i < len; i++) {
                if (files[i].isDirectory()) {
                    deleteFileAll(files[i]);
                } else {
                    files[i].delete();
                }
            }
            file.delete();
        }
    }

    /**
     * 获取指定文件大小
     */
    public static long getFileSize(File file){
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

}
