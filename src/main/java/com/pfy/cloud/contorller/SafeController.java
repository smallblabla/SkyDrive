package com.pfy.cloud.contorller;


import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.service.impl.UserServiceImpl;
import com.pfy.cloud.utils.AboutTime;
import com.pfy.cloud.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class SafeController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/toSafe")
    public String toSafe(){
        return "toSafe";
    }


    @RequestMapping("/safe")
    public String safe(@RequestParam String password,HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        if (userService.selectUser(user.getUsername()).getPassword().equals(MD5Utils.code(password))){
            model.addAttribute("files",fileService.findSafeFile(user.getUid()));
            return "safe";
        }
        return "toSafe";
    }


    @RequestMapping("/safeRename")
    public String safeRename(@RequestParam Integer fid,
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

        return "redirect:/safe";
    }


    @RequestMapping("/safeDelete")
    public String safeDetele(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        int showId = fileService.findF_fid(fid);
        if (cfile.getFile_type().equals("folder")){
            deleteFolder(fid);
            fileService.updateFileDelete(1,fid);
        }else {
            fileService.updateFileDelete(1,fid);
        }
        return "redirect:/safe";
    }
    private void deleteFolder(int id){
        List<Integer> allSon = fileService.findSonById(id);
        fileService.updateFileDelete(2,id);
        for (int son:allSon){
            fileService.updateFileDelete(2,son);
            deleteFolder(son);
        }
    }


    @RequestMapping("/deleteEncryption")
    public String deleteEncryption(@RequestParam Integer fid){
        fileService.updateFileEncryption(false,fid);
        return "redirect:/safe";
    }

    @RequestMapping("/deleteSafe")
    public String deleteSafe(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        fileService.updateAllSafe(user.getUid());
        return "redirect:/safe";
    }
}
