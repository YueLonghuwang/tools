package com.rengu.tools7.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rengu.tools7.utils.JsonResultUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Zeng
 * @date 2020/2/25 11:43
 * 用户携带的token是否有效
 */
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //获取token
        String cliToken = req.getHeader("token");
        PrintWriter pw;
        if("".equals(cliToken) || cliToken == null){
            pw = resp.getWriter();
            resp.setContentType("application/json;charset=utf-8");
            JsonResultUtil jsonResult = JsonResultUtil.failure("必须传递用户的认证信息", null);
            pw.write(new ObjectMapper().writeValueAsString(jsonResult));
            pw.flush();
            pw.close();
            return ;
        }
        //解析token
        Jws<Claims> jws;
        try {
            jws = Jwts.parser()
                    .setSigningKey("turing-team") //设置生成jwt时使用的密钥
                    .parseClaimsJws(cliToken);
        }catch (JwtException ex){
            pw = resp.getWriter();
            resp.setContentType("application/json;charset=utf-8");
            JsonResultUtil jsonResult = JsonResultUtil.failure("登录已过期,请重新登陆", null);
            pw.write(new ObjectMapper().writeValueAsString(jsonResult));
            pw.flush();
            pw.close();
            return ;
        }

        Claims claims = jws.getBody();
        //获取用户的用户名,在生成token时指定了主题为用户名
        String username = claims.getSubject();
        //获取用户的所有角色,以逗号分割的字符串
        String authoritiesStr = (String) claims.get("authorities");
        //转成用户的所有角色对象
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
        //对用户进行校验
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token);
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
    }


}
