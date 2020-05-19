package com.rengu.tools7.filter;

import com.rengu.tools7.entity.Menu;
import com.rengu.tools7.entity.RoleEntiey;
import com.rengu.tools7.mapper.MenuMapper;
import com.rengu.tools7.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author Eason岳
 * @date 2020/5/7
 * 定义过滤器,分析出用户的请求地址匹配逻辑并分析出需要哪些角色
 */
@Component
public class MyPermissionFilter implements FilterInvocationSecurityMetadataSource {

    //路径匹配类,用于检查用户的请求路径是否与数据库中某个路径规则匹配
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    MenuService menuService;
    @Autowired
    MenuMapper menuMapper;
    //每次用户发出请求都会先进入该方法,分析出该请求地址需要哪些角色
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        //强转对象
        FilterInvocation filterInvocation = (FilterInvocation) o;
        //获取用户请求地址
        String requestUrl = filterInvocation.getRequestUrl();
        //获取所有路径规则
        List<Menu> menus = menuMapper.findAllMenusWithRoles();
        //遍历路径规则
        for (Menu menu : menus) {
            //判断与哪一条路由规则匹配
            if(antPathMatcher.match(menu.getPattern(), requestUrl)){
                //获取访问该路径所需要的所有角色
                List<RoleEntiey> roles = menu.getRoles();
                //转化为返回值类型
                String[] rolesStr = new String[roles.size()];
                for (int i = 0; i < rolesStr.length; i++) {
                    rolesStr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(rolesStr);
            }
        }
        //全部都匹配不上,则返回一个默认的标识符,表示该路径是登录后就可以访问的路径
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    //是否支持该方式,返回true
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }



}
