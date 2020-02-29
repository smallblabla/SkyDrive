package com.pfy.cloud.utils;

import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.DownFile;
import com.pfy.cloud.service.DownFileService;
import com.pfy.cloud.service.impl.DownFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class Other {

    @Autowired
    private static DownFileServiceImpl downFileService;

    public static String getPicture(String type){
        if (type.equals("txt")){
            return "/img/document/TXT.png";
        }
        if (type.equals("doc")){
            return "/img/document/DOC.png";
        }
        if (type.equals("pdf")){
            return "/img/document/PDF.png";
        }
        if (type.equals("ppt")){
            return "/img/document/PPT.png";
        }
        if (type.equals("docx")){
            return "/img/document/DOCX.png";
        }
        if (type.equals("css")){
            return "/img/document/CSS.png";
        }
        if (type.equals("html")){
            return "/img/document/HTML.png";
        }
        if (type.equals("js")){
            return "/img/document/js.png";
        }
        if (type.equals("rtf")){
            return "/img/document/RTF.png";
        }
        if (type.equals("wps")){
            return "/img/document/WPS.png";
        }
        if (type.equals("alf")){
            return "/img/music/ALF.png";
        }
        if (type.equals("mp3")){
            return "/img/music/MP3.png";
        }
        if (type.equals("wav")){
            return "/img/music/WAV.png";
        }
        if (type.equals("wma")){
            return "/img/music/WMA.png";
        }
        if (type.equals("jpg")){
            return "/img/picture/JPG.png";
        }
        if (type.equals("psd")){
            return "/img/picture/PSD.png";
        }
        if (type.equals("png")){
            return "/img/picture/PNG.png";
        }
        if (type.equals("avi")){
            return "/img/video/AVI.png";
        }
        if (type.equals("mp4")){
            return "/img/video/MP4.png";
        }
        return "/img/other.png";
    }


    public static String getType(String suffix){
        if (suffix.equals("txt") || suffix.equals("css") || suffix.equals("doc") || suffix.equals("docx") || suffix.equals("html") || suffix.equals("js") || suffix.equals("pdf") || suffix.equals("ppt") || suffix.equals("rtf") || suffix.equals("wps")){
            return "文本";
        }
        if (suffix.equals("alf") || suffix.equals("mp3") || suffix.equals("wav") || suffix.equals("wma")){
            return "音乐";
        }
        if (suffix.equals("jpg") || suffix.equals("psd") || suffix.equals("png")){
            return "图片";
        }
        if (suffix.equals("mp4") || suffix.equals("avi")){
            return "视频";
        }
        return "其他";
    }


    public static String getURL(int id,String md5){
        return "http://106.75.26.41:7777/" + "toShare?id=" + id + "&md5=" + md5;
    }

    public static void savaDownFile(Cfile cfile){
        DownFile downFile = new DownFile();
        downFile.setUid(cfile.getUid());
        downFile.setFile_name(cfile.getFile_name());
        downFile.setFile_size(cfile.getFile_size());
        downFile.setDownSum(0);
        downFileService.addDownFile(downFile);
    }

    public static double convertSize(String str) {
        if (str.contains("m") || str.contains("M") && !str.contains("G") && !str.contains("g")) {
                double d = 0;

                if(str!=null && str.length()!=0){
                    StringBuffer bf = new StringBuffer();

                    char[] chars = str.toCharArray();
                    for(int i=0;i<chars.length;i++)
                    {
                        char c = chars[i];
                        if(c>='0' && c<='9')
                        {
                            bf.append(c);
                        }
                        else if(c=='.')
                        {
                            if(bf.length()==0)
                            {
                                continue;
                            }
                            else if(bf.indexOf(".")!=-1)
                            {
                                break;
                            }
                            else
                            {
                                bf.append(c);
                            }
                        }
                        else
                        {
                            if(bf.length()!=0)
                            {
                                break;
                            }
                        }
                    }
                    try
                    {
                        d = Double.parseDouble(bf.toString());
                    }
                    catch(Exception e)
                    {}
                }

                return d;
            }

        if (str.contains("G") || str.contains("g")) {
            double d = 0;

            if(str!=null && str.length()!=0){
                StringBuffer bf = new StringBuffer();

                char[] chars = str.toCharArray();
                for(int i=0;i<chars.length;i++)
                {
                    char c = chars[i];
                    if(c>='0' && c<='9')
                    {
                        bf.append(c);
                    }
                    else if(c=='.')
                    {
                        if(bf.length()==0)
                        {
                            continue;
                        }
                        else if(bf.indexOf(".")!=-1)
                        {
                            break;
                        }
                        else
                        {
                            bf.append(c);
                        }
                    }
                    else
                    {
                        if(bf.length()!=0)
                        {
                            break;
                        }
                    }
                }
                try
                {
                    d = Double.parseDouble(bf.toString());
                }
                catch(Exception e)
                {}
            }
            return 1000*d;
        }
        return 0;
    }

}


