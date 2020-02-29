package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.utils.AboutFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
public class recycleController {

    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping("/recycle")
    public String recycle(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        List<Cfile> files = fileService.findDeleteFile(user.getUid());
        model.addAttribute("files",files);
        return "recycle";
    }

    @RequestMapping("/deletePermanent")
    public String deletePermanent(@RequestParam Integer fid){
        String path;
        Cfile cfile;
        File file;
        cfile = fileService.findFileById(fid);
        path = cfile.getReal_position() + File.separator + cfile.getFile_name();

        System.out.println(cfile+path);
        file = new File(path);
        if (cfile.getFile_type().equals("folder")){
            // 删除文件夹
            System.out.println(path);
            AboutFile.deleteFileAll(file);
            deleteFolder(fid);
        }
        else {
            // 路径为文件且不为空则进行删除
            if (file.isFile() && file.exists()) {
                file.delete();
                fileService.deleteById(fid);
            }
        }
//        }
        return "redirect:/recycle";
    }

    private void deleteFolder(int id){
        List<Integer> allSon = fileService.findSonById(id);
        fileService.deleteById(id);
        for (int son:allSon){
            fileService.deleteById(son);
            deleteFolder(son);
        }
    }


    @RequestMapping("/reductionFile")
    public String reductionFile(@RequestParam Integer fid){
        Cfile cfile = fileService.findFileById(fid);
        if (cfile.getFile_type().equals("folder")){
            reductionFolder(fid);
            fileService.updateFileDelete(0,fid);
        }else {
            fileService.updateFileDelete(0,fid);
        }
        return "redirect:/recycle";
    }

    private void reductionFolder(int id){
        List<Integer> allSon = fileService.findSonById(id);
        fileService.updateFileDelete(0,id);
        for (int son:allSon){
            fileService.updateFileDelete(0,son);
            reductionFolder(son);
        }
    }


    @RequestMapping("/deleteRecycle")
    public String deleteRecycle(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        List<Cfile> cfiles = fileService.findRecycleFile(user.getUid());
        String path;
        File file;
        for (Cfile cfile : cfiles ){
            path = cfile.getReal_position() + File.separator + cfile.getFile_name();
            file = new File(path);
            if (cfile.getFile_type().equals("folder")){
                // 删除文件夹
                AboutFile.deleteFileAll(file);
                deleteFolder(cfile.getFid());
            }
            else {
                // 路径为文件且不为空则进行删除
                if (file.isFile() && file.exists()) {
                    file.delete();
                    fileService.deleteById(cfile.getFid());
                }
            }
        }
        return "redirect:/recycle";
    }


    @RequestMapping("/reductionRecycle")
    public String reductionRecycle(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        fileService.updateRecycleFile(user.getUid());
        return "redirect:/recycle";
    }



}
