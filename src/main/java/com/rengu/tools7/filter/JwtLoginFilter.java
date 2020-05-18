package com.rengu.tools7.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rengu.tools7.entity.User;
import com.rengu.tools7.utils.JsonResultUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

/**
 * @author Zeng
 * @date 2020/2/25 11:16
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    /**
     * 从登录参数中提取出用户名密码, 然后调用 AuthenticationManager.authenticate() 方法去进行自动校验
     * @param req
     * @param resp
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException, IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(token);
    }

    /**
     * 校验成功的回调函数，生成jwt的token
     * @param req
     * @param resp
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuffer roles = new StringBuffer();
        //遍历用户角色,将其写入jwt中
        for (GrantedAuthority authority : authorities) {
            roles.append(authority.getAuthority())
                    .append(",");
        }
        String jwt = Jwts.builder()
                .claim("authorities", roles)//配置用户角色
                .setSubject(authResult.getName())//设置jwt的主题为用户的用户名
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))//设置过期时间为10分钟
                .signWith(SignatureAlgorithm.HS512,"turing-team") //使用密钥对头部和载荷进行签名
                .compact();//生成jwt
        //返回给前端
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        User user = (User) authResult.getPrincipal();
        user.setToken(jwt);
        JsonResultUtil jsonResultUtil = JsonResultUtil.success("登录成功", user);
        System.out.println(jsonResultUtil.toString());
        out.write(new ObjectMapper().writeValueAsString(jsonResultUtil));
        out.flush();
        out.close();
    }

    /**
     * 校验失败的回调函数
     * @param req
     * @param resp
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        JsonResultUtil failure = JsonResultUtil.failure("用户名或密码错误,请重新登录!", null);
        out.write(new ObjectMapper().writeValueAsString(failure));
        out.flush();
        out.close();
    }
}
