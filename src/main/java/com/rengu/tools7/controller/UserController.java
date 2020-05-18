package com.rengu.tools7.controller;

import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.service.UserService;
import com.rengu.tools7.utils.JsonResultUtil;
import com.rengu.tools7.utils.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping(value = "/findUserByLimit")
//    public ResultEntity findUserByLimit(){
//        return ResultUtils.success(userService.findUserByLimit());
//    }

    @GetMapping(value = "/db/findUserByLimit")
    public JsonResultUtil findUserByLimit(){
        return JsonResultUtil.success("请求成功",userService.findUserByLimit());
    }

}
