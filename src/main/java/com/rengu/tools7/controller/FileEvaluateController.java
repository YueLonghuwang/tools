package com.rengu.tools7.controller;

import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.entity.tb_Toolsevaluate;
import com.rengu.tools7.service.FileService;
import com.rengu.tools7.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @author 徐子钦
 * @date 2020/5/19
 * 评价的增删改查
 */
@RestController
@RequestMapping(value = "/api/v1/filesEvaluate")
public class FileEvaluateController {

    @Autowired
    private FileService fileService;

    /**
     * 添加评价
     * @param toolsevaluate
     * @param
     * @return
     */
    @PostMapping("/evaluate")
    public ResultEntity poolEvaluate(tb_Toolsevaluate toolsevaluate){
        fileService. poolEvaluate(toolsevaluate);
        return  ResultUtils.success(toolsevaluate);
    }



    /**
     * 查看评价
     * @param PoolId
     * @param UserId
     * @return
     */
    @GetMapping("/seeEvaluate")
    public ResultEntity seeEvaluate (String PoolId ,String UserId){

        return  ResultUtils.success( fileService. SeeEvaluate(PoolId,UserId));
    }

    /**
     * 删除评价
     * @param PoolId
     * @param UserId
     * @return
     */
    @DeleteMapping("/deleteEvaluate")
    public ResultEntity deleteEvaluate (String PoolId ,String UserId){

        return  ResultUtils.success( fileService. deleteEvaluate(PoolId,UserId));
    }
}
