package com.pfy.cloud.service;

import com.pfy.cloud.entity.Cfile;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface FileService {
     void addFile(Cfile cfile);
     List<Cfile> findUserFile(int uid);
     List<Cfile> findFileByF(int uid, int f_fid);
     int findIdByName(String file_name);
     int findFatherIdById(int fid);
     Cfile findFileById(int fid);
     void deleteById(int fid);
     List<Integer> findSonById(int father_fid);
     void fileRename(String newName, int fid);
     void updateFileShare(String share_state, int fid);
     void updateFileDelete(int state,int fid);
     List<Cfile> findDeleteFile(int uid);
     void deleteRecycleFile(int uid);
     void updateRecycleFile(int uid);
     List<Cfile> findRecycleFile(int uid);
     void updateFileEncryption(boolean encryption,int fid);
     List<Cfile> findSortFile(int uid,String file_type);
     List<Cfile> findSafeFile(int uid);
     void updateAllSafe(int uid);
     void updateFileTime(Date change_time,int fid);
     List<Cfile> findLately(int uid);
     List<Cfile> findSearchFile(String search,int uid);
}
