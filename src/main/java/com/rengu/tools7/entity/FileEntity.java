package com.rengu.tools7.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @program: OperationsManagementSuiteV3
 * @author: hanchangming
 * @create: 2018-08-24 09:01
 **/

@Data

public class FileEntity implements Serializable {


    private String id = UUID.randomUUID().toString();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();
    private String md5;
    private String type;
    private long size;
    private String localPath;
    private String fileName;
    private String filePath;
    private int state;

    private String toolsbaseinfoId;
    private String toolsstorageId;
    private String versionmanagementId;
    private String classifiedmanagementId;
    private String updownloadmanagementId;
    private int averageScore;//平均分
    private float downloadStatistics;//下载统计

    @Override
    public String toString() {
        return "FileEntity{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", md5='" + md5 + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", localPath='" + localPath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
