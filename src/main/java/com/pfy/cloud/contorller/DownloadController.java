package com.pfy.cloud.contorller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.DownFile;
import com.pfy.cloud.entity.ShareFile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.DownFileService;
import com.pfy.cloud.service.impl.DownFileServiceImpl;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.utils.AboutTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class DownloadController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private DownFileServiceImpl downFileService;

    //实现Spring Boot 的文件下载功能，映射网址为/download
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam Integer fid) throws UnsupportedEncodingException {

        User user = (User) request.getSession().getAttribute("user");
        Cfile cfile = fileService.findFileById(fid);
        if (user.getUid() == cfile.getUid()){
            fileService.updateFileTime(AboutTime.getTime(),cfile.getFid());
        }
        if (downFileService.findDownFile(fid) != null && user.getUid() != downFileService.findDownFile(fid).getUid()){
            downFileService.updateTimeSum(AboutTime.getTime(),fid);
        }
        String fileName = cfile.getFile_name(); //下载的文件名
        System.out.println(fileName);
        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            String realPath = cfile.getReal_position();
            System.out.println(realPath);
            File file = new File(realPath, fileName);
            System.out.println(file);
            System.out.println(file.exists());
            // 如果文件名存在，则进行下载
            if (file.exists()) {
                System.out.println(1);
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("Download the song successfully!");
                }
                catch (Exception e) {
                    System.out.println("Download the song failed!");
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @RequestMapping("/fileDownSum")
    public String fileDownSum(Model model,
                              @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                              HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        PageHelper.startPage(pageNum,15);
        List<DownFile> downFiles = downFileService.findAllDownFile(user.getUid());
        PageInfo<DownFile> pageDownFiles = new PageInfo<DownFile>(downFiles);
        model.addAttribute("pageDownFiles",pageDownFiles);
        System.out.println(downFiles);
        return "fileDownSum";

    }
}

