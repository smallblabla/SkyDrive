package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.FileService;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.service.impl.ShareFileServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyPublicShareController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private ShareFileServiceImpl shareFileService;

    @RequestMapping("/myPublicShare")
    public String myPublicShare(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("shareFiles",shareFileService.findPublicFile(user.getUid()));
        return "myPublicShare";
    }

    @RequestMapping("/deleteShare")
    public String deleteShare(@RequestParam Integer fid){
        shareFileService.deleteFileById(fid);
        fileService.updateFileShare(null,fid);
        return "redirect:/myPublicShare";
    }
}
