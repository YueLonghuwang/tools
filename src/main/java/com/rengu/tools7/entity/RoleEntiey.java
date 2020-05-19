package com.rengu.tools7.entity;

import lombok.Data;

/**
 * @author Eason岳
 * @date 2020/5/7
 */
@Data
public class RoleEntiey {

    private String id;
    private String name;
    private String nameZh;
    private  String companyId;


    @Override
    public String toString() {
        return "RoleEntiey{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameZh='" + nameZh + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
