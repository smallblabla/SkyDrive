package com.pfy.cloud.service.impl;

import com.pfy.cloud.entity.Cfile;
import com.pfy.cloud.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class FileServiceImpl {

    @Autowired
    private FileService fileService;

    public void addFile(Cfile cfile){
        try{
            fileService.addFile(cfile);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件添加失败");
        }
    }


    public List<Cfile> findUserFile(int uid){
        return fileService.findUserFile(uid);
    }

    public List<Cfile> findFileByF(int uid, int f_fid){
        return fileService.findFileByF(uid,f_fid);
    }

    public int findId(String file_name){
        return fileService.findIdByName(file_name);
    }

    public int findF_fid(int fid){
        return fileService.findFatherIdById(fid);
    }

    public Cfile findFileById(int fid){
        return fileService.findFileById(fid);
    }

    public void deleteById(int fid){
        try {
            fileService.deleteById(fid);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件删除失败");
        }
    }


    public List<Integer> findSonById(int father_fid){
        return fileService.findSonById(father_fid);
    };

    public void fileRename(String newName,int fid){
        fileService.fileRename(newName,fid);
    }
    public void updateFileShare(String share_state,int fid){
        fileService.updateFileShare(share_state,fid);
    }

    public void updateFileDelete(int state,int fid){
        fileService.updateFileDelete(state,fid);
    }

    public List<Cfile> findDeleteFile(int uid){
        return fileService.findDeleteFile(uid);
    }


    public void deleteRecycleFile(int uid){
        fileService.deleteRecycleFile(uid);
    }

    public void updateRecycleFile(int uid){
        fileService.updateRecycleFile(uid);
    }

    public List<Cfile> findRecycleFile(int uid){
        return fileService.findRecycleFile(uid);
    }

    public void updateFileEncryption(boolean encryption,int fid){
        fileService.updateFileEncryption(encryption,fid);
    }

    public List<Cfile> findSortFile(int uid,String file_type){
        return fileService.findSortFile(uid,file_type);
    }

    public List<Cfile> findSafeFile(int uid){
        return fileService.findSafeFile(uid);
    }

    public void updateAllSafe(int uid){
        fileService.updateAllSafe(uid);
    }

    public void updateFileTime(Date change_time, int fid){
        fileService.updateFileTime(change_time,fid);
    }

    public  List<Cfile> findLately(int uid){
        return fileService.findLately(uid);
    }

    public List<Cfile> findSearchFile(String search,int uid){
        return fileService.findSearchFile(search,uid);
    }
}
