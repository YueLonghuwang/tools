package com.rengu.tools7.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: OperationsManagementSuiteV3
 * @author: hanchangming
 * @create: 2018-08-27 18:32
 **/

@Data
public class ChunkEntity implements Serializable {
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();//添加时间
    private int chunkNumber;//块数量
    private int totalChunks;//总计数
    private long chunkSize;//块大小
    private long totalSize;//总大小
    private String identifier;//md5
    private String filename;//文件名称
    private String relativePath;//文件路径
}
