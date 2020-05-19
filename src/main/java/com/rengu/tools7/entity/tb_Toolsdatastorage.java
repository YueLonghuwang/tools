package com.rengu.tools7.entity;

import lombok.Data;

//成果管理
@Data
public class tb_Toolsdatastorage {
  private String  id;
    private String userId;
    private String fileId;
    private String filePath;
    private String state;
    private int size;
    private String md5;
    private String name;
    private String type;


}
