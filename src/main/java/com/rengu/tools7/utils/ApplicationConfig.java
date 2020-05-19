package com.rengu.tools7.utils;

/**
 * @program: OperationsManagementSuiteV3
 * @author: hanchangming
 * @create: 2018-08-22 16:59
 **/
public class ApplicationConfig {

    // 默认角色
    public static final String DEFAULT_ADMIN_ROLE_NAME = "admin";
    public static final String DEFAULT_USER_ROLE_NAME = "user";
    // 默认管理员用户
    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "admin";

    // 服务器连接地址、端口
    public static final int TCP_RECEIVE_PORT = 6005;
    public static final int UDP_RECEIVE_PORT = 6004;
    public static final int UDP_SEND_PORT = 3087;
    public static final int TCP_DEPLOY_PORT = 3088;
    public static final String SERVER_CAST_ADDRESS = "224.10.10.15";
    public static final int SERVER_BROAD_CAST_PORT = 3086;
    public static final int SERVER_MULTI_CAST_PORT = 3086;

    // 文件块保存路径
    public static final String CHUNKS_SAVE_PATH ="C:\\Users\\xzq52\\Desktop\\json\\aaa" /*FormatUtils.formatPath(FileUtils.getTempDirectoryPath() + File.separator + "OMS" + File.separator + "CHUNKS")*/;
    // 文件保存路径
    public static final String FILES_SAVE_PATH = "C:\\Users\\xzq52\\Desktop\\json\\bbb"/*FormatUtils.formatPath(FileUtils.getUserDirectoryPath() + File.separator + "OMS" + File.separator + "FILES")*/;
    public static final String FILES_FRUIT_SAVE_PATH = "C:\\Users\\xzq52\\Desktop\\json\\fruit"/*FormatUtils.formatPath(FileUtils.getUserDirectoryPath() + File.separator + "OMS" + File.separator + "FILES")*/;


    // 扫描超时时间
    public static final long SCAN_TIME_OUT = 1000 * 10;
    // 部署回复超时时间
    public static final long REPLY_TIME_OUT = 1000 * 10;
}