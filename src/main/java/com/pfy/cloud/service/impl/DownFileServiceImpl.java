package com.pfy.cloud.service.impl;

import com.pfy.cloud.entity.DownFile;
import com.pfy.cloud.service.DownFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class DownFileServiceImpl {

    @Autowired
    private DownFileService downFileService;

    public void addDownFile(DownFile downFile){
        downFileService.addDownFile(downFile);
    }

    public void updateTimeSum(Date time, int fid){
        downFileService.updateTimeSum(time,fid);
    }

    public DownFile findDownFile(int fid){
        return downFileService.findDownFile(fid);
    }

    public List<DownFile> findAllDownFile(int uid){
        return downFileService.findAllDownFile(uid);
    }

}
