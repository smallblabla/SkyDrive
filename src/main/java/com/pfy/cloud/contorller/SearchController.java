package com.pfy.cloud.contorller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.ShareFile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.pfy.cloud.service.impl.ShareFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ShareFileServiceImpl shareFileService;
    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping("/searchAll")
    public String searchAll(){
        return "toSearch";
    }

    @RequestMapping("/toSearchAll")
    public String toSearchAll(Model model,
                              @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                              @RequestParam String search){
        PageHelper.startPage(pageNum,15);
        List<ShareFile> shareFiles = shareFileService.searchFile(search);
        PageInfo<ShareFile> pageShareFiles = new PageInfo<ShareFile>(shareFiles);
        model.addAttribute("pageShareFiles",pageShareFiles);
        model.addAttribute("search",search);
        return "searchAll";
    }


    @RequestMapping("/searchMyself")
    public String searchMyself(Model model,
                               @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                               @RequestParam String search,
                               HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        PageHelper.startPage(pageNum,10);
        List<Cfile> cfiles = fileService.findSearchFile(search,user.getUid());
        PageInfo<Cfile> pageCfiles = new PageInfo<Cfile>(cfiles);
        model.addAttribute("pageCfiles",pageCfiles);
        model.addAttribute("search",search);
        return "searchMyself";
    }

}
