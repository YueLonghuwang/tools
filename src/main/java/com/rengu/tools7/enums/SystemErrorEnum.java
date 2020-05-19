package com.rengu.tools7.enums;

public enum SystemErrorEnum {


    SUCCESS(0, "请求成功"),
    UNKNOWN_ERROR(0, "未知错误"),
    ROLE_NAME_IS_EMPTY(101, "不存在"),
    ROLE_IS_EXISTS(102, "已存在该角色"),
    ROLE_NOT_FOUND(103, "未发现该角色"),

    //导入文件格式不正确
    UPLOAD_STYLE_ERROR(110, "上传文件格式不正确"),

    MODEL_ID_NOT_FOUND(407, "该id不存在"),
    FILE_MD5_EXISTED(408, "文件MD5不存在"),

    SUBTASK_FILE_ID_NOT_FOUND_ERROR(23001, "未发现该子任务文件ID"),
    FILE_NAME_EXIST(23002, "文件名称已存在"),
    //购物车异常
    SHOPPING_ARGS_NOT_FOUNT(701, "购物车内容不存在"),
    FILE_NOT_EXIST(702, "待在下载的文件不存在"),
    FILE_NAME_NOT_NULL(703, "输入的文件原始文件名为空"),

    DEPARTMENT_NAME_NOT_FOUND(704, "部门名称不存在"),

    USER_USERNAME_IS_EMPTY(201, "用户名参数不存在"),
    USER_PASSWORD_IS_EMPTY(202, "用户密码参数不存在"),
    USER_NAME_NOT_FOUND(205, "用户名不存在"),
    USER_NOT_FOUND(203, "未发现该用户"),
    ROLEENTITY_ID_ISEMPTY(105,"Role id对象为空"),
    USERENTITY_ISEMPTY(104,"未发现该用户"),
    USERENTITY_ID_ISEMPTY(105,"User id为空"),
    CUNKENTITY_IS_NULL(106,"文件对象为空"),
    FIlE_IS_NULL(107,"路径为空"),
    POOL_ID_IS_NULL(108,"poolID为空"),
    TB_TOOLSBASEINFOENTITY_IS_NULL(109,"工具信息为空"),
    FILE_ID_ISEMPTY(110,"文件 id为空"),
    MD5_IS_NULL(111,"md5为空"),
    ROLEIDARRAY_ISNULL(112,"角色数组为空"),
    TOOL_INFORMATION_ISNULL(113,"信息对象为空"),
    VERSION_CONTENT_ISNOLL(114,"版本信息为空"),
    VERSION_NUMBER_ISNOLL(114,"版本号为空"),
    TB_TOOLSEVALUATE_ISNULL(115,"评价信息为空"),
    FILE_IS_BIG(116,"文件过大"),
    ROW_IS_FULL(117,"拒绝添加"),
    EILENAME_CONTENT_ISNOLL(118,"文件名为空"),
    EILETYPE_CONTENT_ISNOLL(119,"文件类型为空"),
    STATE_ISEMPTY(120,"状态吗为空"),
    STATE_ISRUWN(121,"状态码错误"),
    MENUID_IS_NULL(122,"权限id为空"),
    USER_IS_EXISTS(204, "已存在该用户");

    private int code;
    private String message;

    SystemErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
