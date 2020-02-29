package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.DownFile;
import com.pfy.cloud.entity.ShareFile;
import com.pfy.cloud.service.impl.DownFileServiceImpl;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.service.impl.ShareFileServiceImpl;
import com.pfy.cloud.utils.AboutTime;
import com.pfy.cloud.utils.Other;
import com.pfy.cloud.utils.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShareController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private ShareFileServiceImpl shareFileService;
    @Autowired
    private DownFileServiceImpl downFileService;


    /**
     * 分享文件
     *
     * @return
     */
    @RequestMapping("/shareFile")
    public String shareFile(@RequestParam Integer fid,Model model){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        DownFile downFile = new DownFile();
        Cfile cfile = fileService.findFileById(fid);
        cfile.setShare_state("私链分享");
        ShareFile shareFile = new ShareFile();
        shareFile.setId(cfile.getFid());
        shareFile.setUid(cfile.getUid());
        shareFile.setFile_name(cfile.getFile_name());
        shareFile.setFile_type(cfile.getFile_type());
        shareFile.setFile_size(cfile.getFile_size());
        shareFile.setMd5(cfile.getMd5());
        shareFile.setReal_position(cfile.getReal_position());
        shareFile.setShow_position(cfile.getShow_position());
        shareFile.setShare_state(cfile.getShare_state());
        shareFile.setPassword(Random.getRandomFourNum());
        shareFile.setCreate_time(AboutTime.getTime());
        shareFile.setPicture(cfile.getPicture());
        shareFileService.addShareFile(shareFile);
        fileService.updateFileShare(cfile.getShare_state(),cfile.getFid());
        downFile.setUid(cfile.getUid());
        downFile.setFid(cfile.getFid());
        downFile.setDownSum(0);
        downFile.setFile_name(cfile.getFile_name());
        downFile.setFile_size(cfile.getFile_size());
        downFileService.addDownFile(downFile);
        model.addAttribute("url", Other.getURL(fid,shareFile.getMd5()));
        model.addAttribute("password",shareFile.getPassword());
        return "shareSuccess";
    }

    @RequestMapping("/toShare")
    public String toShare(@RequestParam String md5, @RequestParam int id, Model model){
        boolean orFind = shareFileService.selectShareFile(id,md5);
        ShareFile shareFile = shareFileService.selectShareFileById(id);
        if (!orFind){
            return "notFind";
        }
        model.addAttribute("shareFile",shareFile);
        return "toShare";
    }

    @RequestMapping("/Share")
    public String Share(@RequestParam Integer id, @RequestParam String password, Model model){
        ShareFile shareFile = shareFileService.selectShareFileById(id);
        if (shareFile.getPassword().equals(password)){
            model.addAttribute("shareFile",shareFile);
            return "share";
        }
        return "notFind";
    }

    @RequestMapping("/sharePublic")
    public String sharePublic(@RequestParam Integer fid){
        fileService.updateFileTime(AboutTime.getTime(),fid);
        Cfile cfile = fileService.findFileById(fid);
        cfile.setShare_state("共有分享");
        ShareFile shareFile = new ShareFile();
        shareFile.setId(cfile.getFid());
        shareFile.setUid(cfile.getUid());
        shareFile.setFile_name(cfile.getFile_name());
        shareFile.setFile_type(cfile.getFile_type());
        shareFile.setFile_size(cfile.getFile_size());
        shareFile.setMd5(cfile.getMd5());
        shareFile.setReal_position(cfile.getReal_position());
        shareFile.setShow_position(cfile.getShow_position());
        shareFile.setShare_state(cfile.getShare_state());
        shareFile.setPicture(cfile.getPicture());
        shareFile.setCreate_time(AboutTime.getTime());
        shareFileService.addShareFile(shareFile);
        fileService.updateFileShare(cfile.getShare_state(),cfile.getFid());
        DownFile downFile = new DownFile();
        downFile.setUid(cfile.getUid());
        downFile.setFid(cfile.getFid());
        downFile.setDownSum(0);
        downFile.setFile_name(cfile.getFile_name());
        downFile.setFile_size(cfile.getFile_size());
        downFileService.addDownFile(downFile);
        return "success2";
    }
}
