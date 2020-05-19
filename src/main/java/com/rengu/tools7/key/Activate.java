package com.rengu.tools7.key;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activate {
    public static long expireTime;
    public static long startTime;

    public static boolean verification(String privateKey) throws Exception {
        ArrayList<String > time= new ArrayList<>();
        File file = new File("verify\\verify.key");//Text文件知
        BufferedReader br = null;//构造一道个BufferedReader类来读取文件

        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s = null;
        StringBuffer  stringBuffer= new StringBuffer();
        while(true){
            try {
                if (!((s = br.readLine())!=null)) break;
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
        String decrypt = decrypt(stringBuffer.toString(), privateKey);
        System.out.println(decrypt);
        String[] strarr = decrypt.split("@");

        //获取@进行分析日期
        for (int i = 1; i < strarr.length; i++) {
            time.add(strarr[i]);
        }
        long l = System.currentTimeMillis();
        String s1 = time.get(0);
        String s2 = time.get(1);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sf.parse(s1);
        Date date2 = sf.parse(s2);
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        String a="";
        ArrayList<String> allSn = SerialNumberUtil.getAllSn();
        for (int i = 0; i <allSn.size(); i++) {
            a  =a+allSn.get(i);
        }
        if (a.equals(strarr[0])){
            System.out.println("软件信息正确");
        }else {
            System.out.println("软件信息错误");
            System.out.println(strarr[0]);
            System.out.println(a);
      //    System.exit(0);
        }
        expireTime=time2;
        startTime=time1;
        if (l>time1&&l<time2){
            System.out.println("ok");
            return true;
        }else {
            System.out.println("no");
            return false;
        }

    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //System.out.println(str);
        //64位解码加密后的字符串
        byte[] inputByte = new byte[0];
            inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = null;
            priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = null;
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;


    }

}
