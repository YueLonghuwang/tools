package com.rengu.tools7.key;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class verify {

    public  static  boolean   verification(){
        //验证当前激活文件是否存在
        File file = new File("verify\\verify.mage");
        if (file.exists()) {
            System.out.println("文件存在");
            return false;
        } else {
            System.out.println("文件不存在");
            establish();
        }
    return true;
    }
   //创建文件以及文件夹
    public static void  establish(){
     File  file  =new File("verify");
     file.mkdir();
    file =new File("verify\\verify.mage");
        try {
            file.createNewFile();
            read(file.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件信息
     *
     * @param fileName  路径
     * @throws IOException
     */
    public static  void  read(String fileName) throws IOException {
        SerialNumberUtil serialNumberUtil =new SerialNumberUtil();
        ArrayList<String> allSn = serialNumberUtil.getAllSn();

        File f=new File(fileName);
        OutputStream out =new FileOutputStream(f,true);
        for (String str : allSn) {
            byte[] b=str.getBytes();
            for (int i = 0; i < b.length; i++) {
                out.write(b[i]);
            }
        }
        out.close();

    }
}
