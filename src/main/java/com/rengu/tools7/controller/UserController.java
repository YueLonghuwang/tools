package com.rengu.tools7.controller;
import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.entity.UserEntity;
import com.rengu.tools7.service.RoleService;
import com.rengu.tools7.service.UserService;
import com.rengu.tools7.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 徐子钦
 * @date 2020/5/19
 *
 */
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    @Autowired
    private  UserService userService;
    @Autowired
    private  RoleService roleService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    /**
     * 查询所有的用户列表
     * @return 用户列表
     */
    @GetMapping("/db/list")
    public ResultEntity list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        return ResultUtils.success( userService.findAll(page,size,"1"));
    }

    /**
     *
     *     参数：用户id
     *     业务：
     *      1.根据id查询用户
     *      2.查询所有的角色列表
     *
     */
    @GetMapping("/db/roleList")
    public ResultEntity roleList(String id){
        return ResultUtils.success(roleService .findByUserId(id))  ;
    }

    /**z
     * 更新角色
     * @param userid 用户id
     * @param roleIds 角色列表
     * @return
     */
    @GetMapping("/db/changeRole")
    public ResultEntity changeRole(String userid, /*String[] roleIds*/String roleIds){
        roleService .changeRole(userid,roleIds);
        return ResultUtils.success( "ok") ;
    }


//    /**
//     * 根据user对象更新
//     * @param userEntity User对象
//     * @return
//     */
//    @PostMapping("/db/edit")
//    public ResultEntity edit(UserEntity userEntity) {
//        //1.设置企业参数
//        userEntity.setCompanyId("1");
//
//        if (StringUtils.isEmpty(userEntity.getId())) {
//            //保存
//            userService.save(userEntity);
//        }else{
//            //更新
//            userService.update(userEntity);
//        }
//        return  ResultUtils.success(userEntity);
//    }
    @PostMapping("/db/addUser")
    public ResultEntity addUser(UserEntity userEntity,String roleId) {
        //设置企业参数
        userEntity.setCompanyId("1");
         userService.save(userEntity,roleId);
         return ResultUtils.success(userEntity);
    }

    @PostMapping("/db/updateUser")
    public ResultEntity updateUser(UserEntity userEntity){
        userService.update(userEntity);
        return ResultUtils.success(userEntity);
    }

  @DeleteMapping("/db/deleteUser")
    public  ResultEntity deleteUser (String UserId){
         userService.delete(UserId);
         return  ResultUtils.success("ok");
  }

}
