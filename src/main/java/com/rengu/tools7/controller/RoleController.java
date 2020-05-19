package com.rengu.tools7.controller;
import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.entity.RoleEntiey;
import com.rengu.tools7.service.RoleService;
import com.rengu.tools7.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    /**
     * 查询所有角色
     * @return
     */
    @GetMapping("/db/list")
    public ResultEntity findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        return   ResultUtils.success( roleService.findAll(page,size,"1")) ;
    }

    /**
     * 根据id查询角色
     * @param
     * @return
     */
    @GetMapping("/db/findById")
    public ResultEntity  findById(String id ){
         return ResultUtils.success(roleService .findById(id));
    }

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/db/delete")
    public ResultEntity delete(String id ){
        return ResultUtils.success(roleService.delete(id));
    }
    /**
     * 更新角色 如果传过来的id为空则添加角色 有id更新角色
     * @param roleEntiey
     * @return
     */
    @PostMapping("/db/edit")
    public ResultEntity edit(RoleEntiey roleEntiey ){
        roleEntiey.setCompanyId("1");
        if (StringUtils.isEmpty(roleEntiey.getId())){
            //保存
            roleService.save(roleEntiey);
        }else{
            roleService.update(roleEntiey);
        }
        return ResultUtils.success(roleEntiey) ;
    }
}
