package com.pfy.cloud.service;

import com.pfy.cloud.entity.ShareFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareFileService {
     void addShareFile(ShareFile shareFile);
     ShareFile selectShareFile(int id, String md5);
     ShareFile selectShareFileById(int id);
     List<ShareFile> findPublicFile(int uid);
     List<ShareFile> findPrivateFile(int uid);
     void deleteFileById(int fid);
     List<ShareFile> searchFile(String search);
}
