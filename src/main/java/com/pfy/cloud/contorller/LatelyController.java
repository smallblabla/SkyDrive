package com.pfy.cloud.contorller;

import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.entity.User;
import com.pfy.cloud.service.impl.FileServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LatelyController {

    @Autowired
    private FileServiceImpl fileService;

    @RequestMapping("/lately")
    public String lately(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        List<Cfile> cfiles = fileService.findLately(user.getUid());
        model.addAttribute("files",cfiles);
        return "lately";
    }
}
