package com.rengu.tools7.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Eason岳
 * @date 2020/5/7
 * 判断请求当前用户具有哪些角色,如果用户具备访问路径须具备的角色则允许访问,否则判为非法请求
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
    /**
     * 核心方法,判断用户是否可以有权限访问该路径
     * @param authentication 可以获取登录的用户信息
     * @param o 实际是FilterInvocation对象,可以获取请求路径
     * @param collection 访问该路径所需要的角色,是MyFilter中的返回值
     * @throws AccessDeniedException 非法请求,权限不够
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //遍历访问该路径所需要的所有角色名字
        for (ConfigAttribute configAttribute : collection) {
            //如果是登录后可访问,直接放行
            if("ROLE_login".equals(configAttribute.getAttribute())){
                //判断用户是否登录
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录,请前往登录!");
                }
                return ;
            }
            //获取当前用户所具有的所有角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            //遍历该用户的所有角色并判断是否具有必须具备的角色
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(configAttribute.getAttribute())){
                    return ;
                }
            }
        }
        throw new AccessDeniedException("权限不足,请联系管理员!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
