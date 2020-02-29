package com.pfy.cloud.contorller;


import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.utils.AboutTime;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class SoftController {

    @Autowired
    private FileServiceImpl fileService;



    @RequestMapping("/documentSort")
    public String documentSoft(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("files",fileService.findSortFile(user.getUid(),"文本"));
        return "documentSort";
    }

    @RequestMapping("/pictureSort")
    public String pictureSoft(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("files",fileService.findSortFile(user.getUid(),"图片"));
        return "pictureSort";
    }

    @RequestMapping("/videoSort")
    public String videoSoft(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("files",fileService.findSortFile(user.getUid(),"视频"));
        return "videoSort";
    }

    @RequestMapping("/otherSort")
    public String otherSoft(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("files",fileService.findSortFile(user.getUid(),"其他"));
        return "otherSort";
    }

    @RequestMapping("/musicSort")
    public String musicSoft(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("files",fileService.findSortFile(user.getUid(),"音乐"));
        return "musicSort";
    }

    @RequestMapping("/encryptionDocument")
    public String encryptionDoc(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        fileService.updateFileEncryption(true,fid);
        return "redirect:/documentSort";
    }

    @RequestMapping("/encryptionPicture")
    public String encryptionPicture(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        fileService.updateFileEncryption(true,fid);
        return "redirect:/pictureSort";
    }

    @RequestMapping("/encryptionVideo")
    public String encryptionVideo(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        fileService.updateFileEncryption(true,fid);
        return "redirect:/videoSort";
    }

    @RequestMapping("/encryptionMusic")
    public String encryptionMusic(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        fileService.updateFileEncryption(true,fid);
        return "redirect:/musicSort";
    }

    @RequestMapping("/encryptionOther")
    public String encryptionOther(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        fileService.updateFileEncryption(true,fid);
        return "redirect:/otherSort";
    }

    @RequestMapping("/docRename")
    public String docRename(@RequestParam Integer fid,
                             @RequestParam String newName
    ){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        System.out.println(newName);
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

        }

        return "redirect:/documentSort";
    }

    @RequestMapping("/docDelete")
    public String docDetele(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        int showId = fileService.findF_fid(fid);
        if (cfile.getFile_type().equals("folder")){
            deleteFolder(fid);
            fileService.updateFileDelete(1,fid);
        }else {
            fileService.updateFileDelete(1,fid);
        }
        return "redirect:/documentSort";
    }

    @RequestMapping("/videoRename")
    public String videoRename(@RequestParam Integer fid,
                            @RequestParam String newName
    ){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        System.out.println(newName);
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

        }

        return "redirect:/videoSort";
    }

    @RequestMapping("/videoDelete")
    public String videoDetele(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        int showId = fileService.findF_fid(fid);
        if (cfile.getFile_type().equals("folder")){
            deleteFolder(fid);
            fileService.updateFileDelete(1,fid);
        }else {
            fileService.updateFileDelete(1,fid);
        }
        return "redirect:/videoSort";
    }


    @RequestMapping("/pictureRename")
    public String pictureRename(@RequestParam Integer fid,
                            @RequestParam String newName
    ){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        System.out.println(newName);
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
        }

        return "redirect:/pictureSort";
    }

    @RequestMapping("/pictureDelete")
    public String pictureDetele(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        int showId = fileService.findF_fid(fid);
        if (cfile.getFile_type().equals("folder")){
            deleteFolder(fid);
            fileService.updateFileDelete(1,fid);
        }else {
            fileService.updateFileDelete(1,fid);
        }
        return "redirect:/pictureSort";
    }


    @RequestMapping("/otherRename")
    public String otherRename(@RequestParam Integer fid,
                            @RequestParam String newName
    ){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        System.out.println(newName);
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

        }

        return "redirect:/otherSort";
    }

    @RequestMapping("/otherDelete")
    public String otherDetele(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        int showId = fileService.findF_fid(fid);
        if (cfile.getFile_type().equals("folder")){
            deleteFolder(fid);
            fileService.updateFileDelete(1,fid);
        }else {
            fileService.updateFileDelete(1,fid);
        }
        return "redirect:/otherSort";
    }

    @RequestMapping("/musicRename")
    public String musicRename(@RequestParam Integer fid,
                              @RequestParam String newName
    ){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        System.out.println(newName);
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

        }

        return "redirect:/musicSort";
    }

    @RequestMapping("/musicDelete")
    public String musicDetele(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        int showId = fileService.findF_fid(fid);
        if (cfile.getFile_type().equals("folder")){
            deleteFolder(fid);
            fileService.updateFileDelete(1,fid);
        }else {
            fileService.updateFileDelete(1,fid);
        }
        return "redirect:/musicSort";
    }

    private void deleteFolder(int id){
        List<Integer> allSon = fileService.findSonById(id);
        fileService.updateFileDelete(2,id);
        for (int son:allSon){
            fileService.updateFileDelete(2,son);
            deleteFolder(son);
        }
    }
}
