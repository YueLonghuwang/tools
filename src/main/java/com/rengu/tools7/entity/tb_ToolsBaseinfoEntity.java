package com.rengu.tools7.entity;

import lombok.Data;

//表 4信息管理用例描述表
@Data
public class tb_ToolsBaseinfoEntity {
    private String id;
    private String environment; //运行环境
    private String scope;//使用范围
    private String name;//名称
    private String size;//安装包大小
    private String summarize;//功能概述
    private String updateTime;//更新日期
    private String provider;//提供人
    private String developers;//开发商
    private String copyright;//版权信息
    private String describe;//工具描述
    private String score;//分数
    private String typeId;//类型id

    @Override
    public String toString() {
        return "tb_ToolsBaseinfo{" +
                "id='" + id + '\'' +
                ", environment='" + environment + '\'' +
                ", scope='" + scope + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", summarize='" + summarize + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", provider='" + provider + '\'' +
                ", developers='" + developers + '\'' +
                '}';
    }
}
