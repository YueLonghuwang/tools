package com.rengu.tools7.controller;
import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.service.MenuService;
import com.rengu.tools7.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @author 徐子钦
 * @date 2020/5/19
 * 权限的增删改查
 */
@RestController
@RequestMapping(value = "/api/v1/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 查询所有的权限
     * @return
     */
    @GetMapping("/db/list")
    public ResultEntity findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        return  ResultUtils.success(menuService.findAllMenusWithRoles(page,size));
    }
    /**
     * 更新角色权限
     */
    @PostMapping("/db/upadateMenu")
    public ResultEntity upadateMenu(String roleId,String[] menuIds){
        menuService.upadateMenu(roleId,menuIds);
        return  ResultUtils.success("ok");
    }
    /**
     * 查询单个角色的下的权限
     * @param roleId
     * @return
     */
    @GetMapping("/db/findMenuByRole")
    public ResultEntity findMenuByRole(String roleId){
         return ResultUtils.success(menuService.findMenuByRole(roleId));
    }

    /**
     * 添加角色
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/db/addMenu")
    public ResultEntity addMenu(String roleId,String menuIds){
            menuService. addMenu(roleId,menuIds);
            return ResultUtils.success("ok");
        }
}
