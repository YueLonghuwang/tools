package com.rengu.tools7.entity;

import java.util.List;

/**
 * @author Zeng
 * @date 2020/2/24 22:32
 */
public class Menu {

    private Integer id;
    private String pattern;
    //当前路径需要具备的角色
    private List<Role> roles;

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", pattern='" + pattern + '\'' +
                ", roles=" + roles +
                '}';
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
