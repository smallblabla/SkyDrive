package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.ShareFile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.service.impl.ShareFileServiceImpl;
import com.pfy.cloud.utils.Other;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyPrivateShareController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private ShareFileServiceImpl shareFileService;

    @RequestMapping("/myPrivateShare")
    public String myPublicShare(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("shareFiles",shareFileService.findPrivateFile(user.getUid()));
        return "myPrivateShare";
    }

    @RequestMapping("/deleteShare2")
    public String deleteShare(@RequestParam Integer fid){
        shareFileService.deleteFileById(fid);
        fileService.updateFileShare(null,fid);
        return "redirect:/myPrivateShare";
    }

    @RequestMapping("/showUrl")
    public String showUrl(@RequestParam Integer fid,Model model){
        ShareFile shareFile = shareFileService.selectShareFileById(fid);
        model.addAttribute("url", Other.getURL(fid,shareFile.getMd5()));
        model.addAttribute("password",shareFile.getPassword());
        return "shareSuccess";
    }
}
