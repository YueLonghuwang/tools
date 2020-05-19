package com.rengu.tools7;


import com.rengu.tools7.key.Activate;
import com.rengu.tools7.key.TestThread;
import com.rengu.tools7.key.verify;

import java.util.Scanner;

public class aaa {

    public static void main(String[] args) throws Exception {
        Scanner scanner=new Scanner(System.in);
     while (true){
         boolean verification = verify.verification();
         if (verification==false){
             System.out.println("请输入密钥");
             boolean verification1 = Activate.verification(scanner.nextLine());
             if (verification1==true){
                 break;
             }else{
                 System.out.println("密钥错误");
             }
         }
     }
        TestThread testThread = new TestThread();
        testThread.start();
        Tools7Application tools7Application =new Tools7Application();
        tools7Application.bb( args);
    }
}
