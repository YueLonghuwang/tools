package com.rengu.tools7.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rengu.tools7.entity.UserEntity;
import com.rengu.tools7.key.Activate;
import com.rengu.tools7.key.TestThread;
import com.rengu.tools7.utils.JsonResultUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.Cipher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collection;
import java.util.Date;

/**
 * @author Eason岳
 * @date 2020/5/7
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

        long expireTime = Activate.expireTime;
        long now = Activate.startTime+ TestThread.time;
        System.out.println("当前时间："+now);
        Long residueTime  = expireTime - now;
        if (now>=expireTime){
            System.out.println("激活码失效！");
            System.exit(0);
            return ;
        }
        String residueTimeString = residueTime.toString();
        //通过公钥进行加密
        String encrypt = null;
        try {
            encrypt = encrypt(residueTimeString, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQKQnJEXFSWbzsRAtBy36uYe03xpNygrZRJiHX/rPeQwLC4RYey4ID5jBgR5x/Ceeg2X+qJJSHXzXfd0vhFWQQ9LXR1ENFOZXzVCcx8mxdrAJHYlEYb/1uCQ7Otl/91HlNW9Tg12ww3oaSsQrSC3dUWT6wbn4Q4NRrDkh64/mVYwIDAQAB");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String content =encrypt ;
            File file = new File("verify\\Page.xml");
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(content);
            bw.close();
            //  System.out.println("finish");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读取

        String s = "";
        StringBuffer  stringBuffer= new StringBuffer();
        BufferedReader br = null;//构造一道个BufferedReader类来读取文件
        try {
            br = new BufferedReader(new FileReader(new File("verify\\Page.xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                if (((s = br.readLine())==null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }//使用内readLine方法，一次读一行容
            stringBuffer.append(s);
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String decrypt = null;
        try {
            decrypt = decrypt(stringBuffer.toString(), "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANApCckRcVJZvOxEC0HLfq5h7TfGk3KCtlEmIdf+s95DAsLhFh7LggPmMGBHnH8J56DZf6oklIdfNd93S+EVZBD0tdHUQ0U5lfNUJzHybF2sAkdiURhv/W4JDs62X/3UeU1b1ODXbDDehpKxCtILd1RZPrBufhDg1GsOSHrj+ZVjAgMBAAECgYBANrivDzNvHQG7vM3gMR9WOh1MnHR12b7WT7YXBJqGERPz/C2vuEXWYBdCTDUJt4t9wjqwyPIMxKFA23NL7yZbyj+b3SKMbX/6m3hpKaM8fVBvJbWyPphWSowp2jvjAjQNOAp0iCUDPPT8iZRuBkl6kiRkk4t38TCPXYnh/NhdqQJBAOrMmU7nhZIlKpUoLbn4OoVaYaydGYjV6uoDrc32y9NaOo7LlH8FVu51P6hdIxl+mQoWJvwhvT5wTWfWNufV6+0CQQDi9K3Uoj83coFCjDwMos9hf/3Xx7mCFjDxP+0zjdz5S41FxAoGDMb+sK1S5PktUJyrpYH9Plxc+x+CpWC7hHyPAkEAmjIZMD9NccekqmXDedDxpafQY0qcfUd2yY328z7zZm5n7GT3tV8tUtZ/IrNfnJuOJ5iaq5QAsuOPgfSlBCVxlQJBAIMnpQEZVfEEAgDkK5udxrxwi9BUAjdT/aqdJ8XAOK/0Ykqr9KL0ZVcv4Knj2r++MxvahJlFnXi6QsZsLc+Py68CQQCfH0jmclSb53VtdB/eGlNpJ58hxU11pqosB7bB8THeMzQazPehp0NXeTyK41s04cg+ixey1/yOgD/a7n5Ym1vr");
        } catch (Exception e) {
            e.printStackTrace();
        }




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
        UserEntity user = (UserEntity) authResult.getPrincipal();
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


    public static String decrypt(String str,String privateKey) throws Exception {
        System.out.println("加密信息："+str);
        System.out.println("私钥："+privateKey);
        // String  str="FDQhWmPgbxWfAlSGsMZKEcjM/QsXcr0NFN1KG4LGMWmHhCGRMCfa9Ju2UG1cvdI0+eddE5Nf2BOQob2j2/+brPluxNLIq0NftoL+1jSHAhfgqBlBnTXI72nKZm2gHL78mDLlzh34MsUb+RGu+kQmYksaH9p/mUCNhg5ILs/Zios=";
        //  String  privateKey="MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMxoD8aw8YkVehMJGsT2m8q6TxDqIfd8WOqyouSb9eps3yMJST26K+8Hp4Yiix5q2y3Zyd4qK6uJ6cIMyBO1inKbh9U//mI5zdQzevta00VWwVMivFyi4+ho++hL5uDIWvxucFwpM32JmsRfe33Cj0HFMidoVst7rAErXha1pDFlAgMBAAECgYEAmenxlyc9eH+XgCXm5lDbVnebeZ0THoufWHpSddmJ/o8kJfmkELrSwhX9at8v6iOEDq5jdspCP9SFE/0T2uNj7zdjqq9RgZ7jN/8z0eXJUBlqsUEvX79TJA4zxmsTT2AZbbicC148kk+dCXX4CZ0G5HEa9COkwBBz/hYQhqtXqIECQQDyU1c6VwdbLXmjKFRS/28Pks8ZmPUchMfVJbaRxZLEgUyePHfvbClrmMAVKTjKJfIMDjWpLs06eGcpgXrpXcstAkEA1/DwN8PxxA+vu3wrLsfKugA/tGO/Q98pXmehw/T4AcVnbLcY+4xFAoRIi03vSSDnrgVvO5fHMnfmO/h5CpkCGQJBAMvfA/cIOx8hnkF/b5o4XtbKW8ScZDNvHrSbuwLfJ0B9y9kg5IKhDCQC6i+jc6zWydIMrma9Alrb62cHEUJ+760CQQCE0cePJVBxXYyjbu5Iqc7BtSF53Nhp8LtLO8a99tEMldTVg5E0N8TClYbhWCBUBI879E/Egcid/C16uYwJvq8RAkEAvV/TclESN6LSNJbqwh5zW56pSYIRl1lSRHixQxqgFG2V0yn/tQHzn3yk20sRrgBcpN28NEx+lYevKrBcTv88Lw==MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJUO5dzNeVytxJzLDy4lfEejkoUsoXBCyHkyoMfYxtIsFXRro6x8u975bCh8QuJIelIjhl3/PM9Wy1a+22x6o7oPnH3sqVGQrE5oOo9cOhXp5gQGBoIYwDA0e2QgibEuKSs2lPDSkDVeIXMDdWx2v8JOEQhEeMvH+xNap2khUPGjAgMBAAECgYBT9JUxnaX6toEkUxcUxdZPP2l6ix3O7j6eTQ7UIVwy6tOWxD3nHEZxG4l48Cx36ZzEAPBgpy9oFRnonQ1X92eZu7KHjsBm+k/tUtn1JoBHIt7NFT1GR+coNOfxt4zOLxnzhViQ9gQdKuIr0Oqeq1sBhekUIHKT4Ofhm/tXeRLyYQJBAMdGH57TcMpQSUVu/tsEpvVY/5idnTcapPQhMzAMWUYU/pBFf8ud8mV8KLcp8kTqRZBJXS5dHIKRjEKclTG08pMCQQC/fVtAJwy/BAWBSSI3swTduHPz1S8boQkI/omvonu91BavCMMy1r+QCEkla3x7xASt5OShl2nsJoz4EHaLGB6xAkEAjB5QqZs/WeKuQrZTWamhxC66OpPJMi/4APk/Ru/H7jFg9g0IWSJMiPOOpYxHIBIiq9wKO0mZfG3fVA9KENONJQJBAI7ubOxHuZXi1JEaG7Zjo6A0DCjW2aDP5MSX6gH1UFPzOTNRYRJThQ1ngeGyh2qCd9Os5JNRR7kDVXncH//W8yECQB54oB3PmdTp5tndmxb9StFanJQzgqehOQJ+Pj9Z8I3uDny/cKlvlvk1CqASXIT/A2DhQ4d6Mg8KgSqqLOQxSlg=";
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        System.out.println(outStr);
        return outStr;
    }
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }
}
