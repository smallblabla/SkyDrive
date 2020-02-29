package com.pfy.cloud.contorller;

import ch.qos.logback.classic.Logger;
import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.DownFile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.DownFileServiceImpl;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.service.impl.UserServiceImpl;
import com.pfy.cloud.utils.AboutFile;
import com.pfy.cloud.utils.AboutTime;
import com.pfy.cloud.utils.Other;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

/**
 * 分片上传Controller
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private UserServiceImpl userService;

    private static Logger logger = (Logger) LoggerFactory.getLogger(UploadController.class);

    /**
     * 上传路径
     */
    private static String uploadPath = "";
    private Cfile cfile = new Cfile();

    /**
     * 查看当前分片是否上传
     *
     * @param request
     * @param response
     */
    @PostMapping("checkblock")
    @ResponseBody
    public void checkMd5(HttpServletRequest request, HttpServletResponse response) {
        //当前分片
        String chunk = request.getParameter("chunk");
        //分片大小
        String chunkSize = request.getParameter("chunkSize");
        //当前文件的MD5值
        String guid = request.getParameter("guid");
        //分片上传路径
        String tempPath = uploadPath + File.separator + "temp";
        File checkFile = new File(tempPath + File.separator + guid + File.separator + chunk);
        response.setContentType("text/html;charset=utf-8");
        try {
            //如果当前分片存在，并且长度等于上传的大小
            if (checkFile.exists() && checkFile.length() == Integer.parseInt(chunkSize)) {
                response.getWriter().write("{\"ifExist\":1}");
            } else {
                response.getWriter().write("{\"ifExist\":0}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传分片
     *
     * @param file
     * @param chunk
     * @param guid
     * @throws IOException
     */
    @PostMapping("save")
    @ResponseBody
    public void upload(@RequestParam MultipartFile file, Integer chunk, String guid) throws IOException {
        String filePath = uploadPath + File.separator + "temp" + File.separator + guid;
        File tempfile = new File(filePath);
        if (!tempfile.exists()) {
            tempfile.mkdirs();
        }
        RandomAccessFile raFile = null;
        BufferedInputStream inputStream = null;
        if (chunk == null) {
            chunk = 0;
        }
        try {
            File dirFile = new File(filePath, String.valueOf(chunk));
            //以读写的方式打开目标文件
            raFile = new RandomAccessFile(dirFile, "rw");
            raFile.seek(raFile.length());
            inputStream = new BufferedInputStream(file.getInputStream());
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                raFile.write(buf, 0, length);
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (raFile != null) {
                raFile.close();
            }
        }
    }

    /**
     * 合并文件
     *
     * @param guid
     * @param fileName
     */
    @PostMapping("combine")
    @ResponseBody
    public void combineBlock(String guid, String fileName, HttpServletRequest request, String showPath, Integer father_fid) {
        //获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        String path = showPath.replace("Root",uploadPath + File.separator + user.getUsername());
        cfile.setUid(user.getUid());
        cfile.setFather_fid(father_fid);
        cfile.setFile_name(fileName);
        String str1=fileName.substring(0, fileName.indexOf("."));
        String str2=fileName.substring(str1.length()+1, fileName.length());
        cfile.setPicture(Other.getPicture(str2));
        cfile.setFile_type(Other.getType(str2));
        cfile.setCreate_time(AboutTime.getTime());
        cfile.setChange_time(AboutTime.getTime());
        cfile.setShow_position(showPath);
        cfile.setReal_position(path);
        cfile.setMd5(guid);
        //分片文件临时目录
        File tempPath = new File(uploadPath + File.separator + "temp" + File.separator + guid);
        //真实上传路径
        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdirs();
        }
        File realFile = new File(path+File.separator+fileName);
        FileOutputStream os = null;// 文件追加写入
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            logger.info("合并文件——开始 [ 文件名称：" + fileName + " ，MD5值：" + guid + " ]");
            os = new FileOutputStream(realFile, true);
            fcout = os.getChannel();
            if (tempPath.exists()) {
                //获取临时目录下的所有文件
                File[] tempFiles = tempPath.listFiles();
                //按名称排序
                Arrays.sort(tempFiles, (o1, o2) -> {
                    if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                        return -1;
                    }
                    if (Integer.parseInt(o1.getName()) == Integer.parseInt(o2.getName())) {
                        return 0;
                    }
                    return 1;
                });
                //每次读取10MB大小，字节读取
                //byte[] byt = new byte[10 * 1024 * 1024];
                //int len;
                //设置缓冲区为10MB
                ByteBuffer buffer = ByteBuffer.allocate(10 * 1024 * 1024);
                for (int i = 0; i < tempFiles.length; i++) {
                    FileInputStream fis = new FileInputStream(tempFiles[i]);
                    /*while ((len = fis.read(byt)) != -1) {
                        os.write(byt, 0, len);
                    }*/
                    fcin = fis.getChannel();
                    if (fcin.read(buffer) != -1) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            fcout.write(buffer);
                        }
                    }
                    buffer.clear();
                    fis.close();
                    //删除分片
                    tempFiles[i].delete();
                }
                os.close();
                //删除临时目录
                if (tempPath.isDirectory() && tempPath.exists()) {
                    System.gc(); // 回收资源
                    tempPath.delete();
                }
                cfile.setFile_size(AboutFile.formatFileSize(AboutFile.getFileSize(realFile)));
                if (Other.convertSize(AboutFile.formatFileSize(AboutFile.getFileSize(realFile)))+user.getUseMemory() > user.getAllMemory()*1000){
                    logger.error("内存不足" );
                }else{
                    double use = Other.convertSize(AboutFile.formatFileSize(AboutFile.getFileSize(realFile)))+user.getUseMemory();
                    double all = user.getAllMemory()*1000;
                    int ratio = (int) ((use/all)*100);
                    System.out.println(ratio);
                    userService.updateUse(use,ratio,user.getUid());
                    request.getSession().removeAttribute("user");
                    User user1 = userService.selectUser(user.getUsername());
                    request.getSession().setAttribute("user",user1);
                    fileService.addFile(cfile);
                    logger.info("文件合并——结束 [ 文件名称：" + fileName + " ，MD5值：" + guid + " ]");
                }
            }
        } catch (Exception e) {
            logger.error("文件合并——失败 " + e.getMessage());
        }
    }
}
