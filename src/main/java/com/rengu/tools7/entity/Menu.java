package com.rengu.tools7.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Eason岳
 * @date 2020/5/7
 */
@Data
public class Menu {

    private String id;
    private String pattern;
    //当前路径需要具备的角色
    private List<RoleEntiey> roles;
    private String patternZh;
    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", pattern='" + pattern + '\'' +
                ", roles=" + roles +
                '}';
    }

    public List<RoleEntiey> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntiey> roles) {
        this.roles = roles;
    }



    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
