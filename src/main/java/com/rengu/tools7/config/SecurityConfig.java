package com.rengu.tools7.config;

import com.rengu.tools7.filter.JwtFilter;
import com.rengu.tools7.filter.JwtLoginFilter;
import com.rengu.tools7.filter.MyPermissionFilter;
import com.rengu.tools7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author Eason岳
 * @date 2020/5/7
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   // private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
    @Autowired
    UserService userService;
    @Autowired
    MyPermissionFilter myPermissionFilter;
    @Autowired
    CustomAccessDecisionManager customAccessDecisionManager;

    //使用的密码加密方式
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //注册登录认证方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    //配置登录及注销及权限配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //注册MyFilter和customAccessDecisionManager进行权限管理
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customAccessDecisionManager);
                        o.setSecurityMetadataSource(myPermissionFilter);
                        return o;
                    }
                })
                //路径为“/doLogin”的POST请求自动放行
                .antMatchers(HttpMethod.POST, "/doLogin")
                .permitAll()
                //其它请求都需要认证
                .anyRequest().authenticated()
                .and()
                //添加登录的过滤器,当请求路径为"/doLogin"时该过滤器截取请求
                .addFilterBefore(new JwtLoginFilter("/doLogin",
                        authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //添加token校验的过滤器,每次发起请求都被该过滤器截取
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }



}
