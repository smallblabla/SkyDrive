package com.pfy.cloud.service;

import com.pfy.cloud.entity.DownFile;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface DownFileService {
     void addDownFile(DownFile downFile);
     void updateTimeSum(Date time,int fid);
     DownFile findDownFile(int fid);
     List<DownFile> findAllDownFile(int uid);
}
