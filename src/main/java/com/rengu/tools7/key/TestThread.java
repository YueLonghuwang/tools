package com.rengu.tools7.key;

//线程类
public class TestThread extends Thread {
    public static long time ;
    public void run() {
        while(true){
            try {
                sleep(1000);
                //这里可以写你自己要运行的逻辑代码
               time=time+1000;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}