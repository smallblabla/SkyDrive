package com.pfy.cloud.service.impl;

import com.pfy.cloud.entity.ShareFile;
import com.pfy.cloud.service.ShareFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShareFileServiceImpl {

    @Autowired
    private ShareFileService shareFileService;

    public void addShareFile(ShareFile shareFile){
        shareFileService.addShareFile(shareFile);
    }

    public boolean selectShareFile(int id,String md5){
         if (shareFileService.selectShareFile(id,md5) != null){
            return true;
         }
         return false;
    }

    public ShareFile selectShareFileById(int id){
        return shareFileService.selectShareFileById(id);
    }

    public List<ShareFile> findPublicFile(int uid){
        return shareFileService.findPublicFile(uid);
    }

    public List<ShareFile> findPrivateFile(int uid){
        return shareFileService.findPrivateFile(uid);
    }

    public void deleteFileById(int fid){
        shareFileService.deleteFileById(fid);
    }

    public List<ShareFile> searchFile(String search){
        return shareFileService.searchFile(search);
    }

}
