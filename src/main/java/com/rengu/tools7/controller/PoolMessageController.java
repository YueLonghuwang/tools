package com.rengu.tools7.controller;

import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.entity.tb_ToolsBaseinfoEntity;
import com.rengu.tools7.service.FileService;
import com.rengu.tools7.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 徐子钦
 * @date 2020/5/19
 * 工具的增删改查
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pool")
public class PoolMessageController {

    @Autowired
    private FileService fileService;

    /**
     * 根据id查询工具数据
     * @param id
     * @return
     */
    @PostMapping("/findByIdPool")
    public ResultEntity findByIdPool(String id){
        return ResultUtils.success(fileService.findByIdPool(id));
    }

    /**
     * 添加工具信息
     * @param toolsBaseinfoEntity
     * @return
     */
    @GetMapping(value = "/addPool")
    public ResultEntity addPool(     tb_ToolsBaseinfoEntity toolsBaseinfoEntity){
        fileService.addPool(  toolsBaseinfoEntity);
         return ResultUtils.success(toolsBaseinfoEntity) ;
    }

    /**
     * 自定义工具列表
      * @param
     * @return
     */
    @PostMapping("/arrayFile")
    public ResultEntity arrayFile(String poolid /*,String[] fileIds*/){
        String [] fileIds ={"f3725fbe-8e62-4662-a3f0-a7fff59f34c8","f5513c4a-fc1a-4bf7-b061-cf73f6e3ac9b"};
       fileService.arrayFile(poolid,fileIds);
       return ResultUtils.success("ok");
    }

    /**
     * 根据id 修改工具
     * @param toolsBaseinfoEntity
     */
    @PatchMapping(value = "/updatePool")
    public ResultEntity updatePool(  tb_ToolsBaseinfoEntity  toolsBaseinfoEntity){
        fileService.updatePool(  toolsBaseinfoEntity);
        return ResultUtils.success( toolsBaseinfoEntity);
    }

    /**
     * 根据文件名称 查询
     * @param name
     * @return
     */
    @GetMapping(value = "/selectOne")
    public ResultEntity selectOne(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size, String name){
        return ResultUtils.success( fileService.findByName(page,size,name));
    }

    /**
     * 根据文件名称和使用范围进行查询
     * @param name
     * @param scope
     * @return
     */
    @GetMapping(value = "/selectTwo")
    public ResultEntity selectTwo(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size, String name, String scope){
        return ResultUtils.success(fileService.findByNameAndScope(page,size,name,scope)) ;
    }


  @DeleteMapping("/delete")

    public ResultEntity delete(String id){
        fileService.deletePool(id);
        return ResultUtils.success("ok");
  }


}
