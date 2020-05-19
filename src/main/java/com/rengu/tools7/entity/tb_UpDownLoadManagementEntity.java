package com.rengu.tools7.entity;

import lombok.Data;

//表 8工具上传/下载用例描述表
@Data
public class tb_UpDownLoadManagementEntity {
    private String id;
    private String program;//安装程序
    private String environment;//运行环境
    private  String patch;//补丁
    private String instructions;//使用手册
    private String  goodCase;//优秀案例

    @Override
    public String toString() {
        return "tb_upDownLoadManagement{" +
                "id='" + id + '\'' +
                ", program='" + program + '\'' +
                ", environment='" + environment + '\'' +
                ", patch='" + patch + '\'' +
                ", instructions='" + instructions + '\'' +
                ", goodCase='" + goodCase + '\'' +
                '}';
    }
}
