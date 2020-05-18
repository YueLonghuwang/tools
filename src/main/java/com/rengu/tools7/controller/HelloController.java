package com.rengu.tools7.controller;

import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.entity.User;
import com.rengu.tools7.service.UserService;
import com.rengu.tools7.utils.JsonResultUtil;
import com.rengu.tools7.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zeng
 * @date 2020/2/24 22:55
 */


@RestController
@RequestMapping(value = "/api")
public class HelloController {

    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    @ApiOperation("查找文档")
    public JsonResultUtil hello(){
        return JsonResultUtil.success("成功访问公共接口", null);
    }


    @GetMapping("/db/hello")
    public JsonResultUtil db(){
        return JsonResultUtil.success("成功访问dba角色的接口", null);
    }

    @GetMapping("/admin/hello")
    public JsonResultUtil admin(){
        return JsonResultUtil.success("成功访问admin角色的接口", null);
    }

    @GetMapping("/user/hello")
    public JsonResultUtil user(){
        return JsonResultUtil.success("成功访问user角色的接口", null);
    }

    @PostMapping("/db/addUser")
    public ResultEntity addUser(User user, Integer roleId){
        return ResultUtils.success(userService.addUser(user, roleId));
    }
}
