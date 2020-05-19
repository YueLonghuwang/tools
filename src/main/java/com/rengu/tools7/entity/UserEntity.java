package com.rengu.tools7.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Eason岳
 * @date 2020/5/7
 */


public class UserEntity implements UserDetails {

    private String id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean locked;
    private String companyId;
 //用户具备的角色
private List<RoleEntiey> roles;
//登录后返回的token
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<RoleEntiey> getRoles() {
        return roles;
    }


    @Override
    public String getUsername() {
        return username;
    }
    //账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    //账户密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //获取用户的角色
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>(roles.size());
        roles.forEach(role -> {
            list.add(new SimpleGrantedAuthority(role.getName()));
        });
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", companyId='" + companyId + '\'' +
                ", roles=" + roles +
                ", token='" + token + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }



    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setRoles(List<RoleEntiey> roles) {
        this.roles = roles;
    }
}
