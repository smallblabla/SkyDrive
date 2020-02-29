package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.ShareFile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.service.impl.ShareFileServiceImpl;
import com.pfy.cloud.utils.AboutFile;
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
public class IndexController {

    @Autowired
    private FileServiceImpl fileService;

    private static String Path = "F:\\Download";


    /**
     * 跳转到首页
     *
     * @return
     */
    @GetMapping("index")
    public String toUpload(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        List<Cfile> files = fileService.findFileByF(user.getUid(),0);
        model.addAttribute("files",files);
        model.addAttribute("showPath","Root");
        model.addAttribute("nowFartherId",0);
        return "index";
    }

//    /**
//     * 新建文件夹
//     *
//     * @return
//     */
//    @RequestMapping("/addFolder")
//    public String show(String folderName, String showPath, Integer nowFartherId, HttpServletRequest request){
//        User user = (User) request.getSession().getAttribute("user");
//        Cfile cfile = new Cfile();
//        if (nowFartherId != 0){
//            cfile.setFather_fid(nowFartherId);
//        }else {
//            cfile.setFather_fid(0);
//        }
//        cfile.setPicture("/img/folder.png");
//        cfile.setFile_name(folderName);
//        cfile.setUid(user.getUid());
//        cfile.setFile_type("folder");
//        cfile.setCreate_time(AboutTime.getTime());
//        cfile.setShow_position(showPath);
//        String realPath = showPath.replace("Root",Path + File.separator + user.getUsername());
//        cfile.setReal_position(realPath);
//        realPath += File.separator + folderName;
//        File folder = new File(realPath);
//        if (!folder.exists()) {
//            folder.mkdirs();
//            fileService.addFile(cfile);
//        }
//        if (nowFartherId == 0){
//            return "redirect:/index";
//        }
//        return "redirect:/orSon?id="+nowFartherId ;
//    }

    /**
     * 进入文件夹
     *
     * @return
     */
    @RequestMapping("/orSon")
    public String orSon(@RequestParam Integer id,
                        Model model,
                        HttpServletRequest request){
        if (id == 0){
            return "redirect:/index";
        }
        fileService.updateFileTime(AboutTime.getTime(),id);
        User user = (User)request.getSession().getAttribute("user");
        Cfile cfile = fileService.findFileById(id);
        if (cfile.getFile_type().equals("folder")){
            List<Cfile> files = fileService.findFileByF(user.getUid(),id);
            model.addAttribute("files",files);
            model.addAttribute("showPath",cfile.getShow_position()+File.separator+cfile.getFile_name());
            model.addAttribute("nowFartherId",id);
            return "index";
        }
        return "redirect:/index";
    }

    @RequestMapping("/returnFather")
    public String returnFather(@RequestParam Integer id){
        if (id == 0 || fileService.findF_fid(id) == 0){
            return "redirect:/index";
        }
        return "redirect:/orSon?id="+fileService.findF_fid(id);
    }

















//    @RequestMapping("/Rename")
//    public String Rename(@RequestParam Integer fid,
//                         @RequestParam String newName
//                         ){
//        System.out.println(newName);
//        Cfile cfile = fileService.findFileById(fid);
//        File file=new File(cfile.getReal_position() + File.separator + cfile.getFile_name()); //指定文件名及路径
//        if (cfile.getFile_type().equals("folder")){
//            //修改
//            file.renameTo(new File(cfile.getReal_position()+File.separator +newName));
//            fileService.fileRename(newName,fid);
//        }else {
//            if (!file.exists()) {
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            String str1=cfile.getFile_name().substring(0, cfile.getFile_name().indexOf("."));
//            String str2=cfile.getFile_name().substring(str1.length()+1, cfile.getFile_name().length());
//            String rootPath = file.getParent();
//            File newFile = new File(rootPath + File.separator + newName+"."+str2);
//            System.out.println(newFile);
//            System.out.println(rootPath + File.separator + newName+"."+str2);
//            System.out.println(file);
//            if (file.renameTo(newFile)) {
//                System.out.println("修改成功!");
//                fileService.fileRename(newName+"."+str2,fid);
//            } else {
//                System.out.println("修改失败");
//            }
//
////            //修改
////            try {
////                file.renameTo(new File(cfile.getReal_position()+File.separator +newName+"."+str2));
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//
//        }
//
//        return "redirect:/orSon?id="+fileService.findF_fid(fid);
//    }
//
//
//
//
//    /**
//     * 删除文件
//     *
//     * @return
//     */
//    @RequestMapping("/delete")
//    public String detele(@RequestParam Integer fid){
//        Cfile cfile = fileService.findFileById(fid);
//        int showId = fileService.findF_fid(fid);
//        if (cfile.getFile_type().equals("folder")){
//            deleteFolder(fid);
//            fileService.updateFileDelete(1,fid);
//        }else {
//            fileService.updateFileDelete(1,fid);
//        }
//        return "redirect:/orSon?id="+showId;
//    }
//    private void deleteFolder(int id){
//        List<Integer> allSon = fileService.findSonById(id);
//        fileService.updateFileDelete(2,id);
//        for (int son:allSon){
//            fileService.updateFileDelete(2,son);
//            deleteFolder(son);
//        }
//    }


//    @RequestMapping("/delete")
//    public String detele(@RequestParam Integer fid){
//        String path;
//        Cfile cfile;
//        File file;
//        int showId = fileService.findF_fid(fid);
////        for (int fid:fids){
//        cfile = fileService.findFileById(fid);
//        path = cfile.getReal_position() + File.separator + cfile.getFile_name();
//        file = new File(path);
//        if (cfile.getFile_type().equals("folder")){
//            // 删除文件夹
//            System.out.println(path);
//            AboutFile.deleteFileAll(file);
//            deleteFolder(fid);
//        }
//        else {
//            // 路径为文件且不为空则进行删除
//            if (file.isFile() && file.exists()) {
//                file.delete();
//                fileService.deleteById(fid);
//            }
//        }
////        }
//        return "redirect:/orSon?id="+showId;
//    }
//    private void deleteFolder(int id){
//        List<Integer> allSon = fileService.findSonById(id);
//        fileService.deleteById(id);
//        for (int son:allSon){
//            fileService.deleteById(son);
//            deleteFolder(son);
//        }
//    }

//    /**
//     * 分享文件
//     *
//     * @return
//     */
//    @RequestMapping("/shareFile")
//    public String shareFile(@RequestParam Integer fid,Model model){
//        Cfile cfile = fileService.findFileById(fid);
//        cfile.setShare_state("私链分享");
//        ShareFile shareFile = new ShareFile();
//        shareFile.setId(cfile.getFid());
//        shareFile.setUid(cfile.getUid());
//        shareFile.setFile_name(cfile.getFile_name());
//        shareFile.setFile_type(cfile.getFile_type());
//        shareFile.setFile_size(cfile.getFile_size());
//        shareFile.setMd5(cfile.getMd5());
//        shareFile.setReal_position(cfile.getReal_position());
//        shareFile.setShow_position(cfile.getShow_position());
//        shareFile.setShare_state(cfile.getShare_state());
//        shareFile.setPassword(Random.getRandomFourNum());
//        shareFile.setCreate_time(AboutTime.getTime());
//        shareFile.setPicture(cfile.getPicture());
//        shareFileService.addShareFile(shareFile);
//        fileService.updateFileShare(cfile.getShare_state(),cfile.getFid());
//        model.addAttribute("url", Other.getURL(fid,shareFile.getMd5()));
//        model.addAttribute("password",shareFile.getPassword());
//        return "shareSuccess";
//    }
//
//
//    @RequestMapping("/toShare")
//    public String toShare(@RequestParam String md5, @RequestParam int id, Model model){
//        boolean orFind = shareFileService.selectShareFile(id,md5);
//        ShareFile shareFile = shareFileService.selectShareFileById(id);
//        if (!orFind){
//            return "notFind";
//        }
//        model.addAttribute("shareFile",shareFile);
//        return "toShare";
//    }
//
//    @RequestMapping("/Share")
//    public String Share(@RequestParam Integer id, @RequestParam String password, Model model){
//        ShareFile shareFile = shareFileService.selectShareFileById(id);
//        if (shareFile.getPassword().equals(password)){
//            model.addAttribute("shareFile",shareFile);
//            return "share";
//        }
//        return "notFind";
//    }












}
