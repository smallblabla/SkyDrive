package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.ShareFile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.service.impl.ShareFileServiceImpl;
import com.pfy.cloud.utils.AboutTime;
import com.pfy.cloud.utils.Other;
import com.pfy.cloud.utils.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    private static String Path = "F:\\Download";


    /**
     * 新建文件夹
     *
     * @return
     */
    @RequestMapping("/addFolder")
    public String show(String folderName, String showPath, Integer nowFartherId, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        Cfile cfile = new Cfile();
        if (nowFartherId != 0){
            cfile.setFather_fid(nowFartherId);
        }else {
            cfile.setFather_fid(0);
        }
        cfile.setPicture("/img/folder.png");
        cfile.setFile_name(folderName);
        cfile.setUid(user.getUid());
        cfile.setFile_type("folder");
        cfile.setCreate_time(AboutTime.getTime());
        cfile.setFile_size("1KB");
        cfile.setShow_position(showPath);
        String realPath = showPath.replace("Root",Path + File.separator + user.getUsername());
        cfile.setReal_position(realPath);
        realPath += File.separator + folderName;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
            fileService.addFile(cfile);
        }
        if (nowFartherId == 0){
            return "redirect:/index";
        }
        return "redirect:/orSon?id="+nowFartherId ;
    }

    @RequestMapping("/Rename")
    public String Rename(@RequestParam Integer fid,
                         @RequestParam String newName
                         ){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        File file=new File(cfile.getReal_position() + File.separator + cfile.getFile_name()); //指定文件名及路径
        if (cfile.getFile_type().equals("folder")){
            //修改
            file.renameTo(new File(cfile.getReal_position()+File.separator +newName));
            fileService.fileRename(newName,fid);
        }else {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            String str1=cfile.getFile_name().substring(0, cfile.getFile_name().indexOf("."));
            String str2=cfile.getFile_name().substring(str1.length()+1, cfile.getFile_name().length());
            String rootPath = file.getParent();
            File newFile = new File(rootPath + File.separator + newName+"."+str2);
            System.out.println(newFile);
            System.out.println(rootPath + File.separator + newName+"."+str2);
            System.out.println(file);
            if (file.renameTo(newFile)) {
                System.out.println("修改成功!");
                fileService.fileRename(newName+"."+str2,fid);
            } else {
                System.out.println("修改失败");
            }

//            //修改
//            try {
//                file.renameTo(new File(cfile.getReal_position()+File.separator +newName+"."+str2));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }

        return "redirect:/orSon?id="+fileService.findF_fid(fid);
    }


    /**
     * 删除文件
     *
     * @return
     */
    @RequestMapping("/delete")
    public String detele(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        int showId = fileService.findF_fid(fid);
        if (cfile.getFile_type().equals("folder")){
            deleteFolder(fid);
            fileService.updateFileDelete(1,fid);
        }else {
            fileService.updateFileDelete(1,fid);
        }
        return "redirect:/orSon?id="+showId;
    }
    private void deleteFolder(int id){
        List<Integer> allSon = fileService.findSonById(id);
        fileService.updateFileDelete(2,id);
        for (int son:allSon){
            fileService.updateFileDelete(2,son);
            deleteFolder(son);
        }
    }



    @RequestMapping("/encryption")
    public String encryption(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        fileService.updateFileEncryption(true,fid);
        int showId = fileService.findF_fid(fid);
        return "redirect:/orSon?id="+showId;
    }

}
